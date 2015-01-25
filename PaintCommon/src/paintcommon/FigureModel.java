package paintcommon;

import paintcommon.shape.ShapeModel;
import exception.PaintException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author G38772
 */
public class FigureModel implements Serializable {
    
    private List<String> figureNameList;
    
    public static long nextId = 1;
    public long id;
    public String name;
    public List<ShapeModel> shapes;

    public FigureModel() {
        this.shapes = new ArrayList<>();
    }
    
    public void moveShape(ShapeModel shape) {
        boolean found = false;
        int i = 0;
        while (!found && i < shapes.size()) {
            if (shape.getId() == shapes.get(i).getId()) {
                found = true;
                shapes.get(i).move(shape.getX(), shape.getY());
            }
            i++;
        }
    }

    public void addShape(ShapeModel shape) {
        this.shapes.add(shape);
    }
    
    public ShapeModel getShape(long id) throws PaintException {
        boolean found = false;
        int i = 0;
        while (!found && i < shapes.size()) {
            if (shapes.get(i).getId() == id) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            throw new PaintException("aucune forme n'a cet id");
        }
        return shapes.get(i);
    }
    
    public List<String> getFigureNameList() {
        return figureNameList;
    }

    public void setFigureNameList(List<String> figureNameList) {
        this.figureNameList = figureNameList;
    }
    
}
