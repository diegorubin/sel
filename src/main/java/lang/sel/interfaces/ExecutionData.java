package lang.sel.interfaces;

import java.util.HashMap;
import java.util.Map;

import lang.sel.commons.results.TypedResult;

/**
 * Execution data
 * <p>
 * Interface for custom data used on every execution of the parser evaluate.
 *
 * @author diegorubin
 */
public abstract class ExecutionData {

    private final Map<String, TypedResult> variables = new HashMap<>();

    public void assign(final String varName, final TypedResult content) {
        variables.put(varName, content);
    }

    public boolean assigned(final String varName) {
        return variables.containsKey(varName);
    }

    public TypedResult recover(final String varName) {
        return variables.get(varName);
    }

}
