/*
 * Copyright 2012 Robert Stoll <rstoll@tutteli.ch>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.tutteli.tsphp.grammar.utils;

import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@Ignore
public abstract class AParserExceptionTest extends AParserTest
{

    protected int position;
    protected int token;
    protected Class<? extends RecognitionException> exceptionType;

    public AParserExceptionTest() {
    }

    public AParserExceptionTest(String testString, Class<? extends RecognitionException> type, int aToken, int aPosition) {
        super(testString);
        noErrorsOnOutput();
        position = aPosition;
        token = aToken;
        exceptionType = type;
    }

    public void parseExpectingException() throws RecognitionException {
        super.parse(testString);

        Assert.assertTrue(testString + " - lexer.exceptions is not empty ",lexer.getExceptions().isEmpty());

        List<RecognitionException> exceptions = parser.getExceptions();;
        Assert.assertFalse(testString + " - exceptions is empty ",exceptions.isEmpty());
        RecognitionException ex = exceptions.get(0);
        Assert.assertEquals(testString + " - wrong type", exceptionType, ex.getClass());
        Assert.assertEquals("position wrong", position, ex.charPositionInLine);
        Assert.assertEquals("token wrong", token, ex.c);

    }
}
