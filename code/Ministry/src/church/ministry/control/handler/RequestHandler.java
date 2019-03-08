package church.ministry.control.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.child.ChildHandler;
import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.father.FatherHandler;
import church.ministry.control.log.Logger;
import church.ministry.control.minister.MinisterHandler;
import church.ministry.control.org.OrgHandler;
import church.ministry.model.oracle.DatabaseConnectionManager;

public class RequestHandler implements Serializable {

	private static final long serialVersionUID = 1654758708612840208L;

	public static int handleUpdateRequest(String requestType, HashMap<String, String> requestData) {
		try {
			requestData = validateRequestData(requestData);

			if (requestType.equals("newFather")) {
				return FatherHandler.newFather(requestData);
			} else if (requestType.equals("updateFather")) {
				return FatherHandler.updateFather(requestData);
			} else if (requestType.equals("deleteFather")) {
				return FatherHandler.deleteFather(requestData);
			} else if (requestType.equals("newMinister")) {
				return MinisterHandler.newMinister(requestData);
			} else if (requestType.equals("updateMinister")) {
				return MinisterHandler.updateMinister(requestData);
			} else if (requestType.equals("deleteMinister")) {
				return MinisterHandler.deleteMinister(requestData);
			} else if (requestType.equals("newChild")) {
				return ChildHandler.newChild(requestData);
			} else if (requestType.equals("updateChild")) {
				return ChildHandler.updateChild(requestData);
			} else if (requestType.equals("deleteChild")) {
				return ChildHandler.deleteChild(requestData);
			} else if (requestType.equals("newSection")) {
				return OrgHandler.newSection(requestData);
			} else if (requestType.equals("updateSection")) {
				return OrgHandler.updateSection(requestData);
			} else if (requestType.equals("deleteSection")) {
				return OrgHandler.deleteSection(requestData);
			} else if (requestType.equals("newFamily")) {
				return OrgHandler.newFamily(requestData);
			} else if (requestType.equals("updateFamily")) {
				return OrgHandler.updateFamily(requestData);
			} else if (requestType.equals("deleteFamily")) {
				return OrgHandler.deleteFamily(requestData);
			} else if (requestType.equals("newYear")) {
				return OrgHandler.newYear(requestData);
			} else if (requestType.equals("updateYear")) {
				return OrgHandler.updateYear(requestData);
			} else if (requestType.equals("deleteYear")) {
				return OrgHandler.deleteYear(requestData);
			} else if (requestType.equals("newClass")) {
				return OrgHandler.newClass(requestData);
			} else if (requestType.equals("newSubClass")) {
				return OrgHandler.newSubClass(requestData);
			} else if (requestType.equals("updateClass")) {
				return OrgHandler.updateClass(requestData);
			} else if (requestType.equals("updateSubClass")) {
				return OrgHandler.updateSubClass(requestData);
			} else if (requestType.equals("deleteClass")) {
				return OrgHandler.deleteClass(requestData);
			} else if (requestType.equals("deleteSubClass")) {
				return OrgHandler.deleteSubClass(requestData);
			} else if (requestType.equals("assignSubClass")) {
				return OrgHandler.assignSubClassToClass(requestData);
			} else if (requestType.equals("assignChild")) {
				return OrgHandler.assignChild(DatabaseConnectionManager.getConnection(), true, requestData);
			} else if (requestType.equals("unassignChild")) {
				return OrgHandler.unassignChild(requestData);
			} else if (requestType.equals("assignClass")) {
				return OrgHandler.assignClass(requestData);
			} else if (requestType.equals("assignYear")) {
				return OrgHandler.assignYear(requestData);
			} else if (requestType.equals("assignFamily")) {
				return OrgHandler.assignFamily(requestData);
			} else if (requestType.equals("reactivateMember")) {
				return CommonsHandler.reactivateMember(requestData);
			} else if (requestType.equals("changeSubClassMinister")) {
				return OrgHandler.changeSubClassMinister(requestData);
			} else {
				return EMapper.ECODE_UNHANDLED_ACTION;
			}
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static HashMap<String, String> handleQueryRequest(String requestType,
			HashMap<String, String> requestData) {
		try {
			requestData = validateRequestData(requestData);

			if (requestType.equals("getFatherData")) {
				return FatherHandler.getFatherData(requestData);
			} else if (requestType.equals("getMinisterData")) {
				return MinisterHandler.getMinisterData(requestData);
			} else if (requestType.equals("getChildData")) {
				return ChildHandler.getChildData(requestData);
			} else {
				return null;
			}
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	public static ArrayList<ArrayList<Object>> handle2DQueryRequest(String requestType,
			HashMap<String, String> requestData, HashMap<String, String> selectData) {
		try {
			requestData = validateRequestData(requestData);

			if (requestType.equals("searchFather")) {
				return FatherHandler.searchFather(requestData, selectData);
			} else if (requestType.equals("searchMinister")) {
				return MinisterHandler.searchMinister(requestData, selectData);
			} else if (requestType.equals("searchChild")) {
				return ChildHandler.searchChild(requestData, selectData);
			} else {
				return null;
			}
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	private static HashMap<String, String> validateRequestData(HashMap<String, String> requestData) {
		try {
			for (String key : requestData.keySet()) {
				if (requestData.get(key) == null) {
					requestData.put(key, "null");
				} else {
					requestData.put(key, requestData.get(key).trim());
				}
			}
			return requestData;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}
}
