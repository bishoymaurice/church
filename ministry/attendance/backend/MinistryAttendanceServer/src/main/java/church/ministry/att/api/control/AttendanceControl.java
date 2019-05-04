package church.ministry.att.api.control;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import church.ministry.att.api.control.statica.Const;
import church.ministry.att.api.control.util.DateUtil;
import church.ministry.att.hibernate.model.Child;
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
	 * should be inserted to the database for the Holy Mass or for Sunday Schools.
	 * 
	 * It also makes sure that today is Friday; as it is not meant to be used at any
	 * other day until now.
	 * 
	 * @param attendee
	 * @return
	 */
	public AttendeeSignIn signIn(AttendeeSignIn attendee) {

		// Assume there is no meetings identified yet!
		attendee.setMeeting(null);

		// Checking if today is Friday
		if (dateUtil.getDayOfWeekName().equals(Const.SIMPLE_DATA_FORMAT_DAY_FRI)) {

			// This mean there must be a meeting matching that criteria (today is Friday)
			// We need this because it will affect HTTP response in API controller class
			attendee.setMeeting(new Meeting());

			Session session = null;
			try {
				session = HibernateUtil.getSessionFactory().openSession();

				// Check if child exists
				Child child = new Child();
				child = session.get(Child.class, attendee.getMember().getId());

				if (child != null && child.getMember() != null) {

					// Get child information
					child.setMember(child.getMember());

					// Set child information
					attendee.getMember().setName(child.getMember().getName());

					// Checking time to decide if the meeting is Holy Mass or Sunday Schools
					if ((dateUtil.getHourOfDay() < Const.HOLY_MASS_ATT_DEADLINE_HOUR)
							|| (dateUtil.getHourOfDay() == Const.HOLY_MASS_ATT_DEADLINE_HOUR
									&& dateUtil.getMinute() < Const.HOLY_MASS_ATT_DEADLINE_MINUTE)) {

						// Holy Mass
						attendee = massSignIn(attendee, session);

					} else {

						// Sunday Schools
						attendee = sundaySchoolsSignIn(attendee, session);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Closing database session
				if (session != null) {
					session.clear();
					session.close();
				}
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
	private AttendeeSignIn massSignIn(AttendeeSignIn attendee, Session session) {
		// Database transaction
		Transaction transaction = null;

		try {

			// Creating Holy Mass attendee entity
			ChildMassAttendance massAttendee = new ChildMassAttendance();
			massAttendee.setId(attendee.getMember().getId());
			massAttendee.setActionDate(this.dateUtil.getDayScopeCalendar().getTime());

			// *************** Check if member was already registered ***************//

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ChildMassAttendance> criteriaQuery = criteriaBuilder.createQuery(ChildMassAttendance.class);
			Root<ChildMassAttendance> root = criteriaQuery.from(ChildMassAttendance.class);
			criteriaQuery.select(root);

			Predicate idMatch = criteriaBuilder.equal(root.<Integer>get("id"), attendee.getMember().getId());
			Predicate dateMatch = criteriaBuilder.equal(root.<Date>get("actionDate"),
					this.dateUtil.getDayScopeCalendar().getTime());

			criteriaQuery.where(criteriaBuilder.and(idMatch, dateMatch));

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

			// Set meeting ID and name as a confirmation for the registered meeting
			attendee.getMeeting().setId(Const.MEETING_HOLY_MASS_ID);
			attendee.getMeeting().setName(Const.MEETING_HOLY_MASS_NAME);

		} catch (Exception ex) {

			ex.printStackTrace();

			// Roll back in case of exception caught
			if (transaction != null) {
				transaction.rollback();
			}
		}

		return attendee;
	}

	/**
	 * This function is called to register Sunday Schools attendance
	 * 
	 * @param attendee
	 * @return
	 */
	private AttendeeSignIn sundaySchoolsSignIn(AttendeeSignIn attendee, Session session) {
		// Database transaction
		Transaction transaction = null;

		try {

			// Creating ministry attendee entity
			ChildMinistryAttendance ministryAttendee = new ChildMinistryAttendance();
			ministryAttendee.setId(attendee.getMember().getId());
			ministryAttendee.setActionDate(this.dateUtil.getDayScopeCalendar().getTime());

			// *************** Check if member was already registered ***************//

			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<ChildMinistryAttendance> criteriaQuery = criteriaBuilder
					.createQuery(ChildMinistryAttendance.class);
			Root<ChildMinistryAttendance> root = criteriaQuery.from(ChildMinistryAttendance.class);
			criteriaQuery.select(root);

			Predicate idMatch = criteriaBuilder.equal(root.<Integer>get("id"), attendee.getMember().getId());
			Predicate dateMatch = criteriaBuilder.equal(root.<Date>get("actionDate"),
					this.dateUtil.getDayScopeCalendar().getTime());

			criteriaQuery.where(criteriaBuilder.and(idMatch, dateMatch));

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

			// Set meeting ID and name as a confirmation for the registered meeting
			attendee.getMeeting().setId(Const.MEETING_SUNDAY_SCHOOLS_ID);
			attendee.getMeeting().setName(Const.MEETING_SUNDAY_SCHOOLS_NAME);

		} catch (Exception ex) {

			ex.printStackTrace();

			// Roll back in case of exception caught
			if (transaction != null) {
				transaction.rollback();
			}
		}

		return attendee;
	}
}
