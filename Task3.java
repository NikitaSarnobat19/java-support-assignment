import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// Task 3: processedCount++ isn't atomic across threads -> lost updates. Use AtomicInteger.
public class Task3 {

    public static class BankStatementBatchProcessor {
        private final AtomicInteger processedCount = new AtomicInteger(0);
        // FIX: int -> AtomicInteger (plain ++ is not thread-safe)

        public void process(List<StatementRecord> records) {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (StatementRecord record : records) {
                executor.submit(() -> {
                    processRecord(record);
                    processedCount.incrementAndGet();
                    // FIX: atomic increment instead of processedCount++
                });
            }
            executor.shutdown();
            try {
                executor.awaitTermination(5, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public int getProcessedCount() {
            return processedCount.get();
        }

        private void processRecord(StatementRecord record) {
            // unchanged
        }
    }

    //Supporting type, shown here only for compilation context
    static class StatementRecord {
    }
}