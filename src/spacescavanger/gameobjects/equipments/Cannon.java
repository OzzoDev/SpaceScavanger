/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.equipments;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.GameObject;
import spacescavanger.gameobjects.projectiles.Bullet;

/**
 *
 * @author OscBurT21
 */
public class Cannon extends Equipment {

    private int fireCounter;
    private int reloadTimer = 0;
    private int projectileType = 0;
    private boolean firing = false;

    public Cannon(GamePanel gp, Hardpoint hardpoint) {
        super(gp, hardpoint);
        this.equipmentType = EquipmentType.CANNON;
        this.hardpointType = HardpointType.WEAPON;

        //Basic cannon med grundvärden
        maxAmmo = 20;
        damage = 20;
        range = 900;
        reloadTime = 2.0;
        projectileSpeed = 8.0;
        fireDelay = 10;
        equipmentName = "Small PewPew Cannon";
        projectileType = (int) (Math.random() * gp.graphics.ammoImages.length);
        ammo = maxAmmo;
        fireCounter = 0;

        try {
            projecttileImage = gp.graphics.ammoImages[projectileType];
            ammoImage = gp.graphics.bulletImages[projectileType];
            equipmentImages = gp.spriteSheetLoader(32, 32, 8, 1, "ships/weaponsAndAmmo/tri_cannon.png");
        } catch (IOException ex) {
            Logger.getLogger(Cannon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
        //lägga till animation på Kanonen
        if (firing && !reloading) {

            fireCounter++;
            if (fireCounter >= fireDelay) {
                firing = false;
                fireCounter = 0;
            }
        }
        if (ammo <= 0) {
            reloading = true;
        }
        if (reloading) {
            imageNr = 0;
            reloadTimer++;
            if (reloadTimer >= reloadTime * gp.FPS) {
                reloadTimer = 0;
                reloading = false;
                ammo = maxAmmo;
                firing = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        int width = (int) (equipmentImages[imageNr].getWidth() * gp.scaleX * 0.7);
        int height = (int) (equipmentImages[imageNr].getHeight() * gp.scaleY * 0.7);
        int x = (int) (hardpoint.getHardCenterPoint().getX());
        int y = (int) (hardpoint.getHardCenterPoint().getY());
        g.drawImage(equipmentImages[imageNr], x - width / 2, y - height / 2, width, height, null);
    }

    @Override
    public void activate() {
        if (!firing) {
            double xp = hardpoint.getHardCenter().getX() + gp.camera.getOffX();
            double yp = hardpoint.getHardCenter().getY() + gp.camera.getOffY();
            Bullet b = new Bullet(gp, xp, yp, hardpoint.getShip().getRotation(), projectileSpeed, damage, range, projecttileImage);
            gp.map.addObject(b);
            firing = true;
            ammo--;
        }
    }

    @Override
    public void activate(GameObject target) {

    }

    @Override
    public void deactivate() {

    }

}
