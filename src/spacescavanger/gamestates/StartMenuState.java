/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import spacescavanger.GamePanel;
import spacescavanger.gamestates.buttons.Button;
import spacescavanger.gamestates.buttons.RectButton;
import spacescavanger.gamestates.buttons.SpecialButton;

/**
 *
 * @author OscBurT21
 */
public class StartMenuState implements State {

    GamePanel gp;
    public GameState thisGameState = GameState.STARTMENU;
    private ArrayList<Button> buttons = new ArrayList<>();

    public StartMenuState(GamePanel gamePanel) {
        this.gp = gamePanel;
        init();
    }

    private void init() {
        Rectangle r = new Rectangle(0, 0, (int)(100*gp.scaleX), (int)(30*gp.scaleY));
        Button b = new RectButton(gp, gp.screenWidth * 3 / 8, gp.screenHeight * 2 / 8, r, "Start Game");
        buttons.add(b);
        b = new RectButton(gp, gp.screenWidth * 3 / 8, gp.screenHeight * 3 / 8, r, "Quit Game");
        buttons.add(b);
        b = new SpecialButton(gp, gp.screenWidth * 2 / 8, gp.screenHeight * 3 / 8, "Quit Game");
        buttons.add(b);
        b = new SpecialButton(gp, gp.screenWidth * 2 / 8, gp.screenHeight * 2 / 8, "Start Game");
        buttons.add(b);
    }

    @Override
    public void update() {
        if (gp.input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.out.println("Avsluta programmet");
            System.exit(0);
        }
        if (gp.input.isKeyDown(KeyEvent.VK_SPACE)) {
            gp.changeGameState(GameState.PLAYING);
            System.out.println("Starta spelet");
        }
        for (Button button : buttons) {
            button.update();
            if (gp.input.isButtonUp(1) && button.isHit(gp.input.getMouseX(), gp.input.getMouseY())) {
                if (button.getText().equalsIgnoreCase("Start Game")) {
                    gp.changeGameState(GameState.PLAYING);
                } else if (button.getText().equalsIgnoreCase("Quit Game")) {
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
//        g2d.setFont(gp.graphics.axaxax);
        g2d.setColor(Color.cyan);
        g2d.fillRect(gp.screenWidth / 8, gp.screenHeight / 8, gp.screenWidth * 6 / 8, gp.screenHeight * 6 / 8);
        g2d.setColor(Color.black);
        g2d.drawRect(gp.screenWidth / 8, gp.screenHeight / 8, gp.screenWidth * 6 / 8, gp.screenHeight * 6 / 8);
//        g2d.setFont(gp.graphics.basic);
        for (Button button : buttons) {
            button.render(g2d);
        }
    }

    @Override
    public GameState getGameState() {
        return thisGameState;
    }

}
