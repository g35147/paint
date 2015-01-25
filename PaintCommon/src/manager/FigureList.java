package manager;

import exception.PaintException;

/**
 *
 * @author MOHSINE
 */
public class FigureList extends Action {

    public String figurename;

    public FigureList() {
        super(null);
        this.currentaction = ActionEnum.FigureList;
    }

    @Override
    public void undo() throws PaintException {
        // Nothing to do
    }

    @Override
    public void execute() throws PaintException {
        // Nothing to do
    }

}
