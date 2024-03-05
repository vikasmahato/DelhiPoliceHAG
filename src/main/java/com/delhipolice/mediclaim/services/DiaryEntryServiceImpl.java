package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimStatus;
import com.delhipolice.mediclaim.constants.ClaimType;
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

import java.math.BigDecimal;
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
    public DiaryEntryVO find(UUID id) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(id).get();
        return new DiaryEntryVO(diaryEntry);
    }

    @Override
    public DiaryEntry save(DiaryEntryVO diaryEntryVO) {


        DiaryEntry diaryEntry = new DiaryEntry(diaryEntryVO);

        Applicant applicant = applicantService.findByPisNumber(diaryEntryVO.getApplicant().getPisNumber());
        diaryEntry.setApplicant(applicant == null ? new Applicant(diaryEntryVO.getApplicant()) : applicant);

        Hospital hospital = hospitalService.find(diaryEntryVO.getHospital().getId());

        diaryEntry.setHospital(hospital);
        diaryEntry.getClaimDetails().setClaimStatus(ClaimStatus.D_HAND);
        return diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public List<DiaryEntry> findAll(List<DiaryEntryVO> diaryEntryVOS) {
        List<UUID> uuids = diaryEntryVOS.stream().map(DiaryEntryVO::getId).collect(Collectors.toList());
        return diaryEntryRepository.findAllById(uuids);
    }

    @Override
    public List<DiaryEntry> findAllByUUIDs(List<UUID> uuids) {
        return diaryEntryRepository.findAllById(uuids);
    }

    @Override
    public List<DiaryEntryVO> findAllByApplicant(Applicant applicant) {
        return diaryEntryRepository.findAll(applicant.getId()).stream().map(DiaryEntryVO::new).collect(Collectors.toList());
    }

    @Override
    public DiaryEntry update(DiaryEntryVO diaryEntryVO) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(diaryEntryVO.getId()).get();
        diaryEntry.setAmountClaimed(diaryEntryVO.getAmountClaimed());
        diaryEntry.setPhqNumber(diaryEntryVO.getPhqNumber());
        diaryEntry.setPhqDate(diaryEntryVO.getPhqDate());
        diaryEntry.setSanctionDate(diaryEntryVO.getSanctionDate());
        diaryEntry.setSanctionNumber(diaryEntryVO.getSanctionNumber());
        diaryEntry.setSanctionAmount(diaryEntryVO.getSanctionAmount());
        diaryEntry.getClaimDetails().setStartDate(diaryEntryVO.getClaimDetails().getStartDate());
        diaryEntry.getClaimDetails().setEndDate(diaryEntryVO.getClaimDetails().getEndDate());
        diaryEntry.setIsLetterGenerated(diaryEntryVO.getIsLetterGenerated());
        diaryEntry.setPatientDOB(diaryEntryVO.getPatientDOB());
        diaryEntry.getClaimDetails().setDisease(diaryEntryVO.getClaimDetails().getDisease());
        return diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public DiaryEntry update(DiaryEntry diaryEntry) {
        return diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes) {
        List<DiaryEntry> diaryEntries = diaryEntryRepository.findAll(claimTypes);
        return getPage(diaryEntries, pagingRequest);
    }

    @Override
    public void saveCalSheet(CalcSheetVO calcSheetVO) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(calcSheetVO.getDiaryNo()).get();

        LinkedList<String> itemNo = calcSheetVO.getItemNo();
        LinkedList<String> itemHosp = calcSheetVO.getItemHosp();
        LinkedList<String> itemDate = calcSheetVO.getItemDate();
        LinkedList<String> itemName = calcSheetVO.getItemName();
        LinkedList<Double> totalAsked = calcSheetVO.getTotal_asked();
        LinkedList<Double> totalGranted = calcSheetVO.getTotal();

        diaryEntry.setAmountClaimed(BigDecimal.valueOf(totalAsked.stream().reduce(0d, Double::sum)));
        diaryEntry.setAdmissibleAmount(BigDecimal.valueOf(totalGranted.stream().reduce(0d, Double::sum)));


        int count = itemNo.size();

        List<CalculationSheetEntry> calculationSheetEntries = new ArrayList<>();

        for(int i = 0; i<count; i++) {
            CalculationSheetEntry calculationSheetEntry = new CalculationSheetEntry();
            calculationSheetEntry.setSerialNumber(itemNo.get(i));
            calculationSheetEntry.setBillNumber(itemHosp.get(i));
            calculationSheetEntry.setBillDate(itemDate.get(i));
            calculationSheetEntry.setTreatment(itemName.get(i));
            calculationSheetEntry.setAmountAsked(totalAsked.get(i));
            calculationSheetEntry.setTotal(totalGranted.get(i));
            calculationSheetEntries.add(calculationSheetEntry);
        }

        diaryEntry.setCalculationSheet(calculationSheetEntries);

        diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public List<DiaryEntryVO> findCandidateDiaryEntries() {
        List<DiaryEntry> diaryEntries = diaryEntryRepository.findByIsLetterGenerated(false);
        return diaryEntries.stream().map(DiaryEntryVO::new).collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) diaryEntryRepository.count();
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
}
