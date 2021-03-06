package qpra.parser.qdimacs;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import qpra.model.core.Quantifier;
import qpra.model.qsat.QsatInstance;
import qpra.parser.ParseStack;
import qpra.parser.error.ParseError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultQdimacsInputListener implements QdimacsInputListener {

    private final boolean verify;
    private final ParseStack<Integer> literals;
    private final ParseStack<Set<Integer>> clauses;
    private final ParseStack<List<Set<Integer>>> matrices;
    private final ParseStack<Quantifier> quantifiers;
    private final ParseStack<QuantifierSet> quantSets;
    private final ParseStack<List<Set<Integer>>> prefixes;
    private final ParseStack<Pair<Integer, Integer>> problems;
    private final ParseStack<QdimacsInstance> instances;
    private QsatInstance result;

    public DefaultQdimacsInputListener() {
        this(true);
    }

    public DefaultQdimacsInputListener(boolean verify) {
        this.verify = verify;
        literals = new ParseStack<>();
        clauses = new ParseStack<>();
        matrices = new ParseStack<>();
        quantifiers = new ParseStack<>();
        quantSets = new ParseStack<>();
        prefixes = new ParseStack<>();
        problems = new ParseStack<>();
        instances = new ParseStack<>();
        result = null;
        instances.push(x -> result = x.instance());
    }

    @Override
    public void enterInput(QdimacsInputParser.InputContext ctx) {
        QdimacsInstance instance = new QdimacsInstance();
        QsatInstance qsat = instance.instance();
        instances.push(instance);
        problems.push(x -> {
            qsat.variableCount(x.a);
            instance.clauseCount(x.b);
        });
        prefixes.push(qsat::quantifiers);
        matrices.push(qsat::clauses);
    }

    @Override
    public void exitInput(QdimacsInputParser.InputContext ctx) {
        problems.popHandler();
        prefixes.popHandler();
        matrices.popHandler();
        if(verify && !instances.value().verify()) {
            throw new ParseError("Input represents an invalid instance.");
        }
        instances.handle();
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
        List<Set<Integer>> prefix = new ArrayList<>();
        prefixes.push(prefix);
        quantSets.push(set -> {
            if(prefix.size() == 0) {
                if(set.quantifier() == Quantifier.ALL) {
                    prefix.add(set.atoms());
                }
            } else if(set.quantifier() == QsatInstance.getQuantifierOfSet(prefix.size() - 1)) {
                prefix.get(prefix.size() - 1).addAll(set.atoms());
            } else {
                prefix.add(set.atoms());
            }
        });
    }

    @Override
    public void exitPrefix(QdimacsInputParser.PrefixContext ctx) {
        quantSets.popHandler();
        prefixes.handle();
    }

    @Override
    public void enterQuant_set(QdimacsInputParser.Quant_setContext ctx) {
        QuantifierSet set = new QuantifierSet();
        quantSets.push(set);
        quantifiers.push(set::quantifier);
    }

    @Override
    public void exitQuant_set(QdimacsInputParser.Quant_setContext ctx) {
        quantifiers.popHandler();
        Set<Integer> set = quantSets.value().atoms();
        for(TerminalNode atom : ctx.PNUM()) {
            set.add(Integer.parseInt(atom.getText()));
        }
        quantSets.handle();
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
        List<Set<Integer>> matrix = new ArrayList<>();
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
        Set<Integer> clause = new HashSet<>();
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
