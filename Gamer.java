import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Gamer {
    private Integer number;
    private boolean alive;
    private int count_ships;
    private Field field;
    private Field hidden_field;
    private String auto;

    public Gamer(Integer number, Field field, Field hidden_field, String auto){
        System.out.println(number + " " + "gamer");
        this.number = number;
        this.alive = true;
        this.count_ships = 10;
        this.field = field; //Свое поле
        this.hidden_field = hidden_field; //Поле врага
        this.auto = auto;
    }

    public String auto_location_of_ships(Integer length){
        Random r = new Random();
        String str = "ABCDEFGHIJ";
        String start;
        String finish;
        boolean horizontal_arrangement = r.nextBoolean();
        if (horizontal_arrangement){
            String start_number = String.valueOf(r.nextInt(10) + 1);
            int number_start_letter = r.nextInt(10 - length);
            char start_letter = str.charAt(number_start_letter);
            start = start_number + start_letter;
            finish = start_number + str.charAt(number_start_letter + length - 1);
        }
        else {
            int number_start_number = r.nextInt(10 - length);
            String start_number = String.valueOf(number_start_number + 1);
            char start_letter = str.charAt(r.nextInt(10));
            start = start_number + start_letter;
            finish = String.valueOf(number_start_number + length) + start_letter;
        }
        return start + " " + finish;
    }

    public boolean checking_freedom_of_cells(HashSet<String> occupied_cells, String motion){
        String str = "ABCDEFGHIJ";
        String[] two_edges = motion.split(" ");
        String start = two_edges[0];
        String finish = two_edges[1];
        int start_number;
        int finish_number;
        if (start.length() == 2)
            start_number = Integer.parseInt(start.charAt(0)+"");
        else start_number = 10;
        if (finish.length() == 2)
            finish_number = Integer.parseInt(finish.charAt(0)+"");
        else finish_number = 10;
        int start_ind_letter = str.indexOf(start.charAt(start.length()-1));
        int finish_ind_letter = str.indexOf(finish.charAt(finish.length()-1));
        char finish_right_letter;
        if (finish_ind_letter < 9)
            finish_right_letter = str.charAt(finish_ind_letter+1) ;
        else finish_right_letter = 'K';

        char start_left_letter;
        if (start_ind_letter > 0)
            start_left_letter = str.charAt(start_ind_letter-1) ;
        else start_left_letter = 'A';

        for (int number = start_number - 1; number <= finish_number + 1; number++){
            for (char letter = start_left_letter; letter <= finish_right_letter; letter++){
                if (occupied_cells.contains(number + "" + letter)) {
                    if (auto.equals("manually")) {
                        System.out.println("Корабль не может стоять рядом");
                    }
                    return false;
                }
            }
        }

        if (motion.length() == 2) {
            if (occupied_cells.contains(Character.toString(motion.charAt(0) - 1) + (motion.charAt(1) + 1)) ||
                    occupied_cells.contains(Character.toString(motion.charAt(0) + 1) + (motion.charAt(1) + 1)) ||
                    occupied_cells.contains(Character.toString(motion.charAt(0) - 1) + (motion.charAt(1) - 1)) ||
                    occupied_cells.contains(Character.toString(motion.charAt(0) + 1) + (motion.charAt(1) - 1))) {
                if (auto.equals("manually")) {
                    System.out.println("Корабль не может стоять рядом");
                }
                return false;
            }
        }
        else {
            if (occupied_cells.contains("9" + (motion.charAt(2) - 1)) ||
                    occupied_cells.contains("9" + (motion.charAt(2) + 1))) {
                if (auto.equals("manually")) {
                    System.out.println("Корабль не может стоять рядом");
                }
                return false;
            }
        }
        return true;
    }


    public void location_of_ships(){
        String motion;
        HashSet<String> occupied_cells = new HashSet<>();
        Scanner console = new Scanner(System.in);
        int length = 1;
        while (true) {
            String cur[] = new String[1];
            if (auto.equals("manually")) {
                cur[0] = "i";
            }
            else {
                cur[0] = "i";
            }
            if (cur[0].equals("i")) {
                mainCicle: for (int i = 1; i < 11; i++){
                    if (i < 5){length = 1;}
                    else if (i < 8){length = 2;}
                    else if (i< 10){length=3;}
                    else length = 4;
                    if (auto.equals("auto") || auto.equals("bot")) {
                        do {
                            motion = auto_location_of_ships(length);
                        } while (!checking_freedom_of_cells(occupied_cells, motion));
                    }
                    else {
                        System.out.println("Если вы введёте корабль в неположенном месте (наложение кораблей), " +
                                "он не будет учитан");
                        do {
                            subCycle: while (true) {
                                System.out.println("Корабль длины " + length);
                                motion = console.nextLine();
                                String s[] = motion.split(" ");
                                if (s.length != 2) {
                                    System.out.println("Неверный формат ввода!");
                                    continue;
                                }
                                int startRow = Integer.parseInt(s[0].substring(0, s[0].length() - 1));
                                int endRow = Integer.parseInt(s[1].substring(0, s[1].length() - 1));
                                char startCol = s[0].charAt(s[0].length() - 1);
                                char endCol = s[1].charAt(s[1].length() - 1);
                                if (!((startRow >= 1 && startRow <= 10) && (endRow >= 1 && endRow <= 10)
                                        && (startCol >= 'A' && startCol <= 'J') && (endCol >= 'A' && endCol <= 'J') &&
                                        (length == Math.abs(startRow - endRow) + Math.abs(startCol - endCol) + 1))) {
                                    System.out.println("Неверный формат ввода!");
                                    continue subCycle;
                                }
                                break subCycle;
                            }
                        } while (!checking_freedom_of_cells(occupied_cells, motion));
                    }
                    for (String s : occupied_cells) {
                        if (s.equals(motion) ||
                                s.equals(String.valueOf(motion.charAt(0) - 1) + (motion.charAt(1) - 1)) ||
                                s.equals(String.valueOf(motion.charAt(0) + 1) + (motion.charAt(1) - 1)) ||
                                s.equals(String.valueOf(motion.charAt(0) - 1) + (motion.charAt(1) + 1)) ||
                                s.equals(String.valueOf(motion.charAt(0) + 1) + (motion.charAt(1) + 1))) {
                            System.out.println("Наложение клеток");
                            --i;
                            continue mainCicle; //Заново заходим на ту же итерацию цикла,
                            //считывающего все корабли
                        }
                    }
                    String[] two_edges = motion.split(" ");
                    String start = two_edges[0];
                    String finish = two_edges[1];
                    Ship ship = new Ship(length, this);
                    if (start.charAt(0) == finish.charAt(0) && start.length() == finish.length()
                            && start.length() == 2){
                        for (char ch = start.charAt(1); ch <= finish.charAt(1); ch++){
                            field.installing_ship(start.charAt(0) + "" + ch, ship);
                            occupied_cells.add(start.charAt(0) + "" + ch);
                        }
                    }
                    else if (start.charAt(0) == finish.charAt(0) && start.length() == finish.length()){
                        for (char ch = start.charAt(2); ch <= finish.charAt(2); ch++){
                            field.installing_ship("10" + ch, ship);
                            occupied_cells.add("10" + ch);
                        }
                    }
                    else {
                        for (int ch = Character.getNumericValue(start.charAt(0));
                             ch <= Character.getNumericValue(finish.charAt(0)); ch++){
                            field.installing_ship(ch + "" + start.charAt(start.length()-1), ship);
                            occupied_cells.add(ch + "" + start.charAt(start.length()-1));
                        }
                    }
                }
                break;
            }
        }

    }

    public String auto_motion(){
        if (!hidden_field.empty_hot_cell()){
            return hidden_field.get_hot_cell();
        }
        Random r = new Random();
        String number_cell = String.valueOf(r.nextInt(10) + 1);
        String str = "ABCDEFGHIJ";
        char letter_cell = str.charAt(r.nextInt(10));
        return number_cell + letter_cell;
    }

    public void showAllFields() {
        System.out.print("\033[H\033[2J\n");
        System.out.println("Поле игрока:\t\t\t\t\t\t\t\t\t\t\tПоле соперника:");
        System.out.println("\tA B C D E F G H I J \t\t\t\t\t\t\t\t\tA B C D E F G H I J ");
        String left, right;
        for (int i = 1; i < 11; ++i) {
            left = "";
            right = "";
            for (char j = 'A'; j < 'K'; ++j) {
                String stat = field.getDict_cells().get(Integer.toString(i) + j).showing_my_status();
                switch (stat) {
                    case "пустая": {
                        left = left.concat("- ");
                        break;
                    }
                    case "часть корабля целая": {
                        left = left.concat("ц ");
                        break;
                    }
                    case "битая пустая": {
                        left = left.concat("+ ");
                        break;
                    }
                    case "затонул": {
                        left = left.concat("з ");
                        break;
                    }
                    case "часть корабля битая": {
                        left = left.concat("х ");
                        break;
                    }
                    case "бронь": {
                        left = left.concat("- ");
                        break;
                    }
                    default: {
                        left = left.concat("o ");
                        break;
                    }
                }
                stat = hidden_field.getDict_cells().get(Integer.toString(i) + j).show_hidden_status();
                switch (stat) {
                    case "пустая": {
                        right = right.concat("- ");
                        break;
                    }
                    case "часть корабля целая": {
                        right = right.concat("ц ");
                        break;
                    }
                    case "битая пустая": {
                        right = right.concat("+ ");
                        break;
                    }
                    case "затонул": {
                        right = right.concat("з ");
                        break;
                    }
                    case "часть корабля битая": {
                        right = right.concat("х ");
                        break;
                    }
                    case "бронь": {
                        right = right.concat("- ");
                        break;
                    }
                    default: {
                        right = right.concat("o ");
                        break;
                    }
                }
            }
            System.out.println(i + "\t" + left + "\t\t\t\t\t\t\t\t" +
                    i + "\t" + right);
        }
        System.out.print("\n");
    }

    public void motion(){
        Boolean motion_try = false;
        String motion;
        while (! motion_try) {
            if (auto.equals("manually") || auto.equals("auto")) {
                Scanner console = new Scanner(System.in);
                System.out.println("Ход игрока номер " + number);
                motion = console.nextLine();
            }
            else motion = auto_motion();
            switch (motion.length()) {
                case 2: {
                    if (motion.charAt(0) < '1' || motion.charAt(0) > '9' ||
                            motion.charAt(1) < 'A' ||
                            motion.charAt(1) > 'J') {
                        if (auto.equals("manually") || auto.equals("auto")) {
                            System.out.println("Неверный формат ввода! Сначала введите число, затем букву без пробела.");
                        }
                        continue;
                    }
                    break;
                }
                case 3: {
                    if (motion.charAt(0) == '1' && motion.charAt(1) == '0'
                            && motion.charAt(2) >= 'A' && motion.charAt(2) <= 'J') {
                        break;
                    }
                }
                default: {
                    if (auto.equals("manually") || auto.equals("auto")) {
                        System.out.println("Неверный формат ввода! Сначала введите число, затем букву без пробела.");
                    }
                    continue;
                }
            }
            motion_try = hidden_field.shot(motion);
            if (motion_try) {
                if (auto.equals("bot")) System.out.println("Ход игрока номер " + number + " (бота) " + motion);
                else {
                    showAllFields();
                }
            }
            else if (!auto.equals("bot")) System.out.println("В это поле уже стреляли, повторите попытку.");
        }
    }

    public void ship_loss(){
        count_ships -= 1;
        if (count_ships <=0 ) alive = false;
    }

    public Boolean alive(){
        return alive;
    }
}
