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
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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

    @Autowired
    CurrencyFormatUtil currencyFormatUtil;


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

        if(diaryEntryVO.getHospital().getId() != null) {
            Hospital hospital = hospitalService.find(diaryEntryVO.getHospital().getId());
            try{
                hospital.getHospitalAddress().setAddressLine(diaryEntryVO.getHospital().getHospitalAddress().getAddressLine());
                hospitalService.save(hospital);
            } catch (Exception e) {
                log.error(e.toString());
            }
            diaryEntry.setHospital(hospital);
        } else {
            Hospital hospital = hospitalService.save(diaryEntryVO.getHospital());
            diaryEntry.setHospital(hospital);
        }

        diaryEntry.setApplicant(applicant);




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
            log.info("evt=fetchEntries pagingRequest=" + pagingRequest + " pageable=" + pageable.toString() + " totalElements=" + diaryEntries.getTotalElements() + " totalPages=" + diaryEntries.getTotalPages() + " numberOfElements=" + diaryEntries.getNumberOfElements() + " size=" + diaryEntries.getSize());
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

    private Pair<Integer, Double> getRowSpanAndSum(String date, List<String> serialNoDate, LinkedList<Double> totalGranted) {
        Map<String, Integer> indexValueMap = new LinkedHashMap<>();
        for (int i = 0; i < serialNoDate.size(); i++) {
            String value = serialNoDate.get(i);
            if (!"NULL".equals(value)) {
                indexValueMap.put(value, i);
            }
        }

        Iterator<Map.Entry<String, Integer>> iterator = indexValueMap.entrySet().iterator();
        Integer valueAtDate = 1;
        Integer valueAtNextDate = serialNoDate.size();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getKey().equals(date)) {
                valueAtDate = entry.getValue();
                if (iterator.hasNext()) {
                    valueAtNextDate = iterator.next().getValue();
                }
                break;
            }
        }

        Double sum = totalGranted.subList(valueAtDate, valueAtNextDate).stream().mapToDouble(Double::doubleValue).sum();
        int rowSpan = valueAtNextDate - valueAtDate;
        return new ImmutablePair<>(rowSpan, sum);
    }

    @Override
    public void saveCalSheet(CalcSheetVO calcSheetVO) {

        LinkedList<String> itemNo = calcSheetVO.getItemNo();
        LinkedList<String> itemHosp = calcSheetVO.getItemHosp();
        LinkedList<String> itemDate = calcSheetVO.getItemDate();
        LinkedList<String> itemName = calcSheetVO.getItemName();
        LinkedList<Double> totalAsked = calcSheetVO.getTotal_asked();
        LinkedList<Double> totalGranted = calcSheetVO.getTotal();

        LinkedList<String> serialNoDate = calcSheetVO.getSerialNoDate() == null ? new LinkedList<>() : calcSheetVO.getSerialNoDate();
        LinkedList<String> serialNoDescription = calcSheetVO.getSerialNoDescription();

        Map<String, String> serialNoDateAndDescription =  getSerialNoANdDescriptionAsMap(calcSheetVO);



        List<SerialNumberCalculationSheet> serialNumberCalculationSheets = new ArrayList<>();
        for(int j = 0; j<serialNoDate.size(); j++) {
            SerialNumberCalculationSheet.SerialNumberCalculationSheetBuilder builder = SerialNumberCalculationSheet.builder();
            builder.serialNumberDate(serialNoDate.get(j))
                    .serialNumberDescription(serialNoDescription.get(j));
            serialNumberCalculationSheets.add(builder.build());
        }

        List<CalculationSheetEntry> calculationSheetEntries = new ArrayList<>();

        int count = itemNo.size();
        for(int i = 0; i<count; i++) {

            CalculationSheetEntry.CalculationSheetEntryBuilder builder = CalculationSheetEntry.builder();
            builder.serialNumber("NULL".equals(itemNo.get(i)) ? "" : itemNo.get(i))
                    .billNumber("NULL".equals(itemHosp.get(i)) ? "" : itemHosp.get(i) )
                    .billDate("NULL".equals(itemDate.get(i)) ? "" : itemDate.get(i) )
                    .treatment("NULL".equals(itemName.get(i)) ? "" : itemName.get(i) )
                    .amountAsked(totalAsked.get(i))
                    .total(totalGranted.get(i))
                    .displayAmountGranted(currencyFormatUtil.formatCurrencyInr((totalGranted.get(i).toString())));
            calculationSheetEntries.add(builder.build());
        }

        int rowSpan = 1;

        for (int i = 0; i < calculationSheetEntries.size(); i++) {
            CalculationSheetEntry ce = calculationSheetEntries.get(i);
            if(StringUtils.isNotEmpty(ce.getBillDate()) && serialNoDateAndDescription.containsKey(itemDate.get(i))) {
                ce.setSerialNoDescription(serialNoDateAndDescription.get(itemDate.get(i)));
                Pair<Integer, Double> pair = getRowSpanAndSum(itemDate.get(i), itemDate, totalGranted);
                rowSpan = 0; // set this to zero for future rows.
                ce.setSerialNoRowSpan(pair.getLeft());
                ce.setSerialNoTotal(pair.getRight());
                ce.setDisplayAmountGranted(serialNoDateAndDescription.get(itemDate.get(i)) + "<br> <b>TOTAL: " +  currencyFormatUtil.formatCurrencyInr(pair.getRight()) + "</b>");
            } else {
                if(StringUtils.isNotEmpty(ce.getBillDate())) {
                    rowSpan = 1;
                }
                ce.setSerialNoRowSpan(rowSpan);
            }
        }

        BigDecimal totalAmountClaimed = BigDecimal.valueOf(roundOff(totalAsked.stream().reduce(0d, Double::sum)));
        Double amountGranted = roundOff(totalGranted.stream().reduce(0d, Double::sum));

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
        diaryEntry.setSerialNumberCalculationSheet(serialNumberCalculationSheets);

        if("DiaryEntry".equals(calcSheetVO.getDiaryClass())) {
            diaryEntryRepository.save((DiaryEntry) diaryEntry);
        } else if ("ExpiryDiaryEntry".equals(calcSheetVO.getDiaryClass())){
            expiryDiaryEntryRepository.save((ExpiryDiaryEntry) diaryEntry);
        }
    }

    private static Map<String, String> getSerialNoANdDescriptionAsMap(CalcSheetVO calcSheetVO) {
        Map<String, String> serialNoDateAndDescription = new HashMap<>();
        if(calcSheetVO.getSerialNoDate() == null) {
            return serialNoDateAndDescription;
        }
        int c = calcSheetVO.getSerialNoDate().size();
        for (int j = 0; j < c; j++) {
            serialNoDateAndDescription.put(calcSheetVO.getSerialNoDate().get(j), calcSheetVO.getSerialNoDescription().get(j));
        }
        return serialNoDateAndDescription;
    }


    private Double roundOff(double value) {
        double decimalPart = value - Math.floor(value);
        if(decimalPart < 0.5) {
            value = Math.floor(value);
        } else {
            value = Math.ceil(value);
        }
        return value;
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
