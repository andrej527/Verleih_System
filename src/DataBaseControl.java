import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseControl {


    public static void runDatabase() {
        String url = "jdbc:sqlite:./data/Bibliothek.db";

        var sqlCreateTableBuch = "CREATE TABLE IF NOT EXISTS Buch ("
                + "	isbn INTEGER PRIMARY KEY,"
                + "	titel text NOT NULL,"
                + "	verliehen BOOL"
                + ");";

        var sqlCreateTableNutzer = "CREATE TABLE IF NOT EXISTS Nutzer ("
                + "nutzerID INTEGER PRIMARY KEY,"
                + "name text NOT NULL"
                + ");";

        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            try (var statement = conn.createStatement()) {
            statement.execute(sqlCreateTableBuch);
            }

            try (var statement = conn.createStatement()) {
            statement.execute(sqlCreateTableNutzer);
            }
            

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    public static void addBuch(int isbn, String titel, int verliehen) {

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

    public static void addNutzer(int nutzerId, String name) {

        String url = "jdbc:sqlite:./data/Bibliothek.db";

        var sqlInsert = "INSERT OR IGNORE INTO Nutzer (nutzerID, name) VALUES" + "(?, ?)";

        try (var conn = DriverManager.getConnection(url)) {
            
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setLong(1, nutzerId);
                ps.setString(2, name);
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

    public static void deleteUser(int userId) {
    String url = "jdbc:sqlite:./data/Bibliothek.db";
    String sql = "DELETE FROM Nutzer WHERE ?";

    try (var conn = DriverManager.getConnection(url);
            var ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            int deletedRows = ps.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("User deleted.");
            } else {
                System.out.println("No user found with this Id");
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
                    result.append("ISBN: ")
                            .append(rs.getInt("isbn"))
                            .append(", TITEL: ")
                            .append(rs.getString("titel"))
                            .append(", VERLIEHEN: ")
                            .append(rs.getInt("verliehen"))
                            .append("\n"); 
                }
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result.toString();
    }

    public static String getNutzer() {
        String url = "jdbc:sqlite:./data/Bibliothek.db";

        StringBuilder result = new StringBuilder();

        var sqlSelect = "SELECT nutzerID, name FROM Nutzer";

        try (var conn = DriverManager.getConnection(url)) {

            try (var statement = conn.createStatement()) {
                var rs = statement.executeQuery(sqlSelect);

                while (rs.next()) {
                    result.append("ID: ")
                            .append(rs.getInt("nutzerId"))
                            .append(", NAME: ")
                            .append(rs.getString("name"))
                            .append("\n"); 
                }
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result.toString();
    }
    
}
