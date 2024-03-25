package com.delhipolice.mediclaim.model.audit;

import com.delhipolice.mediclaim.utils.CloneUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Embeddable
public class AuditSection implements Serializable {


  private static final long serialVersionUID = 1L;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED_AT")
  private Date dateCreated = new Date();

  @Setter
  @Column(name = "CREATED_BY", length = 60)
  @CreatedBy
  private String createdBy;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "MODIFIED_AT")
  private Date dateModified;

  @Setter
  @Getter
  @Column(name = "UPDATED_BY", length = 60)
  @LastModifiedBy
  private String modifiedBy;

  public AuditSection() {}

  public Date getDateCreated() {
    return CloneUtils.clone(dateCreated);
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = CloneUtils.clone(dateCreated);
  }

  public Date getDateModified() {
    return CloneUtils.clone(dateModified);
  }

  public void setDateModified(Date dateModified) {
    this.dateModified = CloneUtils.clone(dateModified);
  }
}
