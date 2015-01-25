package paintcommon.shape;

import paintcommon.PointModel;
import exception.PaintException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author G38772
 */
public abstract class ShapeModel implements Serializable {

    public static long nextId = 1;
    public long id;

    private ShapeEnum type;

    private String strokeColor = "#000000";
    private String fillColor = "#000000";
    private double strokeWidth;
    protected PointModel pointDeReference;


    protected ShapeModel() {
        this.id = nextId;
        nextId++;
    }

    public ShapeModel(PointModel... points) throws PaintException {
        initSimpleShape(points);
    }

    public ShapeModel(List<PointModel> points) throws PaintException {
        this(points.toArray(new PointModel[0]));
    }

    protected final void initSimpleShape(PointModel... points) throws PaintException {
        if (points.length <= 0) {
            throw new PaintException("il faut au moins un point");
        }
        pointDeReference = points[0];
        for (int i = 1; i <  points.length; i++) {
            points[i].tie(pointDeReference);
        }
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public ShapeEnum getType() {
        return type;
    }

    protected void setType(ShapeEnum type) {
        this.type = type;
    }

    public void setX(double x) {
        pointDeReference.setTiedX(x);
    }

    public void setY(double y) {
        pointDeReference.setTiedY(y);
    }

    public double getX() {
        return pointDeReference.getX();
    }

    public double getY() {
        return pointDeReference.getY();
    }

    public void move(double x, double y) {
        this.pointDeReference.move(x, y);
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }  

    public List<PointModel> getPoints() {
        List<PointModel> list = new ArrayList<>();
        list.add(pointDeReference);
        for (PointModel point : pointDeReference.getReferencedByPoints()) {
            list.add(point);
        }
        return list;
    }
    
}
