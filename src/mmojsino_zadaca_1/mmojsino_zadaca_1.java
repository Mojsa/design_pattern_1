/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario
 */
public class mmojsino_zadaca_1 {

    private static RonilackiKlubSingleton instance = RonilackiKlubSingleton.getInstance();
    private static FileManager fr = new FileManager();

    //private static int randomSeed = 0;
    //private static Random generator;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            AbstractFactory algoritamFactory = FactoryProducer.getFactory("Algoritam");
            Algoritmi algoritamGeneriraj;
            if (args.length == 5) {
                //randomSeed = Integer.parseInt(args[0]);
                instance.setGenerator(Integer.parseInt(args[0]));
               // generator = new Random(randomSeed);
                algoritamGeneriraj = algoritamFactory.getAlgoritam(args[3]);
            } else {
                System.out.println("Provjerite upisane argumente!");
                return;
            }

            String workingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
            fr.openFile(workingDirectory + "\\" + args[1]);
            fr.openWriteFile(workingDirectory + "\\" + args[4]);
            fr.openFile(workingDirectory + "\\" + args[2]);

            List<Uron> listaUrona2 = new ArrayList<>();
            for (Uron u : instance.getListUroni()) {
                if(u.getBrojRonioca() > 1){
                    listaUrona2.add(new Uron.UronBuilder(u.getDatum(), u.getVrijeme(), u.getMaxDubina(), u.getBrojRonioca())
                        .postaviGrupu(algoritamGeneriraj.generirajPlan(getSkupRonioca(u.getBrojRonioca()), u.getMaxDubina()))
                        .build());
                }
            }
            instance.setListUroni(listaUrona2);

            podaciUron();
            podaciRonioci();
        } catch (IOException ex) {
            Logger.getLogger(mmojsino_zadaca_1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Ronioc> getSkupRonioca(int brojRonioca) {
        List<Ronioc> skupRonioca = new ArrayList<>();
        if(instance.getListRonioca().size() < brojRonioca){
            System.out.println("Nema dovoljno ronioca!");
            return null;
        }
        Ronioc random;
        while (brojRonioca > 0) {
            random = instance.getListRonioca().get(instance.getGenerator().nextInt(instance.getListRonioca().size()));
            while (skupRonioca.contains(random) && random.getRangNorm() != "-") {
                random = instance.getListRonioca().get(instance.getGenerator().nextInt(instance.getListRonioca().size()));
            }
            skupRonioca.add(random);
            brojRonioca -= 1;
        }
        return skupRonioca;
    }

    public static void podaciUron() {
        List<String> podaciUron = new ArrayList<>();
        for (Uron u : instance.getListUroni()) {
            podaciUron.add("Uron -------------");
            podaciUron.add("DATUM: " + u.getDatum());
            podaciUron.add("Vrijeme: " + u.getVrijeme());
            podaciUron.add("Broj ronioca: " + u.getBrojRonioca());
            List<Sastav> s = u.getUronGrupa();
            for (Sastav a : s) {
                podaciUron.add("Grupa -------------");
                podaciUron.add("Max dubina: " + a.getDubina());
                List<Ronioc> listaRon = a.getLista();
                for (Ronioc r : listaRon) {
                    podaciUron.add("Ime: " + r.getImeRonioca());
                }
            }
            podaciUron.add("-------------------");
        }
        fr.writeToFile(podaciUron);
    }

    public static void podaciRonioci() {
        List<String> podaciRonioci = new ArrayList<>();
        for (Ronioc r : instance.getListRonioca()) {
            boolean postoji = false;
            podaciRonioci.add("Osobni podaci: -------------");
            podaciRonioci.add("Ime: " + r.getImeRonioca());
            podaciRonioci.add("Godina rodjenja: " + r.getGodinaRodjenja());
            podaciRonioci.add("Agencija: " + r.getAgencija());
            podaciRonioci.add("Rang: " + r.getRang());
            for (Uron u : instance.getListUroni()) {
                List<Sastav> listGrupa = u.getUronGrupa();
                for (Sastav s : listGrupa) {
                    postoji = false;
                    List<Ronioc> listaRonioca = s.getLista();
                    Ronioc ronioR = null;
                    for (Ronioc ro : listaRonioca) {
                        if (r.getImeRonioca().equals(ro.getImeRonioca()) && r.getAgencija().equals(ro.getAgencija())
                                && r.getRang().equals(ro.getRang())) {
                            ronioR = r;
                            podaciRonioci.add("URON: -------------");
                            podaciRonioci.add("Datum, vrijeme: " + u.getDatum() + "/" + u.getVrijeme());
                            podaciRonioci.add("Dubina: " + s.getDubina());
                            postoji = true;
                        }
                    }
                    if (postoji) {
                        for (Ronioc a : listaRonioca) {
                            if (!a.equals(ronioR)) {
                                podaciRonioci.add("Partner/i: " + a.getImeRonioca());
                            }
                        }
                    }
                }
            }
        }
        fr.writeToFile(podaciRonioci);
    }

}
