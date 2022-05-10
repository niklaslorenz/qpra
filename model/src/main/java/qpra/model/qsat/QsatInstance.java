package qpra.model.qsat;

import org.jetbrains.annotations.NotNull;
import qpra.model.core.Quantifier;

import java.util.*;

public class QsatInstance {

    public static Quantifier getQuantifierOfSet(int index) {
        return index % 2 == 0 ? Quantifier.ALL : Quantifier.EXISTENCE;
    }

    private int variableCount;
    private @NotNull List<Set<Integer>> quantifiers;
    private @NotNull List<Set<Integer>> clauses;

    public QsatInstance() {
        this.variableCount = 0;
        this.quantifiers = new ArrayList<>();
        this.clauses = new ArrayList<>();
    }

    public QsatInstance(int variableCount, @NotNull List<Set<Integer>> quantifiers, @NotNull List<Set<Integer>> clauses) {
        this.variableCount = variableCount;
        this.quantifiers = quantifiers;
        this.clauses = clauses;
    }

    public int variableCount() {
        return variableCount;
    }

    public List<Set<Integer>> quantifiers() {
        return quantifiers;
    }

    public List<Set<Integer>> clauses() {
        return clauses;
    }

    public QsatInstance variableCount(int variableCount) {
        this.variableCount = variableCount;
        return this;
    }

    public QsatInstance quantifiers(List<Set<Integer>> quantifiers) {
        this.quantifiers = quantifiers;
        return this;
    }

    public QsatInstance clauses(List<Set<Integer>> clauses) {
        this.clauses = clauses;
        return this;
    }

    public QsatInstance addQuantifierSet(int... quantSet) {
        HashSet<Integer> set = new HashSet<>();
        for(int atom : quantSet) {
            set.add(atom);
        }
        this.quantifiers.add(set);
        return this;
    }

    public QsatInstance addClause(int... clause) {
        HashSet<Integer> set = new HashSet<>();
        for(int literal : clause) {
            set.add(literal);
        }
        this.clauses.add(set);
        return this;
    }

    public boolean verify() {
        HashSet<Integer> foundQuantifiers = new HashSet<>();
        if(variableCount <= 0) {
            return false;
        }
        /*
        Verify quantifiers: An atom must be
        - positive
        - not greater than the variable count
        - not be quantified more than once
         */
        for(Set<Integer> set : quantifiers) {
            for(int atom : set) {
                if(atom <= 0 || atom > variableCount || foundQuantifiers.contains(atom)) {
                    return false;
                }
            }
            foundQuantifiers.addAll(set);
        }
        /*
        Verify clauses: A literal must be
        - non-zero
        - of an atom not greater than the variable count
         */
        for(Set<Integer> clause : clauses) {
            for(int literal : clause) {
                if(literal == 0 || Math.abs(literal) > variableCount) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object second) {
        if(second instanceof QsatInstance instance) {
            return Objects.equals(quantifiers, instance.quantifiers) && Objects.equals(clauses, instance.clauses);
        }
        return false;
    }

    @Override
    public String toString() {
        return new QsatFormatter().toQlp(this);
    }

}
