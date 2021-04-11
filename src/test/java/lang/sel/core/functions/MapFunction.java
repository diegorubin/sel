package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.results.ArrayResult;
import lang.sel.commons.results.IntegerResult;
import lang.sel.commons.results.TypedResult;
import lang.sel.interfaces.AbstractFunction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Map Function
 *
 * @author diegorubin
 */
@Function("map")
public class MapFunction extends AbstractFunction {
    @Override
    public TypedResult execute(TypedResult... args) {
        ArrayResult array = (ArrayResult) args[0];
        List<Object> resultArray = ((List<Object>) array.getResult())
                                   .stream()
        .map(o -> {
            Long result = Long.valueOf(((Long)((TypedResult) o).getResult()) * 2);
            return new IntegerResult(result);
        })
        .collect(Collectors.toList());

        return new ArrayResult(resultArray);
    }
}
