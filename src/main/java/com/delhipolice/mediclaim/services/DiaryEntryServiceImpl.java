package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.model.comparators.DiaryEntryComparators;
import com.delhipolice.mediclaim.repositories.DiaryEntryRepository;
import com.delhipolice.mediclaim.utils.*;
import com.delhipolice.mediclaim.vo.CalcSheetVO;
import com.delhipolice.mediclaim.vo.DiaryEntryVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DiaryEntryServiceImpl implements DiaryEntryService{

    private static final Comparator<DiaryEntry> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    DiaryEntryRepository diaryEntryRepository;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    ApplicantService applicantService;


    public DiaryEntryServiceImpl() {
    }

    @Override
    public DiaryEntryVO find(Long id) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(id).get();
        return new DiaryEntryVO(diaryEntry);
    }

    @Override
    public DiaryEntry save(DiaryEntryVO diaryEntryVO) {


        DiaryEntry diaryEntry = new DiaryEntry(diaryEntryVO);

        Applicant applicant = applicantService.findByPisNumber(diaryEntryVO.getApplicant().getPisNumber());
        if(applicant!=null)
            diaryEntry.setApplicant(applicant);
        else {
            diaryEntry.setApplicant(applicantService.save(diaryEntryVO.getApplicant()));
        }

        Hospital hospital = hospitalService.find(diaryEntryVO.getHospital().getId());
        diaryEntry.setHospital(hospital);
        return diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public DiaryEntry update(DiaryEntry diaryEntry) {
        return null;
    }

    @Override
    public List<DiaryEntry> findAll() {
        return diaryEntryRepository.findAll();
    }

    @Override
    public Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest) {
        List<DiaryEntry> diaryEntries = diaryEntryRepository.findAll();
        return getPage(diaryEntries, pagingRequest);
    }

    @Override
    public void saveCalSheet(CalcSheetVO calcSheetVO) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(calcSheetVO.getDiaryNo()).get();

        LinkedList<String> itemNo = calcSheetVO.getItemNo();
        LinkedList<String> itemHosp = calcSheetVO.getItemHosp();
        LinkedList<String> itemDate = calcSheetVO.getItemDate();
        LinkedList<String> itemName = calcSheetVO.getItemName();
        LinkedList<Float> total_asked = calcSheetVO.getTotal_asked();
        LinkedList<Float> total = calcSheetVO.getTotal();

        int count = itemNo.size();

        List<CalculationSheetEntry> calculationSheetEntries = new ArrayList<>();

        for(int i = 0; i<count; i++) {
            CalculationSheetEntry calculationSheetEntry = new CalculationSheetEntry();
            calculationSheetEntry.setSerialNumber(itemNo.get(i));
            calculationSheetEntry.setBillNumber(itemHosp.get(i));
            calculationSheetEntry.setBillDate(itemDate.get(i));
            calculationSheetEntry.setTreatment(itemName.get(i));
            calculationSheetEntry.setAmountAsked(total_asked.get(i));
            calculationSheetEntry.setTotal(total.get(i));
            calculationSheetEntries.add(calculationSheetEntry);
        }

        diaryEntry.setCalculationSheet(calculationSheetEntries);

        diaryEntryRepository.save(diaryEntry);
    }

    private Page<DiaryEntryVO> getPage(List<DiaryEntry> diaryEntries, PagingRequest pagingRequest) {
        List<DiaryEntryVO> filtered = diaryEntries.stream()
                .sorted(sortEntries(pagingRequest))
                .filter(filterDiaryEntries(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .map(DiaryEntryVO::new)
                .collect(Collectors.toList());

        long count = diaryEntries.stream()
                .filter(filterDiaryEntries(pagingRequest))
                .count();

        Page<DiaryEntryVO> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<DiaryEntry> filterDiaryEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return diaryEntry -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return diaryEntry -> diaryEntry.getApplicant().getName()
                .toLowerCase()
                .contains(value)
                || diaryEntry.getDiaryNumber()
                .toLowerCase()
                .contains(value)
                || diaryEntry.getTreatmentTakenBy().getEnumValue()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<DiaryEntry> sortEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<DiaryEntry> comparator = DiaryEntryComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

   /* @Override
    public PageArray getDiaryEntriesArray(PagingRequest pagingRequest) {
        pagingRequest.setColumns(Stream.of("diaryNumber", "diaryType", "applicantName")
                .map(Column::new)
                .collect(Collectors.toList()));

        Page<DiaryEntry> diaryEntryPage = getDiaryEntries(pagingRequest);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(diaryEntryPage.getRecordsFiltered());
        pageArray.setRecordsTotal(diaryEntryPage.getRecordsTotal());
        pageArray.setDraw(diaryEntryPage.getDraw());
        pageArray.setData(diaryEntryPage.getData().stream().map(this::toStringList).collect(Collectors.toList()));


        return pageArray;
    }*/

    private List<String> toStringList(DiaryEntry diaryEntry) {
        return Arrays.asList(diaryEntry.getDiaryNumber() +"/" + sdf.format(diaryEntry.getDiaryDate()), diaryEntry.getDiaryType().getEnumValue(), diaryEntry.getApplicant().getName());
    }
}
