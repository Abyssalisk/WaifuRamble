package game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import music.Music;

/**
 * Class WaifuRamble is a game where to players choose a Waifu and combat,
 * similar to a street fighter style game.
 * 
 * @author GavinR, SpencerR
 *
 */
public class WaifuRamble {
	public JPanel cards; // a panel that uses CardLayout
	private CardLayout cardLayout; // CardLayout object
	private JPanel mainMenu; // card 1
	private JPanel battleScreen; // card 2
	private JPanel endGameScreen; // card 3

	private JPanel panelButtons_1;
	private static JFrame frame;

	public static Character playerOne; // player one
	public static Character playerTwo; // player two
	private JLabel lblCharacter_1_Name; // label to display selected first player's name
	private JLabel lblC_1_Health; // label to display first player's health
	private JLabel lblCharacter_2_Name; // label to display selected second player's name
	private JLabel lblC_2_Health; // label to display second player's health

	private boolean hasChosen = false; // both players have chosen or not
	private JLabel lblWinner; // displays winner on end game screen

	private Music battleMusic; // music for the game from class Music.java
	private BattlePanel battlePanel; // battle panel with pixel collision and key listeners from BattlePanel.java

	private int roundNum; // tracks the number of rounds played in a session

	/**
	 * Fulfills requirement for generic in team project. Never actually used.
	 * Requirements never say we have to use it, just that is must be included.
	 */
	public static void generic() {
		List<String> words = new ArrayList<String>();
		Collections.addAll(words, "one", "two", "three", "four", "five");
		System.out.println("The smallest words of: " + words.toString() + " is: "
				+ smallest(words.get(0), words.get(1), words.get(2)));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Comparable> T smallest(T x, T y, T z) {
		T result = x;
		if (y.compareTo(result) < 0) // check if it is smaller than the current result.
			result = y;
		if (z.compareTo(result) < 0)
			result = z;
		return result;
	}

	/**
	 * Adds all the cards of the card layout to the pane.
	 * 
	 * @param pane
	 */
	public void addComponentToPane(Container pane) {
		// Create the "cards".
		mainMenu = new JPanel(); // card 1
		initializeMainMenu();
		battleScreen = new JPanel(); // card 2
		initializeBattleScreen();
		endGameScreen = new JPanel();
		initializeEndGameScreen();

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(800, 700));
		cards.add(mainMenu, "Main Menu");
		cards.add(battleScreen, "Battle Screen");
		cards.add(endGameScreen, "End Game Screen");
		// Initialize cardLayout
		cardLayout = (CardLayout) (cards.getLayout());

		pane.add(cards, BorderLayout.CENTER);

	}

	/**
	 * Initializes the EndGameScreen.
	 */
	private void initializeEndGameScreen() {
		endGameScreen.setLayout(new BorderLayout(0, 0));

		JLabel lblEndGameTitle = createLblEndGameTitle();
		lblEndGameTitle.setBackground(new Color(148, 0, 211));
		endGameScreen.add(lblEndGameTitle, BorderLayout.NORTH);

		JLabel lblWinner = createLblWinner();
		lblWinner.setBackground(new Color(148, 0, 211));
		endGameScreen.add(lblWinner, BorderLayout.CENTER);

		JPanel btnPanelEndGame = new JPanel();
		btnPanelEndGame.setBackground(new Color(148, 0, 211));
		endGameScreen.add(btnPanelEndGame, BorderLayout.SOUTH);

		JButton btnRestart = createBtnRestart();
		btnPanelEndGame.add(btnRestart);

		JButton btnExit = createBtnExit();
		btnPanelEndGame.add(btnExit);
	}

	/**
	 * Creates the exit button.
	 * 
	 * @return
	 */
	private JButton createBtnExit() {
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Files.deleteIfExists(Paths.get("src/Resources/Loser.txt"));
				} catch (IOException e1) {
					System.out.println("Error.");
					e1.getMessage();
				}

				System.out.println("\nLosers file deletion successful.");
				System.exit(0);
			}

		});
		return btnExit;
	}

	/**
	 * Creates the restart button, resets fields for replay.
	 * 
	 * @return
	 */
	private JButton createBtnRestart() {
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// battleMusic.stopMusic();
				playerOne.isDead = false;
				playerTwo.isDead = false;
				playerOne.health = 100; // reset health
				playerTwo.health = 100;
				battlePanel.tm.stop();
				hasChosen = false; // reset characters
				cardLayout.show(cards, "Main Menu");

			}
		});
		return btnRestart;
	}

	/**
	 * Creates the spot that will display winner.
	 * 
	 * @return
	 */
	private JLabel createLblWinner() {
		lblWinner = new JLabel("Alloted spot to show who the winner is...");
		lblWinner.setFont(new Font("Dialog", Font.BOLD, 57));
		lblWinner.setForeground(new Color(148, 0, 211));
		lblWinner.setHorizontalAlignment(SwingConstants.CENTER);
		lblWinner.setBackground(new Color(148, 0, 211));
		return lblWinner;
	}

	/**
	 * Updates lblWinner with the picture of the winner.
	 */
	private void updateWinnerPic() {
		Character winner;
		if (playerOne.isDead)
			winner = playerTwo;
		else {
			winner = playerOne;
		}
		if (winner.name == "Yuno") {
			lblWinner.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/YunoWinner.png")));
			lblWinner.setText(winner.name);
		} else if (winner.name == "Yoruichi") {
			lblWinner.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/YoruichiWinner.png")));
			lblWinner.setText(winner.name);
		} else {
			lblWinner.setText(winner.name);
			lblWinner.setIcon(winner.characterImage);
		}
	}

	/**
	 * Creates LblEndGameTitle and sets properties.
	 * 
	 * @return
	 */
	private JLabel createLblEndGameTitle() {
		JLabel lblEndGameTitle = new JLabel("Winner!!!");
		lblEndGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndGameTitle.setFont(new Font("Dialog", Font.PLAIN, 35));
		lblEndGameTitle.setForeground(new Color(148, 0, 211));
		lblEndGameTitle.setBackground(new Color(148, 0, 211));
		return lblEndGameTitle;
	}

	// BATTLE SCREEN
	/********************************************************/

	/**
	 * Initializes the battle screen.
	 */
	private void initializeBattleScreen() {
		battleScreen.setLayout(new BorderLayout(0, 0));

		JPanel panelCharacterInfo = new JPanel();
		initializePanelCharcterInfo(panelCharacterInfo);
		battleScreen.add(panelCharacterInfo, BorderLayout.NORTH);

		battlePanel = new BattlePanel();
		battleScreen.add(battlePanel, BorderLayout.CENTER);

		/**
		 * 
		 * Collision detects if two pixels collide. If pixel collision occurs, health
		 * for damaged player is updated and a winner is checked.
		 * 
		 * @author SpencerR
		 *
		 */
		class Collision extends Thread {
			public void run() {
				while (true) {
					try {

						// ranged attack for player 2
						if (battlePanel.getP1XLocation() + 100 <= battlePanel.getP2RangeAttackLocation()
								&& battlePanel.getP1XLocation() + 150 >= battlePanel.getP2RangeAttackLocation()
								&& battlePanel.getP2RangeAttackYLocation() >= 300 && battlePanel.isP2RangeActive()) {
							playerTwo.meleeAttack(playerTwo, playerOne);
							battlePanel.removePlayer2RangedAttack();
							checkIsDead(playerOne);
							updateHealthLabels(playerOne, playerTwo);
							Thread.sleep(50);

						}

						// ranged attack for player 1
						if (battlePanel.getP2XLocation() + 100 <= battlePanel.getP1RangeAttackLocation()
								&& battlePanel.getP2XLocation() + 150 >= battlePanel.getP1RangeAttackLocation()
								&& battlePanel.getP1RangeAttackYLocation() >= 300 && battlePanel.isP1RangeActive()) {
							playerOne.meleeAttack(playerOne, playerTwo);
							battlePanel.removePlayer1RangedAttack();
							checkIsDead(playerTwo);
							updateHealthLabels(playerOne, playerTwo);
							Thread.sleep(50);
						}
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						System.out.println("Thread dead...");
						break;
					}

				}
				System.out.println("MyThread dead...");
			}
		}
		Collision c = new Collision();
		c.start();

		// adds the words RAMBLE!!! to the battle screen.
		JLabel rambleLabel = new JLabel("RAMBLE!!!");
		rambleLabel.setFont(new Font(null, Font.BOLD, 50));
		rambleLabel.setForeground(Color.WHITE);
		battlePanel.add(rambleLabel);

	}

	/**
	 * Damages playerTwo from playeOne's attack. PlayerOne could be either the true
	 * first player or second player.
	 * 
	 * @param playerOne
	 * @param playerTwo
	 */
	public void playerOneAttacked(Character playerOne, Character playerTwo) {
		playerTwo.meleeAttack(playerTwo, playerOne);
		checkIsDead(playerOne);
		updateHealthLabels(playerOne, playerTwo);
	}

	/**
	 * Checks if character is dead. Writes the loser to a list of losers in
	 * "Losers.txt". Prints the losers from "Losers.txt".
	 * 
	 * @param character
	 */
	public void checkIsDead(Character character) {
		if (character.isDead) {
			battlePanel.removePlayer1RangedAttack();
			battlePanel.removePlayer2RangedAttack();

			// write loser to file
			try (PrintWriter writer = new PrintWriter("src/Resources/Losers.txt")) {
				writer.printf("%s Round: %d%n", character.name, ++roundNum);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// read it and print out list of users for current session.
			try (BufferedReader reader = new BufferedReader(new FileReader("src/Resources/Losers.txt"))) {
				System.out.println("List of Losers: ");
				while (reader.ready()) {
					System.out.printf("%s%n", reader.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			updateWinnerPic();
			cardLayout.show(cards, "End Game Screen");
		}
	}

	/**
	 * Updates the GUI health labels for both characters.
	 * 
	 * @param playerOne
	 * @param playerTwo
	 */
	private void updateHealthLabels(Character playerOne, Character playerTwo) {
		lblC_1_Health.setText(Integer.toString(playerOne.health));
		lblC_2_Health.setText(Integer.toString(playerTwo.health));

		// change color for health
		if (playerOne.health <= 30)
			lblC_1_Health.setForeground(Color.RED);
		else
			lblC_1_Health.setForeground(Color.WHITE); // puts white color if game is restarted
		if (playerTwo.health <= 30)
			lblC_2_Health.setForeground(Color.RED);
		else
			lblC_2_Health.setForeground(Color.WHITE);

		lblCharacter_1_Name.setText(playerOne.name);
		lblCharacter_2_Name.setText(playerTwo.name);

	}

	/**
	 * Initializes the main menu.
	 */
	private void initializeMainMenu() {
		mainMenu.setLayout(new BorderLayout(0, 0));

		battleMusic = new Music();
		battleMusic.startMusic();

		JPanel panelTitle = new JPanel(); // upper section of panel
		panelTitle.setBackground(new Color(148, 0, 211));
		initializePanelTitle(panelTitle);
		mainMenu.add(panelTitle, BorderLayout.NORTH);

		panelButtons_1 = new JPanel(); // middle section of panel
		panelButtons_1.setBackground(new Color(148, 0, 211));
		JButton btnC_4 = initializePanelButtons(panelButtons_1);
		panelButtons_1.add(btnC_4);

		JButton btnStart = new JButton("start");
		btnStart.setBounds(0, 608, 800, 26);
		panelButtons_1.add(btnStart);

		JLabel p1ControlsLabel = createP1ControlsLabel();
		panelButtons_1.add(p1ControlsLabel);

		JLabel p2ControlsLabel = createP2ControlsLabel();
		panelButtons_1.add(p2ControlsLabel);
		btnStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) { // check if both
															// players have
															// selected
															// characters
				if (playerTwo != null) {
					cardLayout.show(cards, "Battle Screen");
					updateHealthLabels(playerOne, playerTwo); // set health and
					battlePanel.tm.start();
					battlePanel.setP1XLocation(0);
					battlePanel.setP2XLocation(550);

				} else {
					JFrame warning = null;
					if (warning == null) {
						warning = new JFrame();
					}
					warning.setVisible(true);
					int x = (int) frame.getLocationOnScreen().getX(); // get
																		// half
																		// the
																		// screen's
																		// width
					int y = (int) frame.getLocationOnScreen().getY(); // get
																		// half
																		// the
																		// screen's
																		// height
					warning.setLocation(x + 350, y + 350); // make up for the
															// size of the frame
					warning.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(warning, "Players must select a character!");
					warning.dispose();
				}
			}
		});
	}

	/**
	 * Creates player two's controls label and informs user of controls for player
	 * two.
	 * 
	 * @return
	 */
	private JLabel createP2ControlsLabel() {
		JLabel p2ControlsLabel = new JLabel(
				"<html>-PLAYER 2 CONTROLS-<br/><br/>L/ ' : Left/Right<br/>J: Ranged Attack<br/>");
		p2ControlsLabel.setForeground(new Color(240, 248, 255));
		p2ControlsLabel.setFont(new Font("DokChampa", Font.BOLD, 12));
		p2ControlsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		p2ControlsLabel.setVerticalAlignment(SwingConstants.TOP);
		p2ControlsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p2ControlsLabel.setBounds(400, 505, 400, 103);
		return p2ControlsLabel;
	}

	/**
	 * Creates player one's controls label and informs user of controls for player
	 * two.
	 * 
	 * @return
	 */
	private JLabel createP1ControlsLabel() {
		JLabel p1ControlsLabel = new JLabel(
				"<html>-PLAYER 1 CONTROLS-<br/><br/>A/D: Left/Right<br/>G: Ranged Attack<br/>");
		p1ControlsLabel.setForeground(new Color(240, 248, 255));
		p1ControlsLabel.setFont(new Font("DokChampa", Font.BOLD, 12));
		p1ControlsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		p1ControlsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p1ControlsLabel.setIconTextGap(0);
		p1ControlsLabel.setVerticalAlignment(SwingConstants.TOP);
		p1ControlsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1ControlsLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		p1ControlsLabel.setBounds(0, 505, 400, 103);
		return p1ControlsLabel;
	}

	/**
	 * Initializes the panel title.
	 * 
	 * @param panelTitle
	 */
	private void initializePanelTitle(JPanel panelTitle) {
		panelTitle.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblTitle = new JLabel("Waifu Ramble!!!");
		lblTitle.setForeground(new Color(240, 248, 255));
		lblTitle.setFont(new Font("DokChampa", Font.BOLD, 25));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitle);

		JLabel lblSelectCharacter = new JLabel("Choose your character!");
		lblSelectCharacter.setFont(new Font("DokChampa", Font.BOLD, 12));
		lblSelectCharacter.setForeground(new Color(240, 248, 255));
		lblSelectCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblSelectCharacter);
	}

	/**
	 * Creates the panel for the each characters control information to be
	 * displayed.
	 * 
	 * @param panelCharacterInfo
	 */
	private void initializePanelCharcterInfo(JPanel panelCharacterInfo) {
		JPanel panelCharacter1Info = createPanelCharacter1Info();
		panelCharacterInfo.add(panelCharacter1Info);
		JPanel panelCharacter2Info = createPanelCharacter2Info();
		panelCharacterInfo.add(panelCharacter2Info);
		panelCharacterInfo.setBackground(new Color(148, 0, 211));
	}

	/**
	 * Creates panel for character one control info.
	 * 
	 * @return
	 */
	private JPanel createPanelCharacter1Info() {
		JPanel panelCharacter1Info = new JPanel();
		panelCharacter1Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter1Info.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelCharacter1Info.setLayout(new GridLayout(2, 0, 0, 0));
		panelCharacter1Info.setBackground(new Color(148, 0, 211));

		lblCharacter_1_Name = new JLabel("character 1 name");
		lblCharacter_1_Name.setForeground(Color.WHITE);
		panelCharacter1Info.add(lblCharacter_1_Name);

		lblC_1_Health = new JLabel("c1 health");
		lblC_1_Health.setForeground(Color.WHITE);
		panelCharacter1Info.add(lblC_1_Health);
		return panelCharacter1Info;
	}

	/**
	 * Creates panel for character two control info.
	 * 
	 * @return
	 */
	private JPanel createPanelCharacter2Info() {
		JPanel panelCharacter2Info = new JPanel();
		panelCharacter2Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter2Info.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelCharacter2Info.setLayout(new GridLayout(2, 0, 0, 0));
		panelCharacter2Info.setBackground(new Color(148, 0, 211));

		lblCharacter_2_Name = new JLabel("character 2 name");
		panelCharacter2Info.add(lblCharacter_2_Name);
		lblCharacter_2_Name.setForeground(Color.WHITE);

		lblC_2_Health = new JLabel("c2 health");
		lblC_2_Health.setForeground(Color.WHITE);
		panelCharacter2Info.add(lblC_2_Health);
		return panelCharacter2Info;
	}

	/**
	 * Initializes the panel for Character selection.
	 * 
	 * @param panelButtons
	 * @return
	 */
	private JButton initializePanelButtons(JPanel panelButtons) {
		mainMenu.add(panelButtons, BorderLayout.CENTER);
		JButton btnC_4 = initializeCharacterButtons(panelButtons);
		return btnC_4;
	}

	/**
	 * Initializes the buttons for the Character selection.
	 * 
	 * @param panelButtons
	 * @return
	 */
	private JButton initializeCharacterButtons(JPanel panelButtons) {
		JButton btnC_1 = new JButton();
		btnC_1.setIconTextGap(0);
		btnC_1.setSize(new Dimension(200, 500));
		btnC_1.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/SaberSelectionCropped.png")));
		btnC_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasChosen) {
					playerOne = createSaber();
					hasChosen = true;
				} else {
					playerTwo = createSaber();
				}
			}
		});
		panelButtons_1.setLayout(null);
		panelButtons.add(btnC_1);

		JButton btnC_2 = new JButton();
		btnC_2.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/PrincessZeldaCropped.png")));
		btnC_2.setSize(new Dimension(200, 500));
		btnC_2.setBounds(200, 0, 200, 500);
		btnC_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasChosen) {
					playerOne = createZelda();
					hasChosen = true;
				} else {
					playerTwo = createZelda();
				}
			}
		});
		panelButtons.add(btnC_2);

		JButton btnC_3 = new JButton();
		btnC_3.setIconTextGap(0);
		btnC_3.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/YunoSelectionCropped.png")));
		btnC_3.setSize(new Dimension(200, 500));
		btnC_3.setBounds(400, 0, 200, 500);
		btnC_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasChosen) {
					playerOne = createYuno();
					hasChosen = true;
				} else {
					playerTwo = createYuno();
				}
			}
		});
		panelButtons.add(btnC_3);

		JButton btnC_4 = new JButton();
		btnC_4.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/YoruichiiSelectionCropped.png")));
		btnC_4.setSize(new Dimension(200, 500));
		btnC_4.setBounds(600, 0, 200, 500);
		btnC_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasChosen) {
					playerOne = createYoruichi();
					hasChosen = true;
				} else {
					playerTwo = createYoruichi();
				}
			}
		});
		return btnC_4;
	}

	/**
	 * Uses the builder form Charcter.java to created character named "Yuno".
	 * 
	 * @return
	 */
	public Character createYuno() {
		return new Character.Builder().name("Yuno").health(100).meleeAttack("chop").defend("defend")
				.rangedAttack("throw axe")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/YunoSelectionCropped.png")))
				.build();
	}

	/**
	 * Uses the builder form Charcter.java to created character named "Saber".
	 * 
	 * @return
	 */
	public Character createSaber() {
		return new Character.Builder().name("Saber").health(100).meleeAttack("slash").defend("defend")
				.rangedAttack("slash wave")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/SaberSelectionCropped.png")))
				.build();
	}

	/**
	 * Uses the builder form Charcter.java to created character named "Zelda".
	 * 
	 * @return
	 */
	public Character createZelda() {
		return new Character.Builder().name("Zelda").health(100).meleeAttack("slash").defend("defend")
				.rangedAttack("lullaby")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/PrincessZeldaCropped.png")))
				.build();
	}

	/**
	 * Uses the builder form Charcter.java to created character named "Yoruichi".
	 * 
	 * @return
	 */
	public Character createYoruichi() {
		return new Character.Builder().name("Yoruichi").health(100).meleeAttack("scratch").defend("defend")
				.rangedAttack("cat throw")
				.characterImage(
						new ImageIcon(WaifuRamble.class.getResource("/Resources/YoruichiiSelectionCropped.png")))
				.build();
	}

	/**
	 * Creates and shows the main GUI.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("Waifu Ramble!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 600);
		frame.setLocation(Window.WIDTH + 300, Window.HEIGHT);

		// Create and set up the content pane.
		WaifuRamble demo = new WaifuRamble();
		demo.addComponentToPane(frame.getContentPane());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Runs the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
