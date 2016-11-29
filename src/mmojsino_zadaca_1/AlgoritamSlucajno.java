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
public class AlgoritamSlucajno implements Algoritmi {

    //slucajno
    @Override
    public List<Sastav> generirajPlan(List<Ronioc> listaOdabranih, int... b) {
        List<Sastav> list = new ArrayList<>();
        Collections.shuffle(listaOdabranih);
        List<Ronioc> kopija = new ArrayList<>();
        kopija.addAll(listaOdabranih);
        ListIterator<Ronioc> listIt = kopija.listIterator();
        if (listaOdabranih.size() < 2) {
            return null;
        }
        Sastav sas = new Sastav();
        while (true) {
            if (kopija.size() == 0) {
                break;
            }
            if (kopija.size() % 2 == 0) {
                sas = new Sastav();
                sas.dodajRoniocaSastav(listIt.next());
                listIt.remove();
                sas.dodajRoniocaSastav(listIt.next());
                listIt.remove();
                list.add(sas);
            }
            if (kopija.size() % 2 == 1) {
                //dodati 3 komada
                sas = new Sastav();
                sas.dodajRoniocaSastav(listIt.next());
                listIt.remove();
                sas.dodajRoniocaSastav(listIt.next());
                listIt.remove();
                sas.dodajRoniocaSastav(listIt.next());
                listIt.remove();
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
        boolean imaProfi = false;
        while (it.hasNext()) {
            Ronioc r = it.next();
            if (Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                if (Integer.parseInt(r.getRangNorm().substring(1, 2)) < index) {
                    index = Integer.parseInt(r.getRangNorm().substring(1, 2));
                    najmanjaRazina = r.getRangNorm();
                    imaVise = true;

                } else {
                    imaVise = true;
                }
            } else if (Arrays.asList(RankingNorm.getRazineProfi()).contains(r.getRangNorm())) {
                imaProfi = true;
            }

        }
        if (najmanjaRazina.equals("") && imaProfi) {
            return 40;
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
