/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author GerardPC
 */
public class GameGUI extends JPanel implements ActionListener {

    public final int PANEL_WIDTH = 800;
    public final int PANEL_HEIGHT = 500;
    private final List<Integer> winnings = Arrays.asList(100, 200, 300, 500, 1000, 2000, 4000,
            8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000);
    private ArrayList<Question> questions;
    private ArrayList<Player> prevPlayers;
    private boolean isOn;
    private int questionNo;
    private int hintCounter = 3;
    private Player player;
    private LifeLines lifelines;
    private Scoreboard playerScores;
    private static DBManager database;
    private JButton startButton, exitButton, scoreButton, buttonA, buttonB, buttonC, buttonD, hintButton;
    private JPanel northPanel, bottomPanel;
    private JLabel currentWinnings, currentQuestion, gameTitle, image, remainLifeLine, scoreBoard;

    public GameGUI() {
        super();
        super.setLayout(new BorderLayout());
        super.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        isOn = false;
        questionNo = 0;
        playerScores = new Scoreboard();
        player = new Player();
        questions = new ArrayList<>();
        lifelines = new LifeLines(questions);
        northPanel = new JPanel();
        database = new DBManager();
        database.establishConnection();
        displayStartScreen();
    }

    // Intro Screen
    private void displayStartScreen() {
        gameTitle = new JLabel("Who Wants To Be A Millionaire");
        gameTitle.setFont(new Font("Monospaced", Font.BOLD, 30));
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");
        scoreButton = new JButton("Scoreboard");

        startButton.addActionListener(this);
        scoreButton.addActionListener(this);
        exitButton.addActionListener(this);

        startButton.setBackground(Color.pink);
        scoreButton.setBackground(Color.pink);
        exitButton.setBackground(Color.pink);
        
        northPanel.add(gameTitle);

        JPanel backgroundPanel = new JPanel();
        image = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("startBackground.jpg").getImage().getScaledInstance(PANEL_WIDTH, 400, Image.SCALE_DEFAULT));
        image.setIcon(imageIcon);
        backgroundPanel.add(image);
        bottomPanel = new JPanel();
        bottomPanel.add(startButton);
        bottomPanel.add(scoreButton);
        bottomPanel.add(exitButton);
        super.add(northPanel, BorderLayout.NORTH);
        super.add(backgroundPanel, BorderLayout.CENTER);
        super.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void displayScoreBoard() {
        super.removeAll();
        northPanel.removeAll();
        bottomPanel.removeAll();

        prevPlayers = database.getQuery();
        
        DefaultListModel<String> playerList = new DefaultListModel<>();
        if(prevPlayers.size() > 0) {
            for(Player player : prevPlayers){
                playerList.addElement("Name: " + player.playerName + "  Winnings: " + player.getWinnings());
            }
        } else {
            playerList.addElement("No Records in Database!");
        }
        JList list = new JList<>(playerList);
        list.setFont(new Font("Monospaced", Font.PLAIN, 15));
        
        scoreBoard = new JLabel("Scoreboard");
        scoreBoard.setFont(new Font("Monospaced", Font.BOLD, 30));
        startButton = new JButton("Start");
        exitButton = new JButton("Exit");

        startButton.addActionListener(this);
        exitButton.addActionListener(this);

        startButton.setBackground(Color.pink);
        exitButton.setBackground(Color.pink);
        
        bottomPanel.add(startButton);
        bottomPanel.add(exitButton); 
        northPanel.add(scoreBoard);
        
        super.add(northPanel, BorderLayout.NORTH);
        super.add(list, BorderLayout.CENTER);
        super.add(bottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void displayGame() {
        loadQuestions();
        displayCurrentQuestion();
        JOptionPane.showMessageDialog(this, "Welcome " + this.player.playerName + "!", "Goodluck!", JOptionPane.DEFAULT_OPTION);
    }

    // Display current question to GUI
    private void displayCurrentQuestion() {
        super.removeAll();
        currentWinnings = new JLabel("Current Winnings: $" + player.getWinnings());
        currentWinnings.setFont(new Font("Monospaced", Font.BOLD, 30));
        remainLifeLine = new JLabel("Hints Left: " + hintCounter);
        remainLifeLine.setFont(new Font("Monospaced", Font.BOLD, 20));
        JPanel topText = new JPanel();
        topText.setLayout(new BorderLayout());
        topText.add(currentWinnings, BorderLayout.NORTH);
        topText.add(remainLifeLine, BorderLayout.SOUTH);
        northPanel.removeAll();
        northPanel.add(topText);

        // Setting up Center Panel for Questions
        currentQuestion = new JLabel(questions.get(questionNo).getQuestion());
        currentQuestion.setFont(new Font("Monospaced", Font.BOLD, 25));
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new GridBagLayout());
        questionPanel.setBackground(Color.LIGHT_GRAY);
        questionPanel.add(currentQuestion);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Setting up South Panel
        buttonA = new JButton(questions.get(questionNo).getAnswerOne());
        buttonA.setForeground(Color.WHITE);
        buttonA.setBackground(Color.BLACK);
        buttonB = new JButton(questions.get(questionNo).getAnswerTwo());
        buttonB.setForeground(Color.WHITE);
        buttonB.setBackground(Color.BLACK);
        buttonC = new JButton(questions.get(questionNo).getAnswerThree());
        buttonC.setForeground(Color.WHITE);
        buttonC.setBackground(Color.BLACK);
        buttonD = new JButton(questions.get(questionNo).getAnswerFour());
        buttonD.setForeground(Color.WHITE);
        buttonD.setBackground(Color.BLACK);
        hintButton = new JButton("Life Line");
        hintButton.setForeground(Color.BLACK);
        hintButton.setBackground(Color.YELLOW);
        if (lifelines.hintStatus() || hintCounter == 0) {
            hintButton.setEnabled(false);
        }
        exitButton = new JButton("Take Winnings");
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(Color.GREEN);

        buttonA.addActionListener(this);
        buttonB.addActionListener(this);
        buttonC.addActionListener(this);
        buttonD.addActionListener(this);
        hintButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel guessOptions = new JPanel();
        guessOptions.add(buttonA);
        guessOptions.add(buttonB);
        guessOptions.add(buttonC);
        guessOptions.add(buttonD);

        JPanel others = new JPanel();
        others.add(hintButton);
        others.add(exitButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(guessOptions, BorderLayout.NORTH);
        bottomPanel.add(others, BorderLayout.SOUTH);

        super.add(northPanel, BorderLayout.NORTH);
        super.add(questionPanel, BorderLayout.CENTER);
        super.add(bottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (!isOn) {
            // Start Menu
            if (source == startButton) {
                String name = JOptionPane.showInputDialog(this, "Enter your name to start!", "Enter your name", JOptionPane.DEFAULT_OPTION);
                if (name != null) {
                    if (name.length() != 0) {
                        this.player.playerName = name;
                        isOn = true;
                        displayGame();
                    } else {
                        JOptionPane.showMessageDialog(this, "Name can't be empty string!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (source == exitButton) {
                JOptionPane.showMessageDialog(this, "Why aren't you playing? :(", "Maybe Next Time!", JOptionPane.DEFAULT_OPTION);
                database.closeConnections();
                System.exit(0);
            }
            if (source == scoreButton) {
                displayScoreBoard();
            }
        } else if (isOn) {
            // Game Screen
            String temp = "";
            if (source == buttonA) {
                temp = String.valueOf(buttonA.getText().charAt(0));
                checkAnswer(temp);
            }
            if (source == buttonB) {
                temp = String.valueOf(buttonB.getText().charAt(0));
                checkAnswer(temp);
            }
            if (source == buttonC) {
                temp = String.valueOf(buttonC.getText().charAt(0));
                checkAnswer(temp);
            }
            if (source == buttonD) {
                temp = String.valueOf(buttonD.getText().charAt(0));
                checkAnswer(temp);
            }
            if (source == hintButton) {
                if (hintCounter > 0) {          
                    hintCounter--;
                    JOptionPane pane = new JOptionPane("");
                    JDialog help = pane.createDialog(null, "Fetching help...");
                    help.setModal(false);
                    help.setVisible(true);
                    try {
                        Thread.sleep(2000);
                        help.setVisible(false);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(this, "Your friend said: Answer maybe is...: "+ questions.get(questionNo).getCorrectAnswer(), "HINT", JOptionPane.DEFAULT_OPTION);                    
                    if (hintCounter == 0) {
                        JOptionPane.showMessageDialog(this, "From now on you will have no more hints");
                    }
                    lifelines.setStatus(true);
                    displayCurrentQuestion();
                } 
            }
            if (source == exitButton) {
                endGame();
            }
        }
    }

    // Check if selected answer is corrected
    private void checkAnswer(String answer) {
        if (answer.equalsIgnoreCase(questions.get(questionNo).getCorrectAnswer())) {
            player.setWinnings(winnings.get(questionNo));
            JOptionPane.showMessageDialog(this, "Correct! Your current winnings is: $" + player.getWinnings(), "Nice!", JOptionPane.DEFAULT_OPTION);
            if (questionNo >= questions.size() - 1) {
                endGame();
            }
            if (questionNo == 5) {
                JOptionPane.showMessageDialog(this, "You reached the safe point! If you lose now you will still win $1000!", "Safe Point!", JOptionPane.DEFAULT_OPTION);
            }
            lifelines.setStatus(false);
            questionNo++;
            displayCurrentQuestion();
        } else {
            if (questionNo > 4) { // safe point for winnings
                player.setWinnings(winnings.get(4));
                displayCurrentQuestion();
            } else {
                player.setWinnings(0);
            }
            questionNo++;
            JOptionPane.showMessageDialog(this, "Incorrect! Game Over!", "Better luck next time!", JOptionPane.DEFAULT_OPTION);
            endGame();
        }
    }

    private void endGame() {
        if (player.getWinnings() == 1000000) {
            JOptionPane.showMessageDialog(this, "Congrats!! you are now a Millioniare!!!!$$$$$", "$$$$$$$$$$", JOptionPane.DEFAULT_OPTION);
            saveWinnings(this.player);
        } else if (player.getWinnings() > 0) {
            JOptionPane.showMessageDialog(this, "Congrats " + player.getPlayerName() + " you won: $" + player.getWinnings(), "Good Job!", JOptionPane.DEFAULT_OPTION);
            saveWinnings(this.player);
        } else {
            JOptionPane.showMessageDialog(this, "You won nothing! Thanks for playing " + this.player.playerName + "!", "Better luck next time!", JOptionPane.DEFAULT_OPTION);
            database.closeConnections();
            System.exit(0);
        }
    }

    // Reading Questions from file
    private void loadQuestions() {
        BufferedReader readFile = null;
        try {
            readFile = new BufferedReader(new FileReader("questions.txt"));
            String readNext;
            while ((readNext = readFile.readLine()) != null) {
                // Grabbing information for each question
                String question = readNext;
                String answerOne = readFile.readLine();
                String answerTwo = readFile.readLine();;
                String answerThree = readFile.readLine();;
                String answerFour = readFile.readLine();;
                String correctAnswer = readFile.readLine();;
                String hint = readFile.readLine();;
                Question temp = new Question(question, answerOne, answerTwo, answerThree, answerFour, correctAnswer, hint);
                this.questions.add(temp);
            }
        } catch (IOException ex) {
            System.out.println("Can't read the file.");
        } finally {
            if (readFile != null) {
                try {
                    readFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Save winnings to database
    private void saveWinnings(Player player) {
        if(JOptionPane.showConfirmDialog(this, "Woud you like to save your game in our database?", "SAVE", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // IF Yes
            if(database.playerExist(player.playerName)) {
                if(JOptionPane.showConfirmDialog(this, "Welcome back " + this.player.playerName + "! Would you like to override your previous winnings?", "SAVE", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(this, "You are now recorded in our database!", "Thanks!",JOptionPane.DEFAULT_OPTION);
                    database.updatePlayer(player);
                } else
                    JOptionPane.showMessageDialog(this, "Thanks for playing " + this.player.playerName + "! Hope to see you again", "Thanks!",JOptionPane.DEFAULT_OPTION);
            } else {
                JOptionPane.showMessageDialog(this, "You are now recorded in our database!", "Thanks!",JOptionPane.DEFAULT_OPTION);
                database.insertToTable(player);
            }
        } else {
            // No
            JOptionPane.showMessageDialog(this, "Thanks for playing " + this.player.playerName + "! Hope to see you again", "Thanks!",JOptionPane.DEFAULT_OPTION);
        }
        database.closeConnections();
        System.exit(0);
    }

    public static void main(String[] args) {
        GameGUI myPanel = new GameGUI();
        JFrame frame = new JFrame("Who Wants To Be A Millioniare!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myPanel);
        frame.pack();
        frame.setResizable(true);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2), (screenHeight / 2) - (frame.getHeight() / 2)));

        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                // Close Database if 'x' button is used to close
                System.out.println("Closing connection to DB");
                GameGUI.database.closeConnections();
            }
        });
    }
}
