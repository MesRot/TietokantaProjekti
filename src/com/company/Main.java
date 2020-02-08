package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner lukija = new Scanner(System.in);
        Toiminnot To = new Toiminnot();


        while(true){
            int valinta = 0;
            System.out.println("Valitse toiminto (1-9):");
            try{
                valinta = Integer.parseInt(lukija.nextLine());
            }
            catch (Exception e){
                System.out.println("Ei hyväksytty syöte");
                continue;
            }

            switch(valinta){
                case 1:
                    To.teeTietokanta();
                    break;

                case 2:
                    System.out.println("Anna paikan nimi:");
                    String nimi = lukija.nextLine();
                    To.teePaikka(nimi);
                    break;

                case 3:
                    System.out.println("Anna asiakkaan nimi:");
                    String animi = lukija.nextLine();
                    To.teeAsiakas(animi);
                    break;

                case 4:
                    System.out.println("Anna seurantakoodi:");
                    String koodi = lukija.nextLine();
                    System.out.println("Anna asiakkaan nimi:");
                    String jnimi = lukija.nextLine();
                    To.teePaketti(koodi, jnimi);
                    break;

                case 5:
                    System.out.println("Anna seurantakoodi:");
                    String pkoodi = lukija.nextLine();
                    System.out.println("Anna paikan nimi:");
                    String paikka = lukija.nextLine();
                    System.out.println("Anna tapahtuman kuvaus: ");
                    String kuvaus = lukija.nextLine();
                    To.teeTapahtuma(pkoodi, paikka, kuvaus);
                    break;

                case 6:
                    System.out.println("Anna paketin seurantakoodi:");
                    String kkoodi = lukija.nextLine();
                    To.printHistoria(kkoodi);
                    break;

                case 7:
                    System.out.println("Anna asiakkaan nimi:");
                    String aasiakas = lukija.nextLine();
                    To.printPakettiMaara(aasiakas);
                    break;


            }
        }
    }
}
