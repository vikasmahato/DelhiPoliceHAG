package com.delhipolice.mediclaim.model;

import com.delhipolice.mediclaim.model.audit.AuditSection;

import java.util.Date;

public interface IDiaryEntry {

    public String getDiaryNumber();
    public Date getDiaryDate();
    public AuditSection getAuditSection();

    void setIsDeleted(boolean b);

    void setDeletedAt(Date date);
}
