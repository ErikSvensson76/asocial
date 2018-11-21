package se.erik.socialboard.utils;

import java.util.function.UnaryOperator;

public class StringUtility {
	
	/**
	 * Makes name start with Upper case letter and the rest with lowercase
	 */
	public static UnaryOperator<String> nameify = s -> {
		s = s.trim();
		return s.length() > 2 ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : s.toUpperCase();				
	};
}
