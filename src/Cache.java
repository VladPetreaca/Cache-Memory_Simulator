/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author vld
 */
public interface Cache {
    
    public void add(Premium object,ArrayList<Premium> cache_memory);
    public int get(ArrayList<Premium> cache_memory,String object_name, PrintWriter pw);
}
