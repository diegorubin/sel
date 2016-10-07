package lang.sel.core.functions;

import lang.sel.annotations.Function;
import lang.sel.core.Keyword;
import lang.sel.interfaces.AbstractFunction;
import lang.sel.interfaces.OperationResult;
import lang.sel.interfaces.OperatorArgument;

/**
 * @author diegorubin
 */
@Function("TF")
public class TestFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {
    return new OperationResult() {
      private int value;

      @Override
      public void setResult(Object result) {
        value = 1;
      }

      @Override
      public Object getResult() {
        return value;
      }

      @Override
      public OperatorArgument toOperatorArgument() {
        return new OperatorArgument() {
          private int content;

          @Override
          public void setContent(Object content) {
            this.content = 2;
          }

          @Override
          public Object getContent() {
            return content;
          }

          @Override
          public Keyword getArgumentType() {
            return Keyword.INTEGER;
          }
        };
      }
    };
  }
}
