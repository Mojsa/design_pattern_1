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
public class AlgoritamFactory extends AbstractFactory {

    @Override
    public Algoritmi getAlgoritam(String type) {
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("AlgoritamMaksUron")){
            return new AlgoritamMaxUron();
        }else if(type.equalsIgnoreCase("AlgoritamIstaRazina")){
            return new AlgoritamIstaRazina();
        }else if(type.equalsIgnoreCase("AlgoritamSlucajno")){
            return new AlgoritamSlucajno();
        }else{
            return new AlgoritamMaxUron();
        }
    }
    
}
