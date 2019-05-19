/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vld
 */
public class Premium extends Basic{
    
    int hits_Premium;
    long timestamp;
    int uses;
    
    //obiectul va avea numele si incercarile arintelui din Basic + alte incercari specifice lui
    //pe langa asta va avea si un timestamp si un numar de folosiri in memoria cache
    public Premium(String name, int hits_Basic, int hits_Premium,long timestamp) {
        super(name, hits_Basic);
        this.hits_Premium = hits_Premium;
        this.timestamp = timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUses(int uses) {
        this.uses = uses;
    }
    
    
    
}
