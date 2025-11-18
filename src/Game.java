import java.io.Serializable;
import java.util.Scanner;

public class Game implements Serializable{

    private boolean gameLoaded = false;

    Player player1;
    Player player2;
    Board board;
    transient Scanner scanner = new Scanner(System.in);
    Game(String player1Name, String player2Name) {
        player1 = new Player(player1Name);
        player2 = new Player(player2Name);
        board = new Board();
    }
    
    public void setInitialHealth(int health) {
        player1.setHealth(health);
        player2.setHealth(health);
    }

    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Board getBoard() { return board; }
    
    public void setPlayer1(Player p1) { this.player1 = p1; }
    public void setPlayer2(Player p2) { this.player2 = p2; }
    public void setGameLoaded(boolean loaded) { this.gameLoaded = loaded; }
    public boolean isGameLoaded() { return gameLoaded; }
    public void play() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        
        while (player1.getHealth() > 0 && player2.getHealth() > 0) {
            turn(player1, player2);
            if (player2.getHealth() > 0) {
                turn(player2, player1);
            }
        }
        System.out.println("\n\n╔════════════════════════════════╗");
        System.out.println("║         GAME OVER!            ║");
        if (player1.getHealth() > 0) {
            System.out.println("║  " + player1.getName() + " WON! 🎉  ║");
        } else {
            System.out.println("║  " + player2.getName() + " WON! 🎉  ║");
        }
        System.out.println("╚════════════════════════════════╝");
        System.out.println("\nFinal Summary:");
        System.out.println(player1.getName() + " - Health: " + player1.getHealth() + " | Points: " + player1.getPoints());
        System.out.println(player2.getName() + " - Health: " + player2.getHealth() + " | Points: " + player2.getPoints());
        System.out.println();
    }
    public void turn(Player currentPlayer, Player opponentPlayer){
        board.showBoard();
        System.out.println(currentPlayer.getName() + ", your turn");
        System.out.println(currentPlayer);

        boolean validMove = false;
        while (!validMove) {
            System.out.println("Enter the position by row column (starts at 0) and the direction (w, a, s, d) to move:");
            try {
                String rowToken = scanner.next();
                if (rowToken.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }
                String colToken = scanner.next();
                if (colToken.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }
                String dirToken = scanner.next();
                if (dirToken.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the game...");
                    System.exit(0);
                }

                int row = Integer.parseInt(rowToken);
                int col = Integer.parseInt(colToken);
                
                if (dirToken.length() != 1 || !dirToken.matches("[wasdWASD]")) {
                    System.out.println("✘ Invalid direction! Use w, a, s or d.");
                    continue;
                }
                char direction = Character.toLowerCase(dirToken.charAt(0));

                if (board.isValidMove(row, col, direction)) {
                    board.swap(row, col, direction);
                    board.checkCombos(currentPlayer, opponentPlayer);
                    validMove = true;
                } else {
                    System.out.println("✘ Invalid move! Position or direction not allowed. Try again.");
                }

            } catch (NumberFormatException nfe) {
                System.out.println("✘ Invalid input: row and column must be numbers (0-7). Try again.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("✘ Invalid input. Format: row column direction (e.g.: 0 1 d)");
                scanner.nextLine();
            }
        }

        System.out.println("--- Score after the turn ---");
        System.out.println(currentPlayer.getName() + " - Points: " + currentPlayer.getPoints());
        System.out.println(opponentPlayer.getName() + " - Points: " + opponentPlayer.getPoints());
        System.out.println("---------------------------");



    }
    void applyEffect(Player currentPlayer, Player opponentPlayer, int row, int col, char direction) {
        int newRow = row, newCol = col;
        switch (direction) {
            case 'w': newRow--; break;
            case 'a': newCol--; break;
            case 's': newRow++; break;
            case 'd': newCol++; break;
        }
        char element = board.board[newRow][newCol];
        board.applySymbolEffect(currentPlayer, opponentPlayer, element);
    }


}
