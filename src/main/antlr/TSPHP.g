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
grammar TSPHP;

options {
	backtrack = true; 
	memoize=true;
	output=AST;
	ASTLabelType=CommonTree;
}

tokens{
	Abstract = 'abstract';
	Arrow = '=>';
	As = 'as';
	Assign = '=';
	At = '@';
	Backslash = '\\';
	BitwiseAnd = '&';
	BitwiseAndEqual = '&=';
	BitwiseNot = '~';
	BitwiseOr = '|';
	BitwiseOrEqual = '|=';
	BitwiseXor = '^';
	BitwiseXorEqual = '^=';
	Break = 'break';
	Case = 'case';
	Catch = 'catch';
	Class = 'class';
	Clone = 'clone';
	Colon = ':';
	Comma = ',';
	Const = 'const';
	Continue = 'continue';
	Default = 'default';
	Divide = '/';
	DivideEqual = '/=';
	Do = 'do';
	Dolar = '$';
	Dot = '.';
	DotEqual = '.=';
	DoubleColon = '::';
	Echo = 'echo';
	Else = 'else';
	Equal = '==';	
	Exit = 'exit';
	Extends = 'extends';
	Final = 'final';
	For = 'for';
	Foreach = 'foreach';
	Function = 'function';
	GreaterThan = '>';
	GreaterEqualThan = '>=';
	Identical = '===';
	If = 'if';
	Implements = 'implements';
	Instanceof = 'instanceof';
	Interface = 'interface';
	LeftCurlyBrace = '{';
	LeftParanthesis = '(';
	LeftSquareBrace = '[';
	LessThan = '<';
	LessEqualThan = '<=';
	LogicAnd = '&&';
	LogicAndWeak = 'and';
	LogicNot = '!';
	LogicOr = '||';
	LogicOrWeak = 'or';
	LogicXorWeak = 'xor';
	Minus = '-';
	MinusEqual = '-=';
	MinusMinus = '--';
	Modulo = '%';
	ModuloEqual = '%=';
	Multiply = '*';
	MultiplyEqual = '*=';
	Namespace = 'namespace';
	New = 'new';
	NotEqual = '!=';
	NotEqualAlternative = '<>';
	NotIdentical = '!==';
	ObjectOperator = '->';
	Parent = 'parent::';
	Plus = '+';
	PlusEqual = '+=';
	PlusPlus = '++';
	Private = 'private';
	Protected = 'protected';
	ProtectParent = 'parent';
	ProtectSelf = 'self';
	ProtectThis = 'this';
	Public = 'public';
	QuestionMark = '?';
	Return = 'return';
	RightCurlyBrace = '}';
	RightParanthesis =')';
	RightSquareBrace = ']';
	ShiftLeft = '<<';
	ShiftLeftEqual = '<<=';
	ShiftRight = '>>';
	ShiftRightEqual = '>>=';
	Static = 'static';
	This = '$this';
	Throw = 'throw';
	Try = 'try';
	TypeBool = 'bool';
	TypeBoolean = 'boolean';
	TypeInt = 'int';
	TypeFloat = 'float';
	TypeString = 'string';
	TypeArray = 'array';
	TypeResource = 'resource';
	TypeObject = 'object';
	Self = 'self::';
	Semicolon = ';';
	Switch = 'switch';
	Use = 'use';
	Void = 'void';
	While = 'while';

	// Imaginary tokens
	BLOCK;
	CAST;
	EXPRESSION_LIST;
	POST_INCREMENT_DECREMENT;
	PRE_INCREMENT_DECREMENT;
	SWITCH_GROUP;
	SWITCH_CASES;
	VARIABLE_DECLARATION;
	VARIABLE_DECLARATION_LIST;
	UMINUS;
	UPLUS;
	
}


@header{
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
}

@lexer::header{
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
}

prog	:	namespaceSemicolon+ EOF!
	|	namespaceBracket+ EOF!
	|	withoutNamespace EOF!
	;
	
namespaceSemicolon
	:	('namespace' namespaceId ';' statement+ );

namespaceBracket
	:	('namespace' namespaceId? '{' statement+ '}');

//Must before Id otherwise Id match true and false
Bool	:	'true'|'false';

Null	:	('N'|'n') ('U'|'u') ('L'|'l') ('L'|'l');

Identifier	
	:	('a'..'z'|'A'..'Z'|'_'|'\u007f'..'\u00ff') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'\u007f'..'\u00ff')*;

namespaceId
	:	Identifier ('\\' Identifier)*;


withoutNamespace 
	:	statement+;

statement	
	:	useDeclarationList
	|	definition
	|	instructionWithoutBreakContinue
	;
	
useDeclarationList
	:	'use' useDeclaration (',' useDeclaration)* ';';
	
useDeclaration
	:	( (Identifier '\\' namespaceId)
			| ('\\'namespaceId)
			) 
		('as' Identifier)? 
	;
definition
	:	classDeclaration
	|	interfaceDeclaration
	|	functionDeclaration
	|	constDeclarationList
	;

/************* OOP related  - many definitions rely on procedural definitions *********************/
//************************************************************************************************/

classDeclaration
	:	('abstract'|'final')? 'class' Identifier extendsDeclaration? implementsDeclaration? '{' classBody+ '}'	
	;
	
extendsDeclaration	
	:	'extends' identifierList;

identifierList
	:	Identifier (',' Identifier)*;

implementsDeclaration
	:	'implements' identifierList;
	
classBody
	:	constDeclarationList
	|	attributeDeclaration	
	|	methodDeclaration
	;

constDeclarationList
	:	 'const' primitiveTypes constantAssignment (',' constantAssignment)* ';';
	
constantAssignment
	:	Identifier  '=' unaryPrimitiveAtom;

attributeDeclaration	
	:	'static'? accessor variableDeclarationListInclVariableId ';';

accessor:	accessorWithoutPrivate
	|	'private'
	;
	
accessorWithoutPrivate
	:	'protected'|'public';

variableDeclarationListInclVariableId
	:	variableDeclaration (',' (variableAssignment|VariableId) )*;


methodDeclaration	
	:	(	'abstract' accessorWithoutPrivate?
		| 	
			(	'static' 'final'?
			|	'final' 'static'?
			|	//empty
			) accessor?
		) functionDeclaration;	

interfaceDeclaration
	:	'interface' Identifier implementsDeclaration? '{' interfaceBody* '}';

interfaceBody
	:	constDeclarationList
	|	interfaceMethodDeclaration
	;

interfaceMethodDeclaration
	:	'public'? functionDeclarationWithoutBody ';';



/******* Procedural related - is also be used by OOP ************/
//***************************************************************/
functionDeclaration	
	:	 functionDeclarationWithoutBody '{' instructionWithoutBreakContinue* '}';
	
functionDeclarationWithoutBody
	:	'function' returnType Identifier '(' paramList? ')';
	
returnType
	:	allTypes | 'void';

allTypes:	primitiveTypesExtended | classInterfaceTypeInclObject;

allTypesWithoutResource
	:	primitiveTypesInclArray
	| 	classInterfaceTypeInclObject
	;

primitiveTypes
	:	TypeBool
	|	bool = TypeBoolean -> TypeBool[$bool,"bool"]
	|	TypeInt
	|	TypeFloat
	|	TypeString
	;
	
primitiveTypesInclArray
	:	primitiveTypes
	|	TypeArray
	;
	
primitiveTypesExtended
	:	primitiveTypesInclArray
	|	TypeResource
	;

classInterfaceTypeWithoutObject
		//namespaceId already contains Identifier at the end
	:	'\\'? namespaceId 
	;
	
classInterfaceTypeInclObject
	:	TypeObject	
	|	classInterfaceTypeWithoutObject
	;
	
paramList
	:	paramDeclarationOptional (',' paramDeclarationOptional)*
	|	paramDeclaration (',' paramDeclaration)*
	|	paramDeclaration (paramDeclaration ',')* (',' paramDeclarationOptional)+
	;
	
paramDeclaration
	:	allTypes VariableId;
	
paramDeclarationOptional
	:	paramDeclaration  '=' unaryPrimitiveAtom;

VariableId	
	:	'$' Identifier;

instructionWithoutBreakContinue	
	:	block = '{' instructionWithoutBreakContinue+  '}' -> ^(BLOCK[$block,"block"] instructionWithoutBreakContinue+)
	|	instruction
	;

instructionInclBreakContinue
	:	block =  '{' instructionInclBreakContinue+  '}' -> ^(BLOCK[$block,"block"] instructionInclBreakContinue+)
	|	instruction
	|	('break'|'continue')^ Int? ';'!
	;	

instruction
	:	variableAssignment ';'!
	|	variableDeclaration ';'!
	|	ifCondition
	|	switchCondition
	|	forLoop
	|	foreachLoop
	|	whileLoop
	|	doWhileLoop
	|	tryCatch
	|	throwException
	|	functionCallFluentWithoutAccessAtTheEnd ';'!
	|	methodCallFluentWithoutAccessAtTheEnd ';'!	
	|	'return' expression? ';'!
	|	'echo'^ expression (','! expression)* ';'!
	|	'exit' ';'!
	;
	
expressionList
	:	expression (',' expression)* -> ^(EXPRESSION_LIST expression+)
	|	-> EXPRESSION_LIST
	;

variableAssignment
	:	VariableId assignmentOperator^ expression
	|	incrementDecrement
	;
	
incrementDecrement
	:	postIncrementDecrement
	|	preIncrementDecrement
	;

assignmentOperator
	:	'='
	|	'+='
	|	'-='
	|	'*='
	|	'/='
	|	'&='
	|	'|='
	|	'^='
	|	'%='
	|	'.='
	|	'<<='
	|	'>>='
	;
	
postIncrementDecrement 
	:	variableMemberStaticMember plusPlusOrMinusMinus -> ^(POST_INCREMENT_DECREMENT plusPlusOrMinusMinus variableMemberStaticMember)
	;
	
plusPlusOrMinusMinus
	:	'++'
	|	'--'
	;
	
preIncrementDecrement
	:	plusPlusOrMinusMinus variableMemberStaticMember -> ^(PRE_INCREMENT_DECREMENT plusPlusOrMinusMinus variableMemberStaticMember)
	;
	
	
variableDeclaration
	:	 allTypes VariableId ('=' expression)? -> ^(VARIABLE_DECLARATION[$allTypes.start,"variable declaration"] allTypes VariableId expression?) ;

expression
	:	logicOrWeak;

expressionForTest
	:	expression ';'!;

logicOrWeak
	:	logicXorWeak ('or'^ logicXorWeak)*; 
	
logicXorWeak
	:	logicAndWeak ('xor'^ logicAndWeak)*; 
	
logicAndWeak
	:	assignment ('and'^ assignment)*;

assignment
	:	ternary (assignmentOperator^ ternary)*;

ternary	:	logicOr ('?'^ expression ':'! expression)?;

logicOr	:	logicAnd ('||'^ logicAnd)*;

logicAnd:	bitwiseOr ('&&'^ bitwiseOr)*;

bitwiseOr
	:	bitwiseXor ('|'^ bitwiseXor)*;

bitwiseXor
	:	bitwiseAnd ('^'^ bitwiseAnd)*;

bitwiseAnd
	:	equality ('&'^ equality)*;

equality:	comparison (equalityOperator^ comparison)?;

equalityOperator
	:	'=='
	|	'==='
	|	'!='
	|	'!=='
	|	'<>'
	;

comparison
	:	bitwiseShift (comparisonOperator^ bitwiseShift)?;

comparisonOperator
	:	'<'
	|	'<='
	|	'>'
	|	'>='
	;

bitwiseShift	:	termOrStringConcatenation (('<<'|'>>')^ termOrStringConcatenation)*;

termOrStringConcatenation	:	factor (('+'|'-'|'.')^ factor)*;

factor	:	logicNot (('*'|'/'|'%')^ logicNot)*;

logicNot:	'!'^ logicNot
	|	instanceOf
	;

instanceOf
	:	castOrBitwiseNotOrAt ('instanceof'^ (classInterfaceTypeWithoutObject|VariableId))?;

castOrBitwiseNotOrAt
	:	cast = '(' allTypesWithoutResource ')' castOrBitwiseNotOrAt -> ^(CAST[$cast,"cast"] allTypesWithoutResource castOrBitwiseNotOrAt)
	|	'~'^ castOrBitwiseNotOrAt
	|	'@'^ castOrBitwiseNotOrAt 
	|	newOrClone
	;

newOrClone
	:	'clone'^ variableMemberStaticMember
	|	newObject
	|	unaryPrimary
	;

	
variableMemberStaticMember
	:	staticAccessOrParent VariableId	memberAccessOrArrayAccess*
	|	'$this' memberAccessOrArrayAccess+
	|	VariableId memberAccessOrArrayAccess+
	|	'$this'
	|	VariableId
	;

staticAccessOrParent
	:	'self::'
	|	'parent::'
	|	classInterfaceTypeWithoutObject'::'
	;

memberAccessOrArrayAccess
	:	memberAccess
	|	arrayAccess
	;
memberAccess
	:	'->' Identifier
	;
arrayAccess
	:	'[' expression ']'
	;
	

newObject
	:	'new' classInterfaceTypeWithoutObject '(' expressionList ')' -> ^('new' classInterfaceTypeWithoutObject expressionList)
	|	'new' classInterfaceTypeWithoutObject -> ^('new' classInterfaceTypeWithoutObject EXPRESSION_LIST)
	;

unaryPrimary
	:	uplus = '+' primary -> ^(UPLUS[$uplus,"unary plus"] primary)
	|	uminus = '-' primary -> ^(UMINUS[$uminus,"unary minus"] primary)
	|	primary
	;
primary
	:	functionCallFluentInclAccessAtTheEnd
	|	methodCallFluentInclAccessAtTheEnd
	|	atom
	;	

functionCallFluentInclAccessAtTheEnd
	:	functionCallFluentWithoutAccessAtTheEnd memberAccessOrArrayAccess?
	;

functionCallFluentWithoutAccessAtTheEnd
	:	functionCall callOrAccess* call
	|	functionCall call*
	;

functionCall
	:	classInterfaceTypeWithoutObject '(' expressionList ')'
	;

callOrAccess
	:	memberAccessOrArrayAccess
	|	call
	;	
	
call	:	('->' Identifier '(' expressionList')');
	
methodCallFluentInclAccessAtTheEnd
	:	methodCallFluentWithoutAccessAtTheEnd memberAccessOrArrayAccess?
	;
	
methodCallFluentWithoutAccessAtTheEnd
	: 	methodCall callOrAccess* call
	|	methodCall call*
	;
methodCall
	:	( (variableMemberStaticMember '->') | staticAccessOrParent) Identifier '(' expressionList')'
	;

atom	:	'(' expression ')' -> expression
	|	incrementDecrement
	|	variableMemberStaticMember
	|	classConstant
	|	globalConstant
	|	array
	|	primitiveAtom
	;
	
globalConstant
	:	Identifier
	;

classConstant
	:	staticAccessOrParent Identifier
	;
	
unaryPrimitiveAtom
	:	'+' primitiveAtom
	|	'-' primitiveAtom
	|	primitiveAtom
	;

primitiveAtom
	:	Bool
	|	Int
	|	Float
	|	String
	|	Null
	|	Identifier
	;
	
Int     : 	DECIMAL
        | 	HEXADECIMAL
        | 	OCTAL
        | 	BINARY
        ;

fragment
DECIMAL
	:	('1'..'9') ('0'..'9')*
        |	 '0'
        ;
        
fragment          
HEXADECIMAL 
	:	'0' ('x'|'X') ('0'..'9'|'a'..'f'|'A'..'F')+;

fragment
OCTAL	:	'0' ('0'..'7')+;


fragment
BINARY	:	'0b'('0'|'1')+;


Float
    	:	('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    	|	'.' ('0'..'9')+ EXPONENT?
    	|	('0'..'9')+ EXPONENT
    	;
    
fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;
	
String	:	STRING_SINGLE_QUOTED | STRING_DOUBLE_QUOTED ;



fragment
STRING_SINGLE_QUOTED
	:	'\'' (
			  ('\\\\')=>'\\\\' 
			|  ('\\\'')=>'\\\'' 
			| ~ ('\'' )
		)* '\'';
	
fragment
STRING_DOUBLE_QUOTED
    	:	'"' (
			  ('\\\\') => '\\\\'
			|  ('\\"') => '\\"'
			| ('\\$') => '\\$'
			| ~ ('"' | '$')
  		)* '"';
	
array	:	'[' array_content? ']' 
	|	TypeArray '(' array_content? ')'
	;
	
array_content
	:	(expression '=>')? expression  (',' (expression '=>')? expression)*;


ifCondition
	:	'if' '(' expression ')' instructionThen =instructionInclBreakContinue	
		( 'else' instructionElse =instructionInclBreakContinue )? -> ^('if' expression $instructionThen $instructionElse?)
	;


switchCondition	
	:	'switch' '(' VariableId ')' '{' switchContent '}'  -> ^('switch' VariableId switchContent)
	;
switchContent
	:	normalCase+ defaultCase normalCase+ 
	|	normalCase+ defaultCase?
	;

normalCase
	:	caseLabel+ instructionInclBreakContinue+ 
		-> ^(SWITCH_GROUP 
			^(SWITCH_CASES[$normalCase.start,"switch cases"] caseLabel+) 
			instructionInclBreakContinue+
		)
	;	
defaultCase
	:	defaultLabel instructionInclBreakContinue+ 
		-> ^(SWITCH_GROUP 
			^(SWITCH_CASES[$defaultCase.start,"switch cases"] defaultLabel) 
			instructionInclBreakContinue+
		)
	;

caseLabel	
	:	'case'! expression ':'!;

defaultLabel
	:	'default' ':'!;
	
forLoop	:	'for' '(' forInit ';' expressionList  ';' expressionList ')' instructionInclBreakContinue -> ^('for' forInit expressionList expressionList instructionInclBreakContinue);

forInit	:	varListOrExprList
	;

varListOrExprList
	:	variableDeclarationList -> ^(VARIABLE_DECLARATION_LIST variableDeclarationList)
	|	expressionList
	;

variableDeclarationList
	:	variableDeclaration (','! variableAssignment)* 
	;

foreachLoop
	:	'foreach' '(' expression 'as' (keyType=primitiveTypes keyVarId=VariableId '=>')? valueType=allTypesWithoutResource valueVarId=VariableId ')' instructionInclBreakContinue 
		-> ^('foreach' expression $keyType? $keyVarId? $valueType $valueVarId instructionInclBreakContinue);

whileLoop
	:	'while' '(' expression ')' instructionInclBreakContinue -> ^('while' expression instructionInclBreakContinue);
	
doWhileLoop
	:	'do' instructionInclBreakContinue 'while' '(' expression ')' ';' -> ^('do' instructionInclBreakContinue expression);

tryCatch:	'try' '{' instructionInclBreakContinue+ '}' 'catch' '(' classInterfaceTypeInclObject VariableId ')''{' instructionInclBreakContinue* '}';

throwException
	:	'throw' newObject ';';

Comment
	:	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
	//comment could be at the end of the file and thus no \n needed
	|  	'//' ~('\n'|'\r')* {$channel=HIDDEN;}
    	|	'/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    	;

Whitespace	
	:	( ' '
        	| '\t'
	        | '\r'
        	| '\n'
	        ) {$channel=HIDDEN;}
	;


	