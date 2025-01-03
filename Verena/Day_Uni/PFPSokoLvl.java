package Blatt_9;
/**
 * A simple level interface for the Sokoban Tools.
 * It is not recommended to use this representation inside
 * the PFPSokoSolverImpl.
 */
public class PFPSokoLvl implements Cloneable{
	private final int[][] data;
	public PFPSokoLvl(int[][] data) {
		this.data = data;
	}
	
	/**
	 * Returns a two-dimensional boolean array containing true for each wall.
	 */
	public boolean[][] getWalls() {
		final boolean[][] result = new boolean[this.data.length][];
		for (int i = 0; i < this.data.length; ++i) {
			result[i] = new boolean[this.data[i].length];
			for (int j = 0; j < this.data[i].length; ++j) {
				result[i][j] = this.data[i][j] == '#';
			}
		}
		return result;
	}

	/**
	 * Returns a two-dimensional boolean array containing true for each goal.
	 */
	public boolean[][] getGoals() {
		final boolean[][] result = new boolean[this.data.length][];
		for (int i = 0; i < this.data.length; ++i) {
			result[i] = new boolean[this.data[i].length];
			for (int j = 0; j < this.data[i].length; ++j) {
				result[i][j] = this.data[i][j] == '.' || this.data[i][j] == '+' || this.data[i][j] == '*';
			}
		}
		return result;
	}

	/**
	 * Returns a two-dimensional boolean array containing true for each box.
	 */
	public boolean[][] getBoxes() {
		final boolean[][] result = new boolean[this.data.length][];
		for (int i = 0; i < this.data.length; ++i) {
			result[i] = new boolean[this.data[i].length];
			for (int j = 0; j < this.data[i].length; ++j) {
				result[i][j] = this.data[i][j] == '$' || this.data[i][j] == '*';
			}
		}
		return result;
	}

	/**
	 * Returns the X coordinate of the player.
	 */
	public int getPlayerX() {
		for (int i = 0; i < this.data.length; ++i) {
			for (int j = 0; j < this.data[i].length; ++j) {
				if (this.data[i][j] == '@' || this.data[i][j] == '+') {
					return j;
				}
			}
		}
		throw new IllegalStateException();
	}

	/**
	 * Returns the Y coordinate of the player.
	 */
	public int getPlayerY() {
		for (int i = 0; i < this.data.length; ++i) {
			for (int j = 0; j < this.data[i].length; ++j) {
				if (this.data[i][j] == '@' || this.data[i][j] == '+') {
					return i;
				}
			}
		}
		throw new IllegalStateException();
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				buffer.append((char)data[i][j]);
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	public int[][] getDataClone() {
		int[][] dataClone = new int[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			System.arraycopy(data[i], 0, dataClone[i], 0, data[i].length);
		}
		return dataClone;
	}
	
	@Override
	public PFPSokoLvl clone() {
		int[][] dataClone = new int[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			System.arraycopy(data[i], 0, dataClone[i], 0, data[i].length);
		}
		return new PFPSokoLvl(dataClone);
	}

}
