package com.delhipolice.mediclaim.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
public class CalcSheetVO {
    Long diaryNo;
    LinkedList<String> itemNo;
    LinkedList<String> itemHosp;
    LinkedList<String> itemDate;
    LinkedList<String> itemName;
    LinkedList<Float> total_asked;
    LinkedList<Float> total;

}
