package com.delhipolice.mediclaim.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class EntrySorter<T> {

    private static final Logger log = LoggerFactory.getLogger(EntrySorter.class);

    private final Comparator<T> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public Comparator<T> sortEntries(PagingRequest pagingRequest, EntryComparatorProvider<T> comparatorProvider) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);

            Comparator<T> comparator = comparatorProvider.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}