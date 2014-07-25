package org.schemaanalyst.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	org.schemaanalyst.test.data.TestStringValue.class,
	org.schemaanalyst.test.data.TestValueEquality.class,
	org.schemaanalyst.test.data.generation.TestCellValueGenerator.class,
	org.schemaanalyst.test.data.generation.TestExpressionConstantMiner.class,
	org.schemaanalyst.test.data.generation.directedrandom.TestExpressionClauseFixer.class,
	org.schemaanalyst.test.data.generation.directedrandom.TestMatchClauseFixer.class,
	org.schemaanalyst.test.data.generation.directedrandom.TestNullClauseFixer.class,
	org.schemaanalyst.test.data.generation.directedrandom.TestPredicateFixer.class,
	org.schemaanalyst.test.data.generation.search.TestAlternatingValueSearch.class,
	org.schemaanalyst.test.data.generation.search.TestSearchEvaluation.class,
	org.schemaanalyst.test.data.generation.search.objective.TestDistanceObjectiveValue.class,
	org.schemaanalyst.test.data.generation.search.objective.TestObjectiveValue.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestAndExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestBetweenExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestInExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestNullExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestOrExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.TestRelationalExpressionRowObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.value.TestEqualsMultiValueObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.value.TestNullValueObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.value.TestRelationalBooleanValueObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.value.TestRelationalCompoundValueObjectiveFunction.class,
	org.schemaanalyst.test.data.generation.search.objective.row.value.TestRelationalNumericValueObjectiveFunction.class,
	org.schemaanalyst.test.faultlocalization.TestCalculator.class,
	org.schemaanalyst.test.faultlocalization.TestProcessMatrix.class,
	org.schemaanalyst.test.logic.TestRelationalOperator.class,
	org.schemaanalyst.test.logic.predicate.TestConstraintPredicateGenerator.class,
	org.schemaanalyst.test.logic.predicate.TestPredicate.class,
	org.schemaanalyst.test.logic.predicate.checker.TestExpressionChecker.class,
	org.schemaanalyst.test.logic.predicate.checker.TestMatchClauseChecker.class,
	org.schemaanalyst.test.logic.predicate.checker.TestNullClauseChecker.class,
	org.schemaanalyst.test.logic.predicate.checker.TestPredicateChecker.class,
	org.schemaanalyst.test.logic.predicate.checker.TestRelationalChecker.class,
	org.schemaanalyst.test.mutation.analysis.util.TestSchemaMerger.class,
	org.schemaanalyst.test.mutation.equivalence.TestChangedConstraintFinder.class,
	org.schemaanalyst.test.mutation.equivalence.TestChangedTableFinder.class,
	org.schemaanalyst.test.mutation.equivalence.TestCheckEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestColumnEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestForeignKeyEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestRedundantMutantChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestNotNullEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestPrimaryKeyEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestSchemaEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestSchemaEquivalenceWithNotNullCheckChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestTableEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.equivalence.TestUniqueEquivalenceChecker.class,
	org.schemaanalyst.test.mutation.mutator.TestListElementRemover.class,
	org.schemaanalyst.test.mutation.mutator.TestRelationalOperatorExchanger.class,
	org.schemaanalyst.test.mutation.operator.TestCCInExpressionRHSListExpressionElementR.class,
	org.schemaanalyst.test.mutation.operator.TestCCNullifier.class,
	org.schemaanalyst.test.mutation.operator.TestCCRelationalExpressionOperatorE.class,
	org.schemaanalyst.test.mutation.operator.TestFKCColumnPairA.class,
	org.schemaanalyst.test.mutation.operator.TestFKCColumnPairE.class,
	org.schemaanalyst.test.mutation.operator.TestFKCColumnPairR.class,
	org.schemaanalyst.test.mutation.operator.TestNNCAR.class,
	org.schemaanalyst.test.mutation.operator.TestPKCColumnARE.class,
	org.schemaanalyst.test.mutation.operator.TestUCColumnARE.class,
	org.schemaanalyst.test.mutation.reduction.TestNSelectiveRemover.class,
	org.schemaanalyst.test.mutation.reduction.TestPercentageSamplingRemover.class,
	org.schemaanalyst.test.mutation.reduction.TestSamplingRemover.class,
	org.schemaanalyst.test.mutation.redundancy.TestRedundancyRemovers.class,
	org.schemaanalyst.test.mutation.supplier.TestAbstractSupplier.class,
	org.schemaanalyst.test.mutation.supplier.TestIteratingSupplier.class,
	org.schemaanalyst.test.mutation.supplier.TestLinkedSupplier.class,
	org.schemaanalyst.test.mutation.supplier.TestSolitaryComponentSupplier.class,
	org.schemaanalyst.test.sqlrepresentation.TestColumn.class,
	org.schemaanalyst.test.sqlrepresentation.TestSchema.class,
	org.schemaanalyst.test.sqlrepresentation.TestTable.class,
	org.schemaanalyst.test.sqlrepresentation.TestTableDependencyOrderer.class,
	org.schemaanalyst.test.sqlrepresentation.constraint.TestConstraints.class,
	org.schemaanalyst.test.sqlrepresentation.datatype.TestDataTypes.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpression.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpressionFilterWalker.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpressionPath.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpressionTreeWithExpressionPath.class,
	org.schemaanalyst.test.sqlrepresentation.expression.TestExpressions.class,
	org.schemaanalyst.test.util.collection.TestIdentifiableEntity.class,
	org.schemaanalyst.test.util.collection.TestIdentifiableEntitySet.class,
	org.schemaanalyst.test.util.collection.TestIdentifier.class,
	org.schemaanalyst.test.util.runner.TestRunner.class,
	org.schemaanalyst.test.util.sql.TestSQLRepairer.class
})

public class AllTests {}

