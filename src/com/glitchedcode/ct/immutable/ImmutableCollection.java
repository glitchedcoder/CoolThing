package com.glitchedcode.ct.immutable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class ImmutableCollection<E> implements Collection<E> {

    private final Collection<E> collection;

    public ImmutableCollection(Collection<E> collection) {
        this.collection = collection;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return new ImmutableIterator<>(collection.iterator());
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        collection.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Nonnull
    @Override
    public <T> T[] toArray(@Nonnull T[] a) {
        return collection.toArray(a);
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
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
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
    public boolean retainAll(@Nonnull Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        // ignore
    }

    @Override
    public Spliterator<E> spliterator() {
        return collection.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return collection.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return collection.parallelStream();
    }
}