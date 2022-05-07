package qpra.model.qsat;

import org.jetbrains.annotations.NotNull;
import qpra.model.sat.SatClause;

import java.util.List;

public class QsatInstance {
    private final @NotNull List<QuantifiedAtom> quantifiers;
    private final @NotNull List<SatClause> clauses;

    public QsatInstance(@NotNull List<QuantifiedAtom> quantifiers, @NotNull List<SatClause> clauses) {
        this.quantifiers = quantifiers;
        this.clauses = clauses;
    }

    public List<QuantifiedAtom> quantifiers() {
        return quantifiers;
    }

    public List<SatClause> clauses() {
        return clauses;
    }

}
