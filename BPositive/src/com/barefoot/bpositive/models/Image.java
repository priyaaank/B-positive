package com.barefoot.bpositive.models;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

public class Image {

	private byte[] imageStream;
	private byte[] shaDigest;

	public Image(byte[] imageStream) {
		this.imageStream = imageStream;
		this.shaDigest = shaDigest(imageStream);
	}

	public Image(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, outputStream);
		imageStream = outputStream.toByteArray();
	}

	public Bitmap getBitmapImage() {
		if (this.imageStream != null) {
			return BitmapFactory.decodeByteArray(this.imageStream, 0,
					this.imageStream.length);
		}
		return null;
	}

	public byte[] getByteStream() {
		return this.imageStream;
	}

	public byte[] getShaDigest() {
		return this.shaDigest;
	}

	private static byte[] shaDigest(byte[] value) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			Log.e("ImageModel",
					"Algorithm to obtain the image SHA was not found. Wierd");
		}
		md.update(value);
		return md.digest();
	}
}
