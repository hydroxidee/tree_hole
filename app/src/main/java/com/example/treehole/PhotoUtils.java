package com.example.treehole;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class PhotoUtils {
    //converts image to base64 (for storage in firebase)
    public static String UriToBase64(Context context, Uri uri)
    {
        Bitmap temp = UriToBitMap(context, uri);
        assert temp != null;
        return BitMapToBase64(temp);
    }

    //converts stored image to bitmap 64
    public static Bitmap Base64ToBitMap(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // converts uri to bitmap 64
    private static Bitmap UriToBitMap(Context context, Uri uri) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);

            // Compress the bitmap
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);

            byte[] compressedBitmapData = outputStream.toByteArray();

            // Convert the compressed byte array back to a bitmap
            return BitmapFactory.decodeByteArray(compressedBitmapData, 0, compressedBitmapData.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // converts bitmap to base64
    private static String BitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
