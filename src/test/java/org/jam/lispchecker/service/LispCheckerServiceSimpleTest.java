package org.jam.lispchecker.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LispCheckerServiceSimpleTest {
	// should pass
	private static final String basicString = "(a b c)";
	private static final String complexString = "(((abc cc)(ll d))(o p))";	
	
	// should fail
	private static final String emptyString = "";
	private static final String basicFail1 = "(()";
	private static final String basicFail2 = "())";
	private static final String basicFail3 = "(";
	private static final String basicFail4 = ")";
	
	private static LispCheckerService lispChecker;
	
	@Before
	public void init() {
		lispChecker = new LispCheckerServiceAdvanced(false);
	}
	
	@Test
	public void testBasicStringPass() {
		assertTrue(lispChecker.areLispParenthesesBalanced(basicString));
	}
	
	@Test
	public void testComplexStringPass() {
		assertTrue(lispChecker.areLispParenthesesBalanced(complexString));
	}
			
	@Test
	public void testEmptyStringFail() {
		assertFalse(lispChecker.areLispParenthesesBalanced(emptyString));
	}
	
	@Test
	public void testBasicFails() {
		assertFalse(lispChecker.areLispParenthesesBalanced(basicFail1));
		assertFalse(lispChecker.areLispParenthesesBalanced(basicFail2));
		assertFalse(lispChecker.areLispParenthesesBalanced(basicFail3));
		assertFalse(lispChecker.areLispParenthesesBalanced(basicFail4));
	}
}
