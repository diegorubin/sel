package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.IntegerResult;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.AbstractFunction;

/**
 * @author diegorubin
 */
@Function("TF")
public class TestFunction extends AbstractFunction {
    @Override
    public TypedResult execute(TypedResult... args) {
        return new IntegerResult(1l);
    }
}
