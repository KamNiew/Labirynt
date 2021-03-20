package labirynt;


public class KluczS extends spraity {

    public KluczS(int x, int y) {
        super(x, y);


        loadImage("resoures/spraity/klucz_srebrny.png");
        getImageDimensions();
    }


    @Override
    public String toString() {
        return "Klucz{" +
                "visible=" + visible +
                '}';
    }
}
