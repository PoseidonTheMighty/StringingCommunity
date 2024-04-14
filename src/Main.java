import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {

        FlatLightLaf.setup();

        Schermata sc = new Schermata("Stringing Community");
        sc.rendiVisibile(sc);
    }
}