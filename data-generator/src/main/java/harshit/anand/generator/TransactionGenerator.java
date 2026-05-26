package harshit.anand.generator;

import com.github.javafaker.Faker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class TransactionGenerator {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {

        String outputFile = "transactions.csv";

        try (
                FileWriter writer = new FileWriter(outputFile);
                CSVPrinter csvPrinter = new CSVPrinter(writer,
                        CSVFormat.DEFAULT.withHeader(
                                "transaction_id",
                                "execution_time",
                                "trader_id",
                                "account_id",
                                "symbol",
                                "side",
                                "quantity",
                                "price"
                        ))
        ) {

            for (int i = 0; i < 100000; i++) {

                String transactionId = UUID.randomUUID().toString();

                LocalDateTime executionTime =
                        LocalDateTime.now()
                                .minusMinutes(random.nextInt(10000));

                String traderId =
                        "TRD" + random.nextInt(1000);

                String accountId =
                        "ACC" + random.nextInt(5000);

                String[] symbols = {
                        "AAPL",
                        "MSFT",
                        "GOOGL",
                        "AMZN",
                        "NVDA"
                };

                String symbol =
                        symbols[random.nextInt(symbols.length)];

                String side =
                        random.nextBoolean() ? "BUY" : "SELL";

                int quantity =
                        (random.nextInt(100) + 1) * 100;

                BigDecimal price =
                        BigDecimal.valueOf(
                                100 + random.nextDouble() * 500
                        ).setScale(2, RoundingMode.HALF_UP);

                csvPrinter.printRecord(
                        transactionId,
                        executionTime,
                        traderId,
                        accountId,
                        symbol,
                        side,
                        quantity,
                        price
                );

                if (i % 10000 == 0) {
                    System.out.println("Generated: " + i);
                }
            }

            csvPrinter.flush();
        }

        System.out.println("CSV generated successfully.");
    }
}