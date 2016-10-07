package lang.sel.parsers.logic.results;

import lang.sel.core.Keyword;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * @author diegorubin
 */
public class NotCommonResult implements OperationResult {
  @Override
  public void setResult(Object result) {

  }

  @Override
  public Object getResult() {
    return null;
  }

  @Override
  public OperatorArgument toOperatorArgument() {
    return new OperatorArgument() {
      @Override
      public void setContent(Object content) {

      }

      @Override
      public Object getContent() {
        return null;
      }

      @Override
      public Keyword getArgumentType() {
        return null;
      }

    };
  }
}
