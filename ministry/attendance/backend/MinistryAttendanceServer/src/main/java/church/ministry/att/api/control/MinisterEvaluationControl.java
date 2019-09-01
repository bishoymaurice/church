package church.ministry.att.api.control;

import org.hibernate.Session;

import church.ministry.att.api.control.common.Code;
import church.ministry.att.api.control.util.DateUtil;

public class MinisterEvaluationControl {

	/**
	 * Updates minister evaluation upon child attendance for the ministry
	 * 
	 * @param session
	 * @param childId
	 * @param ifMinistryAttendant
	 * @return
	 */
	public Code updateMinisterEvaluation(Session session, int childId, boolean ifMinistryAttendant) {

		try {
			DateUtil dateUtil = new DateUtil();
			String todayInString = dateUtil.getTodayDateToString();

			return Code.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			return Code.FAILED;
		}
	}

}
