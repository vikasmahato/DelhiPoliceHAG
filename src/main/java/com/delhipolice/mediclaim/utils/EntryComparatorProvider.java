package com.delhipolice.mediclaim.utils;

import java.util.Comparator;

public interface EntryComparatorProvider<T> {
    Comparator<T> getComparator(String columnData, Direction orderDir);
}