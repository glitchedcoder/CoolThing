package com.glitchedcode.ct.concurrent;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentSet<E> implements Set<E> {

    private final ConcurrentHashMap.KeySetView<E, Boolean> keySetView;

    /**
     * Creates a new instance which wraps the specified {@code keySetView}.
     */
    public ConcurrentSet() {
        keySetView = ConcurrentHashMap.newKeySet();
    }

    public ConcurrentSet(E[] array) {
        keySetView = ConcurrentHashMap.newKeySet(array.length);
        Collections.addAll(keySetView, array);
    }

    public ConcurrentSet(Collection<E> collection) {
        keySetView = ConcurrentHashMap.newKeySet(collection.size());
        keySetView.addAll(collection);
    }

    @Override
    public int size() {
        return keySetView.size();
    }

    @Override
    public boolean isEmpty() {
        return keySetView.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return keySetView.contains(o);
    }

    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return keySetView.iterator();
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return keySetView.toArray();
    }

    @Override
    @Nonnull
    public <T> T[] toArray(@Nonnull T[] a) {
        //noinspection SuspiciousToArrayCall
        return keySetView.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return keySetView.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return keySetView.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return keySetView.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return keySetView.addAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return keySetView.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return keySetView.removeAll(c);
    }

    @Override
    public void clear() {
        keySetView.clear();
    }
}