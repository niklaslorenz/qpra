package qpra.parser;

import qpra.model.qsat.QsatInstance;

import java.io.File;
import java.io.IOException;

public interface QsatParser {

    default QsatInstance parse(File inputFile) throws IOException {
        return parse(inputFile, true);
    }

    QsatInstance parse(File inputFile, boolean verify) throws IOException;

}
