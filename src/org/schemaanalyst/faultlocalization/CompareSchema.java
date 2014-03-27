package org.schemaanalyst.faultlocalization;

import java.util.List;

import javax.swing.SpringLayout.Constraints;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.constraint.UniqueConstraint;

public class CompareSchema {

	
	public void Compare(Schema o, Schema m){

		}
	public static CheckConstraint CompareCheckConstraints(Schema o, Schema m){
		Schema original = o;
		Schema mutated = m;
		List<Table> oTables = original.getTables();
		List<Table> mTables = mutated.getTables();
		for(int i =0; i < oTables.size(); i++){
			List<CheckConstraint> oChecks = original.getCheckConstraints(oTables.get(i));
			List<CheckConstraint> mChecks = mutated.getCheckConstraints(mTables.get(i));
			if(mChecks.size() < oChecks.size()){
				//this means one of the check constraints was set to null
				for(int j = 0; j < mChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						//if theyre the same do nothin!
					}else{
						return oChecks.get(j);
					}
				}
				int oCheckSize = oChecks.size();
				return oChecks.get(oCheckSize-1);
			}else{
				for(int j = 0; j < oChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						
					}else{
						return oChecks.get(j);
					}
				}
			}
			
			
		}
		return null;

	}
	public static ForeignKeyConstraint CompareForeignKeyConstraints(Schema o, Schema m){
		Schema original = o;
		Schema mutated = m;
		List<Table> oTables = original.getTables();
		List<Table> mTables = mutated.getTables();
		for(int i =0; i < oTables.size(); i++){
			List<ForeignKeyConstraint> oChecks = original.getForeignKeyConstraints(oTables.get(i));
			List<ForeignKeyConstraint> mChecks = mutated.getForeignKeyConstraints(mTables.get(i));
			if(mChecks.size() < oChecks.size()){
				//this means one of the check constraints was set to null
				for(int j = 0; j < mChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						//if theyre the same do nothin!
					}else{
						return oChecks.get(j);
					}
				}
				int oCheckSize = oChecks.size();
				return oChecks.get(oCheckSize-1);
			}else{
				for(int j = 0; j < oChecks.size(); j++){
					if(oChecks == null){
						return mChecks.get(j);
					}else{
						if(oChecks.get(j).equals(mChecks.get(j))){
							
						}else{
							return oChecks.get(j);
						}
					}
					
				}
			}
			
			
		}
		return null;
		
	}
	public static PrimaryKeyConstraint ComparePrimaryKeyConstraints(Schema o, Schema m){
		Schema original = o;
		Schema mutated = m;
		List<Table> oTables = original.getTables();
		List<Table> mTables = mutated.getTables();
		for(int i =0; i < oTables.size(); i++){
			PrimaryKeyConstraint oChecks = original.getPrimaryKeyConstraint(oTables.get(i));
			PrimaryKeyConstraint mChecks = mutated.getPrimaryKeyConstraint(mTables.get(i));
			if(oChecks == null){
				return mChecks;
			}else{
				if(oChecks.equals(mChecks)){

				}else{
					return oChecks;
				}
				
			}
			
		}
		System.out.println("PRIMARYKEYCONSTRAINT IS NULL");
		return null;
		
	}
	public static NotNullConstraint CompareNotNullConstraints(Schema o, Schema m){
		Schema original = o;
		Schema mutated = m;
		List<Table> oTables = original.getTables();
		List<Table> mTables = mutated.getTables();
		for(int i =0; i < oTables.size(); i++){
			List<NotNullConstraint> oChecks = original.getNotNullConstraints(oTables.get(i));
			List<NotNullConstraint> mChecks = mutated.getNotNullConstraints(mTables.get(i));
			if(mChecks.size() < oChecks.size()){
				//this means one of the check constraints was set to null
				for(int j = 0; j < mChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						//if theyre the same do nothin!
					}else{
						return oChecks.get(j);
					}
				}
				int oCheckSize = oChecks.size();
				return oChecks.get(oCheckSize-1);
			}
			if(mChecks.size() > oChecks.size()){
				//store the mutant
				int mCheckSize = mChecks.size();
				return mChecks.get(mCheckSize-1);
			}else{
				for(int j = 0; j < oChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						
					}else{
						return oChecks.get(j);
					}
				}
			}
			
			
		}
		return null;
	}
	public static UniqueConstraint CompareUniqueConstraints(Schema o, Schema m){
		Schema original = o;
		Schema mutated = m;
		List<Table> oTables = original.getTables();
		List<Table> mTables = mutated.getTables();
		for(int i =0; i < oTables.size(); i++){
			List<UniqueConstraint> oChecks = original.getUniqueConstraints(oTables.get(i));
			List<UniqueConstraint> mChecks = mutated.getUniqueConstraints(mTables.get(i));
			if(mChecks.size() < oChecks.size()){
				//this means one of the check constraints was set to null
				for(int j = 0; j < mChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						//if theyre the same do nothin!
					}else{
						return oChecks.get(j);
					}
				}
				int oCheckSize = oChecks.size();
				return oChecks.get(oCheckSize-1);
			}
			if(mChecks.size() > oChecks.size()){
				int mCheckSize = mChecks.size();
				return mChecks.get(mCheckSize-1);
			}
			else{
				for(int j = 0; j < oChecks.size(); j++){
					if(oChecks.get(j).equals(mChecks.get(j))){
						
					}else{
						return oChecks.get(j);
					}
				}
			}
			
			
		}
		return null;
	}
}
