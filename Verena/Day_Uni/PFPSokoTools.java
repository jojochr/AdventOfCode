package Blatt_9;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/**
 * 
 * Provides some tools for the PFPSokoSolver.
 *
 */
public class PFPSokoTools {

	/**
	 * Reads Sokoban levels from a file.
	 * @param path The path to the level file.
	 * @return
	 */
	public static PFPSokoLvl[] getLevelsFromFile(String path) {
		File f = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			br.close();
			String text = result.toString();
			String[] levels = text.split(";");
			PFPSokoLvl[] readLvls = new PFPSokoLvl[levels.length-1]; 
			for (int i = 1; i < levels.length; i++) {
				String[] rows = levels[i].split("\n");
				int numberOfRows = rows.length - 2;
				int numberOfCols = 0;
				for (int j = 2; j < rows.length - 1; j++) {
					numberOfCols = Math.max(numberOfCols, rows[j].length());
				}
				int[][] data = new int[numberOfRows][numberOfCols];
				for (int j = 2; j < rows.length; j++) {
					for (int k = 0; k < numberOfCols; k++) {
						if (k < rows[j].length()) {
							data[j-2][k] = rows[j].charAt(k);
						} else {
							data[j-2][k] = 0x20;
						}
					}
				}
				for (int j = 0; j < data.length; j++) {
					for (int k = 0; k < data[j].length; k++) {
						switch (data[j][k]){
						case 0x23:
						case 0x40:
						case 0x2b:
						case 0x24:
						case 0x2a:
						case 0x2e:
						case 0x20:
							break;
						default:
							assert(false);
						}
					}
				}
				readLvls[i-1] = new PFPSokoLvl(data);
			}
			return readLvls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 
	 * Applies a solution string to a Sokoban level. Returns true if it is a correct solution.
	 * 
	 * @param lvl The level to test.
	 * @param solution The possible solution.
	 * @return
	 */
	public static boolean applySolution(PFPSokoLvl lvl, String solution) {
		if (solution == null) {
			return false;
		}
		int[][] cloneLvl = lvl.getDataClone();
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
		char[] solutionSteps = solution.toCharArray();
		for (int i = 0; i < solutionSteps.length; i++) {
			char move = solutionSteps[i];
			switch (move) {
			case 'l':
			case 'L':
				if (playerCol == 0) {
					throw new IllegalArgumentException("Player you cannot leave the playground: "+move+" ("+i+")("+solution+")");
				}
				switch(cloneLvl[playerRow][playerCol-1]) {
				case 0x23:
					throw new IllegalArgumentException("You can't break through walls: "+move+" ("+i+")("+solution+")");
				case 0x24:
				case 0x2a:
					if (playerCol-1 == 0) {
						throw new IllegalArgumentException("You can't push your box down the abyss: "+move+" ("+i+")("+solution+")");
					}
					switch(cloneLvl[playerRow][playerCol-2]) {
					case 0x23:
						throw new IllegalArgumentException("There is a wall in your way: "+move+" ("+i+")("+solution+")");
					case 0x24:
					case 0x2a:
						throw new IllegalArgumentException("There is a box in your way: "+move+" ("+i+")("+solution+")");
					case 0x2e:
					case 0x20:
						if (move == 'l') {
							throw new IllegalArgumentException("You have to push the box: "+move+" ("+i+")("+solution+")");
						}
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
					throw new IllegalArgumentException("What???: "+cloneLvl.toString());
				}
				break;
			case 'r':
			case 'R':
				if (playerCol == cloneLvl[playerRow].length-1) {
					throw new IllegalArgumentException("Player you cannot leave the playground: "+move+" ("+i+")("+solution+")");
				}
				switch(cloneLvl[playerRow][playerCol+1]) {
				case 0x23:
					throw new IllegalArgumentException("You can't break through walls: "+move+" ("+i+")("+solution+")");
				case 0x24:
				case 0x2a:
					if (playerCol+1 == cloneLvl[playerRow].length-1) {
						throw new IllegalArgumentException("You can't push your box down the abyss: "+move+" ("+i+")("+solution+")");
					}
					switch(cloneLvl[playerRow][playerCol+2]) {
					case 0x23:
						throw new IllegalArgumentException("There is a wall in your way: "+move+" ("+i+")("+solution+")");
					case 0x24:
					case 0x2a:
						throw new IllegalArgumentException("There is a box in your way: "+move+" ("+i+")("+solution+")");
					case 0x2e:
					case 0x20:
						if (move == 'r') {
							throw new IllegalArgumentException("You have to push the box: "+move+" ("+i+")("+solution+")");
						}
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
					throw new IllegalArgumentException("What???: "+cloneLvl.toString());
				}
				break;
			case 'u':
			case 'U':
				if (playerRow == 0) {
					throw new IllegalArgumentException("Player you cannot leave the playground: "+move+" ("+i+")("+solution+")");
				}
				switch(cloneLvl[playerRow-1][playerCol]) {
				case 0x23:
					throw new IllegalArgumentException("You can't break through walls: "+move+" ("+i+")("+solution+")");
				case 0x24:
				case 0x2a:
					if (playerRow-1 == 0) {
						throw new IllegalArgumentException("You can't push your box down the abyss: "+move+" ("+i+")("+solution+")");
					}
					switch(cloneLvl[playerRow-2][playerCol]) {
					case 0x23:
						throw new IllegalArgumentException("There is a wall in your way: "+move+" ("+i+")("+solution+")");
					case 0x24:
					case 0x2a:
						throw new IllegalArgumentException("There is a box in your way: "+move+" ("+i+")("+solution+")");
					case 0x2e:
					case 0x20:
						if (move == 'u') {
							throw new IllegalArgumentException("You have to push the box: "+move+" ("+i+")("+solution+")");
						}
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
					throw new IllegalArgumentException("What???: "+cloneLvl.toString());
				}
				break;
			case 'd':
			case 'D':
				if (playerRow == cloneLvl.length-1) {
					throw new IllegalArgumentException("Player you cannot leave the playground: "+move+" ("+i+")("+solution+")");
				}
				switch(cloneLvl[playerRow+1][playerCol]) {
				case 0x23:
					throw new IllegalArgumentException("You can't break through walls: "+move+" ("+i+")("+solution+")");
				case 0x24:
				case 0x2a:
					if (playerRow+1 == cloneLvl.length-1) {
						throw new IllegalArgumentException("You can't push your box down the abyss: "+move+" ("+i+")("+solution+")");
					}
					switch(cloneLvl[playerRow+2][playerCol]) {
					case 0x23:
						throw new IllegalArgumentException("There is a wall in your way: "+move+" ("+i+")("+solution+")");
					case 0x24:
					case 0x2a:
						throw new IllegalArgumentException("There is a box in your way: "+move+" ("+i+")("+solution+")");
					case 0x2e:
					case 0x20:
						if (move == 'd') {
							throw new IllegalArgumentException("You have to push the box: "+move+" ("+i+")("+solution+")");
						}
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
					throw new IllegalArgumentException("What???: "+cloneLvl.toString());
				}
				break;
			default:
				throw new IllegalArgumentException("Illegal move: "+String.valueOf(move)+" ("+solution+")");
			}
		}
		for (int i = 0; i < cloneLvl.length; i++) {
			for (int j = 0; j < cloneLvl[0].length; j++) {
				if (cloneLvl[i][j] == 0x24) {
					return false;
				}
			}
		}
		return true;
	}



	/**
	 * Reads solutions from a file.
	 * @param path The Path to the solution file.
	 * @return
	 */
	public static String[] getSolutionsFromFile(String path) {
		File f = new File(path);
		String[] solutions = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			br.close();
			String text = result.toString();
			solutions = text.split("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solutions;
	}



	/**
	 * Opens an interactive screen to play a Sokoban level.
	 * @param lvl
	 */
	public static void playGame(PFPSokoLvl lvl) {
		int[][] data = lvl.getDataClone();
		PFPSokoScreen screen = new PFPSokoScreen(data[0].length, data.length, true);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				screen.setChar(j, i, (char)data[i][j]);
			}
		}
	}



	/**
	 * Opens a screen to show the movements of a Sokoban solution String.
	 * @param lvl
	 */
	public static void playGame(PFPSokoLvl lvl, String solution) {
		int[][] data = lvl.getDataClone();
		PFPSokoScreen screen = new PFPSokoScreen(data[0].length, data.length, false);
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				screen.setChar(j, i, (char)data[i][j]);
			}
		}
		char[] solutionSteps = solution.toCharArray();
		for (char c : solutionSteps) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			screen.applyMove(c);
		}
	}



	/**
	 * First argument determines the path to the level file.
	 * The second argument specifies the level.
	 * The third argument is the solution String.
	 * @param args
	 */
	public static void main(String[] args) {
		PFPSokoLvl[] lvls = getLevelsFromFile(args[0]);
		if (args.length == 2) {
			playGame(lvls[Integer.parseInt(args[1])]);
		} else if (args.length == 3){
			playGame(lvls[Integer.parseInt(args[1])], args[2]);
		}
	}



	/**
	 * Checks whether the player can move in the direction given by move.
	 */
	public static boolean canMove(final boolean[][] walls, final boolean[][] boxes,
			final int playerX, final int playerY, final char move) {

		switch (move) {
			case 'l':
			case 'L': {
				if (playerX <= 0 || walls[playerY][playerX - 1]) {
					return false;
				}
				if (boxes[playerY][playerX - 1]
						&& (playerX == 1 || walls[playerY][playerX - 2] || boxes[playerY][playerX - 2])) {
					return false;
				}
				return true;
			}

			case 'r':
			case 'R': {
				if (playerX >= boxes[playerY].length - 1 || walls[playerY][playerX + 1]) {
					return false;
				}
				if (boxes[playerY][playerX + 1]
						&& (playerX == boxes[playerY].length - 2 || walls[playerY][playerX + 2]
							|| boxes[playerY][playerX + 2])) {
					return false;
				}
				return true;
			}

			case 'u':
			case 'U': {
				if (playerY <= 0 || walls[playerY - 1][playerX]) {
					return false;
				}
				if (boxes[playerY - 1][playerX]
						&& (playerY == 1 || walls[playerY - 2][playerX] || boxes[playerY - 2][playerX])) {
					return false;
				}
				return true;
			}

			case 'd':
			case 'D': {
				if (playerY >= boxes.length - 1 || walls[playerY + 1][playerX]) {
					return false;
				}
				if (boxes[playerY + 1][playerX]
						&& (playerY == boxes.length - 2 || walls[playerY + 2][playerX]
							|| boxes[playerY + 2][playerX])) {
					return false;
				}
				return true;
			}

			default:
				throw new IllegalArgumentException("Illegal Move: " + move);
		}
	}



	/**
	 * Shift all boxes that would be pushed by a move in the given direction.
	 * This method assumes that this move is possible.
	 */
	public static boolean shiftBoxes(final boolean[][] boxes, final int playerX, final int playerY,
			final char move) {

		switch (move) {
			case 'l':
			case 'L': {
				if (boxes[playerY][playerX - 1]) {
					boxes[playerY][playerX - 1] = false;
					boxes[playerY][playerX - 2] = true;
					return true;
				}
				return false;
			}

			case 'r':
			case 'R': {
				if (boxes[playerY][playerX + 1]) {
					boxes[playerY][playerX + 1] = false;
					boxes[playerY][playerX + 2] = true;
					return true;
				}
				return false;
			}

			case 'u':
			case 'U': {
				if (boxes[playerY - 1][playerX]) {
					boxes[playerY - 1][playerX] = false;
					boxes[playerY - 2][playerX] = true;
					return true;
				}
				return false;
			}

			case 'd':
			case 'D': {
				if (boxes[playerY + 1][playerX]) {
					boxes[playerY + 1][playerX] = false;
					boxes[playerY + 2][playerX] = true;
					return true;
				}
				return false;
			}

			default:
				throw new IllegalArgumentException("Illegal Move: " + move);
		}
	}



	/**
	 * Returns the new X coordinate after performing the given move.
	 */
	public static int getPlayerX(final int playerX, final char move) {
		switch (move) {
			case 'l':
			case 'L': {
				return playerX - 1;
			}

			case 'r':
			case 'R': {
				return playerX + 1;
			}

			default:
				return playerX;
		}
	}



	/**
	 * Returns the new Y coordinate after performing the given move.
	 */
	public static int getPlayerY(final int playerY, final char move) {
		switch (move) {
			case 'u':
			case 'U': {
				return playerY - 1;
			}

			case 'd':
			case 'D': {
				return playerY + 1;
			}

			default:
				return playerY;
		}
	}
}
