package ru.javarush.todo.converters;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EnumToEnumMapping {
    private final Map<?, ?> firstMap;
    private final Map<?, ?> secondMap;

    private final Class<?> firstType;
    private final Class<?> secondType;

    private <S, D> EnumToEnumMapping(Map<? extends S, ? extends D> map) {
        this.firstMap = Collections.unmodifiableMap(map);

        Map.Entry<?, ?> entry = map.entrySet().iterator().next();
        this.firstType = entry.getKey().getClass();
        this.secondType = entry.getValue().getClass();

        HashMap<D, S> reversed = new HashMap<>();
        for (Map.Entry<? extends S, ? extends D> eachEntry : map.entrySet()) {
            reversed.put(eachEntry.getValue(), eachEntry.getKey());
        }
        secondMap = Collections.unmodifiableMap(reversed);
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    public <S extends Enum<S>, D extends Enum<D>> D get(S mappedEnum) {
        if (mappedEnum == null)
            throw new NullPointerException("Enum must not be NULL");

        if (firstType.equals(mappedEnum.getClass())) {
            return (D) firstMap.get(mappedEnum);
        }

        if (secondType.equals(mappedEnum.getClass())) {
            return (D) secondMap.get(mappedEnum);
        }

        throw new IllegalArgumentException("Didn't found mapping for enum value: " + mappedEnum);
    }

    public static class Builder {
        private final Map<Object, Object> sourceToDestinationMap = new HashMap<>();
        private Class<?> sourceEnumType;
        private Class<?> destinationEnumType;

        public Builder source(Class<?> value) {
            sourceEnumType = value;
            return this;
        }

        public Builder destination(Class<?> value) {
            destinationEnumType = value;
            return this;
        }

        public <S extends Enum<S>, D extends Enum<D>> Builder add(S sourceEnum, D destinationEmum) {
            if (sourceEnum.getClass() != sourceEnumType)
                throw new IllegalArgumentException("Value '" + sourceEnum + "' has type '" + sourceEnum.getClass()
                        + "', but should be of type '" + sourceEnumType + "'.");

            if (destinationEmum.getClass() != destinationEnumType)
                throw new IllegalArgumentException("Value '" + destinationEmum + "' has type '"
                        + destinationEmum.getClass() + "', but should be of type '" + destinationEnumType + "'.");

            if (sourceToDestinationMap.containsKey(sourceEnum))
                throw new IllegalArgumentException("Duplicate entry for value '" + sourceEnum + "'.");

            sourceToDestinationMap.put(sourceEnum, destinationEmum);
            return this;
        }

        public EnumToEnumMapping build() {
            return new EnumToEnumMapping(sourceToDestinationMap);
        }
    }
}
