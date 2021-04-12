package lang.sel.core;

import lang.sel.exceptions.SelParserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author diegorubin
 */
@RunWith(MockitoJUnitRunner.class)
public class SelLexerTest {

    @Test
    public void shouldGetId() {
        final String expr = "lista";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getLexeme(), expr);
    }

    @Test
    public void shouldGetAnd() {
        final String expr = "AND";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getLexeme(), expr);
    }

    @Test
    public void shouldGetNot() {
        final String expr = "NOT";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getLexeme(), expr);
    }

    @Test
    public void shouldGetKeywordsSkippingWhitespace() {
        final String expr = "AND TESTE";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
    }

    @Test
    public void shouldGetStartParentheses() {
        final String expr = "(";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.SP);
    }

    @Test
    public void shouldGetEndParentheses() {
        final String expr = ")";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.EP);
    }

    @Test
    public void shouldGetString() {
        final String expr = "TESTE('nike')";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.SP);
        Assert.assertEquals(lexer.getToken(), Keyword.STRING);
        Assert.assertEquals(lexer.getLexeme(), "nike");
        Assert.assertEquals(lexer.getToken(), Keyword.EP);
    }

    @Test
    public void shouldGetExpression() {
        final String expr = "TESTE(list) AND TESTE('nike')";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.SP);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getLexeme(), "list");
        Assert.assertEquals(lexer.getToken(), Keyword.EP);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.SP);
        Assert.assertEquals(lexer.getToken(), Keyword.STRING);
        Assert.assertEquals(lexer.getLexeme(), "nike");
        Assert.assertEquals(lexer.getToken(), Keyword.EP);
    }

    @Test
    public void shouldGetTokensForExpressionList() {
        final String expr = "TESTE(list, 'nike')";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.SP);
        Assert.assertEquals(lexer.getToken(), Keyword.ID);
        Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
        Assert.assertEquals(lexer.getToken(), Keyword.STRING);
        Assert.assertEquals(lexer.getToken(), Keyword.EP);
    }

    @Test
    public void shouldGetNumber() {
        final String expr = "123, 1.123, .1234";

        final SelLexer lexer = new SelLexer(expr);
        Assert.assertEquals(lexer.getToken(), Keyword.INTEGER);
        Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
        Assert.assertEquals(lexer.getToken(), Keyword.FLOAT);
        Assert.assertEquals(lexer.getToken(), Keyword.FACTOR_SEPARATOR);
        Assert.assertEquals(lexer.getToken(), Keyword.FLOAT);
    }

    @Test(expected = SelParserException.class)
    public void shouldThrowExceptionForWrongExpression() {
        final String expr = "AND";

        final SelLexer lexer = new SelLexer(expr);
        lexer.match(Keyword.SP);
    }

    @Test(expected = SelParserException.class)
    public void shouldThrowExceptionForWrongString() {
        final String expr = "'wrong string";

        final SelLexer lexer = new SelLexer(expr);
        lexer.match(null);
        lexer.match(Keyword.STRING);
    }
}
