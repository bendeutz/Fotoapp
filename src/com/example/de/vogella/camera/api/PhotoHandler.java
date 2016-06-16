package com.example.de.vogella.camera.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

  private final Context context;
  
  public PhotoHandler(Context context) {
    this.context = context;
  }

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    File pictureFileDir = getDir();

    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

      Log.d(MakePhotoActivity.DEBUG_TAG, "Can't create directory to save image.");
      Toast.makeText(context, "Can't create directory to save image.",
          Toast.LENGTH_LONG).show();
      return;

    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date());
    String photoFile = "Picture_" + date + ".jpg";
//    MakePhotoActivity.path = "storage/emulated/0/Pictures/CameraAPIDemo/"+ photoFile;

    String filename = pictureFileDir.getPath() + File.separator + photoFile;
    Toast.makeText(context, filename,
          Toast.LENGTH_LONG).show();
    File pictureFile = new File(filename);

    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      fos.write(data);
      fos.close();
      
      //Toast.makeText(context, "New Image saved:" + photoFile,
          //Toast.LENGTH_LONG).show();
    } catch (Exception error) {
      Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename + "not saved: "
          + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
    try{
    	UploadFileTask uploadTask = new UploadFileTask();
    	uploadTask.execute(pictureFile);
    } catch (Exception error){
    	Toast.makeText(context, error.toString(),
		          Toast.LENGTH_LONG).show();
    }
  }
  
  

  private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    return new File(sdDir, "CameraAPIDemo");
  }
} 