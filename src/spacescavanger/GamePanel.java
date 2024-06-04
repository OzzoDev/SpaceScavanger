/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import spacescavanger.gameobjects.Player;
import spacescavanger.gamestates.GameState;
import spacescavanger.gamestates.GameStateManager;

/**
 *
 * @author OscBurT21
 */
public class GamePanel extends JPanel implements Runnable {

    public int screenWidth;
    public int screenHeight;
    public int worldWidth;
    public int worldHeight;
    public int worldRows = 60;
    public int worldColumns = 80;
    public int orgTileSizeX = 64; //beroende på kartbild 5120x3840
    public int orgTileSizeY = 64; //beroende på kartbild 5120x3840
    public double tileSizeX = 64;
    public double tileSizeY = 64;
    public double scaleX = 1.5;
    public double scaleY = 1.5;

    private Thread gameThread;
    private int numberOfFrames = 0;
    public final int FPS = 60;

    public Input input;
    public GameStateManager gameStateManager;
    public Map map;
    public Player player;
    public Camera camera;
    public GraphicsLoader graphics;

    public GamePanel() {
        screenWidth = 768;
        screenHeight = 480;

//        för fullscreen
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        scaleX = screenSize.getWidth()/screenWidth;
//        scaleY = screenSize.getHeight()/screenHeight;
//        tileSizeX = orgTileSizeX * scaleX;
//        tileSizeY = orgTileSizeY * scaleY;

        worldWidth = (int) (tileSizeX * worldColumns);
        worldHeight = (int) (tileSizeY * worldRows);

        screenWidth = (int) (screenWidth * scaleX);
        screenHeight = (int) (screenHeight * scaleY);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        graphics = new GraphicsLoader(this);
        input = new Input(this);
        gameStateManager = new GameStateManager(this);
        map = new Map(this);
        player = new Player(this, "PlayerName");
        camera = new Camera(this, player.getShip());
        startThread();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //rendera
        gameStateManager.render(g2d);
        g2d.setColor(Color.white);
//        g2d.drawString("" + numberOfFrames, 20, 20);
        //städa upp det som inte behöver renderas längre
        g2d.dispose();
    }

    private void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update() {
        switch (GameState.state) {
            case STARTMENU:
                gameStateManager.update();
                break;
            case PLAYING:
                gameStateManager.update();
                break;
            case GAMEOVER:
                gameStateManager.update();
                break;
            case KILLED:
                gameStateManager.update();
                break;
            case STATION:
                gameStateManager.update();
                break;
            case STATIONSTORAGE:
                gameStateManager.update();
                break;
            case STATIONBUYSHIP:
                gameStateManager.update();
                break;
            case STATIONUPGRADESHIP:
                gameStateManager.update();
                break;
            case STATIONBUYUPGRADE:
                gameStateManager.update();
                break;
            default:
                throw new AssertionError(GameState.state.name());
        }
        camera.update();
        input.update();
    }

    public void changeGameState(GameState gameState) {
        GameState.state = gameState;
        switch (gameState) {
            case STARTMENU:
                gameStateManager.push(gameStateManager.startMenuState);
                break;
            case PLAYING:
                gameStateManager.push(gameStateManager.playingState);
                break;
            case GAMEOVER:
                break;
            case KILLED:
                break;
            case STATION:
                gameStateManager.push(gameStateManager.stationState);
                break;
            case STATIONSTORAGE:
                break;
            case STATIONBUYSHIP:
                break;
            case STATIONUPGRADESHIP:
                break;
            case STATIONBUYUPGRADE:
                break;
            default:
                throw new AssertionError(gameState.name());
        }
    }

    /**
     * Här laddar vi in ett spritesheet och får tillbaka en array av bilder
     *
     * @param spriteWidth Breddden på en enskild spritebild
     * @param spriteHeight Höjden på en enskild spritebild
     * @param colX Antalet spritebilder på en rad
     * @param rowY Antalet rader med spritebilder
     * @param fileName Filenamnet inkluderat undermappar
     * @return En array med sprites
     * @throws IOException
     */
    public BufferedImage[] spriteSheetLoader(int spriteWidth, int spriteHeight, int colX, int rowY, String fileName) throws IOException {
        String filePath = "/spacescavanger/res/" + fileName;
        BufferedImage spriteSheet = ImageIO.read(getClass().getResource(filePath));
        BufferedImage[] sprites = new BufferedImage[colX * rowY];
        for (int sy = 0; sy < rowY; sy++) {
            for (int sx = 0; sx < colX; sx++) {
                sprites[(colX * sy) + sx] = spriteSheet.getSubimage(sx * spriteWidth, sy * spriteHeight, spriteWidth, spriteHeight);
            }
        }
        return sprites;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        //systemets tid
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        long lastCheck = System.currentTimeMillis();
        //starta loopen
        while (gameThread != null) {
            //läs in systemtid
            now = System.nanoTime();
            //beräkna skillnaden, när deltavärdet är >= timePerFrame sker uppdateringen
            if (now - lastFrame >= timePerFrame) {
                long start = System.nanoTime();
                //uppdateringsmetod
                update();
                //rendera
                repaint();
                long end = System.nanoTime();
                long passed = (end - start);
                lastFrame = now;
                numberOfFrames++;
            }
            //när skillanden mellan nuvarande tid och senaste tid är >= 1 sekund 
            //lagras antalet frames i numberOfFrames
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + numberOfFrames);
                numberOfFrames = 0;
            }
        }
    }
}
