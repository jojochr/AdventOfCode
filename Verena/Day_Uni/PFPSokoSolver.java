package Blatt_9;
public interface PFPSokoSolver {
	
	/**
	 * Solves a Sokoban level.
	 * 
	 * @param lvl to be solved
	 * @param numberOfThreads Number of additional threads to use.
	 * @param timeout in seconds. Return null, if no solution inside the time limit is found.
	 * @return a solution String with the chars
	 * 			l (player left), L (box pushed, player left),
	 * 			r (player right), R (box pushed, player right),
	 * 			u (player up), U (box pushed, player up),
	 * 			d (player down), D (box pushed, player down),
	 */
	public String solve(PFPSokoLvl lvl, int numberOfThreads, int timeout);
}
