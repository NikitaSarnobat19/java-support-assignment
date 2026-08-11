import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Task 4: conn/ps/rs never closed -> connection pool leak. Use try-with-resources.
public class Task4 {

    public static class ReportDAO {
        private DataSource dataSource;

        public List<ReportEntry> fetchMonthlyReport(String accountId, int month, int year) throws SQLException {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM report_entries " +
                     "WHERE account_id = ? AND MONTH(entry_date) = ? " +
                     "AND YEAR(entry_date) = ?")) {
                // FIX: conn + ps now auto-closed (closes ps before conn)

                ps.setString(1, accountId);
                ps.setInt(2, month);
                ps.setInt(3, year);

                List<ReportEntry> entries = new ArrayList<>();
                try (ResultSet rs = ps.executeQuery()) {
                    // FIX: rs auto-closed here, before ps/conn close above
                    while (rs.next()) {
                        entries.add(mapRow(rs));
                    }
                }
                return entries;
            }
        }

        private ReportEntry mapRow(ResultSet rs) throws SQLException {
            // unchanged
            return new ReportEntry();
        }
    }

    // Supporting type, shown here only for compilation context 
    static class ReportEntry {
    }
}