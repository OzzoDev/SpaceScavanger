/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author OscBurT21
 */
public class GraphicsLoader {

    private GamePanel gp;
    public BufferedImage crater200x200[];
    public BufferedImage resourceIcons[];
    public BufferedImage ammoImages[];
    public BufferedImage bulletImages[];
    public BufferedImage gems[][];
    public BufferedImage backpanel;
    public BufferedImage weapon_panel;
    public BufferedImage cargo_panel;
    public BufferedImage stationImage;
    public BufferedImage stationBack;
    public BufferedImage stButNorm;
    public BufferedImage stButHover;
    public BufferedImage stButPressed;
    public BufferedImage stButLeft;
    public BufferedImage stButRight;
    public BufferedImage stButUp;
    public BufferedImage stButDown;
    //Typsnitt
    public Font basic;
    public Font axaxax;
    public Font axaxax_big;
    public Font axaxax_small;
    public Font axaxax_micro;

    public GraphicsLoader(GamePanel gp) {
        this.gp = gp;
        loadFonts();
        try {
            crater200x200 = getRandomSubimage(200, 200, "gameObjects/craters.jpg", 20);
            resourceIcons = spriteSheetLoader(50, 50, 5, 1, "gameObjects/ResourceIcons50x50.png");
            gems = spriteSheetLoader2D(32, 64, 21, 6, "gameObjects/gems.png");

            bulletImages = spriteSheetLoader(7, 27, 3, 4, "screen/bullets_7x27.png");
            ammoImages = spriteSheetLoader(6, 3, 3, 4, "ships/weaponsAndAmmo/bullets_6x3.png");
            //bilder
            loadScreenImages();
            stationImages();
        } catch (IOException ex) {
            Logger.getLogger(GraphicsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Den här metoden tar en stor bild och sedan randomiserar den små bilder i
     * val storlek från den bilden och retunerar en lista med dessa
     *
     * @param spriteWidth Minibildens bredd
     * @param spriteHeight Minibildens höjd
     * @param filename Mappnamn/filnamn i res
     * @param antal Antalet bilder i arrayen
     * @return En BufferedImage array med 'antal' randomiserade bilder i vald
     * storlek
     * @throws IOException
     */
    private BufferedImage[] getRandomSubimage(int spriteWidth, int spriteHeight, String filename, int antal) throws IOException {
        String filePath = "/spacescavanger/res/" + filename;
        BufferedImage bigImage = ImageIO.read(getClass().getResource(filePath));
        int bigWidth = bigImage.getWidth();
        int bigHeight = bigImage.getHeight();
        BufferedImage image[] = new BufferedImage[antal];
        for (int i = 0; i < antal; i++) {
            int randX = (int) (Math.random() * (bigWidth - spriteWidth));
            int randY = (int) (Math.random() * (bigHeight - spriteHeight));
            image[i] = bigImage.getSubimage(randX, randY, spriteWidth, spriteHeight);
        }
        return image;
    }

    /**
     * Här laddar vi in ett spritesheet och får tillbaka en array av bilder
     *
     * @param spriteWidth Breddden på en enskild spritebild
     * @param spriteHeight Höjden på en enskild spritebild
     * @param colX Antalet spritebilder på en rad
     * @param rowY Antalet rader med spritebilder
     * @param filename Filenamnet inkluderat undermappar
     * @return En array med sprites
     * @throws IOException
     */
    public BufferedImage[] spriteSheetLoader(int spriteWidth, int spriteHeight, int colX, int rowY, String filename) throws IOException {
        String filePath = "/spacescavanger/res/" + filename;
        BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filePath));
        BufferedImage[] sprites = new BufferedImage[colX * rowY];
        for (int sy = 0; sy < rowY; sy++) {
            for (int sx = 0; sx < colX; sx++) {
                sprites[(colX * sy) + sx] = spriteSheet.getSubimage(sx * spriteWidth, sy * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return sprites;
    }

    public BufferedImage[][] spriteSheetLoader2D(int spriteWidth, int spriteHeight, int colX, int rowY, String filename) throws IOException {
        String filePath = "/spacescavanger/res/" + filename;
        BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filePath));
        BufferedImage[][] sprites = new BufferedImage[rowY][colX];
        for (int sy = 0; sy < rowY; sy++) {
            for (int sx = 0; sx < colX; sx++) {
                sprites[sy][sx] = spriteSheet.getSubimage(sx * spriteWidth, sy * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return sprites;
    }

    public void loadFonts() {
        try {
            basic = gp.getFont();
            InputStream ttf = gp.getClass().getResourceAsStream("/spacescavanger/res/screen/axaxax-bd.ttf");
            axaxax = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(20f * (float) (Math.min(gp.scaleX, gp.scaleY)));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(axaxax);
            ttf = gp.getClass().getResourceAsStream("/spacescavanger/res/screen/axaxax-bd.ttf");
            axaxax_big = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(28f * (float) (Math.min(gp.scaleX, gp.scaleY)));
            ge.registerFont(axaxax_big);
            ttf = gp.getClass().getResourceAsStream("/spacescavanger/res/screen/axaxax-bd.ttf");
            axaxax_small = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(16f * (float) (Math.min(gp.scaleX, gp.scaleY)));
            ge.registerFont(axaxax_small);
            ttf = gp.getClass().getResourceAsStream("/spacescavanger/res/screen/axaxax-bd.ttf");
            axaxax_micro = Font.createFont(Font.TRUETYPE_FONT, ttf).deriveFont(8f * (float) (Math.min(gp.scaleX, gp.scaleY)));
            ge.registerFont(axaxax_micro);
        } catch (FontFormatException ex) {
            Logger.getLogger(GraphicsLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphicsLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadScreenImages() throws IOException {
        String filePath = "/spacescavanger/res/screen/frame.png";
        backpanel = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/screen/weapon_panel.png";
        weapon_panel = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/screen/cargo_panel.png";
        cargo_panel = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/rymdstation.png";
        stationImage = ImageIO.read(getClass().getResource(filePath));
    }

    private void stationImages() throws IOException {
        String filePath = "/spacescavanger/res/station/station_bakgr2.png";
        stationBack = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp.png";
        stButNorm = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_hover.png";
        stButHover = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_pressed.png";
        stButPressed = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_left.png";
        stButLeft = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_right.png";
        stButRight = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_upp.png";
        stButUp = ImageIO.read(getClass().getResource(filePath));
        filePath = "/spacescavanger/res/station/knapp_ner.png";
        stButDown = ImageIO.read(getClass().getResource(filePath));
    }
}
