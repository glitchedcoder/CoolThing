package com.glitchedcode.ct.immutable;

import java.util.Iterator;
import java.util.function.Consumer;

public final class ImmutableIterator<E> implements Iterator<E> {

    private final Iterator<E> iterator;

    public ImmutableIterator(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        // ignore
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        iterator.forEachRemaining(action);
    }
}