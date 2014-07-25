package org.schemaanalyst.test.testgeneration.coveragecritrerion.predicate;


import org.junit.Test;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirement;
import org.schemaanalyst.testgeneration.coveragecriterion.TestRequirements;
import org.schemaanalyst.testgeneration.coveragecriterion.predicate.*;
import parsedcasestudy.BrowserCookies;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 24/07/2014.
 */
public class TestComposedPredicate {

    Table table = new Table("tab");
    Column column1 = new Column("column1", new IntDataType());
    Column column2 = new Column("column2", new IntDataType());
    Column column3 = new Column("column3", new IntDataType());

    @Test
    public void testAddSamePredicate() {
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);

        AndPredicate andPredicate = new AndPredicate();
        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate1);
        andPredicate.addPredicate(nullPredicate2);

        assertEquals(1, andPredicate.getSubPredicates().size());
    }

    @Test
    public void testSingularComposedOneLevel() {
        NullPredicate nullPredicate = new NullPredicate(table, column1, true);

        AndPredicate andPredicate = new AndPredicate();
        andPredicate.addPredicate(nullPredicate);

        Predicate reduced = andPredicate.reduce();
        assertTrue(reduced.equals(nullPredicate));
    }

    @Test
    public void testSingularComposedTwoLevel() {
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate1 = new NullPredicate(table, column2, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate2);
        andPredicate1.addPredicate(andPredicate2);

        assertFalse(andPredicate1.contains(nullPredicate2));

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertTrue(reduced.contains(nullPredicate2));
    }

    @Test
    public void testMergeOneLevel() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);
        andPredicate1.addPredicate(andPredicate2);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 2, andPredicate2.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 4, reduced.numSubPredicates());
    }

    @Test
    public void testMergeOneLevelSameSubPredicates() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate1);
        andPredicate2.addPredicate(nullPredicate2);
        andPredicate1.addPredicate(andPredicate2);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 2, andPredicate2.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 2, reduced.numSubPredicates());
    }

    @Test
    public void testMergeTwoLevels() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);
        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);
        NullPredicate nullPredicate5 = new NullPredicate(table, column3, true);
        NullPredicate nullPredicate6 = new NullPredicate(table, column3, false);

        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);
        andPredicate1.addPredicate(andPredicate2);

        AndPredicate andPredicate3 = new AndPredicate();
        andPredicate3.addPredicate(nullPredicate5);
        andPredicate3.addPredicate(nullPredicate6);
        andPredicate2.addPredicate(andPredicate3);

        assertEquals("Num predicates in andPredicate1", 3, andPredicate1.numSubPredicates());
        assertEquals("Num predicates in andPredicate2", 3, andPredicate2.numSubPredicates());
        assertEquals("Num predicates in andPredicate3", 2, andPredicate3.numSubPredicates());

        AndPredicate reduced = (AndPredicate) andPredicate1.reduce();
        assertEquals("Num predicates in reduced", 6, reduced.numSubPredicates());
    }

    @Test
    public void testEquals() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        NullPredicate nullPredicate3 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column1, false);


        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);

        assertTrue(andPredicate1.equals(andPredicate2));
    }

    @Test
    public void testNotEquals() {
        NullPredicate nullPredicate1 = new NullPredicate(table, column1, true);
        NullPredicate nullPredicate2 = new NullPredicate(table, column1, false);

        NullPredicate nullPredicate3 = new NullPredicate(table, column2, true);
        NullPredicate nullPredicate4 = new NullPredicate(table, column2, false);


        AndPredicate andPredicate1 = new AndPredicate();
        andPredicate1.addPredicate(nullPredicate1);
        andPredicate1.addPredicate(nullPredicate2);

        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate3);
        andPredicate2.addPredicate(nullPredicate4);

        assertFalse(andPredicate1.equals(andPredicate2));
    }

    @Test
    public void testEqualsTwoLevels() {
        NullPredicate nullPredicate = new NullPredicate(table, column1, true);

        AndPredicate andPredicate1 = new AndPredicate();
        AndPredicate andPredicate2 = new AndPredicate();
        andPredicate2.addPredicate(nullPredicate);
        andPredicate1.addPredicate(andPredicate2);

        AndPredicate andPredicate3 = new AndPredicate();
        AndPredicate andPredicate4 = new AndPredicate();
        andPredicate4.addPredicate(nullPredicate);
        andPredicate3.addPredicate(andPredicate2);

        assertTrue(andPredicate1.equals(andPredicate3));
    }

    @Test
    public void testBrowserCookies() {

        List<TestRequirement> tr = CoverageCriterionFactory.integrityConstraintCoverageCriterion("aicc", new BrowserCookies()).generateRequirements().getTestRequirements();

        Predicate p1 = (tr.get(0).getPredicate().reduce());
        Predicate p2 = (tr.get(2).getPredicate().reduce());

        System.out.println(p1);
        System.out.println(p2);
        System.out.println();

        MatchPredicate mp1=null, mp2=null;
        for (Predicate subPredciate : ((ComposedPredicate) p1).getSubPredicates()) {
            if (subPredciate instanceof MatchPredicate) {
                mp1 = (MatchPredicate) subPredciate;
            }
        }
        for (Predicate subPredciate : ((ComposedPredicate) p2).getSubPredicates()) {
            if (subPredciate instanceof MatchPredicate) {
                mp2 = (MatchPredicate) subPredciate;
            }
        }

        System.out.println(mp1);
        System.out.println(mp2);

        System.out.println("equals?");

        System.out.println(mp1.equals(mp2));
        System.out.println(mp1.hashCode());
        System.out.println(mp2.hashCode());

        System.out.println();

        assertTrue(p1.equals(p2));

    }

}
