import Game.*;

import java.io.*;
import java.util.*;

public class Leaderboard {
    List<Game> board = new LinkedList<>();
    private static final String PATH = "leaderboard.dat";

    public Leaderboard(){
        ObjectInputStream ois = null;
        try {
            try {
                ois = new ObjectInputStream(new FileInputStream(PATH));
                while (true) {
                    board.add((Game) ois.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
            } finally {
                if (ois != null) ois.close();
            }
        }catch (IOException e){
            System.out.println("Błąd podczas oczytywania z pliku");
        }
        display();
    }

    public void changeName(){
        System.out.print("\tPodaj numer wyniku do zmiany: ");
        Scanner s = new Scanner(System.in);
        try {
            int num = (int) Integer.parseInt(s.nextLine());
            System.out.print("\tProszę podać nową nazwę: ");
            String newName = s.nextLine();
            System.out.println("============================================================");
            this.board.get(num-1).setName(newName);
            save();
        }catch (NumberFormatException | NullPointerException e){
            System.out.println("============================================================");
            System.out.println("\tŹle podane dane!");
        }catch (IndexOutOfBoundsException e){
            System.out.println("\tŻle podane dane!");
        }
        display();
    }

    public void delateRecord(){
        System.out.print("\tPodaj numer wyniku do usunięcia: ");
        Scanner s = new Scanner(System.in);
        try {
            this.board.remove((int) Integer.parseInt(s.nextLine()) - 1);
            System.out.println("============================================================");
            save();
            display();
        }catch (NumberFormatException | NullPointerException | IndexOutOfBoundsException e){
            System.out.println("============================================================");
            System.out.println("\tŹle podane dane!");
        }
    }

    public void save(){
        try {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
                for (int i = 0; i < board.size(); i++)
                    oos.writeObject(board.get(i));
            } finally {
                if (oos != null) oos.close();
            }
        }catch (IOException e){
            System.out.println("Błąd podczas zapisu");
        }
    }

    public void displayByName(){
        System.out.print("\tPodaj nazwę: ");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        System.out.println("============================================================");
        System.out.println("\t   Nazwa           Błąd        Czas       Trudność");
        for(int i=0;i<board.size();i++)
        {
            if(board.get(i).getName().equals(name)) {
                System.out.println("\t"+(i+1) + ". " + board.get(i));
            }
        }
        System.out.println();
    }

    public void displayByDifficulty(){
        boolean wrong;
        String difficulty=null;
        Scanner s = new Scanner(System.in);
        do{
            wrong =false;
            System.out.println();
            System.out.println("\tWybierz poziom trudności do wyświetlenia: ");
            System.out.println("\t- easy");
            System.out.println("\t- medium");
            System.out.println("\t- hard");
            System.out.print("\tWybór: ");
            switch (s.nextLine()){
                case "easy" :
                    System.out.println("============================================================");
                    difficulty = "easy";
                    break;
                case "medium" :
                    System.out.println("============================================================");
                    difficulty = "medium";
                    break;
                case "hard" :
                    System.out.println("============================================================");
                    difficulty = "hard";
                    break;
                default:
                    System.out.println("============================================================");
                    System.out.println("Nie ma takiego poziomu trudności");
                    wrong=true;
            }
        }while (wrong);
        System.out.println("\t   Nazwa           Błąd        Czas       Trudność");
        for(int i=0;i<board.size();i++)
        {
            if(board.get(i).getDifficulty().equals(difficulty)) {
                System.out.println("\t"+(i+1) + ". " + board.get(i));
            }
        }
        System.out.println();
    }
    public void displayByMistakes(){
        System.out.print("\tPodaj liczbę błędów: ");
        Scanner s = new Scanner(System.in);
        int mistakes=(int) Integer.parseInt(s.nextLine());
        System.out.println("============================================================");
        System.out.println("\t   Nazwa           Błąd        Czas       Trudność");
        for(int i=0;i<board.size();i++)
        {
            if(board.get(i).getMistakes()==mistakes) {
                System.out.println("\t"+(i+1) + ". " + board.get(i));
            }
        }
        System.out.println();
    }

    public void sortByTime(){
        LinkedList<Game> board = copyBoard();
        if(board.size()==0) System.out.println("Tablica wyników jest pusta");
        else{
            Collections.sort(board);
            display(board);
        }
    }

    public void sortByName(){
        LinkedList<Game> board = copyBoard();
        if(board.size()==0) System.out.println("Tablica wyników jest pusta");
        else{
            Collections.sort(board, new CompareByName());
            display(board);
        }
    }

    public void sortByMistakes(){
        LinkedList<Game> board = copyBoard();
        if(board.size()==0) System.out.println("Tablica wyników jest pusta");
        else{
            Collections.sort(board, new CompareByMistakes());
            display(board);
        }
    }

    public void sortByDifficulty(){
        LinkedList<Game> board = copyBoard();
        if(board.size()==0) System.out.println("Tablica wyników jest pusta");
        else{
            Collections.sort(board, new CompareByDifficulty());
            display(board);
        }
    }

    private LinkedList<Game> copyBoard(){
        LinkedList board = new LinkedList<>();
        for(int i=0;i<this.board.size();i++)
            board.add(this.board.get(i));
        return board;
    }


    public void display(){
        System.out.println("\t   Nazwa           Błąd        Czas       Trudność");
        for(int i = 0;i<board.size();i++)
        {
            System.out.println("\t"+(i+1) + ". " + board.get(i));
        }
        System.out.println();
    }

    public void display(LinkedList<Game> board){
        System.out.println("\t   Nazwa           Błąd        Czas       Trudność");
        for(int i = 0;i<board.size();i++)
        {
            System.out.println("\t"+(i+1) + ". " + board.get(i));
        }
        System.out.println();
    }



}
