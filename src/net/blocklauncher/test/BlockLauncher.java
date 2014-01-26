/// This class and any its derivatives are free for uncommercial use. If you want to use it in any projects,
/// that brings you(or somebody else) money(including advertising), you MUST contact me via e-mail
/// (boyarshinand at gmail dot com).
/// This rule(as any other license) applies from publishing moment to all already existing copies of this class.
/// I(STALKER_2010) can change it at any time, and changes will apply to all copies from 10 January, 2014
/// forever or next change of this license.
/// Copies means any copies(with any modifications or not).
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

/**
 * BlockLauncher's API wrapper. Run methods of this class to know or to do something with BL.
 * @author STALKER_2010
 */
public class BlockLauncher {
	// Context, we need it to run BL
	private Context mContext;
	// BlockLauncher's classes and packages
	public static final String pkg_free = "net.zhuoweizhang.mcpelauncher";
	public static final String cls_free = "net.zhuoweizhang.mcpelauncher.LauncherAppActivity";
	public static final String pkg = "net.zhuoweizhang.mcpelauncher.pro";
	public static final String cls = "net.zhuoweizhang.mcpelauncher.pro.LauncherProActivity";
	public static final String pkg_api = pkg_free + ".api";

	/**
	 * Simple BlockLauncher's API wrapper constructor
	 * @param context Context for wrapper
	 */
	public BlockLauncher(Context context) {
		mContext = context;
	}

	// ================================================
	// ==
	// == INSTALL CONTENT ==
	// ==
	// ================================================

	/**
	 * Checks, is full public BlockLauncher's API available or not.
	 * @return result of check
	 */
	public boolean isAPIAvailable() {
		// Is BL installed?
		if (!isInstalled())
			return false;
		// Is BL new?
		int version = getVersion();
		if (version == -1)
			return false;
		if (version < 47)
			return false;
		return true;
	}

	/**
	 * Installs patch. Uses new API. Detects BL version.
	 * @param object file with patch
	 */
	public void installPatch(File object) {
		if (!isAPIAvailable()) return;
		final String clsImportPatch = pkg_api + ".ImportPatchActivity";
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

	/**
	 * Installs ModPE Script. Uses new API. Detects BL version.
	 * @param object file with script
	 */
	public void installScript(File object) {
		if (!isAPIAvailable()) return;
		final String clsImportPatch = pkg_api + ".ImportScriptActivity";
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

	/**
	 * Installs texturepack. Uses new API. Detects BL version.
	 * @param object file with texturepack
	 */
	public void installTexturepack(File object) {
		if (!isAPIAvailable()) return;
		final String clsImportPatch = pkg_api + ".ImportTexturepackActivity";
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

	/**
	 * Installs skin. Uses new API. Detects BL version.
	 * @param object file with skin
	 */
	public void installSkin(File object) {
		if (!isAPIAvailable()) return;
		final String clsImportPatch = pkg_api + ".ImportSkinActivity";
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

	/**
	 * Check, is any type of BL installed or not. 
	 * @return result of check
	 */
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

	/**
	 * Check, is BL Pro installed.
	 * @return result of check
	 */
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

	/**
	 * Run any kind of BlockLauncher
	 * @return is successful
	 */
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

	/**
	 * Run BlockLauncher Pro
	 * @return is successful
	 */
	protected boolean runPro() {
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

	/**
	 * Run BlockLauncher Free
	 * @return is successful
	 */
	protected boolean runFree() {
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

	/**
	 * Get BlockLauncher version. Useful for checking features.
	 * @return int - versionCode of BL
	 */
	public int getVersion() {
		if (!isInstalled())
			return -1;
		PackageManager pm = mContext.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(isPro() ? pkg : pkg_free, 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
