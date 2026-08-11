import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Task 1: Fixed 3 defects - null result list, null dueDate NPE, null return
public class Task1 {

    public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
        List<LoanAccount> result = new ArrayList<>();
        // FIX: was `null` -> caused NPE on add() and null return when nothing matched

        for (LoanAccount account : accounts) {
            if (account.getDueDate() != null && account.getDueDate().before(new Date())) {
                // FIX: null-check dueDate; restructured accounts can have it null
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

        public Date getDueDate() { return dueDate; }
        public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
        public double getOutstandingBalance() { return outstandingBalance; }
        public void setOutstandingBalance(double outstandingBalance) { this.outstandingBalance = outstandingBalance; }
        public String getAccountId() { return accountId; }
        public void setAccountId(String accountId) { this.accountId = accountId; }
    }
}