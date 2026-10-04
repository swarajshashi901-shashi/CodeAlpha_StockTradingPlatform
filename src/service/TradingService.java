package service;

import model.PortfolioHolding;
import model.Stock;
import model.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TradingService {

    private final Map<String, PortfolioHolding> portfolio =
            new LinkedHashMap<>();

    private final List<Transaction> transactions =
            new ArrayList<>();

    private double cashBalance = 100000.00;

    private double realizedPnL = 0.00;

    public double getCashBalance() {
        return cashBalance;
    }

    public double getRealizedPnL() {
        return realizedPnL;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(
                transactions
        );
    }

    public Collection<PortfolioHolding> getHoldings() {
        return portfolio.values();
    }

    public void buy(
            Stock stock,
            int quantity) {

        validateQuantity(quantity);

        double total =
                stock.getPrice() * quantity;

        if (total > cashBalance) {

            throw new IllegalArgumentException(
                    "Insufficient cash balance."
            );
        }

        PortfolioHolding holding =
                portfolio.get(stock.getSymbol());

        if (holding == null) {

            holding =
                    new PortfolioHolding(
                            stock.getSymbol(),
                            0,
                            0
                    );

            portfolio.put(
                    stock.getSymbol(),
                    holding
            );
        }

        holding.buy(
                quantity,
                stock.getPrice()
        );

        cashBalance -= total;

        transactions.add(
                new Transaction(
                        "BUY",
                        stock.getSymbol(),
                        quantity,
                        stock.getPrice(),
                        total
                )
        );
    }

    public void sell(
            Stock stock,
            int quantity) {

        validateQuantity(quantity);

        PortfolioHolding holding =
                portfolio.get(
                        stock.getSymbol()
                );

        if (holding == null
                || holding.getQuantity() < quantity) {

            throw new IllegalArgumentException(
                    "You do not own enough shares to sell."
            );
        }

        double proceeds =
                stock.getPrice() * quantity;

        double costBasis =
                holding.getAverageBuyPrice()
                        * quantity;

        realizedPnL +=
                proceeds - costBasis;

        holding.sell(quantity);

        cashBalance += proceeds;

        if (holding.getQuantity() == 0) {

            portfolio.remove(
                    stock.getSymbol()
            );
        }

        transactions.add(
                new Transaction(
                        "SELL",
                        stock.getSymbol(),
                        quantity,
                        stock.getPrice(),
                        proceeds
                )
        );
    }

    private void validateQuantity(
            int quantity) {

        if (quantity <= 0) {

            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }
    }

    public double getMarketValue(
            Map<String, Stock> market) {

        double value = 0;

        for (PortfolioHolding holding :
                portfolio.values()) {

            Stock stock =
                    market.get(
                            holding.getSymbol()
                    );

            if (stock != null) {

                value +=
                        stock.getPrice()
                                * holding.getQuantity();
            }
        }

        return value;
    }

    public double getUnrealizedPnL(
            Map<String, Stock> market) {

        double pnl = 0;

        for (PortfolioHolding holding :
                portfolio.values()) {

            Stock stock =
                    market.get(
                            holding.getSymbol()
                    );

            if (stock != null) {

                pnl +=
                        (stock.getPrice()
                                - holding.getAverageBuyPrice())
                                * holding.getQuantity();
            }
        }

        return pnl;
    }

    public double getTotalPortfolioValue(
            Map<String, Stock> market) {

        return cashBalance
                + getMarketValue(market);
    }
}