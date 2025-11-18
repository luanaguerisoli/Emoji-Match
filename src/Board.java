import java.util.Random;

public class Board {
    public char[][] board;
    public final char[] symbols = {'\u2620', '$', '\u271a', '\u263a', '\u263b', '\u2726', '\u2600'};
    public Random random;
    private final int size = 8;

    public int getSize() {return size;}

    Board(){
        board = new char[size][size];
        random = new Random();
        initialize();
    }
    public void initialize(){
        do {
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    board[r][c] = symbols[random.nextInt(symbols.length)];
                }
            }
        } while (!noThreeInARow());

    }
    public boolean noThreeInARow() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c <= size - 3; c++) {
                if (board[r][c] == board[r][c + 1] && board[r][c] == board[r][c + 2]) {
                    return false;
                }
            }
        }
        for (int r = 0; r <= size - 3; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == board[r + 1][c] && board[r][c] == board[r + 2][c]) {
                    return false;
                }
            }
        }

        return true;
    }
    public void showBoard() {
        System.out.println("    0   1   2    3   4    5    6    7");
        for (int r = 0; r < size; r++) {
            System.out.print(r + "  ");
            for (int c = 0; c < size; c++) {
                System.out.print(board[r][c] + "   ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public boolean isValidMove (int row, int col, char direction) {
            switch (direction) {
                case 'w': return row > 0;
                case 'a': return col > 0;
                case 's': return row < size - 1;
                case 'd': return col < size - 1;
                default: return false;
           }

    }
    public void swap(int row, int col, char direction){
        int newRow = row;
        int newCol = col;
        switch (direction) {
            case 'w':
                newRow--; break;
            case 'a':
                newCol--; break;
            case 's':
                newRow++; break;
            case 'd':
                newCol++; break;
        }
        char temp = board[row][col];
        board[row][col] = board[newRow][newCol];
        board[newRow][newCol] = temp;

    }
    public void checkCombos(Player currentPlayer, Player opponentPlayer) {
        boolean foundCombo;
        boolean starTransform = false;
        boolean smileTransform = false;
        
        do {
            foundCombo = false;
            boolean[][] remove = new boolean[size][size];

            for (int r = 0; r < size; r++) {
                for (int c = 0; c <= size - 3; c++) {
                    char s = board[r][c];
                    if (s != 0 && s == board[r][c + 1] && s == board[r][c + 2]) {
                        remove[r][c] = true;
                        remove[r][c + 1] = true;
                        remove[r][c + 2] = true;
                        foundCombo = true;
                    }
                }
            }

            for (int r = 0; r <= size - 3; r++) {
                for (int c = 0; c < size; c++) {
                    char s = board[r][c];
                    if (s != 0 && s == board[r + 1][c] && s == board[r + 2][c]) {
                        remove[r][c] = true;
                        remove[r + 1][c] = true;
                        remove[r + 2][c] = true;
                        foundCombo = true;
                    }
                }
            }

            if (!foundCombo) break;

            java.util.Map<Character, Integer> counters = new java.util.HashMap<>();
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (remove[r][c]) {
                        char symbol = board[r][c];
                        counters.put(symbol, counters.getOrDefault(symbol, 0) + 1);
                        
                        if (symbol == '\u263a') starTransform = true;
                        if (symbol == '\u263b') smileTransform = true;
                        
                        board[r][c] = 0;
                    }
                }
            }

            for (java.util.Map.Entry<Character, Integer> entry : counters.entrySet()) {
                char symbol = entry.getKey();
                applySymbolEffect(currentPlayer, opponentPlayer, symbol);
            }

            if (starTransform) {
                transformElements('\u2620', '\u271a');
                System.out.println("Transformation ☺: All ☠ became ✚");
                starTransform = false;
            }
            if (smileTransform) {
                transformElements('\u271a', '\u2620');
                System.out.println("Transformation ☻: All ✚ became ☠");
                smileTransform = false;
            }

            for (int c = 0; c < size; c++) {
                int writeRow = size - 1;
                for (int r = size - 1; r >= 0; r--) {
                    if (board[r][c] != 0) {
                        board[writeRow][c] = board[r][c];
                        writeRow--;
                    }
                }
                while (writeRow >= 0) {
                    board[writeRow][c] = symbols[random.nextInt(symbols.length)];
                    writeRow--;
                }
            }

        } while (true);

    }
    public void applySymbolEffect(Player currentPlayer, Player opponentPlayer, char symbol) {
        switch (symbol) {
            case '\u2620':
                opponentPlayer.removeHealth(1);
                System.out.println(currentPlayer.getName() + " removed 1 health from the opponent with ☠");
                break;
            case '$':
                currentPlayer.addGold(1);
                currentPlayer.addPoints(5);
                System.out.println(currentPlayer.getName() + " gained +5 points for $");
                break;
            case '\u271a':
                currentPlayer.addHealth(1);
                currentPlayer.addPoints(8);
                System.out.println(currentPlayer.getName() + " gained +8 points for ✚");
                break;
            case '\u263a':
                currentPlayer.addPoints(3);
                System.out.println(currentPlayer.getName() + " gained +3 points for ☺ (transformation)");
                break;
            case '\u263b':
                currentPlayer.addPoints(3);
                System.out.println(currentPlayer.getName() + " gained +3 points for ☻ (transformation)");
                break;
            case '\u2600':
                opponentPlayer.setGold(0);
                currentPlayer.addPoints(6);
                System.out.println(currentPlayer.getName() + " gained +6 points for ☀");
                break;
            case '\u2726':
                currentPlayer.addEXP(1);
                currentPlayer.addPoints(4);
                System.out.println(currentPlayer.getName() + " gained +4 points for ✦");
                break;
        }
    }
    public void transformElements(char from, char to) {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == from) {
                    board[r][c] = to;
                }
            }
        }
    }

    public String exportBoard() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                sb.append(board[r][c]);
            }
        }
        return sb.toString();
    }

    public void restoreBoard(String data) {
        int index = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (index < data.length()) {
                    board[r][c] = data.charAt(index++);
                }
            }
        }
    }
}

