public class Cell {
    private int number;
    private char letter;
    private String state;
    private Ship ship;
    private String hidden_state;

    public Cell(int number_i, char letter_i){
        number = number_i;
        letter = letter_i;
        state = "пустая";
        hidden_state = "пустая";
    }

    public void installing_ship(Ship ship_i){
        state = "часть корабля целая";
        hidden_state = "пустая";
        ship = ship_i;
    }

    public String showing_my_status(){
        return state;
    }

    public String show_hidden_status() {return hidden_state;}

    public Ship getShip() {return ship;}

    public void shot(){
        if (state.equals("пустая")){
            state = "битая пустая";
            hidden_state = "битая пустая";
            return;
        }
        ship.shot();
        if (ship.getCondition().equals("затонул")){
            state = "затонул";
            hidden_state = "затонул";
        }
        else if (ship.getCondition().equals("подбит")){
            state = "часть корабля битая";
            hidden_state = "часть корабля битая";
        }
    }

    public void booking_cell() {
        if (state.equals("пустая")){
        state = "бронь";
        hidden_state = "бронь";
        }
    }
}
