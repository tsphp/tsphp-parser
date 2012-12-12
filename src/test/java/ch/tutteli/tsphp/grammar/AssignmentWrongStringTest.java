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
package ch.tutteli.tsphp.grammar;

import ch.tutteli.tsphp.grammar.utils.ALexerExceptionTest;
import ch.tutteli.tsphp.grammar.utils.AParserTest;
import ch.tutteli.tsphp.grammar.utils.IdentifierHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class AssignmentWrongStringTest extends ALexerExceptionTest
{

    public AssignmentWrongStringTest(String testString, int character, int position) {
        super(testString, character, position);

    }

    @Test
    public void test() throws RecognitionException {
        super.parseExpectingException();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    //testStringSingleQuotedAssignment
                    {"string $a = ''';", -1, 16},
                    //testStringDoubleQuotedAssignment
                    {"string $a = \" \" \";", -1, 18},
                    // single $ are allowed in PHP but not in TSPHP as well
                    {"string $a = \" $ \";", '$', 14},
                    // $0 cannot be a variable and is therefore allowed in PHP, but not in TSPHP (so far)
                    {"string $a = \" $0123456789 \";", '$', 14}
                });
    }
}
