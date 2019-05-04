package church.ministry.att.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MINISTER_CHILD_ASSIGNED")
public class MinisterChildAssigned {

	@Id
	@Column(name = "MINISTER_ID")
	private int ministerId;

	@Column(name = "CHILD_ID")
	private int childId;

	@Column(name = "ACTIVE_FROM")
	@Temporal(TemporalType.DATE)
	private Date activeFrom;

	@Column(name = "INACTIVE_DATE")
	@Temporal(TemporalType.DATE)
	private Date inactiveDate;

	/**
	 * @return the ministerId
	 */
	public int getMinisterId() {
		return ministerId;
	}

	/**
	 * @param ministerId the ministerId to set
	 */
	public void setMinisterId(int ministerId) {
		this.ministerId = ministerId;
	}

	/**
	 * @return the childId
	 */
	public int getChildId() {
		return childId;
	}

	/**
	 * @param childId the childId to set
	 */
	public void setChildId(int childId) {
		this.childId = childId;
	}

	/**
	 * @return the activeFrom
	 */
	public Date getActiveFrom() {
		return activeFrom;
	}

	/**
	 * @param activeFrom the activeFrom to set
	 */
	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}

	/**
	 * @return the inactiveDate
	 */
	public Date getInactiveDate() {
		return inactiveDate;
	}

	/**
	 * @param inactiveDate the inactiveDate to set
	 */
	public void setInactiveDate(Date inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinisterChildAssigned [ministerId=" + ministerId + ", childId=" + childId + ", activeFrom=" + activeFrom
				+ ", inactiveDate=" + inactiveDate + "]";
	}

}
