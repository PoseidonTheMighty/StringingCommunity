import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        FlatMacDarkLaf.setup();

        Schermata sc = new Schermata("Stringing Community");
        sc.setBounds(400, 200, 330, 330);
        sc.rendiVisibile(sc);

    }
}