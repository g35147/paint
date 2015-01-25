package rmiAdd;

import db.PaintDB;
import db.PaintPersistanceException;
import dto.FigureDto;
import dto.PointDto;
import dto.ShapeDto;
import exception.PaintException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import manager.Action;
import manager.ActionEnum;
import static manager.ActionEnum.Load;
import manager.Draw;
import manager.Fill;
import manager.GroupForms;
import manager.Load;
import manager.Move;
import manager.Save;
import manager.UnGroup;
import metier.FacadeServer;
import paintcommon.FigureModel;
import paintcommon.PaintInterface;
import paintcommon.PaintViewInterface;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;
import paintcommon.shape.CircleModel;
import paintcommon.shape.LineModel;
import paintcommon.shape.PathModel;
import paintcommon.shape.PolygonModel;
import paintcommon.shape.RectangleModel;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class Paint extends UnicastRemoteObject
        implements PaintInterface {

    private final FigureModel etat;
    private final PaintView vues;
    private final List<Action> actions; // pour undo-redo
    private int lastAction; // position de la dernière action

    public Paint() throws RemoteException {
        etat = new FigureModel();
        vues = new PaintView();
        actions = new ArrayList<>();
        lastAction = -1; // qd la liste est vide l'indice vaut -1
    }

    public void ajouterAction(Action action) {
        removeActionsEnTrop();
        lastAction = lastAction + 1;
        actions.add(action);
    }

    private void removeActionsEnTrop() {
        int i = lastAction + 1;
        while (i < actions.size() && i >= 0) {
            actions.remove(i);
        }
    }

    private void redo() throws PaintException {
        if (lastAction + 1 < actions.size()) {
            lastAction++;
            actions.get(lastAction).execute();
        }
    }

    private void undo() throws PaintException {
        if (lastAction >= 0) {
            actions.get(lastAction).undo();
            lastAction--;
        }
    }

    @Override
    public void setState(Action action) throws RemoteException {
        try {
            switch (action.getCurrentaction()) {
                case Move:
                    Move move = (Move) action;
                    move.setPlace(this.etat);
                    try {
                        move.setShape(this.etat.getShape(move.getShape().getId()));
                    } catch (PaintException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Fill:
                    Fill fill = (Fill) action;
                    fill.setPlace(this.etat);
                    try {
                        fill.setShape(this.etat.getShape(fill.getShape().getId()));
                    } catch (PaintException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Draw:
                    Draw draw = (Draw) action;
                    draw.getShape().setId(ShapeModel.nextId++);
                    for (PointModel point : draw.getShape().getPoints()) {
                        point.setId(PointModel.nextId++);
                    }
                    draw.setPlace(this.etat);
                    break;
                case Group:
                    GroupForms group = (GroupForms) action;
                    for (int i = 0; i < group.getShapes().length; i++) {
                        group.getShapes()[i] = this.etat.getShape(group.getShapes()[i].getId());
                    }
                    group.setShape(new ShapeBlockModel());
                    group.setPlace(this.etat);
                    break;
                case Undo:
                    undo();
                    break;
                case Redo:
                    redo();
                    break;
                case Save:
                    try {
                        saveFigure(((Save) action).figurename);
                    } catch (PaintPersistanceException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Ungroup:
                    UnGroup unGroup = (UnGroup) action;
                    unGroup.setPlace(this.etat);
                    try {
                        unGroup.setShape(this.etat.getShape(unGroup.getShape().getId()));
                    } catch (PaintException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Load:
                    try {
                        loadFigure(((Load) action).figurename);
                    } catch (PaintPersistanceException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case FigureList:
                    try {
                        etat.setFigureNameList(FacadeServer.getAllFigure());
                        System.out.println(etat.getFigureNameList() + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                    } catch (PaintPersistanceException ex) {
                        Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
            }
            
            try {
                etat.setFigureNameList(FacadeServer.getAllFigure());
            } catch (PaintPersistanceException ex) {
                Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
             * mise à jour du model
             */
            if (!(action.getCurrentaction() == ActionEnum.Redo)
                    && !(action.getCurrentaction() == ActionEnum.Undo)
                    && !(action.getCurrentaction() == ActionEnum.Load)
                    && !(action.getCurrentaction() == ActionEnum.Save)) {
                action.execute();
                ajouterAction(action);
            }
            vues.fire();
        } catch (PaintException ex) {
            Logger.getLogger(Paint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public FigureModel getState() {
        return (FigureModel) etat;
    }

    @Override
    public PaintViewInterface getViews() {
        return vues;
    }

    public void saveFigure(String name) throws PaintPersistanceException, PaintException {
        boolean newFigure;
        if (PaintDB.getFigure(name) == null) {
            newFigure = true;
            etat.id = FigureModel.nextId++;
            etat.name = name;
        } else {
            if (etat.name.equals(name)) {
                newFigure = false;
            } else {
                newFigure = true;
                etat.id = FigureModel.nextId++;
                etat.name = name;
            }
            FacadeServer.deleteFigure(name);
        }
        FigureDto figureDto = new FigureDto(etat.id, etat.name);
        figureDto.setShapes(new ArrayList<ShapeDto>());
        shapeDtoManagment(figureDto.getShapes(), etat.shapes, etat.id, false, newFigure);
        FacadeServer.saveFigure(figureDto);
        FacadeServer.setNextId("Figure", FigureModel.nextId);
        FacadeServer.setNextId("Shape", ShapeModel.nextId);
        FacadeServer.setNextId("Point", PointModel.nextId);
        if (newFigure) {
            loadFigure(name);
        }
    }

    private static void shapeDtoManagment(Collection<ShapeDto> listToAdd,
            List<ShapeModel> shapes, Long idPere, boolean isFromShape, boolean newFigure) throws PaintPersistanceException, PaintException {
        ShapeDto shapeDto = null;
        for (ShapeModel shape : shapes) {
            Collection<PointDto> dtoPoints = prepareDtoPoints(shape.getPoints(), shape);
            Collection<ShapeDto> dtoShapes = new ArrayList<>();
            Double radius = null;
            Long fig = isFromShape ? null : idPere;
            Long shp = isFromShape ? idPere : null;

            shapeDto = new ShapeDto(shape.getId(), shape.getType(), shape.getStrokeColor(), shape.getFillColor(), fig, shp, null, shape.getStrokeWidth());
            if (newFigure) {
                shapeDto.setId(ShapeModel.nextId++);
            }

            if (shape.getType() == ShapeEnum.ShapeBlock) {
                shapeDtoManagment(dtoShapes, ((ShapeBlockModel) shape).getShapes(), shapeDto.getId(), true, newFigure);
                shapeDto.setShapeList(dtoShapes);
            } else {
                if (shape.getType() == ShapeEnum.Circle) {
                    radius = ((CircleModel) shape).getRadiuslenght();
                }
                shapeDto.setRadius(radius);
                shapeDto.setPointList(dtoPoints);
                if (newFigure) {
                    pointNextId(shapeDto.getPointList(), shapeDto.getId());
                }
            }
            listToAdd.add(shapeDto);
        }
    }

    private static void pointNextId(Collection<PointDto> points, Long idPere) {
        for (PointDto point : points) {
            point.setId(PointModel.nextId++);
            point.setShape(idPere);
        }
    }

    private static Collection<PointDto> prepareDtoPoints(List<PointModel> points, ShapeModel shape) {
        Collection<PointDto> pointsDto = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            pointsDto.add(new PointDto(points.get(i).getId(), points.get(i).getX(), points.get(i).getY(), Integer.toUnsignedLong(i), shape.getId()));
        }
        return pointsDto;
    }

    public void loadFigure(String name) throws PaintPersistanceException, PaintException {
        FigureDto figureDto = FacadeServer.loadFigure(name);
        etat.shapes.clear();
        etat.id = figureDto.getId();
        etat.name = figureDto.getFigurename();
        FigureModel.nextId = FacadeServer.getNextId("Figure");
        ShapeModel.nextId = FacadeServer.getNextId("Shape");
        PointModel.nextId = FacadeServer.getNextId("Point");
        shapeManagment(etat.shapes, figureDto.getShapes());
    }

    private static void shapeManagment(List<ShapeModel> listToAdd,
            Collection<ShapeDto> shapes) throws PaintPersistanceException, PaintException {
        ShapeModel shapeModel = null;
        for (ShapeDto shape : shapes) {
            List<PointModel> points = preparePoints(shape.getPointList());
            switch (shape.getShapetype()) {
                case Rectangle:
                    shapeModel = new RectangleModel(points.get(0), points.get(1), points.get(2), points.get(3));
                    break;
                case Circle:
                    shapeModel = new CircleModel(points.get(0), shape.getRadius());
                    break;
                case Polygon:
                    shapeModel = new PolygonModel(points);
                    break;
                case ShapeBlock:
                    List<ShapeModel> blockShapes = new ArrayList<>();
                    shapeManagment(blockShapes, shape.getShapeList());
                    shapeModel = new ShapeBlockModel(blockShapes);
                    break;
                case Line:
                    shapeModel = new LineModel(points.get(0), points.get(1));
                    break;
                case FreeHand:
                    shapeModel = new PathModel(points);
            }
            shapeModel.setStrokeColor(shape.getStrokecolor());
            shapeModel.setFillColor(shape.getFillcolor());
            shapeModel.setStrokeWidth(shape.getStrokeWidth());
            setIds(shapeModel, shape);
            listToAdd.add(shapeModel);
        }
    }

    private static void setIds(ShapeModel shapeModel, ShapeDto shape) {
        List<PointDto> dtoPoints = (List<PointDto>) shape.getPointList();

        shapeModel.setId(shape.getId());
        if (shape.getShapetype() != ShapeEnum.ShapeBlock) {
            for (int i = 0; i < dtoPoints.size(); i++) {
                shapeModel.getPoints().get(i).setId(dtoPoints.get(i).getId());
            }
        }
    }

    private static List<PointModel> preparePoints(Collection<PointDto> dtoPoints) {
        List<PointModel> points = new ArrayList<>();
        for (PointDto pointDto : dtoPoints) {
            points.add(new PointModel(pointDto.getX(), pointDto.getY()));
        }
        return points;
    }

}
