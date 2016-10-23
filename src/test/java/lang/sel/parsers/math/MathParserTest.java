package lang.sel.parsers.math;

import lang.sel.commons.operators.logic.NotOperator;
import lang.sel.commons.operators.math.*;
import lang.sel.core.SelContext;
import lang.sel.core.SelParser;
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
public class MathParserTest {

  private SelContext selContext;

  @Before
  public void setup() {
    selContext = new SelContext();
    selContext.addBinaryOperator("GTE", GreaterThanEqualsOperator.class);
    selContext.addBinaryOperator("GT", GreaterThanOperator.class);
    selContext.addBinaryOperator("LTE", LessThanEqualsOperator.class);
    selContext.addBinaryOperator("LT", LessThanOperator.class);
    selContext.addBinaryOperator("PLUS", PlusOperator.class);
    selContext.addUnaryOperator("NOT", NotOperator.class);
  }

  @Test
  public void shouldGetTrueIfGreater() {
    SelParser parser = new SelParser("4 GTE 2.0", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfLess() {
    SelParser parser = new SelParser("2 LTE 4.0", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfNotGreater() {
    SelParser parser = new SelParser("1.0 GTE 5.0", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfLess() {
    SelParser parser = new SelParser("4 LTE 2.0", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfEqualsInGreater() {
    SelParser parser = new SelParser("1.0 GT 1", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfIsGreater() {
    SelParser parser = new SelParser("2.0 GT 1", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetFalseIfEqualsInLess() {
    SelParser parser = new SelParser("1.0 LT 1", selContext, new ExecutionData() {
    });
    Assert.assertFalse((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldGetTrueIfIsLess() {
    SelParser parser = new SelParser("1.0 LT 2", selContext, new ExecutionData() {
    });
    Assert.assertTrue((Boolean) parser.evaluate().getResult());
  }

  @Test
  public void shouldSum2Plus2() {
    SelParser parser = new SelParser("2 PLUS 2", selContext, new ExecutionData() {
    });
    Assert.assertEquals(parser.evaluate().getResult().toString(), "4");
  }

  @Test(expected = SelSemanticException.class)
  public void shouldThrowSemanticExceptionIfWrongDataType() {
    SelParser parser = new SelParser("1.0 GTE 'test'", selContext, new ExecutionData() {
    });
    parser.evaluate().getResult();
  }
}
