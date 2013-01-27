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
package ch.tutteli.tsphp.grammar.test.ast;

import ch.tutteli.tsphp.grammar.test.utils.AAstTest;
import ch.tutteli.tsphp.grammar.test.utils.TypeHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Stoll <rstoll@tutteli.ch>
 */
@RunWith(Parameterized.class)
public class InterfaceTest extends AAstTest
{

    public InterfaceTest(String testString, String expectedResult) {
        super(testString, expectedResult);
    }

    @Test
    public void test() throws RecognitionException {
        compareAst();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.add(new Object[]{"interface a{}","(interface a implements interfaceBody)"});
        
        String[][] types = TypeHelper.getClassInterfaceTypes();
        for(String[] type:types){
            
            collection.add(new Object[]{
                "interface a implements "+type[0]+"{}",
                "(interface a (implements "+type[1]+") interfaceBody)"
            });
            collection.add(new Object[]{
                "interface a implements "+type[0]+","+type[0]+"{}",
                "(interface a (implements "+type[1]+" "+type[1]+") interfaceBody)"
            });
            collection.add(new Object[]{
                "interface a implements "+type[0]+","+type[0]+", "+type[0]+"{}",
                "(interface a (implements "+type[1]+" "+type[1]+" "+type[1]+") interfaceBody)"
            });
        }
        return collection;
    }
}