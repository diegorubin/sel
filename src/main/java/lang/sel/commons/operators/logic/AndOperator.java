package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.BinaryOperator;

/**
 * AND Operator
 * <p>
 * Usage BooleanOperator AND BooleanOperator
 *
 * @author diegorubin
 */
@Operator("AND")
public class AndOperator extends OverloadOperator implements BinaryOperator {

    @Override
    public TypedResult execute(final TypedResult arg1, final TypedResult arg2) {
        return execute(arg1, arg2, "operatorAnd");
    }
}
