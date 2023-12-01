package com.erocha.freeman.commons.mapper;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MapperAggregate<E, T, O> {

    T convertTransferObject(E e, O o);

    default List<T> convertTransferObject(List<E> e, O o) {
        return e.stream().map(e1 -> convertTransferObject(e1, o)).toList();
    }

    default Set<T> convertTransferObject(Set<E> e, O o) {
        return e.stream().map(e1 -> convertTransferObject(e1, o)).collect(Collectors.toSet());
    }

    default Page<T> convertPaginated(Page<E> e, O o) {
        return e.map(e1 -> convertTransferObject(e1, o));
    }
}
