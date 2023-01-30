public class Theatre
{
    static int rows = 32;
    static int seatsPerRow = 20;
    static int expensesPerSeat = 2;
    static int bookedSeats = 456;
    static double ticketPrice = 25;

    public static void main (String args[])
    {
        double calculateMaxProfit = maxProfit(ticketPrice);
        double calculateProfit = profit(bookedSeats, ticketPrice);
        double lostProfit = lostProfit (calculateMaxProfit, calculateProfit);
        System.out.println("The lost profit is " + lostProfit);


    }
    static int capacity(int rows, int seatsPerRow)
    {
        //implement this method
        return rows * seatsPerRow;
    }
    static double expenses(double expensesPerSeat, int bookedSeats)
    {
        //implement this method
        return expensesPerSeat * bookedSeats;
    }
    static double income(int bookedSeats, double ticketPrice)
    {
        //implement this method
        return bookedSeats * ticketPrice;
    }
    static double profit(int bookedSeats, double ticketPrice)
    {
        //implement this method
        double income = bookedSeats * ticketPrice;
        double expenses = expensesPerSeat * bookedSeats;
        return income - expenses;
    }
    static double maxProfit(double ticketPrice)
    {
        //implement this method
        double incomeFullCap = (rows * seatsPerRow) * ticketPrice;
        double expensesFullCap = (rows * seatsPerRow) * expensesPerSeat;
        return incomeFullCap - expensesFullCap;
    }
    static double lostProfit(double maxProfit, double profit)
    {
        return maxProfit - profit;
    }

}
