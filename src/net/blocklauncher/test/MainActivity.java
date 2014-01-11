package net.blocklauncher.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	protected BlockLauncher mBL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBL = new BlockLauncher(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_run) {
			mBL.run();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void patch(View v) {
		try {
			File f = extractAsset("FlyInSurvival.mod");
			mBL.installPatch(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void script(View v) {
		try {
			File f = extractAsset("ColorChat.js");
			mBL.installScript(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void texture(View v) {
		try {
			File f = extractAsset("BLTextures.zip");
			mBL.installTexturepack(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void skin(View v) {
		try {
			File f = extractAsset("AnakinSkywalker.png"); // A long time ago...
			mBL.installSkin(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected File extractAsset(String name) throws IOException {
		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		in = assetManager.open(name);
		File f = new File(Environment.getExternalStorageDirectory(),"/tmp/"+name);
		f.getParentFile().mkdirs();
		out = new FileOutputStream(f);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
		in.close();
		in = null;
		out.flush();
		out.close();
		out = null;
		return f;
	}

}
