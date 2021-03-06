/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.parser.test.integration.parser.coverage;

import ch.tsphp.parser.antlr.TSPHPParser;
import ch.tsphp.parser.test.integration.testutils.AParserParserExceptionTest;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ConstructDestructModifierErrorTest extends AParserParserExceptionTest
{

    public ConstructDestructModifierErrorTest(String testString, int character, int position) {
        super(testString, character, position, NoViableAltException.class);
    }

    @Test
    public void test() throws Exception {
        parseExpectingException();
    }

    protected void run() throws RecognitionException {
        result = parser.constructDestructModifier();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                {"abstract", TSPHPParser.Abstract,0},
                {"final $notAnAccessModifier", TSPHPParser.VariableId, 6},
                {"public $notFinal", TSPHPParser.VariableId, 7},
                {"protected $notFinal", TSPHPParser.VariableId, 10},
                {"private $notFinal", TSPHPParser.VariableId, 8},
        });
    }
}

