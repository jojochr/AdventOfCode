package Blatt_9;
/**
 * created 30.03.2008
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * Creates thread-safe pixel-based screen for PFPSokoSolver
 * 
 * @author Marc Woerlein<woerlein@informatik.uni-erlangen.de>
 * @author Georg Dotzler<georg.dotzler@informatik.uni-erlangen.de>
 */
public class PFPSokoScreen {

	public final int[][] array;
	/* final */Runnable repaint;

	/**
	 * Constructor for PFPSokoSolver
	 * 
	 * @param x
	 *            Number of colored areas in x-direction
	 * @param y
	 *            Number of colored areas in y-direction
	 */
	public PFPSokoScreen(final int x, final int y, final boolean useKeyListener) {
		this(x, y, 20, "Matrix", useKeyListener);
	}
	

	/**
	 * Constructor for a flexible pixel field
	 * 
	 * @param x
	 *            Number of colored areas in x-direction
	 * @param y
	 *            Number of colored areas in y-direction
	 * @param pSize
	 *            Side length of a colored area in pixels
	 * @param title
	 *            Window title
	 */
	public PFPSokoScreen(final int x, final int y, final double pSize,
			final String title, final boolean useKeyListener) {
		this(x, y, pSize, title, new int[y][x], useKeyListener);
	}
	
	/** Constructor for window creation */
	private PFPSokoScreen(final int x, final int y, final double pSize,
			final String title, final int[][] array, final boolean useKeyListener
			) {
		this.array = array;
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					// create window
					final JFrame mainFrame = new JFrame();
					if (useKeyListener) {
						mainFrame.addKeyListener(new MyKeyListener());
					}
					// create panel that displays the array as colored areas
					final JPanel colorPanel = new JPanel() {
						private static final long serialVersionUID = 1L;

						@Override
						protected void paintComponent(Graphics g) {
							super.paintComponent(g);

							final double xFac = ((double) getSize().width) / x;
							final double yFac = ((double) getSize().height) / y;
							g.setFont(new Font("Monospace", Font.PLAIN, 20));
							for (int i = 0; i < y; i++) {
								for (int j = 0; j < x; j++) {
										g.setColor(Color.WHITE);
										g.fillPolygon(new int[] { (int) (j * xFac),
										(int) (j * xFac),
										(int) ((j + 1) * xFac),
										(int) ((j + 1) * xFac) },
										new int[] { (int) (i * yFac),
												(int) ((i + 1) * yFac),
												(int) ((i + 1) * yFac),
												(int) (i * yFac) }, 4);
										char[] tmp = {(char)array[i][j]};
										g.setColor(Color.BLACK);
										g.drawChars(tmp, 0, 1, (int)(j * xFac), (int)((i+1) * yFac));
								}
							}
						}
					};
					colorPanel.setPreferredSize(new Dimension(
							(int) (pSize * x), (int) (pSize * y)));
					mainFrame.setContentPane(colorPanel);
					mainFrame.setTitle(title);
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//colorPanel.addKeyListener(new MyKeyListener());
					// display window
					mainFrame.pack();
					mainFrame.setLocationRelativeTo(null);
					mainFrame.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					mainFrame.setVisible(true);

					// repaint window 
					repaint = new Runnable() {
						public void run() {
							mainFrame.repaint();
						}
					};
				}
			});
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Performs the move specified by the given character. The screen is updated
	 * to reflect that move. If the move is invalid, the character 'X' is
	 * printed to the standard output. Otherwise, the given character is
	 * printed.
	 *
	 * @param move the character which specifies the move ('l', 'L', 'r', 'R',
	 *   'u', 'U', 'd', or 'D')
	 */
	public void applyMove(char move) {
		int[][] cloneLvl = array;
		int playerRow = -1;
		int playerCol = -1;
		for (int i = 0; i < cloneLvl.length; i++) {
			for (int j = 0; j < cloneLvl[i].length; j++) {
				if (cloneLvl[i][j] == 0x40 || cloneLvl[i][j] == 0x2b) {
					playerRow = i;
					playerCol = j;
				}
			}
		}
		switch (move) {
		case 'l':
		case 'L':
			if (playerCol == 0) {
				System.out.print('X');
				return;
			}
			switch(cloneLvl[playerRow][playerCol-1]) {
			case 0x23:
				System.out.print('X');
				return;
			case 0x24:
			case 0x2a:
				if (playerCol-1 == 0) {
					System.out.print('X');
					return;
				}
				switch(cloneLvl[playerRow][playerCol-2]) {
				case 0x23:
					System.out.print('X');
					return;
				case 0x24:
				case 0x2a:
					System.out.print('X');
					return;
				case 0x2e:
				case 0x20:
					System.out.print('L');
					if (cloneLvl[playerRow][playerCol-2] == 0x2e) {
						cloneLvl[playerRow][playerCol-2] = 0x2a;
					} else {
						cloneLvl[playerRow][playerCol-2] = 0x24;
					}
					if (cloneLvl[playerRow][playerCol-1] == 0x2a) {
						cloneLvl[playerRow][playerCol-1] = 0x2b;
					} else {
						cloneLvl[playerRow][playerCol-1] = 0x40;
					}
						
					if (cloneLvl[playerRow][playerCol] == 0x2b) {
						cloneLvl[playerRow][playerCol] = 0x2e;
					} else {
						cloneLvl[playerRow][playerCol] = 0x20;
					}
				}
				playerCol--;
				break;
			case 0x2e:
			case 0x20:
				System.out.print('l');
				if (cloneLvl[playerRow][playerCol-1] == 0x2e) {
					cloneLvl[playerRow][playerCol-1] = 0x2b;
				} else {
					cloneLvl[playerRow][playerCol-1] = 0x40;
				}
				if (cloneLvl[playerRow][playerCol] == 0x2b) {
					cloneLvl[playerRow][playerCol] = 0x2e;
				} else {
					cloneLvl[playerRow][playerCol] = 0x20;
				}
				playerCol--;
				break;
			default:
				System.out.print('X');
				return;
			}
			break;
		case 'r':
		case 'R':
			if (playerCol == cloneLvl[playerRow].length-1) {
				System.out.print('X');
				return;
			}
			switch(cloneLvl[playerRow][playerCol+1]) {
			case 0x23:
				System.out.print('X');
				return;
			case 0x24:
			case 0x2a:
				if (playerCol+1 == cloneLvl[playerRow].length-1) {
					System.out.print('X');
					return;
				}
				switch(cloneLvl[playerRow][playerCol+2]) {
				case 0x23:
					System.out.print('X');
					return;
				case 0x24:
				case 0x2a:
					System.out.print('X');
					return;
				case 0x2e:
				case 0x20:
					System.out.print('R');
					if (cloneLvl[playerRow][playerCol+2] == 0x2e) {
						cloneLvl[playerRow][playerCol+2] = 0x2a;
					} else {
						cloneLvl[playerRow][playerCol+2] = 0x24;
					}
					if (cloneLvl[playerRow][playerCol+1] == 0x2a) {
						cloneLvl[playerRow][playerCol+1] = 0x2b;
					} else {
						cloneLvl[playerRow][playerCol+1] = 0x40;
					}
						
					if (cloneLvl[playerRow][playerCol] == 0x2b) {
						cloneLvl[playerRow][playerCol] = 0x2e;
					} else {
						cloneLvl[playerRow][playerCol] = 0x20;
					}
				}
				playerCol++;
				break;
			case 0x2e:
			case 0x20:
				System.out.print('r');
				if (cloneLvl[playerRow][playerCol+1] == 0x2e) {
					cloneLvl[playerRow][playerCol+1] = 0x2b;
				} else {
					cloneLvl[playerRow][playerCol+1] = 0x40;
				}
				if (cloneLvl[playerRow][playerCol] == 0x2b) {
					cloneLvl[playerRow][playerCol] = 0x2e;
				} else {
					cloneLvl[playerRow][playerCol] = 0x20;
				}
				playerCol++;
				break;
			default:
				System.out.print('X');
				return;
			}
			break;
		case 'u':
		case 'U':
			if (playerRow == 0) {
				System.out.print('X');
				return;
			}
			switch(cloneLvl[playerRow-1][playerCol]) {
			case 0x23:
				System.out.print('X');
				return;
			case 0x24:
			case 0x2a:
				if (playerRow-1 == 0) {
					System.out.print('X');
					return;
				}
				switch(cloneLvl[playerRow-2][playerCol]) {
				case 0x23:
					System.out.print('X');
					return;
				case 0x24:
				case 0x2a:
					System.out.print('X');
					return;
				case 0x2e:
				case 0x20:
					System.out.print('U');
					if (cloneLvl[playerRow-2][playerCol] == 0x2e) {
						cloneLvl[playerRow-2][playerCol] = 0x2a;
					} else {
						cloneLvl[playerRow-2][playerCol] = 0x24;
					}
					if (cloneLvl[playerRow-1][playerCol] == 0x2a) {
						cloneLvl[playerRow-1][playerCol] = 0x2b;
					} else {
						cloneLvl[playerRow-1][playerCol] = 0x40;
					}
						
					if (cloneLvl[playerRow][playerCol] == 0x2b) {
						cloneLvl[playerRow][playerCol] = 0x2e;
					} else {
						cloneLvl[playerRow][playerCol] = 0x20;
					}
				}
				playerRow--;
				break;
			case 0x2e:
			case 0x20:
				System.out.print('u');
				if (cloneLvl[playerRow-1][playerCol] == 0x2e) {
					cloneLvl[playerRow-1][playerCol] = 0x2b;
				} else {
					cloneLvl[playerRow-1][playerCol] = 0x40;
				}
				if (cloneLvl[playerRow][playerCol] == 0x2b) {
					cloneLvl[playerRow][playerCol] = 0x2e;
				} else {
					cloneLvl[playerRow][playerCol] = 0x20;
				}
				playerRow--;
				break;
			default:
				System.out.print('X');
				return;
			}
			break;
		case 'd':
		case 'D':
			if (playerRow == cloneLvl.length-1) {
				System.out.print('X');
				return;
			}
			switch(cloneLvl[playerRow+1][playerCol]) {
			case 0x23:
				System.out.print('X');
				return;
			case 0x24:
			case 0x2a:
				if (playerRow+1 == cloneLvl.length-1) {
					System.out.print('X');
					return;
				}
				switch(cloneLvl[playerRow+2][playerCol]) {
				case 0x23:
					System.out.print('X');
					return;
				case 0x24:
				case 0x2a:
					System.out.print('X');
					return;
				case 0x2e:
				case 0x20:
					System.out.print('D');
					if (cloneLvl[playerRow+2][playerCol] == 0x2e) {
						cloneLvl[playerRow+2][playerCol] = 0x2a;
					} else {
						cloneLvl[playerRow+2][playerCol] = 0x24;
					}
					if (cloneLvl[playerRow+1][playerCol] == 0x2a) {
						cloneLvl[playerRow+1][playerCol] = 0x2b;
					} else {
						cloneLvl[playerRow+1][playerCol] = 0x40;
					}
						
					if (cloneLvl[playerRow][playerCol] == 0x2b) {
						cloneLvl[playerRow][playerCol] = 0x2e;
					} else {
						cloneLvl[playerRow][playerCol] = 0x20;
					}
				}
				playerRow++;
				break;
			case 0x2e:
			case 0x20:
				System.out.print('d');
				if (cloneLvl[playerRow+1][playerCol] == 0x2e) {
					cloneLvl[playerRow+1][playerCol] = 0x2b;
				} else {
					cloneLvl[playerRow+1][playerCol] = 0x40;
				}
				if (cloneLvl[playerRow][playerCol] == 0x2b) {
					cloneLvl[playerRow][playerCol] = 0x2e;
				} else {
					cloneLvl[playerRow][playerCol] = 0x20;
				}
				playerRow++;
				break;
			default:
				System.out.print('X');
				return;
			}
			break;
		default:
			System.out.print('X');
			return;
		}
		javax.swing.SwingUtilities.invokeLater(repaint);
		for (int i = 0; i < cloneLvl.length; i++) {
			for (int j = 0; j < cloneLvl[0].length; j++) {
				if (cloneLvl[i][j] == 0x24) {
					return;
				}
			}
		}
		JOptionPane.showMessageDialog(null,
	    		"Finished!",
	    		"SUCCESS!",
	    		JOptionPane.INFORMATION_MESSAGE,null
	    		);
		
	}
	
	public class MyKeyListener implements KeyListener {
		
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				applyMove('l');
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				applyMove('r');
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				applyMove('u');
			}  else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				applyMove('d');
			}
			
		}
		
		
		
	}
	/**
	 * Sets the point(x,y) to the specified character
	 * 
	 * @param x
	 * @param y
	 * @param character
	 */
	 public synchronized void setChar(final int x, final int y, final char character) {
		// for frequent thread changes
		Thread.yield();
		synchronized (array) {
			array[y][x] = character;
		}
		javax.swing.SwingUtilities.invokeLater(repaint);
	}

}
