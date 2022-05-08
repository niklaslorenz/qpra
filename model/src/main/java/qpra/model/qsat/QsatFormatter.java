package qpra.model.qsat;

import qpra.model.core.Quantifier;
import qpra.model.sat.SatClause;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class QsatFormatter {

    private boolean literalNeedsSign;
    private int currentConstraintThreshold;
    private StringBuilder builder;

    public String toQlp(QsatInstance instance) {
        HashSet<Integer> atoms = new HashSet<>();
        HashSet<Integer> unboundAtoms = new HashSet<>();
        builder = new StringBuilder();
        builder.append("MAXIMIZE\nSUBJECT TO\n");
        for(SatClause clause : instance.clauses()) {
            currentConstraintThreshold = 1;
            literalNeedsSign = false;
            for(int literal : clause) {
                appendLiteral(literal);
                int atom = Math.abs(literal);
                atoms.add(atom);
                unboundAtoms.add(atom);
            }
            builder.append(" >= ").append(currentConstraintThreshold);
            builder.append("\n");
        }
        builder.append("BINARIES\n");
        for(int atom : atoms) {
            appendAtom(atom);
            builder.append(" ");
        }
        builder.append("\n");
        for(QuantifiedAtom q : instance.quantifiers()) {
            unboundAtoms.remove(q.index());
        }
        List<QuantifiedAtom> quantifiers = new ArrayList<>();
        for(int atom : unboundAtoms) {
            quantifiers.add(new QuantifiedAtom(atom, Quantifier.EXISTENCE));
        }
        quantifiers.addAll(instance.quantifiers());
        builder.append("EXISTS\n");
        for(QuantifiedAtom atom : quantifiers) {
            if(atom.quantifier() == Quantifier.EXISTENCE) {
                appendAtom(atom.index());
                builder.append(" ");
            }
        }
        builder.append("\n");
        builder.append("ALL\n");
        for(QuantifiedAtom atom : quantifiers) {
            if(atom.quantifier() == Quantifier.ALL) {
                appendAtom(atom.index());
                builder.append(" ");
            }
        }
        builder.append("\n");
        builder.append("ORDER\n");
        for(QuantifiedAtom atom : quantifiers) {
            appendAtom(atom.index());
            builder.append(" ");
        }
        builder.append("\n");
        builder.append("END\n");
        return builder.toString();
    }

    private void appendLiteral(int literal) {
        if(literal > 0 && literalNeedsSign) {
            builder.append(" + ");
        }
        if(literal < 0) {
            builder.append(" - ");
            --currentConstraintThreshold;
        }
        appendAtom(Math.abs(literal));
        literalNeedsSign = true;
    }

    private void appendAtom(int atom) {
        builder.append("x").append(atom);
    }

}
