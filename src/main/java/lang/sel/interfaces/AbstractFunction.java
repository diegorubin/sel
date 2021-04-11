package lang.sel.interfaces;

import lang.sel.commons.results.TypedResult;
import lang.sel.core.SelContext;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.SelSemanticException;

/**
 * Function interface
 * <p>
 * Represents a function. All functions should be extends this class.
 *
 * @author diegorubin
 */
public abstract class AbstractFunction {

    protected SelContext selContext;
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
     * @param selContext the engine context singleton
     * @param executionData the data for current execution
     */
    public void setContext(final String functionName, final SelContext selContext, final ExecutionData executionData) {
        this.functionName = functionName;
        this.selContext = selContext;
        this.executionData = executionData;
    }

    /**
     * The implementation of the function logic
     *
     * @param args list of arguments used on function
     *
     * @return the operation result
     */
    public abstract TypedResult execute(TypedResult... args);

    public void validateFunctionOptions(final TypedResult... args) {
        final FunctionOptions options = selContext.getFunctionOptions(functionName);
        if (options.getNumberOfArguments().isPresent() && (args.length != options.getNumberOfArguments().get())) {
            throw new SelSemanticException(functionName + " expect " + options.getNumberOfArguments().get() +
                                           " arguments but received " + args.length + " arguments!");
        }
    }

}
