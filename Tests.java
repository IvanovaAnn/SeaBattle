import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

    @Test
    public void testShotShipRemainsAfloat() {
        Field field1 = new Field();
        Field field2 = new Field();
        Gamer gamer = new Gamer(1, field1, field2, "auto");
        Ship ship = new Ship(3, gamer);
        ship.shot();
        assertEquals("подбит", ship.getCondition());
        assertEquals(2, ship.getNumber_of_whole_cells());
    }

    @Test
    public void testShotShipSinks() {
        Field field1 = new Field();
        Field field2 = new Field();
        Gamer gamer = new Gamer(1, field1, field2, "auto");
        Ship ship = new Ship(1, gamer);
        ship.shot();
        assertEquals("затонул", ship.getCondition());
        assertEquals(0, ship.getNumber_of_whole_cells());
    }

    @Test
    public void testShotShipAlreadySunk() {
        Field field1 = new Field();
        Field field2 = new Field();
        Gamer gamer = new Gamer(1, field1, field2, "auto");
        Ship ship = new Ship(2, gamer);
        ship.shot();
        ship.shot();
        assertEquals("затонул", ship.getCondition());
        assertEquals(0, ship.getNumber_of_whole_cells());
    }

    @Test
    public void testShotShipAlreadyHit() {
        Field field1 = new Field();
        Field field2 = new Field();
        Gamer gamer = new Gamer(1, field1, field2, "auto");
        Ship ship = new Ship(3, gamer);
        ship.shot();
        ship.shot();
        assertEquals("подбит", ship.getCondition());
        assertEquals(1, ship.getNumber_of_whole_cells());
    }

    @Test
    public void testNewCellIsEmpty() {
        Cell cell = new Cell(5, 'E');
        assertEquals("пустая", cell.showing_my_status());
        assertEquals("пустая", cell.show_hidden_status());
        assertNull(cell.getShip());
    }

    @Test
    public void testInstallShip() {
        Cell cell = new Cell(3, 'B');
        Ship ship = new Ship(3, null);
        cell.installing_ship(ship);
        assertEquals("часть корабля целая", cell.showing_my_status());
        assertEquals("пустая", cell.show_hidden_status());
        assertSame(ship, cell.getShip());
    }

    @Test
    public void testShotOnEmptyCell() {
        Cell cell = new Cell(1, 'A');
        cell.shot();
        assertEquals("битая пустая", cell.showing_my_status());
        assertEquals("битая пустая", cell.show_hidden_status());
        assertNull(cell.getShip());
    }

    @Test
    public void testShotOnShip() {
        Cell cell = new Cell(6, 'F');
        Ship ship = new Ship(2, null);
        cell.installing_ship(ship);
        cell.shot();
        assertEquals("часть корабля битая", cell.showing_my_status());
        assertEquals("часть корабля битая", cell.show_hidden_status());
        assertSame(ship, cell.getShip());
    }

    @Test
    public void testShotOnSunkShip() {
        Field field1 = new Field();
        Field field2 = new Field();
        Gamer gamer = new Gamer(1, field1, field2, "auto");
        Cell cell = new Cell(10, 'D');
        Ship ship = new Ship(2, gamer);
        cell.installing_ship(ship);
        ship.shot();
        cell.shot();
        System.out.println(cell.showing_my_status());
        assertEquals("затонул", cell.showing_my_status());
        assertEquals("затонул", cell.show_hidden_status());
        assertSame(ship, cell.getShip());
    }

    @Test
    public void testBookCell() {
        Cell cell = new Cell(8, 'C');
        cell.booking_cell();
        assertEquals("бронь", cell.showing_my_status());
        assertEquals("бронь", cell.show_hidden_status());
        assertNull(cell.getShip());
    }

    @Test
    public void testShotMiss() {
        Field field = new Field();
        Assertions.assertTrue(field.shot("1A"));
        Assertions.assertFalse(field.shot("1A"));
    }

    @Test
    public void testShotHit() {
        Field field = new Field();
        Ship ship = new Ship(3, null);
        Cell cell = field.getDict_cells().get("1A");
        cell.installing_ship(ship);
        Assertions.assertTrue(field.shot("1A"));
        assertEquals("часть корабля битая", cell.showing_my_status());
    }

    @Test
    public void testShotSunk() {
        Field field = new Field();
        Field field1 = new Field();
        Gamer gamer = new Gamer(1, field, field1, "auto");
        Ship ship = new Ship(1, gamer);
        Cell cell = field.getDict_cells().get("1A");
        cell.installing_ship(ship);
        Assertions.assertTrue(field.shot("1A"));
        assertEquals("затонул", cell.showing_my_status());
    }

    @Test
    public void testShotBooking() {
        Field field = new Field();
        Field field1 = new Field();
        Gamer gamer = new Gamer(1, field, field1, "auto");
        Ship ship = new Ship(1, gamer);
        Cell cell = field.getDict_cells().get("1A");
        cell.installing_ship(ship);
        Assertions.assertTrue(field.shot("1A"));
        Assertions.assertFalse(field.shot("2A"));
        assertEquals("бронь", field.getDict_cells().get("2A").showing_my_status());
    }

    @Test
    public void testBookingShipHit() {
        Field field = new Field();
        Field field1 = new Field();
        Gamer gamer = new Gamer(1, field, field1, "auto");
        Ship ship = new Ship(2, gamer);
        Cell cell1 = field.getDict_cells().get("1A");
        Cell cell2 = field.getDict_cells().get("2A");
        cell1.installing_ship(ship);
        cell2.installing_ship(ship);
        Assertions.assertTrue(field.shot("1A"));
        assertEquals("часть корабля битая", cell1.showing_my_status());
        assertEquals("часть корабля целая", cell2.showing_my_status());
        Assertions.assertFalse(field.shot("2B"));
        assertEquals("бронь", field.getDict_cells().get("2B").showing_my_status());
        Assertions.assertTrue(field.shot("1B"));
        assertEquals("битая пустая", field.getDict_cells().get("1B").showing_my_status());
    }

    @Test
    public void testBookingStraightLine2() {
        Field field = new Field();
        Field field1 = new Field();
        Gamer gamer = new Gamer(1, field, field1, "auto");
        Ship ship = new Ship(2, gamer);
        Cell cell1 = field.getDict_cells().get("1A");
        Cell cell2 = field.getDict_cells().get("2A");
        Cell cell3 = field.getDict_cells().get("1B");
        Cell cell4 = field.getDict_cells().get("3A");
        cell1.installing_ship(ship);
        cell2.installing_ship(ship);
        Assertions.assertTrue(field.shot("1A"));
        assertEquals("часть корабля битая", cell1.showing_my_status());
        assertEquals("часть корабля целая", cell2.showing_my_status());
        Assertions.assertFalse(field.shot("2B"));
        assertEquals("бронь", field.getDict_cells().get("2B").showing_my_status());
        Assertions.assertTrue(field.shot("1B"));
        assertEquals("битая пустая", cell3.showing_my_status());
        Assertions.assertTrue(field.shot("3A"));
        assertEquals("битая пустая", cell4.showing_my_status());
        Assertions.assertTrue(field.shot("2A"));
        assertEquals("битая пустая", cell4.showing_my_status());
    }

    @Test
    void testAutoLocationOfShips() {
        Field field = new Field();
        Field hidden_field = new Field();
        String auto = "manually";
        Gamer gamer = new Gamer(1, field, hidden_field, auto);
        String autoLocation = gamer.auto_location_of_ships(3);
        String[] locations = autoLocation.split(" ");
        int startRow = Integer.parseInt(locations[0].substring(0, locations[0].length() - 1));
        int endRow = Integer.parseInt(locations[1].substring(0, locations[1].length() - 1));
        char startCol = locations[0].charAt(locations[0].length() - 1);
        char endCol = locations[1].charAt(locations[1].length() - 1);
        Assertions.assertTrue(startRow >= 1 && startRow <= 10);
        Assertions.assertTrue(endRow >= 1 && endRow <= 10);
        Assertions.assertTrue(startCol >= 'A' && startCol <= 'J');
        Assertions.assertTrue(endCol >= 'A' && endCol <= 'J');
        int expectedLength = Math.abs(startRow - endRow) + Math.abs(startCol - endCol) + 1;
        assertEquals(expectedLength, 3);
    }

    @Test
    void testCheckingFreedomOfCells() {
        Field field = new Field();
        Field hidden_field = new Field();
        String auto = "manually";
        Gamer gamer = new Gamer(1, field, hidden_field, auto);
        HashSet<String> occupied_cells = new HashSet<>();
        occupied_cells.add("1A");
        occupied_cells.add("1B");
        occupied_cells.add("1C");
        occupied_cells.add("2A");
        occupied_cells.add("2B");
        occupied_cells.add("3A");
        occupied_cells.add("3B");
        occupied_cells.add("3C");
        String motion = "1D 1E";
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupied_cells, motion));
        motion = "2A 4A";
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupied_cells, motion));
        motion = "4D 5E";
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupied_cells, motion));
        motion = "2D 2E";
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupied_cells, motion));
        motion = "3E 3F";
        Assertions.assertTrue(gamer.checking_freedom_of_cells(occupied_cells, motion));
        motion = "3E 4E";
        Assertions.assertTrue(gamer.checking_freedom_of_cells(occupied_cells, motion));
    }

    @Test
    public void testAutoLocationOfShips2() {
        Gamer gamer = new Gamer(1, new Field(), new Field(), "auto");
        String location = gamer.auto_location_of_ships(4);
        String[] cells = location.split(" ");
        assertEquals(2, cells.length);
        Assertions.assertTrue(cells[0].matches("[1-9]?(10)?[A-J]"));
        Assertions.assertTrue(cells[1].matches("[1-9]?(10)?[A-J]"));
        String location2 = gamer.auto_location_of_ships(3);
        Assertions.assertNotEquals(location, location2);
    }

    @Test
    public void testCheckingFreedomOfCells2() {
        Gamer gamer = new Gamer(1, new Field(), new Field(), "manually");
        HashSet<String> occupiedCells = new HashSet<>();
        occupiedCells.add("3A");
        occupiedCells.add("4A");
        occupiedCells.add("5A");
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupiedCells, "6A 8A"));
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupiedCells, "2A 4A"));
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupiedCells, "4B 4D"));
        Assertions.assertTrue(gamer.checking_freedom_of_cells(occupiedCells, "7A 8A"));
        Assertions.assertFalse(gamer.checking_freedom_of_cells(occupiedCells, "6B 8B"));
    }

    @Test
    void auto_motion_returns_valid_cell() {
        Gamer gamer = new Gamer(1, new Field(), new Field(), "manually");
        String cell = gamer.auto_motion();
        Assertions.assertNotNull(cell);
        Assertions.assertTrue(cell.matches("(10|[1-9])[A-J]"));
    }
}
