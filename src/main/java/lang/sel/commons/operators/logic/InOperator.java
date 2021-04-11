package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.BinaryOperator;

/**
 * In Operator
 *
 * @author diegorubin
 */
@Operator("IN")
public class InOperator extends OverloadOperator implements BinaryOperator {

    @Override
    public TypedResult execute(TypedResult arg1, TypedResult arg2) {
        return execute(arg1, arg2, "operatorIn");
    }
}
