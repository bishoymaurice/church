package church.ministry.att.server.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import church.ministry.att.api.control.AttendanceControl;
import church.ministry.att.api.control.RelationControl;
import church.ministry.att.hibernate.model.ChildMassAttendance;
import church.ministry.att.server.hibernate.util.HibernateUtil;
import io.swagger.model.AttendeeSignIn;
import io.swagger.model.Member;

public class TestHibernate {
	public static void main(String[] args) {
		relationControlTest();
	}

	public static void relationControlTest() {
		RelationControl rc = new RelationControl();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		
		
		System.out.println(rc.getMinisterIdByChildId(session, "202"));
	}

	public static void testQuery() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

		CriteriaQuery<ChildMassAttendance> criteriaQuery = criteriaBuilder.createQuery(ChildMassAttendance.class);

		Root<ChildMassAttendance> userRoot = criteriaQuery.from(ChildMassAttendance.class);

		criteriaQuery.select(userRoot);

		List<ChildMassAttendance> users = session.createQuery(criteriaQuery).getResultList();

		System.out.println(users.size());

		session.clear();
		session.close();
		HibernateUtil.shutdown();
	}

	public static void attendeeSignIn() {

		Member member = new Member();
		member.setId(202);

		AttendeeSignIn attendee = new AttendeeSignIn();
		attendee.setMember(member);

		AttendanceControl ac = new AttendanceControl();
		attendee = ac.signIn(attendee);

		System.out.println(attendee.getMember().getName());
	}
}
