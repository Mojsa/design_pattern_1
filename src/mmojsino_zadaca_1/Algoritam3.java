/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Mario
 */
public class Algoritam3 implements Algoritmi {

    //slucajno
    @Override
    public List<Sastav> generirajPlan(List<Ronioc> listaOdabranih) {
        List<Sastav> list = new ArrayList<>();
        Collections.shuffle(listaOdabranih);
        ListIterator<Ronioc> listIt = listaOdabranih.listIterator();

        while (listIt.hasNext()) {
            if ((listaOdabranih.size() % 2 == 0) && (listaOdabranih.size() > 1)) {
                //dodaj dva
                Sastav sas = new Sastav();
                Ronioc r = listIt.next();
                sas.dodajRoniocaSastav(r);
                r = listIt.next();
                sas.dodajRoniocaSastav(r);
                list.add(sas);

            } else if ((listaOdabranih.size() % 2 == 1) && (listaOdabranih.size() > 1)) {
                Sastav sas = new Sastav();
                Ronioc r = listIt.next();
                sas.dodajRoniocaSastav(r);
                r = listIt.next();
                sas.dodajRoniocaSastav(r);
                r = listIt.next();
                sas.dodajRoniocaSastav(r);
                list.add(sas);
            }
        }
        for (Sastav s : list) {
            s.postaviDubinu(vratiDubinu(s));
        }
        return list;
    }

    private int vratiDubinu(Sastav sastav) {
        int index = 10;
        String najmanjaRazina = "";
        ListIterator<Ronioc> it = sastav.getLista().listIterator();
        boolean imaVise = false;
        while (it.hasNext()) {
            Ronioc r = it.next();
            if (Integer.parseInt(r.getRangNorm().substring(1, 2)) <= index && Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                najmanjaRazina = r.getRangNorm();
                imaVise = true;
            } else if (Integer.parseInt(r.getRangNorm().substring(1, 2)) > index && Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                imaVise = true;
            }
        }
        return pronadjiDubinu(Integer.parseInt(najmanjaRazina.substring(1, 2)), imaVise);
    }

    private int pronadjiDubinu(int broj, boolean visaRazina) {
        int dodatak = 0;
        if (visaRazina) {
            dodatak = 10;
        }
        switch (broj) {
            case 0:
                return 0;
            case 1:
                return 10 + dodatak;
            case 2:
                return 30 + dodatak;
            default:
                return 40;
        }
    }
}
