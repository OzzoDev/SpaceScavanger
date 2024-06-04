/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.equipments;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.Ship;

/**
 *
 * @author OscBurT21
 */
public class Hardpoint {

    GamePanel gp;
    private Ship ship;
    private HardpointType type;
    private Equipment equipped;
    private String hardpointName = "unnamed hardpoint";
    public Point2D hardCenter;

    public Hardpoint(GamePanel gp, Ship ship, int centerX, int centerY, HardpointType type, String hardpointName) {
        this.gp = gp;
        this.ship = ship;
        this.type = type;
        this.hardpointName = hardpointName;
        hardCenter = new Point2D.Double(centerX, centerY);
    }

    public void update() {
        if (equipped != null) {
            equipped.update();
        }
    }

    public Point2D getHardCenter() {
        Point2D transformedPoint = ship.objectTrans.transform(hardCenter, null);
        return transformedPoint;
    }
    
    public Point2D getHardCenterPoint(){
        return hardCenter;
    }

    public void render(Graphics2D g) {
        if (equipped != null) {
            equipped.render(g);
        }
//        g.setColor(Color.yellow);
//        int sizeX = (int)(6*gp.scaleX);
//        int sizeY = (int)(6*gp.scaleY);
//        g.drawRect((int) hardCenter.getX() - sizeX / 2, (int) hardCenter.getY() - sizeY / 2, sizeX, sizeY);
    }
    
    public void renderScaled(Graphics2D g, double scaleX, double scaleY){
        if (equipped != null) {
            int width = (int) (equipped.getEquipmentImages()[0].getWidth()*scaleX);
            int height = (int) (equipped.getEquipmentImages()[0].getHeight()*scaleY);
            equipped.renderScaledImage(g, equipped.getEquipmentImages()[0],(int)(hardCenter.getX()*scaleX),(int)(hardCenter.getY()*scaleY),width,height);
        }
    }

    public HardpointType getType() {
        return type;
    }

    public Equipment getEquipped() {
        return equipped;
    }

    public String getHardpointName() {
        return hardpointName;
    }

    public Ship getShip() {
        return ship;
    }

    public void setEquipped(Equipment equipped) {
        this.equipped = equipped;
    }
}
