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
public class FIFOCache implements Cache {

    int size;
    ArrayList<Premium> cache_memory;

    //aceasta memorie are o lista cu capacitate limitata de elemente de tip Premium
    public FIFOCache(int size) {
        cache_memory = new ArrayList<>(size);
        this.size = size;
    }

    //functie care va adauga elementele in memoria cache
    @Override
    public void add(Premium object, ArrayList<Premium> cache_memory) {

        if (cache_memory.size() < size) {
            cache_memory.add(object);
        } else {
            //daca nu este spatiu in memorie, se elimina primul element adaugat
            cache_memory.remove(0);
            cache_memory.add(object);
        }
    }

    //functia va printa elementele cerute din memoria cache si va decremnta accesarile Premium si/sau Basic
    @Override
    public int get(ArrayList<Premium> cache_memory, String object_name, PrintWriter pw) {

        if (!cache_memory.isEmpty()) {
            for (int i = 0; i < cache_memory.size(); i++) {
                if (object_name.equals(cache_memory.get(i).object_name)) {
                    if(cache_memory.get(i).hits_Premium > 0) {
                        pw.print("0 Premium\n");
                        cache_memory.get(i).hits_Premium--;
                    }
                    else {
                        if(cache_memory.get(i).hits_Basic > 0) {
                            pw.print("0 Basic\n");
                            cache_memory.get(i).hits_Basic--;
                        }
                        else {
                            pw.print("0 Free\n");
                        }
                    }
                     return 1;
                }
               
            }
            return -1;
        }
        return 0;
    }

    public ArrayList<Premium> getCache_memory() {
        return cache_memory;
    }
    
    //eliminta obiectul din cache cu numele cautat
    public void remove_object(ArrayList<Premium> cache_memory,String name) {
        
        for(int i=0;i<cache_memory.size();i++) {
            if(name.equals(cache_memory.get(i).object_name)) {
                cache_memory.remove(i);
                break;
            }
        }
    }
}
