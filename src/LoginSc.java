import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    JTextField t1;
    JButton b1;

    private int buttonsPerRow = 10;

    private JLabel azione, dramma, fantascienza, commedia, horror, l1;

    private JPanel contentPane;

    ArrayList<Film> list = new ArrayList<Film>();

    public LoginSc(String titolo) {
        contentPane = new JPanel(null);

        t1 = new JTextField(60);
        t1.setBounds(1200, 20, 270, 30);
        t1.setFont(new Font("Gotham", Font.BOLD, 14));
        t1.setForeground(Color.white);
        t1.setBackground(Color.black);


        b1 = new JButton("Cerca");
        b1.setBounds(1470, 20, 110, 30);
        b1.setFont(new Font("Gotham", Font.BOLD, 14));
        b1.setForeground(Color.black);
        b1.setBackground(Color.white);

        l1 = new JLabel("");
        l1.setBounds(1200, 80, 400, 30);

        add(t1);
        add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l1.setText("Hai cercato");
            }
        });



        JLabel azione = new JLabel("Azione");
        setLabelProperties(azione, 20, 20);
        contentPane.add(azione);

        FilmAzione Avengers = new FilmAzione("The Avengers","sgs","af");
        list.add(Avengers);

        //createButtons(imagePaths, links, 20, 50);

        dramma = new JLabel("Dramma");
        Font labelFont = dramma.getFont();
        dramma.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        dramma.setBounds(20, 320, 200, 20);
        contentPane.add(dramma);



        //createButtons(Dramma_img, Dramma_link, 20, 350);

        fantascienza = new JLabel("Fantascienza");
        labelFont = fantascienza.getFont();
        fantascienza.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        fantascienza.setBounds(20, 620, 200, 20);
        contentPane.add(fantascienza);



        //createButtons(Fantascienza_img, Fantascienza_link, 20, 650);

        commedia = new JLabel("Commedia");
        labelFont = commedia.getFont();
        commedia.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        commedia.setBounds(20, 920, 200, 20);
        contentPane.add(commedia);



        //createButtons(Commedia_img, Commedia_link, 20, 950);

        horror = new JLabel("Horror");
        labelFont = horror.getFont();
        horror.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        horror.setBounds(20, 1220, 200, 20);
        contentPane.add(horror);



        //createButtons(Horror_img, Horror_link, 20, 1250);


        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        contentPane.setPreferredSize(new Dimension(760, 1550));


        scrollPane.getVerticalScrollBar().setUnitIncrement(16);


        add(scrollPane);


        setTitle(titolo);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        setVisible(true);
    }

    private void setLabelProperties(JLabel label, int x, int y) {
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        label.setBounds(x, y, 200, 20);
    }

    private void createButtons(String[] imagePaths, String[] links, int startingX, int startingY) {
        int xOffset = startingX;
        int yOffset = startingY;
        for (int i = 0; i < imagePaths.length; i++) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource(imagePaths[i]));
                Image dimg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                JButton btn = new JButton(imageIcon);
                int col = i % buttonsPerRow;
                if (col == 0 && i != 0) {
                    yOffset += 260;
                    xOffset = startingX;
                }
                btn.setBounds(xOffset + col * 210, yOffset, 200, 250);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.addActionListener(new ButtonClickListener(links[i]));
                contentPane.add(btn);


                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        btn.setBorder(BorderFactory.createEmptyBorder()); // reset del border
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class ButtonClickListener implements ActionListener {
        private String link;

        public ButtonClickListener(String link) {
            this.link = link;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
}