package ch.tsphp.parser.test.integration.ast;

import ch.tsphp.parser.test.integration.testutils.AAstTest;
import ch.tsphp.parser.test.integration.testutils.ExpressionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class NewClassTest extends AAstTest
{

    public NewClassTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                    {"new Type;", "(expr (new Type args))"},
                    {"new Type();", "(expr (new Type args))"}
                }));
        List<String[]> expressions = ExpressionHelper.getAstExpressions();
        for (Object[] expression : expressions) {
            collection.add(new Object[]{
                "new Type(" + expression[0] + ");",
                "(expr (new Type (args " + expression[1] + ")))"
            });
            collection.add(new Object[]{
                        "new Type(" + expression[0] + "," + expression[0] + ");",
                        "(expr (new Type (args " + expression[1] + " " + expression[1] + ")))"
                    });
            collection.add(new Object[]{
                        "new Type(" + expression[0] + "," + expression[0] + "," + expression[0] + ");",
                        "(expr (new Type (args " + expression[1] + " " + expression[1] + " " + expression[1] + ")))"
                    });
        }
        return collection;
    }
}