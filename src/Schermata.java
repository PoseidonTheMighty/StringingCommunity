import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import com.formdev.flatlaf.FlatLightLaf;

public class Schermata extends MioFrame implements ActionListener,WindowListener {



    public Schermata(String titolo){



        GridLayout gl = new GridLayout(0, 4, 2, 2);
        this.setLayout(gl);

        JButton lg = new JButton("LOGIN");
        this.add(lg);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
