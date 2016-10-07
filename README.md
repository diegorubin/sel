Simple Embedded Language
========================

## Usage

### Binary Operators

```java
@Operator("mybinaryop")
public class MyBinaryOperator implements BinaryOperator {
  @Override
  public OperationResult execute(OperatorArgument arg1, OperatorArgument arg2) {
    /* your logic */
    return result;
  }
}
```

### Unary Operators

```java
@Operator("myunaryop")
public class MyUnaryOperator implements UnaryOperator {
  @Override
  public OperationResult execute(OperatorArgument argument) {
    /* your logic */
    return result;
  }
}
```

### Functions

```java
@Function("myfunction")
public class MyFunction extends AbstractFunction {
  @Override
  public OperationResult execute(OperatorArgument... args) {
    /* your logic */
    return new IntegerResult(42L);
  }
}
```

### Constants

```java
@Constant("myconst")
public class MyConstant implements SimpleConstant<BooleanResult> {
  @Override
  public BooleanResult getValue() {
    return new BooleanResult(false);
  }
}
```

#### Using in expression

"myfunction() mybinaryop (myunaryop myconst)"

### Built-in Operators

#### Logic Operators

**AND Operator**

**OR Operator**

**NOT Operator**

#### Math Operators

**Equals Operator**

**GreaterThanEquals Operator**

**GreaterThan Operator**

**LessThanEquals Operator**

**LessThan Operator**

**Plus Operator**

