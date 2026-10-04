package service;

import model.Stock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MarketService {

    private final Map<String, Stock> stocks =
            new LinkedHashMap<>();

    private final Random random =
            new Random();

    public MarketService() {
        seedMarket();
    }

    private void seedMarket() {

        add(
                "TCS",
                "Tata Consultancy Services",
                "IT",
                3825
        );

        add(
                "INFY",
                "Infosys",
                "IT",
                1710
        );

        add(
                "RELIANCE",
                "Reliance Industries",
                "Energy",
                2960
        );

        add(
                "HDFCBANK",
                "HDFC Bank",
                "Banking",
                1815
        );

        add(
                "ICICIBANK",
                "ICICI Bank",
                "Banking",
                1425
        );

        add(
                "SBIN",
                "State Bank of India",
                "Banking",
                865
        );

        add(
                "ITC",
                "ITC Limited",
                "FMCG",
                510
        );

        add(
                "BHARTIARTL",
                "Bharti Airtel",
                "Telecom",
                1940
        );

        add(
                "LT",
                "Larsen & Toubro",
                "Engineering",
                3920
        );

        add(
                "WIPRO",
                "Wipro",
                "IT",
                575
        );

        add(
                "MARUTI",
                "Maruti Suzuki",
                "Auto",
                15250
        );

        add(
                "SUNPHARMA",
                "Sun Pharma",
                "Pharma",
                1780
        );
    }

    private void add(
            String symbol,
            String companyName,
            String sector,
            double price) {

        stocks.put(
                symbol,
                new Stock(
                        symbol,
                        companyName,
                        sector,
                        price
                )
        );
    }

    public List<Stock> getStocks() {

        return new ArrayList<>(
                stocks.values()
        );
    }

    public Stock getStock(String symbol) {

        if (symbol == null) {
            return null;
        }

        return stocks.get(
                symbol.toUpperCase()
        );
    }

    public void simulateMarketMovement() {

        for (Stock stock : stocks.values()) {

            double percent =
                    (random.nextDouble() - 0.48)
                            * 0.035;

            stock.updatePrice(
                    stock.getPrice()
                            * (1 + percent)
            );
        }
    }
}