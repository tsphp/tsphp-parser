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

import ch.tutteli.tsphp.grammar.utils.AParserTest;
import java.util.List;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MissingTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
public class VariableDeclarationParserErrorTest extends AParserTest
{

    @Test
    public void testTypeMissing() throws RecognitionException {
        noErrorsOnOutput();
        testString = "$a;";
        super.parseExpectingException();
        check(EarlyExitException.class, TSPHPParser.VARID, 0);
    }

    @Test
    public void testMissingSemicolon() throws RecognitionException {
        noErrorsOnOutput();
        testString = "int $a int $b;";
        super.parseExpectingException();
        check(NoViableAltException.class, TSPHPParser.T_INT, 0);
    }

    @Test
    public void testMissingSemicolonEndOfFile() throws RecognitionException {
        noErrorsOnOutput();
        testString = "int $a";
        super.parseExpectingException();
        check(NoViableAltException.class, TSPHPParser.T_INT, 0);
    }

    private void check(Class<? extends RecognitionException> type, int token, int position) throws RecognitionException {

        Assert.assertTrue(lexer.getExceptions().isEmpty());

        List<RecognitionException> exceptions = parser.getExceptions();;
        Assert.assertFalse(exceptions.isEmpty());
        RecognitionException ex = exceptions.get(0);
        Assert.assertEquals(type, ex.getClass());
        Assert.assertEquals("position wrong", position, ex.charPositionInLine);
        Assert.assertEquals("token wrong", token, ex.c);

    }
}
