package dto;

import java.util.ArrayList;
import java.util.Collection;
import paintcommon.ShapeEnum;

/**
 *
 * @author supeUse
 */
public class ShapeDto {

    private Long id;
    private ShapeEnum shapetype;
    private String strokecolor;
    private String fillcolor;
    private Double strokeWidth;
    private Long figure;
    private Long shape;
    private Double radius;
    private Collection<ShapeDto> shapeList;
    private Collection<PointDto> pointList;

    public ShapeDto(Long id, ShapeEnum shapetype, String strokecolor,
            String fillcolor, Long figure, Long shape, Double radius, Double strokeWidth) {
        this.id = id;
        this.shapetype = shapetype;
        this.strokecolor = strokecolor;
        this.fillcolor = fillcolor;
        this.figure = figure;
        this.shape = shape;
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        shapeList = new ArrayList<>();
        pointList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public ShapeEnum getShapetype() {
        return shapetype;
    }

    public String getStrokecolor() {
        return strokecolor;
    }

    public String getFillcolor() {
        return fillcolor;
    }

    public Long getFigure() {
        return figure;
    }

    public Long getShape() {
        return shape;
    }

    public Double getRadius() {
        return radius;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setShapetype(ShapeEnum shapetype) {
        this.shapetype = shapetype;
    }

    public void setStrokecolor(String strokecolor) {
        this.strokecolor = strokecolor;
    }

    public void setFillcolor(String fillcolor) {
        this.fillcolor = fillcolor;
    }

    public void setFigure(Long figure) {
        this.figure = figure;
    }

    public void setShape(Long shape) {
        this.shape = shape;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Collection<ShapeDto> getShapeList() {
        return shapeList;
    }

    public void setShapeList(Collection<ShapeDto> ShapeList) {
        this.shapeList = ShapeList;
    }

    /**
     * @return the pointList
     */
    public Collection<PointDto> getPointList() {
        return pointList;
    }

    /**
     * @param pointList the pointList to set
     */
    public void setPointList(Collection<PointDto> pointList) {
        this.pointList = pointList;
    }

    /**
     * @return the strokeWidth
     */
    public Double getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * @param strokeWidth the strokeWidth to set
     */
    public void setStrokeWidth(Double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
    
}
