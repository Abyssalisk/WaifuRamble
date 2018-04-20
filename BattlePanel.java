package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Gavin Rosenvall
 *
 */
@SuppressWarnings("serial")
public class BattlePanel extends JPanel implements ActionListener, KeyListener
{
	/**
	 * Lots and lots of useful fields.
	 */
	Timer tm = new Timer(3, this);
	private boolean p1LeftPressed = false;
	private boolean p2LeftPressed = false;
	private boolean p1RightPressed = false;
	private boolean p2RightPressed = false;
	private boolean p1RangeActive = false;
	private boolean p2RangeActive = false;
	private int p1XLocation = 0, p1XVelocity = 0;
	private int p2XLocation = 550, p2XVelocity = 0;
	private int p1RangeAttackXLocation = p1XLocation + 80;
	private int p2RangeAttackXLocation = p2XLocation + 80;
	private int p1RangeAttackYLocation = 500;
	private int p2RangeAttackYLocation = 500;
	private int p1XRangeVelocity = 0;
	private int p2XRangeVelocity = 0;
	private int p1YRangeVelocity = 0;
	private int p2YRangeVelocity = 0;
	private Collection<ImageIcon> p1RangeIcons = new ArrayList<>();
	private Collection<ImageIcon> p2RangeIcons = new ArrayList<>();
	private Random rand = new Random();
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
	 * Paints the characters' sprites onto the battlefield.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		ImageIcon p1Left = new ImageIcon(WaifuRamble.class.getResource(getPlayerOneImage()));
		ImageIcon p2Left = new ImageIcon(WaifuRamble.class.getResource(getPlayerTwoImage()));
		ImageIcon p1Right = new ImageIcon(WaifuRamble.class.getResource(getPlayerOneImageR()));
		ImageIcon p2Right = new ImageIcon(WaifuRamble.class.getResource(getPlayerTwoImageR()));
		ImageIcon bg = new ImageIcon(WaifuRamble.class.getResource("/Resources/bg.png"));
		bg.paintIcon(this, g, 0, 0);

		/**
		 * Assigns the icon for either left or right facing depending on
		 * location.
		 */
		if (p1XLocation < p2XLocation)
		{
			p1Right.paintIcon(this, g, p1XLocation, 360);
		} else
			p1Left.paintIcon(this, g, p1XLocation, 360);

		/**
		 * Assigns the icon for either left or right facing depending on
		 * location.
		 */
		if (p2XLocation > p1XLocation)
		{
			p2Left.paintIcon(this, g, p2XLocation, 360);
		} else
			p2Right.paintIcon(this, g, p2XLocation, 360);

		/**
		 * fires the ranged attack and checks for either an out of bounds or
		 * collision with the other player.
		 */
		if (p1RangeActive)
		{
			g.setColor(Color.RED);
			g.fillOval(p1RangeAttackXLocation, p1RangeAttackYLocation, 40, 40);
			if (p1RangeAttackXLocation < -20 || p1RangeAttackXLocation > 820)
			{
				p1XRangeVelocity = -p1XRangeVelocity;
			}
			if (p1RangeAttackYLocation < -20 || p1RangeAttackYLocation > 720)
			{
				p1YRangeVelocity = -p1YRangeVelocity;
			}
			if (p1RangeAttackXLocation >= p2RangeAttackXLocation - 40
					&& p1RangeAttackXLocation <= p2RangeAttackXLocation + 40
					&& p1RangeAttackYLocation <= p2RangeAttackYLocation + 40
					&& p1RangeAttackYLocation >= p2RangeAttackYLocation - 40 && p2RangeActive)
			{
				removePlayer1RangedAttack();
				removePlayer2RangedAttack();
			}
		} else
		{
			p1RangeAttackXLocation = getP1XLocation() + 80;
			p1RangeAttackYLocation = 500;
		}

		/**
		 * fires the ranged attack and checks for either an out of bounds or
		 * collision with the other player.
		 */
		if (p2RangeActive)
		{
			g.setColor(Color.BLUE);
			g.fillOval(p2RangeAttackXLocation, p2RangeAttackYLocation, 40, 40);
			if (p2RangeAttackXLocation < -20 || p2RangeAttackXLocation > 820)
			{
				p2XRangeVelocity = -p2XRangeVelocity;
			}
			if (p2RangeAttackYLocation < -20 || p2RangeAttackYLocation > 720)
			{
				p2YRangeVelocity = -p2YRangeVelocity;
			}
			if (p2RangeAttackXLocation >= p1RangeAttackXLocation - 40
					&& p2RangeAttackXLocation <= p1RangeAttackXLocation + 40
					&& p2RangeAttackYLocation >= p1RangeAttackYLocation - 40
					&& p2RangeAttackYLocation <= p1RangeAttackYLocation + 40 && p1RangeActive)
			{
				removePlayer1RangedAttack();
				removePlayer2RangedAttack();
			}
		} else
		{
			p2RangeAttackXLocation = getP2XLocation() + 80;
			p2RangeAttackYLocation = 500;
		}

		/**
		 * Puts focus on the BattlePanel so the listeners can react.
		 */
		requestFocus();
	}

	public void setP1RangeAttackLocation(int p1RangeAttackLocation)
	{
		this.p1RangeAttackXLocation = p1RangeAttackLocation;
	}

	public void setP2RangeAttackLocation(int p2RangeAttackLocation)
	{
		this.p2RangeAttackXLocation = p2RangeAttackLocation;
	}

	public int getP1XLocation()
	{
		return p1XLocation;
	}

	public int getP2XLocation()
	{
		return p2XLocation;
	}

	public int getP1RangeAttackLocation()
	{
		return p1RangeAttackXLocation;
	}

	public int getP2RangeAttackLocation()
	{
		return p2RangeAttackXLocation;
	}

	public void setP1XLocation(int p1xLocation)
	{
		p1XLocation = p1xLocation;
	}

	public void setP2XLocation(int p2xLocation)
	{
		p2XLocation = p2xLocation;
	}

	public int getP1RangeAttackYLocation()
	{
		return p1RangeAttackYLocation;
	}

	public int getP2RangeAttackYLocation()
	{
		return p2RangeAttackYLocation;
	}

	public boolean isP1RangeActive()
	{
		return p1RangeActive;
	}

	public boolean isP2RangeActive()
	{
		return p2RangeActive;
	}

	public void setP1RangeActive(boolean p1RangeActive)
	{
		this.p1RangeActive = p1RangeActive;
	}

	public void setP2RangeActive(boolean p2RangeActive)
	{
		this.p2RangeActive = p2RangeActive;
	}

	/**
	 * removes player twos' ranged attack. Checks the direction the player is
	 * facing to load the next attack.
	 */
	public void removePlayer2RangedAttack()
	{
		p2XRangeVelocity = 0;
		p2YRangeVelocity = 0;
		p2RangeAttackXLocation = p2XLocation + 125;
		p2RangeAttackYLocation = 500;
		p2RangeActive = false;
	}

	/**
	 * removes' player ones' ranged attack. Checks the direction the player is
	 * facing to load the next attack.
	 */
	public void removePlayer1RangedAttack()
	{
		p1XRangeVelocity = 0;
		p1YRangeVelocity = 0;
		p1RangeAttackXLocation = p1XLocation + 125;
		p1RangeAttackYLocation = 500;
		p1RangeActive = false;
	}

	/**
	 * Moves the sprite's location on the JPanel. Added functionality to make
	 * controls smooth.
	 */
	public void actionPerformed(ActionEvent e)
	{
		this.p1XLocation += p1XVelocity;
		this.p2XLocation += p2XVelocity;
		this.p1RangeAttackXLocation += p1XRangeVelocity;
		this.p1RangeAttackYLocation += p1YRangeVelocity;
		this.p2RangeAttackXLocation += p2XRangeVelocity;
		this.p2RangeAttackYLocation += p2YRangeVelocity;

		// =================== P1 Smooth code =========================
		if (!p1LeftPressed && !p1RightPressed)
		{
			p1XVelocity = 0;
		} else if (!p1LeftPressed && p1RightPressed)
		{
			if (p1XLocation + 250 <= 820)
			{
				p1XVelocity = 1;
			} else
				p1XVelocity = 0;
		} else if (p1LeftPressed && !p1RightPressed)
		{
			if (p1XLocation >= -20)
			{
				p1XVelocity = -1;
			} else
				p1XVelocity = 0;
		}

		// =================== P2 Smooth code ==========================
		if (!p2LeftPressed && !p2RightPressed)
		{
			p2XVelocity = 0;
		} else if (!p2LeftPressed && p2RightPressed)
		{
			if (p2XLocation + 250 <= 820)
			{
				p2XVelocity = 1;
			} else
				p2XVelocity = 0;
		} else if (p2LeftPressed && !p2RightPressed)
		{
			if (p2XLocation >= -20)
			{
				p2XVelocity = -1;
			} else
				p2XVelocity = 0;
		}

		/**
		 * updates the battle screen.
		 */
		repaint();
	}

	/**
	 * Performs the Sprites' actions depending on what key is pressed. Toggles
	 * the movement values for smoother controls.
	 */
	public void keyPressed(KeyEvent e)
	{
		// player 1 left
		if (e.getKeyCode() == KeyEvent.VK_A)
		{
			p1XVelocity = -1;
			p1LeftPressed = true;
		}

		// player 1 right
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			p1XVelocity = 1;
			p1RightPressed = true;
		}

		/**
		 * Checks if the player is facing right or left to determine the ranged
		 * attacks' trajectory. Also activates the p1RangeActive boolean field
		 * to limit 1 attack.
		 */
		if (e.getKeyCode() == KeyEvent.VK_H)
		{
			if (p1XLocation < p2XLocation)
			{
				p1XRangeVelocity = rand.nextInt(4) + 1;
				p1YRangeVelocity = rand.nextInt(8) - 4;
			} else
			{
				p1XRangeVelocity = rand.nextInt(5) - 4;
				p1YRangeVelocity = rand.nextInt(8) - 4;
			}
			p1RangeActive = true;
		}

		// player 2 left
		if (e.getKeyCode() == KeyEvent.VK_L)
		{
			p2XVelocity = -1;
			p2LeftPressed = true;
		}

		// player 2 right
		if (e.getKeyCode() == KeyEvent.VK_QUOTE)
		{
			p2XVelocity = 1;
			p2RightPressed = true;
		}

		/**
		 * Checks if the player is facing right or left to determine the ranged
		 * attacks' trajectory. Also activates the p2RangeActive boolean field
		 * to limit 1 attack.
		 */
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD5)
		{
			if (p2XLocation < p1XLocation)
			{
				p2XRangeVelocity = rand.nextInt(4) + 1;
				p2YRangeVelocity = rand.nextInt(8) - 4;
			} else
			{
				p2XRangeVelocity = rand.nextInt(5) - 4;
				p2YRangeVelocity = rand.nextInt(8) - 4;
			}
			p2RangeActive = true;
		}
	}

	/**
	 * Necessary to satisfy the Key Listener Interface.
	 */
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
	 * Selects the Battle Sprite for player two facing right.
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
