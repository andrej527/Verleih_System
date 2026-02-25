import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseControl {


    public static void runDatabase() {
        String url = "jdbc:sqlite:./data/Bibliothek.db";

        var sqlCreateTable = "CREATE TABLE IF NOT EXISTS Buch ("
                + "	isbn INTEGER PRIMARY KEY,"
                + "	titel text NOT NULL,"
                + "	verliehen BOOL"
                + ");";

        //ResultSet resultSetInsert = null;

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            try (var statement = conn.createStatement()) {
            statement.execute(sqlCreateTable);
            }
            

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void setBuch(int isbn, String titel, int verliehen) {

        String url = "jdbc:sqlite:./data/Bibliothek.db";

        var sqlInsert = "INSERT OR IGNORE INTO Buch (isbn, titel, verliehen) VALUES" + "(?, ?, ?)";

        try (var conn = DriverManager.getConnection(url)) {
            
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setLong(1, isbn);
                ps.setString(2, titel);
                ps.setInt(3, verliehen);
                int insertResult = ps.executeUpdate();
                System.out.println("Result from insert: " + insertResult);
            }
        

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    public static void deleteBuch(int isbn) {
    String url = "jdbc:sqlite:./data/Bibliothek.db";
    String sql = "DELETE FROM Buch WHERE ?";

    try (var conn = DriverManager.getConnection(url);
            var ps = conn.prepareStatement(sql)) {

            ps.setInt(1, isbn);

            int deletedRows = ps.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Book deleted.");
            } else {
                System.out.println("No book found with that ISBN.");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getBuch() {
        String url = "jdbc:sqlite:./data/Bibliothek.db";

        StringBuilder result = new StringBuilder();

        var sqlSelect = "SELECT isbn, titel, verliehen FROM Buch";

         try (var conn = DriverManager.getConnection(url)) {

            try (var statement = conn.createStatement()) {
                var rs = statement.executeQuery(sqlSelect);

                while (rs.next()) {
                    result.append(
                        "ISBN: " +
                        rs.getInt("isbn") +
                        " TITEL: " +
                        rs.getString("titel") +
                        " VERLIEHEN: " + 
                        rs.getBoolean("verliehen") +
                        " "
                    );
                }
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result.toString();
    }
    
}
