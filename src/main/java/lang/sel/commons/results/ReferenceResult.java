package lang.sel.commons.results;

/**
 * Reference Result
 *
 * @author diegorubin
 */
public class ReferenceResult extends TypedResult<String> {

    /**
     * Create a result used to references a variable or constant.
     *
     * @param value the reference result
     */
    public ReferenceResult(String value) {
        super(value);
    }

}
