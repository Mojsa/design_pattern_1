/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Mario
 */
public class Algoritam2 implements Algoritmi {

    private static RonilackiKlubSingleton instance = RonilackiKlubSingleton.getInstance();
    private List<Ronioc> listaOdabranih = null;

    //sto vise ronioca roni s partnerima iste razine
    @Override
    public List<Sastav> generirajPlan(List<Ronioc> lista) {
        listaOdabranih = lista;
        List<List<Ronioc>> filtriraniRonioci = new ArrayList<>();
        List<List<Ronioc>> filtriraniRoniociProfi = new ArrayList<>();
        List<Sastav> listSastav = new ArrayList<>();

        for (String a : RankingNorm.getRazineRekreacijske()) {
            filtriraniRonioci.add(listaOdabranih.stream()
                    .filter((x) -> a.equals(x.getRangNorm()))
                    .collect(Collectors.toList()));
        }
        for (String a : RankingNorm.getRazineProfi()) {
            filtriraniRoniociProfi.add(listaOdabranih.stream()
                    .filter((x) -> a.equals(x.getRangNorm()))
                    .collect(Collectors.toList()));
        }//isto kao i za filtrirane ostale - mozda bi prije valjalo provjeriti ima li uopce u listi koji ronioc? (za oba stanja)
        //provjeriti za datum, to ne znam kako cemo
        //dodati dubinu za ostale
        if (!filtriraniRonioci.isEmpty()) {
            listSastav = vratiSastavIsteRazine(filtriraniRonioci);
        }
        if (!filtriraniRoniociProfi.isEmpty()) {
            listSastav.addAll(vratiSastavIsteRazine(filtriraniRoniociProfi));
        }

        System.out.println("Ostali su: ");

        for (Ronioc r : listaOdabranih) {
            System.out.println("Ime: " + r.getImeRonioca());
            System.out.println("Rang : " + r.getRangNorm());
        }
        listSastav.addAll(vratiSastavOstali());

        return listSastav;
    }

    private int pronadjiDubinu(int broj, boolean profiMix) {
        int dodatak = 0;
        if (profiMix) {
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

    private List<Sastav> vratiSastavIsteRazine(List<List<Ronioc>> filtrirani) {
        List<Sastav> listSastav = new ArrayList<>();
        for (List<Ronioc> lista : filtrirani) {
            ListIterator<Ronioc> it = lista.listIterator();
            //provjera je li ronioc odredjene razine sam (tj. nema ronioca iste razine u skupu)
            if (lista.size() == 1) {
                continue;
            }
            for (Ronioc r : lista) {
                //brisanje iz odabranih kako bi ostali samo oni koji nemaju partnera po pitanju razine
                listaOdabranih.remove(r);
            }
            it.forEachRemaining(new Consumer<Ronioc>() {
                @Override
                public void accept(Ronioc n) {
                    while (it.hasNext()) {
                        if ((lista.size() % 2 == 0) && (lista.size() > 1)) {
                            //dodaj dva
                            final Sastav s = new Sastav();
                            s.postaviDubinu(pronadjiDubinu(Integer.parseInt(n.getRangNorm().substring(1, 2)), false));
                            Ronioc r = it.next();
                            s.dodajRoniocaSastav(r);
                            r = it.next();
                            s.dodajRoniocaSastav(r);
                            listSastav.add(s);
                        } else if ((lista.size() % 2 == 1) && (lista.size() > 1)) {
                            //dodaj 3
                            final Sastav s = new Sastav();
                            s.postaviDubinu(pronadjiDubinu(Integer.parseInt(n.getRangNorm().substring(1, 2)), false));
                            Ronioc r = it.next();
                            s.dodajRoniocaSastav(r);
                            r = it.next();
                            s.dodajRoniocaSastav(r);
                            r = it.next();
                            s.dodajRoniocaSastav(r);
                            listSastav.add(s);
                        }
                    }
                }
            });
        }
        return listSastav;
    }

    private List<Sastav> vratiSastavOstali() {
        ListIterator<Ronioc> listIt = listaOdabranih.listIterator();
        List<Sastav> list = new ArrayList<>();
        while (listIt.hasNext()) {
            if ((listaOdabranih.size() % 2 == 0) && (listaOdabranih.size() > 1)) {
                //dodaj dva
                //provjeriti ima li rekreacijskog u grupi, ako ima onda se prema njemu postaviti i dodati +10
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
            s.postaviDubinu(vratiDubinu(list));
        }
        return list;
    }

    private int vratiDubinu(List<Sastav> sastav) {
        int indexRekr = 0;
        boolean rekreacijski = false;
        for (Sastav s : sastav) {
            List<Ronioc> lista = s.getLista();
            for (Ronioc r : lista) {
                if (Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                    //sadrzi rekreacijski, uzeti index;
                    if (lista.indexOf(r) > 0) {
                        if (indexRekr > -1 && (Integer.parseInt(lista.get(indexRekr).getRangNorm().substring(1, 2)))
                                < (Integer.parseInt(r.getRangNorm().substring(1, 2)))) {
                            rekreacijski = true;
                        } else {
                            indexRekr = lista.indexOf(r);
                            rekreacijski = true;

                        }
                    } else {
                        indexRekr = lista.indexOf(r);
                    }
                }
            }
        }
        if (!rekreacijski) {
            indexRekr = 3;
        }
        return pronadjiDubinu(indexRekr+1, true);
    }
}
