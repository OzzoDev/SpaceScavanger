/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import spacescavanger.GamePanel;
import spacescavanger.Vector2D;
import spacescavanger.gameobjects.abilities.Delay;
import spacescavanger.gameobjects.equipments.Equipment;
import spacescavanger.gameobjects.equipments.Hardpoint;
import spacescavanger.gameobjects.equipments.HardpointType;
import spacescavanger.gamestates.GameState;

/**
 *
 * @author OscBurT21
 */
public class Ship extends GameObject {

    protected double throttle = 0.0;
    protected double turnSpeed;
    protected double maxTurnSpeed;
    protected int updateTimer;
    protected int updateDelay;
    protected int currentWeapon;
    protected int cargoSpace;
    protected boolean accelerating = false;
    protected boolean decellerating = false;
    protected boolean turningLeft = false;
    protected boolean turningRight = false;
    protected String shipModelName = " - ";
    protected String shipName = " noName ";
    protected boolean showShipInfo = false;
    protected ArrayList<Hardpoint> hardpoints = new ArrayList<>();
    protected ArrayList<Resource> resoruces = new ArrayList<>();

    public Ship(GamePanel gp) {
        this.gp = gp;
        rotation = 45.0;
        x = gp.screenWidth / 2;
        y = gp.screenHeight / 2;
        int[] xp = {30, -15, -8, -15};
        int[] yp = {0, 15, 0, -15};
        collisionShape = new Polygon(xp, yp, xp.length);
        acc = 0.004;
        decc = 0.0015;
        rotacc = 0.05;
        maxTurnSpeed = 2.5;
        maxSpeed = 4.5;
        updateTimer = 0;
        updateDelay = 3;
    }

    @Override
    public void update() {
        //kolla kollision
        boolean isColliding = checkCollisions();
        if (isColliding) {
            handleCollisions();
        }
        //uppdatera position och hastighet
        updatePosition();
        //applicera friktion
        updateVelocity();
//        if (turnSpeed > 0&&(!turningRight&&!turningLeft)) {
//            turnSpeed *=0.995;
//            if (turnSpeed<0.3) {
//                turnSpeed = 0.0;
//                System.out.println("noll");
//            }
//        }

        //uppdatera hastighet
        //uppdatera lastutrymme
        //uppdatera vapen
        for (Hardpoint hardpoint : hardpoints) {
            hardpoint.update();
        }
        //kasta ut gems
        jetisonCargo();
    }
    
    public void renderStationImage(Graphics2D g){
        
    }

    protected boolean checkCollisions() {
        boolean isColliding = false;
        for (GameObject gameObject : gp.map.getGameObjects()) {
            if (checkCollision(gameObject.getCollisionShape())) {
                //olika typer av objekt kan kollidera olika 
                if (!(gameObject instanceof Delay delayed && delayed.isDelayed())) {
                    if (gameObject instanceof Resource resource && resoruces.size() < cargoSpace && resource.isPickUp()) {
                        resoruces.add(resource);
                        resource.pickUp();
                    } else if(gameObject instanceof SpaceStation st){
                        velVect.setZero();
                        throttle = 0.0;
                        gp.changeGameState(GameState.STATION);
                    }else {
                        if (!(gameObject instanceof Resource resource)) {
                            gameObject.setHit(true);
                            isColliding = true;
                        }
                    }
                    break;
                }
            }
            gameObject.setHit(false);
        }
        return isColliding;
    }

    protected void handleCollisions() {
        velVect.setZero();
        throttle = 0.0;
    }

    public void rotateLeft() {
        turnSpeed += rotacc;
        if (turnSpeed > maxTurnSpeed) {
            turnSpeed = maxTurnSpeed;
        }
        rotation -= turnSpeed;
        if (rotation > 180.0) {
            rotation -= 360;
        }
        turningLeft = true;
    }

    public void stopRotatLeft() {
        turnSpeed = 0.0;
        turningLeft = false;
    }

    public void rotateRight() {
        turnSpeed += rotacc;
        if (turnSpeed > maxTurnSpeed) {
            turnSpeed = maxTurnSpeed;
        }
        rotation += turnSpeed;
        if (rotation < -180.0) {
            rotation += 360;
        }
        turningRight = true;
    }

    public void stopRotatRight() {
        turnSpeed *= 0.98;
        turningRight = false;
    }

    public void accelerate() {
        throttle += acc;
        accelerating = true;
    }

    public void stopAccelerating() {
        accelerating = false;
        throttle = 0.0;
    }

    public void decellerate() {
        throttle -= decc;
        decellerating = true;
    }

    public void stopDecellerating() {
        decellerating = false;
        throttle = 0.0;
    }

    private void updatePosition() {
        posVect.add(velVect);
        x = posVect.x;
        y = posVect.y;
        double friction = 0.995;
        velVect.scale(friction);
        x = (x + gp.worldWidth) % gp.worldWidth;
        y = (y + gp.worldHeight) % gp.worldHeight;
        posVect.set(x, y);
    }

    private void updateVelocity() {
        Vector2D directionVect = new Vector2D(Math.cos(Math.toRadians(rotation)), Math.sin(Math.toRadians(rotation)));
        velVect.add(directionVect.getScaled(throttle));
        double currentSpeed = velVect.getLength();
        if (currentSpeed > maxSpeed) {
            velVect.scale(maxSpeed / currentSpeed);
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if (showShipInfo) {
            g.setFont(gp.graphics.axaxax_micro);
            g.setColor(Color.orange);
            g.drawString(shipName, (int) (x - gp.camera.getOffX())+50, (int) (y - 50 - gp.camera.getOffY()));
            g.drawString("x:" + (int) (x / gp.tileSizeX) + ", y:" + (int) (y / gp.tileSizeY), (int) (x - gp.camera.getOffX())+50, (int) (y - 40 - gp.camera.getOffY())+5);
            g.drawString("x:" + (int) x + ", y:" + (int) y, (int) (x - gp.camera.getOffX())+50, (int) (y - 30 - gp.camera.getOffY()+10));
        }
    }

    public Equipment getWeapon() {
        if (!hardpoints.isEmpty()) {
            return hardpoints.get(currentWeapon).getEquipped();
        } else {
            return null;
        }
    }

    public void activateAll() {
        for (int i = 0; i < hardpoints.size(); i++) {
            hardpoints.get(i).getEquipped().activate();
        }
    }

    public void nextWeapon() {
        do {
            currentWeapon++;
            if (currentWeapon >= hardpoints.size()) {
                currentWeapon = 0;
            }
        } while (hardpoints.get(currentWeapon).getType() != HardpointType.WEAPON && hardpoints.get(currentWeapon).getType() != HardpointType.GENERAL);
    }

    public void previousWeapon() {
        do {
            currentWeapon--;
            if (currentWeapon < 0) {
                currentWeapon = hardpoints.size() - 1;
            }
        } while (hardpoints.get(currentWeapon).getType() != HardpointType.WEAPON && hardpoints.get(currentWeapon).getType() != HardpointType.GENERAL);
    }

    public void showInfo() {
        showShipInfo = !showShipInfo;
    }

    public void jetisonCargo() {
        if (gp.input.isKeyDown(KeyEvent.VK_J) && !resoruces.isEmpty()) {
            for (Resource resoruce : resoruces) {
                Resource r = new Resource(gp, (int) x, (int) (y + (Math.random() * 50 + 10)), resoruce.getValue(), (int) rotation, 100);
                gp.map.addObject(r);
            }
            resoruces.clear();
        }
    }

    public void generateName() {
        String[] pre = {"Scarlet", "Crimson", "Swooping", "Millenium"};
        String[] post = {"Flower", "Fury", "Hawk", "Falcon"};
        shipName = pre[(int) (Math.random() * pre.length)] + " " + post[(int) (Math.random() * post.length)];
    }

    public int getCargoSpace() {
        return cargoSpace;
    }

    public ArrayList<Resource> getResoruces() {
        return resoruces;
    }

    public double getThrottle() {
        return throttle;
    }

    public double getMaxTurnSpeed() {
        return maxTurnSpeed;
    }

    public int getCurrentWeapon() {
        return currentWeapon;
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public boolean isTurningLeft() {
        return turningLeft;
    }

    public String getShipModelName() {
        return shipModelName;
    }

    public String getShipName() {
        return shipName;
    }

    public ArrayList<Hardpoint> getHardpoints() {
        return hardpoints;
    }
    
    public void setPos(Point2D point){
        System.out.println("PointX: "+point.getX()+" PointY: "+point.getY());
        x = 105;
        y = 0;
        posVect.set(point.getX(), point.getY());
    }
    
    public void setPosVect(Point2D pos){
        this.posVect = new Vector2D(pos.getX(),pos.getY());
    }
    
}
