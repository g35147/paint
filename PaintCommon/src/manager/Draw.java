package manager;

import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class Draw extends Action {

    public Draw(ShapeModel shape) {
        super(shape);
        this.currentaction = ActionEnum.Draw;
    }

    @Override
    public void undo() {
        place.shapes.remove(shape);
    }

    @Override
    public void execute() {
        place.shapes.add(shape);
    }
    
}
