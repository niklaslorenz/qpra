package qpra.model.qsat;

import org.jetbrains.annotations.NotNull;
import qpra.model.sat.SatClause;

import java.util.List;

public class QsatInstance {
    private final @NotNull List<? extends QuantifiedAtom> quantifiers;
    private final @NotNull List<SatClause> clauses;

    public QsatInstance(@NotNull List<? extends QuantifiedAtom> quantifiers, @NotNull List<SatClause> clauses) {
        this.quantifiers = quantifiers;
        this.clauses = clauses;
    }

    public List<? extends QuantifiedAtom> quantifiers() {
        return quantifiers;
    }

    public List<SatClause> clauses() {
        return clauses;
    }

}
