import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    JTextField t1;
    JButton b1;
    JButton backButton; // New button for going back to the main view
    JLabel searchResultLabel; // Label for displaying search results

    private JPanel contentPane;
    Font labelFont;

    JLabel azione, dramma, fantascienza, commedia, horror;

    ArrayList<Film> filmList = new ArrayList<>(); // Combined list for all genres

    List<JButton> movieButtons = new ArrayList<>(); // List to hold movie buttons

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

        searchResultLabel = new JLabel("");
        searchResultLabel.setBounds(20, 150, 400, 30);
        searchResultLabel.setFont(new Font("Gotham", Font.BOLD, 16));
        searchResultLabel.setForeground(Color.WHITE);

        azione = createLabel("Azione", 20, 20);
        dramma = createLabel("Dramma", 20, 320);
        fantascienza = createLabel("Fantascienza", 20, 620);
        commedia = createLabel("Commedia", 20, 920);
        horror = createLabel("Horror", 20, 1220);

        add(t1);
        add(b1);
        add(searchResultLabel);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchKeyword = t1.getText().trim();
                if (!searchKeyword.isEmpty()) {
                    searchMovies(searchKeyword);
                }
            }
        });

        readMoviesFromFile("Film.txt", filmList); // Read movies from generic file

        // Now that movies are added to the list, create buttons for each genre
        createButtons(filmList);

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

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        label.setBounds(x, y, 200, 20);
        return label;
    }

    private void readMoviesFromFile(String fileName, ArrayList<Film> list) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by comma
                if (parts.length == 4) { // Assuming each line contains movie name, link, image name, and genre
                    String movieName = parts[0];
                    String movieLink = parts[1];
                    String movieImageName = parts[2];
                    String genre = parts[3];

                    // Create the corresponding Film object based on the genre
                    Film movie = new Film(movieName, movieLink, movieImageName, genre);
                    list.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createButtons(List<Film> movies) {
        // Track current label position
        int currentX, currentY;

        // Lists to keep track of buttons for each genre
        List<JButton> azioneButtons = new ArrayList<>();
        List<JButton> drammaButtons = new ArrayList<>();
        List<JButton> fantascienzaButtons = new ArrayList<>();
        List<JButton> commediaButtons = new ArrayList<>();
        List<JButton> horrorButtons = new ArrayList<>();

        for (Film movie : movies) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource(movie.getImg_nome()));
                Image dimg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(dimg);
                JButton btn = new JButton(imageIcon);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.addActionListener(new ButtonClickListener(movie.getLink()));

                // Determine the position based on the movie's genre
                switch (movie.getGenre()) {
                    case "Azione":
                        azioneButtons.add(btn);
                        break;
                    case "Dramma":
                        drammaButtons.add(btn);
                        break;
                    case "Fantascienza":
                        fantascienzaButtons.add(btn);
                        break;
                    case "Commedia":
                        commediaButtons.add(btn);
                        break;
                    case "Horror":
                        horrorButtons.add(btn);
                        break;
                    default:
                        // Default position if genre is not recognized
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Set initial position for buttons under each genre label
        currentX = azione.getX();
        currentY = azione.getY() + azione.getHeight() + 20;
        for (JButton btn : azioneButtons) {
            btn.setBounds(currentX, currentY, 200, 250);
            contentPane.add(btn);
            movieButtons.add(btn); // Add button to the list
            currentX += 220; // Move to next position
        }

        currentX = dramma.getX();
        currentY = dramma.getY() + dramma.getHeight() + 20;
        for (JButton btn : drammaButtons) {
            btn.setBounds(currentX, currentY, 200, 250);
            contentPane.add(btn);
            movieButtons.add(btn); // Add button to the list
            currentX += 220; // Move to next position
        }

        currentX = fantascienza.getX();
        currentY = fantascienza.getY() + fantascienza.getHeight() + 20;
        for (JButton btn : fantascienzaButtons) {
            btn.setBounds(currentX, currentY, 200, 250);
            contentPane.add(btn);
            movieButtons.add(btn); // Add button to the list
            currentX += 220; // Move to next position
        }

        currentX = commedia.getX();
        currentY = commedia.getY() + commedia.getHeight() + 20;
        for (JButton btn : commediaButtons) {
            btn.setBounds(currentX, currentY, 200, 250);
            contentPane.add(btn);
            movieButtons.add(btn); // Add button to the list
            currentX += 220; // Move to next position
        }

        currentX = horror.getX();
        currentY = horror.getY() + horror.getHeight() + 20;
        for (JButton btn : horrorButtons) {
            btn.setBounds(currentX, currentY, 200, 250);
            contentPane.add(btn);
            movieButtons.add(btn); // Add button to the list
            currentX += 220; // Move to next position
        }
    }

    private void searchMovies(String keyword) {
        // Hide all movie buttons
        for (JButton btn : movieButtons) {
            btn.setVisible(false);
        }

        // Hide all genre labels
        azione.setVisible(false);
        dramma.setVisible(false);
        fantascienza.setVisible(false);
        commedia.setVisible(false);
        horror.setVisible(false);

        // Show search result label
        searchResultLabel.setText("Risultati della ricerca per: " + keyword);
        searchResultLabel.setVisible(true);

        // Search for movies containing the keyword
        ArrayList<Film> searchResults = new ArrayList<>();
        for (Film movie : filmList) {
            if (movie.getNome().toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(movie);
            }
        }

        // Display search results
        if (!searchResults.isEmpty()) {
            createButtons(searchResults); // Create buttons for search results
        } else {
            searchResultLabel.setText("Nessun risultato trovato per: " + keyword);
        }

        // Show back button
        backButton.setVisible(true);
    }

    private void goBack() {
        // Show all movie buttons
        for (JButton btn : movieButtons) {
            btn.setVisible(true);
        }

        // Show all genre labels
        azione.setVisible(true);
        dramma.setVisible(true);
        fantascienza.setVisible(true);
        commedia.setVisible(true);
        horror.setVisible(true);

        // Hide search result label
        searchResultLabel.setVisible(false);

        // Hide back button
        backButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            goBack();
        }
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
