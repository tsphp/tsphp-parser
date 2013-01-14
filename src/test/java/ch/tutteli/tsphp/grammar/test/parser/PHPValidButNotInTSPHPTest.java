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
package ch.tutteli.tsphp.grammar.test.parser;

import ch.tutteli.tsphp.grammar.TSPHPParser;
import ch.tutteli.tsphp.grammar.test.utils.AParserParserExceptionTest;
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
public class PHPValidButNotInTSPHPTest extends AParserParserExceptionTest
{

    public PHPValidButNotInTSPHPTest(String testString, int character, int position) {
        super(testString, RecognitionException.class, character, position);

    }

    @Test
    public void test() throws RecognitionException {
        super.parseExpectingException();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testStrings() {
        List<Object[]> collection = new ArrayList<>();
        collection.addAll(Arrays.asList(new Object[][]{
                    //empty statement
                    {";", TSPHPParser.Semicolon, 0},
                    //statment without assignment
                    {"$a;", TSPHPParser.Semicolon, 2},
                    //empty block
                    {"{}", TSPHPParser.RightCurlyBrace, 1},
                    {"{{}}", TSPHPParser.RightCurlyBrace, 2},
                    //empty if or else blocks
                    {"if($a){}", TSPHPParser.RightCurlyBrace, 7},
                    {"if($a);", TSPHPParser.Semicolon, 6},
                    {"if($a)$a=1; else;", TSPHPParser.Else, 12},
                    {"if($a)$a=1; else{}", TSPHPParser.Else, 12},
                    {"if($a){$a=1;} else;", TSPHPParser.Else, 14},
                    {"if($a){$a=1;} else{}", TSPHPParser.Else, 14},
                    {"if($a){$a=1;} else if($a);", TSPHPParser.Else, 14},
                    {"if($a){$a=1;} else if($a){}", TSPHPParser.Else, 14},
                    {"if($a){$a=1;} else if($a){$a=1;} else;", TSPHPParser.Else, 33},
                    {"if($a){$a=1;} else if($a){$a=1;} else{}", TSPHPParser.Else, 33},
                    //empty for block
                    {"for(;;){}", TSPHPParser.RightCurlyBrace, 8},
                    {"for(;;);", TSPHPParser.Semicolon, 7},
                    //for with declaration without assignment
                    {"for($a;;){$a=1;}",TSPHPParser.Semicolon,6},
                    {"for($a=1,$b;;){$a=1;}",TSPHPParser.Semicolon,11},
                    {"for(;;$a){$a=1;}",TSPHPParser.RightParanthesis,8},
                    {"for(;;$a++,$b){$a=1;}",TSPHPParser.RightParanthesis,13},
                    //for with unusefull expressions
                    {"for($a=1,1+1-2;;){$a=1;}",TSPHPParser.Int,9},
                    {"for($a=1;;1+1-2){$a=1;}",TSPHPParser.Int,10},
                    
                    //empty foreach block
                    {"foreach($a as $k);", TSPHPParser.Semicolon, 17},
                    {"foreach($a as $k){}", TSPHPParser.RightCurlyBrace, 18},
                    {"foreach($a as $k => $v);", TSPHPParser.Semicolon, 23},
                    {"foreach($a as $k => $v){}", TSPHPParser.RightCurlyBrace, 24},
                    //empty while block
                    {"while(true){}", TSPHPParser.RightCurlyBrace, 12},
                    {"while(true);", TSPHPParser.Semicolon, 11},
                    //empty do while block
                    {"do ; while(true);", TSPHPParser.Semicolon, 3},
                    {"do{}while(true);", TSPHPParser.RightCurlyBrace, 3},
                    //empty switch block
                    {"switch($a){}", TSPHPParser.RightCurlyBrace, 11},
                    //switch with case label but without command => same as an empty block
                    {"switch($a){case 1:}", TSPHPParser.RightCurlyBrace, 18},
                    //switch with case labels but without command => same as an empty block
                    {"switch($a){case 1: case 2:}", TSPHPParser.RightCurlyBrace, 26},
                    //switch case label without command
                    {"switch($a){case 1: $a=1; case 2:}", TSPHPParser.RightCurlyBrace, 32},
                    //switch case label without command
                    {"switch($a){case 1: $a=1; case 2: case 3:}", TSPHPParser.RightCurlyBrace, 40},
                    //switch default label without command
                    {"switch($a){case 1: $a=1; default:}", TSPHPParser.RightCurlyBrace, 33},
                    //switch only with default block
                    {"switch($a){default: $a=1;}", TSPHPParser.Default, 11},
                    //switch with case label do nothing more than default
                    {"switch($a){ case 1: default: $a=1;}", TSPHPParser.Default, 20},
                    //switch with case labels do nothing more than default
                    {"switch($a){ case 1: case 2: default: $a=1;}", TSPHPParser.Default, 28},
                    //switch with case labels do nothing more than default
                    {"switch($a){ case 0: $a=1; case 1: case 2: default: $a=1;}", TSPHPParser.Default, 42},
                    //switch with multiple default blocks
                    {"switch($a){case 1: default: $a=1; break; case 2: default: $a=2; break;}", TSPHPParser.Default, 19},
                    //function with pseudo optional parameter
                    {"function a ($a,$b=1,$c){$a=1;}",TSPHPParser.LeftParanthesis,11},
                    //empty try block
                    {"try{}catch(\\Exception $e){}",TSPHPParser.RightCurlyBrace,4},
                    //empty class    
                    {"class a{}", TSPHPParser.RightCurlyBrace, 8},
                    //empty namespace
                    {"namespace a;",-1, 12},
                    {"namespace a{}",TSPHPParser.RightCurlyBrace, 12},
                    {"namespace {}",TSPHPParser.RightCurlyBrace, 11},
                    //use outside of semicolon namespace
                    {"use a\\a; namespace a;",TSPHPParser.Namespace, 9},
                    //clone a new instance directly
                    {"Foo $foo = clone new Foo();",TSPHPParser.VariableId,4},
                    //clone a return value directly
                    {"Foo $foo = clone createFoo();",TSPHPParser.VariableId,4},
                    //clone without assignment
                    {"clone $foo;", TSPHPParser.Clone,0},
                }));
        //expressions without assignments - see ExpressionWithoutAssignmentTest
        return collection;
    }
}
