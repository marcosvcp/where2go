package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * The Class ConvertBitmapToFile.
 */
public class ConvertBitmapToFile {
	
	/**
	 * Gets the file.
	 *
	 * @param bitmap the bitmap
	 * @param ctx the ctx
	 * @return the file
	 */
	public static FileOutputStream getFile(Bitmap bitmap, Context ctx){
	//create a file to write bitmap data
	File file = new File(ctx.getCacheDir(), "Event Cover");
	try {
		file.createNewFile();
	} catch (IOException e1) {
		e1.printStackTrace();
	}

	//Convert bitmap to byte array
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	bitmap.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
	byte[] bitmapdata = bos.toByteArray();

	//write the bytes in file
	FileOutputStream fos = null;
	try {
		fos = new FileOutputStream(file);
		fos.write(bitmapdata);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return fos;
	}
}
