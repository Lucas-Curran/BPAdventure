package com.mygdx.game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashWriter {
	
	private FileWriter fw;
	private PrintWriter pw;
	static Exception error;
	
	/**
	 * 
	 * @param error - the error that has been thrown
	 * @throws IOException - IOEXception in case there is an error writing to a file
	 */
	public CrashWriter(Exception error) throws IOException {
		CrashWriter.error = error;
		fw = new FileWriter("CrashReport.txt", true);
		pw = new PrintWriter(fw);
	}
	
	/**
	 * 
	 * @throws IOException - IOException in case there is an error writing to a file
	 */
	public void writeCrash() throws IOException {
		pw.print(new SimpleDateFormat("[yyyy.MM.dd.HH:mm:ss]").format(new Date()));
		error.printStackTrace(pw);
		pw.close();
		fw.close();
	}
	
}
