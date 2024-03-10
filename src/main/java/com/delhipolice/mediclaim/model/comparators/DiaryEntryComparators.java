package com.delhipolice.mediclaim.model.comparators;

import com.delhipolice.mediclaim.model.IDiaryEntry;
import com.delhipolice.mediclaim.utils.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DiaryEntryComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
     static class Key {
        String name;
        Direction dir;
    }


    static Map<Key, Comparator<IDiaryEntry>> map = new HashMap<>();

    static {
        map.put(new Key("diaryNumber", Direction.asc), Comparator.comparing(IDiaryEntry::getDiaryNumber));
        map.put(new Key("diaryNumber", Direction.desc), Comparator.comparing(IDiaryEntry::getDiaryNumber)
                .reversed());

        map.put(new Key("creationDate", Direction.asc), Comparator.comparing(i -> i.getAuditSection().getDateCreated()));
        map.put(new Key("creationDate", Direction.desc), Comparator.comparing(i -> i.getAuditSection().getDateCreated()));
    }

    public static Comparator<IDiaryEntry> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private DiaryEntryComparators() {
    }

}
