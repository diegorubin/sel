package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.FloatResult;
import lang.sel.commons.results.StringResult;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.AbstractFunction;

/**
 * @author diegorubin
 */
@Function("sum")
public class SumFunction extends AbstractFunction {
    @Override
    public TypedResult execute(TypedResult... args) {
        Double dbl1 = getContent(args[0]);
        Double dbl2 = getContent(args[1]);
        Double result = dbl1 + dbl2;
        return new StringResult(result.toString());
    }

    public Double getContent(TypedResult argument) {
        if (argument.getType().equals(FloatResult.class.getSimpleName())) {
            return (Double) argument.getResult();
        }
        return new Double(((Long) argument.getResult()));
    }
}
