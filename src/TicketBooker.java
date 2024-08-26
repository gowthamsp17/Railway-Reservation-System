import java.util.ArrayList;
import java.util.HashMap;
// import java.util.LinkedList;
import java.util.List;
import java.util.Map;
// import java.util.Queue;

public class TicketBooker {
    static int availableLowerBerths = 21;
    static int availableMiddleBerths = 21;
    static int availableUpperBerths = 21;
    static int availableRACTickets = 18;
    static int availableWaitingList = 10;

    static List<Integer> bookedTicketList = new ArrayList<>();
    static List<Integer> bookedRACList = new ArrayList<>();
    static List<Integer> bookedWaitingList = new ArrayList<>();

    static List<Integer> lowerBerthPositions = new ArrayList<>();
    static List<Integer> middleBerthPositions = new ArrayList<>();
    static List<Integer> upperBerthPositions = new ArrayList<>();
    static List<Integer> racPositions = new ArrayList<>();
    static List<Integer> waitingListPositions = new ArrayList<>();

    static Map<Integer, Passenger> PASSENGERS = new HashMap<>();

    static {
        for(int i = 1; i <= 21; i++)
        {
            lowerBerthPositions.add(i);
            middleBerthPositions.add(i);
            upperBerthPositions.add(i);

            if(i <= 18) {
                racPositions.add(i);
            }

            if(i <= 10) {
                waitingListPositions.add(i);
            }
        }
    }

    public void bookTicket(Passenger p, int seatNumber, String allotedBerth)
    {
        p.number = seatNumber;
        p.alloted = allotedBerth;

        PASSENGERS.put(p.passengerId, p);

        bookedTicketList.add(p.passengerId);
        System.out.println("----------------------------- Ticket Booked Successfully");
    }

    public void addToRAC(Passenger p, int seatNumber, String allotedBerth)
    {
        p.number = seatNumber;
        p.alloted = allotedBerth;

        PASSENGERS.put(p.passengerId, p);

        bookedRACList.add(p.passengerId);
        racPositions.remove(0);
        availableRACTickets--;
        System.out.println("------------------------------ RAC Ticked Booked");
    }
    
    public void addToWaitingList(Passenger p, int seatNumber, String allotedBerth)
    {
        p.number = seatNumber;
        p.alloted = allotedBerth;

        PASSENGERS.put(p.passengerId, p);

        waitingListPositions.remove(0);
        availableWaitingList--;
        bookedWaitingList.add(p.passengerId);
        System.out.println("------------------------------ Booked in Waiting List");
    }

    public void cancelTicket(int passengerId)
    {
        Passenger p = PASSENGERS.get(passengerId);
        PASSENGERS.remove(passengerId);

        boolean isRACRemoved = false;
        boolean isWaitingRemoved = false;

        switch (p.alloted) {
            case "L":
                bookedTicketList.remove(Integer.valueOf(passengerId));
                lowerBerthPositions.add(p.number);
                availableLowerBerths++;
                break;

            case "M":
                bookedTicketList.remove(Integer.valueOf(passengerId));
                middleBerthPositions.add(p.number);
                availableMiddleBerths++;
                break;

            case "U":
                bookedTicketList.remove(Integer.valueOf(passengerId));
                upperBerthPositions.add(p.number);
                availableUpperBerths++;
                break;

            case "RAC":
                bookedRACList.remove(Integer.valueOf(passengerId));
                racPositions.add(p.number);
                availableRACTickets++;
                isRACRemoved = true;
                break;

            case "WL":
                bookedWaitingList.remove(Integer.valueOf(passengerId));
                waitingListPositions.add(p.number);
                availableWaitingList++;
                isWaitingRemoved = true;
                break;
    
            default:
                break;
        }

        if(!bookedRACList.isEmpty() && !isWaitingRemoved)
        {
            if(!isRACRemoved)
            {
                Passenger racPassenger = PASSENGERS.get(bookedRACList.get(0));
                bookedRACList.remove(0);

                racPositions.add(racPassenger.number);
                availableRACTickets++;

                Main.bookTicket(racPassenger);
                isRACRemoved = true;
            }

            if(!bookedWaitingList.isEmpty() && isRACRemoved)
            {
                Passenger waitingPassenger = PASSENGERS.get(bookedWaitingList.get(0));
                bookedWaitingList.remove(0);
                PASSENGERS.remove(waitingPassenger.passengerId);

                waitingListPositions.add(waitingPassenger.number);
                availableWaitingList++;

                waitingPassenger.number = racPositions.get(0);     
                waitingPassenger.alloted = "RAC";
                
                bookedRACList.add(waitingPassenger.passengerId);
                PASSENGERS.put(waitingPassenger.passengerId, waitingPassenger);

                availableWaitingList++;
                availableRACTickets--;
            }
        }
        System.out.println("Cancelled Ticket details");
        System.out.println("Passenger ID : " + p.passengerId);
        System.out.println("Passengers name : " + p.name);
        System.out.println("Passenber age : " + p.age);
        System.out.println("Status : " + p.number + " : " + p.alloted);
        System.out.println("----------------------------- Ticket Cancelled");
    }

    public void printAvailableTickets()
    {
        System.out.println("********** AVAILABLE TICKETS **********");
        System.out.println("Lower Berths : " + availableLowerBerths);
        System.out.println("Middle Berths : " + availableMiddleBerths);
        System.out.println("Upper Berths : " + availableUpperBerths);
        System.out.println("RAC Tickets : " + availableRACTickets);
        System.out.println("Waiting List : " + availableWaitingList + "\n");
    }

    public void printAllPassengers()
    {
        int n = PASSENGERS.size();

        if(n == 0)
        {
            System.out.println("No Passengers details !!");
            return;
        }
        System.out.println("********** PASSENGERS DETAILS **********");
        System.out.println("Total passengers : " + n + "\n");
        System.out.println("-------------------------------------------------");

        for(Passenger p : PASSENGERS.values())
        {
            printPassenger(p.passengerId);
            // System.out.println("Passenger ID : " + p.passengerId);
            // System.out.println("Passengers name : " + p.name);
            // System.out.println("Passenber age : " + p.age);
            // System.out.println("Status : " + p.number + " : " + p.alloted);
            // System.out.println("-------------------------------------------------");
        }
    }

    public void printPassenger(int id)
    {
        Passenger p = PASSENGERS.get(id);
        System.out.println("Passenger ID : " + p.passengerId);
        System.out.println("Passengers name : " + p.name);
        System.out.println("Passenber age : " + p.age);
        System.out.println("Status : " + p.number + " : " + p.alloted);
        System.out.println("-------------------------------------------------");
    }
}
