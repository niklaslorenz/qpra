package qpra.model.sat;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class UnmodifiableSatClause extends SatClause {

    public UnmodifiableSatClause(Collection<Integer> content) {
        super(content);
    }

    @Override
    public boolean add(Integer literal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Integer> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

}
