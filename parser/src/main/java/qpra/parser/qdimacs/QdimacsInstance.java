package qpra.parser.qdimacs;

import qpra.model.qsat.QsatInstance;

public class QdimacsInstance {

    private final QsatInstance instance;
    private int clauseCount;

    public QdimacsInstance() {
        instance = new QsatInstance();
        clauseCount = 0;
    }

    public QsatInstance instance() {
        return instance;
    }

    public int clauseCount() {
        return clauseCount;
    }

    public QdimacsInstance clauseCount(int clauseCount) {
        this.clauseCount = clauseCount;
        return this;
    }

    public boolean verify() {
        if(clauseCount == 0) {
            return false;
        }
        return instance.clauses().size() == clauseCount && instance.verify();
    }

}
