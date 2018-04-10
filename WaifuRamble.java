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

public class WaifuRamble
{
	public JPanel cards; // a panel that uses CardLayout
	private CardLayout cardLayout; // CardLayout object
	private JPanel mainMenu; // card 1
	private JPanel battleScreen; // card 2
	private static JFrame frame;
	private JPanel endGameScreen; // card 3
	private JPanel panelButtons_1;

	public static Character playerOne;
	public static Character playerTwo;
	private JLabel lblCharacter_1_Name;
	private JLabel lblC_1_Health;
	private JLabel lblCharacter_2_Name;
	private JLabel lblC_2_Health;

	private boolean hasChosen = false;
	private JLabel lblWinner;

	public void addComponentToPane(Container pane)
	{
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

	private void initializeEndGameScreen()
	{
		endGameScreen.setLayout(new BorderLayout(0, 0));

		JLabel lblEndGameTitle = createLblEndGameTitle();
		endGameScreen.add(lblEndGameTitle, BorderLayout.NORTH);

		JLabel lblWinner = createLblWinner();
		endGameScreen.add(lblWinner, BorderLayout.CENTER);

		JPanel btnPanelEndGame = new JPanel();
		endGameScreen.add(btnPanelEndGame, BorderLayout.SOUTH);

		JButton btnRestart = createBtnRestart();
		btnPanelEndGame.add(btnRestart);

		JButton btnExit = createBtnExit();
		btnPanelEndGame.add(btnExit);

	}

	private JButton createBtnExit()
	{
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		return btnExit;
	}

	private JButton createBtnRestart()
	{
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				playerOne.health = 100; // reset health
				playerTwo.health = 100;
				hasChosen = false; // reset characters
				cardLayout.show(cards, "Main Menu");
			}
		});
		return btnRestart;
	}

	private JLabel createLblWinner()
	{
		lblWinner = new JLabel("Alloted spot to show who the winner is...");
		lblWinner.setFont(new Font("Dialog", Font.BOLD, 57));
		lblWinner.setHorizontalAlignment(SwingConstants.CENTER);
		return lblWinner;
	}

	private void updateWinnerPic()
	{
		Character winner;
		if (playerOne.isDead)
			winner = playerTwo;
		else
		{
			winner = playerOne;
		}
		lblWinner.setText(winner.name);
		lblWinner.setIcon(winner.characterImage); // set to
													// winner.characterImage
	}

	private JLabel createLblEndGameTitle()
	{
		JLabel lblEndGameTitle = new JLabel("Winner!!!");
		lblEndGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndGameTitle.setFont(new Font("Dialog", Font.PLAIN, 35));
		return lblEndGameTitle;
	}

	/********************************************************/

	private void initializeBattleScreen()
	{
		battleScreen.setLayout(new BorderLayout(0, 0));

		JPanel panelCharacterInfo = new JPanel();
		initializePanelCharcterInfo(panelCharacterInfo);
		battleScreen.add(panelCharacterInfo, BorderLayout.NORTH);

		BattlePanel battlePanel = new BattlePanel();
		battleScreen.add(battlePanel, BorderLayout.CENTER);

		/**
		 * JButton btnAttackPlayerOneTest = new JButton("Attack Player One
		 * Test"); btnAttackPlayerOneTest.addActionListener(new ActionListener()
		 * { public void actionPerformed(ActionEvent e) {
		 * playerOne.meleeAttack(playerOne, playerTwo); // player one is //
		 * attacking // player two checkIsDead(playerTwo);
		 * updateHealthLabels(playerOne, playerTwo); } });
		 * battlePanel.add(btnAttackPlayerOneTest);
		 * 
		 * JButton btnAttack = new JButton("AttackPlayerTwoTest");
		 * btnAttack.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { playerTwo.meleeAttack(playerTwo,
		 * playerOne); // player two is // attacking // player one
		 * checkIsDead(playerOne); updateHealthLabels(playerOne, playerTwo); }
		 * }); battlePanel.add(btnAttack);
		 **/
	}

	protected void checkIsDead(Character character)
	{
		if (character.isDead)
		{
			updateWinnerPic();
			cardLayout.show(cards, "End Game Screen");
		}
	}

	private void updateHealthLabels(Character playerOne2, Character playerTwo2)
	{
		lblC_1_Health.setText(Integer.toString(playerOne2.health));
		lblCharacter_1_Name.setText(playerOne2.name);
		lblC_2_Health.setText(Integer.toString(playerTwo2.health));
		lblCharacter_2_Name.setText(playerTwo2.name);
	}

	private void initializeMainMenu()
	{
		mainMenu.setLayout(new BorderLayout(0, 0));

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
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{ // check if both players have selected characters
				if (playerTwo != null)
				{
					cardLayout.show(cards, "Battle Screen");
					updateHealthLabels(playerOne, playerTwo); // set health and
																// names for
																// players
																// chosen
				} else
				{
					JFrame warning = null;
					if (warning == null)
					{
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

	private JLabel createP2ControlsLabel()
	{
		JLabel p2ControlsLabel = new JLabel(
				"<html>-PLAYER 2 CONTROLS-<br/><br/>P: Jump | L/ ' : Left/Right<br/>4: Melee Attack<br/>5: Ranged Attack<br/>8: Super</html>");
		p2ControlsLabel.setForeground(new Color(240, 248, 255));
		p2ControlsLabel.setFont(new Font("DokChampa", Font.BOLD, 12));
		p2ControlsLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		p2ControlsLabel.setVerticalAlignment(SwingConstants.TOP);
		p2ControlsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		p2ControlsLabel.setBounds(400, 505, 400, 103);
		return p2ControlsLabel;
	}

	private JLabel createP1ControlsLabel()
	{
		JLabel p1ControlsLabel = new JLabel(
				"<html>-PLAYER 1 CONTROLS-<br/><br/>W: Jump | A/D: Left/Right<br/>G: Melee Attack<br/>H: Ranged Attack<br/>Y: Super</html>");
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

	private void initializePanelTitle(JPanel panelTitle)
	{
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

	private void initializePanelCharcterInfo(JPanel panelCharacterInfo)
	{
		JPanel panelCharacter1Info = createPanelCharacter1Info();
		panelCharacterInfo.add(panelCharacter1Info);
		JPanel panelCharacter2Info = createPanelCharacter2Info();
		panelCharacterInfo.add(panelCharacter2Info);
	}

	private JPanel createPanelCharacter1Info()
	{
		JPanel panelCharacter1Info = new JPanel();
		panelCharacter1Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter1Info.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelCharacter1Info.setLayout(new GridLayout(2, 0, 0, 0));

		lblCharacter_1_Name = new JLabel("character 1 name");
		panelCharacter1Info.add(lblCharacter_1_Name);

		lblC_1_Health = new JLabel("c1 health");
		panelCharacter1Info.add(lblC_1_Health);
		return panelCharacter1Info;
	}

	private JPanel createPanelCharacter2Info()
	{
		JPanel panelCharacter2Info = new JPanel();
		panelCharacter2Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter2Info.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelCharacter2Info.setLayout(new GridLayout(2, 0, 0, 0));

		lblCharacter_2_Name = new JLabel("character 2 name");
		panelCharacter2Info.add(lblCharacter_2_Name);

		lblC_2_Health = new JLabel("c2 health");
		panelCharacter2Info.add(lblC_2_Health);
		return panelCharacter2Info;
	}

	private JButton initializePanelButtons(JPanel panelButtons)
	{
		mainMenu.add(panelButtons, BorderLayout.CENTER);
		JButton btnC_4 = initializeCharacterButtons(panelButtons);
		return btnC_4;
	}

	private JButton initializeCharacterButtons(JPanel panelButtons)
	{
		JButton btnC_1 = new JButton();
		btnC_1.setIconTextGap(0);
		btnC_1.setSize(new Dimension(200, 500));
		btnC_1.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/SaberSelectionCropped.png")));
		btnC_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!hasChosen)
				{
					playerOne = createSaber();
					hasChosen = true;
				} else
				{
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
		btnC_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!hasChosen)
				{
					playerOne = createZelda();
					hasChosen = true;
				} else
				{
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
		btnC_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!hasChosen)
				{
					playerOne = createYuno();
					hasChosen = true;
				} else
				{
					playerTwo = createYuno();
				}
			}
		});
		panelButtons.add(btnC_3);

		JButton btnC_4 = new JButton();
		btnC_4.setIcon(new ImageIcon(WaifuRamble.class.getResource("/Resources/YoruichiiSelectionCropped.png")));
		btnC_4.setSize(new Dimension(200, 500));
		btnC_4.setBounds(600, 0, 200, 500);
		btnC_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!hasChosen)
				{
					playerOne = createYoruichi();
					hasChosen = true;
				} else
				{
					playerTwo = createYoruichi();
				}
			}
		});
		return btnC_4;
	}

	public Character createYuno()
	{
		return new Character.Builder().name("Yuno").health(100).meleeAttack("chop").defend("defend")
				.rangedAttack("throw axe")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/YunoSelectionCropped.png")))
				.build();
	}

	public Character createSaber()
	{
		return new Character.Builder().name("Saber").health(100).meleeAttack("slash").defend("defend")
				.rangedAttack("slash wave")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/SaberSelectionCropped.png")))
				.build();
	}

	public Character createZelda()
	{
		return new Character.Builder().name("Zelda").health(100).meleeAttack("slash").defend("defend")
				.rangedAttack("lullaby")
				.characterImage(new ImageIcon(WaifuRamble.class.getResource("/Resources/PrincessZeldaCropped.png")))
				.build();
	}

	public Character createYoruichi()
	{
		return new Character.Builder().name("Yoruichi").health(100).meleeAttack("scratch").defend("defend")
				.rangedAttack("cat throw")
				.characterImage(
						new ImageIcon(WaifuRamble.class.getResource("/Resources/YoruichiiSelectionCropped.png")))
				.build();
	}

	/************************************************************
	 * Create the GUI and show it.
	 */
	private static void createAndShowGUI()
	{
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

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex)
		{
			ex.printStackTrace();
		} catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		} catch (InstantiationException ex)
		{
			ex.printStackTrace();
		} catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
		}
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				createAndShowGUI();
			}
		});
	}
}
