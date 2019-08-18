package com.glitchedcode.ct.immutable;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ImmutableSet<E> implements Set<E> {

    private final Set<E> set;

    public ImmutableSet(E[] array) {
        this.set = new HashSet<>();
        Collections.addAll(set, array);
    }

    public ImmutableSet(Collection<E> collection) {
        this.set = new HashSet<>(collection);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return new ImmutableIterator<>(set.iterator());
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        set.forEach(action);
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    @Nonnull
    public <T> T[] toArray(@Nonnull T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public void clear() {
    }

    @Override
    public Spliterator<E> spliterator() {
        return set.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return set.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return set.parallelStream();
    }
}