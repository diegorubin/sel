package lang.sel.commons.operators.math;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.BinaryOperator;

/**
 * Performs addition of two numbers
 * <p>
 * Used with number PLUS number
 *
 * @author diegorubin
 */
@Operator("PLUS")
public class PlusOperator extends OverloadOperator implements BinaryOperator {

    @Override
    public TypedResult execute(final TypedResult arg1, final TypedResult arg2) {
        return execute(arg1, arg2, "operatorPlus");
    }
}
