import java.util.Scanner;

/**
 * @author Eemeli Allisto
 * @version 4.0
 * @since 1.8
 */
public class TicTacToe {

    /**
     * Gameboard.
     */
    public static char[][] gameboard;

    /**
     * How many rows to win. Set by player.
     */
    public static int amountToWin = 0;

    /**
     * Gameboard size (size x size). Set by player.
     */
    public static int size = 0;

    /**
     * Used for taking user inputs in methods that need it.
     */
    public static Scanner input = new Scanner(System.in);

    /**
     * Coordinate for row.
     */
    public static int row = 0;

    /**
     * Coordinate for column.
     */
    public static int column = 0;

    /**
     * Char that indicates whose turn it is.
     */
    public static char turn = 'X';

    /**
     * Integer that counts turns.
     */
    public static int turnCounter = 0;

    /**
     * The main method.
     *
     * <p>Asks gameboard size from the user. Asks wincondition from the user. Creates gameboard and initializes it. Starts the gameplay loop.
     *
     * @param  args  Command line parameters. Not Used.
     */
    public static void main(String[] args) {
        askSize();
        if(size >= 10) {
            askWinConditionFiaR();
        } else {
            askWinConditionTTT();
        }
        gameboard = new char[size][size];
        createBoard();
        playGame();
    }

    /**
     * This method serves as the main gameplay loop for the entire game.
     * 
     * <p>Prints empty gameboard. While loop lasts until either win or draw happens. Takes player move first, prints the board and then checks if player won or if a draw happens. 
     * Does the same loop for computer until either player wins or a draw happens. If statement afterwards checks if the game ended in a draw or if either player won.
     */
    public static void playGame() {
        boolean gameOver = false;
        printBoard();
        while(gameOver == false) {
            if(turn == 'X') {
                askPlayerMove();
                turn = 'O';
            } else {
                askCpuMove();
                turn = 'X';
            }
            printBoard();
            System.out.println();
            gameOver = checkIfDraw();
            gameOver = checkForWinner();
        }

        if(turnCounter == size * size) {
            System.out.println("Game over, it's a draw!");
        } else if(turnCounter != size * size) {
            System.out.println("Game over, winner is: " + turn);
        }
    }

    /**
     * This method is used for making the player's move.
     * 
     * <p>Adds +1 to turn counter. Do while loop asks player to input a integer until player inputs a valid one. Integers are sent to wrongInput(); method that takes care of the validation. 
     * After the loop, gameboard gets updated with the player's char symbol. Char is placed into the coordinates given by the player earlier.
     */
    public static void askPlayerMove() {
        turnCounter++;
        do {
            System.out.println("Please enter a valid row: ");
            row = input.nextInt();
            System.out.println("Please enter a valid column: ");
            column = input.nextInt();
        } while(wrongInput(row, column));
        gameboard[row - 1][column - 1] = turn;
    }

    /**
     * This method checks if either the player or the computer won.
     *
     * <p>Goes through the gameboard array. If players or computers char symbol is found, then the current positions coordinates are sent to these four methods. 
     * If any of these four method returns true, then someone has won and the method returns true. Otherwise this method returns false.
     *
     * @return Returns true if someone won. Otherwise returns false.
     */
    public static boolean checkForWinner() {
        boolean win = false;
        for(int i=0; i<gameboard.length; i++) {
            for(int j=0; j<gameboard.length; j++) {
                if(gameboard[i][j] == turn) {
                    if(checkHorizontal(i, j)) {
                        win = true;
                    } else if(checkVertical(i, j)) {
                        win = true;
                    } else if(checkDiagonal(i, j)) {
                        win = true;
                    } else if(checkAntiDiagonal(i, j)) {
                        win = true;
                    }
                }  
            }
        }
        return win;
    }

    /**
     * This method checks if someone won by counting the array down to up, left to right.
     *
     * <p>While loop checks if the next coordinates on the gameboard -/+ offset has player char in it. If the next position has player char in it, counter goes up by one. Offset is also increased by one.
     * The counter counts how many player chars are next to eachother. When the counter has same value as the amount needed to win the game, loop ends and method returns true.
     * If the next coordinates don't have the player char in it, loop breaks and the method returns false. If the coordinates try to go over the gameboard, loop ends and the method returns false.
     *
     * @param  i  Row coordinate of the gameboard.
     * @param  j  Column coordinate of the gameboard.
     * @return      Returns true if the amount of adjacent player chars match the amount of chars needed to win the game. Otherwise returns false.
     */
    public static boolean checkAntiDiagonal(int i, int j) {
        boolean win = false;
        int counter = 0;
        int offset = 0;
        while(true) {
            if(gameboard[i-offset][j+offset] == turn) {
                counter++;
                if(counter >= amountToWin) {
                    win = true;
                    break;
                }
            } else {
                break;
            }
            offset++;
            if(i - offset < 0 || j + offset >= gameboard.length) {
                break;
            }
        }
        return win;
    }

    /**
     * This method checks if someone won by counting the array up to down, left to right.
     *
     * <p>While loop checks if the next coordinates on the gameboard -/+ offset has player char in it. If the next position has player char in it, counter goes up by one. Offset is also increased by one.
     * The counter counts how many player chars are next to eachother. When the counter has same value as the amount needed to win the game, loop ends and method returns true.
     * If the next coordinates don't have the player char in it, loop breaks and the method returns false. If the coordinates try to go over the gameboard, loop ends and the method returns false.
     *
     * @param  i  Row coordinate of the gameboard.
     * @param  j  Column coordinate of the gameboard.
     * @return      Returns true if the amount of adjacent player chars match the amount of chars needed to win the game. Otherwise returns false.
     */
    public static boolean checkDiagonal(int i, int j) {
        boolean win = false;
        int counter = 0;
        int offset = 0;
        while(true) {
            if(gameboard[i+offset][j+offset] == turn) {
                counter++;
                if(counter >= amountToWin) {
                    win = true;
                    break;
                }
            } else {
                break;
            }
            offset++;
            if(i + offset >= gameboard.length || j + offset >= gameboard.length) {
                break;
            }
        }
        return win;
    }

    /**
     * This method checks if someone won vertically.
     *
     * <p>While loop checks if the next coordinates on the gameboard has player char in it. If the next position has player char in it, counter goes up by one.
     * The counter counts how many player chars are next to eachother. When the counter has same value as the amount needed to win the game, loop ends and method returns true.
     * If the next coordinates don't have the player char in it, loop breaks and the method returns false.
     *
     * @param  start  Row coordinate where the iteration begins on the gameboard.
     * @param  j  Column coordinate of the gameboard.
     * @return      Returns true if the amount of adjacent player chars match the amount of chars needed to win the game. Otherwise returns false.
     */
    public static boolean checkVertical(int start, int j) {
        boolean win = false;
        int counter = 0;
        for(int i=start; i<gameboard.length; i++) {
            if(gameboard[i][j] == turn) {
                counter++;
                if(counter >= amountToWin) {
                    win = true;
                }
            } else {
                return win;
            }
        }
        return win;
    }

    /**
     * This method checks if someone won horizontally.
     *
     * <p>While loop checks if the next coordinates on the gameboard has player char in it. If the next position has player char in it, counter goes up by one.
     * The counter counts how many player chars are next to eachother. When the counter has same value as the amount needed to win the game, loop ends and method returns true.
     * If the next coordinates don't have the player char in it, loop breaks and the method returns false.
     *
     * @param  i  Row coordinate of the gameboard. coordinate where the iteration begins on the gameboard.
     * @param  start  Column coordinate where the iteration begins on the gameboard.
     * @return      Returns true if the amount of adjacent player chars match the amount of chars needed to win the game. Otherwise returns false.
     */
    public static boolean checkHorizontal(int i, int start) {
        boolean win = false;
        int counter = 0;
        for(int j=start; j<gameboard.length; j++) {
            if(gameboard[i][j] == turn) {
                counter++;
                if(counter >= amountToWin) {
                    win = true;
                }
            } else {
                return win;
            }
        }
        return win;
    }

    /**
     * This method checks if a draw happens.
     *
     * <p>Goes through the gameboard array and checks for empty spots. If there are no empty spots on the gameboard, it means that the gameboard is full and it results in a draw. In this case, the method returns true.
     * If the gameboard has empty spaces, it's not a draw and the method returns false.
     *
     * @return      Returns true if a draw happens. Otherwise returns false.
     */
    public static boolean checkIfDraw() {
        boolean draw = true;
        for(int i=0; i<gameboard.length && draw; i++) {
            for(int j=0; j<gameboard.length && draw; j++) {
                if(gameboard[i][j] == '_') {
                    draw = false;
                }
            }
        }
        return draw;
    }

    /**
     * This method checks if user defined value of integers are between the values specified by this method.
     *
     * @param  row  Integer for row.
     * @param  column  Integer for column.
     * @return      Returns true if the user defined values are within the boundaries specified in the if statement. Otherwise returns false.
     */
    public static boolean wrongInput(int row, int column) {
        if(row > size || row < 1 || column > size || column < 1 || !isEmpty(row, column)) {
            return true;
        } else {   
            return false;
        }
    }

    /**
     * This method checks if a spot on the gameboard is empty.
     *
     * @param  row  Integer for row.
     * @param  column  Integer for column.
     * @return      Returns true if the spot on the gameboard is empty. Otherwise returns false.
     */
    public static boolean isEmpty(int row, int column) {
        if(gameboard[row - 1][column - 1] == '_') {
            return true;
        } else {
            System.out.println("Error: Slot is already full! ");
            return false;
        }
    }

    /**
     * This method is used for making the computer's move.
     * 
     * <p>Adds +1 to turn counter. For loop rolls a random number within the boundaries of the gameboard. Checks if the random spot is empty. If the spot is empty, computer's char symbol is added to the gameboard.
     * Otherwise rolls another spot on the gameboard.
     */
    public static void askCpuMove() {
        turnCounter++;
        for(int i=0; i<1; i++) {
            int x = getRandomNumber(0, gameboard.length);
            int y = getRandomNumber(0, gameboard.length);
            if(gameboard[y][x] == '_') {
                gameboard[y][x] = turn;
            } else {
                i--;
            }
        }
    }

    /**
     This method is used when asking the size of the gameboard.
     *
     * <p>Loop continues until player inputs integer larger than 3.
     */
    public static void askSize() {
        while(size < 3) {
            System.out.println("Please give board size (min 3): ");
            size = input.nextInt();
        }
    }

    /**
     * Random number generator.
     * 
     * <p>Rolls a random integer between imported minimum and maximum values.
     * 
     * @param  min  Minimum value.
     * @param  max  Maximum value.
     * @return      Returns a random number between minimum and maximum value.
     */
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * This method is used for defining the amount of adjacent char symbols needed to win.
     *
     * <p>Loop continues until player inputs integer between 3 and the size of the gameboard.
     */
    public static void askWinConditionTTT() {
        while(amountToWin < 3 || amountToWin > size) {
            System.out.println("How many in a row to win (min 3, board size max)? ");
            amountToWin = input.nextInt();
        }
    }

    /**
     * This method is used for defining the amount of adjacent char symbols needed to win.
     *
     * <p>Loop continues until player inputs integer between 5 and the size of the gameboard.
     */
    public static void askWinConditionFiaR() {
        while(amountToWin < 5 || amountToWin > size) {
            System.out.println("How many in a row to win (min 5, board size max)? ");
            amountToWin = input.nextInt();
        }
    }

    /**
     * This method initializes the gameboard.
     * 
     * <p>Fills the gameboard array with chars that describe empty slots.
     */
    public static void createBoard() {
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                gameboard[i][j] = '_';
            }
        }
    }

    /**
     * This method prints the gameboard.
     *
     * <p>Prints the left side boundary for the gameboard and then prints the gameboard and right side boundaries of the slots.
     */
    public static void printBoard() {
        size = gameboard.length;
        for(int i=0; i<gameboard.length; i++) {
            for(int j=0; j<gameboard.length; j++) {
                if(j == 0)
                    System.out.print("| ");
                    System.out.print(gameboard[i][j] + " | ");
            }
            System.out.println();
        }
    }
}