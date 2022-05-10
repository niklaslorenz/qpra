package qpra.app;

import qpra.model.qsat.QsatFormatter;
import qpra.model.qsat.QsatInstance;
import qpra.parser.qdimacs.QdimacsTreeParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length == 0 || args.length > 2) {
            System.out.println("Usage:\n <qpra> <input-file> [<output-file>]");
            return;
        }
        File inputFile = new File(args[0]);
        if(!inputFile.exists()) {
            System.err.println("Input file does not exist.");
            return;
        }
        File outputFile = args.length > 1 ? new File(args[1]) : getDefaultOutputPath(inputFile.toPath()).toFile();
        QsatInstance instance = new QdimacsTreeParser().parse(inputFile, false);
        if(instance == null) {
            System.err.println("Parsing Error. Could not interpret input.");
            return;
        }
        QsatFormatter formatter = new QsatFormatter();
        String format = formatter.toQlp(instance);
        Files.writeString(outputFile.toPath(), format);
        System.out.println("Converted input into QLP format");
        System.out.println("Wrote output to " + outputFile.getPath());
    }

    private static Path getDefaultOutputPath(Path inputPath) {
        return inputPath.toAbsolutePath().getParent().resolve(inputPath.getFileName() + ".qlp");
    }

}
