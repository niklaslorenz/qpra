package qpra.util;

import java.util.HashSet;

public class NumberSet extends HashSet<Integer> {

    public NumberSet() {
    }

    public NumberSet(int... numbers) {
        add(numbers);
    }

    public NumberSet add(int number) {
        super.add(number);
        return this;
    }

    public NumberSet add(int... numbers) {
        for(int number : numbers) {
            super.add(number);
        }
        return this;
    }

}
