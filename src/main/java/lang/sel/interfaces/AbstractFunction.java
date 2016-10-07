package lang.sel.interfaces;

import lang.sel.core.EngineContext;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.ExpressionSemanticException;

/**
 * Function interface
 * <p>
 * Represents a function. All functions should be extends this class.
 *
 * @author diegorubin
 */
public abstract class AbstractFunction {

  protected EngineContext engineContext;
  protected ExecutionData executionData;
  protected String functionName;

  /**
   * The default constructor
   * <p>
   * This constructor exists because it facilitates the time to instantiate the function within the parser.
   * <p>
   * This constructor is called through reflection.
   */
  public AbstractFunction() {
  }

  /**
   * Set data to execute a function
   *
   * @param functionName  the function name used on expression
   * @param engineContext the engine context singleton
   * @param executionData the data for current execution
   */
  public void setContext(final String functionName, final EngineContext engineContext, final ExecutionData executionData) {
    this.functionName = functionName;
    this.engineContext = engineContext;
    this.executionData = executionData;
  }

  /**
   * The implementation of the function logic
   *
   * @param args list of arguments used on function
   *
   * @return the operation result
   */
  public abstract OperationResult execute(OperatorArgument... args);

  public void validateFunctionOptions(final OperatorArgument... args) {
    final FunctionOptions options = engineContext.getFunctionOptions(functionName);
    if (options.getNumberOfArguments().isPresent() && (args.length != options.getNumberOfArguments().get())) {
      throw new ExpressionSemanticException(functionName + " expect " + options.getNumberOfArguments().get() +
          " arguments but received " + args.length + " arguments!");
    }
  }

}

