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
package ch.tutteli.tsphp.grammar.test.lexer;

import ch.tutteli.tsphp.grammar.test.utils.ALexerTest;
import ch.tutteli.tsphp.grammar.test.utils.IdentifierHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import junit.framework.Assert;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class TokenExceptionTest extends ALexerTest
{

    public TokenExceptionTest(String methodName, String testString) {
        //# is not valid for any token as first letter;
        super(methodName, testString, 0);
    }

    @Test
    public void testTokens() throws Exception {
        try {
            isErrorReportingOn = false;
            analyseToken();
            Assert.fail(methodName + " - " + testString + " failed, no exception occured");
        } catch (InvocationTargetException ex) {
            if (!(ex.getTargetException() instanceof RecognitionException)) {
                System.err.printf(methodName + " - " + testString + " failed");
                ex.printStackTrace();
                Assert.fail(methodName + " - " + testString + " failed, an unexpected exception occured - see output");
            }
        }
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
            {"mBool","t#"},
            {"mBool","f#"},
            {"mIdentifier",IdentifierHelper.asciiToString('A'-1)},
            {"mIdentifier",IdentifierHelper.asciiToString('Z'+1)},
            {"mIdentifier",IdentifierHelper.asciiToString('a'-1)},
            {"mIdentifier",IdentifierHelper.asciiToString('z'+1)},
            {"mIdentifier",IdentifierHelper.asciiToString('_'-1)},
            {"mIdentifier",IdentifierHelper.asciiToString('_'+1)},
            {"mIdentifier",IdentifierHelper.asciiToString(126)},
            {"mIdentifier",IdentifierHelper.asciiToString(256)},          
            {"mVariableId","$"+IdentifierHelper.asciiToString('A'-1)},
        });
    }
}
