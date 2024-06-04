/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package spacescavanger.gameobjects.abilities;

/**
 *
 * @author OscBurT21
 */
public interface Destoryable {
    //hitpoint, maxHitspoints
    /**
     * Vad som ska hända med förstörda objekt
     */
    public void destoryed();
    
    /**
     * Applicera skada på objekt
     * @param damage mängden skada
     */
    public void takeDamage(int damage);
    
//    public boolean isTargeted();
//    
//    public void setTargeted();
}
