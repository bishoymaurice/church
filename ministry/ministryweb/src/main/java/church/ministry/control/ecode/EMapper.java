package church.ministry.control.ecode;

import java.io.Serializable;

import com.vaadin.ui.Notification;

public class EMapper implements Serializable {

	private static final long serialVersionUID = 613175408745312042L;

	public static final int ECODE_DELETE_SUBCLASS_HAS_CHILDREN = 700;
	public static final String EDESC_DELETE_SUBCLASS_HAS_CHILDREN = "·« Ì„ﬂ‰ Õ–› „Ã„Ê⁄…  Õ ÊÌ ⁄·Ï „ŒœÊ„Ì‰";

	public static final int ECODE_SUCCESS = 0;
	private static final String EDESC_SUCCESS = " „ «· ”ÃÌ· »‰Ã«Õ";

	public static final int ECODE_PRINTING_FAILURE = -2;
	private static final String EDESC_PRINTING_FAILURE = "›‘·  «·ÿ»«⁄…";

	public static final int ECODE_FAILURE = -1;
	private static final String EDESC_FAILURE = "›‘· «· ”ÃÌ·";

	public static final int ECODE_DOESNOT_EXIST = -100;
	private static final String EDESC_DOESNOT_EXIST = "Â–« «·«”„ €Ì— „”Ã·";

	public static final int ECODE_ALREADY_EXISTS = 1;
	private static final String EDESC_ALREADY_EXIST = "Â–« «·«”„ „” Œœ„ „‰ ﬁ»·";

	public static final int ECODE_DATABASE_CONN_FAILURE = -500;
	private static final String EDESC_DATABASE_CONN_FAILURE = "€Ì— ﬁ«œ— ⁄·Ï «· Ê«’· „⁄ ﬁ«⁄œ… «·»Ì«‰« ";

	public static final int ECODE_UNHANDLED_ACTION = -200;
	private static final String EDESC_UNHANDLED_ACTION = "€Ì— ﬁ«œ— ⁄·Ï ≈ „«„ ≈Ã—«¡ €Ì— „⁄—Ê›";

	public static final int ECODE_INVALID_INPUT_DATA = -400;
	private static final String EDESC_INVALID_INPUT_DATA = "«·»Ì«‰«  «·„œŒ·… €Ì— ’ÕÌÕ… - „‰ ›÷·ﬂ  √ﬂœ „‰ «·»Ì«‰«  «·„œŒ·…";

	public static final int ECODE_INVALID_INPUT_DATA_NAME_3 = -401;
	private static final String EDESC_INVALID_INPUT_DATA_NAME_3 = "„‰ ›÷·ﬂ ≈œŒ· «·«”„ À·«ÀÌ ⁄·Ï «·√ﬁ·";

	public static final int ECODE_UNKNOWN_ERROR = -300;
	private static final String EDESC_UNKNOWN_ERROR = "Œÿ√ €Ì— „⁄—Ê›";

	public static final int ECODE_NO_RECORD_FOUND = -800;
	private static final String EDESC_NO_RECORD_FOUND = "·«ÌÊÃœ ‰ «∆Ã";

	public static final int ECODE_NO_UPDATE_NEEDED = -900;
	private static final String EDESC_NO_UPDATE_NEEDED = "·« ÌÊÃœ  €ÌÌ— ÌÕ «Ã «·Ï «· ”ÃÌ·";

	public static final String getEDesc(int eCode) {
		if (eCode == ECODE_SUCCESS) {
			return EDESC_SUCCESS;
		} else if (eCode == ECODE_FAILURE) {
			return EDESC_FAILURE;
		} else if (eCode == ECODE_DOESNOT_EXIST) {
			return EDESC_DOESNOT_EXIST;
		} else if (eCode == ECODE_ALREADY_EXISTS) {
			return EDESC_ALREADY_EXIST;
		} else if (eCode == ECODE_DATABASE_CONN_FAILURE) {
			return EDESC_DATABASE_CONN_FAILURE;
		} else if (eCode == ECODE_UNHANDLED_ACTION) {
			return EDESC_UNHANDLED_ACTION;
		} else if (eCode == ECODE_INVALID_INPUT_DATA) {
			return EDESC_INVALID_INPUT_DATA;
		} else if (eCode == ECODE_INVALID_INPUT_DATA_NAME_3) {
			return EDESC_INVALID_INPUT_DATA_NAME_3;
		} else if (eCode == ECODE_NO_RECORD_FOUND) {
			return EDESC_NO_RECORD_FOUND;
		} else if (eCode == ECODE_NO_UPDATE_NEEDED) {
			return EDESC_NO_UPDATE_NEEDED;
		} else if (eCode == ECODE_PRINTING_FAILURE) {
			return EDESC_PRINTING_FAILURE;
		} else if (eCode == ECODE_DELETE_SUBCLASS_HAS_CHILDREN) {
			return EDESC_DELETE_SUBCLASS_HAS_CHILDREN;
		} else {
			return EDESC_UNKNOWN_ERROR;
		}
	}

	public static final Notification.Type getNType(int eCode) {
		if (eCode == ECODE_SUCCESS) {
			return Notification.Type.HUMANIZED_MESSAGE;
		} else if (eCode == ECODE_FAILURE) {
			return Notification.Type.ERROR_MESSAGE;
		} else if (eCode == ECODE_DOESNOT_EXIST) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_ALREADY_EXISTS) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_DATABASE_CONN_FAILURE) {
			return Notification.Type.ERROR_MESSAGE;
		} else if (eCode == ECODE_UNHANDLED_ACTION) {
			return Notification.Type.ERROR_MESSAGE;
		} else if (eCode == ECODE_INVALID_INPUT_DATA) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_NO_RECORD_FOUND) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_INVALID_INPUT_DATA_NAME_3) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_NO_UPDATE_NEEDED) {
			return Notification.Type.WARNING_MESSAGE;
		} else if (eCode == ECODE_PRINTING_FAILURE) {
			return Notification.Type.ERROR_MESSAGE;
		} else if (eCode == ECODE_DELETE_SUBCLASS_HAS_CHILDREN) {
			return Notification.Type.ERROR_MESSAGE;
		} else {
			return Notification.Type.ERROR_MESSAGE;
		}
	}
}
