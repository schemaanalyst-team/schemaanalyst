package org.schemaanalyst.sqlrepresentation.expression;

public class NullExpression extends ExpressionTree {

    public static final int NUM_SUBEXPRESSIONS = 1, SUBEXPRESSION = 0;
    private Expression subexpression;
    private boolean notNull;

    public NullExpression(Expression subexpression, boolean notNull) {
        this.subexpression = subexpression;
        this.notNull = notNull;
    }

    public Expression getSubexpression() {
        return subexpression;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }
    
    @Override
    public int getNumSubexpressions() {
        return NUM_SUBEXPRESSIONS;
    }

    @Override
    public Expression getSubexpression(int index) {
        switch (index) {
            case SUBEXPRESSION:
                return subexpression;
        }
        throw new NonExistentSubexpressionException(this, index);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public NullExpression duplicate() {
        return new NullExpression(subexpression.duplicate(), notNull);
    }
    
    @Override
    public String toString() {
        return subexpression + " IS " + (notNull ? "NOT" : "") + " NULL";
    }
}
