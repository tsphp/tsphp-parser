/*
 * This file is part of the TSPHP project published under the Apache License 2.0
 * For the full copyright and license information, please have a look at LICENSE in the
 * root folder or visit the project's website http://tsphp.ch/wiki/display/TSPHP/License
 */

package ch.tsphp.parser.test.integration.ast;

import ch.tsphp.parser.test.integration.testutils.AAstTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BreakContinueTest extends AAstTest
{

    public BreakContinueTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws Exception {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        return Arrays.asList(new Object[][]{
                    {"while( true  ) break;", "(while true (cBlock break))"},
                    {"while( true  ) break 1;", "(while true (cBlock (break 1)))"},
                    {"while( true  ) continue;", "(while true (cBlock continue))"},
                    {"while( true  ) continue 1;", "(while true (cBlock (continue 1)))"}
                });
    }
}
