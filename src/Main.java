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

        LoginSc an = new LoginSc("Stringing Community");
        an.setBounds(0, 0, 2000, 2000);
        an.rendiVisibile(an);

    }
}