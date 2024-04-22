import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LoginSc extends MioFrame implements ActionListener {

    private int buttonsPerRow = 10;

    private JLabel azione, dramma, fantascienza, commedia, horror;

    private JPanel contentPane;

    public LoginSc(String titolo) {
        contentPane = new JPanel(null);

        JLabel azione = new JLabel("Azione");
        setLabelProperties(azione, 20, 20);
        contentPane.add(azione);

        String[] imagePaths = {"The Avenger.jpg", "Godzilla vs Kong Un nuovo impero.jpg",
                "Avengers Infinity War.jpeg", "Avengers Endgame.jpeg", "Age of Ultron.jpg"};
        String[] links = {"https://streamingcommunity.africa/watch/7692", "https://streamingcommunity.africa/watch/5165",
                "https://streamingcommunity.africawatch/632", "https://streamingcommunity.africa/watch/211",
                "https://streamingcommunity.africa/watch/216", "https://streamingcommunity.africa/watch/217"};

        createButtons(imagePaths, links, 20, 50);

        dramma = new JLabel("Dramma");
        Font labelFont = dramma.getFont();
        dramma.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        dramma.setBounds(20, 320, 200, 20);
        contentPane.add(dramma);

        String[] Dramma_img = {"Il fabbricante di lacrime.jpg", "Hustle.jpg", "The Truman Show.jpg", "E colpa mia.jpeg", "Io Sono Leggenda.jpg", "Ferrari.jpg"
        }; // Add paths to your images
        String[] Dramma_link = {"https://streamingcommunity.africa/watch/8158",
                "https://streamingcommunity.africa/watch/5165", "https://streamingcommunity.africa/watch/2501", "https://streamingcommunity.africa/watch/6393",
                "https://streamingcommunity.africa/watch/124", "https://streamingcommunity.africa/watch/7255"
        };

        createButtons(Dramma_img, Dramma_link, 20, 350);

        fantascienza = new JLabel("Fantascienza");
        labelFont = fantascienza.getFont();
        fantascienza.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        fantascienza.setBounds(20, 620, 200, 20);
        contentPane.add(fantascienza);

        String[] Fantascienza_img = {"Interstellar.jpg", "Inception.jpg"
        };
        String[] Fantascienza_link = {"https://streamingcommunity.africa/watch/112", "https://streamingcommunity.africa/watch/733"
        };

        createButtons(Fantascienza_img, Fantascienza_link, 20, 650);

        commedia = new JLabel("Commedia");
        labelFont = commedia.getFont();
        commedia.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        commedia.setBounds(20, 920, 200, 20);
        contentPane.add(commedia);

        String[] Commedia_img = {"Tutti tranne te.jpeg", "I migliori giorni.jpg", "Rapiniamo il Duce.jpg", "The Wolf of Wall Street.jpg"
        };
        String[] Commedia_link = {"https://streamingcommunity.africa/watch/7622", "https://streamingcommunity.africa/watch/8152",
                "https://streamingcommunity.africa/watch/5726",
                "https://streamingcommunity.africa/watch/159"
        };

        createButtons(Commedia_img, Commedia_link, 20, 950);

        horror = new JLabel("Horror");
        labelFont = horror.getFont();
        horror.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        horror.setBounds(20, 1220, 200, 20);
        contentPane.add(horror);

        String[] Horror_img = {"La maledizione della Queen Mary.jpeg", "IT.jpg", "IT Capitolo 2.jpg"
        };
        String[] Horror_link = {"https://streamingcommunity.africa/watch/6812", "https://streamingcommunity.africa/watch/1123",
                "https://streamingcommunity.africa/watch/10"
        };

        createButtons(Horror_img, Horror_link, 20, 1250);


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
