package qpra.parser.qdimacs;

import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import qpra.model.core.Quantifier;
import qpra.model.qsat.MutableQuantifiedAtom;
import qpra.model.qsat.QsatInstance;
import qpra.model.qsat.QuantifiedAtom;
import qpra.model.sat.SatClause;

import java.util.ArrayList;
import java.util.List;

public class DefaultQdimacsInputVisitor {
/*
    @Override
    public QsatInstance visitInput_WithPrefix(QdimacsInputParser.Input_WithPrefixContext ctx) {
        Pair<Integer, Integer> preamble = (Pair<Integer, Integer>) visit(ctx.preamble());
        List<MutableQuantifiedAtom> prefix = (List<MutableQuantifiedAtom>) visit(ctx.prefix());
        List<SatClause> clauses = visitMatrix(ctx.matrix());
        return new QsatInstance(prefix, clauses);
    }

    @Override
    public QsatInstance visitInput_WithoutPrefix(QdimacsInputParser.Input_WithoutPrefixContext ctx) {
        Pair<Integer, Integer> preamble = (Pair<Integer, Integer>) visit(ctx.preamble());
        List<MutableQuantifiedAtom> prefix = new ArrayList<>();
        List<SatClause> clauses = visitMatrix(ctx.matrix());
        return new QsatInstance(prefix, clauses);
    }

    @Override
    public Pair<Integer, Integer> visitPreamble(QdimacsInputParser.PreambleContext ctx) {
        return visitProblem_line(ctx.problem_line());
    }

    @Override
    public Pair<Integer, Integer> visitProblem_line(QdimacsInputParser.Problem_lineContext ctx) {
        return new Pair<>(Integer.parseInt(ctx.PNUM(0).getText()), Integer.parseInt(ctx.PNUM(1).getText()));
    }

    @Override
    public List<MutableQuantifiedAtom> visitPrefix(QdimacsInputParser.PrefixContext ctx) {
        return (List<MutableQuantifiedAtom>) visit(ctx.quant_sets());
    }

    @Override
    public List<MutableQuantifiedAtom> visitQuantSets_Last(QdimacsInputParser.QuantSets_LastContext ctx) {
        return (List<MutableQuantifiedAtom>) visit(ctx.quant_set());
    }

    @Override
    public List<MutableQuantifiedAtom> visitQuantSets_Intermediate(QdimacsInputParser.QuantSets_IntermediateContext ctx) {
        List<MutableQuantifiedAtom> list = (List<MutableQuantifiedAtom>) visit(ctx.quant_sets());
        List<MutableQuantifiedAtom> quantSet = (List<MutableQuantifiedAtom>) visit(ctx.quant_set());
        list.addAll(quantSet);
        return list;
    }

    @Override
    public List<MutableQuantifiedAtom> visitQuant_set(QdimacsInputParser.Quant_setContext ctx) {
        Quantifier quantifier = (Quantifier) visit(ctx.quantifier());
        List<MutableQuantifiedAtom> atomSet = (List<MutableQuantifiedAtom>) visit(ctx.atom_set());
        for(MutableQuantifiedAtom atom : atomSet) {
            atom.setQuantifier(quantifier);
        }
        return atomSet;
    }

    @Override
    public Quantifier visitQuantifier_Existence(QdimacsInputParser.Quantifier_ExistenceContext ctx) {
        return Quantifier.EXISTENCE;
    }

    @Override
    public Quantifier visitQuantifier_All(QdimacsInputParser.Quantifier_AllContext ctx) {
        return Quantifier.ALL;
    }

    @Override
    public List<MutableQuantifiedAtom> visitAtomSet_Last(QdimacsInputParser.AtomSet_LastContext ctx) {
        List<MutableQuantifiedAtom> list = new ArrayList<>();
        list.add(new MutableQuantifiedAtom(Integer.parseInt(ctx.PNUM().getText()), Quantifier.EXISTENCE));
        return list;
    }

    @Override
    public List<MutableQuantifiedAtom> visitAtomSet_Intermediate(QdimacsInputParser.AtomSet_IntermediateContext ctx) {
        List<MutableQuantifiedAtom> list = (List<MutableQuantifiedAtom>) visit(ctx.atom_set());
        list.add(new MutableQuantifiedAtom(Integer.parseInt(ctx.PNUM().getText()), Quantifier.EXISTENCE));
        return list;
    }

    @Override
    public List<SatClause> visitMatrix(QdimacsInputParser.MatrixContext ctx) {
        return (List<SatClause>) visit(ctx.clause_list());
    }

    @Override
    public List<SatClause> visitClauseList_Last(QdimacsInputParser.ClauseList_LastContext ctx) {
        List<SatClause> list = new ArrayList<>();
        list.add((SatClause) visit(ctx.clause()));
        return list;
    }

    @Override
    public List<SatClause> visitClauseList_Intermediate(QdimacsInputParser.ClauseList_IntermediateContext ctx) {
        List<SatClause> list = (List<SatClause>) visit(ctx.clause_list());
        list.add((SatClause) visit(ctx.clause()));
        return list;
    }

    @Override
    public SatClause visitClause_Last(QdimacsInputParser.Clause_LastContext ctx) {
        SatClause clause = new SatClause();
        clause.add((Integer) visit(ctx.literal()));
        return clause;
    }

    @Override
    public SatClause visitClause_Intermediate(QdimacsInputParser.Clause_IntermediateContext ctx) {
        SatClause clause = (SatClause) visit(ctx.clause());
        clause.add((Integer) visit(ctx.literal()));
        return clause;
    }

    @Override
    public Integer visitLiteral_PNUM(QdimacsInputParser.Literal_PNUMContext ctx) {
        return Integer.parseInt(ctx.PNUM().getText());
    }

    @Override
    public Integer visitLiteral_NNUM(QdimacsInputParser.Literal_NNUMContext ctx) {
        return Integer.parseInt(ctx.NNUM().getText());
    }*/
}
