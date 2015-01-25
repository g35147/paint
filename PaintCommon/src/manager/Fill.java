package manager;

import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public class Fill extends Action {

    private String oldColor;
    private String newColor;

    public Fill(ShapeModel shape, String oldColor, String newColor) {
        super(shape);
        this.currentaction = ActionEnum.Fill;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void undo() {
        shape.setFillColor(oldColor);
    }

    @Override
    public void execute() {
        shape.setFillColor(newColor);
    }

}
