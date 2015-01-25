package paintcommon.shape;

import paintcommon.PointModel;
import exception.PaintException;
import java.util.ArrayList;
import java.util.List;
import paintcommon.ShapeEnum;

/**
 *
 * @author G35147
 */
public class LineModel extends ShapeModel {

    PointModel start;
    PointModel end;

    public LineModel(PointModel begin, PointModel end) throws PaintException {
        this.checkIfLigne(begin, end);
        this.start = begin;
        this.end = end;
        List<PointModel> points = new ArrayList<>();
        points.add(this.start);
        points.add(this.end);
        super.initSimpleShape(points.toArray(new PointModel[0]));
        super.setType(ShapeEnum.Line);
    }

    public LineModel(PointModel begin, PointModel end, String stroke,
            String fill, double strokeWith) throws PaintException {
        this(begin, end);
        this.setFillColor(fill);
        this.setStrokeColor(stroke);
        this.setStrokeWidth(strokeWith);
    }

    private void checkIfLigne(PointModel... points) throws PaintException {
        if (points.length != 2) {
            throw new PaintException("une ligne est composée de 2 points  ");
        }
    }

    public PointModel getStart() {
        return start;
    }

    public PointModel getEnd() {
        return end;
    }

    public void setStart(PointModel Start) {
        this.start = Start;
    }

    public void setEnd(PointModel end) {
        this.end = end;
    }

}
