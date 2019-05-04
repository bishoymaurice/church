package church.ministry.att.server.test;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import church.ministry.att.hibernate.model.ChildMassAttendance;
import church.ministry.att.server.hibernate.util.HibernateUtil;

public class TestHibernate {
	public static void main(String[] args) {
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

}
