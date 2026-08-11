import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

// Task 5: fixed 4 issues - printStackTrace, null return, unsafe isValid() call, swallowed exception
public class Task5 {

    private static final Logger logger = LoggerFactory.getLogger(Task5.class);
    // FIX: added logger to replace printStackTrace() / silent swallowing

    public ValidationResult validate(Document doc) {
        try {
            if (doc == null) {
                throw new IllegalArgumentException("Document is null");
                // FIX: expected failure -> IllegalArgumentException, distinct from real errors
            }
            String content = doc.extractContent();
            if (content.isEmpty()) {
                throw new IllegalArgumentException("Empty content");
            }
            return runValidationRules(content);
        } catch (IllegalArgumentException e) {
            logger.warn("Validation failed for document: {}", e.getMessage());
            // FIX (issue 1): expected failures logged at WARN, no stack trace noise
            return new ValidationResult(false, e.getMessage());
            // FIX (issue 2): never return null
        } catch (Exception e) {
            logger.error("Unexpected error validating document", e);
            // FIX (issue 1): unexpected errors logged at ERROR with stack trace
            return new ValidationResult(false, "Unexpected validation error");
        }
    }

    public void validateBatch(List<Document> docs) {
        for (Document doc : docs) {
            try {
                ValidationResult r = validate(doc);
                if (r.isValid()) {
                    // FIX (issue 3): safe now, validate() never returns null
                    saveResult(r);
                }
            } catch (Exception e) {
                logger.error("Unexpected error processing document in batch", e);
                // FIX (issue 4): was silently swallowed, now logged
            }
        }
    }

    // Supporting types/methods, shown here only for compilation context
    private ValidationResult runValidationRules(String content) { return new ValidationResult(true, null); }
    private void saveResult(ValidationResult r) { }

    static class Document {
        String extractContent() { return ""; }
    }

    static class ValidationResult {
        private final boolean valid;
        private final String message;
        ValidationResult(boolean valid, String message) { this.valid = valid; this.message = message; }
        boolean isValid() { return valid; }
    }
}