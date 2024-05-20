import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class LoginSc extends MioFrame implements ActionListener, WindowListener {

    JTextField t1,t2,t3;
    JButton b1, loginButton,profileButton;

    JLabel homeLabel,azioneNavLabel, drammaNavLabel, fantascienzaNavLabel, commediaNavLabel, horrorNavLabel, watchLaterLabel; // Genre labels in the navbar
    private boolean loggedIn = false; // Variable to track login status
    Schermata sc;

    String username;

    JScrollPane scrollPane;

    private JLabel consigliatiLabel,vistiLabel,azioneLabel, drammaLabel, fantascienzaLabel, commediaLabel, horrorLabel, l1;

    private JPanel contentPane, searchBarPanel, navBarPanel; // Added navBarPanel

    ArrayList<Film> moviesList = new ArrayList<>(); // List for all movies
    ArrayList<Film> searchResults = new ArrayList<>(); // List for search results

    ArrayList<JPanel> moviePanels = new ArrayList<>();

    private Map<String, Set<Film>> movieIndex = new HashMap<>(); // Index for faster search with Set to avoid duplicates

    // HashMap to index movies by genre
    // Define lists to cache movies by genre
    private List<Film> azioneMovies = new ArrayList<>();
    private List<Film> drammaMovies = new ArrayList<>();
    private List<Film> fantascienzaMovies = new ArrayList<>();
    private List<Film> commediaMovies = new ArrayList<>();
    private List<Film> horrorMovies = new ArrayList<>();


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

        readLoginStatusFromFile();

        contentPane.add(loginButton);

        searchBarPanel.add(t1);
        searchBarPanel.add(b1);

        // Add search bar panel to contentPane
        contentPane.add(searchBarPanel);

        vistiLabel = createLabel("Visti Recentemente", 70, 300);
        contentPane.add(vistiLabel);

        showWatchedMovies();

        // Add your labels
        azioneLabel = createLabel("Azione", 70, 780);
        contentPane.add(azioneLabel);

        drammaLabel = createLabel("Dramma", 70, 1120);
        contentPane.add(drammaLabel);

        fantascienzaLabel = createLabel("Fantascienza", 70, 1460);
        contentPane.add(fantascienzaLabel);

        commediaLabel = createLabel("Commedia", 70, 1800);
        contentPane.add(commediaLabel);

        horrorLabel = createLabel("Horror", 70, 2140);
        contentPane.add(horrorLabel);

        readMoviesFromFile("Film.txt", moviesList); // Read movies from a single file

        // Now that movies are added to the list, create buttons for each genre
        createButtons(moviesList, 70);

        // Add action listener to the search button
        b1.addActionListener(e -> searchMovies(t1.getText()));

        // Update UI based on login status
        updateUIBasedOnLoginStatus();

        // Creating navigation bar panel with GridLayout for equal spacing
        navBarPanel = new JPanel(new GridLayout(1, 7, 20, 0));
        navBarPanel.setBounds(45, 13, 800, 50);

        // Home Label
        homeLabel = createNavLabel("Home");
        navBarPanel.add(homeLabel);

        // Add genre JLabels to navigation bar
        azioneNavLabel = createNavLabel("Azione");
        navBarPanel.add(azioneNavLabel);

        drammaNavLabel = createNavLabel("Dramma");
        navBarPanel.add(drammaNavLabel);

        fantascienzaNavLabel = createNavLabel("Fantascienza");
        navBarPanel.add(fantascienzaNavLabel);

        commediaNavLabel = createNavLabel("Commedia");
        navBarPanel.add(commediaNavLabel);

        horrorNavLabel = createNavLabel("Horror");
        navBarPanel.add(horrorNavLabel);

        watchLaterLabel = createNavLabel("Watch Later");
        navBarPanel.add(watchLaterLabel);

        homeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showHomePage();
            }
        });

        // Action listener for Azione genre label
        azioneNavLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMoviesByGenre("Azione");
            }
        });

        // Action listener for Dramma genre label
        drammaNavLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMoviesByGenre("Dramma");
            }
        });

        // Action listener for Fantascienza genre label
        fantascienzaNavLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMoviesByGenre("Fantascienza");
            }
        });

        // Action listener for Commedia genre label
        commediaNavLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMoviesByGenre("Commedia");
            }
        });

        // Action listener for Horror genre label
        horrorNavLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMoviesByGenre("Horror");
            }
        });

        watchLaterLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showWatchLaterMovies();
            }
        });

        contentPane.add(navBarPanel);

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setBounds(0, 0, 800, 600);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        contentPane.setPreferredSize(new Dimension(2000, 2500)); // Adjust preferred size
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        showWatchedMovies();

        initializeMovieIndex();

        setTitle(titolo);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private void showWatchedMovies() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                java.util.List<String> lines = new ArrayList<>();

                // Construct the file path
                File file = new File(username + ".txt");

                // Check if the file exists before attempting to read it
                if (!file.exists()) {
                    System.err.println("File not found: " + file.getAbsolutePath());
                    return null;
                }

                // Read movies from the file associated with the username
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }

                int movieCount = 0; // Track the number of movies added
                int labelWidth = 200;
                int labelHeight = 300;
                int maxFilmsPerRow = 8; // Maximum number of films per row
                int xOffset = 20;
                int yOffset = 400; // Adjusted yOffset to start below the vistiLabel
                int startingX = 70;
                int currentX = startingX;
                int currentY = yOffset;

                // Iterate over the lines in reverse order
                for (int i = lines.size() - 1; i >= 0; i--) {
                    String[] parts = lines.get(i).split(",");
                    if (parts.length >= 2 && parts[1].trim().equals("1")) {
                        // Find the movie by name from the moviesList
                        String movieName = parts[0].trim();
                        for (Film movie : moviesList) {
                            if (movie.getNome().equals(movieName)) {
                                JPanel moviePanel = createPanel(movie, false); // Don't show remove button for Watched
                                moviePanel.setBounds(currentX, currentY, labelWidth, labelHeight);
                                contentPane.add(moviePanel);
                                movieCount++;
                                // Adjust coordinates for the next panel
                                currentX += labelWidth + xOffset;
                                if (movieCount % maxFilmsPerRow == 0) {
                                    // Move to the next row
                                    currentX = startingX;
                                    currentY += labelHeight + xOffset;
                                }
                                // Print the movie as it is added
                                System.out.println("Added to Watched: " + movie.getNome());
                            }
                        }
                    }
                }

                // Repaint the content pane to reflect changes
                contentPane.revalidate();
                contentPane.repaint();

                return null;
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    private void showWatchLaterMovies() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Clear the content pane except for the navigation bar, search bar, and login button
                contentPane.removeAll();
                contentPane.add(navBarPanel);
                contentPane.add(searchBarPanel);
                contentPane.add(loginButton); // Ensure the login button is added back

                // Read movies from the file associated with the username
                try (BufferedReader br = new BufferedReader(new FileReader(username + ".txt"))) {
                    String line;
                    int movieCount = 0; // Track the number of movies added
                    int labelWidth = 200;
                    int labelHeight = 300;
                    int maxFilmsPerRow = 8; // Maximum number of films per row
                    int xOffset = 20;
                    int yOffset = 80;
                    int currentX = 20;
                    int currentY = 80;

                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length >= 2 && parts[1].trim().equals("true")) {
                            // Find the movie by name from the moviesList
                            String movieName = parts[0].trim();
                            for (Film movie : moviesList) {
                                if (movie.getNome().equals(movieName)) {
                                    JPanel moviePanel = createPanel(movie, true); // Show remove button for Watch Later
                                    moviePanel.setBounds(currentX, currentY, labelWidth, labelHeight);
                                    contentPane.add(moviePanel);
                                    movieCount++;
                                    // Adjust coordinates for the next panel
                                    currentX += labelWidth + xOffset;
                                    if (movieCount % maxFilmsPerRow == 0) {
                                        // Move to the next row
                                        currentX = 20;
                                        currentY += labelHeight + xOffset;
                                    }
                                    // Print the movie as it is added
                                    System.out.println("Added to Watch Later: " + movie.getNome());
                                    break;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Repaint the content pane to reflect changes
                contentPane.revalidate();
                contentPane.repaint();

                return null;
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    private void removeMovieFromWatchLater(String movieName) {
        try {
            File inputFile = new File(username + ".txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(movieName)) {
                    // Set the flag to false to mark the movie as removed from Watch Later
                    writer.write(parts[0] + ",false\n");
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

    private void showMoviesByGenre(String genre) {
        // Disable the navbar labels to prevent concurrent clicks
        setNavbarLabelsEnabled(false);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Clear the content pane except for the navigation bar, search bar, and login button
                contentPane.removeAll();
                contentPane.add(navBarPanel);
                contentPane.add(searchBarPanel);
                contentPane.add(loginButton);

                // Define the list of movies to be displayed based on the selected genre
                List<Film> genreMovies = new ArrayList<>();
                switch (genre) {
                    case "Azione":
                        genreMovies.addAll(azioneMovies);
                        break;
                    case "Dramma":
                        genreMovies.addAll(drammaMovies);
                        break;
                    case "Fantascienza":
                        genreMovies.addAll(fantascienzaMovies);
                        break;
                    case "Commedia":
                        genreMovies.addAll(commediaMovies);
                        break;
                    case "Horror":
                        genreMovies.addAll(horrorMovies);
                        break;
                    default:
                        break;
                }

                // If the cache for the selected genre is empty, filter movies based on the genre
                if (genreMovies.isEmpty()) {
                    for (Film movie : moviesList) {
                        if (movie.getGenre().equals(genre)) {
                            genreMovies.add(movie);
                        }
                    }
                }

                // Calculate dimensions for movie panels
                int labelWidth = 200;
                int labelHeight = 300; // Adjusted height to accommodate button
                int maxFilmsPerRow = 8; // Maximum number of films per row
                int xOffset = 20;
                int yOffset = 80;

                // Batch loading parameters
                int batchSize = 20; // Number of movies to load per batch
                int startIndex = 0;

                // Add panels for each movie batch to the content pane
                while (startIndex < genreMovies.size()) {
                    // Calculate the end index for the current batch
                    int endIndex = Math.min(startIndex + batchSize, genreMovies.size());

                    // Add panels for the current batch
                    int row = startIndex / maxFilmsPerRow;
                    int col = startIndex % maxFilmsPerRow;
                    for (int i = startIndex; i < endIndex; i++) {
                        Film movie = genreMovies.get(i);
                        JPanel moviePanel = createPanel(movie, false);
                        if (moviePanel != null) {
                            int x = 20 + col * (labelWidth + xOffset);
                            int y = yOffset + row * (labelHeight + xOffset);
                            moviePanel.setBounds(x, y, labelWidth, labelHeight);
                            contentPane.add(moviePanel);
                            col++;
                            if (col >= maxFilmsPerRow) {
                                // Move to the next row
                                col = 0;
                                row++;
                            }
                        } else {
                            System.err.println("Error: Movie panel is null for movie " + movie.getNome());
                        }
                    }

                    // Increment the start index for the next batch
                    startIndex = endIndex;
                }

                // Repaint the content pane to reflect changes
                contentPane.revalidate();
                contentPane.repaint();

                return null;
            }

            @Override
            protected void done() {
                // Re-enable the navbar labels after processing is finished
                setNavbarLabelsEnabled(true);
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    private void setNavbarLabelsEnabled(boolean enabled) {
        azioneNavLabel.setEnabled(enabled);
        drammaNavLabel.setEnabled(enabled);
        fantascienzaNavLabel.setEnabled(enabled);
        commediaNavLabel.setEnabled(enabled);
        horrorNavLabel.setEnabled(enabled);
    }

    private void readLoginStatusFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3 && parts[2].equals("1")) {
                    loggedIn = true;
                    // Extract username from email
                    String email = parts[0];
                    int atIndex = email.indexOf('@');
                    if (atIndex != -1) {
                        username = email.substring(0, atIndex);
                        // Print the username
                        System.out.println("Username: " + username);
                    }
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
            contentPane.remove(loginButton);

            profileButton = new JButton("Profilo");
            profileButton.setBounds(1700, 20, 90, 30); // Adjusted position for login button
            profileButton.setFont(new Font("Gotham", Font.BOLD, 14));
            profileButton.setForeground(Color.black);
            profileButton.setBackground(Color.white);
            contentPane.add(profileButton);


            profileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle profile button click
                    // Remove all components except search bar and navbar
                    contentPane.removeAll();
                    contentPane.add(navBarPanel);
                    contentPane.add(searchBarPanel);

                    // Create and add new labels
                    JLabel dettagliLabel = new JLabel("Dettagli");
                    JLabel abbonamentiLabel = new JLabel("Abbonamenti");
                    JLabel comunicazioniLabel = new JLabel("Comunicazioni");
                    JLabel logoutLabel = new JLabel("Log Out");

                    dettagliLabel.setBounds(200, 400, 150, 30);
                    dettagliLabel.setFont(new Font("Gotham", Font.ITALIC, 20));
                    dettagliLabel.setForeground(Color.white);

                    abbonamentiLabel.setBounds(200, 450, 150, 30);
                    abbonamentiLabel.setFont(new Font("Gotham", Font.ITALIC, 20));
                    abbonamentiLabel.setForeground(Color.white);

                    comunicazioniLabel.setBounds(200, 500, 150, 30);
                    comunicazioniLabel.setFont(new Font("Gotham", Font.ITALIC, 20));
                    comunicazioniLabel.setForeground(Color.white);

                    logoutLabel.setBounds(200, 550, 150, 30);
                    logoutLabel.setFont(new Font("Gotham", Font.ITALIC, 20));
                    logoutLabel.setForeground(Color.red);

                    dettagliLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Create two text fields for email and password
                            t2 = new JTextField(60);
                            t3 = new JTextField(60);
                            JLabel email = new JLabel("Email");
                            JLabel password = new JLabel("Password");
                            JButton salva = new JButton("Salva");

                            email.setBounds(400,160,150,30);
                            t2.setBounds(400, 200, 150, 30);

                            password.setBounds(400,240,150,30);
                            t3.setBounds(400, 270, 150, 30);

                            salva.setBounds(475,300,100,30);


                            contentPane.add(email);
                            contentPane.add(t2);
                            contentPane.add(password);
                            contentPane.add(t3);
                            contentPane.add(salva);

                            contentPane.revalidate();
                            contentPane.repaint();

                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            dettagliLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on mouse enter
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            dettagliLabel.setCursor(Cursor.getDefaultCursor()); // Set default cursor back on mouse exit
                        }
                    });


                    abbonamentiLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            abbonamentiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on mouse enter
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            abbonamentiLabel.setCursor(Cursor.getDefaultCursor()); // Set default cursor back on mouse exit
                        }
                    });

                    comunicazioniLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            comunicazioniLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on mouse enter
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            comunicazioniLabel.setCursor(Cursor.getDefaultCursor()); // Set default cursor back on mouse exit
                        }
                    });

                    logoutLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            // Change the value of the loggedIn variable to false
                            loggedIn = false;

                            // Remove the profile button
                            contentPane.remove(profileButton);

                            // Add back the login button
                            contentPane.add(loginButton);

                            // Repaint the content pane to reflect changes
                            contentPane.revalidate();
                            contentPane.repaint();

                            // Update the login.txt file
                            updateLoginFile(username, "0"); // Change "1" to "0" in the login.txt file

                            showHomePage();
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set hand cursor on mouse enter
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            logoutLabel.setCursor(Cursor.getDefaultCursor()); // Set default cursor back on mouse exit
                        }
                    });


                    // Add labels to the content pane
                    contentPane.add(dettagliLabel);
                    contentPane.add(abbonamentiLabel);
                    contentPane.add(comunicazioniLabel);
                    contentPane.add(logoutLabel);

                    // Repaint the content pane to reflect changes
                    contentPane.revalidate();
                    contentPane.repaint();
                }
            });
        }
    }

    private void updateLoginFile(String email, String newValue) {
        // Define the file path
        Path path = Paths.get("login.txt");

        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            // Iterate through the lines to find and update the entry with the given email
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\\s+");
                if (parts.length >= 3 && parts[0].trim().equals(email)) {
                    parts[2] = newValue;
                    lines.set(i, String.join(" ", parts));
                    break;
                }
            }

            // Write the updated lines back to the file
            Files.write(path, lines, StandardCharsets.UTF_8);

            // Log success
            System.out.println("Login status updated successfully for email: " + email);
        } catch (IOException e) {
            // Log and handle the exception
            System.err.println("Error updating login status for email: " + email);
            e.printStackTrace();
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
        label.setBounds(x, y, 400, 32);
        return label;
    }

    private JLabel createNavLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Gotham", Font.BOLD, 14)); // Decrease font size to fit the label
        label.setForeground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center align text

        // Add MouseListener to handle hover effect
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand
                label.setBackground(Color.black); // Change background color to black
                label.setOpaque(true); // Make the background color visible
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Change cursor to default
                label.setBackground(null); // Reset background color
                label.setOpaque(false); // Remove background color
            }
        });

        return label;
    }

    private void createButtons(ArrayList<Film> movies, int startingX) {
        createButtons(movies, startingX, contentPane);
    }

    private void createButtons(ArrayList<Film> movies, int startingX, JPanel panel) {
        int yOffsetAzione = 810;
        int yOffsetDramma = 1150;
        int yOffsetFantascienza = 1490;
        int yOffsetCommedia = 1830;
        int yOffsetHorror = 2170;

        int startingXAzione = 70;
        int startingXDramma = 70;
        int startingXFantascienza = 70;
        int startingXACommedia = 70;
        int startingXHorror = 70;

        int labelWidth = 200;
        int labelHeight = 300;
        int maxFilmsPerRow = 8; // Maximum number of films per row

        int azioneCount = 0;
        int drammaCount = 0;
        int fantascienzaCount = 0;
        int commediaCount = 0;
        int horrorCount = 0;

        for (Film movie : movies) {
            JPanel moviePanel = createPanel(movie,false); // Create a panel for each movie

            if (movie instanceof FilmAzione) {
                if (azioneCount < maxFilmsPerRow) {
                    moviePanel.setBounds(startingXAzione, yOffsetAzione, labelWidth, labelHeight);
                    startingXAzione += labelWidth + 20; // Increase x-coordinate for next panel
                    azioneCount++;
                }
            } else if (movie instanceof FilmDramma) {
                if (drammaCount < maxFilmsPerRow) {
                    moviePanel.setBounds(startingXDramma, yOffsetDramma, labelWidth, labelHeight);
                    startingXDramma += labelWidth + 20;
                    drammaCount++;
                }
            } else if (movie instanceof FilmFantascienza) {
                if (fantascienzaCount < maxFilmsPerRow) {
                    moviePanel.setBounds(startingXFantascienza, yOffsetFantascienza, labelWidth, labelHeight);
                    startingXFantascienza += labelWidth + 20;
                    fantascienzaCount++;
                }
            } else if (movie instanceof FilmCommedia) {
                if (commediaCount < maxFilmsPerRow) {
                    moviePanel.setBounds(startingXACommedia, yOffsetCommedia, labelWidth, labelHeight);
                    startingXACommedia += labelWidth + 20;
                    commediaCount++;
                }
            } else if (movie instanceof FilmHorror) {
                if (horrorCount < maxFilmsPerRow) {
                    moviePanel.setBounds(startingXHorror, yOffsetHorror, labelWidth, labelHeight);
                    startingXHorror += labelWidth + 20;
                    horrorCount++;
                }
            }
            panel.add(moviePanel);
            moviePanels.add(moviePanel); // Add movie panel to the list
        }
    }

    private JPanel createPanel(Film movie, boolean showRemoveButton) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(movie.getImg_nome()));

            // Set the original size of the image to 200x250
            Image scaledImg = img.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImg);

            // Pre-scale the hover image by 1.1 times
            int hoverWidth = (int) (200 * 1.1);
            int hoverHeight = (int) (250 * 1.1);
            Image hoverImg = img.getScaledInstance(hoverWidth, hoverHeight, Image.SCALE_SMOOTH);
            ImageIcon hoverIcon = new ImageIcon(hoverImg);

            JPanel panel = new JPanel(new BorderLayout());
            panel.setPreferredSize(new Dimension(200, 300));

            JLabel label = new JLabel(imageIcon);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

            JButton actionButton = new JButton(showRemoveButton ? "Remove" : "Add");
            actionButton.setFont(new Font("Gotham", Font.BOLD, 14));
            actionButton.setForeground(Color.black);
            actionButton.setBackground(Color.white);
            actionButton.setVisible(false);
            actionButton.setEnabled(loggedIn); // Disable button if not logged in

            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (loggedIn) {
                        if (showRemoveButton) {
                            // Remove movie from Watch Later
                            removeMovieFromWatchLater(movie.getNome());
                            // Reload the Watch Later movies
                            showWatchLaterMovies();
                        } else {
                            // Add movie to Watch Later only if it's not already added
                            if (!isMovieInWatchLater(movie.getNome())) {
                                addMovieToWatchLater(movie.getNome());
                                JOptionPane.showMessageDialog(null, "Film Aggiunto a Watch Later", "Info", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Devi eseguire il login per eseguire questa azione.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            JButton openButton = new JButton("Open");
            openButton.setFont(new Font("Gotham", Font.BOLD, 14));
            openButton.setForeground(Color.black);
            openButton.setBackground(Color.white);
            openButton.setVisible(false);

            openButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (loggedIn) {
                        openLink(movie.getLink());
                        // Write movie name and "1" to user's file
                        writeMovieToUserFile(username + ".txt", movie.getNome());
                    } else {
                        JOptionPane.showMessageDialog(null, "Devi eseguire il login per accedere al film.", "Avviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            buttonPanel.add(actionButton);
            buttonPanel.add(openButton);

            Timer hoverTimer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    label.setIcon(imageIcon);
                    label.setSize(200, 250);
                    actionButton.setVisible(false);
                    openButton.setVisible(false);
                    panel.setPreferredSize(new Dimension(200, 300));
                    panel.revalidate();
                    panel.repaint();
                }
            });
            hoverTimer.setRepeats(false);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hoverTimer.stop();
                    label.setIcon(hoverIcon);
                    label.setSize(hoverWidth, hoverHeight);
                    actionButton.setVisible(true);
                    openButton.setVisible(true);
                    panel.setPreferredSize(new Dimension(hoverWidth, hoverHeight + 50));
                    panel.revalidate();
                    panel.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoverTimer.restart();
                }
            });

            actionButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    actionButton.setVisible(true);
                    actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    hoverTimer.stop();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoverTimer.restart();
                }
            });

            openButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    openButton.setVisible(true);
                    openButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    hoverTimer.stop();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hoverTimer.restart();
                }
            });

            panel.add(label, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            return panel;
        } catch (IOException e) {
            e.printStackTrace();
            return new JPanel(); // Return a default panel if image loading fails
        }
    }


    private void writeMovieToUserFile(String fileName, String movieName) {
        try {
            FileWriter writer = new FileWriter(fileName, true); // Append mode
            writer.write(movieName + ",1\n"); // Write movie name and "1" with a comma between them
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isMovieInWatchLater(String movieName) {
        try (BufferedReader br = new BufferedReader(new FileReader(username + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(movieName) && parts[1].trim().equals("true")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addMovieToWatchLater(String movieName) {
        // Write movie name and "true" to a file associated with the username
        if (loggedIn) {
            try (FileWriter writer = new FileWriter(username + ".txt", true)) {
                writer.write(movieName + ",true\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showHomePage() {
        // Clear the content pane except for the navigation bar, search bar, and login button
        contentPane.removeAll();
        contentPane.add(navBarPanel);
        contentPane.add(searchBarPanel);

        // Add genre labels to the content pane
        contentPane.add(azioneLabel);
        contentPane.add(drammaLabel);
        contentPane.add(fantascienzaLabel);
        contentPane.add(commediaLabel);
        contentPane.add(horrorLabel);

        // Add vistiLabel and consigliatiLabel to the content pane
        contentPane.add(vistiLabel);
        contentPane.add(consigliatiLabel);


        if(loggedIn) {
            showWatchedMovies();
            contentPane.add(loginButton);
        }
        else
            contentPane.add(profileButton);

        // Add movie panels back to the content pane
        for (JPanel moviePanel : moviePanels) {
            contentPane.add(moviePanel);
        }

        // Set the preferred size of the content pane to match the dimensions in the LoginSc method
        contentPane.setPreferredSize(new Dimension(1600, 2500));

        // Update the viewport of the scroll pane
        scrollPane.setViewportView(contentPane);

        // Repaint the content pane to reflect changes
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Method to initialize the movie index for faster searches
// Method to initialize the movie index for faster searches
    private void initializeMovieIndex() {
        for (Film movie : moviesList) {
            String firstWord = movie.getNome().toLowerCase().split("\\s+")[0];
            movieIndex.computeIfAbsent(firstWord, k -> new HashSet<>()).add(movie);
        }
    }

    private void searchMovies(String searchText) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Disable the search button at the start of the search
                SwingUtilities.invokeLater(() -> b1.setEnabled(false));

                // Clear the search results
                searchResults.clear();

                if (searchText.isEmpty()) {
                    // If the search text is empty, display the original homepage
                    showHomePage();
                    return null;
                }

                String lowerCaseSearchText = searchText.toLowerCase();
                Set<Film> uniqueResults = new HashSet<>();

                // Search using the index
                for (Map.Entry<String, Set<Film>> entry : movieIndex.entrySet()) {
                    if (entry.getKey().startsWith(lowerCaseSearchText)) {
                        uniqueResults.addAll(entry.getValue()
                                .stream()
                                .filter(movie -> movie.getNome().toLowerCase().contains(lowerCaseSearchText))
                                .collect(Collectors.toSet()));
                    }
                }

                // Add results to the searchResults list from the Set to ensure uniqueness
                searchResults.addAll(uniqueResults);

                // Clear the content pane except for the navigation bar, search bar, and login button
                SwingUtilities.invokeLater(() -> {
                    contentPane.removeAll();
                    contentPane.add(navBarPanel);
                    contentPane.add(searchBarPanel);
                    contentPane.add(loginButton); // Ensure the login button is added back

                    // Set starting coordinates for search results
                    int startingX = 70;
                    int startingY = 100;
                    int labelWidth = 200;
                    int labelHeight = 300;
                    int maxFilmsPerRow = 8; // Maximum number of films per row
                    int xOffset = 20;

                    int currentX = startingX;
                    int currentY = startingY;

                    for (Film movie : searchResults) {
                        JPanel moviePanel = createPanel(movie, false); // Create a panel for each movie
                        moviePanel.setBounds(currentX, currentY, labelWidth, labelHeight);

                        currentX += labelWidth + xOffset;
                        if ((currentX + labelWidth + xOffset) > contentPane.getWidth()) {
                            currentX = startingX;
                            currentY += labelHeight + xOffset;
                        }

                        contentPane.add(moviePanel);
                    }

                    // Repaint the content pane to reflect changes
                    contentPane.revalidate();
                    contentPane.repaint();
                });

                return null;
            }

            @Override
            protected void done() {
                // Re-enable the search button after the search is complete
                SwingUtilities.invokeLater(() -> b1.setEnabled(true));
            }
        };

        // Execute the SwingWorker
        worker.execute();
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