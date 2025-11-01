public class Main {
    public static void main(String[] args) {
        try {
            try {
                // Try to enable virtual terminal processing
                System.out.print("\033[?25h"); // Show cursor
            } catch (Exception e) {
                // If this fails, colors may not work, but game will still function
                TUI.setColorsEnabled(false);
            }

            
            // Create and start the game
            Game game = new Game();
            game.start();
            game.close();
            
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}