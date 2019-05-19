/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author vld
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    //functie care imi returneaza un obiect din memoria principala de tip Premium cu numele dat
    public static Premium check_object_memory(ArrayList<Premium> memory, String name) {

        for (int i = 0; i < memory.size(); i++) {
            if (name.equals(memory.get(i).object_name)) {
                return memory.get(i);
            }
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

        Scanner sc = new Scanner(new BufferedReader(new FileReader(args[0])));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));

        String cache_Name;
        int cache_Size;
        int count_operations;

        //citire tip de cache, marimea si numarul operatiilor
        cache_Name = sc.next();
        cache_Size = sc.nextInt();
        count_operations = sc.nextInt();
        String operation_name;

        //initializarea tuturor memoriilor
        ArrayList<Premium> memory = new ArrayList();
        FIFOCache fifo_cache = new FIFOCache(cache_Size);
        LRUCache lru_cache = new LRUCache(cache_Size);
        LFUCache lfu_cache = new LFUCache(cache_Size);

        for (int i = 0; i < count_operations; i++) {
            operation_name = sc.next();
            //numele operatiei pe care trebuie sa o efectuez este ADD
            if (operation_name.equals("ADD")) {
                String name = sc.next();
                int basic = sc.nextInt();
                int premium;
                if (sc.hasNextInt()) {
                    premium = sc.nextInt();
                } else {
                    premium = 0;
                }
                //creez un obiect nou de tip Premium cu datele citite din fisier
                Premium new_object_Premium = new Premium(name, basic, premium, System.currentTimeMillis());
                int ok = 0;
                //verific daca exista deja in memoria principala obiectul nou creat pentru a-l actualiza si sterge din cache
                for (int j = 0; j < memory.size(); j++) {
                    if (new_object_Premium.object_name.equals(memory.get(j).object_name)) {
                        memory.set(j, new_object_Premium);
                        //sterge obiectul de tip Premium dintr-una dintre memoriile cache dupa caz
                        if (cache_Name.equals("FIFO")) {
                            fifo_cache.remove_object(fifo_cache.getCache_memory(), name);
                        } else if (cache_Name.equals("LRU")) {
                            lru_cache.remove_object(lru_cache.getCache_memory(), name);
                        } else {
                            lfu_cache.remove_object(lfu_cache.getCache_memory(), name);
                        }
                        break;
                    } else {
                        ok++;
                    }
                }
                 //daca ok este de dimensiunea memoriei inseamna ca obiectul nu se afla in memorie si il adaug
                if (ok == memory.size()) {
                    memory.add(new_object_Premium);
                }
            //operatia care trebuie efectuata este GET
            } else {
                String name = sc.next();
                Premium object_Premium = check_object_memory(memory, name);

                //daca obiectul cautat in memorie nu exista
                if (object_Premium == null) {
                    pw.print("2\n");
                } else {
                    //obiectul cautat exista in memorie

                    int cache_case = 1;
                    //daca cache_case = 0 inseamna ca memoria cache este goala
                    //daca cache_case = -1 inseamna ca elementul nu se gaseste in cache si urmeaza sa fie adaugat in memorie dupa caz
                    if (cache_Name.equals("FIFO")) {
                        cache_case = fifo_cache.get(fifo_cache.getCache_memory(), name, pw);
                    } else if (cache_Name.equals("LRU")) {
                        cache_case = lru_cache.get(lru_cache.getCache_memory(), name, pw);
                    } else {
                        cache_case = lfu_cache.get(lfu_cache.getCache_memory(), name, pw);
                    }
                    //noul element va fi adaugat in cache doar daca chace_case este 0 sau 1
                    if (cache_case != 1) {
                        //se afiseaza elementul cand este in memorie
                        if (object_Premium.hits_Premium > 0) {
                            pw.print("1 Premium\n");
                            object_Premium.hits_Premium--;
                        } else if (object_Premium.hits_Basic > 0) {
                            pw.print("1 Basic\n");
                            object_Premium.hits_Basic--;
                        } else {
                            pw.print("1 Free\n");
                        }

                        //se adauga elementul in memoria cache dupa caz
                        if (cache_Name.equals("FIFO")) {
                            fifo_cache.add(object_Premium, fifo_cache.getCache_memory());
                        } else if (cache_Name.equals("LRU")) {
                            lru_cache.add(object_Premium, lru_cache.getCache_memory());
                        } else {
                            lfu_cache.add(object_Premium, lfu_cache.getCache_memory());
                        }
                    }

                }

            }
        }
        sc.close();
        pw.close();
    }

}
