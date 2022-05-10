package qpra.parser.qdimacs;

import qpra.model.core.Quantifier;

import java.util.HashSet;
import java.util.Set;

public final class QuantifierSet {

    private Quantifier quantifier;
    private Set<Integer> atoms;

    public QuantifierSet() {
        quantifier = null;
        atoms = new HashSet<>();
    }

    public Quantifier quantifier() {
        return quantifier;
    }

    public Set<Integer> atoms() {
        return atoms;
    }

    public QuantifierSet quantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
        return this;
    }

    public QuantifierSet atoms(Set<Integer> atoms) {
        this.atoms = atoms;
        return this;
    }

    public QuantifierSet add(int atom) {
        atoms.add(atom);
        return this;
    }

    public QuantifierSet add(int... atoms) {
        for(int atom : atoms) {
            this.atoms.add(atom);
        }
        return this;
    }

}
