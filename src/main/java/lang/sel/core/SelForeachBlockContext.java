package lang.sel.core;

import lang.sel.commons.results.ArrayResult;
import lang.sel.commons.results.TypedResult;

public class SelForeachBlockContext extends SelBlockContext {

    private Integer loopPosition = 0;
    private String varName;
    private ArrayResult data;

    public SelForeachBlockContext(final Keyword blockType, final Integer position, final String varName, final ArrayResult data) {
        super(blockType, position);
        this.varName = varName;
        this.data = data;
    }

    public Integer getLoopPosition() {
        return loopPosition;
    }

    public String getVarName() {
        return varName;
    }

    public boolean isFinished() {
        return loopPosition >= data.getResult().size();
    }

    public <T> TypedResult<T> getNextElement() {
        return ((TypedResult) data.getResult().get(loopPosition++));
    }

}
