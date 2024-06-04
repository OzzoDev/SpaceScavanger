/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import spacescavanger.GamePanel;
import spacescavanger.gameobjects.equipments.Equipment;

/**
 *
 * @author OscBurT21
 */
public class PlayingState implements State {

    GamePanel gp;
    public GameState thisGameState = GameState.PLAYING;

    public PlayingState(GamePanel gamePanel) {
        this.gp = gamePanel;
    }

    @Override
    public void update() {
        if (gp.input.isKeyUp(KeyEvent.VK_BACK_SPACE)) {
            gp.gameStateManager.pop();
        }
        if (gp.input.isKeyUp(KeyEvent.VK_O) && GameState.state == GameState.PLAYING) {
            gp.changeGameState(GameState.STATION);
            if (gp.gameStateManager.peek() instanceof StationState st) {
                st.setSoldResources(gp.player.getShip().getResoruces());
            }
        }
        if (GameState.state == GameState.PLAYING) {
            gp.player.update();
        }
        gp.map.update();
    }

    @Override
    public void render(Graphics2D g) {
        if (gp.map != null) {
            gp.map.render(g);
        }
        g.setFont(gp.graphics.axaxax);
        g.setColor(Color.yellow);
        g.drawString(gp.player.getCredz() + " €", (int) (10 * gp.scaleX), (int) (20 * gp.scaleY));
        //rendera bakgrundspanel
        g.drawImage(gp.graphics.backpanel, 0, (int) (gp.screenHeight - gp.graphics.backpanel.getHeight() * gp.scaleY), gp.screenWidth, (int) (gp.graphics.backpanel.getHeight() * gp.scaleY), null);
        //rendera vapenpanel
        renderWeaponPanel(g);
        //rendera lastpanel
        renderCargoPanel(g);
        gp.player.render(g);
    }

    private void renderWeaponPanel(Graphics2D g) {
        int posX = (int) (gp.screenWidth / 8 * 5);
        int posY = (int) (gp.screenHeight - gp.graphics.weapon_panel.getHeight() * 0.9 * gp.scaleY);
        g.drawImage(gp.graphics.weapon_panel, posX, posY, (int) (gp.graphics.weapon_panel.getWidth() * gp.scaleX), (int) (gp.graphics.weapon_panel.getHeight() * gp.scaleY), null);
        g.setColor(Color.orange);
        g.setFont(gp.graphics.axaxax_small);
        g.drawString(gp.player.getShip().getWeapon().getEquipmentName(), (int) (posX + 10 * gp.scaleX), (int) (posY + 28 * gp.scaleY));
        g.setFont(gp.graphics.axaxax);
        if (gp.player.getShip().getWeapon().isReloading()) {
            g.setColor(Color.red);
        }
        Equipment weapon = gp.player.getShip().getWeapon();
        BufferedImage ammoImage = weapon.getAmmoImage();
        int width = 152;
        double step = (double) width / weapon.getMaxAmmo() * gp.scaleX;
        g.drawString("" + weapon.getAmmo(), (int) (posX + 10 * gp.scaleX), (int) (posY + 60 * gp.scaleY));
        for (int i = 0; i < weapon.getAmmo(); i++) {
            g.drawImage(ammoImage, (int) (posX + (62 * gp.scaleX) + (step * i)), (int) (posY + 38 * gp.scaleY), (int) (ammoImage.getWidth() * gp.scaleX), (int) (ammoImage.getHeight() * gp.scaleY), null);
        }
    }

    private void renderCargoPanel(Graphics2D g) {
        int posX = (int) (gp.screenWidth / 2 - gp.graphics.cargo_panel.getWidth() * gp.scaleX - 135 * gp.scaleX);
        int posY = (int) (gp.screenHeight - gp.graphics.cargo_panel.getHeight() * 0.9 * gp.scaleY);
        g.drawImage(gp.graphics.cargo_panel, posX, posY, (int) (gp.graphics.cargo_panel.getWidth() * gp.scaleX), (int) (gp.graphics.cargo_panel.getHeight() * gp.scaleY), null);
        int scaleX = 2;
        int row = 0;
        int col = 0;
        int nextrow = 100;
        int sizeX = (int) (210 * gp.scaleX);
        int sizeY = (int) (46 * gp.scaleY);
        int panelX = (int) (posX + 8 * gp.scaleX);
        int panelY = (int) (posY + 20 * gp.scaleY);
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
                panelY += (int) (sizeY / 4);
            }
        }
        g.setColor(new Color(60, 60, 60, 150));
        for (int i = 0; i < gp.player.getShip().getCargoSpace(); i++) {
            g.drawRect(panelX + col * sizeX, panelY + row * sizeY, sizeX, sizeY);
            col++;
            if (i > 0 && (i + 1) % nextrow == 0) {
                row++;
                col = 0;
            }
        }
        col = 0;
        row = 0;
        for (int i = 0; i < gp.player.getShip().getResoruces().size(); i++) {
//            g.drawRect(panelX + col * sizeX, panelY + row * sizeY, sizeX, sizeY);
            g.drawImage(gp.player.getShip().getResoruces().get(i).getImage(), (int) ((panelX + col * sizeX) + (sizeX / 2) * gp.scaleX / 2), panelY + row * sizeY, sizeX / scaleX, sizeY, null);
            col++;
            if (i > 0 && (i + 1) % nextrow == 0) {
                row++;
                col = 0;
            }
        }
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }

}
