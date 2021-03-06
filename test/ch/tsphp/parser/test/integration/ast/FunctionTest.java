/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.parser.test.integration.ast;

import ch.tsphp.parser.test.integration.testutils.AAstTest;
import ch.tsphp.parser.test.integration.testutils.ParameterListHelper;
import ch.tsphp.parser.test.integration.testutils.TypeHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class FunctionTest extends AAstTest
{

    public FunctionTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws Exception {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();

        collection.addAll(TypeHelper.getAllTypesWithModifier(
                "function ", " get(){}",
                "(function fMod ", " get() params block)", ""));

        collection.addAll(ParameterListHelper.getTestStrings(
                "function void set(", "){}",
                "(function fMod (type tMod void) set() ", " block)"));

        collection.add(new Object[]{
                    "function void foo(){$a=1; int $b=2;}",
                    "(function fMod (type tMod void) foo() params (block (expr (= $a 1)) (vars (type tMod int) ($b 2))))"
                });

        return collection;
    }
}
