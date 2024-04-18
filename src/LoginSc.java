import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    private String[] imagePaths = {"immagineRegistrazione.png", "Kung Fu Panda 4.jpg", "The Avenger.jpg",
            "Il fabbricante di lacrime.jpg", "Hustle.jpg", "Godzilla vs Kong Un nuovo impero.jpg",
            "Chiara Ferragni Unposted.jpg", "Avengers Infinity War.jpeg",
            "Avengers Endgame.jpeg", "Age of Ultron.jpg"}; // Add paths to your images
    private String[] links = {"https://example.com/link1", "https://streamingcommunity.africa/watch/7692",
            "https://streamingcommunity.africa/watch/215", "https://streamingcommunity.africa/watch/8158",
            "https://streamingcommunity.africa/watch/5165",
            "https://streamingcommunity.africa/watch/8139", "https://streamingcommunity.africawatch/632",
            "https://streamingcommunity.africa/watch/211", "https://streamingcommunity.africa/watch/216",
            "https://streamingcommunity.africa/watch/217"}; // Add links corresponding to each image
    private int buttonsPerRow = 10; // Number of buttons per row
    private JPanel buttonPanel; // Panel to contain the buttons

    public LoginSc(String titolo) {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Align buttons to the left with horizontal and vertical gap of 10 pixels
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        for (int i = 0; i < imagePaths.length; i++) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource(imagePaths[i]));
                Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                JButton btn = new JButton(imageIcon);
                btn.addActionListener(new ButtonClickListener(links[i]));
                buttonPanel.add(btn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        add(scrollPane, BorderLayout.CENTER);

        // Set fullscreen mode
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
