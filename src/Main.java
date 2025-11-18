
import java.util.Scanner;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			GameMenu.showMenu();

			String optionInput = scanner.nextLine().trim();
			if (optionInput.equalsIgnoreCase("exit")) {
				System.out.println("Exiting the game...");
				return;
			}
			int option = 0;
			try {
				option = Integer.parseInt(optionInput);
			} catch (Exception e) {
				System.out.println("✘ Invalid input! Enter a number (1-4) or 'exit'.");
				continue;
			}

			switch (option) {
				case 1:
					String player1Name = "";
					String player2Name = "";
					while (player1Name.isEmpty() || !player1Name.matches("[a-zA-Z\\s]+")) {
						System.out.println("---------Player 1 (letters only):");
						player1Name = scanner.nextLine().trim();
						if (player1Name.equalsIgnoreCase("exit")) {
							System.out.println("Exiting the game...");
							return;
						}
						if (!player1Name.matches("[a-zA-Z\\s]+")) {
							System.out.println("✘ Invalid name! Use only letters.");
							player1Name = "";
						}
					}
					while (player2Name.isEmpty() || !player2Name.matches("[a-zA-Z\\s]+")) {
						System.out.println("---------Player 2 (letters only):");
						player2Name = scanner.nextLine().trim();
						if (player2Name.equalsIgnoreCase("exit")) {
							System.out.println("Exiting the game...");
							return;
						}
						if (!player2Name.matches("[a-zA-Z\\s]+")) {
							System.out.println("✘ Invalid name! Use only letters.");
							player2Name = "";
						}
					}
					Game game = new Game(player1Name, player2Name);
                    
					// Ask for initial health
					int initialHealth = 0;
					while (initialHealth <= 0) {
						System.out.println("---------What is the initial health for players? (recommended: 10-100):");
						String healthInput = scanner.nextLine().trim();
						if (healthInput.equalsIgnoreCase("exit")) {
							System.out.println("Exiting the game...");
							return;
						}
						try {
							initialHealth = Integer.parseInt(healthInput);
							if (initialHealth <= 0) {
								System.out.println("✘ Health must be greater than 0!");
								initialHealth = 0;
							}
						} catch (Exception e) {
							System.out.println("✘ Enter a valid number!");
							initialHealth = 0;
						}
					}
                    
					game.setInitialHealth(initialHealth);
					game.play();
					System.out.println("---------Save the game? (y/n)");
					String saveOption = scanner.next().trim();
					if (saveOption.equalsIgnoreCase("exit")) {
						System.out.println("Exiting the game...");
						return;
					}
					if (saveOption.equalsIgnoreCase("y")) {
						System.out.println("Enter the file name to save:");
						String saveFileName = scanner.next().trim();
						if (saveFileName.equalsIgnoreCase("exit")) {
							System.out.println("Exiting the game...");
							return;
						}
						GameMenu.saveGame(game, saveFileName);
					}
					break;
				case 2:
					Game loadedGame = GameMenu.loadGame(scanner);
					if (loadedGame == null) {
						System.out.println("Error loading the game.");
						break;
					}
					loadedGame.play();
					// Only ask to save if the game was not loaded (is a new game)
					if (!loadedGame.isGameLoaded()) {
						System.out.println("---------Save the game? (y/n)");
						String saveOption2 = scanner.next().trim();
						if (saveOption2.equalsIgnoreCase("exit")) {
							System.out.println("Exiting the game...");
							return;
						}
						if (saveOption2.equalsIgnoreCase("y")) {
							System.out.println("Enter the file name to save:");
							String saveFileName2 = scanner.next().trim();
							if (saveFileName2.equalsIgnoreCase("exit")) {
								System.out.println("Exiting the game...");
								return;
							}
							GameMenu.saveGame(loadedGame, saveFileName2);
						}
					}
					break;
				case 3:
					GameMenu.deleteGame(scanner);
					break;
				case 4:
					System.out.println("--------- Exiting the game...");
					return;
				default:
					System.out.println("✘✘✘ Invalid option! Try again.");
			}
		}
	}
}

