package lang.sel.parsers.logic.functions;

import java.math.BigDecimal;

import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.parsers.logic.results.NotCommonResult;

/**
 * Used to test when operator argument not has respondTo for AND operator
 * @author diegorubin
 */
public class ReturnNotCommonResultFunction extends AbstractFunction {
    @Override
    public TypedResult execute(TypedResult... args) {
        return new NotCommonResult(BigDecimal.ONE);
    }
}
