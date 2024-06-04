/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.ships.*;

/**
 *
 * @author OscBurT21
 */
public class Player {

    private GamePanel gp;
    private String playerName;
    private Ship ship;
    private int credz = 0;

    public Player(GamePanel gp, String playerName) {
        this.gp = gp;
        this.playerName = playerName;
        ship = new Scrapper(gp);
    }

    public void update() {
        if (gp.input.isKey(KeyEvent.VK_W)) {
//            System.out.println("Ship accelerate");
            ship.accelerate();
        }
        if (gp.input.isKeyUp(KeyEvent.VK_W)) {
//            System.out.println("Ship STOP");
            ship.stopAccelerating();
        }
        if (gp.input.isKey(KeyEvent.VK_S)) {
//            System.out.println("Ship break");
            ship.decellerate();
        }
        if (gp.input.isKeyUp(KeyEvent.VK_S)) {
//            System.out.println("Ship STOP break");
            ship.stopDecellerating();
        }
        if (gp.input.isKey(KeyEvent.VK_A)) {
//            System.out.println("Ship turning left");
            ship.rotateLeft();
        }
        if (gp.input.isKeyUp(KeyEvent.VK_A)) {
//            System.out.println("Ship STOP turning left");
            ship.stopRotatLeft();
        }
        if (gp.input.isKey(KeyEvent.VK_D)) {
//            System.out.println("Ship turning right");
            ship.rotateRight();
        }
        if (gp.input.isKeyUp(KeyEvent.VK_D)) {
//            System.out.println("Ship STOP turning right");
            ship.stopRotatRight();
        }
        if (gp.input.isKey(KeyEvent.VK_SPACE)) {
            switch (ship.getWeapon().getEquipmentType()) {
                case CANNON:
                    break;
                case AUTOCANNON:
                    break;
                case MISSILE:
                    break;
                case BEAM:
                    break;
                case MINE:
                    break;
                case SHIELD:
                    break;
                case DEPLOYABLE:
                    break;
                case RADAR:
                    break;
                case AUTOMATED:
                    break;
                default:
                    throw new AssertionError(ship.getWeapon().getEquipmentType().name());
            }
        }
        if (gp.input.isKeyDown(KeyEvent.VK_SPACE)) {
            switch (ship.getWeapon().getEquipmentType()) {
                case CANNON:
//                    ship.getWeapon().activate();
                    ship.activateAll();
                    break;
                case AUTOCANNON:
                    break;
                case MISSILE:
                    break;
                case BEAM:
                    break;
                case MINE:
                    break;
                case SHIELD:
                    break;
                case DEPLOYABLE:
                    break;
                case RADAR:
                    break;
                case AUTOMATED:
                    break;
                default:
                    throw new AssertionError(ship.getWeapon().getEquipmentType().name());
            }
        }
        if (gp.input.isKeyDown(KeyEvent.VK_E)) {
            ship.nextWeapon();
        }
         if (gp.input.isKeyDown(KeyEvent.VK_Q)) {
            ship.previousWeapon();
        }
        if (gp.input.isKeyDown(KeyEvent.VK_I)) {
            ship.showInfo();
        }
        ship.update();
    }

    public void render(Graphics2D g) {
        ship.render(g);
    }

    public Ship getShip() {
        return ship;
    }
    
    public void addCredz(int value){
        credz+=value;
    }
    
    public int getCredz(){
        return credz;
    }
}
