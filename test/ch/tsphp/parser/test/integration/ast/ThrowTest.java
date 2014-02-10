package ch.tsphp.parser.test.integration.ast;

import ch.tsphp.parser.test.integration.testutils.ExpressionHelper;
import ch.tsphp.parser.test.integration.testutils.AAstTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ThrowTest extends AAstTest
{

    public ThrowTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
         List<Object[]> collection = new ArrayList<>();
         List<String[]> expressions = ExpressionHelper.getAstExpressions();
         for(Object[] expression:expressions){
             collection.add(new Object[]{"throw "+expression[0]+";","(throw "+expression[1]+")"});
         }
         return collection;
    }
}