package main;

import domein.Media;
import domein.Statistiek;
import domein.gebruiker.Gebruiker;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Test {
    private static File file;


    public static void main(String[] args) throws IOException {
        String[] datatabellen = {"aankondiging", "academiejaar", "feedback", "gebruiker", "gebruikersprofiel", "gebruikersstatus", "herinnering", "inschrijving", "lokaal", "media", "sessie", "sessiestatus"};

        Statistiek s = new Statistiek();

        s.overzichtLokalen(10);

        /*
        s.geefTopSessieTabel("inschrijving", 10);
        System.out.println();
        s.geefTopSessieTabel("aanwezigheid", 10);
        System.out.println();
        s.geefTopSessieTabel("feedback", 10);
        System.out.println();
        System.out.println();
        s.geefTopGebruikerTabel("media", 10);
        System.out.println();
        System.out.println();
        s.geefTopGebruikerTabel("inschrijving", 10);
        System.out.println();
        s.geefTopGebruikerTabel("aanwezigheid", 10);
        System.out.println();
        s.geefTopGebruikerTabel("feedback", 10);
        System.out.println();
        s.geefTopGebruikerTabel("sessie", 10);
        System.out.println();

         */


        /*
        for (String tabel: datatabellen){
            System.out.println(tabel);
            System.out.println();
            s.geefAllesVan(tabel);
            System.out.println();
            System.out.println();
        }

         */

        /*
        File file = new File("/storage/profielfotos/profielfoto.png");
        //FileExplorer();
        System.out.println(file.getCanonicalFile());

        try {
            BufferedImage image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gebruiker gebruiker = new Gebruiker("naam", "gebruikersnaam",  "hoofdverantwoordelijke",  "actief","profielfoto", 1, "wachtwoord");
        Media media = new Media(gebruiker, "loc", "FOTO");
        */
    }

    private static void FileExplorer() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Media Toevoegen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.gif", "*.jpg"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                java.nio.file.Files.copy(
                        file.toPath(),
                        new File(file.getName()).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

