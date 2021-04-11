package lang.sel.commons.results;

import java.util.List;

/**
 * Array Result
 *
 * @author diegorubin
 */
public class ArrayResult extends TypedResult<List<Object>> {

    /**
     * When a typed result is created it is necessary enter the type
     *
     * @param value the content
     */
    public ArrayResult(List<Object> value) {
        super(value);
    }
}
