package church.ministry.att.hibernate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MINISTER_EVALUATION")
public class MinisterEvaluation {

	@Id
	@Column(name = "ID")
	private int id;

	@Column(name = "ACTION_DATE")
	@Temporal(TemporalType.DATE)
	private Date actionDate;

	@Column(name = "MASS")
	private int mass;

	@Column(name = "MINISTERS_MEETING")
	private int ministersMeeting;

	@Column(name = "FAMILY_MEETING")
	private int familyMeeting;

	@Column(name = "MINISTRY")
	private int ministry;

	@Column(name = "LESSON_PREPARATION")
	private int lessonPreparaion;

	@Column(name = "ILLUSTRATION_TOOL")
	private int illustrationTool;

	@Column(name = "ATTENDANTS_COUNT")
	private int attendantsCount;

	@Column(name = "FOLLOWUP_COUNT")
	private int followupCount;

	@Column(name = "ATTENDANTS_FOLLOWUP_COUNT")
	private int attendantsFollowupCount;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the actionDate
	 */
	public Date getActionDate() {
		return actionDate;
	}

	/**
	 * @param actionDate the actionDate to set
	 */
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	/**
	 * @return the mass
	 */
	public int getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(int mass) {
		this.mass = mass;
	}

	/**
	 * @return the ministersMeeting
	 */
	public int getMinistersMeeting() {
		return ministersMeeting;
	}

	/**
	 * @param ministersMeeting the ministersMeeting to set
	 */
	public void setMinistersMeeting(int ministersMeeting) {
		this.ministersMeeting = ministersMeeting;
	}

	/**
	 * @return the familyMeeting
	 */
	public int getFamilyMeeting() {
		return familyMeeting;
	}

	/**
	 * @param familyMeeting the familyMeeting to set
	 */
	public void setFamilyMeeting(int familyMeeting) {
		this.familyMeeting = familyMeeting;
	}

	/**
	 * @return the ministry
	 */
	public int getMinistry() {
		return ministry;
	}

	/**
	 * @param ministry the ministry to set
	 */
	public void setMinistry(int ministry) {
		this.ministry = ministry;
	}

	/**
	 * @return the lessonPreparaion
	 */
	public int getLessonPreparaion() {
		return lessonPreparaion;
	}

	/**
	 * @param lessonPreparaion the lessonPreparaion to set
	 */
	public void setLessonPreparaion(int lessonPreparaion) {
		this.lessonPreparaion = lessonPreparaion;
	}

	/**
	 * @return the illustrationTool
	 */
	public int getIllustrationTool() {
		return illustrationTool;
	}

	/**
	 * @param illustrationTool the illustrationTool to set
	 */
	public void setIllustrationTool(int illustrationTool) {
		this.illustrationTool = illustrationTool;
	}

	/**
	 * @return the attendantsCount
	 */
	public int getAttendantsCount() {
		return attendantsCount;
	}

	/**
	 * @param attendantsCount the attendantsCount to set
	 */
	public void setAttendantsCount(int attendantsCount) {
		this.attendantsCount = attendantsCount;
	}

	/**
	 * @return the followupCount
	 */
	public int getFollowupCount() {
		return followupCount;
	}

	/**
	 * @param followupCount the followupCount to set
	 */
	public void setFollowupCount(int followupCount) {
		this.followupCount = followupCount;
	}

	/**
	 * @return the attendantsFollowupCount
	 */
	public int getAttendantsFollowupCount() {
		return attendantsFollowupCount;
	}

	/**
	 * @param attendantsFollowupCount the attendantsFollowupCount to set
	 */
	public void setAttendantsFollowupCount(int attendantsFollowupCount) {
		this.attendantsFollowupCount = attendantsFollowupCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MinisterEvaluation [id=" + id + ", actionDate=" + actionDate + ", mass=" + mass + ", ministersMeeting="
				+ ministersMeeting + ", familyMeeting=" + familyMeeting + ", ministry=" + ministry
				+ ", lessonPreparaion=" + lessonPreparaion + ", illustrationTool=" + illustrationTool
				+ ", attendantsCount=" + attendantsCount + ", followupCount=" + followupCount
				+ ", attendantsFollowupCount=" + attendantsFollowupCount + "]";
	}

}
