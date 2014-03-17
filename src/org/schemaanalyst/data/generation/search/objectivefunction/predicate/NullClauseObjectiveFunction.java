package org.schemaanalyst.data.generation.search.objectivefunction.predicate;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst._deprecated.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst._deprecated.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst._deprecated.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst._deprecated.datageneration.search.objective.value.NullValueObjectiveFunction;
import org.schemaanalyst.logic.predicate.clause.NullClause;

import java.util.List;

/**
 * Created by phil on 24/01/2014.
 */
public class NullClauseObjectiveFunction extends ObjectiveFunction<Data> {

    private NullClause nullClause;

    public NullClauseObjectiveFunction(NullClause nullClause) {
        this.nullClause = nullClause;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        String description = nullClause.toString();
        List<Row> rows = data.getRows(nullClause.getTable());

        if (rows.size() > 0) {
            SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

            for (Row row : rows) {
                objVal.add(
                        NullValueObjectiveFunction.compute(
                                row.getCell(nullClause.getColumn()).getValue(),
                                nullClause.getSatisfy())
                );
            }
            return objVal;
        }

        return ObjectiveValue.worstObjectiveValue(description);
    }
}