import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class Mapa extends JPanel {


    private static int czas_levelu = 0;
    private static final ArrayList<String> mapalinie = new ArrayList<>();
    private static final ArrayList<spraity> level = new ArrayList<>();


    public static void wczytaj(String level) {
        int x = 0, y = 0;
        try {
            Scanner plik = new Scanner(new File(level));
            czas_levelu = Integer.parseInt(plik.nextLine());
            while (plik.hasNextLine()) {
                mapalinie.add(plik.nextLine());

            }
            plik.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] podzielone;

        for (String s : mapalinie) {
            podzielone = s.split("");

            for (String value : podzielone) {
                if (value.equals("m")) {
                    Mapa.level.add(new mur(x, y));

                }
                if (value.equals("z")) {
                    Mapa.level.add(new KluczZ(x, y));

                }
                if (value.equals("s")) {
                    Mapa.level.add(new KluczS(x, y));

                }
                if (value.equals("G")) {
                    Mapa.level.add(new gracz(x, y));

                }
                if (value.equals("W")) {
                    Mapa.level.add(new wyjscie(x, y));

                }
                if (value.equals("B")) {
                    Mapa.level.add(new Bonus(x, y));

                }
                if (value.equals("c")) {
                    Mapa.level.add(new Zegar(x, y));

                }
                if (value.equals("P")) {
                    Mapa.level.add(new pulapka(x, y));

                }
                x = x + 30;
            }
            x = 0;
            y = y + 30;


        }

    }

    public static int getCzas_levelu() {
        return czas_levelu;
    }


    public static ArrayList<spraity> getMury(String level) {
        Mapa.level.clear();
        mapalinie.clear();
        wczytaj(level);
        return Mapa.level;
    }
}
