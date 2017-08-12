// TODO 
/**
 * 
 */
package nhlog;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 * @version 1.0.170510
 * @author Niklas Heidenreich
 *         <p>
 *         Created with the help of this tutorial:
 *         {@link http://www.vogella.com/tutorials/Logging/article.html}
 *         <p>
 *         And is therefore released under Eclipse Public License - v 1.0
 * 
 *         <p>
 *         ____________________________________________________________________________
 *         <p>
 *         This class is used for creating the Logger.
 *         <p>
 *         Setup:
 *         <p>
 *         First you have to import the logger:
 *         <p>
 *         {@code import logging.LOGGER; }
 *         <p>
 *         If you want the logs to be saved in different files for every thread
 *         you are logging set:
 *         <p>
 *         {@code LOGGER.enableDifferentLogs(true);} if not then
 *         {@code LOGGER.enableDifferentLogs(false);}
 *         <p>
 *         In order to get an output you have to add a Logger by using the
 *         Method:
 *         <p>
 *         {@code LOGGER.addLogger(Thread.currentThread().getId());}
 *         <p>
 *         If enableDifferentLogs(boolean)/(boolean createDifferentLogs) is set
 *         to true:
 *         <p>
 *         Make sure to use the Method addLoger at the start of every new
 *         thread, or else this thread wont be logged.
 * 
 * 
 *         <p>
 *         commands:
 * 
 *         <p>
 *         {@code LOGGER.setLevel(Level.INFO);} sets the LogLevel to info. Only
 *         info, or more important Messages will be written to the log. You can
 *         change the Level. to every other log Level.
 *         <p>
 *         Level sorted from highest to lowest priority:
 *         <p>
 *         severe>warning>info>finest
 *         <p>
 *         If you set the logLevel to {@code LOGGER.setLevel(off);}} no logs
 *         will be written anymore
 * 
 *         <p>
 *         you can create a new log entry by using one of the following
 *         commands:
 *         <p>
 *         Easy:
 *         <p>
 *         {@code LOGGER.logger."Level"(String);} Set "Level" to the log level
 *         you want it to be like: {@code LOGGER.logger.warning("test");}
 *         <p>
 *         To write an error log containing an exception use:
 *         {@code LOGGER.logger.log(Level.SEVERE, "" , e);} where e is the
 *         variable where the catched exception is stored
 *         <p>
 *         Advanced:
 *         <p>
 *         {@code LOGGER.logger.log(Level, String, object);} ->
 *         {@code LOGGER.logger.log( Level.SEVERE, "This is a message", e );} or
 *         {@code LOGGER.logger.log(Level.SEVERE, e.getMessage(), e);} where e
 *         is the variable where the catched exception is stored
 *         <p>
 *         For more info use:
 *         {@code LOGGER.logger.log(Level.SEVERE,"Exception: "+ e.getMessage() + " at line " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e);}
 *         <p>
 *         To log the name of the Method that is currently running you can use:
 *         <p>
 *         {@code LOGGER.logger.log(Level.INFO,"StartMethod: " +
 *         Thread.currentThread().getStackTrace()[1].getClassName() + " " +
 *         Thread.currentThread().getStackTrace()[1].getMethodName() + "()");}
 */
public class LOGGER {
	/**
	 * List containing all fileHandlers for the text documents (.log)
	 */
	private static java.util.List<FileHandler> fileTxt = new ArrayList<FileHandler>();
	/**
	 * List containing all fileHandlers for the html documents (.html)
	 */
	private static java.util.List<FileHandler> fileHTML = new ArrayList<FileHandler>();
	/**
	 * Boolean that can be set by using enableDifferentLogs(boolean).
	 * <p>
	 * If true their will be created different logs for every thread
	 * <p>
	 */
	private static boolean createDifferentLogs = false;
	/**
	 * A List containing the ID of all threads that have already a logger for
	 * them
	 */
	private static java.util.List<Long> threadList = new ArrayList<Long>();
	/**
	 * The logger. Use this to log stuff. -> See the class description under
	 * commands.^
	 */
	public static Logger logger = Logger.getGlobal();
	/**
	 * Uses the current time as name for the logfile
	 */
	private static String filename = calcDate(System.currentTimeMillis());

	/**
	 * The directory to store the logs
	 */
	private static String txtDirectory = "logs/log/";
	/**
	 * The directory to store the html logs
	 */
	private static String htmlDirectory = "logs/html/";
	/**
	 * Enables txt logs
	 */
	private static boolean allowTxtLogs = true;	
	/**
	 * Enables html logs
	 */
	private static boolean allowHtmlLogs = true;


	/**
	 * Use this Method the start the Logger, or to create a new logger for the
	 * thread.
	 * <p>
	 * The current thread id can be called by using
	 * 
	 * @param threadID
	 *            The ID of the Threat that should be logging
	 */
	public static synchronized void addLogger(long threadID) {
		if (createDifferentLogs) {
			//if the ThreadId has not been used yet
			if (threadList.contains(threadID) != true) {
				createNewHandler(threadID);
				threadList.add(threadID);
			}

		} else {
			if (threadList.contains(0L) != true) {
				createNewHandler(0L);
				threadList.add(0L);
			}
		}
		
		//        logger.log(level, message, thrown);
		//logger.logp(level, _sourceClass_, _sourceMethod_, message, thrown)
	}

	/**
	 * Will change the settings for managing different logs in different files
	 * 
	 * @param enable
	 *            set to true if you want to enable different logs for every
	 *            thread
	 */
	public static void enableDifferentLogs(boolean enable) {
		createDifferentLogs = enable;
	}

	/**
	 * creates a new handler and formatter with the threadID as identifier
	 * 
	 * @param threadID
	 */
	private static void createNewHandler(long threadID) {
		try {
			fileTxt.add(new FileHandler(txtDirectory + filename + "-# " + threadID + ".log"));
			fileHTML.add(new FileHandler(htmlDirectory + filename + "-# " + threadID + ".html"));

			// create a TXT formatter
			//SimpleFormatter formatterTxt = new SimpleFormatter();
			Formatter formatterTxt = new TXTFORMATTER(threadID);
			fileTxt.get(fileTxt.size() - 1).setFormatter(formatterTxt);
			logger.addHandler(fileTxt.get(fileTxt.size() - 1));

			// create an HTML formatter
			Formatter formatterHTML = new HTMLFORMATTER(threadID);
			fileHTML.get(fileHTML.size() - 1).setFormatter(formatterHTML);
			logger.addHandler(fileHTML.get(fileHTML.size() - 1));
		} catch (IOException e) {
		}
	}

	/**
	 * Calculates the date
	 * 
	 * @param millisecs
	 *            millisecs of the time counting from 1970
	 * @return A string containing the date
	 */
	private static String calcDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("YYYY-MM-DD--HHmm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	/**
	 * Changes the directory where the logs should be stored. Does not work if
	 * the logger is already working Default: "logs/log/"
	 * 
	 * @param dir
	 *            the new relative directory
	 */
	public static void setTxtDirectory(String dir) {
		txtDirectory = dir;
	}

	public static String getTxtDirectory() {
		return txtDirectory;
	}

	/**
	 * CURRENTLY NOT WORKING
	 * @param allow
	 */
	public static void allowTxtLogs(boolean allow) {
		// TODO 
		allowTxtLogs=allow;
	}

	public static boolean getAllowTxtLogs() {
		return allowTxtLogs ;
	}

	/**
	 * Changes the directory where the logs should be stored. Does not work if
	 * the logger is already working Default: "logs/html/"
	 * 
	 * @param dir
	 *            the new relative directory
	 */
	public static void setHtmlDirectory(String dir) {
		htmlDirectory = dir;
	}

	public static String getHtmlDirectory() {
		return htmlDirectory;
	}

	/**
	 * CURRENTLY NOT WORKING
	 * @param allow
	 */
	public static void allowHtmlLogs(boolean allow) {
		// TODO 
		allowHtmlLogs=allow;
	}

	public static boolean getAllowHtmlLogs() {
		return allowHtmlLogs;
	}
	
	/**
	 * If true will print out the complete error log if an exception happens
	 * @return true if full logs are be forced
	 */
	public static boolean getPrintFullLogs()
	{
		return HTMLFORMATTER.printFullErrorLog;
	}
	
	/**
	 * If true will print out the complete error log in the html if an exception happens
	 * Can be changed at any time 
	 * @param allow set to true if full logs should be forced
	 */
	public static void setPrintFullLogs(boolean allow)
	{
		HTMLFORMATTER.printFullErrorLog=allow;
	}

}
