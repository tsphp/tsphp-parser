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
package ch.tutteli.tsphp.parser.test.ast;

import static ch.tutteli.tsphp.parser.antlr.TSPHPParser.*;
import ch.tutteli.tsphp.parser.test.testutils.AAstTokenTest;
import static ch.tutteli.tsphp.parser.test.testutils.AstHelper.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class NamespaceTokenTest extends AAstTokenTest
{

    public NamespaceTokenTest(String testString, List<Integer> expectedTokens) {
        super(testString, expectedTokens);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Override
    protected void run() throws RecognitionException {
        result = parser.compilationUnit();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {"namespace a; ", Arrays.asList(new Integer[]{DOWN, Namespace, TYPE_NAME, NAMESPACE_BODY, UP})},
                    {"namespace a {}", Arrays.asList(new Integer[]{DOWN, Namespace, TYPE_NAME, NAMESPACE_BODY, UP})},
                    //default
                    {
                        "namespace{}",
                        Arrays.asList(new Integer[]{DOWN, Namespace, DEFAULT_NAMESPACE, NAMESPACE_BODY, UP})
                    },
                    //without namespace
                    {
                        "$a=1;",
                        Arrays.asList(new Integer[]{
                            DOWN, Namespace, DEFAULT_NAMESPACE, DOWN, NAMESPACE_BODY,
                            DOWN, EXPRESSION, DOWN, Assign, VariableId, Int, UP, UP,
                            UP, UP
                        })
                    },});
    }
}
