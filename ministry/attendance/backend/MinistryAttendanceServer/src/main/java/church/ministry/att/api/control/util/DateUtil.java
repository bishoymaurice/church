package church.ministry.att.api.control.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import church.ministry.att.api.control.statica.Const;

/**
 * This class is doing all operations needed on date and time.
 * 
 * @author bmauric2
 *
 */
public class DateUtil {

	// Define date and calendar
	private Date nowDate = null;
	private Calendar nowCalednar = null;

	// Define current day of week
	private String dayOfWeek = null;

	// Define current hour and minute
	private int hourOfDay = 0;
	private int minute = 0;

	// Define calendar for scope of day
	private Calendar dayScopeCalendar = null;

	// Define string for today date
	private String todayToString;

	// Define date format;
	private final String dateFormat = "dd-MM-yyyy";

	/**
	 * Gets day of week name, hour in 24-hours format and minute based on current
	 * date and time.
	 */
	public DateUtil() {
		// Getting current date
		this.nowDate = new Date();
		this.nowCalednar = Calendar.getInstance();
		this.nowCalednar.setTime(this.nowDate);

		// Formatting current date to get day of week name
		SimpleDateFormat simpleDateformat = new SimpleDateFormat(Const.SIMPLE_DATA_FORMAT_DAY_OF_WEEK_NAME);

		// Return day of week name
		this.dayOfWeek = simpleDateformat.format(this.nowDate);

		// Set current hour of day
		this.hourOfDay = nowCalednar.get(Calendar.HOUR_OF_DAY);

		// Set current minute
		this.minute = this.nowCalednar.get(Calendar.MINUTE);

		// ************** DAY SOCPE **************//
		// Declare Calendar instance
		this.dayScopeCalendar = Calendar.getInstance();
		this.dayScopeCalendar.setTime(this.nowDate);

		// Remove hours, minutes and seconds from Calendar instance
		this.dayScopeCalendar.set(Calendar.HOUR_OF_DAY, Const.CALENDAR_H_0);
		this.dayScopeCalendar.set(Calendar.MINUTE, Const.CALENDAR_M_0);
		this.dayScopeCalendar.set(Calendar.SECOND, Const.CALENDAR_S_0);
		this.dayScopeCalendar.set(Calendar.MILLISECOND, Const.CALENDAR_MS_0);
		// ************** DAY SOCPE **************// DONE

		SimpleDateFormat dateFormatter = new SimpleDateFormat(this.dateFormat);
		this.todayToString = dateFormatter.format(this.nowDate);
	}

	/**
	 * Returns current day of week.
	 * 
	 * @return
	 */
	public String getDayOfWeekName() {
		return this.dayOfWeek;
	}

	/**
	 * Returns current hour of day in 24 hours format.
	 * 
	 * @return
	 */
	public int getHourOfDay() {
		return this.hourOfDay;
	}

	/**
	 * Returns current minute.
	 * 
	 * @return
	 */
	public int getMinute() {
		return this.minute;
	}

	/**
	 * Returns current date in scope of day only.
	 * 
	 * @return
	 */
	public Calendar getDayScopeCalendar() {
		return this.dayScopeCalendar;
	}

	/**
	 * Returns today date in date type of string
	 * 
	 * @param today
	 * @return
	 */
	public String getTodayDateToString() {
		return this.todayToString;
	}
}
