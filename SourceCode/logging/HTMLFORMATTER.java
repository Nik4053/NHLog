/**
 * 
 */
package logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;

// 
/**
 * @version 1.0.170322
 * @author Nik4053
 * <p>Created with the help of this tutorial: {@link http://www.vogella.com/tutorials/Logging/article.html}
 * <p> And is therefore released under Eclipse Public License - v 1.0
 * <p>___________________________________________________________________________________
 * <p>this custom formatter formats parts of a log record to a single line
 */
public class HTMLFORMATTER extends Formatter {
	/**
	 * The ThreadID this Object should be logging for
	 */
	private long threadID;

	/**
	 * 
	 * @param nthreadID
	 *            The threadID of the thread that should be logged. Set to 0 for
	 *            one file logging
	 */
	public HTMLFORMATTER(long nthreadID) {
		this.threadID = nthreadID;
	}

	/**
	 * this method is called for every log record
	 */
	public String format(LogRecord rec) {
		if (threadID == 0L) {
		} else if (rec.getThreadID() != threadID) {
			return null;
		}

		StringBuffer buf = new StringBuffer(1000);
		buf.append("<tr>\n");

		// colorize any levels >= WARNING in red
		if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("\t<td style=\"color:red\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else {
			buf.append("\t<td>");
			buf.append(rec.getLevel());
		}

		buf.append("</td>\n");
		buf.append("\t<td>");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>\n");
		buf.append("\t<td>");
		if (threadID == 0L) {
			buf.append(rec.getThreadID());
			buf.append("</td>\n");
			buf.append("\t<td>");
		}
		buf.append(rec.getSourceClassName());
		buf.append("</td>\n");
		buf.append("\t<td>");
		buf.append(formatMessage(rec));
		buf.append("</td>\n");
		buf.append("</tr>\n");

		return buf.toString();
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

	/**
	 * this method is called just after the handler using this formatter is
	 * created
	 */
	public String getHead(Handler h) {
		if (threadID == 0) {
			return "<!DOCTYPE html>\n<head>\n<style>\n" + "table { width: 100% }\n" + "th { font:bold 10pt Tahoma; }\n"
					+ "td { font:normal 10pt Tahoma; }\n" + "h1 {font:normal 11pt Tahoma;}\n" + "</style>\n"
					+ "</head>\n" + "<body>\n" + "<h1>" + (new Date()) + "</h1>\n"
					+ "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n" + "<tr align=\"left\">\n"
					+ "\t<th style=\"width:5%\">Loglevel</th>\n" + "\t<th style=\"width:10%\">Time</th>\n"
					+ "\t<th style=\"width:1%\">Thread</th>\n" + "\t<th style=\"width:10%\">Class</th>\n"
					+ "\t<th style=\"width:75%\">Log Message</th>\n" + "</tr>\n";
		}
		return "<!DOCTYPE html>\n<head>\n<style>\n" + "table { width: 100% }\n" + "th { font:bold 10pt Tahoma; }\n"
				+ "td { font:normal 10pt Tahoma; }\n" + "h1 {font:normal 11pt Tahoma;}\n" + "</style>\n" + "</head>\n"
				+ "<body>\n" + "<h1>" + (new Date()) + "</h1>\n"
				+ "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n" + "<tr align=\"left\">\n"
				+ "\t<th style=\"width:5%\">Loglevel</th>\n" + "\t<th style=\"width:10%\">Time</th>\n"
				+ "\t<th style=\"width:10%\">Class</th>\n" + "\t<th style=\"width:75%\">Log Message</th>\n" + "</tr>\n";
	}

	/**
	 * this method is called just after the handler using this formatter is
	 * closed
	 */
	public String getTail(Handler h) {
		return "</table>\n</body>\n</html>";
	}

}
