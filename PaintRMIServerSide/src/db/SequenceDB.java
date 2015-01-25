package db;

import java.sql.SQLException;

/**
 * Classe d'accès au gestionnaire de persistance pour les Séquences
 */
public class SequenceDB {

    static synchronized int getNumSuivant(String sequence) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "Update SEQUENCES set seqNum= seqNum+1 where seqName='" + sequence + "'";
            java.sql.PreparedStatement update = connexion.prepareStatement(query);
            update.execute();
            java.sql.Statement stmt = connexion.createStatement();
            query = "Select seqNum FROM Sequences where seqName='" + sequence + "'";
            java.sql.ResultSet rs = stmt.executeQuery(query);
            int nvId;
            if (rs.next()) {
                nvId = rs.getInt("seqNum");
                return nvId;
            } else {
                throw new PaintPersistanceException("Nouveau n° de séquence inaccessible!");
            }
        } catch (java.sql.SQLException eSQL) {
            throw new PaintPersistanceException("Nouveau n° de séquence inaccessible!\n" + eSQL.getMessage());
        }

    }

    public static long getNextId(String table) throws PaintPersistanceException {
        try {
            String query = "Select SEQ_COUNT FROM SEQUENCE where SEQ_NAME= ?";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, table);

            java.sql.ResultSet rs = stmt.executeQuery();
            long nextId = 0;
            if (rs.next()) {
                nextId = rs.getLong("SEQ_COUNT");
            }
            return nextId;
        } catch (SQLException e) {
            throw new PaintPersistanceException("Figure not found " + e.getMessage());
        }
    }

    public static int setNextId(String table, Long id) throws PaintPersistanceException {
        try {
            String query = "UPDATE SEQUENCE\n"
                    + "SET SEQ_COUNT=?\n"
                    + "WHERE SEQ_NAME=?";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setLong(1, id);
            stmt.setString(2, table);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new PaintPersistanceException("Figure not found " + e.getMessage());
        }
    }

}
