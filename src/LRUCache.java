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
public class LRUCache implements Cache {

    int size;
    ArrayList<Premium> cache_memory;

    //aceasta memorie are o lista cu capacitate limitata de elemente de tip Premium
    public LRUCache(int size) {
        this.size = size;
        cache_memory = new ArrayList<>(size);
    }

    @Override
    //functie care va adauga elementele in memoria cache
    public void add(Premium object, ArrayList<Premium> cache_memory) {

        //daca mai este loc in memorie, se adauga un nou proces
        if (cache_memory.size() < size) {
            //se adauga elementului un timestamp pentru a sti decand este in memorie
            object.setTimestamp(System.currentTimeMillis());
            try {
                //se incetineste programul cu o milisecunda(este posibil sa adauge,lucrand foarte repede,acelasi timestamp
                //la mai multe elemente)
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(LRUCache.class.getName()).log(Level.SEVERE, null, ex);
            }
            cache_memory.add(object);
        } else {
            //se cauta elementul cu cel mai mic timestamp si se retine pozitia lui pentru a sti de unde trebuie eliminat
            long min = cache_memory.get(0).timestamp;
            int index = 0;
            for (int i = 1; i < cache_memory.size(); i++) {
                if (min > cache_memory.get(i).timestamp) {
                    min = cache_memory.get(i).timestamp;
                    index = i;
                    //break;
                }
            }
            //se elimina elementul, si se adauga cel nou in cache
            cache_memory.remove(index);
             object.setTimestamp(System.currentTimeMillis());
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(LRUCache.class.getName()).log(Level.SEVERE, null, ex);
            }
            cache_memory.add(object);

        }
    }

    /**
     *
     * @param cache_memory
     * @param object_name
     * @param pw
     * @return
     * @throws InterruptedException
     */
    @Override
    //functia va printa elementele cerute din memoria cache si va decremnta accesarile Premium si/sau Basic
    //actualizeaza mereu timestam-ul elementului care a fost afisat si incetineste din nou procesul
    public int get(ArrayList<Premium> cache_memory, String object_name, PrintWriter pw) {
        if (!cache_memory.isEmpty()) {
            for (int i = 0; i < cache_memory.size(); i++) {
                if (object_name.equals(cache_memory.get(i).object_name)) {
                    if (cache_memory.get(i).hits_Premium > 0) {
                        pw.print("0 Premium\n");
                        cache_memory.get(i).hits_Premium--;
                        cache_memory.get(i).setTimestamp(System.currentTimeMillis());
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LRUCache.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        if (cache_memory.get(i).hits_Basic > 0) {
                            pw.print("0 Basic\n");
                            cache_memory.get(i).hits_Basic--;
                            cache_memory.get(i).setTimestamp(System.currentTimeMillis());
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(LRUCache.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            pw.print("0 Free\n");
                            cache_memory.get(i).setTimestamp(System.currentTimeMillis());
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(LRUCache.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    //returneaza 1 daca elementul s-a aflat in cache
                    return 1;
                }

            }
            //elementul s-a aflat in cache
            return -1;
        }
        //memoria cache este goala
        return 0;
    }

    public ArrayList<Premium> getCache_memory() {
        return cache_memory;
    }
    //sterge obiectul din memoria cache cu numele cautat
    public void remove_object(ArrayList<Premium> cache_memory, String name) {

        for (int i = 0; i < cache_memory.size(); i++) {
            if (name.equals(cache_memory.get(i).object_name)) {
                cache_memory.remove(i);
                break;
            }
        }
    }
}
