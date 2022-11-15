package org.jam.lispchecker;

import org.jam.lispchecker.service.LispCheckerService;
import org.jam.lispchecker.service.LispCheckerServiceSimple;

public class App {
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("Error: No LISP string passed");
		} else {
			LispCheckerService lispChecker = new LispCheckerServiceSimple();
			boolean isLispStringBalanced = lispChecker.areLispParenthesesBalanced(args[0]);
			System.out.println(String.format("Input string value: %s", args[0]));
			System.out.println(String.format(
				"Does input string have balanced parenthesis?: %b", isLispStringBalanced));
		}
	}
	
}
