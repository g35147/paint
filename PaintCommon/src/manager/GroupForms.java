package manager;

import exception.PaintException;
import java.util.ArrayList;
import java.util.List;
import paintcommon.shape.ShapeBlockModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class GroupForms extends Action {

    ShapeModel[] shapes;
    List<Integer> positions;

    public GroupForms(ShapeModel... shapes) {
        super(null);
        this.shapes = shapes;
        positions = new ArrayList<>();
        this.currentaction = ActionEnum.Group;
    }

    public ShapeModel[] getShapes() {
        return shapes;
    }

    public void setShapes(ShapeModel[] shapes) {
        this.shapes = shapes;
    }

    @Override
    public void undo() throws PaintException {
        place.shapes.remove(shape);
        ((ShapeBlockModel) this.shape).unGroup();
        for (int i = 0; i < shapes.length; i++) {
            place.shapes.add(positions.get(i), shapes[i]);
        }
        positions.clear();

    }

    @Override
    public void execute() throws PaintException {
        place.shapes.add(shape);
        for (ShapeModel aShape : shapes) {
            positions.add(place.shapes.indexOf(aShape));
            place.shapes.remove(aShape);
        }
        ((ShapeBlockModel) this.shape).group(shapes);
    }

}
