/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.ships;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.Ship;
import spacescavanger.gameobjects.equipments.Cannon;
import spacescavanger.gameobjects.equipments.Hardpoint;
import spacescavanger.gameobjects.equipments.HardpointType;

/**
 *
 * @author OscBurT21
 */
public class Scrapper extends Ship {

    private BufferedImage[] mainEngine;
    private BufferedImage[] rightEngine;
    private BufferedImage[] leftEngine;
    private BufferedImage[] breakEngine;
    private int mainCounter;
    private int rightCounter;
    private int leftCounter;
    private int breakCounter;

    public Scrapper(GamePanel gp) {
        super(gp);
        rotation = 45.0;
        x = gp.screenWidth / 2;
        y = gp.screenHeight / 2;
        shipModelName = "Scrapper";
        try {
            images = gp.spriteSheetLoader(64, 64, 1, 1, "ships/scrapperShip.png");
            mainEngine = gp.spriteSheetLoader(64, 64, 17, 1, "ships/mainEngine.png");
            rightEngine = gp.spriteSheetLoader(64, 64, 17, 1, "ships/rightTurn.png");
            leftEngine = gp.spriteSheetLoader(64, 64, 17, 1, "ships/leftTurn.png");
            breakEngine = gp.spriteSheetLoader(64, 64, 17, 1, "ships/breakEngine.png");
        } catch (IOException ex) {
            Logger.getLogger(Scrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        generateShape();
        acc = 0.004;
        decc = 0.0015;
        rotacc = 0.05;
        maxTurnSpeed = 2.5;
        maxSpeed = 4.5 * gp.scaleX;
        cargoSpace = 40;
        generateName();
//        System.out.println("ShipName: " + shipName);
    }

    @Override
    public void update() {
        super.update();
        updateTimer++;
        if (updateTimer >= updateDelay) {
            //uppdatera vilken bildruta som ska visas
            currentImage++;
            if (currentImage >= images.length - 1) {
                currentImage = 0;
            }
            if (accelerating) {
                if (mainCounter >= mainEngine.length - 5) {
                    mainCounter = 7;
                }
                mainCounter++;
            } else if (!accelerating && mainCounter >= mainEngine.length - 1) {
                mainCounter = 0;
            } else if (mainCounter != 0) {
                mainCounter++;
            }
            if (decellerating) {
                if (breakCounter >= breakEngine.length - 5) {
                    breakCounter = 7;
                }
                breakCounter++;
            } else if (!decellerating && breakCounter >= breakEngine.length - 1) {
                breakCounter = 0;
            } else if (breakCounter != 0) {
                breakCounter++;
            }
            if (turningLeft) {
                if (leftCounter >= leftEngine.length - 5) {
                    leftCounter = 7;
                }
                leftCounter++;
            } else if (!turningLeft && leftCounter >= leftEngine.length - 1) {
                leftCounter = 0;
            } else if (leftCounter != 0) {
                leftCounter++;
            }
            if (turningRight) {
                if (rightCounter >= rightEngine.length - 5) {
                    rightCounter = 7;
                }
                rightCounter++;
            } else if (!turningRight && rightCounter >= rightEngine.length - 1) {
                rightCounter = 0;
            } else if (rightCounter != 0) {
                rightCounter++;
            }
            updateTimer = 0;
        }
    }

    @Override
    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        objectTrans.scale(1.0, 1.0);
        g.transform(objectTrans);
        if (accelerating) {
            g.drawImage(mainEngine[mainCounter], (int) (0 - mainEngine[mainCounter].getWidth() / 2 * gp.scaleX), (int) (0 - mainEngine[mainCounter].getHeight() / 2 * gp.scaleY), (int) (mainEngine[mainCounter].getWidth() * gp.scaleX), (int) (mainEngine[mainCounter].getHeight() * gp.scaleY), null);
        } else if (!accelerating && mainCounter != 0) {
            g.drawImage(mainEngine[mainCounter], (int) (0 - mainEngine[mainCounter].getWidth() / 2 * gp.scaleX), (int) (0 - mainEngine[mainCounter].getHeight() / 2 * gp.scaleY), (int) (mainEngine[mainCounter].getWidth() * gp.scaleX), (int) (mainEngine[mainCounter].getHeight() * gp.scaleY), null);
        }
        if (decellerating) {
            g.drawImage(breakEngine[breakCounter], (int) (0 - breakEngine[breakCounter].getWidth() / 2 * gp.scaleX), (int) (0 - breakEngine[breakCounter].getHeight() / 2 * gp.scaleY), (int) (breakEngine[breakCounter].getWidth() * gp.scaleX), (int) (breakEngine[breakCounter].getHeight() * gp.scaleY), null);
        } else if (!decellerating && breakCounter != 0) {
            g.drawImage(breakEngine[breakCounter], (int) (0 - breakEngine[breakCounter].getWidth() / 2 * gp.scaleX), (int) (0 - breakEngine[breakCounter].getHeight() / 2 * gp.scaleY), (int) (breakEngine[breakCounter].getWidth() * gp.scaleX), (int) (breakEngine[breakCounter].getHeight() * gp.scaleY), null);
        }
        if (turningLeft) {
            g.drawImage(leftEngine[leftCounter], (int) (0 - leftEngine[leftCounter].getWidth() / 2 * gp.scaleX), (int) (0 - leftEngine[leftCounter].getHeight() / 2 * gp.scaleY), (int) (leftEngine[leftCounter].getWidth() * gp.scaleX), (int) (leftEngine[leftCounter].getHeight() * gp.scaleY), null);
        } else if (!turningLeft && leftCounter != 0) {
            g.drawImage(leftEngine[leftCounter], (int) (0 - leftEngine[leftCounter].getWidth() / 2 * gp.scaleX), (int) (0 - leftEngine[leftCounter].getHeight() / 2 * gp.scaleY), (int) (leftEngine[leftCounter].getWidth() * gp.scaleX), (int) (leftEngine[leftCounter].getHeight() * gp.scaleY), null);
        }
        if (turningRight) {
            g.drawImage(rightEngine[rightCounter], (int) (0 - rightEngine[rightCounter].getWidth() / 2 * gp.scaleX), (int) (0 - rightEngine[rightCounter].getHeight() / 2 * gp.scaleY), (int) (rightEngine[rightCounter].getWidth() * gp.scaleX), (int) (rightEngine[rightCounter].getHeight() * gp.scaleY), null);
        } else if (!turningRight && rightCounter != 0) {
            g.drawImage(images[currentImage], (int) (0 - images[currentImage].getWidth() / 2 * gp.scaleX), (int) (0 - images[currentImage].getHeight() / 2 * gp.scaleY), (int) (images[currentImage].getWidth() * gp.scaleX), (int) (images[currentImage].getHeight() * gp.scaleY), null);
        }
        g.drawImage(images[currentImage], (int) (0 - images[currentImage].getWidth() / 2 * gp.scaleX), (int) (0 - images[currentImage].getHeight() / 2 * gp.scaleY), (int) (images[currentImage].getWidth() * gp.scaleX), (int) (images[currentImage].getHeight() * gp.scaleY), null);
        if (!hardpoints.isEmpty()) {
            for (Hardpoint hardpoint : hardpoints) {
                hardpoint.render(g);
            }
        }
        g.setTransform(old);
    }

    @Override
    public void renderStationImage(Graphics2D g) {
        AffineTransform old = g.getTransform();
        AffineTransform stationTrans = new AffineTransform();
        stationTrans.translate(gp.screenWidth / 2 - 95 * gp.scaleX, gp.screenHeight / 2 - 10 * gp.scaleY);
        stationTrans.rotate(Math.toRadians(-90.0));
        stationTrans.scale(2.0, 2.0);
        g.transform(stationTrans);
//        int scaleX = 4;
//        int scaleY = 4;
//        int width = images[0].getWidth() * scaleX;
//        int height = images[0].getHeight() * scaleY;
//        renderScaledImage(g, images[0], -width / 2, -width / 2, width, height);
        g.drawImage(images[0], (int) (0 - images[0].getWidth() / 2 * gp.scaleX), (int) (0 - images[0].getHeight() / 2 * gp.scaleY), (int) (images[0].getWidth() * gp.scaleX), (int) (images[currentImage].getHeight() * gp.scaleY), null);
        if (!hardpoints.isEmpty()) {
            for (Hardpoint hardpoint : hardpoints) {
                hardpoint.render(g);
            }
        }
        g.setTransform(old);
        g.setColor(new Color(34, 54, 240));
        g.setFont(gp.graphics.axaxax_small);
//        g.drawString("", width, width);
    }

    private void renderScaledImage(Graphics2D g2d, BufferedImage imageToScale, int x, int y, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        double scaleX = (double) width / imageToScale.getWidth();
        double scaleY = (double) height / imageToScale.getHeight();
        at.scale(scaleX, scaleY);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaleOp.filter(imageToScale, scaledImage);
        g2d.drawImage(scaledImage, x, y, null);
    }

    private void generateShape() {
        int[] xp = {(int) (31 * gp.scaleX), (int) -(12 * gp.scaleX), (int) (-15 * gp.scaleX), (int) (-12 * gp.scaleX)};
        int[] yp = {(int) (-1 * gp.scaleY), (int) (14 * gp.scaleY), (int) (0 * gp.scaleY), (int) (-15 * gp.scaleY)};
        collisionShape = new Polygon(xp, yp, xp.length);
        Hardpoint h = new Hardpoint(gp, this, (int) (22 * gp.scaleX), (int) (0 * gp.scaleY), HardpointType.WEAPON, "Front gun");
        Cannon pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);
        hardpoints.add(h);
        h = new Hardpoint(gp, this, (int) (13 * gp.scaleX), (int) (18 * gp.scaleY), HardpointType.WEAPON, "Left gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);
        h = new Hardpoint(gp, this, (int) (13 * gp.scaleX), (int) (-18 * gp.scaleY), HardpointType.WEAPON, "Right gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);
        //all temp cannon
        h = new Hardpoint(gp, this, (int) (-5 * gp.scaleX), (int) (-20 * gp.scaleY), HardpointType.WEAPON, "Back left gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);

        h = new Hardpoint(gp, this, (int) (-5 * gp.scaleX), (int) (20 * gp.scaleY), HardpointType.WEAPON, "Back right gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);

        h = new Hardpoint(gp, this, (int) (-22 * gp.scaleX), (int) (-24 * gp.scaleY), HardpointType.WEAPON, "Last left gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);

        h = new Hardpoint(gp, this, (int) (-22 * gp.scaleX), (int) (24 * gp.scaleY), HardpointType.WEAPON, "Last right gun");
        hardpoints.add(h);
        pewpew = new Cannon(gp, h);
        h.setEquipped(pewpew);

//        for (int i = 0; i < 10; i++) {
//            h = new Hardpoint(gp, this, (int) (Math.random()*80-40), (int) (Math.random()*80-40), HardpointType.WEAPON, "Last right gun");
//            hardpoints.add(h);
//            pewpew = new Cannon(gp, h);
//            h.setEquipped(pewpew);
//        }
    }
}
