package paintcommon.shape;

import exception.PaintException;
import java.util.List;
import paintcommon.PointModel;
import paintcommon.ShapeEnum;

/**
 *
 * @author supeUse
 */
public class PolygonModel extends ShapeModel {

    public PolygonModel(PointModel... points) throws PaintException {
        super(points);
        super.setType(ShapeEnum.Polygon);
    }

    public PolygonModel(List<PointModel> points) throws PaintException {
        super(points);
        super.setType(ShapeEnum.Polygon);
    }
    
}
