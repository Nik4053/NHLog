/**
 * 
 */
package nhlog;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;

// 
/**
 * @author Niklas Heidenreich
 *         <p>
 *         Created with the help of this tutorial:
 *         {@link http://www.vogella.com/tutorials/Logging/article.html}
 *         <p>
 *         And is therefore released under Eclipse Public License - v 1.0
 *         <p>
 *         ___________________________________________________________________________________
 *         <p>
 *         This custom formatter formats parts of a log record to a single line
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

	public static boolean printFullErrorLog = false;
	public static String colorSevere = "red";
	public static String colorWarning = "#FF8000";
	public static String colorInfo = "";
	public static String colorConfig = "#04B404";
	public static String colorFine;
	public static String colorFiner;
	public static String colorFinest;

	/**
	 * this method is called for every log record
	 */
	public String format(LogRecord rec) {
		if (threadID == 0L) {
		} else if (rec.getThreadID() != threadID) {
			return "";
		}

		StringBuffer buf = new StringBuffer(1000);
		buf.append("<tr>\n");

		// colorize any levels >= WARNING in red
		if (rec.getLevel().intValue() == Level.SEVERE.intValue()) {
			buf.append("\t<td style=\"color:" + colorSevere + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
			buf.append("\t<td style=\"color:" + colorWarning + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.INFO.intValue()) {
			buf.append("\t<td style=\"color:" + colorInfo + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.CONFIG.intValue()) {
			buf.append("\t<td style=\"color:" + colorConfig + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.FINE.intValue()) {
			buf.append("\t<td style=\"color:" + colorFine + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.FINER.intValue()) {
			buf.append("\t<td style=\"color:" + colorFiner + "\">");
			buf.append("<b>");
			buf.append(rec.getLevel());
			buf.append("</b>");
		} else if (rec.getLevel().intValue() == Level.FINEST.intValue()) {
			buf.append("\t<td style=\"color:" + colorFinest + "\">");
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
		String methodName = rec.getSourceMethodName();
		//deletes the <> signs around the method name
		methodName.substring(1, methodName.length() - 1);
		//adds the <> signs in html language
		methodName = "&lt" + methodName + "&gt";
		buf.append(rec.getSourceClassName() + methodName);
		buf.append("</td>\n");
		buf.append("\t<td>");

		String mes1 = formatMessage(rec);
		String mes2 = "";
		Throwable ta = rec.getThrown();
		if (rec.getThrown() != null) {
			if (mes1.length() > 0) {
				mes1 = mes1 + ": ";
			}

			mes2 = ta.toString();
		}
		if (printFullErrorLog && rec.getThrown() != null) {
			StringWriter errors = new StringWriter();
			rec.getThrown().printStackTrace(new PrintWriter(errors));
			mes2 = errors.toString();
		}
		buf.append(mes1 + mes2);

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
