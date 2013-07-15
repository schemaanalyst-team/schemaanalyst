package org.schemaanalyst.datageneration.search.objective.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.BestOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.NullValueObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.relationalpredicate.ListOfCellsObjectiveFunction;
import org.schemaanalyst.logic.RelationalOperator;
import org.schemaanalyst.sqlrepresentation.Column;

import static org.schemaanalyst.logic.RelationalOperator.EQUALS;
import static org.schemaanalyst.logic.RelationalOperator.NOT_EQUALS;

public class UniqueObjectiveFunction extends ObjectiveFunction<Data> {

    protected List<Column> columns;
    protected RelationalOperator op;
    protected Data state;
    protected String description;
    protected boolean goalIsToSatisfy, allowNull;

    public UniqueObjectiveFunction(List<Column> columns,
            Data state,
            String description,
            boolean goalIsToSatisfy, boolean allowNull) {
        this.columns = columns;
        this.state = state;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        this.allowNull = allowNull;

        this.op = goalIsToSatisfy ? NOT_EQUALS : EQUALS;
    }

    public ObjectiveValue evaluate(Data data) {
        List<List<Cell>> dataRows = data.getCells(columns);
        List<List<Cell>> stateRows = state.getCells(columns);

        // special case for negating and there being one or fewer rows
        if (!goalIsToSatisfy && dataRows.size() + stateRows.size() <= 1) {
            return ObjectiveValue.worstObjectiveValue(description + "(nothing to negate row against)");
        }

        MultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);
        ListIterator<List<Cell>> dataRowsIterator = dataRows.listIterator();

        while (dataRowsIterator.hasNext()) {
            List<Cell> dataRow = dataRowsIterator.next();

            if (dataRowsIterator.hasNext() || stateRows.size() > 0) {
                String description = "Row " + dataRow;

                MultiObjectiveValue rowObjVal = goalIsToSatisfy
                        ? new SumOfMultiObjectiveValue(description)
                        : new BestOfMultiObjectiveValue(description);

                evaluateRowAgainstOtherRows(rowObjVal, dataRow, dataRows, dataRowsIterator.nextIndex());
                evaluateRowAgainstOtherRows(rowObjVal, dataRow, stateRows, 0);

                objVal.add(rowObjVal);
            }
        }

        return objVal;
    }

    protected void evaluateRowAgainstOtherRows(
            MultiObjectiveValue objVal,
            List<Cell> row, List<List<Cell>> otherRows, int fromIndex) {

        ListIterator<List<Cell>> rowsIterator = otherRows.listIterator(fromIndex);

        while (rowsIterator.hasNext()) {
            List<Cell> compareRow = rowsIterator.next();
            objVal.add(evaluateRow(row, compareRow, allowNull));
        }
    }

    protected ObjectiveValue evaluateRow(List<Cell> row, List<Cell> otherRow, boolean allowNull) {

        if (allowNull) {

            MultiObjectiveValue rowObjVal = new BestOfMultiObjectiveValue("Allowing for nulls");
            rowObjVal.add(evaluateRow(row, otherRow, false));

            List<Cell> allCells = new ArrayList<>(row);
            allCells.addAll(otherRow);
            for (Cell cell : allCells) {
                rowObjVal.add(NullValueObjectiveFunction.compute(cell.getValue(), true));
            }

            return rowObjVal;

        } else {
            return ListOfCellsObjectiveFunction.compute(row, op, otherRow);
        }
    }
}