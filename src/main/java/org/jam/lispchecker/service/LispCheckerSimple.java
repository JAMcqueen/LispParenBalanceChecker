package org.jam.lispchecker.service;

import java.util.Stack;

import org.jam.lispchecker.util.LispCheckerConstants;

public class LispCheckerSimple implements LispChecker {

	private boolean outputInfo = true;
	
	private Stack<String> leftParenthesesStack;
	
	public LispCheckerSimple() {
		leftParenthesesStack = new Stack<>();
	}
	
	public LispCheckerSimple(boolean outputInfo) {
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
				handleParenthesis(currentChar);				
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
