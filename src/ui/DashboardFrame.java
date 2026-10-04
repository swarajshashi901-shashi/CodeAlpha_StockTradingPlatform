package ui;

import model.PortfolioHolding;
import model.Stock;
import model.Transaction;
import service.DataService;
import service.MarketService;
import service.TradingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DashboardFrame extends JFrame {

    private final MarketService marketService = new MarketService();
    private final TradingService tradingService = new TradingService();
    private final DataService dataService = new DataService();

    private final DefaultTableModel marketModel =
            createModel(new String[]{
                    "Symbol",
                    "Company",
                    "Sector",
                    "Price",
                    "Change",
                    "Change %"
            });

    private final DefaultTableModel portfolioModel =
            createModel(new String[]{
                    "Symbol",
                    "Quantity",
                    "Average Buy",
                    "Current Price",
                    "Market Value",
                    "P/L"
            });

    private final DefaultTableModel transactionModel =
            createModel(new String[]{
                    "Type",
                    "Symbol",
                    "Quantity",
                    "Price",
                    "Total",
                    "Time"
            });

    private final JTable marketTable =
            new JTable(marketModel);

    private final JTable portfolioTable =
            new JTable(portfolioModel);

    private final JTable transactionTable =
            new JTable(transactionModel);

    private final JTextField searchField =
            new JTextField();

    private final JComboBox<String> sectorBox =
            new JComboBox<>();

    private final JComboBox<String> stockBox =
            new JComboBox<>();

    private final JSpinner quantitySpinner =
            new JSpinner(
                    new SpinnerNumberModel(
                            1,
                            1,
                            100000,
                            1
                    )
            );

    private final JLabel cashLabel =
            new JLabel();

    private final JLabel marketValueLabel =
            new JLabel();

    private final JLabel portfolioValueLabel =
            new JLabel();

    private final JLabel pnlLabel =
            new JLabel();

    private final JLabel statusLabel =
            new JLabel(
                    "Ready • Simulated trading mode"
            );

    private final NumberFormat currency =
            NumberFormat.getCurrencyInstance(
                    new Locale("en", "IN")
            );

    public DashboardFrame() {

        setTitle(
                "CodeAlpha Stock Trading Platform"
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(
                1250,
                800
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        setLocationRelativeTo(null);

        buildInterface();

        refreshAll();
    }

    private static DefaultTableModel createModel(
            String[] columns) {

        return new DefaultTableModel(
                columns,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };
    }

    private void buildInterface() {

        JPanel root =
                new JPanel(
                        new BorderLayout(
                                12,
                                12
                        )
                );

        root.setBorder(
                new EmptyBorder(
                        15,
                        15,
                        10,
                        15
                )
        );

        root.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        setContentPane(root);

        root.add(
                createHeader(),
                BorderLayout.NORTH
        );

        root.add(
                createTabs(),
                BorderLayout.CENTER
        );

        root.add(
                createStatusBar(),
                BorderLayout.SOUTH
        );
    }

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout(
                                15,
                                10
                        )
                );

        header.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Stock Trading Platform"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(
                new Color(
                        30,
                        40,
                        55
                )
        );

        JLabel subtitle =
                new JLabel(
                        "CodeAlpha Java Internship • Advanced Trading Dashboard"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(
                new Color(
                        95,
                        105,
                        120
                )
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                8,
                                0
                        )
                );

        cards.setOpaque(false);

        cards.add(
                createCard(
                        "Cash Balance",
                        cashLabel
                )
        );

        cards.add(
                createCard(
                        "Market Value",
                        marketValueLabel
                )
        );

        cards.add(
                createCard(
                        "Portfolio Value",
                        portfolioValueLabel
                )
        );

        cards.add(
                createCard(
                        "Total P/L",
                        pnlLabel
                )
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                cards,
                BorderLayout.EAST
        );

        return header;
    }

    private JPanel createCard(
            String name,
            JLabel value) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                3,
                                3
                        )
                );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        220,
                                        225,
                                        232
                                )
                        ),
                        new EmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        JLabel label =
                new JLabel(name);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        label.setForeground(
                new Color(
                        100,
                        110,
                        125
                )
        );

        value.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        value.setForeground(
                new Color(
                        30,
                        40,
                        55
                )
        );

        card.add(
                label,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        return card;
    }

    private JTabbedPane createTabs() {

        JTabbedPane tabs =
                new JTabbedPane();

        tabs.addTab(
                "Market",
                createMarketPanel()
        );

        tabs.addTab(
                "Portfolio",
                createPortfolioPanel()
        );

        tabs.addTab(
                "Transactions",
                createTransactionPanel()
        );

        return tabs;
    }

    private JPanel createMarketPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        JPanel top =
                new JPanel(
                        new BorderLayout(
                                8,
                                8
                        )
                );

        top.setOpaque(false);

        JPanel filters =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                8,
                                0
                        )
                );

        filters.setOpaque(false);

        searchField.setToolTipText(
                "Search stock symbol or company"
        );

        filters.add(searchField);

        sectorBox.addItem("All Sectors");
        sectorBox.addItem("IT");
        sectorBox.addItem("Banking");
        sectorBox.addItem("Energy");
        sectorBox.addItem("FMCG");
        sectorBox.addItem("Telecom");
        sectorBox.addItem("Engineering");
        sectorBox.addItem("Auto");
        sectorBox.addItem("Pharma");

        filters.add(sectorBox);

        JButton refreshButton =
                new JButton(
                        "Refresh Market"
                );

        refreshButton.addActionListener(
                e -> {

                    marketService
                            .simulateMarketMovement();

                    refreshAll();

                    statusLabel.setText(
                            "Market refreshed successfully"
                    );
                }
        );

        filters.add(refreshButton);

        top.add(
                filters,
                BorderLayout.NORTH
        );

        top.add(
                createOrderPanel(),
                BorderLayout.CENTER
        );

        marketTable.setRowHeight(28);

        marketTable.setAutoCreateRowSorter(true);

        searchField
                .getDocument()
                .addDocumentListener(
                        SimpleDocumentListener.of(
                                this::refreshMarketTable
                        )
                );

        sectorBox.addActionListener(
                e -> refreshMarketTable()
        );

        marketTable
                .getSelectionModel()
                .addListSelectionListener(
                        e -> {

                            if (!e.getValueIsAdjusting()) {

                                int row =
                                        marketTable
                                                .getSelectedRow();

                                if (row >= 0) {

                                    int modelRow =
                                            marketTable
                                                    .convertRowIndexToModel(
                                                            row
                                                    );

                                    String symbol =
                                            String.valueOf(
                                                    marketModel
                                                            .getValueAt(
                                                                    modelRow,
                                                                    0
                                                            )
                                            );

                                    stockBox.setSelectedItem(
                                            symbol
                                    );
                                }
                            }
                        }
                );

        panel.add(
                top,
                BorderLayout.NORTH
        );

        panel.add(
                new JScrollPane(
                        marketTable
                ),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createOrderPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                8
                        )
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        "Order Ticket"
                )
        );

        panel.add(
                new JLabel("Stock:")
        );

        stockBox.setPreferredSize(
                new Dimension(
                        120,
                        28
                )
        );

        panel.add(stockBox);

        panel.add(
                new JLabel("Quantity:")
        );

        quantitySpinner.setPreferredSize(
                new Dimension(
                        80,
                        28
                )
        );

        panel.add(quantitySpinner);

        JButton buyButton =
                new JButton(
                        "BUY"
                );

        JButton sellButton =
                new JButton(
                        "SELL"
                );

        buyButton.addActionListener(
                e -> executeTrade(true)
        );

        sellButton.addActionListener(
                e -> executeTrade(false)
        );

        panel.add(buyButton);

        panel.add(sellButton);

        return panel;
    }

    private JPanel createPortfolioPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        JLabel info =
                new JLabel(
                        "Your current holdings and unrealized profit/loss"
                );

        info.setBorder(
                new EmptyBorder(
                        0,
                        5,
                        5,
                        5
                )
        );

        portfolioTable.setRowHeight(30);

        portfolioTable.setAutoCreateRowSorter(true);

        panel.add(
                info,
                BorderLayout.NORTH
        );

        panel.add(
                new JScrollPane(
                        portfolioTable
                ),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createTransactionPanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                new Color(
                        245,
                        247,
                        250
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        JPanel top =
                new JPanel(
                        new BorderLayout()
                );

        top.setOpaque(false);

        JLabel info =
                new JLabel(
                        "Complete simulated trading history"
                );

        JButton saveButton =
                new JButton(
                        "Save History"
                );

        saveButton.addActionListener(
                e -> saveHistory()
        );

        top.add(
                info,
                BorderLayout.WEST
        );

        top.add(
                saveButton,
                BorderLayout.EAST
        );

        transactionTable.setRowHeight(28);

        transactionTable.setAutoCreateRowSorter(true);

        panel.add(
                top,
                BorderLayout.NORTH
        );

        panel.add(
                new JScrollPane(
                        transactionTable
                ),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createStatusBar() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setOpaque(false);

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        statusLabel.setForeground(
                new Color(
                        90,
                        100,
                        115
                )
        );

        panel.add(
                statusLabel,
                BorderLayout.WEST
        );

        JLabel right =
                new JLabel(
                        "Java OOP • Swing • File I/O"
                );

        right.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        right.setForeground(
                new Color(
                        90,
                        100,
                        115
                )
        );

        panel.add(
                right,
                BorderLayout.EAST
        );

        return panel;
    }

    private void executeTrade(
            boolean buy) {

        String symbol =
                String.valueOf(
                        stockBox.getSelectedItem()
                );

        Stock stock =
                marketService.getStock(symbol);

        int quantity =
                (Integer)
                        quantitySpinner
                                .getValue();

        if (stock == null) {

            showError(
                    "Please select a stock."
            );

            return;
        }

        try {

            if (buy) {

                tradingService.buy(
                        stock,
                        quantity
                );

                statusLabel.setText(
                        "BUY successful: "
                                + quantity
                                + " "
                                + symbol
                );

            } else {

                tradingService.sell(
                        stock,
                        quantity
                );

                statusLabel.setText(
                        "SELL successful: "
                                + quantity
                                + " "
                                + symbol
                );
            }

            dataService.saveTransactions(
                    tradingService.getTransactions()
            );

            refreshAll();

        } catch (IllegalArgumentException ex) {

            showError(
                    ex.getMessage()
            );
        }
    }

    private void saveHistory() {

        try {

            dataService.saveTransactions(
                    tradingService.getTransactions()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Transaction history saved successfully.",
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (RuntimeException ex) {

            showError(
                    ex.getMessage()
            );
        }
    }

    private void refreshAll() {

        refreshMarketTable();

        refreshPortfolioTable();

        refreshTransactionTable();

        updateStatistics();
    }

    private void refreshMarketTable() {

        String search =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String sector =
                String.valueOf(
                        sectorBox.getSelectedItem()
                );

        marketModel.setRowCount(0);

        for (Stock stock :
                marketService.getStocks()) {

            boolean searchMatch =
                    search.isEmpty()
                            || stock
                            .getSymbol()
                            .toLowerCase()
                            .contains(search)
                            || stock
                            .getCompanyName()
                            .toLowerCase()
                            .contains(search);

            boolean sectorMatch =
                    "All Sectors".equals(sector)
                            || stock
                            .getSector()
                            .equals(sector);

            if (!searchMatch
                    || !sectorMatch) {

                continue;
            }

            marketModel.addRow(
                    new Object[]{
                            stock.getSymbol(),
                            stock.getCompanyName(),
                            stock.getSector(),
                            money(
                                    stock.getPrice()
                            ),
                            signed(
                                    stock.getChange()
                            ),
                            signedPercent(
                                    stock.getChangePercent()
                            )
                    }
            );
        }

        String selected =
                String.valueOf(
                        stockBox.getSelectedItem()
                );

        stockBox.removeAllItems();

        for (Stock stock :
                marketService.getStocks()) {

            stockBox.addItem(
                    stock.getSymbol()
            );
        }

        if (selected != null
                && !selected.equals("null")) {

            stockBox.setSelectedItem(
                    selected
            );
        }
    }

    private void refreshPortfolioTable() {

        portfolioModel.setRowCount(0);

        Map<String, Stock> stocks =
                new HashMap<>();

        for (Stock stock :
                marketService.getStocks()) {

            stocks.put(
                    stock.getSymbol(),
                    stock
            );
        }

        for (PortfolioHolding holding :
                tradingService.getHoldings()) {

            Stock stock =
                    stocks.get(
                            holding.getSymbol()
                    );

            if (stock == null) {
                continue;
            }

            double marketValue =
                    stock.getPrice()
                            * holding.getQuantity();

            double pnl =
                    (
                            stock.getPrice()
                                    - holding
                                    .getAverageBuyPrice()
                    )
                            * holding.getQuantity();

            portfolioModel.addRow(
                    new Object[]{
                            holding.getSymbol(),
                            holding.getQuantity(),
                            money(
                                    holding
                                            .getAverageBuyPrice()
                            ),
                            money(
                                    stock.getPrice()
                            ),
                            money(marketValue),
                            money(pnl)
                    }
            );
        }
    }

    private void refreshTransactionTable() {

        transactionModel.setRowCount(0);

        for (Transaction transaction :
                tradingService.getTransactions()) {

            transactionModel.addRow(
                    new Object[]{
                            transaction.getType(),
                            transaction.getSymbol(),
                            transaction.getQuantity(),
                            money(
                                    transaction.getPrice()
                            ),
                            money(
                                    transaction.getTotal()
                            ),
                            transaction
                                    .getFormattedTime()
                    }
            );
        }
    }

    private void updateStatistics() {

        Map<String, Stock> stocks =
                new HashMap<>();

        for (Stock stock :
                marketService.getStocks()) {

            stocks.put(
                    stock.getSymbol(),
                    stock
            );
        }

        double marketValue =
                tradingService.getMarketValue(
                        stocks
                );

        double portfolioValue =
                tradingService
                        .getTotalPortfolioValue(
                                stocks
                        );

        double totalPnl =
                tradingService
                        .getRealizedPnL()
                        + tradingService
                        .getUnrealizedPnL(
                                stocks
                        );

        cashLabel.setText(
                money(
                        tradingService
                                .getCashBalance()
                )
        );

        marketValueLabel.setText(
                money(marketValue)
        );

        portfolioValueLabel.setText(
                money(portfolioValue)
        );

        pnlLabel.setText(
                money(totalPnl)
        );
    }

    private String money(
            double value) {

        return currency.format(value);
    }

    private String signed(
            double value) {

        return String.format(
                "%+.2f",
                value
        );
    }

    private String signedPercent(
            double value) {

        return String.format(
                "%+.2f%%",
                value
        );
    }

    private void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Trading Error",
                JOptionPane.WARNING_MESSAGE
        );
    }

    public static void launch() {

        SwingUtilities.invokeLater(
                () -> {

                    DashboardFrame frame =
                            new DashboardFrame();

                    frame.setVisible(true);
                }
        );
    }
}