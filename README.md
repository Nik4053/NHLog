# NHLog
An easy to use fully functioning logger. Just import it and you are ready to log. 
No setup required.
Check the documentation for more info. https://nik4053.github.io/NHLog/index.html

You can find the already built .jar in the /jar/ directory.



 version: 1.0.170322
 author: Nik4053

Created with the help of this tutorial:
{@link http://www.vogella.com/tutorials/Logging/article.html}

And is therefore released under Eclipse Public License - v 1.0

INFO: If you can not find your logs check if you have a folder called logs/html and logs/log !!!!!
Here the first part of the LOGGER class documentation:
 ____________________________________________________________________________
          This class is used for creating the Logger.
          Setup:
          First you have to import the logger:
          {@code import logging.LOGGER; }
          If you want the logs to be saved in different files for every thread
          you are logging set:
          {@code LOGGER.enableDifferentLogs(true);} if not then
          {@code LOGGER.enableDifferentLogs(false);}
          In order to get an output you have to add a Logger by using the
          Method:
          {@code LOGGER.addLogger(Thread.currentThread().getId());}
          If enableDifferentLogs(boolean)/(boolean createDifferentLogs) is set
          to true:
          Make sure to use the Method addLoger at the start of every new
          thread, or else this thread wont be logged.
 
          commands:
 
          {@code LOGGER.setLevel(Level.INFO);} sets the LogLevel to info. Only
          info, or more important Messages will be written to the log. You can
          change the Level. to every other log Level.

          Level sorted from highest to lowest priority:
          severe>warning>info>finest

          If you set the logLevel to {@code LOGGER.setLevel(off);}} no logs
          will be written anymore
  

          you can create a new log entry by using one of the following
          commands:
 
          Easy:

          {@code LOGGER.logger."Level"(String);} Set "Level" to the log level
          you want it to be like: {@code LOGGER.logger.warning(String);}

          to write an error log containing the an exception use:
          {@code LOGGER.info(getStackTrace(e));} where e is the variable where
          the catched exception is stored

          Advanced:

          {@code LOGGER.logger.log(Level, String, object);} ->
          {@code LOGGER.logger.log( Level.SEVERE, "This is a message", e );} or
          {@code LOGGER.logger.log(Level.SEVERE, e.getMessage(), e);} where e
          is the variable where the catched exception is stored

          For more info use:
          {@code LOGGER.logger.log(Level.SEVERE,"Exception: "+ e.getMessage() + " at line " + Thread.currentThread().getStackTrace()[1].getLineNumber(), e);}

          To log the name of the Method that is currently running you can use:
          LOGGER.logger.log(Level.INFO,"StartMethod: " +
          Thread.currentThread().getStackTrace()[1].getClassName() + " " +
          Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
          
License: Released under Eclipse Public License - v 1.0
