package org.schemaanalyst.datageneration.search.objective.constraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.schemaanalyst.data.Data;
import org.schemaanalyst.datageneration.search.objective.ObjectiveFunction;
import org.schemaanalyst.datageneration.search.objective.ObjectiveValue;
import org.schemaanalyst.datageneration.search.objective.SumOfMultiObjectiveValue;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

public class SchemaConstraintSystemObjectiveFunction extends ObjectiveFunction<Data> {

    private String description;
    private List<ObjectiveFunction<Data>> subObjectiveFunctions;

    public SchemaConstraintSystemObjectiveFunction(Schema schema) {
        this(schema, null, null);
    }

    public SchemaConstraintSystemObjectiveFunction(Schema schema, Data state, Constraint constraintToInvalidate) {
        subObjectiveFunctions = new ArrayList<>();

        // NULL is only allowed for row acceptance when we are not trying to 
        // invalidate one constraint, i.e. when constraintToInvalidate != null
        boolean allowNull = constraintToInvalidate != null;

        List<Table> tables;
        if (constraintToInvalidate == null) {
            tables = schema.getTables();
        } else {
            Table constraintTable = constraintToInvalidate.getTable();
            tables = constraintTable.getConnectedTables();
            tables.add(constraintTable);
        }
        
        for (Table table : tables) {
            for (Constraint constraint : table.getConstraints()) {
                boolean satisfyConstraint = !constraint.equals(constraintToInvalidate);
    
                // You cannot make a valid PK NULL, so the objective function for the primary key 
                // is not included 
                if ((constraintToInvalidate instanceof NotNullConstraint)
                        && (constraint instanceof PrimaryKeyConstraint)) {
    
                    PrimaryKeyConstraint primaryKey = (PrimaryKeyConstraint) constraint;
                    NotNullConstraint notNull = (NotNullConstraint) constraintToInvalidate;
    
                    if (primaryKey.getColumns().contains(notNull.getColumn())) {
                        continue;
                    }
                }
    
                ConstraintObjectiveFunctionFactory factory = new ConstraintObjectiveFunctionFactory(
                        constraint, state, satisfyConstraint, allowNull);
    
                subObjectiveFunctions.add(factory.create());
            }
        }
        
        description = constraintToInvalidate == null
                ? "Satisfy all constraints"
                : "Satisfy all except " + constraintToInvalidate;
    }

    @Override
    public ObjectiveValue evaluate(Data data) {
        SumOfMultiObjectiveValue objVal = new SumOfMultiObjectiveValue(description);

        for (ObjectiveFunction<Data> objFun : subObjectiveFunctions) {
            objVal.add(objFun.evaluate(data));
        }

        return objVal;
    }
    
    public List<ObjectiveFunction<Data>> getSubObjectiveFunctions() {
        return Collections.unmodifiableList(subObjectiveFunctions);
    }
}
