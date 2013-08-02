package org.schemaanalyst.datageneration.search.objective.data;

import java.util.ArrayList;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.Row;
import org.schemaanalyst.datageneration.search.objective.MultiObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Column;

public abstract class ConstraintObjectiveFunction extends ObjectiveFunction<Data> {
    
    protected List<Column> columns;
    protected String description;
    protected boolean goalIsToSatisfy;
    
    protected List<Row> dataRows;
    protected MultiObjectiveValue objVal;
    protected List<Row> satisfyingRows, falsifyingRows;
    
    public ConstraintObjectiveFunction(List<Column> columns, String description, boolean goalIsToSatisfy) {
        this.columns = columns;
        this.description = description;
        this.goalIsToSatisfy = goalIsToSatisfy;
        satisfyingRows = new ArrayList<Row>();
        falsifyingRows = new ArrayList<Row>();
    }    
    
    public boolean goalIsToSatisfy() {
        return goalIsToSatisfy;
    }
    
    @Override
    public ObjectiveValue evaluate(Data data) {
        satisfyingRows.clear();
        falsifyingRows.clear();
        
        loadRows(data);
        
        if (dataRows.size() == 0) {
            description +=  "(no data rows)";
            return zeroRowsOptimalityCondition() 
                    ? ObjectiveValue.optimalObjectiveValue(description)
                    : ObjectiveValue.worstObjectiveValue(description);
        }
        
        // The optimum corresponds to
        // -- a success (goalIsToSatisfy) on EVERY row, or 
        // -- a fail (!goalIsToSatisfy) on EVERY row.   
        // (Partially succeeding or failing rows could leave the database in an
        // inconsistent state unless knowledge about succeeding an failing rows
        // is taken into account.  It is assumed it won't be.)          
        objVal = new SumOfMultiObjectiveValue(description);
        
        for (Row row : dataRows) {
            ObjectiveValue rowObjVal = evaluateRow(row); 
            
            objVal.add(rowObjVal);
            
            boolean satisfying = 
                    (goalIsToSatisfy && rowObjVal.isOptimal()) ||
                    (!goalIsToSatisfy && !rowObjVal.isOptimal());
            
            if (satisfying) {
                satisfyingRows.add(row);
            } else {
                falsifyingRows.add(row);
            }                           
        }

        return objVal;        
    }
    
    protected void loadRows(Data data) {
        dataRows = data.getRows(columns);
    }
    
    protected abstract ObjectiveValue evaluateRow(Row row);
    
    protected boolean zeroRowsOptimalityCondition() {
        return goalIsToSatisfy;
    }
    
    public List<Row> getSatisfyingRows() {
        return satisfyingRows;
    }   
    
    public List<Row> getFalsifyingRows() {
        return falsifyingRows;
    }    
}