import java.util.List;

/**
 * Text User Interface utilities for creating a polished terminal-based display.
 * Provides box drawing, colors, screen utilities, and formatted displays.
 */
public class TUI {
    
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BOLD = "\u001B[1m";
    
    // Background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    private static boolean colorsEnabled = true;
    

    public static void setColorsEnabled(boolean enabled) {
        colorsEnabled = enabled;
    }
    

    public static String colorize(String text, String color) {
        if (colorsEnabled) {
            return color + text + RESET;
        }
        return text;
    }
    

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback: print multiple newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static String horizontalLine(int length, char character) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(character);
        }
        return sb.toString();
    }
    
    /**
     * Draw a box with text inside.
     * @param width Total width of the box
     * @param lines Lines of text to display inside
     * @param borderColor Color for the border
     */
    public static void drawBox(int width, List<String> lines, String borderColor) {
        char horizontal = '═';
        char vertical = '║';
        char topLeft = '╔';
        char topRight = '╗';
        char bottomLeft = '╚';
        char bottomRight = '╝';
        
        // Top border
        System.out.println(colorize(topLeft + horizontalLine(width - 2, horizontal) + topRight, borderColor));
        
        // Content lines
        for (String line : lines) {
            // Truncate or pad line to fit
            String paddedLine = padToWidth(line, width - 4);
            System.out.println(colorize(vertical + " " + paddedLine + " " + vertical, borderColor));
        }
        
        // Bottom border
        System.out.println(colorize(bottomLeft + horizontalLine(width - 2, horizontal) + bottomRight, borderColor));
    }
    
    /**
     * Draw a simple box with text.
     */
    public static void drawSimpleBox(String text, int width) {
        drawBox(width, List.of(text), CYAN);
    }
    
    /**
     * Pad or truncate string to exact width.
     */
    private static String padToWidth(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width - 3) + "...";
        }
        return String.format("%-" + width + "s", text);
    }
    
    /**
     * Center text within a given width.
     */
    public static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = (width - text.length()) / 2;
        return String.format("%" + (padding + text.length()) + "s", text);
    }
    
    /**
     * Display formatted title.
     */
    public static void displayTitle(String title) {
        int width = 80;
        System.out.println();
        System.out.println(colorize(centerText("═".repeat(width), width), CYAN));
        System.out.println(colorize(centerText(title.toUpperCase(), width), BOLD + YELLOW));
        System.out.println(colorize(centerText("═".repeat(width), width), CYAN));
        System.out.println();
    }
    
    /**
     * Display separator line.
     */
    public static void displaySeparator() {
        System.out.println(colorize("─".repeat(80), CYAN));
    }
    
    /**
     * Format prize amount with commas.
     */
    public static String formatPrize(int amount) {
        if (amount >= 1_000_000) {
            return String.format("$%,d", amount);
        } else if (amount >= 1000) {
            return String.format("$%,d", amount);
        } else {
            return "$" + amount;
        }
    }
    
    /**
     * Highlight text with background color.
     */
    public static String highlight(String text, String bgColor) {
        if (colorsEnabled) {
            return bgColor + text + RESET;
        }
        return "[" + text + "]";
    }
    
    /**
     * Print text with a specific color.
     */
    public static void printColor(String text, String color) {
        System.out.print(colorize(text, color));
    }
    
    /**
     * Print line with color.
     */
    public static void printlnColor(String text, String color) {
        System.out.println(colorize(text, color));
    }
}

