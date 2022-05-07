package qpra.model.sat;

import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.Supplier;

public class SatClause implements Iterable<Integer>, Collection<Integer> {

    private static final Supplier<Collection<Integer>> defaultSupplier = HashSet::new;

    public static SatClause wrap(Collection<Integer> content) {
        return new SatClause(content);
    }
    public static SatClause create(Collection<Integer> content) {
        return create(content, defaultSupplier);
    }
    public static SatClause create(Collection<Integer> content, Supplier<? extends Collection<Integer>> containerProducer) {
        Collection<Integer> literals = containerProducer.get();
        literals.addAll(content);
        return new SatClause(literals);
    }

    protected final Collection<Integer> literals;

    protected SatClause(Collection<Integer> content) {
        literals = content;
    }
    public SatClause() {
        this(defaultSupplier);
    }
    public SatClause(Supplier<? extends Collection<Integer>> containerProducer) {
        literals = containerProducer.get();
    }

    @Override
    public int size() {
        return literals.size();
    }

    @Override
    public boolean isEmpty() {
        return literals.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return literals.contains(o);
    }

    @Override
    @NotNull
    public Iterator<Integer> iterator() {
        return literals.iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return literals.toArray();
    }

    @NotNull
    @Override
    public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
        return literals.toArray(a);
    }

    @Override
    public boolean add(Integer literal) {
        return literals.add(literal);
    }

    @Override
    public boolean remove(Object o) {
        return literals.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return literals.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Integer> c) {
        for(Integer literal : c) {
            assert literal != 0;
        }
        return literals.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return literals.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return literals.retainAll(c);
    }

    @Override
    public void clear() {
        literals.clear();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof SatClause clause) {
            return literals.equals(clause.literals);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return literals.hashCode();
    }

    public boolean containsAtom(@Range(from = 1, to = Integer.MAX_VALUE) int atom) {
        return literals.contains(atom) || literals.contains(-atom);
    }

    @NotNull
    @Unmodifiable
    public Set<Integer> atoms() {
        HashSet<Integer> atoms = new HashSet<>();
        for(int literal : literals) {
            atoms.add(Math.abs(literal));
        }
        return Collections.unmodifiableSet(atoms);
    }

    public UnmodifiableSatClause unmodifiableView() {
        return new UnmodifiableSatClause(literals);
    }

}