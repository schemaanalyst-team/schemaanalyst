package org.schemaanalyst.testgeneration;

import org.schemaanalyst.data.Cell;
import org.schemaanalyst.data.Data;
import org.schemaanalyst.data.StringValue;
import org.schemaanalyst.data.ValueFactory;
import org.schemaanalyst.data.generation.DataGenerationReport;
import org.schemaanalyst.data.generation.DataGenerator;
import org.schemaanalyst.data.generation.selector.SelectorDataGenerator;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirementDescriptor;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.integrityconstraint.PredicateGenerator;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;
import org.schemaanalyst.util.DataMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by phil on 24/01/2014.
 */
public class TestSuiteGenerator {

    private final static Logger LOGGER = Logger.getLogger(TestSuiteGenerator.class.getName());

    private Schema schema;
    private TestRequirements testRequirements;
    private ValueFactory valueFactory;
    private DataGenerator dataGenerator;
    private HashMap<Table, Data> initialTableData;
    private TestSuite testSuite;
    //private Data selectorState;
    private TestSuiteGenerationReport testSuiteGenerationReport;
    
	private DataMapper mapper = new DataMapper();

    public TestSuiteGenerator(Schema schema,
                              TestRequirements testRequirements,
                              ValueFactory valueFactory,
                              DataGenerator dataGenerator) {
        this.schema = schema;
        this.testRequirements = testRequirements;
        this.valueFactory = valueFactory;
        this.dataGenerator = dataGenerator;

        initialTableData = new HashMap<>();
    }

    public TestSuite generate() {
        LOGGER.fine("Generating test suite for " + schema);

        testSuite = new TestSuite();
        testSuiteGenerationReport = new TestSuiteGenerationReport();
        
        if (dataGenerator instanceof SelectorDataGenerator) {
        	mapper.connectDB(schema);
        	mapper.mapData();
        	/*
            DataMapper mapper = new DataMapper();
            mapper.connectDB(schema);
            mapper.mapData();
            selectorState = mapper.getData();
            */
        }
        
        generateInitialTableData();
        generateTestCases();
        return testSuite;
    }

    public TestSuiteGenerationReport getTestSuiteGenerationReport() {
        return testSuiteGenerationReport;
    }

    protected void generateInitialTableData() {
        for (Table table : schema.getTablesInOrder()) {

            ComposedPredicate acceptancePredicate = PredicateGenerator.generatePredicate(schema.getConstraints(table));

            // add not null predicates
            List<Column> notNullColumns = table.getColumns();

            /*
            // NOTE: selecting individual columns like this will cause AUCC test requirements to fail.

            List<Column> notNullColumns = new ArrayList<>();
            PrimaryKeyConstraint primaryKeyConstraint = schema.getPrimaryKeyConstraint(table);
            if (primaryKeyConstraint != null) {
                // TODO: some check as to whether it's been added already ...
                notNullColumns.addAll(primaryKeyConstraint.getColumns());
            }
            for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
                notNullColumns.addAll(uniqueConstraint.getColumns());
            }
            for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {
                notNullColumns.addAll(foreignKeyConstraint.getColumns());
            }
            */
            AndPredicate predicate = new AndPredicate();
            predicate.addPredicate(acceptancePredicate);
            PredicateGenerator.addNullPredicates(predicate, table, notNullColumns, false);

            LOGGER.fine("\nGENERATING INITIAL TABLE DATA FOR " + table);
            LOGGER.fine("--- Predicate is " + predicate);
            Data state = new Data();
            Data data = new Data();

            /*
            if (dataGenerator instanceof SelectorDataGenerator) {
            	//state.appendData(mapper.returnPerfectState(table));
            	state.appendData(mapper.returnPerfectState(table));
            }
            */
            // add referenced tables to the state
            boolean haveLinkedData = addInitialTableDataToState(state, table);
            /*
            if (dataGenerator instanceof SelectorDataGenerator) {
            	//state.appendData(mapper.returnPerfectState(table));
            	mapper.returnPerfectoState(table);
            	if (schema.getConnectedTables(table).size() > 0) {
            		state = mapper.returnState(table).duplicate();
            	}
            	data = mapper.returnData(table).duplicate();

            }
            */
            if (haveLinkedData) {
                if (!(dataGenerator instanceof SelectorDataGenerator)) {
                	data.addRow(table, valueFactory);
                } else {
                	mapper.returnPerfectoState(table);
                	if (mapper.returnData(table).getNumRows() == 0)
                    	data.addRow(table, valueFactory);
                	else
                		data = mapper.returnData(table).duplicate();
                }

                // generate the row
                DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
                if (dataGenerationReport.isSuccess()) {
                    LOGGER.fine("--- Success, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
                    LOGGER.fine("--- Data is: \n" + data);
                    initialTableData.put(table, data);
                } else {
                    LOGGER.fine("--- Failed");
                    System.err.println("Generating Initial Data --- Failed");
                    System.err.println("PREDICATE");
                    System.err.println(predicate);
                    System.err.println("STATE");
                    System.err.println(state);
                    System.err.println("DATA");
                    System.err.println(data);
                }

                testSuiteGenerationReport.addInitialTableDataResult(
                        table, new DataGenerationResult(data, state, dataGenerationReport));
            } else {
                // there was no linked data generated to add to the state, so generated of this row failed by default
                testSuiteGenerationReport.addInitialTableDataResult(
                        table, null);
            }
        }
    }

    protected void generateTestCases() {
        for (TestRequirement testRequirement : testRequirements.getTestRequirements()) {

            Predicate predicate = testRequirement.getPredicate();
            Table table = getTestRequirementTable(testRequirement);

            LOGGER.fine("\nGENERATING TEST CASE");
            for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
                LOGGER.fine(testRequirementDescriptor.toString());
            }
            LOGGER.fine("--- Predicate is " + predicate);

            Data state = new Data();
            Data data = new Data();
            /*
            if (dataGenerator instanceof SelectorDataGenerator) {
            	//state.appendData(mapper.returnPerfectState(table));
            	mapper.returnPerfectoState(table);
            	if (schema.getConnectedTables(table).size() > 0) {
            		state = mapper.returnState(table).duplicate();
            	}
            	data = mapper.returnData(table).duplicate();

            }
            */
            predicate = addAdditionalRows(state, data, predicate, table, testRequirement.getRequiresComparisonRow());
            if (predicate != null) {
                if (!(dataGenerator instanceof SelectorDataGenerator)) {
                	data.addRow(table, valueFactory);
                } else {
                	//mapper.returnPerfectoState(table);
                	if (mapper.returnData(table).getNumRows() == 0)
                    	data.addRow(table, valueFactory);
                	else
                		data.appendData(mapper.returnData(table).duplicate());
                }
                LOGGER.fine("--- Pre-reduced predicate is " + predicate);
                predicate = predicate.reduce();
                LOGGER.fine("--- Reduced predicate is " + predicate);

                DataGenerationReport dataGenerationReport = dataGenerator.generateData(data, state, predicate);
                if (dataGenerationReport.isSuccess()) {

                    TestCase testCase = new TestCase(testRequirement, data, state);
                    testSuite.addTestCase(testCase);
                    
                	// Scoring Readable strings and VarChars
                	double readableState = 0;
                	double readableData = 0;
                	for (Cell cell : data.getCells()) {
                		if (cell.getValueInstance() instanceof StringValue) {
                			if (!cell.isNull() && !cell.getValue().toString().equals("\"\"")) {
                				readableData += this.langModelScore(cell.getValue().toString());
                				testSuite.addlengthOfStrings(cell.getValue().toString().length());
                			}
                			
                			if (cell.getValue().toString().equals("\"\"")) {
                				testSuite.addnumberOfEmptyStrings(1);
                			}
                		}
                	}
                	
                	for (Cell cell : state.getCells()) {
                		if (cell.getColumn().getDataType() instanceof CharDataType || cell.getColumn().getDataType() instanceof TextDataType ) {
                			if (!cell.isNull() && !cell.getValue().toString().equals("\"\"")) {
                				readableState += this.langModelScore(cell.getValue().toString());
                				testSuite.addlengthOfStrings(cell.getValue().toString().length());
                			}
                			
                			
                			if (cell.getValue().toString().equals("\"\"")) {
                				testSuite.addnumberOfEmptyStrings(1);
                			}
                		}
                	}
                    
                    // Adding the total score of T-suffiecnt and test data together
                    testSuite.addReadableScore(readableData + readableState);
                    
                    LOGGER.fine("--- SUCCESS, generated in " + dataGenerationReport.getNumEvaluations() + " evaluations");
                    LOGGER.fine("--- Data is \n" + data);
                } else {
                    LOGGER.fine("--- FAILED");
                    System.err.println("Generating Data --- Failed");
                    System.err.println("TestRequirementDescriptor");
                    for (TestRequirementDescriptor testRequirementDescriptor : testRequirement.getDescriptors()) {
                    	System.err.println(testRequirementDescriptor.toString());
                    }
                    System.err.println("Result is: " + testRequirement.getResult());
                    System.err.println("STATE");
                    System.err.println(state);
                    System.err.println("DATA");
                    System.err.println(data);
                }

                testSuiteGenerationReport.addTestRequirementResult(
                        testRequirement, new DataGenerationResult(data, state, dataGenerationReport));
            } else  {
                testSuiteGenerationReport.addTestRequirementResult(
                        testRequirement, null);
            }
        }
    }

    protected Table getTestRequirementTable(TestRequirement testRequirement) {
        Set<Table> tables = testRequirement.getTables();
        if (tables.size() != 1) {
            throw new TestGenerationException("Test requirement  should have predicates involving exactly one table, has " + tables.size() + ". Test requirement is: \n" + testRequirement);
        }
        return tables.iterator().next();
    }

    protected Predicate addAdditionalRows(Data state, Data data, Predicate predicate, Table table, boolean requiresComparisonRow) {
        LOGGER.fine("--- adding additional rows");

        boolean haveLinkedData = addInitialTableDataToState(state, table);
        if (!haveLinkedData) {
            return null;
        }

        if (requiresComparisonRow) { // if (getRequiresComparisonRow(predicate)) {
            Data comparisonRow;
            if ((dataGenerator instanceof SelectorDataGenerator)) {
            	if (mapper.returnData(table).getNumRows() == 0)
                	comparisonRow = initialTableData.get(table);
            	else
            		comparisonRow = mapper.returnData(table).duplicate();
            } else {
            	comparisonRow = initialTableData.get(table);
            }
            if (comparisonRow == null) {
                LOGGER.fine("--- could not add comparison row, data generation FAILED");
                return null;
            }

            
            LOGGER.fine("--- added comparison row");
            state.appendData(comparisonRow);

            predicate = addLinkedTableRowsToData(data, predicate, table);
        }
        return predicate;
    }

    protected boolean addInitialTableDataToState(Data state, Table table) {
        LOGGER.fine("--- adding initial data to state for linked tables");

        // add rows for tables linked via foreign keys to the state
        List<Table> linkedTables = schema.getConnectedTables(table);
        for (Table linkedTable : linkedTables) {

            // a row should always have been previously-generated
            // for a linked table
            if (!linkedTable.equals(table)) {
            	Data initialData = null;
                if (!(dataGenerator instanceof SelectorDataGenerator)) {
                	initialData = initialTableData.get(linkedTable);
                } else {
                	mapper.returnPerfectoState(table);
                	if (mapper.returnStatedTable(table).getNumRows() == 0)
                    	initialData = initialTableData.get(linkedTable);
                	else
                		initialData = mapper.returnStatedTable(linkedTable).duplicate();
                	//initialData = mapper.returnState(linkedTable);
                	//System.out.println(initialData);
                }
                // cannot generate data in this instance
                if (initialData == null) {
                    return false;
                }
                state.appendData(initialData);
            }
        }
        return true;
    }

    protected Predicate addLinkedTableRowsToData(Data data, Predicate predicate, Table table) {

        for (ForeignKeyConstraint foreignKeyConstraint : schema.getForeignKeyConstraints(table)) {

            Table refTable = foreignKeyConstraint.getReferenceTable();
            if (!refTable.equals(table)) {

                boolean refColsUnique = areRefColsUnique(predicate, foreignKeyConstraint);

                if (refColsUnique) {
                    LOGGER.fine("--- foreign key columns are unique in " + table);

                    // append the predicate with the acceptance predicate of the original
                    AndPredicate newPredicate = new AndPredicate();
                    newPredicate.addPredicate(predicate);
                    newPredicate.addPredicate(PredicateGenerator.generatePredicate(schema.getConstraints(refTable)));
                    predicate = newPredicate;

                    LOGGER.fine("--- new predicate is " + predicate);

                    LOGGER.fine("--- adding foreign key row for " + refTable);
                    predicate = addLinkedTableRowsToData(data, predicate, refTable);
                    //data.addRow(refTable, valueFactory);
                    if (!(dataGenerator instanceof SelectorDataGenerator)) {
                    	data.addRow(refTable, valueFactory);
                    } else {
                    	//mapper.returnPerfectoState(table);
                    	if (mapper.returnData(table).getNumRows() == 0)
                        	data.addRow(refTable, valueFactory);
                    	else
                    		data.appendData(mapper.returnData(refTable).duplicate());
                    }
                }
            }
        }

        return predicate;
    }

    protected boolean areRefColsUnique(Predicate predicate, ForeignKeyConstraint foreignKeyConstraint) {
        //Table table = foreignKeyConstraint.getTable();
        final List<Column> uniqueColumns = new ArrayList<>();

        /*
        if (schema.hasPrimaryKeyConstraint(table)) {
            uniqueColumns.addAll(schema.getPrimaryKeyConstraint(table).getColumns());
        }

        for (UniqueConstraint uniqueConstraint : schema.getUniqueConstraints(table)) {
            uniqueColumns.addAll(uniqueConstraint.getColumns());
        }
        */

        predicate.accept(new PredicateAdaptor() {
            @Override
            public void visit(MatchPredicate predicate) {
                if (predicate.tableIsRefTable()) {
                    uniqueColumns.addAll(predicate.getColumns());
                }
            }
        });

        for (Column column : foreignKeyConstraint.getColumns()) {
            if (!uniqueColumns.contains(column)) {
                return false;
            }
        }

        return true;
    }
    
    protected double langModelScore(String string) {
    	try {
    	    // LangModel lm = new LangModel("../moby_dick_char_lm");
    	    LangModel lm = new LangModel("ukwac_char_lm");
    	    return lm.score(string, false);
	    } catch (Exception e) {
    	    System.err.println("Error: " + e.getMessage());
    	    return 0;
	    }
    }
} 
