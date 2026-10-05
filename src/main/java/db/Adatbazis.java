package db;

import java.sql.*;

public class Adatbazis {
    private static final String URL = "jdbc:sqlite:highscores.db";

    public void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS highscores (name TEXT PRIMARY KEY, wins INTEGER)";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("DB hiba: " + e.getMessage());
        }
    }

    public void incrementWin(String playerName) {
        String upsertSql = "INSERT INTO highscores(name, wins) VALUES(?, 1) ON CONFLICT(name) DO UPDATE SET wins = wins + 1;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(upsertSql)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
        } catch (SQLException ignore) {}
    }

    public void printHighScores() {
        String query = "SELECT name, wins FROM highscores ORDER BY wins DESC LIMIT 10";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getInt("wins") + " nyert meccs");
            }
        } catch (SQLException ignore) {}
    }
}