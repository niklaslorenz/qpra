package qpra.app;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import qpra.model.qsat.QsatFormatter;
import qpra.model.qsat.QsatInstance;
import qpra.parser.qdimacs.DefaultQdimacsInputVisitor;
import qpra.parser.qdimacs.QdimacsInputLexer;
import qpra.parser.qdimacs.QdimacsInputParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.err.println("Please specify an input file.");
            return;
        }
        File f = new File(args[0]);
        if(!f.exists()) {
            System.err.println("This file does not exist.");
            return;
        }
        QdimacsInputLexer lexer = new QdimacsInputLexer(new ANTLRFileStream(f.getPath()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QdimacsInputParser parser = new QdimacsInputParser(tokens);
        ParseTree tree = parser.input();
        DefaultQdimacsInputVisitor converter = new DefaultQdimacsInputVisitor();
        QsatInstance instance = (QsatInstance) converter.visit(tree);
        QsatFormatter formatter = new QsatFormatter();
        File out = f.toPath().toAbsolutePath().getParent().resolve(f.getName() + ".qlp").toFile();
        String format = formatter.toYasolInput(instance);
        System.out.println(format);
        Files.writeString(out.toPath(), format);
    }
}
