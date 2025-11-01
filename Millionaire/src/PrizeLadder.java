/**
 * Manages the prize ladder for the Millionaire game.
 * Implements 15 question levels with exponential prize increases and checkpoints.
 */
public class PrizeLadder {
    
    private static final int[] PRIZES = {
        100,      // Level 1
        200,      // Level 2
        300,      // Level 3
        500,      // Level 4
        1_000,    // Level 5 - Checkpoint 1
        2_000,    // Level 6
        4_000,    // Level 7
        8_000,    // Level 8
        16_000,   // Level 9
        32_000,   // Level 10 - Checkpoint 2
        64_000,   // Level 11
        125_000,  // Level 12
        250_000,  // Level 13
        500_000,  // Level 14
        1_000_000 // Level 15 - Final prize
    };
    
    private static final int[] CHECKPOINTS = {5, 10, 15};
    
    /**
     * Get the prize amount for a given question level (1-15).
     */
    public static int getPrize(int level) {
        if (level < 1 || level > 15) {
            return 0;
        }
        return PRIZES[level - 1];
    }
    
    /**
     * Check if a level is a checkpoint (safety net).
     */
    public static boolean isCheckpoint(int level) {
        for (int checkpoint : CHECKPOINTS) {
            if (level == checkpoint) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the guaranteed prize at a checkpoint.
     * If the level is not a checkpoint, returns 0.
     */
    public static int getCheckpointPrize(int level) {
        if (isCheckpoint(level)) {
            return getPrize(level);
        }
        return 0;
    }
    
    /**
     * Get the guaranteed prize for the highest reached checkpoint.
     */
    public static int getHighestCheckpointPrize(int currentLevel) {
        int highestCheckpoint = 0;
        for (int checkpoint : CHECKPOINTS) {
            if (checkpoint <= currentLevel) {
                highestCheckpoint = checkpoint;
            } else {
                break;
            }
        }
        return highestCheckpoint > 0 ? getPrize(highestCheckpoint) : 0;
    }
    
    /**
     * Display the prize ladder with highlighting for current level.
     */
    public static void displayLadder(int currentLevel) {
        System.out.println(TUI.colorize("╔════════════════════════════════════════════╗", TUI.CYAN));
        System.out.println(TUI.colorize("║         PRIZE LADDER                       ║", TUI.CYAN));
        System.out.println(TUI.colorize("╠════════════════════════════════════════════╣", TUI.CYAN));
        
        for (int i = 15; i >= 1; i--) {
            String levelStr = String.format("%2d", i);
            String prizeStr = TUI.formatPrize(PRIZES[i - 1]);
            String checkpointMarker = isCheckpoint(i) ? " ✓" : "  ";
            
            String color = TUI.WHITE;
            if (i == currentLevel) {
                color = TUI.BOLD + TUI.YELLOW;
            } else if (i < currentLevel) {
                color = TUI.GREEN;
            } else {
                color = TUI.WHITE;
            }
            
            String line = String.format("║ Level %2s: %-15s%s ║", levelStr, prizeStr, checkpointMarker);
            System.out.println(TUI.colorize(line, color));
        }
        
        System.out.println(TUI.colorize("╚════════════════════════════════════════════╝", TUI.CYAN));
    }
    
    /**
     * Get total number of levels.
     */
    public static int getTotalLevels() {
        return PRIZES.length;
    }
}

