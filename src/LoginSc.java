import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    JTextField t1;
    JButton b1;
    JLabel goBackLabel, homeLabel; // Navigation bar labels

    private JLabel azioneLabel, drammaLabel, fantascienzaLabel, commediaLabel, horrorLabel, l1;

    private JPanel contentPane, searchBarPanel, navBarPanel; // Added navBarPanel

    JMenuItem i1, i2, i3, i4, i5;

    ArrayList<Film> moviesList = new ArrayList<>(); // List for all movies
    ArrayList<Film> searchResults = new ArrayList<>(); // List for search results

    public LoginSc(String titolo) {
        contentPane = new JPanel(null);

        // Create search bar panel
        searchBarPanel = new JPanel(null);
        searchBarPanel.setBounds(1200, 20, 380, 50); // Adjusted position for search bar panel
        searchBarPanel.setBackground(Color.black);

        t1 = new JTextField(60);
        t1.setBounds(0, 0, 270, 30);
        t1.setFont(new Font("Gotham", Font.BOLD, 14));
        t1.setForeground(Color.white);
        t1.setBackground(Color.black);
        t1.setBorder(BorderFactory.createLineBorder(Color.white)); // Add border
        t1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchResults();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchResults();
            }
        });

        b1 = new JButton("Cerca");
        b1.setBounds(290, 0, 90, 30); // Adjusted position for search button
        b1.setFont(new Font("Gotham", Font.BOLD, 14));
        b1.setForeground(Color.black);
        b1.setBackground(Color.white);
        b1.addActionListener(this); // Add ActionListener to search button

        searchBarPanel.add(t1);
        searchBarPanel.add(b1);

        // Add search bar panel to contentPane
        contentPane.add(searchBarPanel);

        // Add your labels
        azioneLabel = createLabel("Azione", 20, 20);
        contentPane.add(azioneLabel);

        drammaLabel = createLabel("Dramma", 20, 320);
        contentPane.add(drammaLabel);

        fantascienzaLabel = createLabel("Fantascienza", 20, 620);
        contentPane.add(fantascienzaLabel);

        commediaLabel = createLabel("Commedia", 20, 920);
        contentPane.add(commediaLabel);

        horrorLabel = createLabel("Horror", 20, 1220);
        contentPane.add(horrorLabel);

        readMoviesFromFile("Film.txt", moviesList); // Read movies from a single file

        // Now that movies are added to the list, create buttons for each genre
        createButtons(moviesList, 20);

        // Creating navigation bar panel
        navBarPanel = new JPanel(null);
        navBarPanel.setBounds(0, 0, 800, 50);
        navBarPanel.setBackground(Color.black);

        // Go Back Label
        goBackLabel = createNavLabel("Go Back", 20);
        navBarPanel.add(goBackLabel);

        // Home Label
        homeLabel = createNavLabel("Home", 100);
        navBarPanel.add(homeLabel);

        contentPane.add(navBarPanel);

        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        contentPane.setPreferredSize(new Dimension(1600, 1600)); // Adjust preferred size
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);

        setTitle(titolo);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private void readMoviesFromFile(String fileName, ArrayList<Film> list) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Split the line by comma
                if (parts.length == 4) { // Assuming each movie has name, link, image name, and genre
                    String movieName = parts[0];
                    String movieLink = parts[1];
                    String movieImageName = parts[2];
                    String genre = parts[3]; // Genre is the fourth part

                    // Determine the type of Film subclass based on the genre
                    Film movie;
                    switch (genre) {
                        case "Azione":
                            movie = new FilmAzione(movieName, movieLink, movieImageName, genre);
                            list.add(movie);
                            break;
                        case "Dramma":
                            movie = new FilmDramma(movieName, movieLink, movieImageName, genre);
                            list.add(movie);
                            break;
                        case "Fantascienza":
                            movie = new FilmFantascienza(movieName, movieLink, movieImageName, genre);
                            list.add(movie);
                            break;
                        case "Commedia":
                            movie = new FilmCommedia(movieName, movieLink, movieImageName, genre);
                            list.add(movie);
                            break;
                        case "Horror":
                            movie = new FilmHorror(movieName, movieLink, movieImageName, genre);
                            list.add(movie);
                            break;
                        default:
                            movie = null;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
        label.setBounds(x, y, 200, 20);
        return label;
    }

    private JLabel createNavLabel(String text, int x) {
        JLabel label = new JLabel(text);
        Font labelFont = label.getFont();
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, 16));
        label.setForeground(Color.white);
        label.setBounds(x, 10, 80, 30);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovered
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle click events for navigation labels (Go Back, Home)
                if (text.equals("Go Back")) {
                    // Implement go back functionality
                } else if (text.equals("Home")) {
                    showHomePage();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.blue); // Change color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.white); // Change color back on exit
            }
        });
        return label;
    }

    private void createButtons(ArrayList<Film> movies, int startingX) {
        int yOffsetAzione = 50;
        int yOffsetDramma = 350;
        int yOffsetFantascienza = 650;
        int yOffsetCommedia = 950;
        int yOffsetHorror = 1250;

        int startingXAzione = 20;
        int startingXDramma = 20;
        int startingXFantascienza = 20;
        int startingXACommedia = 20;
        int startingXHorror = 20;

        int buttonWidth = 200;
        int buttonHeight = 250;

        for (Film movie : movies) {
            JButton btn = createButton(movie);
            // Place the button under the corresponding label based on the genre
            if (movie instanceof FilmAzione) {
                btn.setBounds(startingXAzione, yOffsetAzione, buttonWidth, buttonHeight);
                startingXAzione += buttonWidth + 20; // Increase x-coordinate for next button
            } else if (movie instanceof FilmDramma) {
                btn.setBounds(startingXDramma, yOffsetDramma, buttonWidth, buttonHeight);
                startingXDramma += buttonWidth + 20;
            } else if (movie instanceof FilmFantascienza) {
                btn.setBounds(startingXFantascienza, yOffsetFantascienza, buttonWidth, buttonHeight);
                startingXFantascienza += buttonWidth + 20;
            } else if (movie instanceof FilmCommedia) {
                btn.setBounds(startingXACommedia, yOffsetCommedia, buttonWidth, buttonHeight);
                startingXACommedia += buttonWidth + 20;
            } else if (movie instanceof FilmHorror) {
                btn.setBounds(startingXHorror, yOffsetHorror, buttonWidth, buttonHeight);
                startingXHorror += buttonWidth + 20;
            }
            contentPane.add(btn);
        }
    }

    private JButton createButton(Film movie) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(movie.getImg_nome()));
            Image dimg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JButton btn = new JButton(imageIcon);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(new ButtonClickListener(movie.getLink()));

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

            return btn;
        } catch (IOException e) {
            e.printStackTrace();
            return new JButton(); // Return a default button if image loading fails
        }
    }

    private void showHomePage() {
        // Clear the content pane except for the navigation bar and search bar
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);

        // Recreate buttons for all movies
        createButtons(moviesList, 20);

        // Repaint the content pane to reflect changes
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void updateSearchResults() {
        String searchText = t1.getText().trim();
        searchMovies(searchText);
    }

    private void searchMovies(String searchText) {
        // Clear the search results
        searchResults.clear();

        if (searchText.isEmpty()) {
            // If the search text is empty, display the home page
            showHomePage();
            return;
        }

        // Filter movies based on the search text
        for (Film movie : moviesList) {
            if (movie.getNome().toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(movie);
            }
        }

        // Clear the content pane except for the navigation bar and search bar
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);

        // Create buttons for search results
        createButtons(searchResults, 20);

        // Repaint the content pane to reflect changes
        contentPane.revalidate();
        contentPane.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

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
