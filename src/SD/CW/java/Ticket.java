package SD.CW.java;

//task 10
public class Ticket {
    int row;
    int seat;
    int price;
    Person person;

    public Ticket(int row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }


    //task 11
    public void print() {
        String information = """
                \nName        :\s""" + person.name + """
                \nSurname     :\s""" + person.surname + """
                \nEmail       :\s""" + person.email + """
                \nRow number  :\s""" + this.row + """
                \nSeat number :\s""" + this.seat + """
                \nPrice       : £""" + this.price + """
                """;
        System.out.println(information);
    }
}