package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner lukija = new Scanner(System.in);
        Toiminnot toim = new Toiminnot();

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

            if(valinta == 1){
                toim.teeTietokanta();
                break;
            }
            else if(valinta == 2){
                System.out.println("Anna paikan nimi:");
                String nimi = lukija.nextLine();
                toim.teePaikka(nimi);
                break;
            }
            else if(valinta == 3){
                System.out.println("Anna asiakkaan nimi:");
                String animi = lukija.nextLine();
                toim.teeAsiakas(animi);
                break;
            }
            else if(valinta == 4){
                System.out.println("Anna seurantakoodi:");
                String koodi = lukija.nextLine();
                System.out.println("Anna asiakkaan nimi:");
                String jnimi = lukija.nextLine();
                toim.teePaketti(koodi, jnimi);
                break;
            }
            else if(valinta == 5){
                System.out.println("Anna seurantakoodi:");
                String pkoodi = lukija.nextLine();
                System.out.println("Anna paikan nimi:");
                String paikka = lukija.nextLine();
                System.out.println("Anna tapahtuman kuvaus: ");
                String kuvaus = lukija.nextLine();
                toim.teeTapahtuma(pkoodi, paikka, kuvaus);
                break;
            }
            else if(valinta == 6){
                System.out.println("Anna paketin seurantakoodi:");
                String kkoodi = lukija.nextLine();
                toim.printHistoria(kkoodi);
                break;
            }
            else if(valinta == 7){
                System.out.println("Anna asiakkaan nimi:");
                String aasiakas = lukija.nextLine();
                toim.printPakettiMaara(aasiakas);
                break;
            }
            else if(valinta == 8){
                System.out.println("Anna paivamaara: (muodossa yyyy/mm/dd");
                String paivamaara = lukija.nextLine();
                System.out.println("Anna paikka");
                String spaikka = lukija.nextLine();
                toim.printPaivamaaranPerusteella(paivamaara, spaikka);
                break;

            }
            else if (valinta == 9){
                System.out.println("Suoritetaan järjestelmän nopeuden mittaus...");
                Testaus.testaaKaikki(toim);
                break;
            }
        }
    }
}
