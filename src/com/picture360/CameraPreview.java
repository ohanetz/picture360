package com.picture360;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder _holder;
	private Camera _camera;
	
	
	
	public CameraPreview(Context context) 
	{
		super(context);
		
		_holder = getHolder();
		_holder.addCallback(this);
		_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		try
		{
			_camera = Camera.open();
			_camera.setPreviewDisplay(this._holder);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.out);
		}
	}

	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		Camera.Parameters parameters = _camera.getParameters();
		parameters.setPreviewSize(width, height);
		_camera.setParameters(parameters);
		_camera.startPreview();
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		_camera.stopPreview();
		_camera.release();
		_camera = null;
	}
	
	
	
	public Camera getCamera() {
		return _camera;
	}
}
