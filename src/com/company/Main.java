package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner lukija = new Scanner(System.in);

        while(true){
            int valinta;
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
                    Toiminnot.teeTietokanta();
                    break;

                case 2:
                    System.out.println("Anna paikan nimi:");
                    String paikanNimi = lukija.nextLine();
                    Toiminnot.teePaikka(paikanNimi);
                    break;

                case 3:
                    System.out.println("Anna asiakkaan nimi:");
                    String asiakasNimi = lukija.nextLine();
                    Toiminnot.teeAsiakas(asiakasNimi);
                    break;

                case 4:
                    System.out.println("Anna seurantakoodi:");
                    String koodi = lukija.nextLine();
                    System.out.println("Anna asiakkaan nimi:");
                    String jnimi = lukija.nextLine();
                    Toiminnot.teePaketti(koodi, jnimi);
                    break;

                case 5:
                    System.out.println("Anna seurantakoodi:");
                    String pkoodi = lukija.nextLine();
                    System.out.println("Anna paikan nimi:");
                    String paikka = lukija.nextLine();
                    System.out.println("Anna tapahtuman kuvaus: ");
                    String kuvaus = lukija.nextLine();
                    Toiminnot.teeTapahtuma(pkoodi, paikka, kuvaus);
                    break;

                case 6:
                    System.out.println("Anna paketin seurantakoodi:");
                    String kkoodi = lukija.nextLine();
                    Toiminnot.printHistoria(kkoodi);
                    break;

                case 7:
                    System.out.println("Anna asiakkaan nimi:");
                    String aasiakas = lukija.nextLine();
                    Toiminnot.printPakettiMaara(aasiakas);
                    break;

                case 8:
                    System.out.println("Anna paivamaara: (muodossa yyyy/mm/dd)");
                    String paivamaara = lukija.nextLine();
                    System.out.println("Anna paikka");
                    String spaikka = lukija.nextLine();
                    Toiminnot.printPaivamaaranPerusteella(paivamaara, spaikka);
                    break;


                case 9:
                    System.out.println("Suoritetaan järjestelmän nopeuden mittaus...");
                    Testaus.testaaKaikki();
                    break;
            }
        }
    }
}
