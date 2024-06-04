/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import spacescavanger.GamePanel;
import spacescavanger.Vector2D;
import spacescavanger.gameobjects.Resource;
import spacescavanger.gameobjects.SpaceStation;
import spacescavanger.gamestates.buttons.Button;
import spacescavanger.gamestates.buttons.StationButton;

/**
 *
 * @author OscBurT21
 */
public class StationState implements State {

    private GamePanel gp;
    public GameState thisGameState = GameState.STATION;
    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Resource> soldResources = new ArrayList<>();
    private int panelX;
    private int panelY;
    private BufferedImage backImg;
    private Color col;

    public StationState(GamePanel gp) {
        this.gp = gp;
        init();
    }

    @Override
    public void update() {
        if (gp.input.isKeyUp(KeyEvent.VK_O)) {
            gp.gameStateManager.pop();
        }

        Iterator<Resource> iterator = soldResources.iterator();
        while (iterator.hasNext()) {
            Resource r = iterator.next();
            r.update();
            double sellX = 20 * gp.scaleX;
            double sellY = 20 * gp.scaleY;
            double startX = r.getCargoX();
            double startY = r.getCargoY();
            double dist = Math.sqrt((startX - sellX) * (startX - sellX) + (startY - sellY) * (startY - sellY));

            if (dist < 50) {
                gp.player.addCredz(r.getValue());
                r.setDead(true);
                iterator.remove();
            }
            if (soldResources.isEmpty()) {
                gp.player.getShip().getResoruces().clear();
            }
        }

        mangeButtons();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 190));
        g.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int width = (int) (backImg.getWidth() * gp.scaleX);
        int height = (int) (backImg.getHeight() * gp.scaleY);
        g.drawImage(backImg, panelX, panelY, width, height, null);
        for (Button button : buttons) {
            button.render(g);
        }
        g.setFont(gp.graphics.axaxax_big);
        g.setColor(col.brighter());
        g.drawString("Scavanger Station", (int) (panelX + 132 * gp.scaleX), (int) (panelY + 58 * gp.scaleY));
        drawCargospace(g);
        gp.player.getShip().renderStationImage(g);
    }

    private void mangeButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            Button b = buttons.get(i);
            b.update();
            if (gp.input.isButtonUp(1) && b.isHit(gp.input.getMouseX(), gp.input.getMouseY())) {
                switch (i) {
                    case 0:
                        for (Resource soldResource : soldResources) {
                            double sellX = 20 * gp.scaleX;
                            double sellY = 20 * gp.scaleY;
                            double startX = soldResource.getCargoX() - panelX;
                            double startY = soldResource.getCargoY() - panelY;
                            soldResource.moveTo(startX, startY, sellX, sellY);
                        }
                        System.out.println("sold");
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        //Lämna stationen
                        gp.player.getShip().setPosVect(gp.map.spaceStation.getSpawnPoint());
//                        gp.player.getShip().setPos(gp.map.spaceStation.getSPoint());
                        gp.gameStateManager.pop();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void drawCargospace(Graphics2D g) {
        g.setColor(col.darker());
        float dash[] = {1, 2, 1, 5, 10, 1, 2, 5};
        BasicStroke stroke = new BasicStroke(3, 1, 0, 100, dash, 10);
        g.setStroke(stroke);
        int posX = (int) (panelX + 28 * gp.scaleX);
        int posY = (int) (panelY + 280 * gp.scaleY);
        int scaleX = 2;
        int row = 0;
        int col = 0;
        int nextrow = 100;
        int sizeX = (int) (230 * gp.scaleX);
        int sizeY = (int) (60 * gp.scaleY);
        int px = (int) (posX + 8 * gp.scaleX);
        int py = (int) (posY + 20 * gp.scaleY);
        int cargospace = gp.player.getShip().getCargoSpace();
        if (cargospace >= 33) {
            nextrow = cargospace / 4 + cargospace % 4;
            sizeX /= nextrow;
            sizeY /= 4;
            scaleX = 3;
        } else if (cargospace >= 21) {
            nextrow = cargospace / 3 + cargospace % 3;
            sizeX /= nextrow;
            sizeY /= 3;
            scaleX = 3;
        } else if (cargospace > 10) {
            nextrow = cargospace / 2 + cargospace % 2;
            sizeX /= nextrow;
            sizeY /= 2;
        } else {
            sizeX /= cargospace;
            if (cargospace > 5) {
                sizeY = sizeX;
                py += (int) (sizeY / 4);
            }
        }

        //rutnät
        g.setColor(new Color(60, 60, 60, 150));
        for (int i = 0; i < gp.player.getShip().getCargoSpace(); i++) {
            g.drawRect(px + col * sizeX, py + row * sizeY, sizeX, sizeY);
            col++;
            if (i > 0 && (i + 1) % nextrow == 0) {
                row++;
                col = 0;
            }
        }
        col = 0;
        row = 0;

        for (int i = 0; i < soldResources.size(); i++) {
            if (soldResources.get(i).isSold()) {
                g.drawImage(soldResources.get(i).getImage(), (int) (soldResources.get(i).getCargoX()), (int) (soldResources.get(i).getCargoY()), sizeX / scaleX, sizeY, null);
            } else {
                int cargoX = (int) ((px + col * sizeX) + (sizeX / 2) * gp.scaleX / 2);
                int cargoY = py + row * sizeY;
                g.drawImage(soldResources.get(i).getImage(), cargoX, cargoY, sizeX / scaleX, sizeY, null);
                soldResources.get(i).setCargoX(cargoX + panelX);
                soldResources.get(i).setCargoY(cargoY + panelY);
                col++;
                if (i > 0 && (i + 1) % nextrow == 0) {
                    row++;
                    col = 0;
                }
            }
        }
    }

    public GameState getGameState() {
        return thisGameState;
    }

    public void setSoldResources(ArrayList<Resource> soldResources) {
        this.soldResources = soldResources;
    }

    public void init() {
        col = new Color(34, 54, 240);
        backImg = gp.graphics.stationBack;
        panelX = gp.screenWidth / 2 - (int) (backImg.getWidth() * gp.scaleX / 2);
        panelY = gp.screenHeight / 2 - (int) (backImg.getHeight() * gp.scaleY / 2);
        int bx = (int) (panelX + 275 * gp.scaleX);
        int by = (int) (panelY + 62 * gp.scaleY);
        String[] text = {"Sell Resources", "Storage", "Buy Upgrades", "Buy Ship", "Upgrade Ship", "Leave Station"};
        Button b = null;
        for (int i = 0; i < 6; i++) {
            int diffY = (int) (40 * gp.scaleY) * i;
            b = new StationButton(gp, bx, by + diffY, text[i]);
            buttons.add(b);
        }
    }
}
