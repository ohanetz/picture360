package com.picture360;

import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Picture360Main extends Activity {
	
	private CameraPreview _cameraPreview;
	private FrameLayout _preview;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture360_main);
		
		_cameraPreview = new CameraPreview(this);
        _preview = (FrameLayout)findViewById(R.id.frameCameraPreview); 
        _preview.addView(_cameraPreview);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture360_main, menu);
		return true;
	}
	
	
	
	public void takePicture(View view) {

		Camera camera = _cameraPreview.getCamera();
		camera.takePicture(null, null, new HandlePictureStorage());
	}
	
	
	private class HandlePictureStorage implements PictureCallback {
	    public void onPictureTaken(byte[] data, Camera camera) {
	    	
	    	Uri uriTarget = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());
	        OutputStream imageFileOS;

	        try {

	            imageFileOS = getContentResolver().openOutputStream(uriTarget);
	            imageFileOS.write(data);
	            imageFileOS.flush();
	            imageFileOS.close();
	            
	            ((TextView)findViewById(R.id.textImagePath)).setText(uriTarget.getPath());
	            _preview.removeAllViews();
	            _preview.addView(_cameraPreview);
	            
	            
	            
//	            Bitmap imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriTarget));
//			    ImageView imageView = (ImageView) findViewById(R.id.imageView);
//			    imageView.setImageBitmap(imageBitmap);

	        } catch (IOException e) {
	            e.printStackTrace();

	        }
	    }
	};
}
