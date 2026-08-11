import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task1 {

    public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
        List<LoanAccount> result = new ArrayList<>();
        // FIX: initialize to empty ArrayList instead of null (defect 1) —
        // prevents NPE on result.add() and guarantees a non-null return.

        for (LoanAccount account : accounts) {
            if (account.getDueDate() != null && account.getDueDate().before(new Date())) {
                // FIX: added null-check on getDueDate() (defect 2) — restructured
                // accounts have a null dueDate, which previously caused NPE.
                if (account.getOutstandingBalance() > 0) {
                    result.add(account);
                }
            }
        }
        return result;
    }

    // ---- Supporting type, shown here only for compilation context ----
    static class LoanAccount {
        private Date dueDate;
        private double outstandingBalance;
        private String accountId;

        public Date getDueDate() {
            return dueDate;
        }

        public void setDueDate(Date dueDate) {
            this.dueDate = dueDate;
        }

        public double getOutstandingBalance() {
            return outstandingBalance;
        }

        public void setOutstandingBalance(double outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }
    }
}