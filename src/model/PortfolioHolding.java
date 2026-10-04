package model;

public class PortfolioHolding {

    private final String symbol;

    private int quantity;
    private double averageBuyPrice;

    public PortfolioHolding(
            String symbol,
            int quantity,
            double averageBuyPrice) {

        this.symbol = symbol;
        this.quantity = quantity;
        this.averageBuyPrice = averageBuyPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void buy(int quantity, double price) {

        double totalCost =
                averageBuyPrice * this.quantity
                        + price * quantity;

        this.quantity += quantity;

        if (this.quantity > 0) {
            averageBuyPrice =
                    totalCost / this.quantity;
        }
    }

    public void sell(int quantity) {

        this.quantity -= quantity;

        if (this.quantity <= 0) {

            this.quantity = 0;
            this.averageBuyPrice = 0;
        }
    }

    public double getInvestedValue() {

        return quantity * averageBuyPrice;
    }
}