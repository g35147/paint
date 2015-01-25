package paintcommon.shape;

import paintcommon.PointModel;
import exception.PaintException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author supeUse
 */
public class ShapeBlockModel extends ShapeModel implements Serializable {

    private List<ShapeModel> shapes;
    private boolean isGrouped;

    public ShapeBlockModel() {
        super();
        isGrouped = false;
    }

    public ShapeBlockModel(ShapeModel... shapes) throws PaintException {
        isGrouped = false;
        group(shapes);
    }

    public ShapeBlockModel(List<ShapeModel> shapes) throws PaintException {
        this(shapes.toArray(new ShapeModel[0]));
    }

    private void checkIfIsBlockShape(ShapeModel... shapes) throws PaintException {
        if (shapes.length < 2) {
            throw new PaintException("pour faire un block de Shape il faut au moins 2 Shape");
        }
    }

    public final void group(ShapeModel... shapes) throws PaintException {
        if (isGrouped) {
            throw new PaintException("cet objet est déjà groupé");
        }
        this.checkIfIsBlockShape(shapes);
        PointModel firstPoint = new PointModel(shapes[0].getX(), shapes[0].getY());
        List<PointModel> pointsReferences = new ArrayList<>();
        pointsReferences.add(firstPoint);
        for (ShapeModel shape : shapes) {
            pointsReferences.add(shape.pointDeReference);
        }
        super.setType(ShapeEnum.ShapeBlock);
        super.initSimpleShape(pointsReferences.toArray(new PointModel[0]));
        this.shapes = new ArrayList<>();
        for (ShapeModel shape : shapes) {
            this.shapes.add(shape);
        }
        isGrouped = true;
    }

    public boolean unGroup() {
        boolean isDone = false;
        if (this.shapes != null) {
            for (ShapeModel shape : this.shapes) {
                shape.pointDeReference.untie();
            }
            isDone = true;
            isGrouped = false;
            this.shapes.clear();
        }
        return isDone;
    }

    public List<ShapeModel> getShapes() {
        return this.shapes;
    }

}
