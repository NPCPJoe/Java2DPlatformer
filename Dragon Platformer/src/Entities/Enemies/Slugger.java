/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities.Enemies;

import Entity.Enemy;
import dragon.TileMap.TileMap;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import Entity.*;
import java.awt.Graphics2D;

/**
 *
 * @author joeco_000
 */
public class Slugger extends Enemy {
   
    private BufferedImage[] sprites;
    
    
    public Slugger(TileMap tm){
        super(tm);
        
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
        health = maxHealth = 2;
        damage = 1;
        
        //load sprites
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
            
            sprites = new BufferedImage[3];
            for(int i = 0; i <sprites.length; i++){
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }
        }
        catch(Exception e){
        e.printStackTrace();
    }
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);
        
        right = true;
        
    }
    
    private void getNextPosition(){
        // movement
    if(left) { dx -= moveSpeed;if(dx < -maxSpeed) {dx = -maxSpeed;
			}
		}
		else if(right) {dx += moveSpeed;if(dx > maxSpeed) {dx = maxSpeed;
			}
		}
		else {if(dx > 0) {dx -= stopSpeed;if(dx < 0) { dx = 0;
				}
			}
			else if(dx < 0) {dx += stopSpeed;if(dx > 0) {dx = 0;
				}
			}
                if(falling){
                    dy+= fallSpeed;
                }
		}
        }
    public void update(){
        
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //check flinching
        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 400){
                flinching = false;
            }
        }
        //if hits a wall
        if(right && dx == 0 ){
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left && dx == 0){
            right = true;
            left = false;
            facingRight = true;
        }
        //update animation
        animation.update();
    }
    public void draw(Graphics2D g){
        
        //if(notOnScreen()) return;
        
        setMapPosition();
        
        super.draw(g);
    }
    
    
}
