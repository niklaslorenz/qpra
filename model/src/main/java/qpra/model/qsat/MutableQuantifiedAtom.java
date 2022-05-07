package qpra.model.qsat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import qpra.model.core.Quantifier;

public class MutableQuantifiedAtom extends QuantifiedAtom {

    public MutableQuantifiedAtom(@Range(from = 1, to = Integer.MAX_VALUE) int index, @NotNull Quantifier quantifier) {
        super(index, quantifier);
    }

    public void setQuantifier(@NotNull Quantifier quantifier) {
        super.quantifier = quantifier;
    }

}
