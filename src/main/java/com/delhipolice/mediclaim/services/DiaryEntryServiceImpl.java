package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimStatus;
import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.model.comparators.DiaryEntryComparators;
import com.delhipolice.mediclaim.model.comparators.HelathCheckupDiaryEntryComparators;
import com.delhipolice.mediclaim.repositories.DiaryEntryRepository;
import com.delhipolice.mediclaim.repositories.HealthCheckupDiaryEntryRepository;
import com.delhipolice.mediclaim.repositories.ReferralDiaryEntryRepository;
import com.delhipolice.mediclaim.utils.*;
import com.delhipolice.mediclaim.vo.*;
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

    private static final Comparator<IDiaryEntry> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private static final Comparator<HealthCheckupDiaryEntry> HEALTH_CHECKUP_DIARY_ENTRY_EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    DiaryEntryRepository diaryEntryRepository;

    @Autowired
    HealthCheckupDiaryEntryRepository healthCheckupDiaryEntryRepository;

    @Autowired
    ReferralDiaryEntryRepository referralDiaryEntryRepository;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    ApplicantService applicantService;


    public DiaryEntryServiceImpl() {
    }

    @Override
    public Optional<DiaryEntryVO> find(UUID id) {
        Optional<DiaryEntry> diaryEntry = diaryEntryRepository.findById(id);
        return diaryEntry.map(DiaryEntryVO::new);
    }

    @Override
    public Optional<HealthCheckupDiaryEntryVo> find1(UUID id) {
        Optional<HealthCheckupDiaryEntry> diaryEntry = healthCheckupDiaryEntryRepository.findById(id);
        return diaryEntry.map(HealthCheckupDiaryEntryVo::new);
    }

    @Override
    public Optional<ReferralDiaryEntryVO> find2(UUID id) {
        Optional<ReferralDiaryEntry> diaryEntry = referralDiaryEntryRepository.findById(id);
        return diaryEntry.map(ReferralDiaryEntryVO::new);
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
    public HealthCheckupDiaryEntry save(HealthCheckupDiaryEntryVo diaryEntryVO) {
        HealthCheckupDiaryEntry diaryEntry = new HealthCheckupDiaryEntry(diaryEntryVO);
        return healthCheckupDiaryEntryRepository.save(diaryEntry);
    }

    @Override
    public ReferralDiaryEntry save(ReferralDiaryEntryVO diaryEntryVO) {
        ReferralDiaryEntry diaryEntry = new ReferralDiaryEntry(diaryEntryVO);
        return referralDiaryEntryRepository.save(diaryEntry);
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
    public Page<HealthCheckupDiaryEntryVo> getHealthCheckupDiaryEntries(PagingRequest pagingRequest) {
        List<HealthCheckupDiaryEntry> diaryEntries = healthCheckupDiaryEntryRepository.findAll();
        return getPageHealthCheckup(diaryEntries, pagingRequest);
    }

    @Override
    public Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes,  DiaryType diaryType) {
        List<IDiaryEntry> diaryEntries = diaryEntryRepository.findAll(claimTypes, diaryType);

        Page<IDiaryEntryVO> page =  getPage(diaryEntries, pagingRequest);

        Page<DiaryEntryVO> page1 = new Page<>(page.getData().stream().map(DiaryEntryVO.class::cast).collect(Collectors.toList()));
        page1.setRecordsFiltered(page.getRecordsFiltered());
        page1.setRecordsTotal(page.getRecordsTotal());
        page1.setDraw(page.getDraw());

        return page1;
    }

    @Override
    public Page<ReferralDiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, ClaimType claimType) {
        List<IDiaryEntry> diaryEntries = referralDiaryEntryRepository.findAllEntries();
        Page<IDiaryEntryVO> page =  getPage(diaryEntries, pagingRequest);

        Page<ReferralDiaryEntryVO> page1 = new Page<>(page.getData().stream().map(ReferralDiaryEntryVO.class::cast).collect(Collectors.toList()));
        page1.setRecordsFiltered(page.getRecordsFiltered());
        page1.setRecordsTotal(page.getRecordsTotal());
        page1.setDraw(page.getDraw());
        return page1;
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

    private Page<IDiaryEntryVO> getPage(List<IDiaryEntry> diaryEntries, PagingRequest pagingRequest) {
        List<IDiaryEntryVO> filtered = diaryEntries.stream()
                .sorted(sortEntries(pagingRequest))
                .filter(filterDiaryEntries(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .map(diaryEntry -> {
                    if (diaryEntry instanceof DiaryEntry) {
                        return new DiaryEntryVO((DiaryEntry) diaryEntry);
                    } else if (diaryEntry instanceof ReferralDiaryEntry) {
                        return new ReferralDiaryEntryVO((ReferralDiaryEntry) diaryEntry);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        long count = diaryEntries.stream()
                .filter(filterDiaryEntries(pagingRequest))
                .count();

        Page<IDiaryEntryVO> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Page<HealthCheckupDiaryEntryVo> getPageHealthCheckup(List<HealthCheckupDiaryEntry> diaryEntries, PagingRequest pagingRequest) {
        List<HealthCheckupDiaryEntryVo> filtered = diaryEntries.stream()
                .sorted(sortHealthCheckupEntries(pagingRequest))
                .filter(filterHealthCHeckupDiaryEntries(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .map(HealthCheckupDiaryEntryVo::new)
                .collect(Collectors.toList());

        long count = diaryEntries.stream()
                .filter(filterHealthCHeckupDiaryEntries(pagingRequest))
                .count();

        Page<HealthCheckupDiaryEntryVo> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<IDiaryEntry> filterDiaryEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return diaryEntry -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return diaryEntry -> {
            if (diaryEntry instanceof DiaryEntry) {
                DiaryEntry diaryEntryCast = (DiaryEntry) diaryEntry;
                return diaryEntryCast.getApplicant().getName().toLowerCase().contains(value)
                        || diaryEntryCast.getDiaryNumber().toLowerCase().contains(value)
                        || diaryEntryCast.getTreatmentTakenBy().getEnumValue().toLowerCase().contains(value);
            } else if (diaryEntry instanceof ReferralDiaryEntry) {
                ReferralDiaryEntry referralDiaryEntryCast = (ReferralDiaryEntry) diaryEntry;
                return referralDiaryEntryCast.getReferralApplicants().stream().anyMatch(applicant -> applicant.getApplicantDetails().toLowerCase().contains(value));
            }
            return false;
        };
    }

    private Predicate<HealthCheckupDiaryEntry> filterHealthCHeckupDiaryEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return diaryEntry -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return diaryEntry -> diaryEntry.getDiaryNumber()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<IDiaryEntry> sortEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<IDiaryEntry> comparator = DiaryEntryComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

    private Comparator<HealthCheckupDiaryEntry> sortHealthCheckupEntries(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return HEALTH_CHECKUP_DIARY_ENTRY_EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<HealthCheckupDiaryEntry> comparator = HelathCheckupDiaryEntryComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return HEALTH_CHECKUP_DIARY_ENTRY_EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return HEALTH_CHECKUP_DIARY_ENTRY_EMPTY_COMPARATOR;
    }
}
