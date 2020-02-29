package com.company;


import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Toiminnot{
    public static void teePaikka(String paikannimi) throws SQLException {
        if(!loytyy(Tables.PAIKAT, paikannimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            Statement s = db.createStatement();
            PreparedStatement p = db.prepareStatement("INSERT INTO Paikat (nimi) VALUES (?)");
            p.setString(1, paikannimi);
            p.execute();
            db.close();
        }
        else{
            System.out.println("Paikannimi on jo tietokannassa");
        }


    }

    public static void teeTietokanta() throws SQLException{  // lisaa try catch ettei ohjelma mene vituiks
        try{
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            Statement s = db.createStatement();
            s.execute("CREATE TABLE Asiakkaat (id INTEGER PRIMARY KEY, nimi TEXT)");
            s.execute("CREATE TABLE Paikat (id INTEGER PRIMARY KEY, nimi TEXT)");
            s.execute("CREATE TABLE Paketit (id INTEGER PRIMARY KEY, koodi TEXT, asiakas_id INTEGER)");
            s.execute("CREATE TABLE Tapahtumat (id INTEGER PRIMARY KEY, paketti_id INTEGER, paikka_id INTEGER, tapahtuman_kuvaus TEXT, paivamaara TEXT)");
            db.close();
        }
        catch (Exception e){
            System.out.println("Tietokanta tehtynä jo valmiiksi");

        }
    }
    public static void teeAsiakas(String nimi) throws SQLException{
        if(!loytyy(Tables.ASIAKKAAT, nimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            PreparedStatement p = db.prepareStatement("INSERT INTO Asiakkaat (nimi) VALUES (?)");
            p.setString(1,nimi);
            p.execute();
            db.close();
        }
        else{
            System.out.println("Asiakas löytyy jo tietokannasta");
        }


    }
    public static void teePaketti(String koodi, String asiakasNimi) throws SQLException {
        if(loytyy(Tables.ASIAKKAAT, asiakasNimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            int asiakasid = haeId(Tables.ASIAKKAAT, asiakasNimi);
            PreparedStatement p = db.prepareStatement("INSERT INTO Paketit (koodi, asiakas_id) VALUES (?, ?)");
            p.setString(1, koodi);
            p.setString(2, String.valueOf(asiakasid));
            p.execute();
            db.close();
        }
        else{
            System.out.println("Asiakasta " + asiakasNimi + " ei löydy tietokannasta");
        }
    }
    public static void teeTapahtuma(String koodi, String paikka, String kuvaus) throws SQLException{
        if(loytyy(Tables.PAKETIT, koodi) && loytyy(Tables.PAIKAT, paikka)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String paivamaara = dateFormat.format(date).split(" ")[0];
            String kellonAika = dateFormat.format(date).split(" ")[1];
            int paikkaId = haeId(Tables.PAIKAT, paikka);
            int pakettiId = haeId(Tables.PAKETIT, koodi);
            PreparedStatement p = db.prepareStatement("INSERT INTO Tapahtumat (paketti_id, paikka_id, tapahtuman_kuvaus, paivamaara, kellonaika) VALUES (?, ?, ?, ?, ?)");
            p.setString(1, String.valueOf(pakettiId));
            p.setString(2, String.valueOf(paikkaId));
            p.setString(3, kuvaus);
            p.setString(4, paivamaara);
            p.setString(5, kellonAika);
            p.execute();
            db.close();
        }
        else{
            System.out.println("Jotain tietoa ei löydy tietokannasta");
        }

    }

    private static int haeId(Tables poyta, String haettava) throws SQLException{
        PreparedStatement p = null;
        Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
        switch (poyta){
            case PAIKAT:
                p = db.prepareStatement("SELECT id FROM Paikat WHERE nimi = ?");
                break;
            case ASIAKKAAT:
                p = db.prepareStatement("SELECT id FROM Asiakkaat WHERE nimi = ?");
                break;
            case PAKETIT:
                p = db.prepareStatement("SELECT id FROM Paketit WHERE koodi = ?");
                break;
        }

        assert p != null;
        p.setString(1, haettava);
        ResultSet r = p.executeQuery();
        int id = r.getInt("id");
        p.close();
        db.close();
        return id;
    }

    public static void printHistoria(String koodi) throws SQLException{
        if(loytyy(Tables.PAKETIT, koodi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            PreparedStatement p = db.prepareStatement("SELECT Paketit.koodi, Paikat.nimi, Tapahtumat.tapahtuman_kuvaus FROM Paketit, Paikat, Tapahtumat WHERE Paketit.koodi= ? AND Paketit.id = Tapahtumat.paketti_id AND Paikat.id = Tapahtumat.paikka_id");
            p.setString(1, koodi);
            ResultSet r = p.executeQuery();
            db.close();
            while (r.next()) {
                System.out.println(r.getString("koodi")+", "+r.getString("nimi")+ ", " + r.getString("tapahtuman_kuvaus")); //lisaa kelllonajan haku
            }
        }
        else{
            System.out.println("Pakettia ei löytynyt");
        }
    }
    public static void printPakettiMaara(String asiakas) throws SQLException {
        if(loytyy(Tables.ASIAKKAAT, asiakas)) {
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            String A_id = String.valueOf(haeId(Tables.ASIAKKAAT, asiakas));
            PreparedStatement p = db.prepareStatement("SELECT Asiakkaat.nimi, Paketit.koodi, COUNT(Tapahtumat.id) maara FROM Paketit, Asiakkaat, Tapahtumat WHERE Asiakkaat.id=? AND Tapahtumat.paketti_id=Paketit.id AND Paketit.asiakas_id=Asiakkaat.id GROUP BY Paketit.id");
            p.setString(1, A_id);
            ResultSet r = p.executeQuery();
            db.close();
            while (r.next()) {
                System.out.println(r.getString("koodi") + ", " + r.getInt("maara") + " tapahtumaa");
            }
        }
        else{
                System.out.println("Pakettia ei löytynyt");
            }
        }
    public static void printPaivamaaranPerusteella(String paivamaara, String paikka) throws SQLException {
        if(loytyy(Tables.PAIKAT, paikka)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            PreparedStatement p = db.prepareStatement("SELECT P.nimi as kaupunki, T.paivamaara as paivamaara, COUNT(T.id) as tapahtumia FROM Paikat P LEFT JOIN Tapahtumat T ON T.paikka_id=P.id AND P.nimi=?  AND T.paivamaara=?");
            p.setString(1, paikka);
            p.setString(2, paivamaara);
            ResultSet r = p.executeQuery();
            db.close();
            while (r.next()) {
                System.out.println("Paivamaaralla " + paivamaara + " loytyi " + r.getInt("tapahtumia") + " tapahtumaa");
            }
        }
        else{
            System.out.println("Paikkaa ei loytynyt");
        }

    }

    private static Boolean loytyy(Tables poyta, String haettava) throws SQLException{ ///etsii pöydästä tietoa, palauttaa true jos löytyy
        PreparedStatement p = null;
        Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
        switch (poyta){
            case ASIAKKAAT:
                    p = db.prepareStatement("SELECT nimi FROM Asiakkaat WHERE nimi=?");
                    break;

            case PAIKAT:
                    p = db.prepareStatement("SELECT nimi FROM Paikat WHERE nimi = ?");
                    break;

            case PAKETIT:
                    p = db.prepareStatement("SELECT koodi FROM Paketit WHERE koodi = ?");
                    break;

        }
        assert p != null;
        p.setString(1, haettava);
        ResultSet r = p.executeQuery();
        Boolean loytyy = r.next();
        p.close();
        db.close();
        return loytyy;
    }
}
