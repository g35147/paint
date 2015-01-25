package manager;

/**
 *
 * @author MOHSINE
 */
public class Undo extends Action {

    public Undo() {
        super(null);
        this.currentaction = ActionEnum.Undo;
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
