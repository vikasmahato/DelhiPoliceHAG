package com.delhipolice.mediclaim.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Page<T> {

    public Page(List<T> data) {
        this.data = data;
    }

    public Page(List<T> data, long recordsFiltered, long recordsTotal, int draw) {
        this.data = data;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.draw = draw;
    }

    private List<T> data;
    private long recordsFiltered;
    private long recordsTotal;
    private int draw;

}