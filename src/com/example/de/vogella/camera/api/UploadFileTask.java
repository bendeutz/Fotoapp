package com.example.de.vogella.camera.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class UploadFileTask extends AsyncTask<File, Integer, Boolean> {

	private String serverIP = "192.168.0.100";
	private BufferedReader in;
	private PrintWriter out;
	private MySocket cliSock;
	private String path;


	@Override
	protected Boolean doInBackground(File... params) {
	
		try {
			File f = params[0];
			path = f.getAbsolutePath();
		// Establish Connection
			appendLog("LOL");
			cliSock = new MySocket(serverIP, 8070);
			appendLog(cliSock.toString());
			appendLog(Boolean.toString(cliSock.isConnected()));
			in = new BufferedReader(new InputStreamReader(
					cliSock.getInputStream()));
			appendLog(in.toString());
			 appendLog("Connection established\n");

		} catch (Exception e) {
			Log.v("MERA MSG", e.toString());
			 appendLog(e.toString());
		}

		// Send file
		try {
			File myFile = new File(path);
			System.out.println((int) myFile.length());
			byte[] mybytearray = new byte[450560];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = cliSock.getOutputStream();
			System.out.println("Sending...");
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
			System.out.println("Completed");
			appendLog("File sended");
		} catch (Exception e) {
			Log.v("MERA MSG", e.toString());			
			 appendLog(e.toString());
		} finally {
			try {
				cliSock.reallyClose();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	public void appendLog(String text)
	{       
	   File logFile = new File("/storage/emulated/0/Pictures/CameraAPIDemo/log.txt");
	   if (!logFile.exists())
	   {
	      try
	      {
	         logFile.createNewFile();
	      } 
	      catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	   }
	   try
	   {
	      //BufferedWriter for performance, true to set append to file flag
	      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
	      buf.append(text);
	      buf.newLine();
	      buf.flush();
	      buf.close();
	   }
	   catch (IOException e)
	   {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	   }
	}
}
