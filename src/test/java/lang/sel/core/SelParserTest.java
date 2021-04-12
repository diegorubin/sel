package lang.sel.core;

import lang.sel.commons.constants.logic.FalseConstant;
import lang.sel.commons.constants.logic.TrueConstant;
import lang.sel.commons.operators.logic.InOperator;
import lang.sel.commons.operators.logic.NotOperator;
import lang.sel.commons.operators.math.PlusOperator;
import lang.sel.commons.results.TypedResult;
import lang.sel.core.functions.AnswerFunction;
import lang.sel.core.functions.EqualsFunction;
import lang.sel.core.functions.MapFunction;
import lang.sel.core.functions.SumFunction;
import lang.sel.core.wrappers.FunctionOptions;
import lang.sel.exceptions.SelSemanticException;
import lang.sel.interfaces.ExecutionData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

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
        selContext.addFunction("map", MapFunction.class, new FunctionOptions());
        selContext.addBinaryOperator("plus", PlusOperator.class);
        selContext.addBinaryOperator("IN", InOperator.class);
        selContext.addUnaryOperator("NOT", NotOperator.class);
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
    public void shouldSumTwoReferences() {
        String block = "a = 2;b = 3;a = (a plus b);a = (a plus b);a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(8), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldIncrementReference() {
        String block = "a = 1;b = 3;a plus b";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(4), (Long) parser.evaluate().getResult());
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

    @Test
    public void shouldNotExecuteIfIntoIfStmtWhenConditionsIsFalse() {
        String block = "a = 1;\n" +
                       "IF(FALSE)\n" +
                       "  IF(TRUE)\n" +
                       "    a = 2\n" +
                       "  END\n" +
                       "END;\n" +
                       "a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(1), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldExecuteIfIntoIfStmtWhenConditionsIsTrue() {
        String block = "a = 1;\n" +
                       "IF(TRUE)\n" +
                       "  IF(TRUE)\n" +
                       "    a = 2\n" +
                       "  END\n" +
                       "END;\n" +
                       "a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldCreateAnArray() {
        String block = "[1, 'teste', 2.2]";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        List<Object> result = (List<Object>) parser.evaluate().getResult();
        Assert.assertEquals(1L, ((TypedResult) result.get(0)).getResult());
        Assert.assertEquals("teste", ((TypedResult) result.get(1)).getResult());
        Assert.assertEquals(2.2, ((TypedResult) result.get(2)).getResult());
    }

    @Test
    public void testItemInArray() {
        String block = "'teste' IN [1, 'teste', 2.2]";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertTrue((Boolean) parser.evaluate().getResult());
    }

    @Test
    public void testItemNotInArray() {
        String block = "NOT ('teste' IN [1, 'teste', 2.2])";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertFalse((Boolean) parser.evaluate().getResult());
    }

    @Test
    public void testMapExampleFunction() {
        String block = "map([2, 3, 4])";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        List<Object> result = (List<Object>) parser.evaluate().getResult();
        Assert.assertEquals(4L, ((TypedResult) result.get(0)).getResult());
        Assert.assertEquals(6L, ((TypedResult) result.get(1)).getResult());
        Assert.assertEquals(8L, ((TypedResult) result.get(2)).getResult());
    }

    @Test
    public void shouldOneLineExpression() {
        String block = "(1 plus 1)";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldAssignOneLineExpression() {
        String block = "a = (1 plus 1)";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldSimpleExpression() {
        String block = "a = (1 plus 1);\n" +
                       "a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldIncrementVariable() {
        String block = "a = 1;\n" +
                       "a = (a plus 1);\n" +
                       "a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(2), (Long) parser.evaluate().getResult());
    }

    @Test
    public void shouldExecuteLoopIntoForeach() {
        String block = "a = 0;\n" +
                       "FOREACH [1, 2, 4] AS element\n" +
                       "  a = (a plus element)\n" +
                       "END;\n" +
                       "a";
        SelParser parser = new SelParser(block, selContext, new ExecutionData() {
        });
        Assert.assertEquals(Long.valueOf(7), (Long) parser.evaluate().getResult());
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
