/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Mario
 */
public class AlgoritamMaxUron implements Algoritmi {

//maks uron (maksimalno dopustena dubina u odnosu na planiranu)
    @Override
    public List<Sastav> generirajPlan(List<Ronioc> listaOdabranih, int... br) {
        List<Sastav> listSastav = new ArrayList<>();
        int maxBroj = 0;
        if (br.length > 0) {
            maxBroj = br[0];
        }
        if (listaOdabranih.size() < 2) {
            return null;
        }
        List<Ronioc> nesretnici = new ArrayList<>();//ne mogu sami
        List<Ronioc> sretnici = new ArrayList<>();//mogu sami roniti

        for (Ronioc r : listaOdabranih) {
            if (pronadjiDubinu(Integer.parseInt(r.getRangNorm().substring(1, 2)), false)
                    < maxBroj
                    && Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                nesretnici.add(r);
            } else if (pronadjiDubinu(Integer.parseInt(r.getRangNorm().substring(1, 2)), false)
                    >= maxBroj
                    && Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
                sretnici.add(r);
            } else if (Arrays.asList(RankingNorm.getRazineProfi()).contains(r.getRangNorm())) {
                sretnici.add(r);
            }
        }
        ListIterator<Ronioc> listIt = listaOdabranih.listIterator();
        ListIterator<Ronioc> itSretnici = sretnici.listIterator();
        ListIterator<Ronioc> itNesretnici = nesretnici.listIterator();
        Sastav sastav = new Sastav();
        while (true) {
            if (!itNesretnici.hasNext() && !itSretnici.hasNext()) {
                listSastav.add(sastav);
                break;
            }
            if (sastav.mozeJosRonioca() && itNesretnici.hasNext()) {
                sastav.dodajRoniocaSastav(itNesretnici.next());
            }
            if (sastav.mozeJosRonioca() && itSretnici.hasNext()) {
                sastav.dodajRoniocaSastav(itSretnici.next());
            }
            if (!sastav.mozeJosRonioca()) {
                listSastav.add(sastav);
                sastav = new Sastav();
            }
        }
        for (Sastav s : listSastav) {
            s.postaviDubinu(vratiDubinu(listSastav));
            List<Ronioc> list = s.getLista();
            list.sort((r1, r2) -> r1.getRangNorm().compareTo(r2.getRangNorm()));
            if (!list.isEmpty()) {
                pronadjiApsolutnuRazinu(list.get(0).getRangNorm(), list.get(list.size() - 1).getRangNorm());
            }
        }
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

    private int vratiDubinu(List<Sastav> sastav) {
        int indexRekr = 0;
        boolean rekreacijski = false;
        for (Sastav s : sastav) {
            List<Ronioc> lista = s.getLista();
            for (Ronioc r : lista) {
                if (Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r.getRangNorm())) {
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
        return pronadjiDubinu(indexRekr + 1, true);
    }

    private int pronadjiApsolutnuRazinu(String r1, String r2) {
        String[] razine = {"R0", "R1", "R2", "R3", "R4", "R5", "I0", "I1", "I2", "I3", "I4", "I5", "I6", "I7"};
        int apsolutnaRazina = 0;
        Arrays.asList(razine).indexOf(r1);
        System.out.println("Prvi: " + r1);
        System.out.println("Drugi: " + r2);
        if (Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r1)
                && Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r2)) {
            //both recreational
            apsolutnaRazina = Math.abs(Arrays.asList(razine).indexOf(r1) - Arrays.asList(razine).indexOf(r2)) + 1;

        } else if (Arrays.asList(RankingNorm.getRazineProfi()).contains(r1)
                && Arrays.asList(RankingNorm.getRazineProfi()).contains(r2)) {
            //both professional
            apsolutnaRazina = Math.abs(Arrays.asList(razine).indexOf(r1) - Arrays.asList(razine).indexOf(r2)) + 1;
        } else if ((Arrays.asList(RankingNorm.getRazineRekreacijske()).contains(r1)
                && Arrays.asList(RankingNorm.getRazineProfi()).contains(r2))) {
            //1.rekr 2.profess
            apsolutnaRazina = Math.abs(Arrays.asList(razine).indexOf(r1) - Arrays.asList(razine).indexOf(r2)) + 1;
        } else {
            //1.prof 2.rekr
            apsolutnaRazina = Math.abs(Arrays.asList(razine).indexOf(r1) - Arrays.asList(razine).indexOf(r2)) + 1;
        }
        return apsolutnaRazina;
    }

}
