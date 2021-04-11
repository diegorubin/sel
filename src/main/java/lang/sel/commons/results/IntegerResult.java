package lang.sel.commons.results;

import lang.sel.core.Keyword;

/**
 * Integer Result
 *
 * @author diegorubin
 */
public class IntegerResult extends TypedResult<Long> {

    /**
     * Create a operation result with integer as content
     *
     * @param value the integer result
     */
    public IntegerResult(Long value) {
        super(value);
    }
}
