/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mario
 */
public class RonilackiKlubSingleton {

    //uroni, ronioci, jos nesto?
    private List<Ronioc> listRonioca = new ArrayList<>();
    private List<Uron> listUroni = new ArrayList<>();
    private Random generator;


    private RonilackiKlubSingleton() {

    }
    
    public void setGenerator(int seed){
        generator = new Random(seed);
    }
    
    public Random getGenerator(){
        return generator;
    }

    public List<Ronioc> getListRonioca() {
        return listRonioca;
    }

    public List<Uron> getListUroni() {
        return listUroni;
    }

    public void dodajRonioca(Ronioc r) {
        listRonioca.add(r);
    }

    public void dodajUron(Uron u) {
        listUroni.add(u);
    }

    public void setListUroni(List<Uron> listUroni) {
        this.listUroni = listUroni;
    }

    private static class SingletonHelper {

        private static final RonilackiKlubSingleton instance = new RonilackiKlubSingleton();

    }

    public static RonilackiKlubSingleton getInstance() {
        return SingletonHelper.instance;
    }

}
