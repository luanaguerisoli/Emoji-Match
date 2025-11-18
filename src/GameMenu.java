import java.io.*;
import java.util.Scanner;

public class GameMenu {
    private static final String SAVED_GAMES_FOLDER = "saved_games";

    public static void showMenu() {
        System.out.println("---------Menu---------");
        System.out.println("1. New game");
        System.out.println("2. Load previous game");
        System.out.println("3. Delete a saved game");
        System.out.println("4. Exit the game");
        System.out.println("-----------------------");
        System.out.println("Type 'exit' at any time to quit the program.");
    }

    public static void saveGame(Game game, String fileName) {
        File folder = new File(SAVED_GAMES_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String path = SAVED_GAMES_FOLDER + File.separator + fileName + ".txt";
        GameData.saveGame(path, game);
    }

    public static Game loadGame(Scanner scanner) {
        listSavedGames();

        System.out.println("Enter the file name to load:");
        String fileName = scanner.next().trim();
        if (fileName.equalsIgnoreCase("exit")) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }
        String path = SAVED_GAMES_FOLDER + File.separator + fileName + ".txt";
        return GameData.loadGame(path);
    }

    public static void deleteGame(Scanner scanner) {
        listSavedGames();

        File folder = new File(SAVED_GAMES_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (files == null || files.length == 0) {
            System.out.println("✘ No saved games to delete.");
            return;
        }

        System.out.println("Enter the file name to delete:");
        String fileName = scanner.next().trim();
        if (fileName.equalsIgnoreCase("exit")) {
            System.out.println("Exiting the game...");
            System.exit(0);
        }

        try {
            String path = SAVED_GAMES_FOLDER + File.separator + fileName + ".txt";
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("✘ File not found: " + fileName);
                return;
            }

            if (file.delete()) {
                System.out.println("✓ Game deleted successfully: " + fileName);
            } else {
                System.out.println("✘ Error deleting the file.");
            }
        } catch (Exception e) {
            System.out.println("✘ Error deleting the game: " + e.getMessage());
        }
    }

    public static void listSavedGames() {
        File folder = new File(SAVED_GAMES_FOLDER);

        if (!folder.exists()) {
            System.out.println("No saved games yet.");
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No saved games yet.");
            return;
        }

        System.out.println("---------Saved Games---------");
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getName().replace(".txt", "");
            System.out.println((i + 1) + ". " + name);
        }
        System.out.println("------------------------------");
    }
}
