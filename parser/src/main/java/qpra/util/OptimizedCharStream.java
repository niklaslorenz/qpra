package qpra.util;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.misc.Interval;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OptimizedCharStream extends ANTLRFileStream implements CharSequence {

    public OptimizedCharStream(String fileName) throws IOException {
        super(fileName);
    }

    public OptimizedCharStream(String fileName, String encoding) throws IOException {
        super(fileName, encoding);
    }

    public int parseInt(int begin, int end) {
        if ( end >= n ) end = n-1;
        int count = end - begin + 1;
        if ( begin >= n ) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return Integer.parseInt(this, begin, end + 1, 10);
    }

    @Override
    public int length() {
        return data.length;
    }

    @Override
    public char charAt(int index) {
        return data[index];
    }

    @NotNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return getText(new Interval(start, end));
    }
}
