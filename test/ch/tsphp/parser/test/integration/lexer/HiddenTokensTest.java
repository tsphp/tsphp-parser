/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.parser.test.integration.lexer;

import ch.tsphp.parser.antlr.TSPHPLexer;
import ch.tsphp.parser.test.integration.testutils.ALexerTest;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class HiddenTokensTest extends ALexerTest
{

    public HiddenTokensTest(String methodName, String testString, int type) {
        super(methodName, testString, type,TSPHPLexer.HIDDEN);
    }

    @Test
    public void testTokens() throws Exception {
        analyseAndCheckForError();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {"mComment", "//bla", TSPHPLexer.Comment},
                    {"mComment", "// bla \t !", TSPHPLexer.Comment},
                    {"mComment", "// bla \r\n", TSPHPLexer.Comment},
                    {"mComment", "// bla \n", TSPHPLexer.Comment},
                    {"mComment", "/* \n a \n bla bla */", TSPHPLexer.Comment},
                    {"mComment", "/** \n* a \n* bla bla \t **/", TSPHPLexer.Comment},
                    {"mWhitespace", " ", TSPHPLexer.Whitespace},
                    {"mWhitespace", "\n", TSPHPLexer.Whitespace},
                    {"mWhitespace", "\t", TSPHPLexer.Whitespace},
                    {"mWhitespace", "\r", TSPHPLexer.Whitespace},
                });
    }
}
