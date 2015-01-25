package manager;

import paintcommon.PointModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class Move extends Action {

    private int shapeIndex;
    private PointModel oldPoint;
    private PointModel newPoint;

    public Move(ShapeModel shape) {
        super(shape);
        this.initPosition();
        this.currentaction = ActionEnum.Move;
    }

    public void setNewPosition(double newX, double newY) {
        this.newPoint = new PointModel(newX, newY);
    }

    public void setOldPosition(double oldX, double oldY) {
        this.oldPoint = new PointModel(oldX, oldY);
    }

    private void initPosition() {
        this.oldPoint = new PointModel(shape.getX(), shape.getY());
    }

    private void moveToOldPosition() {
        shape.move(oldPoint.getX(), oldPoint.getY());
    }

    private void moveToNewPosition() {
        shape.move(newPoint.getX(), newPoint.getY());
    }

    @Override
    public void undo() {
        place.shapes.remove(shape);
        moveToOldPosition();
        place.shapes.add(shapeIndex, shape);
    }

    @Override
    public void execute() {
        place.shapes.remove(shape);
        moveToNewPosition();
        place.shapes.add(shape);
    }

}
