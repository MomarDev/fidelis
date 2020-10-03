/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leyu
 */
public class ApplicationLogger {

    public ApplicationLogger() {
    }

    private boolean IsDate(String file, String jour) {
        String line = "";
        boolean teste = false;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            try {
                line = br.readLine();
                if (!line.isEmpty() && line != null && line.contains(jour)) {
                    teste = true;
                }
            } catch (IOException ex) {
                Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return teste;
    }

    public void log(String mot) {
        ecritSurJour(Utilis.jourDeSemaine(), mot);
    }

    private void ecritSurJour(int jour, String mot) {
        switch (jour) {
            case 1:
                ecrirSurFile("1.bst", mot, IsDate("1.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 2:
                ecrirSurFile("2.bst", mot, IsDate("2.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 3:
                ecrirSurFile("3.bst", mot, IsDate("3.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 4:
                ecrirSurFile("4.bst", mot, IsDate("4.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 5:
                ecrirSurFile("5.bst", mot, IsDate("5.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 6:
                ecrirSurFile("6.bst", mot, IsDate("6.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            case 7:
                ecrirSurFile("7.bst", mot, IsDate("7.bst", Utilis.NOW_LOCAL_DATE().toString()));
                break;
            default:
                System.out.println("Log: the log is not ok!");
                break;
        }
    }

    private void ecrirSurFile(String file, String mot, boolean append) {
        try {
            if (append) {
                Files.write(Paths.get(file), Utilis.heureActuel().toString().getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(file), mot.getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(Paths.get(file), Utilis.NOW_LOCAL_DATE().toString().getBytes(), StandardOpenOption.WRITE);
                Files.write(Paths.get(file), Utilis.heureActuel().toString().getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(file), mot.getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException ex) {
            Logger.getLogger(ApplicationLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
