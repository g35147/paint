package paintcommon.shape;

import paintcommon.PointModel;
import exception.PaintException;
import paintcommon.ShapeEnum;

/**
 *
 * @author G35147
 */
public class CircleModel extends ShapeModel {

    // taille rayon le point est donné par le ShapeModel
    private double radiusLenght;

    public CircleModel(PointModel centre, double radiuslenght) throws PaintException {
        super(centre);
        this.checkIfCircle(centre);
        super.setType(ShapeEnum.Circle);
        this.radiusLenght = radiuslenght;

    }

    public CircleModel(PointModel centre, double radiuslenght, String stroke,
            String fill, double strokeWith) throws PaintException {
        this(centre, radiuslenght);
        this.setFillColor(fill);
        this.setStrokeColor(stroke);
        this.setStrokeWidth(strokeWith);
    }

    private void checkIfCircle(PointModel... points) throws PaintException {
        if (points.length != 1) {
            throw new PaintException("un cercle a un seul point le centre ");
        }
    }

    public double getRadiuslenght() {
        return radiusLenght;
    }

    public void setRadiuslenght(double rayonlenght) {
        this.radiusLenght = rayonlenght;
    }

}
