package church.ministry.att.api.control;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import church.ministry.att.api.control.common.Const;
import church.ministry.att.hibernate.model.MinisterChildAssigned;

public class RelationControl {

	/**
	 * Gets ID of the minister, who is assigned to certain child. This function can
	 * only be used for real time attendance taken by the QR or any other method, as
	 * it considers the date at the current moment only. By another words, it does
	 * not know who was the minister of that child at any other time except for this
	 * moment.
	 * 
	 * @param session
	 * @param childId
	 * @return
	 */
	public int getMinisterIdByChildId(Session session, String childId) {

		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<MinisterChildAssigned> criteriaQuery = criteriaBuilder.createQuery(MinisterChildAssigned.class);
		Root<MinisterChildAssigned> root = criteriaQuery.from(MinisterChildAssigned.class);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("activeFrom")));

		Predicate childIdFilter = criteriaBuilder.equal(root.<Integer>get("childId"), childId);

		criteriaQuery.where(childIdFilter);

		List<MinisterChildAssigned> resultList = session.createQuery(criteriaQuery).getResultList();

		int ministerId = Const.ID_NOT_FOUND;

		if (resultList.size() > 0) {

			if (resultList.get(0).getInactiveDate() == null) {
				ministerId = resultList.get(0).getMinisterId();
			}
		}

		return ministerId;
	}

}
