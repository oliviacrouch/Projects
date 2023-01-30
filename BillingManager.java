public class BillingManager
{
    private double vat;
    private double amount;
    private int quantity;
    private double coupon;
    public BillingManager ()
    {
        vat = 0.2;

    }
    public BillingManager (double vat)
    {
        this.vat = vat;
        //complete constructor
    }
    public double getVAT()
    {
        //complete this method
        return vat;
    }

    public double computeBill(double amount)
    {
        //complete this method
        return amount + (amount * vat);
    }

    public double computeBill(double amount, int quantity)
    {
        //complete this method
        return (amount + (amount * vat)) * quantity;
    }
    public double computeBill(double amt, int quantity, double coupon)
    {
        //complete this method
        double priceBeforeVat = ((amt * quantity) - coupon);
        return priceBeforeVat + (priceBeforeVat * vat);
    }

}