package dto;

/**
 *
 * @author supeUse
 */
public class PointDto {

    private Long id;
    private Double x;
    private Double y;
    private Long listorder;
    private Long shape;

    public PointDto(Long id, Double x, Double y,
            Long listorder, Long shape) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.shape = shape;
        this.listorder = listorder;
    }

    public Long getId() {
        return id;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Long getListorder() {
        return listorder;
    }

    public Long getShape() {
        return shape;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setListorder(Long listorder) {
        this.listorder = listorder;
    }

    public void setShape(Long shape) {
        this.shape = shape;
    }
    
}
