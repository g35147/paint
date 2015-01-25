package manager;

import exception.PaintException;

/**
 *
 * @author MOHSINE
 */
public class Save extends Action {
    
    public String figurename;

    public Save() {
        this(null);
        this.currentaction = ActionEnum.Save;
    }
    
    public Save(String figurename) {
        super(null);
        setCurrentaction(ActionEnum.Save);
        this.figurename = figurename;
    }

    @Override
    public void execute() {
        // Nothing to do
    }

    @Override
    public void undo() throws PaintException {
        // Nothing to do
    }

}
