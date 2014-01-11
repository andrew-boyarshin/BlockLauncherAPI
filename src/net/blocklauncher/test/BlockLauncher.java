package net.blocklauncher.test;

import java.io.File;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.widget.Toast;

public class BlockLauncher {
	private Context mContext;
	public static final String pkg_free = "net.zhuoweizhang.mcpelauncher";
	public static final String cls_free = "net.zhuoweizhang.mcpelauncher.LauncherAppActivity";
	public static final String pkg = "net.zhuoweizhang.mcpelauncher.pro";
	public static final String cls = "net.zhuoweizhang.mcpelauncher.pro.LauncherProActivity";

	public BlockLauncher(Context con) {
		mContext = con;
	}

	// ================================================
	// ==
	// == INSTALL CONTENT ==
	// ==
	// ================================================

	public void installPatch(File object) {
		final String clsImportPatch = pkg_free + ".ImportPatchActivity";
		try {
			Intent bl = new Intent("android.intent.action.VIEW");
			bl.setData(Uri.fromFile(object));
			bl.setClassName(isPro() ? pkg : pkg_free, clsImportPatch);
			mContext.startActivity(bl);
		} catch (ActivityNotFoundException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
			Toast.makeText(mContext, R.string.blocklauncher_failed, Toast.LENGTH_LONG).show();
		}
	}

	public void installScript(File object) {
		final String clsImportPatch = pkg_free + ".ImportScriptActivity";
		try {
			Intent bl = new Intent("net.zhuoweizhang.mcpelauncher.action.IMPORT_SCRIPT");
			bl.setData(Uri.fromFile(object));
			bl.setClassName(isPro() ? pkg : pkg_free, clsImportPatch);
			mContext.startActivity(bl);
		} catch (ActivityNotFoundException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
			Toast.makeText(mContext, R.string.blocklauncher_failed, Toast.LENGTH_LONG).show();
		}
	}

	public void installTexturepack(File object) {
		final String clsImportPatch = pkg_free + ".ImportTexturepackActivity";
		try {
			Intent bl = new Intent("android.intent.action.VIEW");
			bl.setData(Uri.fromFile(object));
			bl.setClassName(isPro() ? pkg : pkg_free, clsImportPatch);
			mContext.startActivity(bl);
		} catch (ActivityNotFoundException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
			Toast.makeText(mContext, R.string.blocklauncher_failed, Toast.LENGTH_LONG).show();
		}
	}

	public void installSkin(File object) {
		final String clsImportPatch = pkg_free + ".ImportSkinActivity";
		try {
			Intent bl = new Intent("net.zhuoweizhang.mcpelauncher.action.SET_SKIN");
			bl.setData(Uri.fromFile(object));
			bl.setClassName(isPro() ? pkg : pkg_free, clsImportPatch);
			mContext.startActivity(bl);
		} catch (ActivityNotFoundException e) {
			if (BuildConfig.DEBUG)
				e.printStackTrace();
			Toast.makeText(mContext, R.string.blocklauncher_failed, Toast.LENGTH_LONG).show();
		}
	}

	// ================================================
	// ==
	// == RUN AND CHECK BlockLauncher ==
	// ==
	// ================================================

	public boolean isInstalled() {
		PackageManager pm = mContext.getPackageManager();
		try {
			pm.getPackageInfo(pkg, 0);
			return true;
		} catch (NameNotFoundException e) {
			try {
				pm.getPackageInfo(pkg_free, 0);
				return true;
			} catch (NameNotFoundException e1) {
				return false;
			}
		}
	}

	public boolean isPro() {
		PackageManager pm = mContext.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(pkg, 0);
			if (pi != null)
				return true;
			else
				return false;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	public boolean run() {
		if (!isInstalled())
			return false;
		if (isPro()) {
			if (!runPro())
				return runFree();
			else
				return true;
		} else
			return runFree();
	}

	public boolean runPro() {
		Intent in = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
		in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			in.setComponent(new ComponentName(pkg, cls));
			mContext.startActivity(in);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean runFree() {
		Intent in = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER");
		in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			in.setComponent(new ComponentName(pkg_free, cls_free));
			mContext.startActivity(in);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
