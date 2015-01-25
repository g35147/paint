package dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author supeUse
 */
public class FigureDto {

    private Long id;
    private String figurename;
    private Collection<ShapeDto> shapes;
    private Date datemaj;

    public FigureDto(Long id, String figurename) {
        this.id = id;
        this.figurename = figurename;
        shapes = new ArrayList<>();
    }

    public FigureDto(Long id, String figurename, Date datemaj) {
        this.id = id;
        this.figurename = figurename;
        this.datemaj = datemaj;
        shapes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getFigurename() {
        return figurename;
    }

    public Collection<ShapeDto> getShapes() {
        return shapes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFigurename(String figurename) {
        this.figurename = figurename;
    }

    public void setShapes(Collection<ShapeDto> Shapes) {
        this.shapes = Shapes;
    }

    public Date getDatemaj() {
        return datemaj;
    }

    public void setDatemaj(Date datemaj) {
        this.datemaj = datemaj;
    }

}
