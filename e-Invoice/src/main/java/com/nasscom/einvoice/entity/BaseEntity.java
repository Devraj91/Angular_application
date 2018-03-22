package com.nasscom.einvoice.entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
/**
 * This is the base entity and other entities will inherit from it
 * @author vipin.chaudhary1
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;	
	@Column(name = "create_time", updatable = false)	
	protected LocalDateTime created;
	@Column(name = "last_updated")
	//@Convert(converter = LocalDateTimeConverter.class)
	protected LocalDateTime updated;

	@PrePersist
	protected void onCreate() {
		created = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = LocalDateTime.now();
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}