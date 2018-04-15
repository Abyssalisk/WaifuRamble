package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	private int p1RangeAttackLocation = p1XLocation;
	private int p2RangeAttackLocation = p2XLocation + 250;
	private int p1RangeVelocity = 0;
	private int p2RangeVelocity = 0;
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
			g.fillOval(p1RangeAttackLocation, 500, 40, 40);
			if (p1RangeAttackLocation < 0 || p1RangeAttackLocation > 800)
			{
				removePlayer1RangedAttack();
			}
			if (p1RangeAttackLocation >= p2RangeAttackLocation - 20
					&& p1RangeAttackLocation <= p2RangeAttackLocation + 20)
			{
				removePlayer1RangedAttack();
				removePlayer2RangedAttack();
			}
			if(p1RangeAttackLocation == p2XLocation) {
				removePlayer1RangedAttack();
				
			}
		}

		/**
		 * fires the ranged attack and checks for either an out of bounds or
		 * collision with the other player.
		 */
		if (p2RangeActive)
		{
			g.setColor(Color.BLUE);
			g.fillOval(p2RangeAttackLocation, 500, 40, 40);
			if (p2RangeAttackLocation < 0 || p2RangeAttackLocation > 800)
			{
				removePlayer2RangedAttack();
			}
			if (p2RangeAttackLocation >= p1RangeAttackLocation - 20
					&& p2RangeAttackLocation <= p1RangeAttackLocation + 20)
			{
				removePlayer1RangedAttack();
				removePlayer2RangedAttack();
			}
			if(p2RangeAttackLocation == p1XLocation) {
				removePlayer2RangedAttack();
			}
		}
		/**
		 * Puts focus on the BattlePanel so the listeners can react.
		 */
		requestFocus();
	}

	public void setP1RangeAttackLocation(int p1RangeAttackLocation) {
		this.p1RangeAttackLocation = p1RangeAttackLocation;
	}


	public void setP2RangeAttackLocation(int p2RangeAttackLocation) {
		this.p2RangeAttackLocation = p2RangeAttackLocation;
	}


	public int getP1XLocation() {
		return p1XLocation;
	}

	public int getP2XLocation() {
		return p2XLocation;
	}

	public int getP1RangeAttackLocation() {
		return p1RangeAttackLocation;
	}

	public int getP2RangeAttackLocation() {
		return p2RangeAttackLocation;
	}

	/**
	 * removes player twos' ranged attack. Checks the direction the player is
	 * facing to load the next attack.
	 */
	public void removePlayer2RangedAttack()
	{
		p2RangeVelocity = 0;
		if (p2XLocation > p1XLocation)
		{
			p2RangeAttackLocation = p2XLocation + 250;
		} else
			p2RangeAttackLocation = p2XLocation;
		p2RangeActive = false;
	}

	/**
	 * removes' player ones' ranged attack. Checks the direction the player is
	 * facing to load the next attack.
	 */
	public void removePlayer1RangedAttack()
	{
		p1RangeVelocity = 0;
		if (p1XLocation > p2XLocation)
		{
			p1RangeAttackLocation = p1XLocation + 250;
		} else
			p1RangeAttackLocation = p1XLocation;
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
		this.p1RangeAttackLocation += p1RangeVelocity;
		this.p2RangeAttackLocation += p2RangeVelocity;

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
				p1RangeVelocity = 2;
			} else
			{
				p1RangeVelocity = -2;
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
		if (e.getKeyCode() == KeyEvent.VK_5)
		{
			if (p2XLocation < p1XLocation)
			{
				p2RangeVelocity = 2;
			} else
			{
				p2RangeVelocity = -2;
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
