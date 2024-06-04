/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spacescavanger.gameobjects.GameObject;
import spacescavanger.gameobjects.GraphicEffects;
import spacescavanger.gameobjects.SpaceStation;
import spacescavanger.gameobjects.obstacle.Asteroid;

/**
 *
 * @author OscBurT21
 */
public class Map {

    private GamePanel gp;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<GameObject> addedObjects = new ArrayList<>();
    private ArrayList<GraphicEffects> graphicEffects = new ArrayList<>();
    private BufferedImage background;
    private BufferedImage[][] bgTiles;
    public SpaceStation spaceStation;

    public Map(GamePanel gp) {
        this.gp = gp;
        loadBackground("worldgraphics/spaceBackground.jpg");
        for (int i = 0; i < 40; i++) {
            GameObject a = new Asteroid(gp, (int) (Math.random() * gp.worldWidth), (int) (Math.random() * gp.worldHeight), (int) (Math.random() * 25 + 70));
            gameObjects.add(a);
        }
        spaceStation= new SpaceStation(gp, gp.worldWidth/2, gp.worldHeight/2);
//            spaceStation= new SpaceStation(gp, 0, 0);
        gameObjects.add(spaceStation);

    }

    public void update() {
        GameObject deadObject = null;
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
            if (gameObject.isDead()) {
                deadObject = gameObject;
                continue;
            }
        }
        if (deadObject != null) {
            gameObjects.remove(deadObject);
        }
        GraphicEffects deadEffect = null;
        for (GraphicEffects ge : graphicEffects) {
            ge.update();
            if (ge.isDead()) {
                deadEffect = ge;
                continue;
            }
        }
        if (deadEffect != null) {
            graphicEffects.remove(deadEffect);
        }
        if (!addedObjects.isEmpty()) {
            for (GameObject addObj : addedObjects) {
                gameObjects.add(addObj);
            }
            addedObjects.clear();
        }
    }

    public void render(Graphics2D g) {
        int tileX = (int) gp.tileSizeX;
        int tileY = (int) gp.tileSizeY;
        //beräkna antalet synliga tiles baserat på skärmstorleken
        int visibleTilesX = (int) gp.screenWidth / tileX + 3;
        int visibleTilesY = (int) gp.screenHeight / tileY + 3;
        //beräkna startpunkten för renderingen av kartan
        int startTileX = (int) gp.camera.getOffX() / tileX;
        int startTileY = (int) gp.camera.getOffY() / tileY;
        for (int y = 0; y < visibleTilesY; y++) {
            for (int x = 0; x < visibleTilesX; x++) {
                int currentTileX = (startTileX + x - 1 + gp.worldColumns) % gp.worldColumns;
                int currentTileY = (startTileY + y - 1 + gp.worldRows) % gp.worldRows;
                int screenX = x * tileX - (int) (gp.camera.getOffX() % tileX) - tileX;
                int screenY = y * tileY - (int) (gp.camera.getOffY() % tileY) - tileY;
                g.drawImage(bgTiles[currentTileY][currentTileX], screenX, screenY, tileX, tileY, null);
            }
        }
        for (int i = 0; i < gameObjects.size(); i++) {
            try {
                gameObjects.get(i).render(g);
            } catch (ConcurrentModificationException e) {
                System.out.println("Caught a ConcurrentModificationException");
                i--;
            }
        }
        //render graphicEffects
        for (int i = 0; i < graphicEffects.size(); i++) {
            try {
                graphicEffects.get(i).render(g);
            } catch (ConcurrentModificationException e) {
                System.out.println("Caught a ConcurrentModificationException");
                i--;
            }
        }
    }

    private void loadBackground(String filename) {
        BufferedImage[] tempBg = null;
        try {
            tempBg = gp.spriteSheetLoader((int) gp.orgTileSizeX, (int) gp.orgTileSizeY, gp.worldColumns, gp.worldRows, filename);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        bgTiles = new BufferedImage[gp.worldRows][gp.worldColumns];
        int counter = 0;
        for (int y = 0; y < gp.worldRows; y++) {
            for (int x = 0; x < gp.worldColumns; x++) {
                bgTiles[y][x] = tempBg[counter];
                counter++;
            }
        }
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<GraphicEffects> getGraphicEffects() {
        return graphicEffects;
    }

    public void addEffect(GraphicEffects ge) {
        graphicEffects.add(ge);
    }

    public void addObject(GameObject go) {
        addedObjects.add(go);
    }

    public GameObject getSpaceStation() {
        return (SpaceStation)spaceStation;
    }
}
