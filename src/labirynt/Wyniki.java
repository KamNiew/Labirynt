package labirynt;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;


public class Wyniki extends JPanel {

    private final Font bigFont = new Font("Arial", Font.BOLD, 24);
    private static final ArrayList<String> punkty = new ArrayList<>();
    private static final ArrayList<WynRozl> wypisz = new ArrayList<>();


    public static void wczytaj() {

        try {
            Scanner plik = new Scanner(new File("resoures/wyniki"));

            while (plik.hasNextLine()) {
                punkty.add(plik.nextLine());

            }
            plik.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s : punkty) {
            wypisz.add(new WynRozl(s));
        }

    }

    public void wyniki(Graphics g) {
        wypisz.clear();
        punkty.clear();
        wczytaj();
        int y = 0;
        g.setFont(bigFont);
        g.setColor(Color.BLACK);
        for (int i = 0; i < wypisz.size(); i++) {

            String p = i + 1 + ". " + "Imie: " + wypisz.get(i).getNick();
            String d = " Punkty:" + wypisz.get(i).getPunktacje();
            g.drawString(p, 110, 50 + y);
            g.drawString(d, 500, 50 + y);
            y += 30;

        }
    }

    public void wprowadz(String imie, int punk) throws Exception {
        wypisz.clear();
        punkty.clear();
        String tymcz = imie + " " + punk;
        wczytaj();

        wypisz.add(new WynRozl(tymcz));

        Collections.sort(wypisz);
        while (wypisz.size() != 9) {
            wypisz.remove(wypisz.size() - 1);
        }
        Writer w = new FileWriter("resoures/wyniki");
        for (WynRozl wynRozl : wypisz) {
            w.write(wynRozl.getNapis());
            w.write(System.lineSeparator());
        }
        w.close();

    }

    public ArrayList<WynRozl> getWynik() {
        wypisz.clear();
        punkty.clear();
        wczytaj();
        return wypisz;
    }

}

class WynRozl implements Comparable<WynRozl> {
    private final String punkty;
    private String imie;
    private int wynik;

    public WynRozl(String punkty) {
        this.punkty = punkty;
        rozlorz();
    }

    private void rozlorz() {
        String[] podzielone;
        podzielone = punkty.split(" ");
        imie = podzielone[0];
        wynik = Integer.parseInt(podzielone[1]);


    }

    public String getNick() {
        return imie;
    }

    public int getPunktacje() {
        return wynik;
    }

    @Override
    public int compareTo(WynRozl o) {

        int poruwnaj = o.getPunktacje();

        return poruwnaj - wynik;
    }


    public String getNapis() {
        return imie + " " + wynik;
    }
}





