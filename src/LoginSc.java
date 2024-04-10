import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginSc extends MioFrame implements ActionListener,WindowListener {

    Popup p;

    public LoginSc(String titolo){
        JFrame f = new JFrame("pop");

        // create a label
        JLabel l = new JLabel("This is a popup");

        f.setSize(400, 400);

        PopupFactory pf = new PopupFactory();

        // create a panel
        JPanel p2 = new JPanel();

        // set Background of panel
        p2.setBackground(Color.red);

        p2.add(l);

        // create a popup
        p = pf.getPopup(f, p2, 180, 100);

        // create a button
        JButton b = new JButton("click");

        // add action listener
        b.addActionListener(this);

        // create a panel
        JPanel p1 = new JPanel();

        p1.add(b);
        f.add(p1);
        f.show();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}