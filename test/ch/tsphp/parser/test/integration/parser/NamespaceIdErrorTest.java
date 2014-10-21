/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

/*
 * This class is based on the class NamespaceIdErrorTest from the TinsPHP project.
 * TinsPHP is also published under the Apache License 2.0
 * For more information see http://tsphp.ch/wiki/display/TINS/License
 */

package ch.tsphp.parser.test.integration.parser;

import ch.tsphp.parser.antlr.TSPHPParser;
import ch.tsphp.parser.test.integration.lexer.TokenTest;
import ch.tsphp.parser.test.integration.testutils.AParserParserExceptionTest;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class NamespaceIdErrorTest extends AParserParserExceptionTest
{

    public NamespaceIdErrorTest(String testString, int token, int position) {
        super(testString, token, position, RecognitionException.class);
    }

    @Test
    public void test() throws Exception {
        parseExpectingException();
    }

    @Override
    protected void run() throws RecognitionException {
        result = parser.namespaceId();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> vars() {
        List<Object[]> collection = new ArrayList<>();
        Collection<Object[]> idTestStrings = TokenTest.getIDTestStrings();
        for (Object[] obj : idTestStrings) {
            collection.add(new Object[]{"\\" + obj[1], TSPHPParser.Backslash, 0});
            collection.add(new Object[]{"\\" + obj[1] + "\\" + obj[1], TSPHPParser.Backslash, 0});
            collection.add(new Object[]{"\\" + obj[1] + "\\" + obj[1] + "\\" + obj[1], TSPHPParser.Backslash, 0});
        }
        return collection;
    }
}