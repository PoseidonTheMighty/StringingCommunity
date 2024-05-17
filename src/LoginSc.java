import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    JTextField t1;
    JButton b1, loginButton;
    JLabel goBackLabel, homeLabel; // Navigation bar labels
    private boolean loggedIn = false; // Variable to track login status
    Schermata sc;

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
                    dispose();
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

        readLoginStatusFromFile();

        // Update UI based on login status
        updateUIBasedOnLoginStatus();

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

    private void readLoginStatusFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length >= 3 && parts[2].equals("1")) {
                    loggedIn = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void updateUIBasedOnLoginStatus() {
        if (loggedIn) {
            // If logged in, change the login button to a profile button
            loginButton.setText("Profile");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle profile button click
                    // For example, show user profile information
                }
            });
        }
    }

    private void keepUserLoggedIn() {
        if (loggedIn) {
            // If the user is logged in, update the login status to keep them logged in
            try {
                File inputFile = new File("login.txt");
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length >= 3 && parts[2].equals("1")) {
                        // Update login status to keep user logged in
                        writer.write(parts[0] + "\t" + parts[1] + "\t" + "1");
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }

                reader.close();
                writer.close();

                // Rename temp file to the original file
                inputFile.delete();
                tempFile.renameTo(inputFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Override windowClosing to keep user logged in when the app is closed
        keepUserLoggedIn();
        super.windowClosing(e);
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
        createButtons(movies, startingX, contentPane);
    }

    private void createButtons(ArrayList<Film> movies, int startingX, JPanel panel) {
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
            JPanel moviePanel = createPanel(movie); // Create a panel for each movie
            // Place the panel under the corresponding label based on the genre
            if (movie instanceof FilmAzione) {
                moviePanel.setBounds(startingXAzione, yOffsetAzione, labelWidth, labelHeight);
                startingXAzione += labelWidth + 20; // Increase x-coordinate for next panel
            } else if (movie instanceof FilmDramma) {
                moviePanel.setBounds(startingXDramma, yOffsetDramma, labelWidth, labelHeight);
                startingXDramma += labelWidth + 20;
            } else if (movie instanceof FilmFantascienza) {
                moviePanel.setBounds(startingXFantascienza, yOffsetFantascienza, labelWidth, labelHeight);
                startingXFantascienza += labelWidth + 20;
            } else if (movie instanceof FilmCommedia) {
                moviePanel.setBounds(startingXACommedia, yOffsetCommedia, labelWidth, labelHeight);
                startingXACommedia += labelWidth + 20;
            } else if (movie instanceof FilmHorror) {
                moviePanel.setBounds(startingXHorror, yOffsetHorror, labelWidth, labelHeight);
                startingXHorror += labelWidth + 20;
            }
            panel.add(moviePanel);
        }
    }

    private JPanel createPanel(Film movie) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(movie.getImg_nome()));

            // Set the original size of the image to 200x250
            Image scaledImg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImg);

            JPanel panel = new JPanel(new BorderLayout());

            JLabel label = new JLabel(imageIcon);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Store the original size of the image
            int originalWidth = 200;
            int originalHeight = 250;

            JButton button = new JButton("Open"); // Declare the button variable here
            button.setFont(new Font("Gotham", Font.BOLD, 14));
            button.setForeground(Color.black);
            button.setBackground(Color.white);
            button.setVisible(false); // Initially hide the button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (loggedIn) {
                        openLink(movie.getLink());
                    } else {
                        JOptionPane.showMessageDialog(null, "Devi eseguire il login per accedere al film.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Show the button when the mouse enters the label
                    button.setVisible(true);
                    // Scale up the image by 1.1 (or any factor you prefer)
                    int newWidth = (int) (originalWidth * 1.1);
                    int newHeight = (int) (originalHeight * 1.1);
                    Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(newImg));
                    label.setSize(newWidth, newHeight);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Hide the button when the mouse exits the label
                    button.setVisible(false);
                    // Restore the original size of the image (200x250)
                    label.setIcon(imageIcon);
                    label.setSize(originalWidth, originalHeight);
                }
            });

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    // Keep the button visible when the mouse enters it
                    button.setVisible(true);
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand when hovered
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Hide the button when the mouse exits it
                    button.setVisible(false);

                }
            });

            panel.add(label, BorderLayout.CENTER);
            panel.add(button, BorderLayout.SOUTH); // Add the button to the panel's SOUTH position initially

            return panel;
        } catch (IOException e) {
            e.printStackTrace();
            return new JPanel(); // Return a default panel if image loading fails
        }
    }

    private boolean isUserLoggedIn() {
        if (sc != null && sc.isLogin()) {
            sc.setLogin(true);
            return true;
        }
        sc.setLogin(false);
        return false;
    }

    private void showHomePage() {
        // Clear the content pane except for the navigation bar, search bar, and login button
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);
        contentPane.add(loginButton); // Ensure the login button is added back

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
            String[] movieNameWords = movie.getNome().toLowerCase().split("\\s+");
            if (movieNameWords.length > 0 && movieNameWords[0].startsWith(searchText.toLowerCase())) {
                searchResults.add(movie);
            }
        }

        // Clear the content pane except for the navigation bar, search bar, and login button
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);
        contentPane.add(loginButton); // Ensure the login button is added back

        // Create labels for search results
        createButtons(searchResults, 20, contentPane);

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
