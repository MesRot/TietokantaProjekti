package com.company;

import java.sql.SQLException;
import java.util.Random;

public class Testaus {
    public Testaus(){
    }
    static public void testaaKaikki() throws SQLException {
        testi1();
        testi2();
        testi3();
        testi4();
        testi5();
    }
    static public void testi1() throws SQLException {
        long alku = System.nanoTime();
        for(int i = 0; i < 1000; i++){
            String paikka = "P" + (i+1);
            Toiminnot.teePaikka(paikka);
        }
        long kulunutAika = System.nanoTime() - alku;

        printKulunutAika(1, kulunutAika);
    }

    static public void testi2() throws SQLException {
        long alku = System.nanoTime();
        for(int i = 0; i < 1000; i++){
            String asiakas = "A" + (i+1);
            Toiminnot.teeAsiakas(asiakas);
        }
        long kulunutAika = System.nanoTime() - alku;

        printKulunutAika(2, kulunutAika);
    }

    static public void testi3() throws SQLException {
        Random rng = new Random();
        long alku = System.nanoTime();
        for(int i = 0; i < 1000; i++){
            String koodi = "KOODI" + i;
            String asiakas = "A" + (rng.nextInt(1000) + 1);
            Toiminnot.teePaketti(koodi, asiakas);
        }
        long kulunutAika = System.nanoTime() - alku;
        printKulunutAika(3, kulunutAika);
    }

    static public void testi4() throws SQLException {
        Random rng = new Random();
        long alku = System.nanoTime();
        for(int i = 0; i < 1000000000; i++){
            if(i % 100000 == 0){
                System.out.println("Paasty pakettiin: " + i);
            }
            String koodi = "KOODI" + rng.nextInt(1000);
            String paikka = "P" + (rng.nextInt(1000) + 1);
            String kuvaus = "Kuvaus numero " + i+1;
            Toiminnot.teeTapahtuma(koodi, paikka, kuvaus);
        }
        long kulunutAika = System.nanoTime() - alku;
        printKulunutAika(4, kulunutAika);

    }

    static public void testi5() throws SQLException {
        Random rng = new Random();
        long alku = System.nanoTime();
        for(int i = 0; i < 1000; i++){
            String asiakas = "A" + rng.nextInt(1000);
            Toiminnot.printPakettiMaara(asiakas);
        }
        long kulunutAika = System.nanoTime() - alku;
        printKulunutAika(5, kulunutAika);
    }
    private static void printKulunutAika(int testi, long aika){
        System.out.println("Aikaa kului testiin " + testi + ": " + (aika/1000000000) + " sekuntia");
    }
}
