/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vld
 */
public class LFUCache implements Cache {

    int size;
    ArrayList<Premium> cache_memory;

    //aceasta memorie are o lista cu capacitate limitata de elemente de tip Premium
    public LFUCache(int size) {
        this.size = size;
        cache_memory = new ArrayList<>(size);
    }

    //eliminta obiectul din cache cu numele cautat
    public void remove_object(ArrayList<Premium> cache_memory, String name) {

        for (int i = 0; i < cache_memory.size(); i++) {
            if (name.equals(cache_memory.get(i).object_name)) {
                cache_memory.remove(i);
                break;
            }
        }
    }

    //functie care va adauga elementele in memoria cache
    @Override
    public void add(Premium object, ArrayList<Premium> cache_memory) {

        //se adauga in cache daca exista loc in memorie si se seteaza numarul de folosiri la 0 pentru ca urmeaza sa fie folosit
        if (cache_memory.size() < size) {
            object.setUses(0);
            cache_memory.add(object);
        } else {
            //se cauta in cache elemntul cu cele mai putine utilizari si se elimina din memorie
            int minUses = cache_memory.get(0).uses;
            int index = 0;
            for (int i = 1; i < cache_memory.size(); i++) {
                if (minUses > cache_memory.get(i).uses) {
                    minUses = cache_memory.get(i).uses;
                    //minTime = cache_memory.get(i).timestamp;
                    index = i;
                }
            }
            //se adauga un nou obiect cu 0 utilizari
            cache_memory.remove(index);
            object.setUses(0);
            cache_memory.add(object);
        }
    }

    //functia va printa elementele cerute din memoria cache si va decremnta accesarile Premium si/sau Basic
    //de asemenea se incrementeaza numarul de utilizari cand este afisat
    @Override    
    public int get(ArrayList<Premium> cache_memory, String object_name, PrintWriter pw) {
        if (!cache_memory.isEmpty()) {
            for (int i = 0; i < cache_memory.size(); i++) {
                if (object_name.equals(cache_memory.get(i).object_name)) {
                    if (cache_memory.get(i).hits_Premium > 0) {
                        pw.print("0 Premium\n");
                        cache_memory.get(i).hits_Premium--;
                        cache_memory.get(i).uses++;
                    } else {
                        if (cache_memory.get(i).hits_Basic > 0) {
                            pw.print("0 Basic\n");
                            cache_memory.get(i).hits_Basic--;
                            cache_memory.get(i).uses++;
                        } else {
                            pw.print("0 Free\n");
                            cache_memory.get(i).uses++;
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

}
