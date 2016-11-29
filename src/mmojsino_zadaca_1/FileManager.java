/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmojsino_zadaca_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario
 */
public class FileManager {

    File writeFile = null;

    public void openFile(String path) throws IOException {
        RonilackiKlubSingleton instance = RonilackiKlubSingleton.getInstance();
        Path filePath = Paths.get(path);
        Scanner scanner = new Scanner(filePath);
        String[] parts;
        if (path.contains("ronioci")) {
            while (scanner.hasNext()) {
                if (scanner.hasNext()) {
                    parts = scanner.next().split(";");
                    if (parts.length == 4) {
                        instance.dodajRonioca(new Ronioc.RoniocBuilder(parts[0], Integer.parseInt(parts[3]))
                                .setAgencija(parts[1])
                                .setAgencijaNorm(RankingNorm.getAgencije()[0])
                                .setRang(parts[2])
                                .setRangNorm()
                                .build());
                    } else {
                        System.out.println("Neispravan zapis, preskacem liniju.");
                    }

                } else {
                    scanner.next();
                }
            }
        } else if (path.contains("uroni")) {
            while (scanner.hasNext()) {
                if (scanner.hasNext()) {
                    parts = scanner.next().split(";");
                    if (parts.length == 4) {
                        instance.dodajUron(new Uron.UronBuilder(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3])).build());
                    } else {
                        System.out.println("Neispravan zapis, preskacem liniju.");
                    }

                } else {
                    scanner.next();
                }
            }
        }

        scanner.close();
    }

    public void writeToFile(List<String> data) {
        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new FileWriter(writeFile, true);
            br = new BufferedWriter(fr);
            for (String s : data) {
                br.append(s);
                br.append(System.lineSeparator());
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void openWriteFile(String path) {
        writeFile = new File(path);
        try {
            if (writeFile.exists()) {
                writeFile.delete();
                writeFile.createNewFile();
            } else {
                writeFile.createNewFile();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
