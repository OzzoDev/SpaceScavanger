/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gamestates;

import java.awt.Graphics2D;
import java.util.ArrayList;
import spacescavanger.GamePanel;

/**
 *
 * @author OscBurT21
 */
public class GameStateManager {
    
    private GamePanel gp;
    private ArrayList<State> gameStates = new ArrayList<>();
    public State startMenuState;
    public State playingState;
    public State stationState;
    
    public GameStateManager(GamePanel gamePanel) {
        this.gp = gamePanel;
        createGameStates();
    }
    
    public void update() {
        switch (GameState.state) {
            case STARTMENU:
                break;
            case PLAYING:
                break;
            case GAMEOVER:
                break;
            case KILLED:
                break;
            case STATION:
                gameStates.get(gameStates.size() - 2).update();
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
                throw new AssertionError(GameState.state.name());
        }
        peek().update();
    }
    
    public void render(Graphics2D g2d) {
        switch (GameState.state) {
            case STARTMENU:
                break;
            case PLAYING:
                break;
            case GAMEOVER:
                break;
            case KILLED:
                break;
            case STATION:
                gameStates.get(gameStates.size() - 2).render(g2d);
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
                throw new AssertionError(GameState.state.name());
        }
        peek().render(g2d);
    }
    
    public ArrayList<State> getGameStates() {
        return gameStates;
    }
    
    public void push(State gameState) {
        gameStates.add(gameState);
    }
    
    public State peek() {
        return gameStates.get(gameStates.size() - 1);
    }
    
    public void pop() {
        gameStates.remove(gameStates.size() - 1);
        GameState.state = gameStates.get(gameStates.size() - 1).getGameState();
    }
    
    private void createGameStates() {
        startMenuState = new StartMenuState(gp);
        playingState = new PlayingState(gp);
        stationState = new StationState(gp);

        //lägg till och sätt startMenu som start
        gameStates.add(startMenuState);
        GameState.state = GameState.STARTMENU;
    }
}
