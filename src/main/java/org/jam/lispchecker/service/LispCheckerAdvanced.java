package org.jam.lispchecker.service;

import java.util.Stack;

import org.jam.lispchecker.util.LispCheckerConstants;

/**
 * This class checks if the parentheses in a given LISP string are balanced
 * and nested properly.
 * 
 * Empty/null strings are not considered valid and will return false
 * 
 * Takes into account these special cases:
 * 	- comments
 * 	- escaped parenthesis
 * 	- special multicharacter symbols (e.g. everything between "|" characters
 * 	- parentheses in strings
 * 
 * @author Josh McQueen
 *
 */
public class LispCheckerAdvanced implements LispChecker {

	private boolean outputInfo = true;
	
	private Stack<String> leftParenthesesStack;
	private boolean escaping = false;
	private boolean inString = false;
	private boolean inMultipleSymbol = false;
	
	public LispCheckerAdvanced() {
		leftParenthesesStack = new Stack<>();
	}
	
	public LispCheckerAdvanced(boolean outputInfo) {
		leftParenthesesStack = new Stack<>();
		this.outputInfo = outputInfo;
	}	
	
	@Override
	public boolean areLispParenthesesBalanced(String inputLispString) {
		char currentChar = Character.MIN_VALUE;		
		
		leftParenthesesStack.clear();
		
		if(isLispStringEmpty(inputLispString)) {
			return false;
		}
		
		try {
			for(int charIndex = 0; charIndex < inputLispString.length(); charIndex++) {
				currentChar = inputLispString.charAt(charIndex);
				
				if(escaping) {
					escaping = false;
					continue;
				}
				
				if(!inMultipleSymbol && currentChar == LispCheckerConstants.stringChar) {
					if(inString) {
						inString = false; 
					} else {
						inString = true;
					}
					continue;
				}

				if(!inString && currentChar == LispCheckerConstants.multipleSymbolChar) {
					if(inMultipleSymbol) {
						inMultipleSymbol = false; 
					} else {
						inMultipleSymbol = true;
					}
					continue;
				}
				
				if(currentChar == LispCheckerConstants.escapeChar) {
					escaping = true;
					continue;
				}
				
				if(currentChar == LispCheckerConstants.commentChar) {
					break;
				}
				
				if(!inString && !inMultipleSymbol) {
					handleParenthesis(currentChar);
				}
			}
			
		} catch (Exception e) {
			outputInfo(e.getMessage());
			return false;
		}		
		
		if(!leftParenthesesStack.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	private boolean isLispStringEmpty(String inputLispString) {

		if(inputLispString == null) {
			outputInfo("No string passed");
			return true;
		} else if (inputLispString.length() == 0) {
			outputInfo("Empty string passed");
			return true;
		}
		
		return false;
	}
	
	private void handleParenthesis(char currentChar) throws Exception{
		switch(currentChar) {
		case LispCheckerConstants.leftParenthesis:
			// actual value doesn't matter, just used for tracking purposes
			leftParenthesesStack.push("("); 
			break;
		case LispCheckerConstants.rightParenthesis:
			if(leftParenthesesStack.isEmpty()) {
				throw new Exception("left parentheses stack is empty");
			} else {
				leftParenthesesStack.pop();
			}
		}		
	}

	private void outputInfo(String info) {
		if(outputInfo) {
			System.out.println(info);
		}
	}
}
