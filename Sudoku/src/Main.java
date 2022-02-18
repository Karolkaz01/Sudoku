import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner s1 = new Scanner(System.in);
        boolean wrong1;
        System.out.println("============================================================");
        do{
            wrong1=true;
            System.out.println("Menu: ");
            System.out.println("1. Nowa gra");
            System.out.println("2. Continue");
            System.out.println("3. Wyniki");
            System.out.println("4. Wyjdź");
            System.out.print("Wybór: ");
            switch (s1.nextLine()){
                case "1" :
                    System.out.println("============================================================");
                    Sudoku game = new Sudoku();
                    String move1;
                    game.tutorial();
                    do{
                        game.display();
                        System.out.print("Ruch: ");
                        if((move1=s1.nextLine()).equals("END")){
                            System.out.println("============================================================");
                            game.saveGame();
                            break;
                        }
                        System.out.println("============================================================");
                        game.move(move1);
                    }while (!game.isEnd());
                    break;
                case "2" :
                    System.out.println("============================================================");
                    File f = new File("LastGame.txt");
                    if(!f.exists()) System.out.println("Nie ma gry do kontynuacji");
                    else {
                        Sudoku LastGame = new Sudoku("LastGame.txt");
                        String move2;
                        do {
                            LastGame.display();
                            System.out.print("Ruch: ");
                            if ((move2 = s1.nextLine()).equals("END")) {
                                LastGame.saveGame();
                                break;
                            }
                            LastGame.move(move2);
                        } while (!LastGame.isEnd());
                    }
                    break;
                case "3" :
                    System.out.println("============================================================");
                    Leaderboard lb = new Leaderboard();
                    boolean wrong2;
                    do {
                        wrong2 = true;
                        System.out.println("\tMenu tabeli wyników:");
                        System.out.println("\t1. Zmień nazwę wyniku");
                        System.out.println("\t2. Usuń wynik");
                        System.out.println("\t3. Filtry");
                        System.out.println("\t4. Sortój");
                        System.out.println("\t5. Powrót do menu");
                        System.out.print("\tWybór: ");
                        switch (s1.nextLine()) {
                            case "1" :
                                lb.changeName();
                                break;
                            case "2" :
                                lb.delateRecord();
                                break;
                            case "3" :
                                System.out.println("============================================================");
                                boolean wrong3;
                                do {
                                    wrong3 = false;
                                    System.out.println("\tWybierz filtr:");
                                    System.out.println("\tA. Nazwa");
                                    System.out.println("\tB. Poziom tródności");
                                    System.out.println("\tC. Liczba błędów");
                                    System.out.print("\tWybór: ");
                                    switch (s1.nextLine()) {
                                        case "A":
                                            lb.displayByName();
                                            break;
                                        case "B":
                                            lb.displayByDifficulty();
                                            break;
                                        case "C":
                                            lb.displayByMistakes();
                                            break;
                                        default:
                                            System.out.println("============================================================");
                                            System.out.println("\tNie ma takiego wyboru");
                                            wrong3 = true;
                                    }
                                }while(wrong3);
                                break;
                            case "4" :
                                System.out.println("============================================================");
                                boolean wrong4;
                                do {
                                    wrong4 = false;
                                    System.out.println("\tSortowannie po:");
                                    System.out.println("\tA. Nazwie");
                                    System.out.println("\tB. Liczbie błędów");
                                    System.out.println("\tC. Czasie");
                                    System.out.println("\tD. Poziomie trudności");
                                    System.out.print("\tWybów: ");
                                    switch (s1.nextLine()) {
                                        case "A":
                                            System.out.println("============================================================");
                                            lb.sortByName();
                                            break;
                                        case "B":
                                            System.out.println("============================================================");
                                            lb.sortByMistakes();
                                            break;
                                        case "C":
                                            System.out.println("============================================================");
                                            lb.sortByTime();
                                            break;
                                        case "D":
                                            System.out.println("============================================================");
                                            lb.sortByDifficulty();
                                            break;
                                        default:
                                            System.out.println("============================================================");
                                            System.out.println("\tNie ma takiego wyboru");
                                            wrong4 = true;
                                    }
                                }while (wrong4);
                                break;
                            case "5" :
                                System.out.println("============================================================");
                                wrong2 = false;
                                break;

                            default:
                                System.out.println("============================================================");
                                System.out.println("\tNie ma takiego wyboru");
                                wrong2 = true;
                        }
                    }while (wrong2);
                    break;
                case "4" :
                    System.out.println("============================================================");
                    System.out.println("");
                    wrong1 = false;
                    break;
                default:
                    System.out.println("============================================================");
                    System.out.println("Taka opcja nie istnieje!");
                    wrong1 = true;
            }
        }while (wrong1);
    }
}
