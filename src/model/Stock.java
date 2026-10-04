package model;

public class Stock {

    private final String symbol;
    private final String companyName;
    private final String sector;

    private double price;
    private double previousClose;

    public Stock(
            String symbol,
            String companyName,
            String sector,
            double price) {

        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.price = price;
        this.previousClose = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSector() {
        return sector;
    }

    public double getPrice() {
        return price;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void updatePrice(double newPrice) {
        previousClose = price;
        price = Math.max(1.0, newPrice);
    }

    public double getChange() {
        return price - previousClose;
    }

    public double getChangePercent() {

        if (previousClose == 0) {
            return 0;
        }

        return (getChange() / previousClose) * 100.0;
    }
}