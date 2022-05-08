package qpra.parser;

import qpra.model.qsat.QsatInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface QsatParser {

    QsatInstance parse(File inputFile) throws IOException;

}
