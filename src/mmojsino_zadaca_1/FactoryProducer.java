/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

/**
 *
 * @author Mario
 */
public class FactoryProducer {
    public static AbstractFactory getFactory (String type){
        if(type.equalsIgnoreCase("Algoritam")){
            return new AlgoritamFactory();
        }
        return null;
    }
}
