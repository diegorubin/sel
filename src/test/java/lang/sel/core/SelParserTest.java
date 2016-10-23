package lang.sel.core;

import lang.sel.commons.constants.logic.FalseConstant;
import lang.sel.commons.constants.logic.TrueConstant;
import lang.sel.commons.operators.math.PlusOperator;
import lang.sel.core.functions.AnswerFunction;
import lang.sel.core.functions.EqualsFunction;
import lang.sel.core.functions.SumFunction;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.SelSemanticException;
import lang.sel.interfaces.ExecutionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author diegorubin
 */
@RunWith(MockitoJUnitRunner.class)
public class SelParserTest {

  private SelContext selContext;

  @Before
  public void setup() {
    selContext = new SelContext();
    selContext.addConstant("TRUE", TrueConstant.class);
    selContext.addConstant("FALSE", FalseConstant.class);
    selContext.addFunction("equals", EqualsFunction.class, new FunctionOptions());
    selContext.addFunction("sum", SumFunction.class, new FunctionOptions());
    selContext.addBinaryOperator("plus", PlusOperator.class);
  }

  @Test
  public void shouldRecoverTwoArguments() {
    SelParser parser = new SelParser("equals('1','3')", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());

    parser = new SelParser("equals('1','1')", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldUseFloat() {
    SelParser parser = new SelParser("equals(sum(1.0, 1), '2.0')", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldSumIntegers() {
    SelParser parser = new SelParser("1 plus 2 plus 3", selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(6), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnValueOfFunctionWithoutArgs() {
    FunctionOptions options = new FunctionOptions();
    options.setNumberOfArguments(0);

    selContext.addFunction("answer", AnswerFunction.class, options);

    SelParser parser = new SelParser("answer()", selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(42), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldExecuteTwoExpressionAndReturnSecondValue() {
    String block = "1 plus 1;\n" +
        "1 plus 2";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(3), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldAssignResultInToVariable() {
    String block = "var = 1 plus 1;\n" +
        "1 plus 2;\n" +
        "var";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldExecuteTwoExpressionSeparatedBySemicolonAndReturnSecondValue() {
    String block = "1 plus 1;1 plus 2";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(3), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnNullIfConditionsIsFalse() {
    String block = "IF(FALSE) 1 plus 1 END";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertNull(parser.evaluate());
  }

  @Test
  public void shouldReturnExpressionAfterIfWhenTrue() {
    String block = "IF(TRUE) 1 ELSE 2 END";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(1), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionsAfterIfWhenTrue() {
    String block = "IF(TRUE) 1; 3; 70 plus 10 ELSE 2 END";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(80), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionAfterElseWhenFalse() {
    String block = "IF(FALSE) 1 ELSE 2; 4 END";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(4), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionAfterIfElse() {
    String block = "IF(FALSE) 1 ELSE 2; 4 END; 5";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(5), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionAfterMultilineIfElse() {
    String block = "IF(FALSE)\n" +
        "  1\n" +
        "ELSE\n" +
        "  2;\n" +
        "  4\n" +
        "END;\n" +
        "5";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(5), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldTerminateScriptAndReturnResult() {
    String block = "IF(TRUE)\n" +
        "  2;\n" +
        "  RETURN 1\n" +
        "ELSE\n" +
        "  4\n" +
        "END;\n" +
        "5";
    SelParser parser = new SelParser(block, selContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(1), (Long) parser.evaluate().getResult());
  }

  @Test(expected = SelSemanticException.class)
  public void shouldThrowErrorIfNumberOfArgumentsIsWrong() {
    FunctionOptions options = new FunctionOptions();
    options.setNumberOfArguments(2);

    selContext.addFunction("sum", SumFunction.class, options);
    SelParser parser = new SelParser("sum(1, 2, 3)", selContext, new ExecutionData() {
    });

    parser.evaluate();
  }

}