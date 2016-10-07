package lang.sel.interfaces;

import java.util.HashMap;
import java.util.Map;

/**
 * Execution data
 * <p>
 * Interface for custom data used on every execution of the parser evaluate.
 *
 * @author diegorubin
 */
public abstract class ExecutionData {

  private final Map<String, OperationResult> variables = new HashMap<>();

  public void assign(final String var, final OperationResult content) {
    variables.put(var, content);
  }

  public boolean assigned(final String var) {
    return variables.containsKey(var);
  }

  public OperationResult recover(final String var) {
    return variables.get(var);
  }

}
