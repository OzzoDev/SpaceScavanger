/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package spacescavanger.gamestates;

/**
 *
 * @author OscBurT21
 */
public enum GameState {
    STARTMENU, PLAYING, GAMEOVER, KILLED, STATION, STATIONSTORAGE, STATIONBUYSHIP, STATIONUPGRADESHIP, STATIONBUYUPGRADE;
    
    public static GameState state = STARTMENU;

}
