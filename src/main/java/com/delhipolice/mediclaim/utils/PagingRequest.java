package com.delhipolice.mediclaim.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PagingRequest {

    private int start;
    private int length;
    private int draw;
    private List<Order> order;
    private List<Column> columns;
    private Search search;

    public Pageable toPageable() {

        if (order != null && !order.isEmpty()) {
            Order order = this.order.get(0);
            return PageRequest.of(start/10, length, Sort.by(Sort.Direction.fromString(order.getDir().name()), "auditSection.dateCreated"));
        }

        return PageRequest.of(start/10, length, Sort.by(Sort.Direction.DESC, "auditSection.dateCreated"));
    }

}