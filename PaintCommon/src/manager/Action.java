package manager;

import exception.PaintException;
import java.io.Serializable;
import paintcommon.FigureModel;
import paintcommon.shape.ShapeModel;

/**
 *
 * @author supeUse
 */
public abstract class Action implements Serializable {

    protected FigureModel place;
    protected ShapeModel shape;
    protected ActionEnum currentaction;

    public Action(ShapeModel shape) {
        this.place = null;
        this.shape = shape;
    }

    public void setPlace(FigureModel place) {
        this.place = place;
    }

    public void setShape(ShapeModel shape) {
        this.shape = shape;
    }

    public FigureModel getPlace() {
        return place;
    }

    public ShapeModel getShape() {
        return shape;
    }

    public ActionEnum getCurrentaction() {
        return currentaction;
    }

    public void setCurrentaction(ActionEnum currentaction) {
        this.currentaction = currentaction;
    }

    public abstract void undo() throws PaintException;

    public abstract void execute() throws PaintException;
    
}
