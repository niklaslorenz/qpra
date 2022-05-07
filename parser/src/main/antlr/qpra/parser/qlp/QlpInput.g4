grammar QlpInput;

@header {
package qpra.parser.qlp;
}

unsigned_scalar
	: INT 
	| FLOAT
	;
signed_scalar
	: SIGN unsigned_scalar
	;
scalar
	: signed_scalar
	| unsigned_scalar
	;
scaled_var
	: unsigned_scalar VAR
	| VAR
	;
bound
	: scalar OPERATOR VAR OPERATOR scalar
	| scalar OPERATOR VAR
	;
bound_list
	: bound
	| bound bound_list
	;
signed_sum
	: SIGN scaled_var
	| SIGN scaled_var signed_sum
	;
unsigned_sum
	: scaled_var
	| scaled_var signed_sum
	;
sum
    : signed_sum
    | unsigned_sum
    ;
restriction
	: sum OPERATOR scalar
	;
restriction_list
	: restriction
	| restriction restriction_list
	;
variable_list
	: VAR
	| VAR variable_list
	;
goal
	: KEY_MIN
	| KEY_MAX
	;
function
	: goal sum
	;
subject
	: KEY_SUBJECT restriction_list
	;
bounds
	: KEY_BOUNDS bound_list
	;
binaries
	: KEY_BINARIES variable_list
	;
generals
	: KEY_GENERALS variable_list
	;
exists
	: KEY_EXISTS variable_list
	;
all
	: KEY_ALL variable_list
	;
order
	: KEY_ORDER variable_list
	;
problem
	: function subject bounds binaries generals exists all order KEY_END
    ;

KEY_MIN : 'MINIMIZE';
KEY_MAX : 'MAXIMIZE';
KEY_SUBJECT : 'SUBJECT TO';
KEY_BOUNDS : 'BOUNDS';
KEY_BINARIES : 'BINARIES';
KEY_GENERALS : 'GENERALS';
KEY_EXISTS : 'EXISTS';
KEY_ALL : 'ALL';
KEY_ORDER : 'ORDER';
KEY_END : 'END';
OPERATOR : '<=' | '>=' | '=';
INT : [0-9];
FLOAT : [0-9] ('.' [0-9])?;
SIGN : '-' | '+';
VAR : [A-Za-z][A-Za-z0-9]*;
WHITESPACE : [ \n\r] -> skip;