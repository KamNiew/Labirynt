package labirynt;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class Labirynt extends JFrame implements ActionListener {
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private int Punkty = 0;
    private final Timer timer;
    private final Timer dokonca;
    private final gracz gracz;
    private final wyjscie wyjscie;
    public Random rand;
    private final Wyniki wyniki;
    private int kluczliczz = 0;
    private ArrayList<spraity> level;
    private final ArrayList<String> listaleveli = new ArrayList<>();
    private final Image tlo = Toolkit.getDefaultToolkit().getImage("resoures/tla/Tlo_labirynt.jpg");
    private final Image tlo2 = Toolkit.getDefaultToolkit().getImage("resoures/tla/Tlo_labirynt2.jpg");
    private final Image tlo_pocz = Toolkit.getDefaultToolkit().getImage("resoures/tla/Tlo_poczatek.jpg");
    private final Image tlo_instrukcja = Toolkit.getDefaultToolkit().getImage("resoures/tla/Tlo_instrukcja.jpg");
    private final Image tlo_pauza = Toolkit.getDefaultToolkit().getImage("resoures/tla/Pauza_tlo.jpg");
    private final Image tlo_pocz2 = Toolkit.getDefaultToolkit().getImage("resoures/tla/Tlo_labirynt2_pocz.jpg");
    private final Image tlo_kon = Toolkit.getDefaultToolkit().getImage("resoures/tla/Koniec_tlo.jpg");
    private final Image krater = Toolkit.getDefaultToolkit().getImage("resoures/spraity/krater.png");
    private final Image n_gra = Toolkit.getDefaultToolkit().getImage("resoures/przyciski/n_gra.png");
    private final Image pauza_im = Toolkit.getDefaultToolkit().getImage("resoures/przyciski/pauza.png");
    private final Image wynik_im = Toolkit.getDefaultToolkit().getImage("resoures/przyciski/wynik.png");
    private final Image wyjscie_im = Toolkit.getDefaultToolkit().getImage("resoures/przyciski/wyjscie.png");
    private final Image instrukje_im = Toolkit.getDefaultToolkit().getImage("resoures/przyciski/instrukcja.png");

    private final Icon wznow_im = new ImageIcon(Toolkit.getDefaultToolkit().getImage("resoures/przyciski/wznow.png"));
    private final Icon pauza_img = new ImageIcon(pauza_im);

    private final ActionClass actionEvent = new ActionClass();
    private boolean czygracz = false, poczatek = true, wynwpisz = false, wynikiwys = false, pauza = false, instrukcja = false;
    private int zmiana;
    private int inital = 0;
    private int inital2 = 0;
    private int ktory = 0;
    private int kluczez = 0;
    private int kluczes = 0;
    private int petla = 1;
    private int miejscewpisu = 0;
    private int kontguz = 0;
    private int calez = 0;
    private int cales = 0;
    private int nowamapa = 0;
    private final JTextField textfield = new JTextField();
    private final JButton potwierdz = new JButton("Potwierdź");
    public static Labirynt labirynt;
    public render render;
    private JButton bpauza;
    private JButton bnowg;
    private JButton bwyjscie;
    private JButton bwynik;
    private JButton binst;

    public Labirynt() {

        wyniki = new Wyniki();
        render = new render();
        timer = new Timer(20, this);

        setContentPane(render);
        getContentPane();
        setTitle("Labirynt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 624);
        render.addKeyListener(new Klawiatura());
        setResizable(false);
        setVisible(true);
        setFocusable(true);

        wczytajlewele();

        level = Mapa.getMury(listaleveli.get(ktory));
        zmiana = Mapa.getCzas_levelu() / petla;

        gracz = new gracz(30, 30);
        przyciski();
        wyjscie = new wyjscie(120, 30);
        ActionListener d = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    zmien();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            private void zmien() {
                if (zmiana != 0) {
                    if (czygracz) {
                        zmiana = zmiana - 1;

                        render.repaint();
                    }

                }

                czygracz();


            }


        };

        dokonca = new Timer(1000, d);
        dokonca.start();

        timer.start();

    }

    public class render extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            rysuj(g);

        }
    }

    private void wczytajlewele() {

        try {
            Scanner plik = new Scanner(new File("resoures/mapy/listaleveli"));

            while (plik.hasNextLine()) {
                listaleveli.add("resoures/mapy/" + plik.nextLine());

            }
            plik.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void przyciski() {
        bpauza = new JButton(new ImageIcon(pauza_im));
        bnowg = new JButton(new ImageIcon(n_gra));
        bwyjscie = new JButton(new ImageIcon(wyjscie_im));
        bwynik = new JButton(new ImageIcon(wynik_im));
        binst = new JButton(new ImageIcon(instrukje_im));

        add(bnowg);
        add(bpauza);
        add(bwynik);
        add(binst);
        add(bwyjscie);


        bnowg.setBounds(900, 374, 100, 46);
        bnowg.addActionListener(actionEvent);
        bpauza.setBounds(900, 420, 100, 46);
        bpauza.addActionListener(actionEvent);

        bwynik.setBounds(900, 466, 100, 46);
        bwynik.addActionListener(actionEvent);
        binst.setBounds(900, 512, 100, 46);
        binst.addActionListener(actionEvent);
        bwyjscie.setBounds(900, 558, 100, 46);
        bwyjscie.addActionListener(actionEvent);
        bnowg.setActionCommand("1");
        bpauza.setActionCommand("2");
        bwynik.setActionCommand("3");
        binst.setActionCommand("4");
        bwyjscie.setActionCommand("5");


    }

    private void dod_wypisz() {
        kontguz = 1;


        potwierdz.setBounds(350, 450, 140, 40);
        add(potwierdz);
        potwierdz.addActionListener(actionEvent);
        potwierdz.setActionCommand("6");

        textfield.setFont(smallFont);

        textfield.setBackground(Color.GREEN.darker().darker());
        textfield.setBounds(350, 400, 140, 30);
        add(textfield);
    }

    private void usun_wypisz() {
        remove(potwierdz);
        remove(textfield);
        render.repaint();
    }

    class ActionClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent menu) {
            int action = Integer.parseInt(menu.getActionCommand());
            switch (action) {
                case 1 -> {
                    nowa_gra();
                    render.requestFocusInWindow();
                }
                case 2 -> {
                    if (!poczatek)
                        pauza();
                    render.requestFocusInWindow();
                }
                case 3 -> {
                    wyniki_przyc();
                    render.requestFocusInWindow();
                }
                case 4 -> {

                    instrukcja();
                    render.requestFocusInWindow();
                }
                case 5 -> System.exit(0);
                case 6 -> {
                    String imie_zpola = textfield.getText();
                    try {
                        wyniki.wprowadz(imie_zpola, Punkty);
                        wynwpisz = false;
                        usun_wypisz();
                        wynikiwys = true;
                        render.repaint();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void rysuj(Graphics g) {
        if (poczatek) {

            g.drawImage(tlo_pocz, 0, 0, render);
            g.drawImage(tlo_pocz2, 900, 0, render);
        }
        if (pauza) {

            g.drawImage(tlo_pauza, 0, 0, render);
            g.drawImage(tlo_pocz2, 900, 0, render);

        }
        if (wynwpisz) {
            g.drawImage(tlo_kon, 0, 0, render);
            g.drawImage(tlo_pocz2, 900, 0, render);
            g.setFont(smallFont);
            g.setColor(Color.BLACK);
            String potw = "Wpisz imie: ";

            g.drawString(potw, 370, 390);
            if (kontguz == 0) {
                dod_wypisz();
                render.repaint();
            }
        }
        if (wynikiwys) {
            g.drawImage(tlo, 0, 0, render);
            g.drawImage(tlo_pocz2, 900, 0, render);
            wyniki.wyniki(g);
        }

        if (instrukcja) {

            g.drawImage(tlo_instrukcja, 0, 0, render);
            g.drawImage(tlo_pocz2, 900, 0, render);
        }
        if (czygracz) {


            g.drawImage(tlo, 0, 0, render);
            g.drawImage(tlo2, 900, 0, render);

            g.drawImage(wyjscie.getImage(), wyjscie.getX(),
                    wyjscie.getY(), render);


            rysujMape(g);
            g.setFont(smallFont);
            g.setColor(Color.BLACK);
            g.drawImage(gracz.getImage(), gracz.getX(),
                    gracz.getY(), render);
            String s = "" + Punkty;
            String z = "" + kluczez;
            String sr = "" + kluczes;
            g.drawString("Punkty:", 900, 140);
            g.drawString(s, 900, 155);
            g.drawString("Czas: " + zmiana, 900, 170);

            g.drawString(z + "/" + calez, 930, 200);
            g.drawString(sr + "/" + cales, 930, 240);

        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void rysujMape(Graphics g) {
        for (spraity element : level) {
            if (element instanceof mur)
                g.drawImage(element.getImage(), element.getX(), element.getY(), render);
            if (element instanceof Bonus && element.isVisible())
                g.drawImage(element.getImage(), element.getX(), element.getY(), render);
            if (element instanceof Zegar && element.isVisible())
                g.drawImage(element.getImage(), element.getX(), element.getY(), render);
            if ((element instanceof KluczZ || element instanceof KluczS) && element.isVisible()) {
                //  System.out.println(element);
                if (element instanceof KluczZ && nowamapa == 0) calez++;
                if (element instanceof KluczS && nowamapa == 0) cales++;
                g.drawImage(element.getImage(), element.getX(), element.getY(), render);
            }
            if (element instanceof gracz && inital == 0) {
                //  System.out.println(element);
                gracz.setX(element.getX());
                gracz.setY(element.getY());
                inital = 1;
            }
            if (element instanceof wyjscie && inital2 == 0) {
                //  System.out.println(element);
                wyjscie.setX(element.getX());
                wyjscie.setY(element.getY());
                inital2 = 1;
            }
            if (element instanceof pulapka && element.isVisible()) {


                g.drawImage(element.getImage(), element.getX(), element.getY(), render);
                //  System.out.println(element);

            }
            if (element instanceof pulapka && !element.isVisible()) {


                g.drawImage(krater, element.getX(), element.getY(), render);
                //  System.out.println(element);

            }
        }
        nowamapa = 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        try {
            krok(kolizjamur());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (kolizjadrzwi() && kluczlicz() == kluczez && kluczes > 0) {
            //  System.out.println(kluczlicz);
            Punkty = kluczes * Punkty;

            zmiana_levelu();


        }
        if (kolizjaklucz()) {

            render.repaint();


        }
        if (kolizjabonus()) {
           // java.awt.Toolkit.getDefaultToolkit().beep();

            render.repaint();
        }
        if (kolizjazegar()) {
            render.repaint();
        }
        if (kolizjapulapka()) {

            render.repaint();
        }


    }

    private void zmiana_levelu() {
        nowamapa = 0;
        calez = 0;
        cales = 0;
        Punkty += zmiana * 10;
        kluczliczz = 0;

        if (ktory != listaleveli.size() - 1) {
            ktory = +1;
        } else {
            ktory = 0;
            petla++;
        }

        level.clear();
        try {
            level = Mapa.getMury(listaleveli.get(ktory));
            zmiana = Mapa.getCzas_levelu() / petla;
            inital = 0;
            inital2 = 0;
            kluczes = 0;
            kluczez = 0;
            render.repaint();


        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private void nowa_gra() {
        usun_wypisz();
        dokonca.start();
        timer.start();
        nowamapa = 0;
        calez = 0;
        cales = 0;
        poczatek = false;
        if (pauza) pauza();
        wynwpisz = false;
        wynikiwys = false;
        czygracz = true;
        Punkty = 0;
        ktory = 0;
        inital = 0;
        inital2 = 0;
        kluczes = 0;
        kluczez = 0;
        kluczliczz = 0;

        kontguz = 0;
        level.clear();
        try {
            level = Mapa.getMury(listaleveli.get(ktory));
            zmiana = Mapa.getCzas_levelu();
            repaint();


        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private void pauza() {
        if (pauza) {
            if (instrukcja||wynikiwys) { }
            else{
            czygracz = true;
            timer.start();
            dokonca.start();
            }
            bpauza.setIcon(pauza_img);
            pauza = false;

        } else {
            bpauza.setIcon(wznow_im);
            czygracz = false;


            timer.stop();
            dokonca.stop();

            pauza = true;

        }
        render.repaint();

    }

    private void instrukcja() {
        if (!poczatek) {
            if (instrukcja) {
                if (pauza||wynikiwys) { }
                else{
                czygracz = true;
                timer.start();
                dokonca.start();}
                instrukcja = false;
            } else {
                czygracz = false;

                timer.stop();
                dokonca.stop();

                instrukcja = true;

            }
        }
        else {
            instrukcja = !instrukcja;
        }

        render.repaint();

    }

    private void wyniki_przyc() {
        if (!poczatek) {
        if (wynikiwys) {
            if (pauza||instrukcja) { }
            else {
                czygracz = true;
                timer.start();
                dokonca.start();
            }
            wynikiwys = false;
        } else {
            czygracz = false;


            timer.stop();
            dokonca.stop();

            wynikiwys = true;

        }}
        else {
            wynikiwys = !wynikiwys;
        }
        render.repaint();
    }

    private void czygracz() {

        if (zmiana <= 0) {
            czygracz = false;
            if (!poczatek) {

                ArrayList<WynRozl> wypisz = wyniki.getWynik();

                for (WynRozl wynRozl : wypisz) {
                    // System.out.println(Punkty);
                    if (wynRozl.getPunktacje() < Punkty) {
                        miejscewpisu = 1;
                        break;
                    }
                }
                // System.out.println(miejscewpisu);
                if (miejscewpisu == 0)
                    wynikiwys = true;
                else wynwpisz = true;
                render.repaint();
            }
        }
    }

    private boolean kolizjamur() {
        Rectangle g = gracz.getBounds();

        for (spraity mur : level) {
            if (mur instanceof mur) {
                Rectangle m = mur.getBounds();

                if (g.intersects(m)) {

                    return true;
                }
            }
        }
        return false;
    }

    private boolean kolizjadrzwi() {
        Rectangle g = gracz.getBounds();
        Rectangle d = wyjscie.getBounds();
        return g.intersects(d);
    }

    private boolean kolizjaklucz() {
        Rectangle g = gracz.getBounds();

        for (spraity klucz : level) {
            if (klucz instanceof KluczZ || klucz instanceof KluczS) {
                Rectangle k = klucz.getBounds();

                if (g.intersects(k)) {
                    kluczzieksz(klucz);


                    // System.out.println(klucz);
                    return true;
                }
            }
        }
        return false;

    }

    private boolean kolizjazegar() {
        Rectangle g = gracz.getBounds();

        for (spraity zegar : level) {
            if (zegar instanceof Zegar) {
                Rectangle z = zegar.getBounds();
                if (g.intersects(z)) {
                    if (zegar.isVisible()) {

                        zmiana += 20;
                        zegar.setVisible(false);
                    }

                    return true;
                }
            }
        }
        return false;

    }

    private boolean kolizjabonus() {
        rand = new Random();
        Rectangle g = gracz.getBounds();

        for (spraity bonus : level) {
            if (bonus instanceof Bonus) {
                Rectangle b = bonus.getBounds();
                if (g.intersects(b)) {
                    if (bonus.isVisible()) {

                        Punkty += 20 + 10 * rand.nextInt(3);
                        bonus.setVisible(false);
                    }

                    return true;
                }
            }
        }
        return false;

    }

    private boolean kolizjapulapka() {
        rand = new Random();
        Rectangle g = gracz.getBounds();

        for (spraity pulapka : level) {
            if (pulapka instanceof pulapka) {
                Rectangle p = pulapka.getBounds();
                if (g.intersects(p)) {
                    if (pulapka.isVisible()) {

                        zmiana -= 20 + rand.nextInt(20);
                        pulapka.setVisible(false);
                    }

                    return true;
                }
            }
        }
        return false;

    }

    private int kluczlicz() {
        kluczliczz = 0;

        for (spraity klucz : level) {
            if (klucz instanceof KluczZ) {
                kluczliczz++;
            }
        }
        return kluczliczz;
    }


    private void kluczzieksz(spraity klucz) {
        if (klucz.isVisible()) {
            if (klucz instanceof KluczZ) {
                kluczez++;
                klucz.setVisible(false);
            }

            if (klucz instanceof KluczS) {
                kluczes++;
                klucz.setVisible(false);
            }
        }

    }

    private void krok(boolean d) {

        gracz.move(d);
        render.repaint(gracz.getX() - 5, gracz.getY() - 5, gracz.getWidth() + 10, gracz.getHeight() + 10);
    }

    private class Klawiatura extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            gracz.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            gracz.keyPressed(e);
        }
    }




    public static void main(String[] args) {

        labirynt = new Labirynt();
    }
}

