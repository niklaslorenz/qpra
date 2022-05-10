package qpra.parser.qdimacs;

import org.junit.jupiter.api.*;
import qpra.model.qsat.QsatInstance;
import qpra.parser.QsatParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class QdimacsCorrectInstanceTest {

    private static HashMap<String, QsatInstance> instances;

    private QsatParser parser;

    @BeforeEach
    public void setup() {
        parser = new QdimacsTreeParser();
    }

    @TestFactory
    public List<DynamicTest> instanceTests() {
        List<DynamicTest> tests = new ArrayList<>();
        File instanceDir = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("qdimacs_correct")).getFile());
        for(File instance : Objects.requireNonNull(instanceDir.listFiles())) {
            tests.add(DynamicTest.dynamicTest(instance.getName(), () -> {
                QsatInstance parsed = parser.parse(instance, true);
                QsatInstance expected = instances.get(getFileName(instance.getName()));
                Assertions.assertEquals(expected, parsed);
            }));
        }
        return tests;
    }

    private static String getFileName(String name) {
        int beginPostfix = name.lastIndexOf(".");
        if(beginPostfix < 0) {
            return name;
        }
        return name.substring(0, beginPostfix);
    }

    @BeforeAll
    public static void createInstances() {
        instances = new HashMap<>();
        instances.put("example", new QsatInstance()
                .variableCount(4)
                .addClause(-1, 2)
                .addClause(2, -3, -4)
        );
        instances.put("implicit_variable", new QsatInstance()
                .variableCount(3)
                .addQuantifierSet(2)
                .addQuantifierSet(1)
                .addClause(1, 2)
                .addClause(1, -3)
        );
        instances.put("explicit_variable", new QsatInstance()
                .variableCount(3)
                .addQuantifierSet(2)
                .addQuantifierSet(3)
                .addClause(1, -2)
        );
        instances.put("explicit_implicit_variables", new QsatInstance()
                .variableCount(4)
                .addQuantifierSet(4)
                .addQuantifierSet(1)
                .addClause(1, -2, 3)
        );
        instances.put("minimal", new QsatInstance()
                .variableCount(1)
                .addClause(1)
        );
        instances.put("comment", new QsatInstance()
                .variableCount(1)
                .addClause(-1)
        );
        instances.put("one_line", new QsatInstance()
                .variableCount(2)
                .addQuantifierSet(2)
                .addQuantifierSet(1)
                .addClause(-1)
                .addClause(-1, 2)
                .addClause(2)
        );
        instances.put("sat_instance", new QsatInstance()
                .variableCount(2)
                .addClause(-1, -2)
                .addClause(1, -2)
                .addClause(1, 2)
        );
        instances.put("missing_whitespaces", new QsatInstance()
                .variableCount(3)
                .addQuantifierSet(2)
                .addQuantifierSet(1)
                .addQuantifierSet(3)
                .addClause(1, 2, 3)
                .addClause(-1,-2,3)
                .addClause(1)
        );
        instances.put("no_e_quantifier", new QsatInstance()
                .variableCount(3)
                .addQuantifierSet(1, 2, 3)
                .addClause(1, -1, 2)
                .addClause(-2, 2, 3)
                .addClause(-2, 3, -3)
        );
        instances.put("stacked_quantifiers", new QsatInstance()
                .variableCount(7)
                .addQuantifierSet(3, 4, 5)
                .addQuantifierSet(6)
                .addQuantifierSet(7)
                .addClause(1, 2)
                .addClause(3, 4)
        );
    }

}
