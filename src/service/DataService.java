package service;

import model.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private final Path dataDir =
            Paths.get("data");

    private final Path transactionFile =
            dataDir.resolve("transactions.csv");

    public DataService() {

        try {
            Files.createDirectories(dataDir);
        } catch (IOException ignored) {
        }
    }

    public void saveTransactions(
            List<Transaction> transactions) {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(transactionFile)) {

            writer.write(
                    "type,symbol,quantity,price,total,time"
            );

            writer.newLine();

            for (Transaction transaction :
                    transactions) {

                writer.write(
                        transaction.toCsv()
                );

                writer.newLine();
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not save transaction history.",
                    e
            );
        }
    }

    public List<Transaction> loadTransactions() {

        List<Transaction> list =
                new ArrayList<>();

        if (!Files.exists(transactionFile)) {
            return list;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(transactionFile)) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {

                if (header) {
                    header = false;
                    continue;
                }

                String[] parts =
                        line.split(",");

                if (parts.length >= 6) {

                    list.add(
                            new Transaction(
                                    parts[0],
                                    parts[1],
                                    Integer.parseInt(parts[2]),
                                    Double.parseDouble(parts[3]),
                                    Double.parseDouble(parts[4]),
                                    LocalDateTime.parse(parts[5])
                            )
                    );
                }
            }

        } catch (Exception ignored) {
        }

        return list;
    }
}