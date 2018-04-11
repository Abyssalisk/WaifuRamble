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
	Timer tm = new Timer(3, this);
	private boolean p1LeftPressed = false;
	private boolean p2LeftPressed = false;
	private boolean p1RightPressed = false;
	private boolean p2RightPressed = false;
	private int p1XLocation = 0, p1XVelocity = 0;
	private int p2XLocation = 550, p2XVelocity = 0;
	// No jumping since I cut off at the knee to match up the images.

	/**
	 * Starts the listeners and timer for the battle.
	 */
	public BattlePanel()
	{
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	/**
	 * Paints the characters sprites onto the battlefield.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ImageIcon p1Left = new ImageIcon(WaifuRamble.class.getResource(getPlayerOneImage()));
		ImageIcon p2Left = new ImageIcon(WaifuRamble.class.getResource(getPlayerTwoImage()));
		ImageIcon p1Right = new ImageIcon(WaifuRamble.class.getResource(getPlayerOneImageR()));
		ImageIcon p2Right = new ImageIcon(WaifuRamble.class.getResource(getPlayerTwoImageR()));

		if (p1XLocation < p2XLocation)
		{
			p1Right.paintIcon(this, g, p1XLocation, 360);
		} else
			p1Left.paintIcon(this, g, p1XLocation, 360);

		if (p2XLocation > p1XLocation)
		{
			p2Left.paintIcon(this, g, p2XLocation, 360);
		} else
			p2Right.paintIcon(this, g, p2XLocation, 360);

		requestFocus();
	}

	/**
	 * Moves the sprite's location on the JPanel. Added functionality to make
	 * controls smooth.
	 */
	public void actionPerformed(ActionEvent e)
	{
		this.p1XLocation += p1XVelocity;
		this.p2XLocation += p2XVelocity;

		// =================== P1 Smooth code =========================
		if (!p1LeftPressed && !p1RightPressed)
		{
			p1XVelocity = 0;
		} else if (!p1LeftPressed && p1RightPressed)
		{
			p1XVelocity = 1;
		} else if (p1LeftPressed && !p1RightPressed)
		{
			p1XVelocity = -1;
		}

		// =================== P2 Smooth code ==========================
		if (!p2LeftPressed && !p2RightPressed)
		{
			p2XVelocity = 0;
		} else if (!p2LeftPressed && p2RightPressed)
		{
			p2XVelocity = 1;
		} else if (p2LeftPressed && !p2RightPressed)
		{
			p2XVelocity = -1;
		}

		repaint();
	}

	/**
	 * Performs the sprites actions depending on what key is pressed. Toggles
	 * the movement values for smoother controls.
	 */
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			p1XVelocity = -1;
			p1LeftPressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			p1XVelocity = 1;
			p1RightPressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_L)
		{
			p2XVelocity = -1;
			p2LeftPressed = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_QUOTE)
		{
			p2XVelocity = 1;
			p2RightPressed = true;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	/**
	 * Toggles the pressed down booleans for the two characters movement
	 * controls.
	 */
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_A:
			p1LeftPressed = false;
			break;
		case KeyEvent.VK_D:
			p1RightPressed = false;
			break;
		case KeyEvent.VK_L:
			p2LeftPressed = false;
			break;
		case KeyEvent.VK_QUOTE:
			p2RightPressed = false;
			break;
		}
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
	 * Selects the Battle Sprite for Player two.
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

	/**
	 * Selects the Battle Sprite for player one facing right.
	 * 
	 * @return
	 */
	public static String getPlayerOneImageR()
	{
		switch (WaifuRamble.playerOne.name)
		{
		case "Saber":
			return "/Resources/SaberBattleR.png";
		case "Zelda":
			return "/Resources/ZeldaBattleR.png";
		case "Yuno":
			return "/Resources/YunoBattleR.png";
		case "Yoruichi":
			return "/Resources/YoruichiBattleR.png";
		default:
			return "Buff Magikarp";
		}
	}

	/**
	 * Selects the Battle Sprite for Player two facing right.
	 * 
	 * @return
	 */
	public static String getPlayerTwoImageR()
	{
		switch (WaifuRamble.playerTwo.name)
		{
		case "Saber":
			return "/Resources/SaberBattleR.png";
		case "Zelda":
			return "/Resources/ZeldaBattleR.png";
		case "Yuno":
			return "/Resources/YunoBattleR.png";
		case "Yoruichi":
			return "/Resources/YoruichiBattleR.png";
		default:
			return "Buff Magikarp";
		}
	}
}
