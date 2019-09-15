package com.glitchedcode.ct.immutable;

import java.util.ListIterator;
import java.util.function.Consumer;

public final class ImmutableListIterator<E> implements ListIterator<E> {

    private final ListIterator<E> listIterator;

    public ImmutableListIterator(ListIterator<E> listIterator) {
        this.listIterator = listIterator;
    }

    @Override
    public boolean hasNext() {
        return listIterator.hasNext();
    }

    @Override
    public E next() {
        return listIterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return listIterator.hasPrevious();
    }

    @Override
    public E previous() {
        return listIterator.previous();
    }

    @Override
    public int nextIndex() {
        return listIterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return listIterator.previousIndex();
    }

    @Override
    public void remove() {
        // ignore
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        listIterator.forEachRemaining(action);
    }

    @Override
    public void set(E e) {
        // ignore
    }

    @Override
    public void add(E e) {
        // ignore
    }
}