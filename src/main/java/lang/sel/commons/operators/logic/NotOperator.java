package lang.sel.commons.operators.logic;

import lang.sel.annotations.Operator;
import lang.sel.commons.operators.OverloadOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.UnaryOperator;

/**
 * Not operator
 *
 * @author diegorubin
 */
@Operator("NOT")
public class NotOperator extends OverloadOperator implements UnaryOperator {
    @Override
    public TypedResult execute(final TypedResult argument) {
        return execute(argument, "operatorNot");
    }
}
