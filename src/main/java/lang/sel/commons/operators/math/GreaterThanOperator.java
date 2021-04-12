package lang.sel.commons.operators.math;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.BinaryOperator;

/**
 * Greater Than Operator
 *
 * @author diegorubin
 */
@Operator("GT")
public class GreaterThanOperator extends OverloadOperator implements BinaryOperator {

    /**
     * Implements the greater than operator (&gt;)
     *
     * @param arg1 the argument in double value
     * @param arg2 the argument in double value
     *
     * @return the operation result as boolean
     */
    @Override
    public TypedResult execute(final TypedResult arg1, final TypedResult arg2) {
        return execute(arg1, arg2, "operatorGt");
    }
}
