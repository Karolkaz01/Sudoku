import Game.Game;

import java.io.*;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Sudoku {

    private int[][] board = {
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
    };
    private int[][] compleatBoard;
    private long time,startTime;
    private int mistakes;
    private String difficulty;
    private static final int EMPTY = 0;
    private static final int SIZE = 9;
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String alf = "ABCDEFGHI ";


    public Sudoku() {
        System.out.println("Wybierz poziom trudności: ");
        System.out.println("- easy");
        System.out.println("- medium");
        System.out.println("- hard");
        System.out.print("Wybór: ");
        Scanner s = new Scanner(System.in);
        String difficult;
        boolean wrong;
        do{
            difficult = s.nextLine();
            System.out.println("============================================================");
            wrong = false;
            switch (difficult) {
                case "easy":
                    generateSudoku(25);
                    compleatBoard=copyBoard();
                    remover(1); //43
                    this.difficulty = "easy";
                    break;
                case "medium":
                    generateSudoku(25);
                    compleatBoard=copyBoard();
                    remover(51);
                    this.difficulty = "medium";
                    break;
                case "hard":
                    generateSudoku(25);
                    compleatBoard=copyBoard();
                    remover(55);
                    this.difficulty = "hard";
                    break;
                default:
                    System.out.println("Źle podane dane!");
                    wrong=true;
            }
        }while (wrong);
        this.mistakes=0;
        this.time = 0;
        this.startTime = System.currentTimeMillis();
    }

    public Sudoku(String filePath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            String[] codes;
            for (int i = 0; i < SIZE; i++) {
                line = br.readLine();
                codes = line.split("");
                for (int j = 0; j < SIZE; j++) {
                    this.board[i][j] = Integer.parseInt(codes[j]);
                }
            }
            this.compleatBoard = new int[SIZE][SIZE];
            for (int i = 0; i < SIZE; i++) {
                line = br.readLine();
                codes = line.split("");
                for (int j = 0; j < SIZE; j++) {
                    this.compleatBoard[i][j] = Integer.parseInt(codes[j]);
                }
            }
            this.time = (long) Long.parseLong(br.readLine());
            this.startTime = System.currentTimeMillis() - this.time;
            this.mistakes = (int) Integer.parseInt(br.readLine());
            this.difficulty = br.readLine();
            br.close();
        }catch (IOException e){
            System.out.println("Błąd podczas odczytu pliku");
        }
    }

    public void tutorial(){
        System.out.print("Aby wpisać liczbę w konkretną komórkę należy wpisać kolejno jej nr. wiersza ,literę oznaczającą kolumnę oraz wpisywaną liczbę,\nnp.jeśli chcemy wpisać 7 w 3 rzędzie i 6 kolumnie należy wpisać \"3F7\" lub\"3f7\",\njeśli chcesz zapisać oraz opuścić grę należy wpisać \"END\"\n");
    }

    public void saveGame(){
        try {
            File f = new File("LastGame.txt");
            FileWriter fw = new FileWriter(f);
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    fw.write(String.valueOf(this.board[i][j]));
                }
                fw.write("\n");
            }

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    fw.write(String.valueOf(this.compleatBoard[i][j]));
                }
                fw.write("\n");
            }
            time = System.currentTimeMillis() - startTime;
            String time = String.valueOf(this.time);
            fw.write(time + "\n");
            fw.write(this.mistakes + "\n");
            fw.write(this.difficulty + "\n");
            fw.close();
        }catch (IOException e){
            System.out.println("Błąd podczas zapisu gry");
        }
    }

    public void move(String line){
        try {
            String[] codes = line.split("");
            if (this.board[Integer.parseInt(codes[0]) - 1][alf.indexOf(codes[1].toUpperCase(Locale.ROOT))] != 0) {
                System.out.println("Możesz zmieniać tylko puste pola!!!");
                mistakes++;
            } else if (compleatBoard[Integer.parseInt(codes[0]) - 1][alf.indexOf(codes[1].toUpperCase(Locale.ROOT))] != Integer.parseInt(codes[2])) {
                System.out.println("Źle!!!");
                mistakes++;
            } else {
                this.board[Integer.parseInt(codes[0]) - 1][alf.indexOf(codes[1].toUpperCase(Locale.ROOT))] = Integer.parseInt(codes[2]);
                if (isEnd()) {
                    time = Math.round((System.currentTimeMillis()-startTime)/1000);
                    System.out.println("!!!GRATULACJE!!!  Popełniłeś "+mistakes+" błędów, a twój czas to: "+time + " s.");
                    System.out.println("Podaj sowją nazwę: ");
                    Scanner s = new Scanner(System.in);
                    Game game = new Game(time, s.nextLine(), mistakes, difficulty);
                    System.out.println("============================================================");
                    addScore(game);
                    File f = new File("LastGame.txt");
                    if(f.exists()) f.delete();
                }
            }
        }catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Źle podane dane!");
        }
    }

    public void addScore(Game game) {
        try {
            LinkedList<Game> board = new LinkedList<>();
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
                while (true) board.add((Game) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                if (ois != null) ois.close();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
                    for (int i = 0; i < board.size(); i++)
                        oos.writeObject(board.get(i));

                    oos.writeObject(game);
                    oos.flush();
                } finally {
                    if (oos != null) oos.close();
                }
            }
        }catch (IOException e){
            System.out.println("Błąd poczas zapisu do pliku");
        }
    }

    private void generateSudoku(int numbers) {
        Random rand = new Random();
        int num,row,col;
        do {
            clearBoard();
            for (int i = 0; i < numbers; ) {
                num = rand.nextInt(SIZE) + 1;
                row = rand.nextInt(SIZE);
                col = rand.nextInt(SIZE);
                if (isOk(row, col, num) && (board[row][col] == 0)) {
                    board[row][col] = num;
                    i++;
                }
            }
        }while (!solve());
    }

    private int[][] copyBoard(){
        int[][] copy = new int[SIZE][SIZE];
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                copy[i][j]=board[i][j];
            }
        }
        return copy;
    }

    private void clearBoard(){
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            board[i][j]=0;
        }
    }

    private void remover (int toRemove){
        Random rand = new Random();
        int num,row,col;
        for(int i=0;i<toRemove;)
        {
            row = rand.nextInt(SIZE);
            col = rand.nextInt(SIZE);

            if((num=board[row][col])!=0){
                board[row][col]=0;
                if(!trySolve(copyBoard()))
                {
                    board[row][col]=num;
                }else{
                    i++;
                }
            }
        }
    }

    private boolean isInRow(int row, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[row][i] == number)
                return true;

        return false;
    }

    private boolean isInCol(int col, int number) {
        for (int i = 0; i < SIZE; i++)
            if (board[i][col] == number)
                return true;

        return false;
    }

    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == number)
                    return true;

        return false;
    }

    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }

    private boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            board[row][col] = number;

                            if (solve()) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    private boolean trySolve(int [][] testBoard) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (testBoard[row][col] == EMPTY) {
                    for (int number = 1; number <= SIZE; number++) {
                        if (isOk(row, col, number)) {
                            testBoard[row][col] = number;

                            if (trySolve(testBoard)) {
                                return true;
                            } else {
                                testBoard[row][col] = EMPTY;
                            }
                        }
                    }

                    return false;
                }
            }
        }

        return true;
    }

    public boolean isEnd(){
        for(int i=0;i<SIZE;i++)
        {
            for(int j=0;j<SIZE;j++)
            {
                if(board[i][j]==0)
                {
                    return false;
                }
            }
        }
        return true;
    }


    public void display() {
        String colorChar = ANSI_BLUE+"+"+ANSI_RESET;
        System.out.println(ANSI_BLUE+"| A | B | C | D | E | F | G | H | I |"+ANSI_RESET);
        for (int i = 0; i < SIZE; i++) {
            if(i%3==0) System.out.println(ANSI_BLUE+"+---+---+---+---+---+---+---+---+---+---"+ANSI_RESET);
            else System.out.println(colorChar+ANSI_GREEN+"---+---+---"+colorChar+ANSI_GREEN+"---+---+---"+colorChar+ANSI_GREEN+"---+---+---"+colorChar+ANSI_BLUE+"---"+ANSI_RESET);
            for (int j = 0; j < SIZE; j++) {
                if(j%3==0) System.out.print(ANSI_BLUE+"|"+ANSI_RESET);
                else System.out.print(ANSI_GREEN+"|"+ANSI_RESET);
                if(board[i][j]==0) System.out.print("   ");
                else System.out.print(" " + board[i][j] + " ");
            }
            System.out.println(ANSI_BLUE+"| " + (i+1) + ANSI_RESET);
        }
        System.out.println(ANSI_BLUE+"+---+---+---+---+---+---+---+---+---+---"+ANSI_RESET);

        System.out.println();
    }

//    public void display() {
//        System.out.println("| A | B | C | D | E | F | G | H | I |");
//        for (int i = 0; i < SIZE; i++) {
//            if(i%3==0) System.out.println("+---+---+---+---+---+---+---+---+---+---");
//            else System.out.println("+---+---+---+---+---+---+---+---+---+---");
//            for (int j = 0; j < SIZE; j++) {
//                if(j%3==0) System.out.print("|");
//                else System.out.print("|");
//                if(board[i][j]==0) System.out.print("   ");
//                else System.out.print(" " + board[i][j] + " ");
//            }
//            System.out.println("| " + (i+1));
//        }
//        System.out.println("+---+---+---+---+---+---+---+---+---+---");
//
//        System.out.println();
//    }
}
