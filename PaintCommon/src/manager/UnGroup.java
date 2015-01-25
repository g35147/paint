package manager;

import exception.PaintException;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class UnGroup extends Action {

    ShapeModel[] shapes;
    ShapeModel newShape;

    public UnGroup(ShapeModel shape, ShapeModel... shapes) {
        super(shape);
        newShape = shape;
        this.shapes = shapes;
        this.currentaction = ActionEnum.Ungroup;
    }

    public ShapeModel[] getShapes() {
        return shapes;
    }

    public void setShapes(ShapeModel[] shapes) {
        this.shapes = shapes;
    }

    @Override
    public void undo() throws PaintException {
        place.shapes.add(shape);
        for (ShapeModel aShape : shapes) {
            place.shapes.remove(aShape);
        }
        ((ShapeBlockModel) this.newShape).group(shapes);
    }

    @Override
    public void execute() throws PaintException {
        place.shapes.remove(shape);
        ((ShapeBlockModel) this.newShape).unGroup();
        for (ShapeModel shape1 : shapes) {
            place.shapes.add(shape1);
        }
    }
}
