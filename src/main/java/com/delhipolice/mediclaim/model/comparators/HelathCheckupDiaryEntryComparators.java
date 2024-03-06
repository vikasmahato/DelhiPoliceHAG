package com.delhipolice.mediclaim.model.comparators;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.HealthCheckupDiaryEntry;
import com.delhipolice.mediclaim.utils.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HelathCheckupDiaryEntryComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
     static class Key {
        String name;
        Direction dir;
    }


    static Map<Key, Comparator<HealthCheckupDiaryEntry>> map = new HashMap<>();

    static {
        map.put(new Key("diaryNumber", Direction.asc), Comparator.comparing(HealthCheckupDiaryEntry::getDiaryNumber));
        map.put(new Key("diaryNumber", Direction.desc), Comparator.comparing(HealthCheckupDiaryEntry::getDiaryNumber)
                .reversed());

        map.put(new Key("diaryDate", Direction.asc), Comparator.comparing(HealthCheckupDiaryEntry::getDiaryDate));
        map.put(new Key("diaryDate", Direction.desc), Comparator.comparing(HealthCheckupDiaryEntry::getDiaryDate)
                .reversed());

    }

    public static Comparator<HealthCheckupDiaryEntry> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private HelathCheckupDiaryEntryComparators() {
    }

}
