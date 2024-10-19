package DnDGen.TSWP1;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
//import java.util.Scanner;

public class RandomFileSelector {
    static Random random = new SecureRandom();
    static Logger logger = Logger.getLogger(RandomFileSelector.class.getName());


    public static void main(String[] args) {
        /*
        // Scanner für Benutzereingaben initialisieren
        Scanner scanner = new Scanner(System.in);

        // Ordnerpfad vom Benutzer abfragen
        // System.out.print("Bitte geben Sie den Pfad zum Ordner an: ");
        // String folderPath = scanner.nextLine();
        String folderPath = "F:\\DnD\\DnD Material\\DnD Token  Collection";
        File folder = new File(folderPath);

        // Anzahl der zufälligen Dateien abfragen
        System.out.print("Wie viele zufällige Dateien möchten Sie auswählen? ");
        int fileCount = scanner.nextInt();

        randFile(folder, fileCount);
        scanner.close();

         */
        getRandomPNG("Tiefling");
    }

    // Rekursiv alle Dateien eines Ordners (inkl. Unterordnern) auflisten
    public static List<File> listAllFiles(File folder) {
        List<File> files = new ArrayList<>();
        File[] fileList = folder.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                if (file.isDirectory()) {
                    // Unterordner rekursiv durchsuchen
                    files.addAll(listAllFiles(file));
                } else {
                    files.add(file); // Datei hinzufügen
                }
            }
        }
        return files;
    }

    // Zufällige Anzahl von Dateien aus der Liste auswählen
    public static List<File> getRandomFiles(List<File> files, int count) {
        List<File> randomFiles = new ArrayList<>();

        // Anzahl der Dateien begrenzen, falls sie kleiner als die angegebene Anzahl ist
        count = Math.min(count, files.size());

        // Dateien zufällig auswählen
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(files.size());
            randomFiles.add(files.get(randomIndex));
            files.remove(randomIndex); // Datei entfernen, um Duplikate zu vermeiden
        }
        return randomFiles;
    }

    public static void randFile(File folder, int fileCount) {

        // Prüfen, ob der Ordner existiert
        if (folder.exists() && folder.isDirectory()) {
            // Alle Dateien in diesem Ordner (und Unterordnern) sammeln
            List<File> files = listAllFiles(folder);

            // Wenn Dateien gefunden wurden, zufällige Dateien auswählen
            if (!files.isEmpty()) {
                List<File> randomFiles = getRandomFiles(files, fileCount);
                logger.info("Zufällig ausgewählte Dateien:");
                for (File file : randomFiles) {
                    logger.info(file.getAbsolutePath()); // Ordner Pfad des Bildes

                    // Prüfen, ob die Desktop-API unterstützt wird
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            if (file.exists()) {
                                desktop.open(file); // Datei im Standardprogramm öffnen
                            } else {
                                logger.info("Die Datei existiert nicht.");
                            }
                        } catch ( IOException e) {
                            e.printStackTrace();
                        }
                }
            } else {
                logger.info("Keine Dateien gefunden.");
            }
        }
    }
    //Hier bekommt man das zufällige Bild des NPCs ausgewählt
    public static void getRandomPNG(/*NPCClass generatedNPC*/ String race){
        String filePath = "F:\\DnD\\DnD Material\\DnD Token  Collection";
        File folder = new File(filePath);
        //String race = generatedNPC.getRace();
        File[] orderList = folder.listFiles();

        assert orderList != null;
        for(File file : orderList ){
            if(file.getName().contains(race)){
                randFile(file,1);
                break;
            }
        }
    }
}
