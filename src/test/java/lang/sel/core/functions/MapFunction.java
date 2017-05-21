package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.commons.arguments.CommonOperatorArgument;
import lang.sel.commons.results.ArrayResult;
import lang.sel.commons.results.IntegerResult;
import lang.sel.core.Keyword;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

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
    public OperationResult execute(OperatorArgument... args) {
        CommonOperatorArgument array = (CommonOperatorArgument) args[0];
        List<Object> resultArray = ((List<Object>) array.getContent())
                .stream()
                .map(o -> {
                    CommonOperatorArgument argument = new CommonOperatorArgument(Keyword.INTEGER);
                    argument.setContent(((Long) ((CommonOperatorArgument) o).getContent()) * 2);
                    return argument;
                })
                .collect(Collectors.toList());

        return new ArrayResult(resultArray);
    }
}
