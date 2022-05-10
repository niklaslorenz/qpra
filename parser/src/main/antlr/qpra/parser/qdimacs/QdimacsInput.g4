grammar QdimacsInput;

@header {
package qpra.parser.qdimacs;
}

input
    : problem prefix matrix
    | problem matrix
    ;
problem
    : KEY_PROBLEM PNUM PNUM
    ;
prefix
    : quant_set+
    ;
quant_set
    : quantifier PNUM+ KEY_ZERO
    ;
quantifier
    : KEY_EXQ                       #Quantifier_Existence
    | KEY_ALLQ                      #Quantifier_All
    ;
matrix
    : clause+
    ;
clause
    : (NNUM|PNUM)+ KEY_ZERO
    ;

PNUM: [1-9][0-9]*;
NNUM: '-' [1-9][0-9]*;
KEY_PROBLEM: 'p cnf';
COMMENT: 'c' ~('\r' | '\n')* -> skip;
KEY_EXQ: 'e';
KEY_ALLQ: 'a';
KEY_ZERO: '0';
WHITESPACE: [ \r\n] -> skip;
