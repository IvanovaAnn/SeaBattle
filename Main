import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Field field1 = new Field();
        Field field2 = new Field();
        Scanner console = new Scanner(System.in);
        System.out.println("Выберете режим первого игрока:" +
                "\n manually - самостоятельная расстановка кораблей и игра" +
                "\n auto - автоматическая расстановка кораблей, самостоятельная игра" +
                "\n bot - автоматическая расстановка кораблей, играет бот");
        String auto1 = console.nextLine().trim();
        System.out.println("Выберете режим второго игрока:" +
                "\n manually - самостоятельная расстановка кораблей и игра" +
                "\n auto - автоматическая расстановка кораблей, самостоятельная игра" +
                "\n bot - автоматическая расстановка кораблей, играет бот");
        String auto2 = console.nextLine().trim();
        Gamer gamer1 = new Gamer(1, field1, field2, auto1);
        Gamer gamer2 = new Gamer(2, field2, field1, auto2);
        gamer1.location_of_ships();
        gamer2.location_of_ships();
        while (gamer1.alive() && gamer2.alive()){
            System.out.print("\033[H\033[2J\n");
            gamer1.motion();
            if (!gamer2.alive()){
                System.out.println("Победа первого игрока." +
                        "\n Итоговые поля: \n");
                gamer1.showAllFields();
                break;
            }
            gamer2.motion();
            if (!gamer1.alive()){
                System.out.println("Победа второго игрока." +
                        "\n Итоговые поля: \n");
                gamer2.showAllFields();
            }
        }
    }
}
