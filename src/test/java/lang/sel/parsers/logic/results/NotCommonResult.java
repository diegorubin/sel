package lang.sel.parsers.logic.results;

import java.math.BigDecimal;

import lang.sel.commons.results.TypedResult;

/**
 * @author diegorubin
 */
public class NotCommonResult extends TypedResult<BigDecimal> {

    public NotCommonResult(BigDecimal value) {
        super(value);
    }

    @Override
    public String getType() {
        return "NotCommonResult";
    }
}
