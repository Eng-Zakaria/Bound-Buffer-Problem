
package com.example.boundbuffer.Models;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class QR_Generate {
	private static void QR(String data, String path, String charset, Map hashMap,
								int height, int width)
			                    throws WriterException, IOException
	{

		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(data.getBytes(charset), charset),
				BarcodeFormat.QR_CODE, width, height);

		MatrixToImageWriter.writeToFile(
				matrix,
				path.substring(path.lastIndexOf('.') + 1),
				new File(path));
	}

	// Driver code
	public static int CearteQr(String path,String data) throws WriterException, IOException, NotFoundException
	{

		// The path where the image will get saved
		// Encoding charset
		String charset = "UTF-8";

		Map<EncodeHintType, ErrorCorrectionLevel> hashMap
				= new HashMap<EncodeHintType,
				ErrorCorrectionLevel>();

		hashMap.put(EncodeHintType.ERROR_CORRECTION,
				ErrorCorrectionLevel.L);

		// Create the QR code and save
		// in the specified folder
		// as a jpg file
		QR(data, path, charset, hashMap, 200, 200);
		System.out.println("QR Code Generated!!! ");
		return 1;
	}
}
