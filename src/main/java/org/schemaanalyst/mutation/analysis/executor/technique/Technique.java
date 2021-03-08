
package org.schemaanalyst.mutation.analysis.executor.technique;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.mutation.analysis.executor.testsuite.TestSuiteResult;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.TestSuite;

import java.util.List;

/**
 * <p>Abstract parent class of techniques performing mutation analysis.</p>
 * 
 * @author Chris J. Wright
 */
public abstract class Technique {
    
    protected Schema schema;
    protected List<Mutant<Schema>> mutants;
    protected TestSuite testSuite;
    protected DBMS dbms;
    protected DatabaseInteractor databaseInteractor;
    protected boolean useTransactions;
    protected String dataGenerator;
    protected String criterion;
    protected long randomseed;

    public Technique(Schema schema, List<Mutant<Schema>> mutants, TestSuite testSuite, DBMS dbms, DatabaseInteractor databaseInteractor, 
    		boolean useTransactions, String dataGenerator, String criterion, long randomseed) {
        this.schema = schema;
        this.mutants = mutants;
        this.testSuite = testSuite;
        this.dbms = dbms;
        this.databaseInteractor = databaseInteractor;
        this.useTransactions = useTransactions;
        this.dataGenerator = dataGenerator;
        this.criterion = criterion;
        this.randomseed = randomseed;
    }
    
    public abstract AnalysisResult analyse(TestSuiteResult originalResults);
    
}
