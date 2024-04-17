import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {

        FlatLightLaf.setup();

        Schermata sc = new Schermata("Stringing Community");
        sc.setBounds(400, 200, 330, 300);
        sc.rendiVisibile(sc);
    }
}