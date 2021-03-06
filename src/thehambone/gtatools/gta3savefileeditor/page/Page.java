package thehambone.gtatools.gta3savefileeditor.page;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import thehambone.gtatools.gta3savefileeditor.savefile.var.component.VariableComponent;
import thehambone.gtatools.gta3savefileeditor.Observable;
import thehambone.gtatools.gta3savefileeditor.Observer;
import thehambone.gtatools.gta3savefileeditor.savefile.var.Variable;
import thehambone.gtatools.gta3savefileeditor.util.Logger;

/**
 * Created on Mar 7, 2015.
 * 
 * @author thehambone
 */
public abstract class Page extends JPanel implements Observable
{
    private final List<Observer> observers;
    private final String pageTitle;
    private final Visibility visibility;
    
    /**
     * Creates a new {@code Page} object.
     * 
     * @param pageTitle the name of the page
     * @param visibility the page visibility type
     */
    public Page(String pageTitle, Visibility visibility)
    {
        super();
        observers = new ArrayList<>();
        this.pageTitle = pageTitle;
        this.visibility = visibility;
    }
    
    /**
     * Returns the name of this page.
     * 
     * @return the page title
     */
    public final String getPageTitle()
    {
        return pageTitle;
    }
    
    /**
     * Gets the page visibility type.
     * 
     * @return the page visibility
     * @see Visibility
     */
    public final Visibility getVisibility()
    {
        return visibility;
    }
    
    /**
     * Notifies all observers that a variable has been updated.
     * 
     * @param var the updated variable
     */
    protected void notifyVariableChange(Variable var)
    {
        Logger.debug("Variable updated: " + var);
        notifyObservers(Event.VARIABLE_CHANGED);
    }
    
    /**
     * Adds an observer to all {@code VariableComponents} in a given
     * {@code Container}.
     * 
     * @param root a {@code Container} containing {@code VariableComponents}
     */
    private void addObserverToVariableComponents(Container root, Observer o)
    {
        for (Component c : root.getComponents()) {
            if (c instanceof VariableComponent) {
                VariableComponent vc = (VariableComponent)c;
                vc.addObserver(o);
            } else if (c instanceof Container) {
                addObserverToVariableComponents((Container)c, o);   // Recurse
            }
        }
    }
    
    /**
     * Associates variables with {@code VariableComponents} and updates the
     * components' states so they reflect the current values of variables.
     */
    public abstract void loadPage();
    
    @Override
    public void addObserver(Observer o)
    {
        if (!observers.contains(o)) {
            observers.add(o);
            addObserverToVariableComponents(this, o);
        }
    }
    
    @Override
    public void removeObserver(Observer o)
    {
        observers.remove(o);
    }
    
    @Override
    public void notifyObservers(Object message, Object... args)
    {
        for (Observer o : observers) {
            o.update(message, args);
        }
    }
    
    /**
     * Constants defining Page visibility types.
     */
    public static enum Visibility
    {
        /**
         * The Page is always visible.
         */
        ALWAYS_VISIBLE,
        
        /**
         * The Page is only visible when a save file is loaded.
         */
        VISIBLE_WHEN_FILE_LOADED_ONLY,
        
        /**
         * The Page is only visible when a save file is not loaded.
         */
        VISIBLE_WHEN_FILE_NOT_LOADED_ONLY;
    }
    
    /**
     * Constants defining page events.
     */
    public static enum Event
    {
        /**
         * Signifies that a variable's value has been changed.
         */
        VARIABLE_CHANGED,
        
        /**
         * Signals that a file should be loaded.
         */
        FILE_LOAD,
        
        /**
         * Signals that a file should be deleted.
         */
        FILE_DELETE,
        
        /**
         * Signals that the save slots should be refreshed.
         */
        REFRESH_SLOTS;
    }
}
