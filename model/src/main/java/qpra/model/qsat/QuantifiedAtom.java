package qpra.model.qsat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import qpra.model.core.Quantifier;

public class QuantifiedAtom {

    protected final int atom;
    protected @NotNull Quantifier quantifier;

    public QuantifiedAtom(@Range(from = 1, to = Integer.MAX_VALUE) int index, @NotNull Quantifier quantifier) {
        this.atom = index;
        this.quantifier = quantifier;
    }

    public int index() {
        return atom;
    }

    public @NotNull Quantifier quantifier() {
        return quantifier;
    }

}
