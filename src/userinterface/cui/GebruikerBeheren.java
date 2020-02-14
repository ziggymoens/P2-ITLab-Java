package userinterface.cui;

import domein.DomeinController;

import java.util.Scanner;

public class GebruikerBeheren {
    public static void main(String[] args) {
        DomeinController dc = new DomeinController();
        Scanner scan = new Scanner(System.in);

        System.out.println("Wil je een gebruiker aanpassen?");
        String aanpassen = scan.next();
        if(aanpassen.equalsIgnoreCase("ja")){
        }
    }
}
