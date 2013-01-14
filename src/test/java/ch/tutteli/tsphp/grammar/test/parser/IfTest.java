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
package ch.tutteli.tsphp.grammar.test.parser;

import ch.tutteli.tsphp.grammar.test.utils.AParserTest;
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
public class IfTest extends AParserTest
{

    public IfTest(String testString) {
        super(testString);
    }

    @Test
    public void test() throws RecognitionException {
        parseAndCheckForException();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {"if($a) int $b=1; else int $b=2;"},
                    {"if($a){ int $b=1;} else int $b=2;"},
                    {"if($a) int $b=1; else{ int $b=2;}"},
                    {"if($a){ int $b=1;} else{ int $b=2;}"},
                    {"if($a){ int $b=1; $b=1;} else{ int $b=2; $b=1;}"},
                    {"if($a){ $b=1; $b=2;} else{ int $b=2; $b=1;}"},
                    {"if($a){ $b=1; $b=2;} else if ($a) int $b=1;"},
                    {"if($a){ $b=1; $b=2;} else if ($a){int $b=1;}"},
                    {"if($a) $b=1; else if ($a){int $b=1;} else $b=2;"},
                    {"if($a){ if($b){ $b=2;}} else if ($a){int $b=1;} else $b=2;"},
                    {"if($a){ $c=2;} else { if($a) $c=3; else if($a){ $b=2;}else $d=0;}"},
                });
    }
}
