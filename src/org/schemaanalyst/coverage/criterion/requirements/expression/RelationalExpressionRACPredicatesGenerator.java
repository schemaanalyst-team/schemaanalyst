package org.schemaanalyst.coverage.criterion.requirements.expression;

import org.schemaanalyst.coverage.criterion.Predicate;
import org.schemaanalyst.coverage.criterion.clause.ExpressionClause;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.expression.RelationalExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phil on 07/02/2014.
 */
public class RelationalExpressionRACPredicatesGenerator extends ExpressionRACPredicatesGenerator {

    private RelationalExpression relationalExpression;

    public RelationalExpressionRACPredicatesGenerator(Table table,
                                                      RelationalExpression relationalExpression) {
        super(table);
        this.relationalExpression = relationalExpression;
    }

    @Override
    public List<Predicate> generateTruePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + relationalExpression + " is true");
        predicate.addClause(new ExpressionClause(table, relationalExpression, true));

        requirements.add(predicate);
        return requirements;
    }

    @Override
    public List<Predicate> generateFalsePredicates() {
        List<Predicate> requirements = new ArrayList<>();

        Predicate predicate = new Predicate("Testing " + relationalExpression + " is false");
        predicate.addClause(new ExpressionClause(table, relationalExpression, true));

        requirements.add(predicate);
        return requirements;
    }
}
