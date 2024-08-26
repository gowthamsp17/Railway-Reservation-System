import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TicketBooker booker = new TicketBooker();
        
        int id;
        boolean isLooping = true;
        while(isLooping)
        {
            System.out.println("\n\n******************************** RAILWAY TICKET RESERVATION SYSTEM ********************************");
            System.out.println("1. Book Tickets \n" +
                                "2. Cancel Tickets \n" +
                                "3. Available Tickets \n" +
                                "4. Passenger Detail \n" +
                                "5. Display All Passengers \n" +
                                "6. Exit \n");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                System.out.println("********** Ticket Booking **********");
                    System.out.print("Enter Passenger name : ");
                    String name = sc.next();
                    System.out.print("Enter age : ");
                    int age = sc.nextInt();
                    System.out.print("Enter Berth Preference (L / M / U) : ");
                    String berthPreference = sc.next();

                    Passenger p = new Passenger(name, age, berthPreference);
                    bookTicket(p);
                    break;
            
                case 2:
                    System.out.println("********** Cancelling Ticket **********");
                    System.out.print("Enter passenger ID : ");
                    id = sc.nextInt();
                    cancelTicket(id);
                    break;

                case 3:
                    booker.printAvailableTickets();
                    break;

                case 4:
                    System.out.print("Enter Passenger ID : ");
                    id = sc.nextInt();
                    booker.printPassenger(id);
                    break;
                
                case 5:
                    booker.printAllPassengers();
                    break;

                case 6:
                    isLooping = false;
                    System.out.println("Exit.................");
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }

    public static void bookTicket(Passenger p) {
        TicketBooker booker = new TicketBooker();

        if(TicketBooker.availableWaitingList == 0)
        {
            System.out.println("No Ticket Available");
            return;
        }

        String berthPreference = p.preferredBerth;
        if(berthPreference.equals("L") && TicketBooker.availableLowerBerths > 0 ||
            berthPreference.equals("M") && TicketBooker.availableMiddleBerths > 0 ||
            berthPreference.equals("U") && TicketBooker.availableUpperBerths > 0)
        {
            System.out.println("Preferred Berth Allocated");

            if(berthPreference.equals("L"))
            {
                System.out.println("Lower berth given");
                booker.bookTicket(p, TicketBooker.lowerBerthPositions.get(0), berthPreference);
                TicketBooker.lowerBerthPositions.remove(0);
                TicketBooker.availableLowerBerths--;
            }
            else if(berthPreference.equals("M"))
            {
                System.out.println("Middle berth given");
                booker.bookTicket(p, TicketBooker.middleBerthPositions.get(0), berthPreference);
                TicketBooker.middleBerthPositions.remove(0);
                TicketBooker.availableMiddleBerths--;
            }
            else if(berthPreference.equals("U"))
            {
                System.out.println("Upper berth given");
                booker.bookTicket(p, TicketBooker.upperBerthPositions.get(0), berthPreference);
                TicketBooker.upperBerthPositions.remove(0);
                TicketBooker.availableUpperBerths--;
            }
        }
        else if(TicketBooker.availableLowerBerths > 0)
        {
            System.out.println("Lower berth given");
            booker.bookTicket(p, TicketBooker.lowerBerthPositions.get(0), "L");
            TicketBooker.lowerBerthPositions.remove(0);
            TicketBooker.availableLowerBerths--;
        }
        else if(TicketBooker.availableMiddleBerths > 0)
        {
            System.out.println("Middle berth given");
            booker.bookTicket(p, TicketBooker.middleBerthPositions.get(0), "M");
            TicketBooker.middleBerthPositions.remove(0);
            TicketBooker.availableMiddleBerths--;
        }
        else if(TicketBooker.availableUpperBerths > 0)
        {
            System.out.println("Upper berth given");
            booker.bookTicket(p, TicketBooker.upperBerthPositions.get(0), "U");
            TicketBooker.upperBerthPositions.remove(0);
            TicketBooker.availableUpperBerths--;            
        }
        else if(TicketBooker.availableRACTickets > 0)
        {
            System.out.println("RAC Available");
            booker.addToRAC(p, TicketBooker.racPositions.get(0), "RAC");
        }
        else if(TicketBooker.availableWaitingList > 0)
        {
            System.out.println("Waiting List Available");
            booker.addToWaitingList(p, TicketBooker.waitingListPositions.get(0), "WL");
        }
    }

    private static void cancelTicket(int id) {
        TicketBooker booker = new TicketBooker();
        if(!TicketBooker.PASSENGERS.containsKey(id))
        {
            System.out.println("No passenger having id " + id);
            return;
        }
        booker.cancelTicket(id);
    }
}
