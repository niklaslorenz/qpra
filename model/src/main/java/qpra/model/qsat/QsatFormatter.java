package qpra.model.qsat;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class QsatFormatter {

    private boolean literalNeedsSign;
    private int currentConstraintThreshold;
    private StringBuilder builder;

    public String toQlp(QsatInstance instance) {
        HashSet<Integer> unboundAtoms = new HashSet<>();
        IntStream.range(1, instance.variableCount() + 1).forEach(unboundAtoms::add);
        builder = new StringBuilder();
        builder.append("MAXIMIZE\nSUBJECT TO\n");
        for(Set<Integer> clause : instance.clauses()) {
            appendClause(clause);
        }
        builder.append("BINARIES\n");
        for(int i = 1; i <= instance.variableCount(); ++i){
            appendAtom(i);
            builder.append(" ");
        }
        builder.append("\n");
        for(Set<Integer> set : instance.quantifiers()) {
            unboundAtoms.removeAll(set);
        }
        Set<Integer> allQuantified = new HashSet<>(),
                existenceQuantified;
        existenceQuantified = new HashSet<>(unboundAtoms);
        for(int i = 0; i < instance.quantifiers().size(); ++i) {
            switch(QsatInstance.getQuantifierOfSet(i)) {
                case EXISTENCE -> existenceQuantified.addAll(instance.quantifiers().get(i));
                case ALL -> allQuantified.addAll(instance.quantifiers().get(i));
            }
        }
        builder.append("EXISTS\n");
        appendAtomSet(existenceQuantified);
        builder.append("\n");
        builder.append("ALL\n");
        appendAtomSet(allQuantified);
        builder.append("\n");
        builder.append("ORDER\n");
        appendAtomSet(unboundAtoms);
        for(Set<Integer> set : instance.quantifiers()) {
            appendAtomSet(set);
        }
        builder.append("\n");
        builder.append("END\n");
        return builder.toString();
    }

    private void appendAtomSet(Set<Integer> set) {
        for(int atom : set) {
            appendAtom(atom);
            builder.append(" ");
        }
    }

    private void appendClause(Set<Integer> clause) {
        currentConstraintThreshold = 1;
        literalNeedsSign = false;
        for(int literal : clause) {
            appendLiteral(literal);
        }
        builder.append(" >= ").append(currentConstraintThreshold);
        builder.append("\n");
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
