package labirynt;


import java.awt.event.KeyEvent;


public class gracz extends spraity {
    private int dx, popx;
    private int dy, popy;

    public gracz(int x, int y) {
        super(x, y);
        loadImage("resoures/spraity/gracz.png");
        getImageDimensions();


    }

    public void move(boolean d) {

        if (!d) {
            x += dx;
            y += dy;

        } else {
            x = popx;
            y = popy;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        dx = 0;
        dy = 0;
        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
            popx = x + 2;
            popy = y;
            loadImage("resoures/spraity/graczl.png");


        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
            popx = x - 2;
            popy = y;
            loadImage("resoures/spraity/graczp.png");

        }


        if (key == KeyEvent.VK_UP) {
            dy = -2;
            popx = x;
            popy = y + 2;
            loadImage("resoures/spraity/graczg.png");


        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
            popx = x;
            popy = y - 2;
            loadImage("resoures/spraity/graczd.png");
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (dx == 0) {
            if (key == KeyEvent.VK_UP) {
                dy = 0;
            }

            if (key == KeyEvent.VK_DOWN) {
                dy = 0;
            }
        }
    }


}
