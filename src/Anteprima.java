import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class Anteprima extends MioFrame implements ActionListener, WindowListener{
    JTextField t1;
    JButton b1,loginButton;
    JLabel goBackLabel, homeLabel; // Navigation bar labels

    Schermata sc;

    boolean temp = false;

    private JLabel azioneLabel, drammaLabel, fantascienzaLabel, commediaLabel, horrorLabel, l1;

    private JPanel contentPane, searchBarPanel, navBarPanel; // Added navBarPanel

    JMenuItem i1, i2, i3, i4, i5;

    ArrayList<Film> moviesList = new ArrayList<>(); // List for all movies
    ArrayList<Film> searchResults = new ArrayList<>(); // List for search results

    public Anteprima(String titolo) {
        contentPane = new JPanel(null);

        // Create search bar panel
        searchBarPanel = new JPanel(null);
        searchBarPanel.setBounds(1200, 20, 380, 50); // Adjusted position for search bar panel


        t1 = new JTextField(60);
        t1.setBounds(0, 0, 270, 30);
        t1.setFont(new Font("Gotham", Font.BOLD, 14));
        t1.setForeground(Color.white);
        t1.setBackground(Color.black);
        t1.setBorder(BorderFactory.createLineBorder(Color.white)); // Add border

        b1 = new JButton("Cerca");
        b1.setBounds(290, 0, 90, 30); // Adjusted position for search button
        b1.setFont(new Font("Gotham", Font.BOLD, 14));
        b1.setForeground(Color.black);
        b1.setBackground(Color.white);
        b1.addActionListener(this); // Add ActionListener to search button

        loginButton = new JButton("Login");
        loginButton.setBounds(1700, 20, 90, 30); // Adjusted position for login button
        loginButton.setFont(new Font("Gotham", Font.BOLD, 14));
        loginButton.setForeground(Color.black);
        loginButton.setBackground(Color.white);
        // Add ActionListener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc = new Schermata("Stringing Community");
                sc.setBounds(0, 0, 350, 350);
                sc.rendiVisibile(sc);
            }
        });
        contentPane.add(loginButton);

        searchBarPanel.add(t1);
        searchBarPanel.add(b1);

        // Add search bar panel to contentPane
        contentPane.add(searchBarPanel);

        // Add your labels
        azioneLabel = createLabel("Azione", 20, 50);
        contentPane.add(azioneLabel);

        drammaLabel = createLabel("Dramma", 20, 350);
        contentPane.add(drammaLabel);

        fantascienzaLabel = createLabel("Fantascienza", 20, 650);
        contentPane.add(fantascienzaLabel);

        commediaLabel = createLabel("Commedia", 20, 950);
        contentPane.add(commediaLabel);

        horrorLabel = createLabel("Horror", 20, 1250);
        contentPane.add(horrorLabel);

        readMoviesFromFile("Film.txt", moviesList); // Read movies from a single file

        // Now that movies are added to the list, create buttons for each genre
        createButtons(moviesList, 20);

        // Creating navigation bar panel
        navBarPanel = new JPanel(null);
        navBarPanel.setBounds(0, 0, 800, 50);

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
        int yOffsetAzione = 80;
        int yOffsetDramma = 380;
        int yOffsetFantascienza = 680;
        int yOffsetCommedia = 980;
        int yOffsetHorror = 1280;

        int startingXAzione = 20;
        int startingXDramma = 20;
        int startingXFantascienza = 20;
        int startingXACommedia = 20;
        int startingXHorror = 20;

        int labelWidth = 200;
        int labelHeight = 250;

        for (Film movie : movies) {
            JLabel label = createLabel(movie);
            // Place the label under the corresponding label based on the genre
            if (movie instanceof FilmAzione) {
                label.setBounds(startingXAzione, yOffsetAzione, labelWidth, labelHeight);
                startingXAzione += labelWidth + 20; // Increase x-coordinate for next label
            } else if (movie instanceof FilmDramma) {
                label.setBounds(startingXDramma, yOffsetDramma, labelWidth, labelHeight);
                startingXDramma += labelWidth + 20;
            } else if (movie instanceof FilmFantascienza) {
                label.setBounds(startingXFantascienza, yOffsetFantascienza, labelWidth, labelHeight);
                startingXFantascienza += labelWidth + 20;
            } else if (movie instanceof FilmCommedia) {
                label.setBounds(startingXACommedia, yOffsetCommedia, labelWidth, labelHeight);
                startingXACommedia += labelWidth + 20;
            } else if (movie instanceof FilmHorror) {
                label.setBounds(startingXHorror, yOffsetHorror, labelWidth, labelHeight);
                startingXHorror += labelWidth + 20;
            }
            contentPane.add(label);
        }
    }

    private JLabel createLabel(Film movie) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(movie.getImg_nome()));
            Image dimg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            JLabel label = new JLabel(imageIcon);
            label.setLayout(new BorderLayout()); // Use BorderLayout to add components
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Create a button and set its properties
            JButton button = new JButton("Open");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (sc != null && isUserLoggedIn()) {
                        openLink(movie.getLink());
                    } else {
                        JOptionPane.showMessageDialog(Anteprima.this, "Devi eseguire il login per accedere al film.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            // Add a mouse listener to handle hover events
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Scale up the image and label size on mouse enter
                    Image img = imageIcon.getImage();
                    double width = img.getWidth(null) * 1.25;
                    double height = img.getHeight(null) * 1.25;
                    Image newImg = img.getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(newImg));

                    // Add the button to the label and repaint
                    label.add(button, BorderLayout.SOUTH);
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Restore the original image and label size on mouse exit
                    label.setIcon(imageIcon);

                    // Remove the button from the label and repaint
                    label.remove(button);
                    label.revalidate();
                    label.repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (sc != null && isUserLoggedIn()) {
                        if (e.getComponent().getComponentAt(e.getPoint()) != button) {
                            openLink(movie.getLink());
                        }
                    } else {
                        JOptionPane.showMessageDialog(Anteprima.this, "Devi eseguire il login per accedere al film.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            return label;
        } catch (IOException e) {
            e.printStackTrace();
            return new JLabel(); // Return a default label if image loading fails
        }
    }

    private boolean isUserLoggedIn() {
        if (sc != null && sc.isLogin()) {
            return true;
        }
        return false;
    }

    private void showHomePage() {
        // Clear the content pane except for the navigation bar and search bar
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);

        // Add genre labels to the content pane
        contentPane.add(azioneLabel);
        contentPane.add(drammaLabel);
        contentPane.add(fantascienzaLabel);
        contentPane.add(commediaLabel);
        contentPane.add(horrorLabel);

        // Recreate buttons for all movies
        createButtons(moviesList, 20);

        // Repaint the content pane to reflect changes
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void searchMovies(String searchText) {
        // Clear the search results
        searchResults.clear();

        if (searchText.isEmpty()) {
            // If the search text is empty, display the original homepage
            showHomePage();
            return;
        }

        // Filter movies based on the search text
        for (Film movie : moviesList) {
            if (movie.getNome().toLowerCase().startsWith(searchText.toLowerCase())) {
                searchResults.add(movie);
            }
        }

        // Clear the content pane except for the navigation bar and search bar
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);

        // Create labels for search results
        createButtons(searchResults, 20);

        // Repaint the content pane to reflect changes
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void openLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            // Search button clicked
            String searchText = t1.getText().trim();
            searchMovies(searchText);
        }
    }
}