package game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BattlePanel extends JPanel implements ActionListener, KeyListener
{
	Timer tm = new Timer(5, this);
	private int p1XLocation = 0, p1XVelocity = 0;
	private int p2XLocation = 550, p2XVelocity = 0; // Not going to mess with
													// jumping
	// since I cut off all their knees.

	public BattlePanel()
	{
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ImageIcon p1 = new ImageIcon(WaifuRamble.class.getResource(getPlayerOneImage()));
		ImageIcon p2 = new ImageIcon(WaifuRamble.class.getResource(getPlayerTwoImage()));

		p1.paintIcon(this, g, p1XLocation, 360);
		p2.paintIcon(this, g, p2XLocation, 360);
	}

	/**
	 * Moves the sprite's location on the JPanel.
	 */
	public void actionPerformed(ActionEvent e)
	{
		this.p1XLocation += p1XVelocity;
		this.p2XLocation += p2XVelocity;
		repaint();
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			p1XVelocity = -1;
		}

		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			p1XVelocity = 1;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
		p1XVelocity = 0;
		p2XVelocity = 0;
	}

	/**
	 * Selects the Battle Sprite for player one.
	 * 
	 * @return
	 */
	public static String getPlayerOneImage()
	{
		switch (WaifuRamble.playerOne.name)
		{
		case "Saber":
			return "/Resources/SaberBattle.png";
		case "Zelda":
			return "/Resources/ZeldaBattle.png";
		case "Yuno":
			return "/Resources/YunoBattle.png";
		case "Yoruichi":
			return "/Resources/YoruichiBattle.png";
		default:
			return "Buff Magikarp";
		}
	}

	/**
	 * Selects the Battle Sprite for PLayer two.
	 * 
	 * @return
	 */
	public static String getPlayerTwoImage()
	{
		switch (WaifuRamble.playerTwo.name)
		{
		case "Saber":
			return "/Resources/SaberBattle.png";
		case "Zelda":
			return "/Resources/ZeldaBattle.png";
		case "Yuno":
			return "/Resources/YunoBattle.png";
		case "Yoruichi":
			return "/Resources/YoruichiBattle.png";
		default:
			return "Buff Magikarp";
		}
	}
}
