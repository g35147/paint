package db;

import dto.FigureDto;
import dto.PointDto;
import dto.ShapeDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author G35147
 */
public class PaintDB {

    public static FigureDto getFigure(String x) throws PaintPersistanceException {
        try {
            String query = "SELECT id, figurename FROM FIGURE WHERE figurename = ?  ";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setString(1, x);

            java.sql.ResultSet rs = stmt.executeQuery();
            FigureDto target = null;
            if (rs.next()) {
                target = new FigureDto(rs.getLong("id"), x);
            }
            return target;
        } catch (SQLException e) {
            throw new PaintPersistanceException("Figure not found");
        }
    }

    public static void insertFigure(FigureDto target) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO FIGURE(id , figurename ) " + " values(?, ?)";
            java.sql.PreparedStatement insert = connexion.prepareStatement(query);
            insert.setLong(1, target.getId());
            insert.setString(2, target.getFigurename());
            insert.execute();
        } catch (SQLException e) {
            throw new PaintPersistanceException(e.getMessage());
        }
    }

    public static void insertShape(ShapeDto target) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO SHAPE (id,shapetype,strokecolor,fillcolor,"
                    + "figure,radius,shape, strokewidth) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement insert = connexion.prepareStatement(query);
            insert.setLong(1, target.getId());
            insert.setString(2, target.getShapetype().name());
            insert.setString(3, target.getStrokecolor());
            insert.setString(4, target.getFillcolor());
            if (target.getFigure() != null) {
                insert.setLong(5, target.getFigure());
            } else {
                insert.setNull(5, java.sql.Types.LONGVARCHAR);
            }
            if (target.getRadius() != null) {
                insert.setDouble(6, target.getRadius());
            } else {
                insert.setNull(6, java.sql.Types.DOUBLE);
            }
            if (target.getShape() != null) {
                insert.setLong(7, target.getShape());
            } else {
                insert.setNull(7, java.sql.Types.LONGVARCHAR);
            }
            insert.setDouble(8, target.getStrokeWidth());

            insert.execute();
        } catch (SQLException e) {
            throw new PaintPersistanceException(e.getMessage());
        }
    }

    public static void insertPoints(Collection<PointDto> target) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO POINT (id ,x ,y , "
                    + "shape,listorder) values(?, ?, ?, ?, ?)";
            java.sql.PreparedStatement insert = connexion.prepareStatement(query);

            for (PointDto p : target) {
                insert.setLong(1, p.getId());
                insert.setDouble(2, p.getX());
                insert.setDouble(3, p.getY());
                insert.setLong(4, p.getShape());
                insert.setLong(5, p.getListorder());
                insert.execute();

            }

        } catch (SQLException e) {
            throw new PaintPersistanceException(e.getMessage());
        }
    }

    public static void SaveFigure(FigureDto aDto) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();
            String query = "INSERT INTO FIGURE(id , figurename ) " + " values(?, ?)";
            java.sql.PreparedStatement insert = connexion.prepareStatement(query);
            insert.setLong(1, aDto.getId());
            insert.setString(2, aDto.getFigurename());
            insert.execute();
            query = "INSERT INTO Shape (id,shapetype,strokecolor,fillcolor,"
                    + "figure,radius,shape values(?, ?, ?, ?, ?, ?, ?)";
            insert = connexion.prepareStatement(query);
            for (ShapeDto v : aDto.getShapes()) {
                insert.setLong(1, v.getId());
                insert.setString(2, v.getShapetype().name());
                insert.setString(3, v.getStrokecolor());
                insert.setString(4, v.getFillcolor());
                insert.setLong(5, v.getFigure());
                insert.setDouble(6, v.getRadius());
                insert.setLong(7, v.getShape());

                insert.execute();
                query = "INSERT INTO POINT (id ,x ,y , "
                        + "shape,listorder values(?, ?, ?, ?, ?, ?)";
                insert = connexion.prepareStatement(query);
                for (PointDto p : v.getPointList()) {
                    insert.setLong(1, p.getId());
                    insert.setDouble(2, p.getX());
                    insert.setDouble(3, p.getY());
                    insert.setLong(4, p.getShape());
                    insert.setLong(5, p.getListorder());
                    insert.execute();
                }
            }
        } catch (SQLException ex) {
            throw new PaintPersistanceException("Ajout de liste impossible:\r SQLException: " + ex.getMessage());
        }
    }

    public static List<ShapeDto> getFigureShapes(long x) throws PaintPersistanceException {
        try {
            String query = "SELECT id, shapetype, strokecolor, fillcolor,"
                    + " figure, shape, "
                    + " radius, strokewidth FROM SHAPE WHERE figure = ?  ";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setLong(1, x);

            java.sql.ResultSet rs = stmt.executeQuery();
            List<ShapeDto> target = new ArrayList<>();
            while (rs.next()) {
                target.add(new ShapeDto(rs.getLong("id"), ShapeEnum.valueOf(rs.getString("shapetype")),
                        rs.getString("strokecolor"), rs.getString("fillcolor"),
                        rs.getLong("figure"), rs.getLong("shape"), rs.getDouble("radius"), rs.getDouble("strokewidth")));
            }
            return target;
        } catch (SQLException e) {
            throw new PaintPersistanceException("SHAPE  not found");
        }
    }

    public static List<ShapeDto> getShapeShapes(long x) throws PaintPersistanceException {
        try {
            String query = "SELECT id, shapetype, strokecolor, fillcolor,"
                    + " figure, shape,"
                    + " radius, strokewidth FROM SHAPE WHERE shape = ?  ";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setLong(1, x);

            java.sql.ResultSet rs = stmt.executeQuery();
            List<ShapeDto> target = new ArrayList<>();
            while (rs.next()) {
                target.add(new ShapeDto(rs.getLong("id"), ShapeEnum.valueOf(rs.getString("shapetype")),
                        rs.getString("strokecolor"), rs.getString("fillcolor"),
                        rs.getLong("figure"), rs.getLong("shape"), rs.getDouble("radius"), rs.getDouble("strokewidth")));
            }
            return target;
        } catch (SQLException e) {
            throw new PaintPersistanceException("SHAPE  not found");
        }
    }

    public static PointDto FindPointByPoint(long x) throws PaintPersistanceException {
        try {
            String query = "SELECT id, x, y, tiedpoint, shape ,listorder FROM POINT WHERE id = ?";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setLong(1, x);

            java.sql.ResultSet rs = stmt.executeQuery();
            PointDto target = null;
            if (rs.next()) {
                target = new PointDto(rs.getLong("id"),
                        rs.getDouble("x"), rs.getDouble("y"), rs.getLong("shape"), rs.getLong("listorder"));
            }
            return target;
        } catch (SQLException e) {
            throw new PaintPersistanceException("POINT  not found");
        }
    }

    public static List<PointDto> getShapePoints(long x) throws PaintPersistanceException {
        List<PointDto> target = new ArrayList<PointDto>();
        try {

            String query = "SELECT id, x, y ,shape, listorder FROM POINT "
                    + "WHERE shape = ? ORDER BY listorder";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareStatement(query);
            stmt.setLong(1, x);

            java.sql.ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                target.add(new PointDto(rs.getLong("id"),
                        rs.getDouble("x"), rs.getDouble("y"), rs.getLong("shape"), rs.getLong("listorder")));
            }

        } catch (SQLException e) {
            throw new PaintPersistanceException("POINT  not found shape = " + x + " " + e.getMessage());
        }
        return target;
    }

    public static void DeleteFigure(String name) throws PaintPersistanceException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();

            String queryDel2 = "DELETE FROM FIGURE WHERE figurename = ?";
            java.sql.PreparedStatement delete = connexion.prepareStatement(queryDel2);
            delete.setString(1, name);
            delete.execute();

        } catch (SQLException ex) {
            throw new PaintPersistanceException("Supression impossible:\r SQLException: " + ex.getMessage());
        }
    }

    public static List<FigureDto> NamesList(String nom) throws PaintPersistanceException {
        List<FigureDto> al = new ArrayList<FigureDto>();

        try {
            String query = "SELECT id, NOM, Datemaj FROM Figure WHERE figurename LIKE ? ";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareCall(query);
            stmt.setString(1, "%" + nom + "%");
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new FigureDto(rs.getLong("id"), rs.getString("NOM"), rs.getDate("DateMaj")));
            }
        } catch (SQLException e) {
            throw new PaintPersistanceException("rien trouvé");
        }
        return al;
    }

    public static List<String> GetAllFigure() throws PaintPersistanceException {
        List<String> al = new ArrayList<String>();

        try {
            String query = "SELECT id, figurename FROM Figure ";

            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt = connexion.prepareCall(query);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(rs.getString("figurename"));
            }
        } catch (SQLException e) {
                throw new PaintPersistanceException(e.getMessage());
        }
        return al;
    }

}
