
package yafei.big.map.node.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/**
	 * 时间格式HHmmss
	 */
	public final static String HHmmss = "HHmmss";

	/** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy/MM/dd) */
    public final static String DATE_PATTERN_2 = "yyyy/MM/dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public final static String DATE_TIME_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";
	/**
	 * (时间格式 yy/MM/dd hh:mm:ss:SSS)
	 */
	public final static String DATE_TIME_MILLISECOND_PATTERN_2 = "yy/MM/dd HH:mm:ss:SSS";
	/**
	 * yy/MM/dd HH:mm:ss.SSS
	 */
	public final static String DATE_TIME_MILLISECOND_PATTERN_4 = "yy/MM/dd HH:mm:ss.SSS";

    /**
     * 格式yyMMdd_HHmmssSSS
     */
    public final static String DATE_TIME_MILLISECOND_PATTERN_3 = "yyMMdd_HHmmssSSS";

	/**
	 * 获取小时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

/*	public static String formatNew(LocalDateTime date, String pattern) {
		if(date != null){
			DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
			return df.format(date);
		}
		return null;
	}*/
	public static Date parse(String date, String pattern) {
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try {
				return df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 时间戳转LocalDateTime
	 * @param timestamp 时间戳
	 * @return LocalDateTime
	 */
	public static LocalDateTime timestampToLocalDateTime(long timestamp) {
		try {
			Instant instant = Instant.ofEpochMilli(timestamp);
			ZoneId zone = ZoneId.systemDefault();
			return LocalDateTime.ofInstant(instant, zone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Date转LocalDateTime
	 * @param date Date
	 * @return LocalDateTime
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		try {
			Instant instant = date.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			return instant.atZone(zoneId).toLocalDateTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Date转LocalDate
	 * @param date Date
	 * @return LocalDate
	 */
	public static LocalDate dateToLocalDate(Date date) {
		try {
			Instant instant = date.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			return instant.atZone(zoneId).toLocalDate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * String 转 LocalDateTime
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static LocalDate stringToLocalDate(String date, String pattern) {
		if(date != null){
			DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
			return LocalDate.parse(date,df);
		}
		return null;
	}

	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    public static String formatTime(Date date) {
        return format(date, DATE_TIME_MILLISECOND_PATTERN);
    }
    /**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List
    **/
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}
    
    /* public static  Timestamp convert(String text) { 
         Timestamp timestamp=null;
         if(StringUtils.hasText(text)){
             text = text.trim();
             boolean isLong = true;
             try {
                 long millisecond = Long.parseLong(text);
                 timestamp=new Timestamp(millisecond);
             }catch(Exception e){
                 isLong = false;
             }
             if(!isLong) {
                 try {
                     SimpleDateFormat sdf = new SimpleDateFormat(FormatUtils.getFormatter(text.length()));
                     String format = defaultDateFormat.format(sdf.parse(text));
                     System.out.println(format);
                 } catch (ParseException var3) {
                     throw new IllegalArgumentException("Could not parse date: " + var3.getMessage(), var3);
                 }
             }
         }
         return timestamp;
     }
 */
    
}

