/*
 * Copyright 2013 Robert Stoll <rstoll@tutteli.ch>
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
package ch.tutteli.tsphp.parser.test.lexer;

import ch.tutteli.tsphp.parser.test.testutils.ALexerStateFailedTest;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class IntStateFailedTest extends ALexerStateFailedTest
{

    public IntStateFailedTest(String methodName,String testString) {
        //# is not valid for any token as first letter;
        super(methodName, testString, 0);
    }

    @Test
    public void testTokens() throws Exception {
        super.checkForStateFailed();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return IntErrorTest.testStrings();
    }
}