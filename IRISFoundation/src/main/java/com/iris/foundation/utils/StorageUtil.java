package com.iris.foundation.utils;

import java.io.File;

import com.iris.foundation.activity.IRISApplication;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

/**
 * 存储工具,获得系统存储空间信息
 * 
 * @author yang_qingan
 *
 */
public class StorageUtil {
	private static final long MIN_FREE_SPACE = 1024 * 1024 * 5L;

	private StorageUtil() {
	}

	/**
	 * 获得扩展卡绝对路径
	 * 
	 * @return
	 */
	public static String getExternalStorageDirectoryPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	}

	/**
	 * 获得扩展卡
	 * 
	 * @return
	 */
	public static File getExternalStorageDirectory() {
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * 获得当前应用在data目录下的存储目录
	 * 
	 * @param ctx
	 *            android上下文对象
	 * @return
	 */
	public static String getCurAppDataFolder() {
		return IRISApplication.getApplication().getApplicationInfo().dataDir + "/";
	}

	/**
	 * 判断sdcard是否已挂载
	 * 
	 * @return
	 */
	public static boolean isSDCardMounded() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 获得sdcard剩余空间
	 * 
	 * @return
	 */
	public static long getSDFreeSize() {
		// 取得SD卡文件路径
		String path = getExternalStorageDirectoryPath();
		StatFs sf = new StatFs(path);

		@SuppressWarnings("deprecation")
		long blockSize = sf.getBlockSize();
		// 空闲的数据块的数量
		@SuppressWarnings("deprecation")
		long freeBlocks = sf.getAvailableBlocks();
		// 返回SD卡空闲大小
		return freeBlocks * blockSize; // 单位Byte
	}

	/**
	 * 判断sdcard是否可用
	 * 
	 * @return
	 */
	public static boolean isCanUseSDCard() {
		return isSDCardMounded() && getSDFreeSize() > MIN_FREE_SPACE;
	}

	/**
	 * 获得应用在sd卡上创建的文件夹
	 * 
	 * @return
	 */
	public static String getAppFolderFromSDCard() {
		return getAppFolderFromSDCard(null);
	}

	/**
	 * 获得应用在sd卡上创建的子文件夹
	 * 
	 * @param child
	 * @return
	 */
	public static String getAppFolderFromSDCard(String child) {
		child = TextUtils.isEmpty(child) ? "" : child + "/";
		String path = getExternalStorageDirectoryPath() + IRISApplication.getApplication().getPackageName() + "/"
				+ child;
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		return path;
	}

	/**
	 * 获得SDCard上系统分配的应用目录
	 * 
	 * @param child
	 *            子目录，
	 * @return
	 */
	public static String getExternalStoragePublicDirectory(String child) {
		File f = IRISApplication.getApplication().getExternalFilesDir(child);
		if (!f.exists()) {
			f.mkdirs();
		}
		return f.getAbsolutePath() + "/";
	}
}
