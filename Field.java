import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Field {
    private Map<String, Cell> dict_cells;
    private HashSet<String> broken_cells;
    private ArrayList<String> hot_list;

    {
        dict_cells = new HashMap<>();
        broken_cells = new HashSet<>();
        hot_list = new ArrayList<String>();
        for (Integer i = 1; i < 11; i++) {
            for (char j = 'A'; j < 'K'; j++){
                dict_cells.put(i.toString() + j, new Cell(i, j));
            }
        }
    }

    public void showing_my_field(){
        for (Integer i = 1; i < 11; i++) {
            for (char j = 'A'; j < 'K'; j++){
                String status = dict_cells.get(i.toString()+j).showing_my_status();
                switch (status){
                    case "пустая": System.out.print("-"); break;
                    case "часть корабля целая": System.out.print("ц"); break;
                    case "битая пустая": System.out.print("+"); break;
                    case "затонул": System.out.print("з"); break;
                    case "часть корабля битая": System.out.print("х"); break;
                    default: System.out.print("o"); break;
                }
            }
            System.out.println("");
        }
    }


    public void show_hidden_field(){
        for (Integer i = 1; i < 11; i++) {
            for (char j = 'A'; j < 'K'; j++){
                String status = dict_cells.get(i.toString()+j).show_hidden_status();
                switch (status){
                    case "пустая": System.out.print("-"); break;
                    case "часть корабля целая": System.out.print("ц"); break;
                    case "битая пустая": System.out.print("+"); break;
                    case "затонул": System.out.print("з"); break;
                    case "часть корабля битая": System.out.print("х"); break;
                    default: System.out.print("o"); break;
                }
            }
            System.out.println("");
        }
    }

    public boolean shot(String motion){
        String str = "ABCDEFGHIJ";
        if (broken_cells.contains(motion)) return false;
        Cell cell = dict_cells.get(motion);
        cell.shot();
        String status_cell = cell.showing_my_status();
        int motion_len = motion.length();
        System.out.println(motion_len);
        int number = Integer.parseInt(motion.substring(0, motion_len-1));
        int number_letter = str.indexOf(motion.charAt(motion_len-1));
        if (status_cell.equals("затонул")){
            for (Integer i = number - 1; i <= number + 1; i++) {
                    for (Integer j = number_letter - 1; j <= number_letter + 1; j++) {
                        if (i < 1 || i > 10 || j < 0 || j > 9 || (i == number && j == number_letter)) {
                            continue;
                        } else {
                            dict_cells.get(i.toString() + str.charAt(j)).booking_cell();
                            broken_cells.add(i.toString() + str.charAt(j));
                        }
                    }
                }
        } else if (status_cell.equals("часть корабля битая")){
            for (Integer i = number - 1; i <= number + 1; i++) {
                for (Integer j = number_letter - 1; j <= number_letter + 1; j++) {
                    if (i < 1 || i > 10 || j < 0 || j > 9 || i == number || j == number_letter) {
                        continue;
                    } else {
                        dict_cells.get(i.toString() + str.charAt(j)).booking_cell();
                        broken_cells.add(i.toString() + str.charAt(j));
                    }
                }
            }
            for (Integer i = number - 1; i <= number + 1; i++) {
                for (Integer j = number_letter - 1; j <= number_letter + 1; j++) {
                    if (i < 1 || i > 10 || j < 0 || j > 9 || !(i == number || j == number_letter)) {
                        continue;
                    } else {
                        hot_list.add(i.toString() + str.charAt(j));
                    }
                }
            }
        }
        broken_cells.add(motion);
        return true;
    }

    public void installing_ship(String cell, Ship ship){
        dict_cells.get(cell).installing_ship(ship);
    }

    public Map<String, Cell> getDict_cells() {
        return dict_cells;
    }

    public HashSet<String> getBroken_cells() {
        return broken_cells;
    }

    public Boolean empty_hot_cell(){
        return hot_list.isEmpty();
    }

    public String get_hot_cell(){
        String motion = hot_list.get(0);
        hot_list.remove(motion);
        return motion;
    }
}
