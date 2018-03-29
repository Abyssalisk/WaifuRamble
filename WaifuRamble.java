package game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class WaifuRamble implements ItemListener {
	public JPanel cards; // a panel that uses CardLayout
	private CardLayout c1;
	private JPanel mainMenu; // card 1
	private JPanel battleScreen; // card 2
	// private JPanel endGameScreen; // card 3

	public void addComponentToPane(Container pane) {

		// Create the "cards".
		mainMenu = new JPanel(); // card 1
		initializeMainMenu();
		battleScreen = new JPanel(); // card 2
		initializeBattleScreen();
		// endGameScreen = new JPanel(); // card 3

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(800, 600));
		cards.add(mainMenu, "Main Menu");
		cards.add(battleScreen, "Battle Screen");

		pane.add(cards, BorderLayout.CENTER);
	}

	/********************************************************/

	private void initializeBattleScreen() {
		battleScreen.setLayout(new BorderLayout(0, 0));

		JPanel panelCharacterInfo = new JPanel();
		initializePanelCharcterInfo(panelCharacterInfo);
		battleScreen.add(panelCharacterInfo, BorderLayout.NORTH);

		JPanel panelCenter = new JPanel();
		battleScreen.add(panelCenter, BorderLayout.CENTER);
	}

	private void initializeMainMenu() {
		mainMenu.setLayout(new BorderLayout(0, 0));

		JPanel panelTitle = new JPanel(); // upper section of panel
		initializePanelTitle(panelTitle);
		mainMenu.add(panelTitle, BorderLayout.NORTH);

		JPanel panelButtons = new JPanel(); // middle section of panel
		JButton btnC_4 = initializePanelButtons(panelButtons);
		panelButtons.add(btnC_4);

		JPanel panelLower = new JPanel(); // lower section of panel
		initializePanelLower(panelLower);
		mainMenu.add(panelLower, BorderLayout.SOUTH);
	}

	private void initializePanelTitle(JPanel panelTitle) {
		panelTitle.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblTitle = new JLabel("Waifu Ramble!!!");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 25));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblTitle);

		JLabel lblSelectCharacter = new JLabel("Select a Character");
		lblSelectCharacter.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblSelectCharacter);
	}

	private void initializePanelCharcterInfo(JPanel panelCharacterInfo) {
		JPanel panelCharacter1Info = createPanelCharacter1Info();
		panelCharacterInfo.add(panelCharacter1Info);
		JPanel panelCharacter2Info = createPanelCharacter2Info();
		panelCharacterInfo.add(panelCharacter2Info);
	}

	private JPanel createPanelCharacter1Info() {
		JPanel panelCharacter1Info = new JPanel();
		panelCharacter1Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter1Info.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelCharacter1Info.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblCharacter_1_Name = new JLabel("character 1 name");
		panelCharacter1Info.add(lblCharacter_1_Name);

		JLabel lblC_1_Health = new JLabel("c1 health");
		panelCharacter1Info.add(lblC_1_Health);
		return panelCharacter1Info;
	}

	private JPanel createPanelCharacter2Info() {
		JPanel panelCharacter2Info = new JPanel();
		panelCharacter2Info.setBorder(new EmptyBorder(0, 0, 0, 250));
		panelCharacter2Info.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelCharacter2Info.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblCharacter_2_Name = new JLabel("character 2 name");
		panelCharacter2Info.add(lblCharacter_2_Name);

		JLabel lblC_2_Health = new JLabel("c2 health");
		panelCharacter2Info.add(lblC_2_Health);
		return panelCharacter2Info;
	}

	private void initializePanelLower(JPanel panelLower) {
		panelLower.setLayout(new GridLayout(2, 0, 10, 10));

		JLabel lblInstruction = new JLabel("instruction");
		lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
		panelLower.add(lblInstruction);

		JButton btnStart = new JButton("start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c1.next(cards);
			}
		});

		panelLower.add(btnStart);
	}

	private JButton initializePanelButtons(JPanel panelButtons) {
		mainMenu.add(panelButtons, BorderLayout.CENTER);
		panelButtons.setLayout(new GridLayout(1, 0, 0, 0));
		JButton btnC_4 = initializeCharacterButtons(panelButtons);
		return btnC_4;
	}

	private JButton initializeCharacterButtons(JPanel panelButtons) {
		JButton btnC_1 = new JButton("c1");
		btnC_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelButtons.add(btnC_1);

		JButton btnC_2 = new JButton("c2");
		btnC_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelButtons.add(btnC_2);

		JButton btnC_3 = new JButton("c3");
		btnC_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelButtons.add(btnC_3);

		JButton btnC_4 = new JButton("c4");
		btnC_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		return btnC_4;
	}

	public void itemStateChanged(ItemEvent evt) {
		c1 = (CardLayout) (cards.getLayout());
		c1.show(cards, (String) evt.getItem());
	}

	/************************************************************
	 * Create the GUI and show it.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Waifu Ramble!!!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 800, 600);

		// Create and set up the content pane.
		WaifuRamble demo = new WaifuRamble();
		demo.addComponentToPane(frame.getContentPane());

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}