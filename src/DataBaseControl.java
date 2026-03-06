import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

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

        var sqlCreateTableAusleihen = "CREATE TABLE IF NOT EXISTS Ausleihen ("
                + "ausleiheID INTEGER PRIMARY KEY,"
                + "nutzerID INTEGER NOT NULL,"
                + "isbn INTEGER INTEGER NOT NULL,"
                + "vonDatum DATE NOT NULL DEFAULT (DATE('now')),"
                + "rueckgabeDatum DATE"
                + "FOREIGN KEY (nutzerID) REFERENCES Nutzer(nutzerID)"
                + " FOREIGN KEY (isbn) REFERENCES Buch(isbn)"
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

            try (var statement = conn.createStatement()) {
            statement.execute(sqlCreateTableAusleihen);
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
                            .append(rs.getBoolean("verliehen"))
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

    public static void buchAusleihen(int isbn, int nutzerId) {

        String url = "jdbc:sqlite:./data/Bibliothek.db";

        var sqlInsert = "INSERT OR IGNORE INTO Ausleihen (ausleiheId, nutzerId, isbn, vonDatum, rueckgabeDatum) VALUES" + "(?, ?, ?, ?, ?)";
        var sqlUpdate = "UPDATE Buch SET verliehen = 1 WHERE isbn = ?";

        try (var conn = DriverManager.getConnection(url)) {

            Random rand = new Random();
            LocalDate vonDatum = LocalDate.now();
            LocalDate rueckgabeDatum = vonDatum.plusDays(30);
            int ausleiheId = rand.nextInt(1000000);

            
            try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                ps.setInt(1, ausleiheId);
                ps.setInt(2, nutzerId);
                ps.setInt(3, isbn);
                ps.setDate(4, java.sql.Date.valueOf(vonDatum));
                ps.setDate(5, java.sql.Date.valueOf(rueckgabeDatum));
                int insertResult = ps.executeUpdate();
                System.out.println("Result from insert: " + insertResult);
            }

            try (PreparedStatement pu = conn.prepareStatement(sqlUpdate)) {
                pu.setInt(1, isbn);
                int updateResult = pu.executeUpdate();
                System.out.println("Update result: " + updateResult);
            }
        

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    public static void buchRueckgabe(int nutzerId, int isbn) {
    String url = "jdbc:sqlite:./data/Bibliothek.db";
    String sql = "DELETE FROM Ausleihen WHERE ? AND ?";
    String sqlUpdate = "UPDATE Buch SET verliehen = 0 WHERE isbn = ?";

    try (var conn = DriverManager.getConnection(url);
            var ps = conn.prepareStatement(sql)) {

            ps.setInt(2, nutzerId);
            ps.setInt(3, isbn);

            int deletedRows = ps.executeUpdate();

            if (deletedRows > 0) {
                System.out.println("Buch erfolgreich zurückgegeben");
            } else {
                System.out.println("No book found with that ISBN and Nutzer");
            }


            try (PreparedStatement pu = conn.prepareStatement(sqlUpdate)) {
                pu.setInt(1, isbn);
                int updateResult = pu.executeUpdate();
                System.out.println("Update result: " + updateResult);
            }
            

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getVerlieheneBuch() {
        String url = "jdbc:sqlite:./data/Bibliothek.db";
        String sql = "SELECT ausleiheId, nutzerId, isbn, vonDatum, rueckgabeDatum FROM Ausleihen";

        StringBuilder result = new StringBuilder();

        try (var conn = DriverManager.getConnection(url)) { 

            try (var statement = conn.createStatement()) {
                var rs = statement.executeQuery(sql);

                while (rs.next()) {
                    result.append("ausleiheID: ")
                            .append(rs.getInt("ausleiheId"))
                            .append(", NutzerID: ")
                            .append(rs.getString("nutzerId"))
                            .append(", ISBN: ")
                            .append(rs.getBoolean("isbn"))
                            .append(",von Datum: ")
                            .append(rs.getDate("vonDatum"))
                            .append("Rückgabe Datum: ")
                            .append(rs.getDate("rueckgabeDatum"))
                            .append("\n"); 
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return result.toString();
        
    }
    
}
