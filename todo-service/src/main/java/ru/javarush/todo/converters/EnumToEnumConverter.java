package ru.javarush.todo.converters;

import jakarta.annotation.PostConstruct;

public abstract class EnumToEnumConverter<S extends Enum<S>, D extends Enum<D>> {

    private EnumToEnumMapping mapping;

    protected abstract EnumToEnumMapping buildMapping();

    @PostConstruct
    public void init() {
        mapping = buildMapping();
    }

    public S toSource(D value) {
        return mapping.get(value);
    }

    public D toDestination(S value) {
        return mapping.get(value);
    }
}