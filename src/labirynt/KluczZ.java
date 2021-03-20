package labirynt;


public class KluczZ extends spraity {

    public KluczZ(int x, int y) {
        super(x, y);


        loadImage("resoures/spraity/klucz_zloty.png");
        getImageDimensions();
    }


    @Override
    public String toString() {
        return "Klucz{" +
                "visible=" + visible +
                '}';
    }
}
