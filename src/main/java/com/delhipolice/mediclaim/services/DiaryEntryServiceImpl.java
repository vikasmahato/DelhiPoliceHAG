package com.delhipolice.mediclaim.services;

import com.delhipolice.mediclaim.constants.ClaimStatus;
import com.delhipolice.mediclaim.constants.ClaimType;
import com.delhipolice.mediclaim.constants.DiaryType;
import com.delhipolice.mediclaim.model.*;
import com.delhipolice.mediclaim.repositories.*;
import com.delhipolice.mediclaim.utils.*;
import com.delhipolice.mediclaim.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DiaryEntryServiceImpl implements DiaryEntryService{

    @Autowired
    DiaryEntryRepository diaryEntryRepository;

    @Autowired
    HealthCheckupDiaryEntryRepository healthCheckupDiaryEntryRepository;

    @Autowired
    HealthCheckupApplicantRepository healthCheckupApplicantRepository;


    @Autowired
    ReferralApplicantRepository referralApplicantRepository;

    @Autowired
    ReferralDiaryEntryRepository referralDiaryEntryRepository;

    @Autowired
    ExpiryDiaryEntryRepository expiryDiaryEntryRepository;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    ApplicantService applicantService;


    public DiaryEntryServiceImpl() {
    }

    @Override
    public Optional<DiaryEntryVO> findDiaryEntry(Long id) {
        DiaryEntry diaryEntry = diaryEntryRepository.findDiaryEntry(id);
        return Optional.of(new DiaryEntryVO(diaryEntry));
    }

    @Override
    public Optional<HealthCheckupDiaryEntryVo> findHealthCheckupDiaryEntry(Long id) {
        Optional<HealthCheckupDiaryEntry> diaryEntry = healthCheckupDiaryEntryRepository.findById(id);
        return diaryEntry.map(HealthCheckupDiaryEntryVo::new);
    }

    @Override
    public Optional<ReferralDiaryEntryVO> findReferralDiaryEntry(Long id) {
        Optional<ReferralDiaryEntry> diaryEntry = referralDiaryEntryRepository.findById(id);
        return diaryEntry.map(ReferralDiaryEntryVO::new);
    }

    @Override
    public Optional<ExpiryDiaryEntryVO> findExpiryDiaryEntry(Long id) {
        Optional<ExpiryDiaryEntry> diaryEntry = expiryDiaryEntryRepository.findById(id);
        return diaryEntry.map(ExpiryDiaryEntryVO::new);
    }

    @Override
    public DiaryEntry save(DiaryEntryVO diaryEntryVO) {

        DiaryEntry diaryEntry = new DiaryEntry(diaryEntryVO);

        Applicant applicant = applicantService.findByPisNumber(diaryEntryVO.getApplicant().getPisNumber());

        if(applicant != null) {
            applicant.setName(diaryEntryVO.getApplicant().getName());
            applicant.setGender(diaryEntryVO.getApplicant().getGender());
            applicant.setName(diaryEntryVO.getApplicant().getName());
            applicant.setCghsNumber(diaryEntryVO.getApplicant().getCghsNumber());
            applicant.setCghsCategory(diaryEntryVO.getApplicant().getCghsCategory());
            applicant = applicantService.update(applicant);
        } else {
            applicant = new Applicant(diaryEntryVO.getApplicant());
        }

        diaryEntry.setApplicant(applicant);

        Hospital hospital = hospitalService.find(diaryEntryVO.getHospital().getId());

        diaryEntry.setHospital(hospital);
        diaryEntry.getClaimDetails().setClaimStatus(ClaimStatus.D_HAND);

        if(diaryEntryVO.getId() != null) {
            diaryEntry.setId(diaryEntryVO.getId());
            diaryEntry.getAuditSection().setModifiedBy(User.getLoggedInUser().getUsername());
            diaryEntry.getAuditSection().setDateModified(new Date());
        } else {
            diaryEntry.getAuditSection().setCreatedBy(User.getLoggedInUser().getUsername());
        }

        return diaryEntryRepository.save(diaryEntry);
    }

    @Override
    public HealthCheckupDiaryEntry save(HealthCheckupDiaryEntryVo diaryEntryVO) {
        diaryEntryVO.getHealthCheckupApplicants().removeIf(x -> StringUtils.isEmpty(x.getApplicantDetails()) && StringUtils.isEmpty(x.getRegisterNumber()));
        HealthCheckupDiaryEntry diaryEntry = new HealthCheckupDiaryEntry(diaryEntryVO);
        if(diaryEntryVO.getId() != null) {
            diaryEntry.setId(diaryEntryVO.getId());
            HealthCheckupDiaryEntry exisitingEntry = healthCheckupDiaryEntryRepository.findById(diaryEntryVO.getId()).get();
            healthCheckupApplicantRepository.deleteAll(exisitingEntry.getHealthCheckupApplicants());
            diaryEntry.setHealthCheckupApplicants(diaryEntryVO.getHealthCheckupApplicants());
            diaryEntry.getAuditSection().setModifiedBy(User.getLoggedInUser().getUsername());
            diaryEntry.getAuditSection().setDateModified(new Date());
        } else {
            diaryEntry.getAuditSection().setCreatedBy(User.getLoggedInUser().getUsername());
        }
        return healthCheckupDiaryEntryRepository.save(diaryEntry);
    }

    @Override
    public ReferralDiaryEntry save(ReferralDiaryEntryVO diaryEntryVO) {
        diaryEntryVO.getReferralApplicants().removeIf(x -> StringUtils.isEmpty(x.getApplicantDetails()) && StringUtils.isEmpty(x.getRegisterNumber()));
        ReferralDiaryEntry diaryEntry = new ReferralDiaryEntry(diaryEntryVO);
        if(diaryEntryVO.getId() != null) {
            diaryEntry.setId(diaryEntryVO.getId());

            ReferralDiaryEntry exisitingEntry = referralDiaryEntryRepository.findById(diaryEntryVO.getId()).get();
            referralApplicantRepository.deleteAll(exisitingEntry.getReferralApplicants());
            diaryEntry.setReferralApplicants(diaryEntryVO.getReferralApplicants());
            diaryEntry.getAuditSection().setModifiedBy(User.getLoggedInUser().getUsername());
            diaryEntry.getAuditSection().setDateModified(new Date());
        } else {
            diaryEntry.getAuditSection().setCreatedBy(User.getLoggedInUser().getUsername());
        }
        return referralDiaryEntryRepository.save(diaryEntry);
    }

    @Override
    public ExpiryDiaryEntry save(ExpiryDiaryEntryVO diaryEntryVO) {
        ExpiryDiaryEntry diaryEntry = new ExpiryDiaryEntry(diaryEntryVO);

        Applicant applicant = applicantService.findByPisNumber(diaryEntryVO.getApplicant().getPisNumber());

        if(applicant != null) {
            applicant.setName(diaryEntryVO.getApplicant().getName());
            applicant.setGender(diaryEntryVO.getApplicant().getGender());
            applicant.setName(diaryEntryVO.getApplicant().getName());
            applicant.setCghsNumber(diaryEntryVO.getApplicant().getCghsNumber());
            applicant.setCghsCategory(diaryEntryVO.getApplicant().getCghsCategory());
            applicant = applicantService.update(applicant);
        } else {
            applicant = new Applicant(diaryEntryVO.getApplicant());
        }

        diaryEntry.setApplicant(applicant);

        Hospital hospital = hospitalService.find(diaryEntryVO.getHospital().getId());

        diaryEntry.setHospital(hospital);
        diaryEntry.getClaimDetails().setClaimStatus(ClaimStatus.D_HAND);

        if(diaryEntryVO.getId() != null) {
            diaryEntry.setId(diaryEntryVO.getId());
            diaryEntry.getAuditSection().setModifiedBy(User.getLoggedInUser().getUsername());
            diaryEntry.getAuditSection().setDateModified(new Date());
        } else {
            diaryEntry.getAuditSection().setCreatedBy(User.getLoggedInUser().getUsername());
        }

        return expiryDiaryEntryRepository.save(diaryEntry);
    }

    @Override
    public List<DiaryEntry> findAll(List<DiaryEntryVO> diaryEntryVOS) {
        List<Long> Longs = diaryEntryVOS.stream().map(DiaryEntryVO::getId).collect(Collectors.toList());
        return diaryEntryRepository.findAllById(Longs);
    }

    @Override
    public List<DiaryEntry> findAllByLongs(List<Long> Longs) {
        return diaryEntryRepository.findAllById(Longs);
    }

    @Override
    public List<DiaryEntryVO> findAllByApplicant(Applicant applicant) {
        return diaryEntryRepository.findAll(applicant.getId()).stream().map(DiaryEntryVO::new).collect(Collectors.toList());
    }

    @Override
    public DiaryEntry update(DiaryEntryVO diaryEntryVO) {
        DiaryEntry diaryEntry = diaryEntryRepository.findById(diaryEntryVO.getId()).get();
        diaryEntry.setAmountClaimed(diaryEntryVO.getAmountClaimed());
        diaryEntry.getClaimDetails().setStartDate(diaryEntryVO.getClaimDetails().getStartDate());
        diaryEntry.getClaimDetails().setEndDate(diaryEntryVO.getClaimDetails().getEndDate());
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
    public Page<ExpiryDiaryEntryVO> getExpiryDiaryEntries(PagingRequest pagingRequest) {

        if(StringUtils.isNotEmpty(pagingRequest.getSearch().getValue())) {
            List<ExpiryDiaryEntry> diaryEntries = expiryDiaryEntryRepository.findAll(ClaimType.EMERGENCY);
            List<ExpiryDiaryEntryVO> filtered = diaryEntries.stream()
                    .filter(filterDiaryEntries(pagingRequest))
                    .sorted(Comparator.comparing(e -> e.getAuditSection().getDateCreated()))
                    .skip(pagingRequest.getStart())
                    .limit(pagingRequest.getLength())
                    .map(ExpiryDiaryEntryVO::new)
                    .collect(Collectors.toList());

            return new Page<>(filtered, diaryEntries.size(), diaryEntries.size(), pagingRequest.getDraw());

        } else {
            Pageable pageable = pagingRequest.toPageable();
            org.springframework.data.domain.Page<ExpiryDiaryEntry> diaryEntries = expiryDiaryEntryRepository.findAll(ClaimType.EMERGENCY, pageable);
            List<ExpiryDiaryEntryVO> content = diaryEntries.getContent().stream().map(ExpiryDiaryEntryVO::new).collect(Collectors.toList());
            return new Page<>(content, diaryEntries.getTotalElements(), diaryEntries.getTotalElements(), pagingRequest.getDraw());
        }

    }

    @Override
    public Page<DiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, List<ClaimType> claimTypes,  DiaryType diaryType) {

        if(StringUtils.isNotEmpty(pagingRequest.getSearch().getValue())) {
            List<IDiaryEntry> diaryEntries = diaryEntryRepository.findAll(claimTypes, diaryType);
            List<DiaryEntryVO> filtered = diaryEntries.stream()
                    .filter(filterDiaryEntries(pagingRequest))
                    .sorted(Comparator.comparing(e -> e.getAuditSection().getDateCreated()))
                    .skip(pagingRequest.getStart())
                    .limit(pagingRequest.getLength())
                    .map(diaryEntry -> new DiaryEntryVO((DiaryEntry) diaryEntry))
                    .collect(Collectors.toList());

            return new Page<>(filtered, diaryEntries.size(), diaryEntries.size(), pagingRequest.getDraw());

        } else {
            Pageable pageable = pagingRequest.toPageable();
            org.springframework.data.domain.Page<DiaryEntry> diaryEntries = diaryEntryRepository.findAll(claimTypes, diaryType, pageable);
            List<DiaryEntryVO> content = diaryEntries.getContent().stream().map(DiaryEntryVO::new).collect(Collectors.toList());
            return new Page<>(content, diaryEntries.getTotalElements(), diaryEntries.getTotalElements(), pagingRequest.getDraw());
        }
    }

    @Override
    public Page<ReferralDiaryEntryVO> getDiaryEntries(PagingRequest pagingRequest, ClaimType claimType) {
        if(StringUtils.isNotEmpty(pagingRequest.getSearch().getValue())) {
            List<IDiaryEntry> diaryEntries = referralDiaryEntryRepository.findAllEntries();
            List<ReferralDiaryEntryVO> filtered = diaryEntries.stream()
                    .filter(filterDiaryEntries(pagingRequest))
                    .sorted(Comparator.comparing(e -> e.getAuditSection().getDateCreated()))
                    .skip(pagingRequest.getStart())
                    .limit(pagingRequest.getLength())
                    .map(diaryEntry -> new ReferralDiaryEntryVO((ReferralDiaryEntry) diaryEntry))
                    .collect(Collectors.toList());

            return new Page<>(filtered, diaryEntries.size(), diaryEntries.size(), pagingRequest.getDraw());

        } else {
            org.springframework.data.domain.Page<ReferralDiaryEntry> diaryEntries = referralDiaryEntryRepository.findAll(pagingRequest.toPageable());
            List<ReferralDiaryEntryVO> content = diaryEntries.getContent().stream().map(ReferralDiaryEntryVO::new).collect(Collectors.toList());
            return new Page<>(content, diaryEntries.getTotalElements(), diaryEntries.getTotalElements(), pagingRequest.getDraw());
        }

    }

    @Override
    public void saveCalSheet(CalcSheetVO calcSheetVO) {

        LinkedList<String> itemNo = calcSheetVO.getItemNo();
        LinkedList<String> itemHosp = calcSheetVO.getItemHosp();
        LinkedList<String> itemDate = calcSheetVO.getItemDate();
        LinkedList<String> itemName = calcSheetVO.getItemName();
        LinkedList<Double> totalAsked = calcSheetVO.getTotal_asked();
        LinkedList<Double> totalGranted = calcSheetVO.getTotal();

        int count = itemNo.size();

        List<CalculationSheetEntry> calculationSheetEntries = new ArrayList<>();

        for(int i = 0; i<count; i++) {

            CalculationSheetEntry.CalculationSheetEntryBuilder builder = CalculationSheetEntry.builder();
            builder.serialNumber("NULL".equals(itemNo.get(i)) ? "" : itemNo.get(i))
                    .billNumber("NULL".equals(itemHosp.get(i)) ? "" : itemHosp.get(i) )
                    .billDate("NULL".equals(itemDate.get(i)) ? "" : itemDate.get(i) )
                    .treatment("NULL".equals(itemName.get(i)) ? "" : itemName.get(i) )
                    .amountAsked(totalAsked.get(i))
                    .total(totalGranted.get(i));
            calculationSheetEntries.add(builder.build());
        }

        BigDecimal totalAmountClaimed = BigDecimal.valueOf(totalAsked.stream().reduce(0d, Double::sum));
        Double amountGranted = totalGranted.stream().reduce(0d, Double::sum);
        String adjustmentDescription = calcSheetVO.getAdjustmentDescription();
        Double adjustmentFactor = calcSheetVO.getAdjustmentFactor();

        IDiaryEntry diaryEntry = null;
        if("DiaryEntry".equals(calcSheetVO.getDiaryClass())) {
            diaryEntry = diaryEntryRepository.findById(calcSheetVO.getDiaryNo()).get();
        } else if ("ExpiryDiaryEntry".equals(calcSheetVO.getDiaryClass())){
            diaryEntry = expiryDiaryEntryRepository.findById(calcSheetVO.getDiaryNo()).get();
        }

        assert diaryEntry != null;
        diaryEntry.setAmountClaimed(totalAmountClaimed);

        if(adjustmentFactor != null && adjustmentFactor != 0d) {
            diaryEntry.setCalculationSheetAdjustmentFactor(adjustmentFactor);
            double adjustmentAmount = amountGranted * (adjustmentFactor/100);
            amountGranted += adjustmentAmount;

            CalculationSheetEntry.CalculationSheetEntryBuilder builder = CalculationSheetEntry.builder();
            builder.serialNumber("0").billNumber("").billDate("").treatment(adjustmentDescription).amountAsked(0d).total(adjustmentAmount).isAdjustment(true);
            calculationSheetEntries.add(builder.build());
        }
        diaryEntry.setAdmissibleAmount(BigDecimal.valueOf(amountGranted));
        diaryEntry.setCalculationSheet(calculationSheetEntries);

        if("DiaryEntry".equals(calcSheetVO.getDiaryClass())) {
            diaryEntryRepository.save((DiaryEntry) diaryEntry);
        } else if ("ExpiryDiaryEntry".equals(calcSheetVO.getDiaryClass())){
            expiryDiaryEntryRepository.save((ExpiryDiaryEntry) diaryEntry);
        }
    }

    @Override
    public int count() {
        return (int) diaryEntryRepository.count();
    }

    @Override
    public void deleteDiaryEntry(Long id, String diaryEntryClass) {
        IDiaryEntry diaryEntry = null;

        switch (diaryEntryClass) {
            case "DiaryEntry":
                diaryEntry = diaryEntryRepository.findById(id).orElse(null);
                break;
            case "ReferralDiaryEntry":
                diaryEntry = referralDiaryEntryRepository.findById(id).orElse(null);
                break;
            case "HealthCheckupDiaryEntry":
                diaryEntry = healthCheckupDiaryEntryRepository.findById(id).orElse(null);
                break;
            case "ExpiryDiaryEntry":
                diaryEntry = expiryDiaryEntryRepository.findById(id).orElse(null);
                break;
        }

        if (diaryEntry != null) {
            diaryEntry.setIsDeleted(true);
            diaryEntry.setDeletedAt(new Date());
            diaryEntry.setDeletedBy(User.getLoggedInUser().getUsername());


            switch (diaryEntryClass) {
                case "DiaryEntry":
                    diaryEntryRepository.save((DiaryEntry) diaryEntry);
                    break;
                case "ReferralDiaryEntry":
                    referralDiaryEntryRepository.save((ReferralDiaryEntry) diaryEntry);
                    break;
                case "HealthCheckupDiaryEntry":
                    healthCheckupDiaryEntryRepository.save((HealthCheckupDiaryEntry) diaryEntry);
                    break;
                case "ExpiryDiaryEntry":
                    expiryDiaryEntryRepository.save((ExpiryDiaryEntry) diaryEntry);
                    break;
            }
        }
    }

    private Page<HealthCheckupDiaryEntryVo> getPageHealthCheckup(List<HealthCheckupDiaryEntry> diaryEntries, PagingRequest pagingRequest) {
        List<HealthCheckupDiaryEntryVo> filtered = diaryEntries.stream()
                .sorted(Comparator.comparing(e -> e.getAuditSection().getDateCreated()))
                .filter(filterDiaryEntries(pagingRequest))
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

        String value = pagingRequest.getSearch().getValue();

        return diaryEntry -> {
            if (diaryEntry instanceof DiaryEntry) {
                DiaryEntry diaryEntryCast = (DiaryEntry) diaryEntry;
                return diaryEntryCast.getApplicant().getName().toLowerCase().contains(value)
                        || diaryEntryCast.getDiaryNumber().toLowerCase().contains(value)
                        || diaryEntryCast.getTreatmentTakenBy().getEnumValue().toLowerCase().contains(value);
            }  if (diaryEntry instanceof ExpiryDiaryEntry) {
                ExpiryDiaryEntry diaryEntryCast = (ExpiryDiaryEntry) diaryEntry;
                return diaryEntryCast.getApplicant().getName().toLowerCase().contains(value)
                        || diaryEntryCast.getDiaryNumber().toLowerCase().contains(value)
                        || diaryEntryCast.getTreatmentTakenBy().getEnumValue().toLowerCase().contains(value);
            } else if (diaryEntry instanceof ReferralDiaryEntry) {
                ReferralDiaryEntry referralDiaryEntryCast = (ReferralDiaryEntry) diaryEntry;
                return referralDiaryEntryCast.getReferralApplicants()
                        .stream().anyMatch(applicant -> applicant.getApplicantDetails().toLowerCase().contains(value));
            } else if( diaryEntry instanceof HealthCheckupDiaryEntry) {
                HealthCheckupDiaryEntry healthCheckupDiaryEntry = (HealthCheckupDiaryEntry) diaryEntry;
                return healthCheckupDiaryEntry.getHealthCheckupApplicants()
                        .stream().anyMatch(applicant -> applicant.getApplicantDetails().toLowerCase().contains(value));
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
}
