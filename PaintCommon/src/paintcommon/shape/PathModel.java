package paintcommon.shape;

import paintcommon.PointModel;
import exception.PaintException;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author supeUse
 */
public class PathModel extends ShapeModel {
    
    public PathModel(PointModel... points) throws PaintException {
        super(points);
        super.setType(ShapeEnum.FreeHand);
    }
    
    public PathModel(List<PointModel> points) throws PaintException {
        super(points);
        super.setType(ShapeEnum.FreeHand);
    }
    
}
