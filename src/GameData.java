import java.io.*;

public class GameData {

    public static void saveGame(String filePath, Game game) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(game.getPlayer1().exportData());
            writer.newLine();
            
            writer.write(game.getPlayer2().exportData());
            writer.newLine();
            
            writer.write(game.getBoard().exportBoard());
            writer.newLine();
            
            System.out.println("✓ Game saved successfully: " + filePath);
        } catch (IOException e) {
            System.out.println("✘ Error saving the game: " + e.getMessage());
        }
    }

    public static Game loadGame(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            String line3 = reader.readLine();
            
            if (line1 == null || line2 == null || line3 == null) {
                System.out.println("✘ Corrupted or invalid file.");
                return null;
            }
            
            Player player1 = Player.fromString(line1);
            Player player2 = Player.fromString(line2);
            
            if (player1 == null || player2 == null) {
                System.out.println("✘ Error loading player data.");
                return null;
            }
            
            Game game = new Game(player1.getName(), player2.getName());
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.getBoard().restoreBoard(line3);
            game.setGameLoaded(true); // Mark as loaded
            
            System.out.println("✓ Game loaded successfully: " + filePath);
            return game;
        } catch (IOException e) {
            System.out.println("✘ Error loading the game: " + e.getMessage());
            return null;
        }
    }
}
