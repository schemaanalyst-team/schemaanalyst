package originalcasestudy;

import org.schemaanalyst.deprecated.sqlrepresentation.checkcondition.RelationalCheckCondition;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

public class StudentResidence extends Schema {

    private static final long serialVersionUID = -2821897632006616153L;

    public StudentResidence() {

        super("StudentResidence");

        /*
         CREATE TABLE Residence 
         (
         name VARCHAR(50) PRIMARY KEY NOT NULL,
         capacity INT NOT NULL,
         CHECK (capacity > 1),	
         CHECK (capacity <= 10)
         );
         */

        Table residenceTable = createTable("Residence");

        Column nameColumn = residenceTable.addColumn("name", new VarCharDataType(50));
        nameColumn.setPrimaryKey();
        nameColumn.setNotNull();
        Column capacityColumn = residenceTable.addColumn("capacity", new IntDataType());
        capacityColumn.setNotNull();

        residenceTable.addCheckConstraint(new RelationalCheckCondition(capacityColumn, ">", 1));
        residenceTable.addCheckConstraint(new RelationalCheckCondition(capacityColumn, "<=", 10));

        /*
         CREATE TABLE Student 
         ( 
         id INT PRIMARY KEY, 
         firstName VARCHAR(50), 
         lastName VARCHAR(50), 
         residence VARCHAR(50), 
         FOREIGN KEY(residence) REFERENCES Residence(name), 
         CHECK (id >= 0)	 
         );
         */

        Table studentTable = createTable("Student");

        Column idColumn = studentTable.addColumn("id", new IntDataType());
        idColumn.setPrimaryKey();

        studentTable.addColumn("firstName", new VarCharDataType(50));

        studentTable.addColumn("lastName", new VarCharDataType(50));

        Column residenceColumn = studentTable.addColumn("residence", new VarCharDataType(50));
        residenceColumn.setForeignKey(residenceTable, nameColumn);

        studentTable.addCheckConstraint(new RelationalCheckCondition(idColumn, ">=", 0));
    }
}