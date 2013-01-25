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
import java.util.Arrays;
import java.util.Collection;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class NamespaceTest extends AAstTest
{

    public NamespaceTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Override
    protected void run() throws RecognitionException {
        result = parser.prog();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {"namespace a; \n $a=1; $b=1;", "(namespace (type a) (block (= $a 1) (= $b 1)))"},
                    {"namespace a { \n $a=1; $b=1;}", "(namespace (type a) (block (= $a 1) (= $b 1)))"},
                    {
                        "namespace a; $a=1; namespace b; $b=1;",
                        "(namespace (type a) (block (= $a 1))) "
                        + "(namespace (type b) (block (= $b 1)))"
                    },
                    {
                        "namespace a { $a=1;} namespace b{ $b=1;}",
                        "(namespace (type a) (block (= $a 1))) "
                        + "(namespace (type b) (block (= $b 1)))"
                    },
                    {
                        "namespace a\\b; \n $a=1; $b=1;",
                        "(namespace (type a b) (block (= $a 1) (= $b 1)))"
                    },
                    {
                        "namespace a\\b\\c { \n $a=1; $b=1;}",
                        "(namespace (type a b c) (block (= $a 1) (= $b 1)))"
                    },
                    {
                        "namespace a\\b\\c; $a=1; namespace d\\e; $b=1;",
                        "(namespace (type a b c) (block (= $a 1))) "
                        + "(namespace (type d e) (block (= $b 1)))"
                    },
                    {
                        "namespace a\\b { $a=1;} namespace c\\d\\e{ $b=1;}",
                        "(namespace (type a b) (block (= $a 1))) "
                        + "(namespace (type c d e) (block (= $b 1)))"
                    },
                    //default
                    {"namespace{$a=1;}", "(namespace (type default) (block (= $a 1)))"},
                    //without namespace
                    {"$a=1;", "(namespace (type default) (block (= $a 1)))"}
                });
    }
}
