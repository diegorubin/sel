package lang.sel.core;

import lang.sel.commons.constants.logic.FalseConstant;
import lang.sel.commons.constants.logic.TrueConstant;
import lang.sel.commons.operators.math.PlusOperator;
import lang.sel.core.functions.AnswerFunction;
import lang.sel.core.functions.EqualsFunction;
import lang.sel.core.functions.SumFunction;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.ExpressionSemanticException;
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
public class ExpressionParserTest {

  private EngineContext engineContext;

  @Before
  public void setup() {
    engineContext = new EngineContext();
    engineContext.addConstant("TRUE", TrueConstant.class);
    engineContext.addConstant("FALSE", FalseConstant.class);
    engineContext.addFunction("equals", EqualsFunction.class, new FunctionOptions());
    engineContext.addFunction("sum", SumFunction.class, new FunctionOptions());
    engineContext.addBinaryOperator("plus", PlusOperator.class);
  }

  @Test
  public void shouldRecoverTwoArguments() {
    ExpressionParser parser = new ExpressionParser("equals('1','3')", engineContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());

    parser = new ExpressionParser("equals('1','1')", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldUseFloat() {
    ExpressionParser parser = new ExpressionParser("equals(sum(1.0, 1), '2.0')", engineContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldSumIntegers() {
    ExpressionParser parser = new ExpressionParser("1 plus 2 plus 3", engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(6), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnValueOfFunctionWithoutArgs() {
    FunctionOptions options = new FunctionOptions();
    options.setNumberOfArguments(0);

    engineContext.addFunction("answer", AnswerFunction.class, options);

    ExpressionParser parser = new ExpressionParser("answer()", engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(42), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldExecuteTwoExpressionAndReturnSecondValue() {
    String block = "1 plus 1;\n" +
        "1 plus 2";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(3), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldAssignResultInToVariable() {
    String block = "var = 1 plus 1;\n" +
        "1 plus 2;\n" +
        "var";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldExecuteTwoExpressionSeparatedBySemicolonAndReturnSecondValue() {
    String block = "1 plus 1;1 plus 2";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(3), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnNullIfConditionsIsFalse() {
    String block = "IF(FALSE) 1 plus 1 END";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertNull(parser.evaluate());
  }

  @Test
  public void shouldReturnExpressionAfterIfWhenTrue() {
    String block = "IF(TRUE) 1 ELSE 2 END";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(1), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionsAfterIfWhenTrue() {
    String block = "IF(TRUE) 1; 3; 70 plus 10 ELSE 2 END";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(80), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionAfterElseWhenFalse() {
    String block = "IF(FALSE) 1 ELSE 2; 4 END";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(4), (Long) parser.evaluate().getResult());
  }

  @Test
  public void shouldReturnExpressionAfterIfElse() {
    String block = "IF(FALSE) 1 ELSE 2; 4 END; 5";
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
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
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
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
    ExpressionParser parser = new ExpressionParser(block, engineContext, new ExecutionData() {
    });
    Assert.assertEquals(Long.valueOf(1), (Long) parser.evaluate().getResult());
  }

  @Test(expected = ExpressionSemanticException.class)
  public void shouldThrowErrorIfNumberOfArgumentsIsWrong() {
    FunctionOptions options = new FunctionOptions();
    options.setNumberOfArguments(2);

    engineContext.addFunction("sum", SumFunction.class, options);
    ExpressionParser parser = new ExpressionParser("sum(1, 2, 3)", engineContext, new ExecutionData() {
    });

    parser.evaluate();
  }

}
