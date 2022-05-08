package qpra.app;

import qpra.model.qsat.QsatFormatter;
import qpra.model.qsat.QsatInstance;
import qpra.parser.qdimacs.QdimacsTreeParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.out.println("Usage:\n <qpra> <input-file> [<output-file>]");
            return;
        }
        File f = new File(args[0]);
        if(!f.exists()) {
            System.err.println("Input file does not exist.");
            return;
        }
        QsatInstance instance = new QdimacsTreeParser().parse(f);
        if(instance == null) {
            System.err.println("Parsing Error. Could not interpret input.");
            return;
        }
        QsatFormatter formatter = new QsatFormatter();
        File out = f.toPath().toAbsolutePath().getParent().resolve(f.getName() + ".qlp").toFile();
        String format = formatter.toYasolInput(instance);
        Files.writeString(out.toPath(), format);
        System.out.println("Converted input into QLP format");
        System.out.println("Wrote output to " + out.getPath());
    }
}
