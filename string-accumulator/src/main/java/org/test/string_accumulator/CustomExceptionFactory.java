package org.test.string_accumulator;

import java.util.List;

public class CustomExceptionFactory{
	
	public static Exception generateNegativeInputException(List<String> negativeInputs) {
		String details = negativeInputs.toString();
		return new Exception(CustomExceptionMsg.NEGATIVES_NOT_ALLOWED + " : " + details);
	}
}
