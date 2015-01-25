package paintcommon;

import exception.PaintException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author G38772
 */
public class PointModel implements Serializable {

    public static long nextId = 1;

    private long id;

    private double x;
    private double y;

    public PointModel tiedPoint;
    private double xGap;
    private double yGap;

    private List<PointModel> referencedByPoints;

    public List<PointModel> getReferencedByPoints() {
        return referencedByPoints;
    }

    /**
     * crée un point non lié
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public PointModel(double x, double y) {
        this.id = nextId++;
        this.x = x;
        this.y = y;
        this.xGap = 0;
        this.yGap = 0;
        this.tiedPoint = null;
        this.referencedByPoints = new ArrayList<>();
    }

    /**
     * crée un point lié
     *
     * @param x abscisse
     * @param y ordonnée
     * @param tiedPoint point auquel le point courant se liera.
     */
    public PointModel(double x, double y, PointModel tiedPoint) throws PaintException {
        this(x, y);
        this.tie(tiedPoint);
    }

    /**
     * lie le point courant
     *
     * @param tiedPoint point avec lequel le point courant va se lier
     */
    public final void tie(PointModel tiedPoint) throws PaintException {
        if (tiedPoint.id == this.id) {
            throw new PaintException("un point ne peut pas être lié à lui même");
        }
        if (isDeeplyReferencedBy(tiedPoint)) {
            throw new PaintException("le point passé en paramètre est lié au point courant,"
                    + " directement ou de manière recursive");
        }
        untie();
        this.xGap = tiedPoint.x - this.x;
        this.yGap = tiedPoint.y - this.y;
        this.tiedPoint = tiedPoint;
        if (!isInReferecendByPoints(tiedPoint)) {
            tiedPoint.referencedByPoints.add(this);
        }
    }

    @Override
    public String toString() {
        return "(" + id + ", (" + x + ", " + y + ")";
    }

    private boolean isInReferecendByPoints(PointModel point) {
        boolean isIn = false;
        int i = 0;

        while (!isIn && i < point.referencedByPoints.size()) {
            if (point.referencedByPoints.get(i).id == this.id) {
                isIn = true;
            }
            i++;
        }
        return isIn;
    }

    /**
     * délie le point courant du point avec lequel il s'est lié.
     *
     * @return vrai en cas de réussite
     */
    public boolean untie() {
        boolean isDone = false;
        if (this.tiedPoint != null) {
            this.tiedPoint.referencedByPoints.remove(this);
            this.tiedPoint = null;
            this.xGap = 0;
            this.yGap = 0;
            isDone = true;
        }
        return isDone;
    }

    /**
     * change l'abscisse du point courant et de tout les points qui se sont lié
     * au point courant, et ce de manière récursive.
     *
     * @param x nouvelle abscisse
     */
    public void setTiedX(double x) {
        setRecursiveTiedX(x, this);
    }

    private void setRecursiveTiedX(double x, PointModel p) {
        p.x = x - p.xGap;
        for (PointModel referenced : p.referencedByPoints) {
            setRecursiveTiedX(p.x, referenced);
        }
    }

    /**
     * change l'ordonnée du point courant et de tout les points qui se sont lié
     * au point courant, et ce de manière récursive.
     *
     * @param y ordonnée
     */
    public void setTiedY(double y) {
        setRecursiveTiedY(y, this);
    }

    private void setRecursiveTiedY(double y, PointModel p) {
        p.y = y - p.yGap;
        for (PointModel referencedByPoint : p.referencedByPoints) {
            setRecursiveTiedY(p.y, referencedByPoint);
        }
    }

    /**
     * change seulement l'abscisse du point courant. Et met à jour l'écart en
     * abscisse (xGap) des point qui se sont lié au point courant.
     *
     * @param x abscisse
     * @see getXGap()
     */
    public void setX(double x) {
        this.x = x;
        for (PointModel referencedByPoint : referencedByPoints) {
            referencedByPoint.xGap = this.x - referencedByPoint.x;
        }
    }

    /**
     * change seulement l'ordonnée du point courant. Et met à jour l'écart en
     * ordonnée (yGap) des point qui se sont lié au point courant.
     *
     * @param y ordonnée
     * @see getYGap()
     */
    public void setY(double y) {
        this.y = y;
        for (PointModel referencedByPoint : referencedByPoints) {
            referencedByPoint.yGap = this.y - referencedByPoint.y;
        }
    }

    /**
     * donne l'abscisse du point courant
     *
     * @return abscisse
     */
    public double getX() {
        return x;
    }

    /**
     * donne l'ordonnée du point courant
     *
     * @return ordonnée
     */
    public double getY() {
        return y;
    }

    /**
     * donne l'écart, en abscisse, entre le point courant et le point auquel il
     * s'est lié
     *
     * @return l'écart en abscisse
     */
    public double getXGap() {
        return xGap;
    }

    /**
     * donne l'écart, en ordonnée, entre le point courant et le point auquel il
     * s'est lié
     *
     * @return l'écart en ordonnée
     */
    public double getYGap() {
        return yGap;
    }

    private boolean isDeeplyReferencedBy(PointModel thePoint) {
        return isDeeplyReferencedByRecursive(this.referencedByPoints, thePoint);
    }

    private boolean isDeeplyReferencedByRecursive(List<PointModel> list, PointModel thePoint) {
        PointModel current;
        boolean isDeeplyReferencedBy = false;
        int i = 0;
        while (!isDeeplyReferencedBy && i < list.size()) {
            current = list.get(i);
            if (current.id == thePoint.id) {
                isDeeplyReferencedBy = true;
            } else {
                isDeeplyReferencedBy = isDeeplyReferencedByRecursive(current.referencedByPoints, thePoint);
            }
            i++;
        }
        return isDeeplyReferencedBy;
    }

    /**
     * déplace le point courant et tout les point qui se sont associé à lui, et
     * ce de manière récursive.
     *
     * @param x abscisse
     * @param y ordonnée
     * @see setTiedX()
     * @see setTiedY()
     */
    public void move(double x, double y) {
        this.setTiedX(x);
        this.setTiedY(y);
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
