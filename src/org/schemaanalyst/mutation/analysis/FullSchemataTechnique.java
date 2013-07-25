/*
 */
package org.schemaanalyst.mutation.analysis;

import org.schemaanalyst.mutation.mutators.ConstraintMutatorWithoutFK;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

/**
 * Runs the 'Full Schemata' style of mutation analysis. This requires that the
 * result generation tool has been run, as it bases the mutation analysis on the
 * results produced by it.
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Full Schemata' style of mutation analysis. This requires"
        + " that the result generation tool has been run, as it bases the "
        + "mutation analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class FullSchemataTechnique extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    /**
     * The number of the trial.
     */
    @Parameter("The number of the trial.")
    protected int trial;
    /**
     * The folder to retrieve the generated results.
     */
    @Parameter("The folder to retrieve the generated results.")
    protected String inputfolder; // Default in validate
    /**
     * The folder to write the results.
     */
    @Parameter("The folder to write the results.")
    protected String outputfolder; // Default in validate
    /**
     * Whether to submit drop statements prior to running.
     */
    @Parameter(value="Whether to submit drop statements prior to running.", valueAsSwitch = "true")
    protected boolean dropfirst = false;

    @Override
    public void task() {
        // Start results file
        CSVResult result = new CSVResult();
        result.addValue("dbms", databaseConfiguration.getDbDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("trial", trial);

        // Instantiate the DBMS and related objects
        DBMS dbms;
        try {
            dbms = DBMSFactory.instantiate(databaseConfiguration.getDbDbms());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        SQLWriter sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor();

        // Get the required schema class
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

        // Load the SQLExecutionReport for the non-mutated schema
        String reportPath = inputfolder + casestudy + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // Start mutation timing
        long startTime = System.currentTimeMillis();

        // Create the mutant schemas
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        List<Schema> mutants = cm.produceMutants(schema);

        // Schemata step: Rename the mutants
        renameMutants(mutants);

        // Schemata step: Build single drop statement
        StringBuilder dropBuilder = new StringBuilder();
        for (Schema mutant : mutants) {
            for (String statement : sqlWriter.writeDropTableStatements(mutant, true)) {
                dropBuilder.append(statement);
                dropBuilder.append("; ");
                dropBuilder.append(System.lineSeparator());
            }
        }
        String dropStmt = dropBuilder.toString();

        // Schemata step: Build single create statement
        // add new mutant tables
        StringBuilder createBuilder = new StringBuilder();
        for (Schema mutant : mutants) {
            for (String statement : sqlWriter.writeCreateTableStatements(mutant)) {
                createBuilder.append(statement);
                createBuilder.append("; ");
                createBuilder.append(System.lineSeparator());
            }
        }
        String createStmt = createBuilder.toString();

        // Schemata step: Drop existing tables before iterating mutants
        if (dropfirst) {
            databaseInteractor.executeUpdate(dropStmt);
        }

        // Schemata step: Create table before iterating mutants
        databaseInteractor.executeUpdate(createStmt);

        // Begin mutation analysis
        int killed = 0;
        for (int id = 0; id < mutants.size(); id++) {
            Schema mutant = mutants.get(id);

            logger.log(Level.INFO, "Mutant {0}", id);

            // Schemata step: Generate insert prefix string
            String schemataPrefix = "INSERT INTO mutant_" + (id + 1) + "_";

            // Insert the test data
            List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
            for (SQLInsertRecord insertRecord : insertStmts) {

                // Schemata step: Rewrite insert for mutant ID
                String insertStmt = insertRecord.getStatement().replaceAll("INSERT INTO ", schemataPrefix);

                int returnCount = databaseInteractor.executeUpdate(insertStmt);
                if (returnCount != insertRecord.getReturnCode()) {
                    killed++;
                    break; // Stop once killed
                }
            }

        }

        // Schemata step: Drop tables after iterating mutants
        databaseInteractor.executeUpdate(dropStmt);

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        result.addValue("mutationtime", totalTime);
        result.addValue("mutationscore_numerator", killed);
        result.addValue("mutationscore_denominator", mutants.size());

        new CSVWriter(outputfolder + casestudy + ".dat").write(result);
    }

    /**
     * Prepends each mutant with the relevant mutation number
     *
     * @param mutants
     */
    private static void renameMutants(List<Schema> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            for (Table table : mutants.get(i).getTables()) {
                table.setName("mutant_" + (i + 1) + "_" + table.getName());
            }
        }
    }

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
        if (inputfolder == null) {
            inputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator;
        }
    }

    public static void main(String[] args) {
        new FullSchemataTechnique().run(args);
    }
}