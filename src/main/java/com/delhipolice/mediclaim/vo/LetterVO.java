package com.delhipolice.mediclaim.vo;

import com.delhipolice.mediclaim.model.DiaryEntry;
import com.delhipolice.mediclaim.model.Letter;
import com.delhipolice.mediclaim.model.audit.AuditSection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class LetterVO {

    private UUID id;
    @Temporal(TemporalType.DATE)
    private Date letterDate;
    private List<DiaryEntryVO> diaryEntryVOS = new ArrayList<>();
    private AuditSection auditSection;
    private BigDecimal totalAmount;

    public LetterVO(Letter letter) {
        this.id = letter.getId();
        this.letterDate = letter.getLetterDate();
        this.diaryEntryVOS = letter.getDiaryEntries().stream().map(DiaryEntryVO::new).collect(Collectors.toList());
        this.auditSection = letter.getAuditSection();
        this.totalAmount = letter.getDiaryEntries().stream().map(DiaryEntry::getSanctionAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
