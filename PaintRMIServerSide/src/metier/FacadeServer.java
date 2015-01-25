package metier;

import db.DBManager;
import db.PaintDB;
import db.PaintPersistanceException;
import db.SequenceDB;
import dto.FigureDto;
import dto.ShapeDto;
import java.util.Collection;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 */
public class FacadeServer {

    public static void deleteFigure(String name) throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            PaintDB.DeleteFigure(name);
            DBManager.valideTransaction();
        } catch (PaintPersistanceException eDB) {
            eDB.printStackTrace();
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

    public static List<String> getAllFigure() throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            List<String> list = PaintDB.GetAllFigure();
            DBManager.valideTransaction();
            return list;
        } catch (PaintPersistanceException eDB) {
            eDB.printStackTrace();
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

    public static void saveFigure(FigureDto target) throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            setFigure(target);
            DBManager.valideTransaction();
        } catch (PaintPersistanceException eDB) {
            eDB.printStackTrace();
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

    private static void setFigure(FigureDto target) throws PaintPersistanceException {
        PaintDB.insertFigure(target);
        shapeSaving(target.getShapes());
    }

    private static void shapeSaving(Collection<ShapeDto> shapes) throws PaintPersistanceException {
        for (ShapeDto shape : shapes) {
            PaintDB.insertShape(shape);
            if (shape.getShapetype() == ShapeEnum.ShapeBlock) {
                /*récursif*/
                shapeSaving(shape.getShapeList());

            } else {
                PaintDB.insertPoints(shape.getPointList());
            }
        }
    }

    public static FigureDto loadFigure(String name) throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            FigureDto figure = getFigure(name);
            DBManager.valideTransaction();
            return figure;
        } catch (PaintPersistanceException eDB) {
            eDB.printStackTrace();
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

    private static FigureDto getFigure(String name) throws PaintPersistanceException {
        System.out.println("load : le nom de la figure " + name);
        FigureDto figure = PaintDB.getFigure(name);
        figure.setShapes(PaintDB.getFigureShapes(figure.getId()));

        shapeManagment(figure.getShapes());

        return figure;
    }

    private static void shapeManagment(Collection<ShapeDto> shapes) throws PaintPersistanceException {
        for (ShapeDto shape : shapes) {

            if (shape.getShapetype() == ShapeEnum.ShapeBlock) {
                shape.setShapeList(PaintDB.getShapeShapes(shape.getId()));
                /*récursif*/
                shapeManagment(shape.getShapeList());
            } else {
                shape.setPointList(PaintDB.getShapePoints(shape.getId()));
            }
        }
    }

    public static long getNextId(String name) throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            long figure = SequenceDB.getNextId(name);
            DBManager.valideTransaction();
            return figure;
        } catch (PaintPersistanceException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

    public static void setNextId(String name, Long id) throws PaintPersistanceException {
        try {
            DBManager.startTransaction();
            SequenceDB.setNextId(name, id);
            DBManager.valideTransaction();
        } catch (PaintPersistanceException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.annuleTransaction();
            } catch (PaintPersistanceException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new PaintPersistanceException(" \n" + msg);
            }
        }
    }

}
