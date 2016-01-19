
package thehambone.gtatools.gta3savefileeditor.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JCheckBox;
import thehambone.gtatools.gta3savefileeditor.gui.observe.Observer;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.Variable;

/**
 * Created on Jan 17, 2016.
 *
 * @author thehambone
 * @param <T>
 */
public abstract class VariableCheckBox<T extends Variable>
        extends JCheckBox implements VariableComponent<T>
{
    private final List<Observer> observers;
    private final List<T> supplementaryVars;
    
    private T var;
    private boolean doUpdateOnChange;
    
    protected VariableCheckBox(T var, T... supplementaryVars)
    {
        super();
        observers = new ArrayList<>();
        this.var = var;
        this.supplementaryVars
                = new ArrayList<>(Arrays.asList(supplementaryVars));
        
        initActionListener();
    }
    
    private void initActionListener()
    {
        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (doUpdateOnChange) {
                    updateVariable();
                }
            }
        });
    }
    
    @Override
    public boolean hasVariable()
    {
        return var != null;
    }
    
    @Override
    public T getVariable()
    {
        return var;
    }
    
    @Override
    public void setVariable(T var, T... supplementaryVars)
    {
        this.var = var;
        
        this.supplementaryVars.clear();
        this.supplementaryVars.addAll(Arrays.asList(supplementaryVars));
        
        refreshComponent();
    }
    
    @Override
    public List<T> getSupplementaryVariables()
    {
        return Collections.unmodifiableList(supplementaryVars);
    }
    
    @Override
    public void updateVariableOnChange(boolean doUpdate)
    {
        doUpdateOnChange = doUpdate;
    }
    
    @Override
    public void addObserver(Observer o)
    {
        if (!observers.contains(o)) {
            observers.add(o);
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
}
