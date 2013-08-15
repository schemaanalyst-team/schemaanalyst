package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.NumericDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML182NotNulls schema.
 * Java code originally generated: 2013/08/15 23:00:23
 *
 */

@SuppressWarnings("serial")
public class NistDML182NotNulls extends Schema {

	public NistDML182NotNulls() {
		super("NistDML182NotNulls");

		Table tableIdCodes = this.createTable("ID_CODES");
		tableIdCodes.createColumn("CODE1", new IntDataType());
		tableIdCodes.createColumn("CODE2", new IntDataType());
		tableIdCodes.createColumn("CODE3", new IntDataType());
		tableIdCodes.createColumn("CODE4", new IntDataType());
		tableIdCodes.createColumn("CODE5", new IntDataType());
		tableIdCodes.createColumn("CODE6", new IntDataType());
		tableIdCodes.createColumn("CODE7", new IntDataType());
		tableIdCodes.createColumn("CODE8", new IntDataType());
		tableIdCodes.createColumn("CODE9", new IntDataType());
		tableIdCodes.createColumn("CODE10", new IntDataType());
		tableIdCodes.createColumn("CODE11", new IntDataType());
		tableIdCodes.createColumn("CODE12", new IntDataType());
		tableIdCodes.createColumn("CODE13", new IntDataType());
		tableIdCodes.createColumn("CODE14", new IntDataType());
		tableIdCodes.createColumn("CODE15", new IntDataType());
		tableIdCodes.createPrimaryKeyConstraint(tableIdCodes.getColumn("CODE1"), tableIdCodes.getColumn("CODE2"), tableIdCodes.getColumn("CODE3"), tableIdCodes.getColumn("CODE4"), tableIdCodes.getColumn("CODE5"), tableIdCodes.getColumn("CODE6"), tableIdCodes.getColumn("CODE7"), tableIdCodes.getColumn("CODE8"), tableIdCodes.getColumn("CODE9"), tableIdCodes.getColumn("CODE10"), tableIdCodes.getColumn("CODE11"), tableIdCodes.getColumn("CODE12"), tableIdCodes.getColumn("CODE13"), tableIdCodes.getColumn("CODE14"), tableIdCodes.getColumn("CODE15"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE1"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE2"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE3"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE4"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE5"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE6"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE7"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE8"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE9"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE10"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE11"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE12"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE13"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE14"));
		tableIdCodes.createNotNullConstraint(tableIdCodes.getColumn("CODE15"));

		Table tableOrders = this.createTable("ORDERS");
		tableOrders.createColumn("CODE1", new IntDataType());
		tableOrders.createColumn("CODE2", new IntDataType());
		tableOrders.createColumn("CODE3", new IntDataType());
		tableOrders.createColumn("CODE4", new IntDataType());
		tableOrders.createColumn("CODE5", new IntDataType());
		tableOrders.createColumn("CODE6", new IntDataType());
		tableOrders.createColumn("CODE7", new IntDataType());
		tableOrders.createColumn("CODE8", new IntDataType());
		tableOrders.createColumn("CODE9", new IntDataType());
		tableOrders.createColumn("CODE10", new IntDataType());
		tableOrders.createColumn("CODE11", new IntDataType());
		tableOrders.createColumn("CODE12", new IntDataType());
		tableOrders.createColumn("CODE13", new IntDataType());
		tableOrders.createColumn("CODE14", new IntDataType());
		tableOrders.createColumn("CODE15", new IntDataType());
		tableOrders.createColumn("TITLE", new VarCharDataType(80));
		tableOrders.createColumn("COST", new NumericDataType(5, 2));
		tableOrders.createForeignKeyConstraint(Arrays.asList(tableOrders.getColumn("CODE1"), tableOrders.getColumn("CODE2"), tableOrders.getColumn("CODE3"), tableOrders.getColumn("CODE4"), tableOrders.getColumn("CODE5"), tableOrders.getColumn("CODE6"), tableOrders.getColumn("CODE7"), tableOrders.getColumn("CODE8"), tableOrders.getColumn("CODE9"), tableOrders.getColumn("CODE10"), tableOrders.getColumn("CODE11"), tableOrders.getColumn("CODE12"), tableOrders.getColumn("CODE13"), tableOrders.getColumn("CODE14"), tableOrders.getColumn("CODE15")), tableIdCodes, Arrays.asList(tableIdCodes.getColumn("CODE1"), tableIdCodes.getColumn("CODE2"), tableIdCodes.getColumn("CODE3"), tableIdCodes.getColumn("CODE4"), tableIdCodes.getColumn("CODE5"), tableIdCodes.getColumn("CODE6"), tableIdCodes.getColumn("CODE7"), tableIdCodes.getColumn("CODE8"), tableIdCodes.getColumn("CODE9"), tableIdCodes.getColumn("CODE10"), tableIdCodes.getColumn("CODE11"), tableIdCodes.getColumn("CODE12"), tableIdCodes.getColumn("CODE13"), tableIdCodes.getColumn("CODE14"), tableIdCodes.getColumn("CODE15")));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE1"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE2"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE3"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE4"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE5"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE6"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE7"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE8"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE9"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE10"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE11"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE12"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE13"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE14"));
		tableOrders.createNotNullConstraint(tableOrders.getColumn("CODE15"));
	}
}

