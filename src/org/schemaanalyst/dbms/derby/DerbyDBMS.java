package org.schemaanalyst.dbms.derby;

import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.DBMSVisitor;
import org.schemaanalyst.sqlwriter.SQLWriter;

/**
 * <p>
 * Contains the various objects relating to interacting with a Derby DBMS.
 * </p>
 */
public class DerbyDBMS extends DBMS {

    private SQLWriter sqlWriter = new DerbySQLWriter();
    private DerbyDatabaseInteractor databaseInteractor;
    private boolean useNetwork;
    
    public DerbyDBMS() {
    	this(false);
    }
    
    public DerbyDBMS(boolean useNetwork) {
    	this.useNetwork = useNetwork;
    }
    
    @Override
    public String getName() {
    	return "Derby";
    }      
    
    @Override
    public SQLWriter getSQLWriter() {
        return sqlWriter;
    }
    
        @Override
    public DatabaseInteractor getDatabaseInteractor(String databaseName, DatabaseConfiguration databaseConfiguration, LocationsConfiguration locationConfiguration) {
        if (databaseInteractor == null) {
        	return useNetwork 
        			? new DerbyNetworkDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration)
        			: new DerbyDatabaseInteractor(databaseName, databaseConfiguration, locationConfiguration);
        }
        return null;
    }

    @Override
    public void accept(DBMSVisitor visitor) {
        visitor.visit(this);
    }
}
