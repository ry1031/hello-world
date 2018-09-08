package org.test.string_accumulator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for simple App.
 */
public class StringAccumulatorTest
{
    /**
     * Simple Cases
     * @throws Exception 
     */
    @Test
    public void add_SimpleCases() throws Exception
    {
        StringAccumulator app = new StringAccumulator();
        int result = app.add("");
        assertEquals(0, result);
        
        result = app.add("1");
        assertEquals(1, result);

        result = app.add("1,2");
        assertEquals(3, result);

        result = app.add("1\n2,3");
        assertEquals(6, result);

        result = app.add("1\n2,3,1000");
        assertEquals(1006, result);

        result = app.add("1\n2,3,1001");
        assertEquals(6, result);
    }
    
    /**
     * Cases with delimiters
     * @throws Exception 
     */
    @Test
    public void add_casesWithDelimiters() throws Exception
    {
        StringAccumulator app = new StringAccumulator();
        int result = app.add("//;\n1;2");
        assertEquals(3, result);
        
        result = app.add("//***\n1***2***3");
        assertEquals(6, result);
        
        result = app.add("//*|%\n1*2%3");
        assertEquals(6, result);
        
        result = app.add("//***|%%%\n1***2%%%3");
        assertEquals(6, result);
    }
    
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    /**
     * Negative input with exception
     * @throws Exception 
     */
    @Test
    public void add_WithNegativeInput_Exception() throws Exception
    {
        StringAccumulator app = new StringAccumulator();
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage(CustomExceptionMsg.NEGATIVES_NOT_ALLOWED);
        exceptionRule.expectMessage("-1");
        app.add("-1");
    }
    
    /**
     * Negative input with exception
     * @throws Exception 
     */
    @Test
    public void add_WithMultipleNegativeInput_Exception() throws Exception
    {
        StringAccumulator app = new StringAccumulator();
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage(CustomExceptionMsg.NEGATIVES_NOT_ALLOWED);
        exceptionRule.expectMessage("-1, -2");
        app.add("-1,-2");
    }
    
    /**
     * Negative input with exception
     * @throws Exception 
     */
    @Test
    public void add_WithMultipleNegativeInputWithDelimiters_Exception() throws Exception
    {
        StringAccumulator app = new StringAccumulator();
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage(CustomExceptionMsg.NEGATIVES_NOT_ALLOWED);
        exceptionRule.expectMessage("-1, -2");
        app.add("//---|***\n1----1***-2");
    }
    
    @Test
    public void extractNumbers_default() throws Exception
    {
    	StringAccumulator app = new StringAccumulator();
    	String[] result = app.extractNumbers("");
    	assertArrayEquals(new String[] { "" }, result);
    	
    	result = app.extractNumbers("1");
    	assertArrayEquals(new String[] { "1" }, result);
    	
    	result = app.extractNumbers("1,2");
    	assertArrayEquals(new String[] { "1", "2" }, result);
    	
    	result = app.extractNumbers("11,22");
    	assertArrayEquals(new String[] { "11", "22" }, result);
    	
    	result = app.extractNumbers("1\n2,3");
    	assertArrayEquals(new String[] { "1", "2", "3" }, result);
    }
    
    @Test
    public void extractNumbers_delimiters() throws Exception
    {
    	StringAccumulator app = new StringAccumulator();
    	String[] result = app.extractNumbers("//;\n1;2");
    	assertArrayEquals(new String[] { "1", "2" }, result);
    	
    	result = app.extractNumbers("//;\n11;22");
    	assertArrayEquals(new String[] { "11", "22" }, result);
    	
    	result = app.extractNumbers("//***\n1***2***3");
    	assertArrayEquals(new String[] { "1", "2", "3" }, result);
    	
    	result = app.extractNumbers("//*|%\n1*2%3");
    	assertArrayEquals(new String[] { "1", "2", "3" }, result);
    	
    	result = app.extractNumbers("//***|%%%\n1***2%%%3");
    	assertArrayEquals(new String[] { "1", "2", "3" }, result);
    }
    
    @Test
    public void escapeSpecialCharaters_SimpleCases() {
    	StringAccumulator app = new StringAccumulator();
    	String result = app.escapeSpecialCharaters("***|%%%");
    	assertEquals("\\*\\*\\*|%%%", result);
    	
    	result = app.escapeSpecialCharaters("...|+++|---");
    	assertEquals("\\.\\.\\.|\\+\\+\\+|\\-\\-\\-", result);
    }
}
