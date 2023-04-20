public class Ship {
    private String condition;
    private int number_of_whole_cells;
    private Gamer gamer;

    public Ship(int type, Gamer gamer_i){
        condition = "цел";
        number_of_whole_cells = type;
        gamer = gamer_i;
    }

    public void shot(){
        number_of_whole_cells -= 1;
        if (number_of_whole_cells > 0 && condition.equals("цел")){
            condition = "подбит";
        }
        else if (number_of_whole_cells == 0) {
            condition = "затонул";
            gamer.ship_loss();
        }
    }

    public String getCondition(){
        return condition;
    }

    public int getNumber_of_whole_cells() { return number_of_whole_cells;}
}
