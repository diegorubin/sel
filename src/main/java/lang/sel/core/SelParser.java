package lang.sel.core;

import lang.sel.commons.results.*;
import lang.sel.exceptions.SelParserException;
import lang.sel.exceptions.SelSemanticException;
import lang.sel.interfaces.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Expression Parser class implements the expression language
 * <p>
 * Below is described the context-free grammar used in the engine.
 * <p>
 * block -&gt; stmtlist end_expression
 * <p>
 * stmtlist -&gt; stmt [endstmt stmt]
 * <p>
 * stmt -&gt; ifstmt | foreachstmt | returnstmt | expression
 * <p>
 * ifstmt -&gt; 'IF' sp expression ep stmtlist ['ELSE' stmtlist] 'END'
 * <p>
 * foreachstmt -&gt; 'FOREACH' factor 'AS' id stmtlist 'END'
 * <p>
 * returnstmt -&gt; 'RETURN' expression
 * <p>
 * expression -&gt; term [ op term ]
 * <p>
 * term -&gt; [unary_operator] factor
 * <p>
 * factor -&gt; function_name sp [expression_list] ep | sp expression ep | constant | string | number | array | id ['='
 * expression]
 * <p>
 * expression_list -&gt; expression [, expression]
 * <p>
 * unary_operator -&gt; id
 * <p>
 * op -&gt; id
 * <p>
 * function_name -&gt; id
 * <p>
 * constant -&gt; id
 * <p>
 * number -&gt; integer | float
 * <p>
 * integer -&gt; [0-9]+
 * <p>
 * float -&gt; [0-9]*.[0-9]+
 * <p>
 * id -&gt; [a-z][a-z0-9_]*
 * <p>
 * array -&gt; '[' expression_list ']'
 * <p>
 * sp -&gt; '('
 * <p>
 * ep -&gt; ')'
 * <p>
 * end_expression -&gt;'\0'
 * <p>
 * endstmt -&gt; ';'
 *
 * @author diegorubin
 */
public class SelParser {

    private static final String IN_POSITION = " in position ";

    private SelContext selContext;
    private ExecutionData executionData;
    private SelLexer lexer;
    private List<SelBlockContext> stack;
    private Boolean skipToEnd = false;

    /**
     * Instantiates a new Expression parser.
     *
     * @param expression promotion expression to be evaluated
     * @param selContext       engine context singleton
     * @param executionData       execution data with specific data for one single evaluation
     */
    public SelParser(final String expression, final SelContext selContext,
                     final ExecutionData executionData) {
        this.selContext = selContext;
        this.executionData = executionData;
        this.stack = new ArrayList<>();

        lexer = new SelLexer(expression);
        lexer.match(null);
    }

    /**
     * Evaluates the expression.
     * <p>
     * This method execute the expression and return an object with result.
     *
     * @return the operation result
     */
    public TypedResult evaluate() {
        return block();
    }

    /**
     * Execute block of stmtlist's
     *
     * @return the result of last expression
     */
    private TypedResult block() {
        final TypedResult operationResult = stmtlist();
        lexer.match(Keyword.END_EXPRESSION);
        return operationResult;
    }

    /**
     * Execute list of stmt's
     *
     * @return the result of last expression
     */
    private TypedResult stmtlist() {
        TypedResult result = stmt();
        while (lexer.getLookahead() == Keyword.END_STMT) {
            lexer.match(Keyword.END_STMT);
            result = stmt();
        }
        return result;
    }

    /**
     * Execute stmt
     *
     * @return result of expression
     */
    private TypedResult stmt() {
        if (lexer.getLookahead() == Keyword.IF) {
            lexer.match(Keyword.IF);
            TypedResult operationResult = ifstmt();
            if (!skipToEnd) {
                lexer.match(Keyword.END);
            }
            return operationResult;
        }

        if (lexer.getLookahead() == Keyword.FOREACH) {
            lexer.match(Keyword.FOREACH);
            foreachstmt();
            return null;
        }

        if (lexer.getLookahead() == Keyword.RETURN) {
            lexer.match(Keyword.RETURN);
            return returnstmt();
        }

        return expression();
    }

    /**
     * Execute foreachstmt
     *
     * @return the expression
     */
    private void foreachstmt() {
        final TypedResult iterator = factor();
        String varName;
        Integer position;


        lexer.match(Keyword.AS);
        varName = lexer.getLexeme();
        position = lexer.getPosition();
        lexer.match(Keyword.ID);

        SelForeachBlockContext blockContext = new SelForeachBlockContext(Keyword.FOREACH, position, varName, ((ArrayResult) iterator));
        putBlock(blockContext);

        while (true) {
            executionData.assign(varName, blockContext.getNextElement());
            stmtlist();
            if (!blockContext.isFinished()) {
                lexer.jumpToPosition(blockContext.getPosition());
            } else {
                break;
            }
        }

        lexer.match(Keyword.END);
    }

    /**
     * Execute ifstmt inside if block or null if conditions is false
     *
     * @return the expression
     */
    private TypedResult ifstmt() {
        lexer.match(Keyword.SP);
        TypedResult condition = expression();
        lexer.match(Keyword.EP);

        if ((Boolean) condition.getResult()) {
            TypedResult result = stmtlist();
            if (lexer.getLookahead() == Keyword.ELSE) {
                lexer.match(Keyword.ELSE);
                jumpToNextBlock();
            }
            return result;
        }

        jumpToNextBlock();

        if (lexer.getLookahead() == Keyword.ELSE) {
            lexer.match(Keyword.ELSE);
            return stmtlist();
        }

        return null;
    }

    /**
     * Return stmt
     *
     * @return operation result
     */
    private TypedResult returnstmt() {
        skipToEnd = true;
        TypedResult operationResult = expression();
        jumpToEnd();
        return operationResult;
    }

    /**
     * Representation of non terminal symbol for expression
     * <p>
     * expression produces term and an optional op term
     * <p>
     * expression -&gt; term [ op term ]
     *
     * @return the operation result
     */
    private TypedResult expression() {
        TypedResult result = term();
        while (selContext.isBinaryOperator(lexer.getLexeme())) {
            final String operator = op();
            final TypedResult term2 = term();
            result = executeBinaryOperator(result, operator, term2);
        }
        return result;
    }

    /**
     * Representation of non terminal symbol for term
     * <p>
     * term produces an optional unary operator and a factor
     * <p>
     * term -&gt; [unary_operator] factor
     *
     * @return the operation result
     */
    private TypedResult term() {
        final String operator = lexer.getLexeme();
        if (selContext.isUnaryOperator(lexer.getLexeme())) {
            lexer.match(Keyword.ID);
            return executeUnaryOperator(factor(), operator);
        }
        return factor();
    }

    /**
     * Representation of terminal symbol for id
     * <p>
     * All terminals should return a result.
     *
     * @param lexeme the current read lexeme
     *
     * @return the result data. If id is a pointer to a function will be returned the result of function, if is a constant
     * the content of constant will be returned. If not be a constant or a function a reference will be returned.
     */
    private TypedResult id(final String lexeme) {
        final TypedResult result;
        TypedResult[] arguments = new TypedResult[0];

        if (selContext.isFunction(lexeme)) {
            lexer.match(Keyword.ID);
            lexer.match(Keyword.SP);
            if (lexer.getLookahead() != Keyword.EP) {
                arguments = expressionList();
            }
            lexer.match(Keyword.EP);
            return executeFunction(lexeme, arguments);
        }

        if (selContext.isConstant(lexeme)) {
            lexer.match(lexer.getLookahead());
            return getConstantValue(lexeme);
        }

        result = new ReferenceResult(lexer.getLexeme());
        lexer.match(Keyword.ID);

        if (lexer.getLookahead() == Keyword.ASSIGNMENT) {
            lexer.match(Keyword.ASSIGNMENT);
            executionData.assign((String) result.getResult(), expression());
        }

        if (executionData.assigned((String) result.getResult())) {
            return executionData.recover((String) result.getResult());
        }

        return result;
    }

    /**
     * Representation of non terminal symbol for factor.
     * <p>
     * factor -&gt; function_name sp expression_list ep | sp expression ep | constant | string | number | id
     *
     * @return the result data. Return a final value or start a new expression.
     */
    private TypedResult factor() {
        final TypedResult result;
        final Keyword lookahead = lexer.getLookahead();
        final String lexeme = lexer.getLexeme();

        if (lookahead == Keyword.STRING) {
            result = new StringResult(lexeme);
            lexer.match(Keyword.STRING);
            return result;
        }

        if (lookahead == Keyword.SP) {
            lexer.match(Keyword.SP);
            result = expression();
            lexer.match(Keyword.EP);
            return result;
        }

        if (lookahead == Keyword.SA) {
            return array_factor();
        }

        if ((result = number(lexeme)) != null) {
            return result;
        }

        if (lookahead == Keyword.ID) {
            return id(lexeme);
        }

        throw new SelParserException("expecting a valid factor in position " + lexer.getPosition() + " and found lexeme: " + lexeme);
    }

    private TypedResult array_factor() {
        final TypedResult result;
        lexer.match(Keyword.SA);
        result = new ArrayResult(Arrays.asList(expressionList()));
        lexer.match(Keyword.EA);
        return result;
    }

    /**
     * Representation of the non terminal symbol for factor.
     *
     * @param lexeme the current lexeme
     *
     * @return operation result with number content
     */
    private TypedResult number(final String lexeme) {
        final Keyword lookahead = lexer.getLookahead();
        final TypedResult result;

        if (lookahead == Keyword.INTEGER) {
            result = new IntegerResult(Long.parseLong(lexeme));
            lexer.match(Keyword.INTEGER);
            return result;
        }

        if (lookahead == Keyword.FLOAT) {
            result = new FloatResult(Double.parseDouble(lexeme));
            lexer.match(Keyword.FLOAT);
            return result;
        }
        return null;
    }

    /**
     * Representation of a expression list
     *
     * @return the array of arguments resulted of expression list
     */
    private TypedResult[] expressionList() {
        final List<TypedResult> args = new ArrayList<>();
        args.add(expression());

        while (lexer.getLookahead() == Keyword.FACTOR_SEPARATOR) {
            lexer.match(Keyword.FACTOR_SEPARATOR);
            args.add(expression());
        }

        final TypedResult[] arguments = new TypedResult[args.size()];
        return args.toArray(arguments);
    }

    /**
     * Get the value of expression constant
     *
     * @param constant the name of constant
     *
     * @return the value of constant
     */
    private TypedResult getConstantValue(final String constant) {
        try {
            final SimpleConstant simpleConstant = selContext.getConstant(constant).newInstance();
            return (TypedResult) simpleConstant.getValue();
        } catch (final Exception e) {
            // TODO log exception
        }
        throw new SelParserException("expecting a valid constant in position " + lexer.getPosition());
    }

    /**
     * Execute the function
     *
     * @param function the name of function
     * @param args     the args from expression list used on function
     *
     * @return the result of function
     */
    private TypedResult executeFunction(final String function, final TypedResult... args) {

        final AbstractFunction abstractFunction;
        try {
            abstractFunction = selContext.getFunction(function).newInstance();
            abstractFunction.setContext(function, selContext, executionData);
            abstractFunction.validateFunctionOptions(args);
            return abstractFunction.execute(args);
        } catch (InstantiationException e) {
            // TODO log exception
        } catch (IllegalAccessException e) {
            // TODO log exception
        }

        throw new SelParserException(
            "Error in the execution of the  " + function + " function " +
            IN_POSITION + lexer.getPosition());
    }

    private TypedResult executeUnaryOperator(final TypedResult argument, final String operator) {
        try {
            final UnaryOperator unaryOperator = selContext.getUnaryOperator(operator).newInstance();
            return unaryOperator.execute(argument);
        } catch (InstantiationException e) {
            // TODO log exception
        } catch (IllegalAccessException e) {
            // TODO log exception
        }
        throw new SelParserException("no such unary operator " + operator + IN_POSITION + lexer.getPosition());
    }

    private TypedResult executeBinaryOperator(final TypedResult value1,
                                              final String operator, final TypedResult value2) {

        final Constructor<?> constructor;

        try {
            constructor = selContext.getBinaryOperator(operator).getConstructor();
            final BinaryOperator binaryOperator = (BinaryOperator) constructor.newInstance();
            return binaryOperator.execute(value1, value2);
        } catch (final NoSuchMethodException e) {
            // TODO log exception
        } catch (final IllegalAccessException e) {
            // TODO log exception
        } catch (final InstantiationException e) {
            // TODO log exception
        } catch (final InvocationTargetException e) {
            // TODO log exception
            throw new SelSemanticException("failed: " + IN_POSITION + lexer.getPosition());
        }

        throw new SelParserException("no such binary operator " + operator + IN_POSITION + lexer.getPosition());
    }

    private String op() {
        final String operator = lexer.getLexeme();
        lexer.match(Keyword.ID);

        return operator;
    }

    /**
     * Skip current stmt
     */
    private void jumpToNextBlock() {
        int blockCounter = 0;
        while (!(lexer.getLookahead() == Keyword.ELSE || lexer.getLookahead() == Keyword.END) || blockCounter != 0) {
            if (startIfBlock(lexer.getLookahead())) {
                blockCounter++;
            }
            if (lexer.getLookahead() == Keyword.END) {
                blockCounter--;
            }
            lexer.match(lexer.getLookahead());
        }
    }

    private void jumpToEnd() {
        while (lexer.getLookahead() != Keyword.END_EXPRESSION) {
            lexer.match(lexer.getLookahead());
        }
    }

    private boolean startIfBlock(Keyword keyword) {
        return keyword == Keyword.IF;
    }

    private void putBlock(final SelBlockContext blockContext) {
        this.stack.add(blockContext);
    }

    private void popBlock() {
        this.stack.remove(this.stack.size() - 1);
    }

    private SelBlockContext currentBlock() {
        return this.stack.get(this.stack.size() - 1);
    }
}
