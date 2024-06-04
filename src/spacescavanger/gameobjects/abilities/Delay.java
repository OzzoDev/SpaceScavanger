/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spacescavanger.gameobjects.abilities;

/**
 *
 * @author OscBurT21
 */
public interface Delay {
    
    /**
     * Kolla om fördröjning är aktiv
     * @return true om aktiv
     */
    public boolean isDelayed();
    
    /**
     * Öka fördröjningstimer och kolla om den har nått fördröjningstid
     * @return true om aktiv och falskt om inte (efter beräkning)
     */
    public boolean checkDelayed();
}
