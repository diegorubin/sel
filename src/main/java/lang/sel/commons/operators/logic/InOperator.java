package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.interfaces.BinaryOperator;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * In Operator
 *
 * @author diegorubin
 */
@Operator("IN")
public class InOperator extends OverloadOperator implements BinaryOperator {

    @Override
    public OperationResult execute(OperatorArgument arg1, OperatorArgument arg2) {
        return execute(arg1, arg2, "operatorIn");
    }
}
