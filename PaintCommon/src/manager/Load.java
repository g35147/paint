package manager;

import exception.PaintException;

/**
 *
 * @author supeUse
 */
public class Load extends Action {

    public String figurename;

    public Load(String figurename) {
        super(null);
        setCurrentaction(ActionEnum.Load);
        this.figurename = figurename;
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
