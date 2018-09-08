package org.test.string_accumulator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class StringAccumulator 
{
	public static final String DEFAULT_DELIMITER = ",|\n";  
	
	private static final Logger logger = LoggerFactory.getLogger(StringAccumulator.class);
	
    public int add(String input) throws Exception
    {
    	int sum = 0;
    	
    	if(StringUtils.isEmpty(input)) {
    		return sum;
    	}
    	
    	String[] numberStrArray = extractNumbers(input);
    	List<String> negativeNumArray = new ArrayList<>();
    	for(String numberStr : numberStrArray) {
    		int num = Integer.valueOf(numberStr);
    		if(num < 0) {
    			negativeNumArray.add(numberStr);
    		} else if(num <= 1000) {
    			sum += num;
    		}
    	}
    	
    	if(negativeNumArray.size() > 0) {
    		Exception e = CustomExceptionFactory.generateNegativeInputException(negativeNumArray); 
    		logger.error(e.getMessage());
    		throw e;
    	}
    	
    	return sum;
    }
    
    public String[] extractNumbers(String input) {
    	String patterns = StringAccumulator.DEFAULT_DELIMITER;
    	String inputPart = input;
    	if (input.startsWith("//")) {
    		patterns = escapeSpecialCharaters(input.substring(2, input.indexOf('\n')));
    		inputPart = input.substring(input.indexOf('\n') + 1);
    	}
    	String[] inputStr = inputPart.split(patterns);
    	return inputStr;
    }
    
    public String escapeSpecialCharaters(String patterns) {
    	
    	// All regex characters except '|' separator
    	final String regExSpecialChars = "<([{\\^-=$!]})?*+.>";
    	final String regExSpecialCharsRE = regExSpecialChars.replaceAll( ".", "\\\\$0");
    	final Pattern reCharsREP = Pattern.compile( "[" + regExSpecialCharsRE + "]");
    	Matcher m = reCharsREP.matcher(patterns);
    	String str = m.replaceAll("\\\\$0");
    	return str;
    }
}
