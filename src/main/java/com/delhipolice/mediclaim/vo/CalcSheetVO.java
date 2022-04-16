package com.delhipolice.mediclaim.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CalcSheetVO {
    UUID diaryNo;
    LinkedList<String> itemNo;
    LinkedList<String> itemHosp;
    LinkedList<String> itemDate;
    LinkedList<String> itemName;
    LinkedList<Double> total_asked;
    LinkedList<Double> total;

}
