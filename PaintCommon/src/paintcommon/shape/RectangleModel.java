package paintcommon.shape;

import exception.PaintException;
import paintcommon.PointModel;
import java.util.ArrayList;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author G38772
 */
public class RectangleModel extends ShapeModel {

    private PointModel upperLeftCorner;
    private PointModel upperRightCorner;
    private PointModel lowerLeftCorner;
    private PointModel lowerRightCorner;

    private double width;
    private double height;

    public RectangleModel(PointModel upperLeftCorner, double width, double height) throws PaintException {
        this.height = height;
        this.width = width;
        this.upperLeftCorner = upperLeftCorner;
        upperRightCorner = new PointModel(upperLeftCorner.getX() + width, upperLeftCorner.getY());
        lowerLeftCorner = new PointModel(upperLeftCorner.getX(), upperLeftCorner.getY() + height);
        lowerRightCorner = new PointModel(upperLeftCorner.getX() + width, upperLeftCorner.getY() + height);
        List<PointModel> points = new ArrayList<>();
        points.add(this.upperLeftCorner);
        points.add(upperRightCorner);
        points.add(lowerRightCorner);
        points.add(lowerLeftCorner);
        super.initSimpleShape(points.toArray(new PointModel[0]));
        setType(ShapeEnum.Rectangle);
    }

    public RectangleModel(PointModel upperLeftCorner, PointModel upperRightCorner,
            PointModel lowerRightCorner, PointModel lowerLeftCorner, String stroke,
            String fill, double strokeWith) throws PaintException {
        this(upperLeftCorner, upperRightCorner.getX() - upperLeftCorner.getX(),
                lowerLeftCorner.getY() - upperRightCorner.getY());
        this.checkIfRectangle(upperLeftCorner, upperRightCorner, lowerRightCorner, lowerLeftCorner);
        this.setFillColor(fill);
        this.setStrokeColor(stroke);
        this.setStrokeWidth(strokeWith);
    }

    public RectangleModel(PointModel upperLeftCorner, PointModel upperRightCorner,
            PointModel lowerRightCorner, PointModel lowerLeftCorner) throws PaintException {
        this(upperLeftCorner, upperRightCorner.getX() - upperLeftCorner.getX(),
                lowerLeftCorner.getY() - upperRightCorner.getY());
        this.checkIfRectangle(upperLeftCorner, upperRightCorner, lowerRightCorner, lowerLeftCorner);
    }

    private void checkIfRectangle(PointModel... points) throws PaintException {
        if (points.length != 4) {
            throw new PaintException("un rectangle a 4 cotés");
        }

        if (points[0].getY() != points[1].getY()
                || points[1].getX() != points[2].getX()
                || points[2].getY() != points[3].getY()
                || points[3].getX() != points[0].getX()) {
            throw new PaintException("un RectangleModel a ses côtés "
                    + "parrallèle aux abscisse et aux ordonnée utilisez PolygoneModel"
                    + points[0] + " " + points[1] + " " + points[2] + " " + points[3] + " ");
        }
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
