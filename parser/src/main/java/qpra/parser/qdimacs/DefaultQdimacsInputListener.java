package qpra.parser.qdimacs;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import qpra.model.core.Quantifier;
import qpra.model.qsat.QsatInstance;
import qpra.model.qsat.QuantifiedAtom;
import qpra.model.sat.SatClause;
import qpra.parser.ParseStack;
import qpra.parser.error.ParseError;

import java.util.*;

public class DefaultQdimacsInputListener implements QdimacsInputListener {

    private ParseStack<Integer> literals;
    private ParseStack<SatClause> clauses;
    private ParseStack<List<SatClause>> matrices;
    private ParseStack<Quantifier> quantifiers;
    private ParseStack<QuantifiedAtom> quantSets;
    private ParseStack<List<QuantifiedAtom>> prefixes;
    private ParseStack<Pair<Integer, Integer>> problems;

    private QsatInstance result;
    private Pair<Integer, Integer> problem;
    private List<QuantifiedAtom> prefix;
    private List<SatClause> matrix;

    public DefaultQdimacsInputListener() {
        literals = new ParseStack<>();
        clauses = new ParseStack<>();
        matrices = new ParseStack<>();
        quantifiers = new ParseStack<>();
        quantSets = new ParseStack<>();
        prefixes = new ParseStack<>();
        problems = new ParseStack<>();
    }

    public void reset() {
        literals.clear();
        clauses.clear();
        matrices.clear();
        quantifiers.clear();
        quantSets.clear();
        prefixes.clear();
        problems.clear();
        result = null;
        problem = null;
        prefix = null;
        matrix = null;
    }

    @Override
    public void enterInput(QdimacsInputParser.InputContext ctx) {
        problems.push(x -> problem = x);
        prefixes.push(x -> prefix = x);
        matrices.push(x -> matrix = x);
    }

    @Override
    public void exitInput(QdimacsInputParser.InputContext ctx) {
        problems.popHandler();
        prefixes.popHandler();
        matrices.popHandler();
        result = new QsatInstance(prefix, matrix);
    }

    @Override
    public void enterProblem(QdimacsInputParser.ProblemContext ctx) {

    }

    @Override
    public void exitProblem(QdimacsInputParser.ProblemContext ctx) {
        problems.handle(new Pair<>(Integer.parseInt(ctx.PNUM(0).getText()), Integer.parseInt(ctx.PNUM(1).getText())));
    }

    @Override
    public void enterPrefix(QdimacsInputParser.PrefixContext ctx) {
        List<QuantifiedAtom> prefix = new ArrayList<>();
        prefixes.push(prefix);
        quantSets.push(prefix::add);
    }

    @Override
    public void exitPrefix(QdimacsInputParser.PrefixContext ctx) {
        quantSets.popHandler();
        prefixes.handle();
    }

    @Override
    public void enterQuant_set(QdimacsInputParser.Quant_setContext ctx) {
        quantifiers.push(quantifiers::push);
    }

    @Override
    public void exitQuant_set(QdimacsInputParser.Quant_setContext ctx) {
        Quantifier quantifier = quantifiers.popValue();
        quantifiers.popHandler();
        for(TerminalNode atom : ctx.PNUM()) {
            quantSets.handle(new QuantifiedAtom(Integer.parseInt(atom.getText()), quantifier));
        }
    }

    @Override
    public void enterQuantifier_Existence(QdimacsInputParser.Quantifier_ExistenceContext ctx) {
    }

    @Override
    public void exitQuantifier_Existence(QdimacsInputParser.Quantifier_ExistenceContext ctx) {
        quantifiers.handle(Quantifier.EXISTENCE);
    }

    @Override
    public void enterQuantifier_All(QdimacsInputParser.Quantifier_AllContext ctx) {
    }

    @Override
    public void exitQuantifier_All(QdimacsInputParser.Quantifier_AllContext ctx) {
        quantifiers.handle(Quantifier.ALL);
    }

    @Override
    public void enterMatrix(QdimacsInputParser.MatrixContext ctx) {
        List<SatClause> matrix = new ArrayList<>();
        matrices.push(matrix);
        clauses.push(matrix::add);
    }

    @Override
    public void exitMatrix(QdimacsInputParser.MatrixContext ctx) {
        clauses.popHandler();
        matrices.handle();
    }

    @Override
    public void enterClause(QdimacsInputParser.ClauseContext ctx) {
        SatClause clause = new SatClause();
        clauses.push(clause);
        literals.push(clause::add);
    }

    @Override
    public void exitClause(QdimacsInputParser.ClauseContext ctx) {
        literals.popHandler();
        clauses.handle();
    }

    @Override
    public void enterLiteral(QdimacsInputParser.LiteralContext ctx) {
    }

    @Override
    public void exitLiteral(QdimacsInputParser.LiteralContext ctx) {
        literals.handle(Integer.parseInt(ctx.getText()));
    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }

    public QsatInstance result() {
        return result;
    }

}
