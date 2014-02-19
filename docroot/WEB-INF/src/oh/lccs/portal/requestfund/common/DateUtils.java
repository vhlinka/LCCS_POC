package oh.lccs.portal.requestfund.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class DateUtils {
	
    private final static String MMDDYYYY_FORMAT = "MM/dd/yyyy";
    private final static String MMYYYY_FORMAT = "MMyyyy";
    private final static String OJ_TIMESTAMP_FORMAT = "MM/dd/yyyy hh:mm a";
    private final static String OJ_TIMESTAMP_FORMAT_EXTENDED = "MM/dd/yyyy hh:mm:ss a";

    //private static SimpleDateFormat sdfGetMMDDYYYYDate = new SimpleDateFormat(MMDDYYYY_FORMAT);
    private static final ThreadLocal threadLocal = new ThreadLocal() {
        protected Object initialValue() {
            return new HashMap();
        }
    };

    /**
     * Instead use new SimpleDateFormt(str), we can call this method to enhance performance.
     *
     * @param formatStr String
     *
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getSimpleDateFormat(String formatStr) {
        Map formatterMap = (Map) threadLocal.get();
        SimpleDateFormat sdf = (SimpleDateFormat) formatterMap.get(formatStr);

        if (sdf == null) {
            sdf = new SimpleDateFormat(formatStr);
            formatterMap.put(formatStr, sdf);
        }

        return sdf;
    }

    /**
     * DateAdd function
     *
     * @param dtSource date, field (Calendar.DATE,Calendar.MONTH,Calendar.YEAR), amount to adjust
     * @param field
     * @param amt
     *
     * @return java.util.Date
     */
    public static java.util.Date dateAdd(java.util.Date dtSource, int field, int amt) {
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(dtSource);
            calendar.add(field, amt);

            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     *
     * @param recvDate
     *
     * @return
     */
    public static boolean isDateGreaterThanCurrentDate(Date recvDate) { //throws ParseException {

        java.util.Date now = new java.util.Date();

        if (recvDate.compareTo(now) > 0) {
            return true;
        }

        return false;
    }

    /**
	 * #<Added by: Deloitte:Anirban> < Reason: To check which date is greater>
     *
     * @param recvDate
     *
     * @return boolean
     */
   public static boolean isSecondDateLessThanFirstDate(Date date1, Date date2) { //throws ParseException {

       if (date2.compareTo(date1) < 0) {
           return true;
       }

       return false;
   }
   
   /**
	 * #<Added by: Deloitte:Anirban> < Reason: To convert Date value into a
	 * String and return the String value as MM/dd/yyyy format>
	 * 
	 * @param date
	 * @return String
	 */
	public static String convertDateToString(Date date) {

		SimpleDateFormat MMDDYYYY = new SimpleDateFormat(MMDDYYYY_FORMAT);
		String convertedDate = new String(MMDDYYYY.format(date));
		return convertedDate;
	}

    /**
	 * 
	 * 
	 * @return
	 */
    public static String getMMDDYYYYDate() {
        //This is a thread-safe implementation.
        return getSimpleDateFormat(MMDDYYYY_FORMAT).format(new Date());
    }
    
    public static String getMMYYYYDate() {
        //This is a thread-safe implementation.
        return getSimpleDateFormat(MMYYYY_FORMAT).format(new Date());
    }
   
    /**
     *
     *
     * @param date
     *
     * @return
     */
    public static String getMMDDYYYYDate(Date date) {
        if (date == null) {
            return null; //donot throw runtime ex, please.
        }

        SimpleDateFormat df = getSimpleDateFormat(MMDDYYYY_FORMAT);

        return df.format(date);
    }

    /**
     *
     *
     * @param ts
     * @param format
     *
     * @return
     */
    public static String getStringDate(Timestamp ts, String format) {
        if (format == null) {
            format = MMDDYYYY_FORMAT;
        }

        if (ts == null) {
            return null;
        }

        SimpleDateFormat df = getSimpleDateFormat(format);

        return df.format(ts);
    }

    /**
     *
     *
     * @param date
     *
     * @return
     */
    public static String getOJTimestampString(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat df = getSimpleDateFormat(OJ_TIMESTAMP_FORMAT);

        return df.format(date);
    }

    /**
     *
     *
     * @param date
     *
     * @return
     */
    public static String getExtendedOJTimestampString(Date date) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat df = getSimpleDateFormat(OJ_TIMESTAMP_FORMAT_EXTENDED);
        return df.format(date);
    }

    /**
     *
     *
     * @param strDate
     *
     * @return
     */
    public static Date getMMDDYYYYStringAsDate(String strDate) {
        return getMMDDYYYYStringAsDate(strDate, null);
    }

    /**
     *
     *
     * @param strDate
     * @param defaultDate
     *
     * @return
     */
    public static Date getMMDDYYYYStringAsDate(String strDate, Date defaultDate) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(MMDDYYYY_FORMAT);
        Date date = defaultDate;

        if (strDate != null && strDate.length() > 0) {
            try {
                date = simpleDateFormat.parse(strDate);
            } catch (Exception e) {
                date = defaultDate;
            }
        }

        return date;
    }

    /**
     *
     *
     * @param dateStr
     * @param timeString
     *
     * @return
     */
    public static Timestamp convertToTimestamp(String dateStr, String timeString) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat("MM/dd/yyyy hh:mm");
        java.sql.Timestamp ts = null;

        try {
            java.util.Date newDate = simpleDateFormat.parse(dateStr + " " + timeString);
            long longData = newDate.getTime();
            ts = new Timestamp(longData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ts;
    }

    /**
     *
     *
     * @param ts
     *
     * @return
     */
    public static String getDateFromTimestamp(Timestamp ts) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(MMDDYYYY_FORMAT);
        String dateStr = null;
        Date newDate = new Date(ts.getTime());

        dateStr = simpleDateFormat.format(newDate);

        return dateStr;
    }

    /**
     *
     *
     * @param ts
     *
     * @return
     */
    public static String getTimeFromTimestamp(Timestamp ts) {
        String timeStr = null;
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat("hh:mm");

        Date newDate = new Date(ts.getTime());
        timeStr = simpleDateFormat.format(newDate);

        return timeStr;
    }

    /**
     * Computes the age given the Date of Birth and As of Date
     *
     * @param dob Date
     * @param asOfDate Date
     *
     * @return int
     */
    public static int getAge(java.util.Date dob, java.util.Date asOfDate) {
        Calendar dateOfBirth = new GregorianCalendar();
        Calendar asOfDateCalendar = new GregorianCalendar();
        int age = 0;

        if (dob != null && asOfDate != null) {
            dateOfBirth.setTime(dob);
            asOfDateCalendar.setTime(asOfDate);
            // Get age based on year
            age = asOfDateCalendar.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            // Add the tentative age to the date of birth to get this year's birthday
            dateOfBirth.add(Calendar.YEAR, age);

            // If this year's birthday has not happened asOfDate, subtract one from age
            if (age > 0 && asOfDateCalendar.before(dateOfBirth)) {
                age--;
            }
        }

        return (age);
    }
    
 

    /**
     * Get Date from string
     *
     * @param strDate
     * @param format
     *
     * @return None
     *
     * @throws ParseException
     */
    public static java.util.Date getDateFromString(String strDate, String format)
                                            throws ParseException {
        SimpleDateFormat sdfmt = null;

        if (strDate == null) {
            return null;
        }

        if (strDate.trim().length() == 0) {
            return null;
        }

        if (format == null) {
            format = MMDDYYYY_FORMAT;
        }

        sdfmt = getSimpleDateFormat(format);

        return sdfmt.parse(strDate);
    }

    /**
     *
     *
     *
     * @param strDate String
     * @param strTime String
     * @param strPeriod String
     *
     * @return Date
     *
     * @throws ParseException
     */
    public static java.util.Date getDateFromString(String strDate, String strTime, String strPeriod)
                                            throws ParseException {
        return getDateFromString(strDate + " " + strTime + " " + strPeriod, null);
    }

    /**
     * Returns the current Period of the day. PM or AM.
     *
     * @param now
     *
     * @return String
     */
    public static String getPeriodofDay(java.util.Date now) {
        if (now == null) {
            return null;
        }

        return getSimpleDateFormat("a").format(new java.util.Date(now.getTime()));
    }

    /**
     * Get SQL Date from java.util.Date
     *
     * @param date
     *
     * @return None
     *
     * @see
     */
    public static java.sql.Date getSQLDateFromDate(java.util.Date date) {
        if (date == null) {
            return null;
        }

        return new java.sql.Date(date.getTime());
    }

    /**
     *
     *
     * @param birthDate
     *
     * @return
     */
    public static int calculateAge(java.util.Date birthDate) {
        Date dateToday = new Date();

        return calculateAge(birthDate, dateToday);
    }

    /**
     *
     *
     * @param birthDate
     * @param ageDate
     *
     * @return
     */
    public static int calculateAge(java.util.Date birthDate, java.util.Date ageDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(birthDate);

        int birthYear = calendar.get(Calendar.YEAR);
        int birthMonth = calendar.get(Calendar.MONTH) + 1; //Month starts at zero
        int birthDay = calendar.get(Calendar.DATE);
        int birthMonthDay = (birthMonth * 100) + birthDay;

        calendar.setTime(ageDate);

        int todayYear = calendar.get(Calendar.YEAR);
        int todayMonth = calendar.get(Calendar.MONTH) + 1; //Month starts at zero
        int todayDay = calendar.get(Calendar.DATE);
        int todayMonthDay = (todayMonth * 100) + todayDay;

        int age = (birthMonthDay > todayMonthDay) ? todayYear - birthYear - 1 : todayYear - birthYear;

        return age;
    }

    /**
     *
     * Use Case PM05c: Medication By Deloitte kavitha
     * Added to compare two dates in String format
     * @param date1
     * @param date2
     *
     * @return
     *
     * @throws ParseException
     */
    public static boolean compareDates(Date date1, Date date2)
                                throws ParseException {
        if (date1.compareTo(date2) > 0) {
            return true;
        }

        return false;
    }


    /**
     *
     *
     * @param date
     *
     * @return
     */
    public static boolean isValidDate(String date) {
        try {
            DateUtils.getDateFromString(date, MMDDYYYY_FORMAT);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
//  #<Added by: Deloitte:Akhilesh> < Reason: To return age in YY MM format> 
    /**
     * Computes the age given the Date of Birth and As of Date
     *
     * @param dob Date
     * @param asOfDate Date
     *
     * @return String
     */
    public static String  getAgeAsYYMMString(java.util.Date dob, java.util.Date asOfDate) {
        Calendar dateOfBirth = new GregorianCalendar();
        Calendar asOfDateCalendar = new GregorianCalendar();
        int year = 0;
        int month=0;
        int date=0;
        
        String age= "";	
        if (dob != null && asOfDate != null) {
            dateOfBirth.setTime(dob);
            asOfDateCalendar.setTime(asOfDate);
            // Get age based on year
            year = asOfDateCalendar.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            month=asOfDateCalendar.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH);
            date=asOfDateCalendar.get(Calendar.DATE) - dateOfBirth.get(Calendar.DATE);
            // Add the tentative age to the date of birth to get this year's birthday
            dateOfBirth.add(Calendar.YEAR, year);
            dateOfBirth.add(Calendar.MONTH, month);

            if(month<0){
            	month=12+month;
            	year--;
            }
            if (month == 0){
            	if (date < 0){
            		month = 11;
            		year--;
            	}
            }
            	
            age= year+ " Y"+" "+month+ " M";  
        }
        
       
        
        return (age);
    }
    
//  #<Added by: Deloitte:Akhilesh> < Reason: To return age in YY MM format> 
    /**
     * Computes the age given the Date of Birth and As of Date
     *
     * @param dob Date
     * @param asOfDate Date
     *
     * @return String
     */
    public static String  getAgeAsYYMMDDString(java.util.Date dob, java.util.Date asOfDate) {
        Calendar dateOfBirth = new GregorianCalendar();
        Calendar asOfDateCalendar = new GregorianCalendar();
        int year = 0;
        int month=0;
        int date=0;
        
        String age= "";	
        if (dob != null && asOfDate != null) {
            dateOfBirth.setTime(dob);
            asOfDateCalendar.setTime(asOfDate);
            // Get age based on year
            year = asOfDateCalendar.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            month=asOfDateCalendar.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH);
            date=asOfDateCalendar.get(Calendar.DATE) - dateOfBirth.get(Calendar.DATE);
            // Add the tentative age to the date of birth to get this year's birthday
            dateOfBirth.add(Calendar.YEAR, year);
            dateOfBirth.add(Calendar.MONTH, month);

            if(date<0){
            	date=30+date;
            	month--;
            }
            	
            if(month<0){
            	month=12+month;
            	year--;
            }
                       	
            age= year+ " Years"+" "+month+ " Months"+" "+date+ " Days";  
        }
        
       
        
        return (age);
    }
    
    //    #<Added by: Deloitte:Neha> < Reason: To return difference b/w 2 dates in number of days> 
    /**
     * Computes difference b/w 2 dates in number of days
     *
     * @param date1 Date
     * @param date2 Date
     *
     * @return long
     */
    public static long getDateDiff(Date date1, Date date2){
    	// added and commented by sud for defect 17958
		//long diff = (date1.getTime() - date2.getTime())/86400000L;
    	long diff =getDaysBetween(date1,date2);
    	return diff;
    }

    
    /**
     * <Added by: Deloitte:Kshama>
     * This method is used to subtract a Date (arg1) by number of days (arg2),
     * returns the difference as Date.
     * 
     * @param1 strDate String
     * @param2 days int
     *
     * @return long
     */
    public static String getDateDiff(String strDate, int days){
		Calendar cal = Calendar.getInstance();
		try {
			
			cal.setTime(DateUtils.getDateFromString(strDate, MMDDYYYY_FORMAT));
			cal.add(cal.DATE, -days);
			return getMMDDYYYYDate(cal.getTime());
		
		} catch (ParseException e) {
			return null;
		}
    }    
    
    /**
     * <Added by: Deloitte:Sid>
     * This method is used to add a Date (arg1) by number of days (arg2),
     * returns the addition as Date.
     * 
     * @param1 strDate String
     * @param2 days int
     *
     * @return long
     */
    public static String getDateAdd(String strDate, int days){
		Calendar cal = Calendar.getInstance();
		try {
			
			cal.setTime(DateUtils.getDateFromString(strDate, MMDDYYYY_FORMAT));
			cal.add(cal.DATE, days);
			return getMMDDYYYYDate(cal.getTime());
		
		} catch (ParseException e) {
			return null;
		}
    } 
    
    /**
     * Computes the age based on the specified Date of Birth and As of Date.
     * (This function will return the age in the standard display format of:
     * 		xx Yrs 			- (for ages 3 and above)
     * 		xx Yrs yy Mon	- (for ages greater than 30 days to less than 3 years)
     * 		xx Days			- (for ages 1 to 30 days old)
     * 		Newborn			- (for ages 0 days old)
     *
     * @param dob Date
     * @param asOfDate Date
     *
     * @return String
     */
    public static String getAgeStandardDisplay(java.util.Date dob, java.util.Date asOfDate) {
        Calendar dateOfBirth = new GregorianCalendar();
        Calendar asOfDateCalendar = new GregorianCalendar();
        
        int year = 0;
        int month = 0;
        int date = 0;
        String age = "";	

        // Check to make sure a DOB and As Of Date were specified.
        if (dob != null && asOfDate != null) {

        	// Initialize the date for each calendar object appropriately.
        	dateOfBirth.setTime(dob);
            asOfDateCalendar.setTime(asOfDate);
            
            // Get age based on year.
            year = asOfDateCalendar.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

            // Get age based on month.
            month = asOfDateCalendar.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH);
            
            // Get age based on the actual date.
            date = asOfDateCalendar.get(Calendar.DATE) - dateOfBirth.get(Calendar.DATE);
            
            // Add the tentative age to the date of birth to get this year's birthday
            dateOfBirth.add(Calendar.YEAR, year);
            dateOfBirth.add(Calendar.MONTH, month);

            // If the date is negative, then add 30 to it and back the month up by 1.
            if (date < 0) {
            	date = 30 + date;
            	month--;
            }
            	
            // If the month is negative, then add 12 to it and back the year up by 1.
            if (month < 0) {
            	month = 12 + month;
            	year--;
            }
            
            // Build the appropriate age string.
            if (year >= 3) {
            	age = year + " Yrs";
            	
            } else if (year > 0 || month > 0) {
            	if (year > 0) {
            		age = year + " Yr";
            		
            		if (year > 1) {
            			age = age + "s";
            		}
            		
            		if (month > 0) {
            			age = age + " ";
            		}
            	}
            	
            	if (month > 0) {
            		age = age + month + " Mos";
            	}
            	
            } else if (date > 0) {
            	age = date + " day";
            	
            	if (date > 1) {
            		age = age + "s";
            	}
            	
            } else {
            	age = "Newborn";
            	
            }
        }
        
        return (age);
    }
    
    /** added by sud for defect 17958 
     * computing difference between two days
     * @param endDate
     * @param startDate
     * @return long (no of days between two dates)
     */
    public static long getDaysBetween(Date endDate,Date startDate) {
    	long daysBetween = 0;
    	Calendar edate = Calendar.getInstance();
    	edate.setTime(endDate);
		Calendar bdate = Calendar.getInstance();
		bdate.setTime(startDate);
		
    	Calendar date1 = (Calendar) bdate.clone();
    	Calendar date2 = (Calendar) edate.clone();   	
    	// if end date is greater than start date
    	if (endDate.compareTo(startDate)>0){
    		while (date1.before(edate)) {
        		date1.add(Calendar.DAY_OF_MONTH, 1);
        		daysBetween++;
        	}
    	}
    	// if end date is less than start date
    	else if(endDate.compareTo(startDate)<0){
    		while (date2.before(bdate)) {
        		date2.add(Calendar.DAY_OF_MONTH, 1);
        		daysBetween--; 
        	}
    	}
    	return daysBetween;
    }

    
   // end added
    
    
}
