package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IDiaryEntry {

    public String getDiaryNumber();
    public Date getDiaryDate();
    public AuditSection getAuditSection();

    void setIsDeleted(boolean b);

    void setDeletedAt(Date date);

    void setDeletedBy(String string);

    void setAmountClaimed(BigDecimal totalAmountClaimed);

    void setCalculationSheetAdjustmentFactor(Double adjustmentFactor);

    void setAdmissibleAmount(BigDecimal bigDecimal);

    void setCalculationSheet(List<CalculationSheetEntry> calculationSheetEntries);
}
