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
public class VariableDeclarationLexerErrorTest extends ALexerExceptionTest
{

    public VariableDeclarationLexerErrorTest(String testString, char evilCharacter, int atPosition) {
        super(testString, RecognitionException.class, (int) evilCharacter, atPosition);
    }

    @Test
    public void test() throws RecognitionException {
        super.parseExpectingException();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> variables() {
        return Arrays.asList(new Object[][]{
//                    {"int $1;", '1', 5},
//                    {"bool $2a;", '2', 6},
//                    {"boolean $3_;", '3', 9},
//                    {"float $4£;", '4', 7},
//                    {"string $5ééé;", '5', 8},
//                    {"resource $6AAAA;", '6', 10},
//                    {"array $7aA;", '7', 7},
//                    {"int $8_A;", '8', 5},
//                    {"bool $9££;", '9', 6},
//                    {"boolean $0a;", '0', 9},
//                    {"float $%;", '%', 7},
//                    {"string $(;", '(', 8},
//                    {"int $);", ')', 5},
//                    {"int $#;", '#', 5},
                    {"int a;", ';', 5},
                    {"int &a;", '&', 4}
                });
    }
}
