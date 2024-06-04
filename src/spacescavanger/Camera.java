/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger;

import spacescavanger.gameobjects.GameObject;

/**
 *
 * @author OscBurT21
 */
public class Camera {

    private GamePanel gp;
    private GameObject target;
    private float offX;
    private float offY;

    public Camera(GamePanel gp, GameObject target) {
        this.gp = gp;
        this.target = target;
    }

    public void update() {
        float targetX = (float) (target.getX() - gp.screenWidth / 2.0f);
        float targetY = (float) (target.getY() - gp.screenHeight / 2.0f);

        offX = targetX;
        offY = targetY;
        //Fördröjning på kameran
//        offX = offX-(offX-targetX)*0.05f;
//        offY = offY-(offY-targetY)*0.05f;

//Om kameran ska stanna vid världens kant
//        if (offX < 0) {
//            offX = 0;
//        }
//        if (offX + gp.screenWidth > gp.worldWidth) {
//            offX = gp.worldWidth - gp.screenWidth;
//        }
//        if (offY < 0) {
//            offY = 0;
//        }
//        if (offY + gp.screenHeight > gp.worldHeight) {
//            offY = gp.worldHeight - gp.screenHeight;
//        }
    }

    public float getOffX() {
        return offX;
    }

    public float getOffY() {
        return offY;
    }
    
    
    
}
