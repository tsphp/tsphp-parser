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
package ch.tutteli.tsphp.grammar.test.ast;

import ch.tutteli.tsphp.grammar.test.utils.AAstTest;
import java.util.ArrayList;
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
public class ArrayDeclarationTest extends AAstTest
{

    public ArrayDeclarationTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.add(new Object[]{"array $d = [];", "(variable declaration array $d array)"});
        collection.add(new Object[]{"array $d = array();", "(variable declaration array $d array)"});
        String[][] expressions = ExpressionTest.getExpressions();
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                        "array $d = [" + expression[0] + "];",
                        "(variable declaration array $d (array " + expression[1] + "))"
                    });
           collection.add(new Object[]{
                        "array $d = [" + expression[0] + ","+expression[0]+"];",
                        "(variable declaration array $d (array " + expression[1] + " "+expression[1]+"))"
                    });
            collection.add(new Object[]{
                        "array $d = [1 => " + expression[0] + ", $a=>" + expression[0] + "];",
                        "(variable declaration array $d (array (=> 1 " + expression[1] + ") (=> $a " + expression[1] + ")))"
                    });
            collection.add(new Object[]{
                        "array $d = array( 'a' => array(" + expression[0] + "), " + expression[0] + "=> 1, 2,3 );",
                        "(variable declaration array $d (array (=> 'a' (array " + expression[1] + ")) (=> " + expression[1] + " 1) 2 3))"
                    });


        }
        return collection;
    }
}