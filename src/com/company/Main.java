package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner lukija = new Scanner(System.in);
        Toiminnot To = new Toiminnot();

        while(true){
            System.out.println("Valitse toiminto (1-9):");
            int valinta = Integer.parseInt(lukija.nextLine());
            if(valinta == 1){ //tee tietokanta
                To.teeTietokanta();
            }
            if(valinta == 2){ // tee paikka
                System.out.println("Anna paikan nimi:");
                String nimi = lukija.nextLine();
                To.teePaikka(nimi);
            }
            if(valinta == 3){ // tee asiakas
                System.out.println("Anna asiakkaan nimi:");
                String nimi = lukija.nextLine();
                To.teeAsiakas(nimi);
            }
            if (valinta == 4){ // tee paketti
                System.out.println("Anna seurantakoodi:");
                String koodi = lukija.nextLine();
                System.out.println("Anna asiakkaan nimi:");
                String nimi = lukija.nextLine();
                To.teePaketti(koodi, nimi);

            }
            if (valinta == 5){ // tee tapahtuma
                System.out.println("Anna seurantakoodi:");
                String koodi = lukija.nextLine();
                System.out.println("Anna paikan nimi:");
                String paikka = lukija.nextLine();
                System.out.println("Anna tapahtuman kuvaus: ");
                String kuvaus = lukija.nextLine();
                To.teeTapahtuma(koodi, paikka, kuvaus);


                //Lisää uusi tapahtuma tietokantaan, kun annetaan paketin seurantakoodi, tapahtuman paikka sekä kuvaus. Paketin ja paikan tulee olla valmiiksi tietokannassa.
            }
            if (valinta == 6){
                System.out.println("Anna paketin seurantakoodi:");
                String koodi = lukija.nextLine();
                To.haeHistoria(koodi);

            }
            if (valinta == 7){

            }
            if (valinta == 8){

            }
            if (valinta == 9){

            }


        }
    }
}
