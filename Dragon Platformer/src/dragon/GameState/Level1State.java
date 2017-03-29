/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dragon.GameState;

import Entities.Enemies.Slugger;
import Entity.*;
import java.awt.event.KeyEvent;
import dragon.TileMap.*;
import dragon.platformer.GamePanel;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author joeco_000
 */
public class Level1State extends GameState {
    
    private TileMap tileMap;
	private Background bg;
	private HUD hud;
        private ArrayList<Enemy> enemies;
        private ArrayList<Explosion> explosion;
	private Player player;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		
		player = new Player(tileMap);
		player.setPosition(100, 100);
	
                
                
              populateEnemies();
                
                explosion = new ArrayList<Explosion>(); 
                hud = new HUD(player);
	}
	
	
	public void update() {
		
		// update player
		player.update();
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
                // set Background
                bg.setPosition(tileMap.getx(), tileMap.gety());
		
                
                // attack enemnies
                player.checkAttack(enemies);
                
                //update enemies
                for(int i = 0; i < enemies.size(); i ++){
                    Enemy e = enemies.get(i);
                e.update();
                if(e.isDead()){
                    enemies.remove(i);
                    i--;
                    explosion.add(new Explosion(e.getx(), e.gety()));
                }
        }
            // update explosion
            for(int i = 0; i < explosion.size(); i++){
            explosion.get(i).update();
            if(explosion.get(i).shouldRemove()){
                explosion.remove(i); i --;
            }
        }
        }
          private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {
			new Point(200, 100),
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}      
        
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
                // draw enemies
                for(int i = 0; i < enemies.size(); i++){
                    enemies.get(i).draw(g);
                }
                //draw explosions
                for(int i = 0; i < explosion.size(); i++){
                    explosion.get(i).setMapPosition((int) tileMap.getx(), (int) tileMap.gety());
                    explosion.get(i).draw(g);
                }
                
                //draw hud
                hud.draw(g);
	}

    
    public void keyPressed(int k) {
       if( k == KeyEvent.VK_A) player.setLeft(true);
       if( k == KeyEvent.VK_D) player.setRight(true);
       if( k == KeyEvent.VK_W) player.setUp(true);
       if( k == KeyEvent.VK_S) player.setDown(true);
       if( k == KeyEvent.VK_SPACE) player.setJumping(true);
       if( k == KeyEvent.VK_E) player.setGliding(true);
       if( k == KeyEvent.VK_Q) player.setScratching();
       if( k == KeyEvent.VK_R) player.setFiring();
    }

    
    public void keyReleased(int k) {
          if( k == KeyEvent.VK_A) player.setLeft(false);
       if( k == KeyEvent.VK_D) player.setRight(false);
       if( k == KeyEvent.VK_W) player.setUp(false);
       if( k == KeyEvent.VK_S) player.setDown(false);
       if( k == KeyEvent.VK_SPACE) player.setJumping(false);
       if( k == KeyEvent.VK_E) player.setGliding(false);
    }
    
}
