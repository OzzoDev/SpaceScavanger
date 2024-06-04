/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class Particle {

    private GamePanel gp;
    private int lifeCounter;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double rotation; //rotation är riktning som obejektet tittar mot
    private double direction; //direction är riktning som objektet rör sig
    private double speed;
    private double rotationSpeed;
    private BufferedImage image;
    private Color col;
    private AffineTransform objectTrans = new AffineTransform();
    private boolean hit = false;
    private boolean dead = false;
    private Shape particleShape;
    private GameObject go;
    private BufferedImage[] images;

    public Particle(GamePanel gp, double x, double y, float size, Color col) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.col = col;
        if (size < 2) {
            size = 2.0f;
        }
        if (this.col == null) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            this.col = new Color(r, g, b);
        } else {
            int ro = col.getRGB();
            int go = col.getGreen();
            int bo = col.getBlue();
            int r = (int) (Math.random() * 50 - 25) + ro;
            int g = (int) (Math.random() * 50 - 25) + go;
            int b = (int) (Math.random() * 50 - 25) + bo;
            r = (int) (Math.max(Math.min(r, 255), 0));
            g = (int) (Math.max(Math.min(g, 255), 0));
            b = (int) (Math.max(Math.min(b, 255), 0));
            this.col = new Color(r, g, b);
        }
        objectTrans.translate(x, y);
        particleShape = new Ellipse2D.Float(0 - size / 2.0f, 0 - size / 2.0f, size, size);
        rotation = 0;
        rotationSpeed = 0;
        direction = Math.random() * 360.0;
        speed = Math.random() * gp.scaleX + 1.0;
        lifeCounter = (int) (Math.random() * 20 + 10);
        dx = Math.cos(Math.toRadians(direction)) * speed;
        dy = Math.sin(Math.toRadians(direction)) * speed;
    }

    public Particle(GamePanel gp, double x, double y, Color col) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.col = col;
        if (this.col == null) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            this.col = new Color(r, g, b);
        }
        particleShape = new Rectangle(0,0,20,5);
        rotation = 0;
        rotationSpeed = 0;
        direction = Math.random() * 360.0;
        speed = Math.random() * gp.scaleX + 1.0;
        lifeCounter = (int) (Math.random() * 20 + 10);
        dx = Math.cos(Math.toRadians(direction)) * speed;
        dy = Math.sin(Math.toRadians(direction)) * speed;
    }
    
    public Particle(GamePanel gp, double x, double y, Color col, Shape shape, GameObject go) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.col = col;
        this.go = go;
        if (this.col == null) {
            int r = (int) (Math.random() * 255);
            int g = (int) (Math.random() * 255);
            int b = (int) (Math.random() * 255);
            this.col = new Color(r, g, b);
        }
        particleShape = shape;
        rotation = 0;
        rotationSpeed = 0;
        direction = Math.random() * 360.0;
        speed = Math.random() * gp.scaleX + 1.0;
        lifeCounter = (int) (Math.random() * 20 + 10);
        dx = Math.cos(Math.toRadians(direction)) * speed;
        dy = Math.sin(Math.toRadians(direction)) * speed;
        images = new BufferedImage[1];
        images[0] = gp.graphics.crater200x200[(int) (Math.random() * gp.graphics.crater200x200.length)];
    }

    public void update() {
        lifeCounter--;
        if (lifeCounter < 0) {
            dead = true;
        }
        x += dx;
        y += dy;
        x = (x + gp.worldWidth) % gp.worldWidth;
        y = (y + gp.worldHeight) % gp.worldHeight;
    }

    public void render(Graphics2D g) {
        int screenX = (int) (x - gp.camera.getOffX());
        int screenY = (int) (y - gp.camera.getOffY());
        renderObject(g, screenX, screenY);
        boolean nearRightEdge = screenX > gp.screenWidth - gp.tileSizeX;
        boolean nearBottomEdge = screenY > gp.screenHeight - gp.tileSizeY;
        boolean nearLeftEdge = screenX < 0;
        boolean nearTopEdge = screenY < 0;
        //rakt ut över kanterna
        if (nearRightEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY);
        }
        if (nearLeftEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY);
        }
        if (nearTopEdge) {
            renderObject(g, screenX, screenY + gp.worldHeight);
        }
        if (nearBottomEdge) {
            renderObject(g, screenX, screenY - gp.worldHeight);
        }
        //över kant vid hörn
        if (nearRightEdge && nearTopEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY + gp.worldHeight);
        }
        if (nearRightEdge && nearBottomEdge) {
            renderObject(g, screenX - gp.worldWidth, screenY - gp.worldHeight);
        }
        if (nearLeftEdge && nearTopEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY + gp.worldHeight);
        }
        if (nearLeftEdge && nearBottomEdge) {
            renderObject(g, screenX + gp.worldWidth, screenY - gp.worldHeight);
        }
    }

    public void renderObject(Graphics2D g, double posX, double posY) {
        AffineTransform old = g.getTransform();
        objectTrans = new AffineTransform();
        objectTrans.translate(posX, posY);
        objectTrans.rotate(Math.toRadians(rotation));
        g.transform(objectTrans);
        if(go==null){
            g.setColor(col);
            g.fill(particleShape);
        }else{
            if(images!=null){
                g.setClip(particleShape);
                g.drawImage(images[0], -particleShape.getBounds().width / 2, -particleShape.getBounds().height / 2, (int) (images[0].getWidth() * gp.scaleX), (int) (images[0].getHeight() * gp.scaleY), null);
                g.setClip(null);
            }
        }
        g.setTransform(old);
    }

    public boolean isDead() {
        return dead;
    }

}
