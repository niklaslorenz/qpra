package qpra.parser.qdimacs;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import qpra.model.qsat.QsatInstance;
import qpra.parser.QsatParser;

import java.io.File;
import java.io.IOException;

public class QdimacsTreeParser implements QsatParser {

    @Override
    public QsatInstance parse(File inputFile) throws IOException {
        QdimacsInputLexer lexer = new QdimacsInputLexer(new ANTLRFileStream(inputFile.getPath()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QdimacsInputParser parser = new QdimacsInputParser(tokens);
        ParseTree tree = parser.input();
        DefaultQdimacsInputListener listener = new DefaultQdimacsInputListener();
        ParseTreeWalker.DEFAULT.walk(listener, tree);
        return listener.result();
    }

}
