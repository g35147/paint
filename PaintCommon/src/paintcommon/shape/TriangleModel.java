package paintcommon.shape;

import exception.PaintException;
import java.util.ArrayList;
import java.util.List;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;

/**
 *
 * @author MOHSINE
 */
public class TriangleModel extends ShapeModel {

    private PointModel apex;
    private PointModel leftCorner;
    private PointModel rightCorner;

    public TriangleModel(PointModel apex, PointModel rightCorner, PointModel leftCorner) throws PaintException {
        this.apex = apex;
        this.rightCorner = rightCorner;
        this.leftCorner = leftCorner;
        List<PointModel> points = new ArrayList<>();
        points.add(this.apex);
        points.add(this.rightCorner);
        points.add(this.leftCorner);
        super.initSimpleShape(points.toArray(new PointModel[0]));
        setType(ShapeEnum.Triangle);
    }

    public double[] getDoubles() {
        double points[] = new double[6];
        points[0] = super.getPoints().get(0).getX();
        points[1] = super.getPoints().get(0).getY();
        points[2] = super.getPoints().get(1).getX();
        points[3] = super.getPoints().get(1).getY();
        points[4] = super.getPoints().get(2).getX();
        points[5] = super.getPoints().get(2).getY();
        return points;
    }

    public PointModel getApex() {
        return apex;
    }

    public void setApex(PointModel apex) {
        this.apex = apex;
    }

    public PointModel getLeftCorner() {
        return leftCorner;
    }

    public void setLeftCorner(PointModel LeftCorner) {
        this.leftCorner = LeftCorner;
    }

    public PointModel getRightCorner() {
        return rightCorner;
    }

    public void setRightCorner(PointModel RightCorner) {
        this.rightCorner = RightCorner;
    }

}
