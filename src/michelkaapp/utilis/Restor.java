/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package michelkaapp.utilis;

import java.io.File;
import java.nio.file.Path;
import javafx.concurrent.Task;
import michelkaapp.objets.Trace;

/**
 *
 * @author leyu
 */
public class Restor {

    private Task task;
    private Path pathSource;
    private Path pathDest;
    private File dir;
    private boolean isOk = false;

    public Restor(File fileDir, Path src, Path dest) {
        pathDest = dest;
        pathSource = src;
        dir = fileDir;
        copyTask();
    }

    private void copyTask() {
        if (dir == null || pathSource == null || pathDest == null || !dir.exists()) {
            System.err.println("Erreur au niveau des fichiers.");
            return;
        }
        task = new Task() {
            @Override
            protected Object call() throws Exception {
//                Thread.sleep(15000);
                if (dir.isDirectory()) {
//                    Faire le zip 
                    ZipperJob appZip = new ZipperJob(pathDest.toString(), pathSource.toString());
                    appZip.generateFileList(new File(pathSource.toString()));
                    appZip.zipIt(pathDest.toString() + ".zip");
//lancer la sauvegarde dans le serveur ftp.
                    FTPUploader ftpUploader = new FTPUploader(Utilis.trouverParametre("HOST_SERVER_FTP").getValeur(), Utilis.trouverParametre("USER_SERVER_FTP").getValeur(),
                            Utilis.trouverParametre("PWD_SERVER_FTP").getValeur());
                    ftpUploader.deleteFile("/" + Utilis.RESTOR_DIR + "/" + Utilis.jourDeSemaine() + ".zip");
                    isOk = ftpUploader.uploadFile(pathDest.toString() + ".zip", Integer.toString(Utilis.jourDeSemaine()) + ".zip", Utilis.RESTOR_DIR);
                    ftpUploader.disconnect();
                    System.out.println("Done");

                }
                return null;
            }
        };
        new Thread(task).run();
        task.setOnFailed(failed -> {
            Utilis.notification(false, "Le systeme de sauvegarde du " + Utilis.NOW_LOCAL_DATE().toString() + " echec.");
        });
        task.setOnCancelled(canced -> {
            Utilis.notification(false, "Le systeme de sauvegarde du " + Utilis.NOW_LOCAL_DATE().toString() + " echec.");
        });
        task.setOnSucceeded(succed -> {
            Utilis.notification(true, "Le systeme de sauvegarde du " + Utilis.NOW_LOCAL_DATE().toString() + " reussie.");
            michelkaapp.MichelKaApp.IS_RTORED = true;
            if (isOk) {
                Utilis.driver.insertTrace(new Trace(0, "sauvegarde", "data", isOk ? "effectué" : "echec", null, Utilis.getUser()));
            }
        });

    }
}
