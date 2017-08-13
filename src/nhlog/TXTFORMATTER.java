/**
 * 
 */
package nhlog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Niklas Heidenreich
 * The formatter for the .log files.
 * Uses the java.util.logging.SimpleFormater as base
 */
public class TXTFORMATTER extends Formatter {

	private Object args[] = new Object[1];

	// Line separator string.  This is the value of the line.separator
	// property at the moment that the SimpleFormatter was created.
	private String lineSeparator = System.getProperty("line.separator");
	private long threadID;

	public TXTFORMATTER(long nthreadID) {
		this.threadID = nthreadID;
	}

	/**
	 * Format the given LogRecord.
	 * <p>
	 * This method can be overridden in a subclass. It is recommended to use the
	 * {@link Formatter#formatMessage} convenience method to localize and format
	 * the message field.
	 *
	 * @param record
	 *            the log record to be formatted.
	 * @return a formatted log record
	 */
	public String format(LogRecord record) {
		if (threadID == 0L) {
		} else if (record.getThreadID() != threadID) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		// Minimize memory allocations here.
		args[0] = calcDate(record.getMillis());
		//StringBuffer text = new StringBuffer();
		//sb.append(text);
		sb.append(args[0]);
		sb.append(" ");
		if (record.getSourceClassName() != null) {
			sb.append(record.getSourceClassName());
		} else {
			sb.append(record.getLoggerName());
		}
		if (record.getSourceMethodName() != null) {
			sb.append(" ");
			sb.append(record.getSourceMethodName());
		}
		if (threadID == 0L) {
			sb.append(" ");
			sb.append("Thread: ");
			sb.append(record.getThreadID());
		}
		sb.append(lineSeparator);
		String message = formatMessage(record);
		sb.append(record.getLevel().getLocalizedName());
		sb.append(": ");
		sb.append(message);
		sb.append(lineSeparator);
		if (record.getThrown() != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch (Exception ex) {
			}
		}
		return sb.toString();
	}

	/**
	 * Calculates the date
	 * 
	 * @param millisecs
	 *            millisecs of the time counting from 1970
	 * @return A string containing the date
	 */
	private String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}
}
