package com.neonex.domain.entity;

import java.util.Objects;

public abstract class BaseEntity<T> {
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> entity = (BaseEntity<?>) o;
        return id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
