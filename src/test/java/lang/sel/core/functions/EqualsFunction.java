package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.BooleanResult;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.AbstractFunction;

/**
 * @author diegorubin
 */
@Function("equals")
public class EqualsFunction extends AbstractFunction {
    @Override
    public TypedResult execute(TypedResult... args) {

        return new BooleanResult(((String) args[0].getResult()).equals((String) args[1].getResult()));
    }
}
