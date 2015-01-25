package manager;

/**
 *
 * @author MOHSINE
 */
public class Redo extends Action {

    public Redo() {
        super(null);
        this.currentaction = ActionEnum.Redo;
    }

    @Override
    public void undo() {
        // Nothing to do
    }

    @Override
    public void execute() {
        // Nothing to do
    }

}
