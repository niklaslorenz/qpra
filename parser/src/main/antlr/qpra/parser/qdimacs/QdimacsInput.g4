grammar QdimacsInput;

@header {
package qpra.parser.qdimacs;
}

input
    : preamble prefix matrix        #Input_WithPrefix
    | preamble matrix               #Input_WithoutPrefix
    ;

preamble
    : problem_line
    ;
problem_line
    : KEY_PROBLEM PNUM PNUM
    ;
prefix
    : quant_sets
    ;
quant_sets
    : quant_set                 #QuantSets_Last
    | quant_set quant_sets      #QuantSets_Intermediate
    ;
quant_set
    : quantifier atom_set KEY_ZERO
    ;
quantifier
    : KEY_EXQ                   #Quantifier_Existence
    | KEY_ALLQ                  #Quantifier_All
    ;
atom_set
    : PNUM                      #AtomSet_Last
    | PNUM atom_set             #AtomSet_Intermediate
    ;
matrix
    : clause_list
    ;
clause_list
    : clause                    #ClauseList_Last
    | clause clause_list        #ClauseList_Intermediate
    ;
clause
    : literal KEY_ZERO      #Clause_Last
    | literal clause            #Clause_Intermediate
    ;
literal
    : NNUM                      #Literal_NNUM
    | PNUM                      #Literal_PNUM
    ;

PNUM: [1-9][0-9]*;
NNUM: '-' [1-9][0-9]*;
KEY_PROBLEM: 'p cnf';
COMMENT: 'c' ~('\r' | '\n')* -> skip;
KEY_EXQ: 'e';
KEY_ALLQ: 'a';
KEY_ZERO: '0';
WHITESPACE: [ \r\n] -> skip;
