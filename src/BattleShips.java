import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BattleShips {
    private int numRows;
    private int numCols;
    private int playerShips;
    private int computerShips;
    private String[][] grid;
    private String[][] normalGrid;
    private boolean cheat;
    private int tries;
    private String name;

    public BattleShips(String name, boolean cheat) {
        numRows = 4;
        numCols = 4;
        playerShips = 5;
        computerShips = 5;
        grid = new String[numRows][numCols];
        normalGrid = new String[numRows][numCols];
        this.cheat = cheat;
        tries = 0;
        this.name = name;
    }

    public BattleShips(){
        numRows = 4;
        numCols = 4;
        playerShips = 5;
        computerShips = 5;
        grid = new String[numRows][numCols];
        normalGrid = new String[numRows][numCols];
        tries = 0;
    }

    public void createOceanMap() {
        System.out.print("  "); //add space to the top
        for (int i = 0; i < numCols; i++)
            System.out.print(i); //0123
        System.out.println(); //next line
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0) { //if it is the beginning
                    System.out.print(i + "|" + grid[i][j]); // 0|
                } else if (j == grid[i].length - 1) { //if it is right before the end
                    System.out.print(grid[i][j] + "|" + i); //  |0
                } else {
                    System.out.print(grid[i][j]); // print out the blanks
                }
            }
            System.out.println(); //0|    |01|    |12|    |23|    |3  0123 helps skip lines
        }
        System.out.print("  "); // add space to the bottom
        for (int i = 0; i < numCols; i++) {
            System.out.print(i); //print bottom numbers
        }
    }

    public void deployPlayerShips() {
        Scanner input = new Scanner(System.in);
        System.out.println("\nDeploy your ships:");
        for (int i = 1; i <= playerShips; ) {
            System.out.print("Enter row for your " + i + " ship: ");
            int x = input.nextInt();
            System.out.print("Enter column for your " + i + " ship: ");
            int y = input.nextInt();
            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) {
                grid[x][y] = "@";
                i++;
            }
            else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@") {
                System.out.println("You can't place two or more ships on the same location");
            }
            else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
            }
        }
        if (!cheat) {
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[0].length; c++) {
                    normalGrid[r][c] = grid[r][c];
                }
            }
        }
        printOceanMap();
    }

    public void deployComputerShips() {
        System.out.println("\nComputer is deploying ships");
        if (cheat) {
            for (int i = 1; i <= computerShips; ) {
                int x = (int) (Math.random() * 10); // random row
                int y = (int) (Math.random() * 10); // random column
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) { //if the grid is empty
                    grid[x][y] = "?"; //places location of the ship
                    System.out.println(i + ". ship DEPLOYED");
                    i++;
                }
            }
        } else if (!cheat) {
            for (int i = 1; i <= computerShips; ) {
                int x = (int) (Math.random() * 10);
                int y = (int) (Math.random() * 10);
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) {
                    grid[x][y] = "?";
                    normalGrid[x][y] = " ";
                    System.out.println(i + ". ship DEPLOYED");
                    i++;
                }
            }
        }
        printOceanMap();
    }

    public void Battle() {
        playerTurn();
        computerTurn();
        printOceanMap();
        System.out.println();
        System.out.println("Your ships: " + playerShips + " | Computer ships: " + computerShips);
        System.out.println();
    }

    public void playerTurn() {
        System.out.println("\nYOUR TURN");
        int x = -1;
        int y = -1;
        if (cheat) {
            while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter X coordinate: ");
                x = input.nextInt();
                System.out.print("Enter Y coordinate: ");
                y = input.nextInt();
                tries++;
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                    if (grid[x][y] == "?") {
                        System.out.println("Boom! You sunk the ship!");
                        grid[x][y] = "!";
                        computerShips--;
                    } else if (grid[x][y] == "@") {
                        System.out.println("Oh no, you sunk your own ship");
                        grid[x][y] = "!";
                        playerShips--;
                    } else if (grid[x][y] == " ") {
                        System.out.println("Sorry, you missed");
                        grid[x][y] = "-";
                    }
                } else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                    System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
                }
            }
        }
        if (!cheat) {
            while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter X coordinate: ");
                x = input.nextInt();
                System.out.print("Enter Y coordinate: ");
                y = input.nextInt();
                tries++;
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                    if (grid[x][y] == "?") {
                        System.out.println("Boom! You sunk the ship!");
                        grid[x][y] = "!";
                        normalGrid[x][y] = "!";
                        computerShips--;
                    } else if (grid[x][y] == "@") {
                        System.out.println("Oh no, you sunk your own ship");
                        grid[x][y] = "!";
                        normalGrid[x][y] = "!";
                        playerShips--;
                    } else if (grid[x][y] == " ") {
                        System.out.println("Sorry, you missed");
                        grid[x][y] = "-";
                        normalGrid[x][y] = "-";
                    }
                } else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                    System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
                }
            }
        }
    }

    public void computerTurn() {
        System.out.println("\nCOMPUTER'S TURN");
        int x = -1;
        int y = -1;
        if (cheat) {
            while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                    if (grid[x][y] == "@") {
                        System.out.println("The Computer sunk one of your ships!");
                        grid[x][y] = "x";
                        playerShips--;
                    } else if (grid[x][y] == "?") {
                        System.out.println("The Computer sunk one of its own ships");
                        grid[x][y] = "!";
                        computerShips--;
                    } else if (grid[x][y] == " ") {
                        System.out.println("Computer missed");
                    }
                }
            }
        }
        if (!cheat) {
            while ((x < 0 || x >= numRows) || (y < 0 || y >= numCols)) {
                x = (int) (Math.random() * 10);
                y = (int) (Math.random() * 10);
                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                    if (grid[x][y] == "@") {
                        System.out.println("The Computer sunk one of your ships!");
                        grid[x][y] = "x";
                        normalGrid[x][y] = "x";
                        playerShips--;
                    } else if (grid[x][y] == "?") {
                        System.out.println("The Computer sunk one of its own ships");
                        grid[x][y] = "!";
                        normalGrid[x][y] = "!";
                        computerShips--;
                    } else if (grid[x][y] == " ") {
                        System.out.println("Computer missed");
                    }
                }
            }
        }
    }

    public void gameOver() {
        System.out.println("Your ships: " + playerShips + " | Computer ships: " + computerShips);
        if (playerShips > 0 && computerShips <= 0) {
            PlayerData p = new PlayerData(name, tries);
            p.save();
            System.out.println("Hooray " + name + "! " + "You won the battle :)");
            System.out.println("You won in " + tries + " turns");
            System.out.println("Check the Leaderboard for your name");
        } else {
            System.out.println("Sorry, you lost the battle :(");
            System.out.println();
        }
    }

    public void printOceanMap() {
        if (!cheat) {
            System.out.println();
            System.out.print("  ");
            for (int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
            for (int x = 0; x < normalGrid.length; x++) {
                System.out.print(x + "|");
                for (int y = 0; y < normalGrid[x].length; y++) {
                    System.out.print(normalGrid[x][y]);
                }
                System.out.println("|" + x);
            }
            System.out.print("  ");
            for (int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }
        if (cheat) {
            System.out.println(); //space on the top
            System.out.print("  "); //space for the top numbers
            for (int i = 0; i < numCols; i++)
                System.out.print(i); //print the numbers on the top
            System.out.println(); //01230|@@  |0
            for (int x = 0; x < grid.length; x++) {
                System.out.print(x + "|"); // print the 0|
                for (int y = 0; y < grid[x].length; y++) {
                    System.out.print(grid[x][y]); //print the deployed ships
                }
                System.out.println("|" + x); // prints |0
            }
            System.out.print("  ");
            for (int i = 0; i < numCols; i++)
                System.out.print(i); //print bottom numbers
            System.out.println();
        }
    }

    public void start() {
        Scanner s = new Scanner(System.in);
        String n = "";
        String mode = "";
        boolean cheat = false;
        ArrayList<String> leaderboard = new ArrayList<String>();
        System.out.println("Welcome to BattleShips");
        System.out.print("Would you like to (s)how leaderboard or start a (n)ew game? ");
        String option = s.nextLine();
        if (option.toLowerCase().equals("s")) {
            try {
                File f = new File("src/game.data");
                Scanner line = new Scanner(f);
                System.out.println("LeaderBoard");
                while (line.hasNextLine()) {
                    String data = line.nextLine();
                    leaderboard.add(data);
                }
                for (int i = 1; i < leaderboard.size() + 1; i++) {
                    System.out.println(i + ") Admiral " + leaderboard.get(i - 1));
                }
            } catch (FileNotFoundException e) {
                System.out.println("There are no games to show.");
                System.out.println("Starting new game...");
                option = "n";
            }
        }
        if (option.toLowerCase().equals("n")) {
            System.out.print("Hello Admiral what is your name? ");
            Scanner in = new Scanner(System.in);
            n = in.nextLine();
            System.out.print("Hello Admiral " + n + " would you like to receive the information from our spy? (Cheat Mode) ");
            mode = in.nextLine();
            if (mode.toLowerCase().equals("yes")) {
                cheat = true;
            } else {
                cheat = false;
            }
            BattleShips b = new BattleShips(n,cheat);
            b.createOceanMap();
            b.deployPlayerShips();
            b.deployComputerShips();
            while (b.getPlayerShips() != 0 && b.getComputerShips() != 0) {
                b.Battle();
            }
            b.gameOver();
        }
    }

    public int getComputerShips() {
        return computerShips;
    }

    public int getPlayerShips() {
        return playerShips;
    }

}
