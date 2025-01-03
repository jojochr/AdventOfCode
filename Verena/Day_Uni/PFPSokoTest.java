package Blatt_9;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PFPSokoTest {


    PFPSokoLvl[] lvls;

    public static final int[] NUM_THREADS = {2, 4, 8, 16};
    public static final int[] EASY_CASES = {0, 7, 8, 13, 14, 18, 22, 23, 45, 56, 62, 153, 154};
    public static final int[] PRUNING_CASES = {0, 1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 54, 55, 56, 57, 60, 62, 63, 66, 67, 70, 71, 72, 73, 74, 75, 78, 80, 81, 83, 85, 87, 90, 93, 95, 102, 103, 109, 118, 119, 134, 148, 153, 154};
    public static final int[] HARDER_PRUNING_CASES = {4, 6, 33, 34, 35, 53, 58, 59, 61, 64, 65, 68, 69, 76, 79, 82, 84, 88, 91, 99, 86, 89, 94, 96, 100, 105, 106, 115, 117, 123, 124, 125, 126, 127, 128, 129, 130, 131, 133, 135, 136, 141, 142, 146, 147, 150, 151,};
    public static final int[] DEADLOCK_PRUNING_CASES = {77, 101, 107, 112, 113, 114, 116, 120, 137, 139, 140};
    public static final int[] HARD_CASES = {97, 98, 108, 110, 111, 121, 132, 149};
    public static final int[] EVEN_HARDER_CASES = {92, 104, 122, 138, 144, 145, 152};
    public static final int[] MEMORY_DEMANDING_CASES = {143};

    public static final boolean PRINT_DETAILS = false;

    @Before
    public void init() {
        lvls = PFPSokoTools.getLevelsFromFile("src/Blatt_9/SokoTests.txt"); //Adjust this path to the location of the level file.
    }

    /**
     * Tests the timeout.
     */
    @Test(timeout = 10000)
    public void timeoutTest() {
        Thread t = new Thread() {
            public void run() {
                PFPSokoSolver impl = new PFPSokoSolverImpl();
                @SuppressWarnings("unused")
                String solution = impl.solve(lvls[122], 1, 5);
            }
        };
        t.start();
        try {
            t.join(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (t.isAlive()) {
            assertTrue(false);
        }
    }

    /**
     * This test should work with naive solutions.
     */
    @Test(timeout = 40000)
    public void simpleTestSequential() {
        int timeout = 30;
        PFPSokoSolver impl = new PFPSokoSolverImpl();
        String solution = impl.solve(lvls[43], 1, timeout);
        boolean success = PFPSokoTools.applySolution(lvls[43], solution);
        if (PRINT_DETAILS) {
            if (success) {
                System.out.println("SUCCESS! Level " + "43" + " solution: " + solution + ".");
            } else {
                System.out.println("Solver failed at Level " + "43" + ".");
            }
        }
        assertTrue(success);
    }

    /**
     * This test should work with naive parallel solutions.
     */
    @Test(timeout = 40000)
    public void simpleTestParallel() {
        int timeout = 30;
        PFPSokoSolver impl = new PFPSokoSolverImpl();
        String solution = impl.solve(lvls[43], 8, timeout);
        boolean success = PFPSokoTools.applySolution(lvls[43], solution);
        if (PRINT_DETAILS) {
            if (success) {
                System.out.println("SUCCESS! Level " + "43" + " solution: " + solution + ".");
            } else {
                System.out.println("Solver failed at Level " + "43" + ".");
            }
        }
        assertTrue(success);
    }

    /**
     * These tests should work with if the algorithm skips already examined level configurations.
     */
    @Test
    public void easyTestsSequential() {
        int timeout = 60;
        for (final int i : EASY_CASES) {
            PFPSokoSolver impl = new PFPSokoSolverImpl();
            String solution = impl.solve(lvls[i], 1, timeout);
            boolean success = PFPSokoTools.applySolution(lvls[i], solution);
            if (PRINT_DETAILS) {
                if (success) {
                    System.out.println("SUCCESS! Level " + i + " solution: " + solution + ".");
                } else {
                    System.out.println("Solver failed at Level " + i + ".");
                }
            }
            assertTrue("Level " + i + " failed", success);
        }
    }

    /**
     * These tests should work with if the algorithm skips already examined level configurations.
     */
    @Test
    public void easyTestsParallel() {
        int timeout = 60;
        for (final int i : EASY_CASES) {
            for (final int threads : NUM_THREADS) {
                PFPSokoSolver impl = new PFPSokoSolverImpl();
                String solution = impl.solve(lvls[i], threads, timeout);
                boolean success = PFPSokoTools.applySolution(lvls[i], solution);
                if (PRINT_DETAILS) {
                    if (success) {
                        System.out.println("SUCCESS! Level " + i + " Threads: " + threads + ".");
                    } else {
                        System.out.println("Solver failed at Level " + i + " Threads: " + threads + ".");
                    }
                }
                assertTrue("Level " + i + " failed with " + threads + " threads", success);
            }
        }
    }

//	/**
//	 * These tests should work with pruning of duplicate positions.
//	 */
//	@Test
//	public void PruningTestsSequential() {
//		int timeout = 60;
//		for (final int i : PRUNING_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * These tests should work with pruning of duplicate positions.
//	 */
//	@Test
//	public void PruningTestsParallel() {
//		int timeout = 60;
//		for (final int i : PRUNING_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
//	
//	/**
//	 * These tests should work with pruning of duplicate positions.
//	 */
//	@Test
//	public void HarderPruningTestsSequential() {
//		int timeout = 60;
//		for (final int i : HARDER_PRUNING_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * These tests should work with pruning of duplicate positions.
//	 */
//	@Test
//	public void HarderPruningTestsParallel() {
//		int timeout = 60;
//		for (final int i : HARDER_PRUNING_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
//	
//	/**
//	 * These tests should work with pruning of duplicate positions
//	 * and assignment of a higher priority to profitable moves
//	 * (e.g. a box is pushed towards a goal)
//	 * and a standard deadlock (e.g. a box in a corner) detection.
//	 */
//	@Test
//	public void DeadlockTestsSequential() {
//		int timeout = 120;
//		for (final int i : DEADLOCK_PRUNING_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * These tests should work with pruning of duplicate positions
//	 * and assignment of a higher priority to profitable moves
//	 * (e.g. a box is pushed towards a goal)
//	 * and a standard deadlock (e.g. a box in a corner) detection.
//	 */
//	@Test
//	public void DeadlockTestsParallel() {
//		int timeout = 120;
//		for (final int i : DEADLOCK_PRUNING_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
//	
//	/**
//	 * Just some harder test cases. Timeout increased.
//	 */
//	@Test
//	public void HardTestsSequential() {
//		int timeout = 240;
//		for (final int i : HARD_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * Just some harder test cases. Timeout increased.
//	 */
//	@Test
//	public void HardTestsParallel() {
//		int timeout = 240;
//		for (final int i : HARD_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
//	
//	/**
//	 * Just some even harder test cases. Timeout increased.
//	 * Not required for Bonuspunkte ;)
//	 */
//	@Test
//	public void EvenHarderTestsSequential() {
//		int timeout = 500;
//		for (final int i : EVEN_HARDER_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * Just some even harder test cases. Timeout increased.
//	 * Not required for Bonuspunkte ;)
//	 */
//	@Test
//	public void EvenHarderTestsParallel() {
//		int timeout = 500;
//		for (final int i : EVEN_HARDER_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
//	
//	/**
//	 * Just some memory consuming test cases. Timeout increased.
//	 * Not required for Bonuspunkte ;)
//	 */
//	@Test
//	public void MemoryTestsSequential() {
//		int timeout = 500;
//		for (final int i : MEMORY_DEMANDING_CASES) {
//			PFPSokoSolver impl = new PFPSokoSolverImpl();
//			String solution = impl.solve(lvls[i], 1, timeout);
//			boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//			if (PRINT_DETAILS) {
//				if (success) {
//					System.out.println("SUCCESS! Level "+ i + " solution: "+ solution+ ".");
//				} else {
//					System.out.println("Solver failed at Level "+ i + ".");
//				}
//			}
//			assertTrue("Level " + i + " failed", success);
//		}
//	}
//	
//	/**
//	 * Just some memory consuming test cases. Timeout increased.
//	 * Not required for Bonuspunkte ;)
//	 */
//	@Test
//	public void MemoryTestsParallel() {
//		int timeout = 500;
//		for (final int i : MEMORY_DEMANDING_CASES) {
//			for (final int threads : NUM_THREADS) {
//				PFPSokoSolver impl = new PFPSokoSolverImpl();
//				String solution = impl.solve(lvls[i], threads, timeout);
//				boolean success = PFPSokoTools.applySolution(lvls[i], solution);
//				if (PRINT_DETAILS) {
//					if (success) {
//						System.out.println("SUCCESS! Level "+ i + " Threads: "+ threads+ ".");
//					} else {
//						System.out.println("Solver failed at Level "+ i + " Threads: "+ threads+ ".");
//					}
//				}
//				assertTrue("Level " + i + " failed with " + threads + " threads", success);
//			}
//		}
//	}
}
