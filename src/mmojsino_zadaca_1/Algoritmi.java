/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.util.List;

/**
 *
 * @author Mario
 */
public interface Algoritmi {
    List<Sastav> generirajPlan(List<Ronioc> listaOdabranih, int... broj);
    
}
