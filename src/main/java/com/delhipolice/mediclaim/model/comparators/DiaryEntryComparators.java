package com.delhipolice.mediclaim.model.comparators;

import com.delhipolice.mediclaim.model.DiaryEntry;
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


    static Map<Key, Comparator<DiaryEntry>> map = new HashMap<>();

    static {
        map.put(new Key("diaryNumber", Direction.asc), Comparator.comparing(DiaryEntry::getDiaryNumber));
        map.put(new Key("diaryNumber", Direction.desc), Comparator.comparing(DiaryEntry::getDiaryNumber)
                .reversed());

        map.put(new Key("diaryType", Direction.asc), Comparator.comparing(DiaryEntry::getDiaryType));
        map.put(new Key("diaryType", Direction.desc), Comparator.comparing(DiaryEntry::getDiaryType)
                .reversed());

    }

    public static Comparator<DiaryEntry> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private DiaryEntryComparators() {
    }

}
