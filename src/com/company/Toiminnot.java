package com.company;


import java.sql.*;

public class Toiminnot{
    Connection db;

    public Toiminnot() throws SQLException{
        this.db = DriverManager.getConnection("jdbc:sqlite:testi.db");
    }
    public void teePaikka(String paikannimi) throws SQLException {
        if(!this.loytyy("Paikat", paikannimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            Statement s = db.createStatement();
            PreparedStatement p = db.prepareStatement("INSERT INTO Paikat (nimi) VALUES (?)");
            p.setString(1, paikannimi);
            p.execute();
        }
        else{
            System.out.println("Paikannimi on jo tietokannassa");
        }


    }

    public void teeTietokanta() throws SQLException{  // lisaa try catch ettei ohjelma mene vituiks
        try{
            Statement s = this.db.createStatement();
            s.execute("CREATE TABLE Asiakkaat (id INTEGER PRIMARY KEY, nimi TEXT)");
            s.execute("CREATE TABLE Paikat (id INTEGER PRIMARY KEY, nimi TEXT)");
            s.execute("CREATE TABLE Paketit (id INTEGER PRIMARY KEY, koodi TEXT, asiakas_id INTEGER)");
            s.execute("CREATE TABLE Tapahtumat (id INTEGER PRIMARY KEY, paketti_id INTEGER, paikka_id INTEGER, tapahtuman_kuvaus TEXT)");
        }
        catch (Exception e){
            System.out.println("Tietokanta tehtynä jo valmiiksi");

        }

    }
    public void teeAsiakas(String nimi) throws SQLException{
        if(!this.loytyy("Asiakkaat", nimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            PreparedStatement p = db.prepareStatement("INSERT INTO Asiakkaat (nimi) VALUES (?)");
            p.setString(1,nimi);
            p.execute();
        }
        else{
            System.out.println("Asiakas löytyy jo tietokannasta");
        }


    }
    public void teePaketti(String koodi, String asiakasNimi) throws SQLException {
        if(this.loytyy("Asiakkaat", asiakasNimi)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            int asiakasid = haeId("Asiakkaat", asiakasNimi);
            PreparedStatement p = db.prepareStatement("INSERT INTO Paketit (koodi, asiakas_id) VALUES (?, ?)");
            p.setString(1, koodi);
            p.setString(2, String.valueOf(asiakasid));
            p.execute();
        }
        else{
            System.out.println("Asiakasta " + asiakasNimi + " ei löydy tietokannasta");
        }
    }
    public void teeTapahtuma(String koodi, String paikka, String kuvaus) throws SQLException{
        if(this.loytyy("Paketit", koodi) && this.loytyy("Paikat", paikka)){
            Connection db = DriverManager.getConnection("jdbc:sqlite:testi.db");
            int paikkaId = haeId("Paikat", paikka);
            int pakettiId = haeId("Paketit", koodi);
            PreparedStatement p = db.prepareStatement("INSERT INTO Tapahtumat (paketti_id, paikka_id, tapahtuman_kuvaus) VALUES (?, ?, ?)");
            p.setString(1, String.valueOf(pakettiId));
            p.setString(2, String.valueOf(paikkaId));
            p.setString(3, kuvaus);
            p.execute();
        }
        else{
            System.out.println("Jotain tietoa ei löydy tietokannasta");
        }

    }

    private int haeId(String table, String haettava) throws SQLException{
        PreparedStatement p = null;
        if(table.equals("Asiakkaat")){
            p = this.db.prepareStatement("SELECT id FROM Asiakkaat WHERE nimi = ?");
        }
        if(table.equals("Paketit")){
            p = this.db.prepareStatement("SELECT id FROM Paketit WHERE koodi = ?");
        }
        if(table.equals("Paikat")){
            p = this.db.prepareStatement("SELECT id FROM Paikat WHERE nimi = ?");
        }

        assert p != null;
        p.setString(1, haettava);
        ResultSet r = p.executeQuery();
        int id = r.getInt("id");
        p.close();
        return id;
    }

    public void printHistoria(String koodi) throws SQLException{
        if(this.loytyy("Paketit", koodi)){
            PreparedStatement p = this.db.prepareStatement("SELECT Paketit.koodi, Paikat.nimi, Tapahtumat.tapahtuman_kuvaus FROM Paketit, Paikat, Tapahtumat WHERE Paketit.koodi= ? AND Paketit.id = Tapahtumat.paketti_id AND Paikat.id = Tapahtumat.paikka_id");
            p.setString(1, koodi);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                System.out.println(r.getString("koodi")+", "+r.getString("nimi")+ ", " + r.getString("tapahtuman_kuvaus")); //lisaa kelllonajan haku
            }
        }
        else{
            System.out.println("Pakettia ei löytynyt");
        }
    }
    public void printPakettiMaara(String asiakas) throws SQLException {
        if(this.loytyy("Asiakkaat", asiakas)) {
            String A_id = String.valueOf(haeId("Asiakkaat", asiakas));
            PreparedStatement p = this.db.prepareStatement("SELECT Asiakkaat.nimi, Paketit.koodi, COUNT(Tapahtumat.id) maara FROM Paketit, Asiakkaat, Tapahtumat WHERE Asiakkaat.id=? AND Tapahtumat.paketti_id=Paketit.id AND Paketit.asiakas_id=Asiakkaat.id GROUP BY Paketit.id");
            p.setString(1, A_id);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                System.out.println(r.getString("koodi") + ", " + r.getInt("maara") + " tapahtumaa");
            }
        }
        else{
                System.out.println("Pakettia ei löytynyt");
            }
        }

    private Boolean loytyy(String table, String haettava) throws SQLException{ ///etsii pöydästä tietoa, palauttaa true jos löytyy
        PreparedStatement p;
        if(table.equals("Asiakkaat")){
            p = this.db.prepareStatement("SELECT nimi FROM Asiakkaat WHERE nimi=?");
        }
        else if(table.equals("Paikat")){
            p = this.db.prepareStatement("SELECT nimi FROM Paikat WHERE nimi = ?");
        }
        else if(table.equals("Paketit")){
            p = this.db.prepareStatement("SELECT koodi FROM Paketit WHERE koodi = ?");
        }
        else{
            System.out.println("Ei löydetty pöytää");
            return false;
        }
        assert p != null;
        p.setString(1, haettava);
        ResultSet r = p.executeQuery();
        Boolean loytyy = r.next();
        p.close();
        return loytyy;
    }
}
