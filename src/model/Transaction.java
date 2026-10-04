package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final String type;
    private final String symbol;
    private final int quantity;
    private final double price;
    private final double total;
    private final LocalDateTime time;

    public Transaction(
            String type,
            String symbol,
            int quantity,
            double price,
            double total) {

        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.time = LocalDateTime.now();
    }

    public Transaction(
            String type,
            String symbol,
            int quantity,
            double price,
            double total,
            LocalDateTime time) {

        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String toCsv() {

        return String.format(
                "%s,%s,%d,%.2f,%.2f,%s",
                type,
                symbol,
                quantity,
                price,
                total,
                time.toString()
        );
    }

    public String getFormattedTime() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy HH:mm"
                );

        return time.format(formatter);
    }
}