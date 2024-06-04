/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.equipments;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.GameObject;

/**
 *
 * @author OscBurT21
 */
public abstract class Equipment {

    protected GamePanel gp;
    protected Hardpoint hardpoint;
    protected EquipmentType equipmentType;
    protected HardpointType hardpointType;
    protected BufferedImage projecttileImage;
    protected BufferedImage ammoImage;
    protected BufferedImage[] equipmentImages;
    protected String equipmentName;
    protected int imageNr = 0;
    protected int ammo;
    protected int maxAmmo;
    protected int damage;
    protected int range;
    protected int fireDelay;
    protected double reloadTime;
    protected double projectileSpeed;
    protected boolean reloading = false;
    protected boolean manualReloadAllowed = true;

    public Equipment(GamePanel gp, Hardpoint hardpoint) {
        this.gp = gp;
        this.hardpoint = hardpoint;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public void renderScaledImage(Graphics2D g, BufferedImage imageToScale, int x, int y, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        double scaleX = (double) width / imageToScale.getWidth();
        double scaleY = (double) height / imageToScale.getHeight();
        at.scale(scaleX, scaleY);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaleOp.filter(imageToScale, scaledImage);
        g.drawImage(scaledImage, x, y, null);
    }

    public abstract void activate();

    public abstract void activate(GameObject target);

    public abstract void deactivate();

    public void reload() {
        if (manualReloadAllowed) {
            reloading = true;
        }
    }

    public void setHardpoint(Hardpoint hardpoint) {
        this.hardpoint = hardpoint;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public HardpointType getHardpointType() {
        return hardpointType;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public int getAmmo() {
        return ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getFireDealy() {
        return fireDelay;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public boolean isReloading() {
        return reloading;
    }

    public BufferedImage getAmmoImage() {
        return ammoImage;
    }

    public BufferedImage[] getEquipmentImages() {
        return equipmentImages;
    }
    
    
}
