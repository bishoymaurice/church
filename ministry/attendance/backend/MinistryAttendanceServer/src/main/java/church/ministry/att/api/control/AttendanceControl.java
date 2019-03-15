package church.ministry.att.api.control;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import church.ministry.att.api.control.conval.CONST;
import church.ministry.att.api.control.util.DateUtil;
import church.ministry.att.hibernate.model.ChildMassAttendance;
import church.ministry.att.hibernate.model.ChildMinistryAttendance;
import church.ministry.att.server.hibernate.util.HibernateUtil;
import io.swagger.model.AttendeeSignIn;
import io.swagger.model.Meeting;

/**
 * This class is responsible for saving meetings attendance on the database. It
 * will be called by the APIs controller.
 * 
 * @author bmauric2
 */
public class AttendanceControl {

	// Define date utility
	DateUtil dateUtil = null;

	/**
	 * The purpose of this constructor is to initialize date utility
	 */
	public AttendanceControl() {
		this.dateUtil = new DateUtil();
	}

	/**
	 * The purpose of this function is to check time and decide either attendance
	 * should be inserted on the database for the Holy Mass or for Sunday Schools.
	 * 
	 * It also makes sure that today is Friday; as it is not meant to be used at any
	 * other day until now.
	 * 
	 * @param attendee
	 * @return
	 */
	public AttendeeSignIn signIn(AttendeeSignIn attendee) {

		// Checking if today is Friday
		if (dateUtil.getDayOfWeekName().equals(CONST.SIMPLE_DATA_FORMAT_DAY_FRI)) {

			// Checking time to decide if the meeting is Holy Mass or Sunday Schools
			if (dateUtil.getHourOfDay() <= CONST.HOLY_MASS_ATT_DEADLINE_HOUR
					&& dateUtil.getMinute() < CONST.HOLY_MASS_ATT_DEADLINE_MINUTE) {

				// Holy Mass
				attendee = massSignIn(attendee);

			} else {

				// Sunday Schools
				attendee = sundaySchoolsSignIn(attendee);
			}
		}
		return attendee;
	}

	/**
	 * This function is called to register Holy Mass attendance
	 * 
	 * @param attendee
	 * @return
	 */
	private AttendeeSignIn massSignIn(AttendeeSignIn attendee) {
		// Creating boolean for sign in status
		boolean ifSignedIn = false;

		// Database session
		Session session = null;

		// Database transaction
		Transaction transaction = null;

		try {

			// Creating Holy Mass attendee entity
			ChildMassAttendance massAttendee = new ChildMassAttendance();
			massAttendee.setId(attendee.getMember().getId());
			massAttendee.setActionDate(this.dateUtil.getDayScopeCalendar().getTime());

			// Opening database session
			session = HibernateUtil.getSessionFactory().openSession();

			// *************** Check if member was already registered ***************//

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ChildMassAttendance> criteriaQuery = criteriaBuilder.createQuery(ChildMassAttendance.class);
			Root<ChildMassAttendance> root = criteriaQuery.from(ChildMassAttendance.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.equal(root.get("id"), attendee.getMember().getId()));
			criteriaQuery.where(
					criteriaBuilder.equal(root.get("actionDate"), this.dateUtil.getDayScopeCalendar().getTime()));

			List<ChildMassAttendance> attendanceResult = session.createQuery(criteriaQuery).getResultList();

			// *************** Check if member was already registered ***************// DONE

			// *************** Register ***************//

			// If no record was found, then insert new record for member attendance
			if (attendanceResult.size() == 0) {

				// Creating transaction
				transaction = session.beginTransaction();

				// Saving hibernate entity
				session.save(massAttendee);

				// Commit transaction on the database
				transaction.commit();
			}
			// *************** Register ***************// DONE

			// Sign in status is success
			ifSignedIn = true;

			// Set meeting ID and name as a confirmation for the registered meeting
			if (attendee.getMeeting() == null) {
				attendee.setMeeting(new Meeting());
			}
			attendee.getMeeting().setId(CONST.MEETING_HOLY_MASS_ID);
			attendee.getMeeting().setName(CONST.MEETING_HOLY_MASS_NAME);

		} catch (Exception ex) {

			ex.printStackTrace();

			// Sign in status is failed
			ifSignedIn = false;

			// Rollback in case of exception caught
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {

			// Closing database session
			if (session != null) {
				session.clear();
				session.close();
			}
		}

		if (ifSignedIn) {
			return attendee;
		} else {
			return null;
		}
	}

	/**
	 * This function is called to register Sunday Schools attendance
	 * 
	 * @param attendee
	 * @return
	 */
	private AttendeeSignIn sundaySchoolsSignIn(AttendeeSignIn attendee) {
		// Creating boolean for sign in status
		boolean ifSignedIn = false;

		// Database session
		Session session = null;

		// Database transaction
		Transaction transaction = null;

		try {

			// Creating ministry attendee entity
			ChildMinistryAttendance ministryAttendee = new ChildMinistryAttendance();
			ministryAttendee.setId(attendee.getMember().getId());
			ministryAttendee.setActionDate(this.dateUtil.getDayScopeCalendar().getTime());

			// Opening database session
			session = HibernateUtil.getSessionFactory().openSession();

			// *************** Check if member was already registered ***************//

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ChildMinistryAttendance> criteriaQuery = criteriaBuilder
					.createQuery(ChildMinistryAttendance.class);
			Root<ChildMinistryAttendance> root = criteriaQuery.from(ChildMinistryAttendance.class);
			criteriaQuery.select(root);

			criteriaQuery.where(criteriaBuilder.equal(root.get("id"), attendee.getMember().getId()));
			criteriaQuery.where(
					criteriaBuilder.equal(root.get("actionDate"), this.dateUtil.getDayScopeCalendar().getTime()));

			List<ChildMinistryAttendance> attendanceResult = session.createQuery(criteriaQuery).getResultList();

			// *************** Check if member was already registered ***************// DONE

			// *************** Register ***************//

			// If no record was found, then insert new record for member attendance
			if (attendanceResult.size() == 0) {

				// Creating transaction
				transaction = session.beginTransaction();

				// Saving hibernate entity
				session.save(ministryAttendee);

				// Commit transaction on the database
				transaction.commit();
			}
			// *************** Register ***************// DONE

			// Sign in status is success
			ifSignedIn = true;

			// Set meeting ID and name as a confirmation for the registered meeting
			if (attendee.getMeeting() == null) {
				attendee.setMeeting(new Meeting());
			}
			attendee.getMeeting().setId(CONST.MEETING_SUNDAY_SCHOOLS_ID);
			attendee.getMeeting().setName(CONST.MEETING_SUNDAY_SCHOOLS_NAME);

		} catch (Exception ex) {

			ex.printStackTrace();

			// Sign in status is failed
			ifSignedIn = false;

			// Rollback in case of exception caught
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {

			// Closing database session
			if (session != null) {
				session.clear();
				session.close();
			}
		}

		if (ifSignedIn) {
			return attendee;
		} else {
			return null;
		}
	}
}
