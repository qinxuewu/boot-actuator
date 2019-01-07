package com.github.qinxuewu.utils;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 读取本地日志文
 * @author qinxuewu 2019年1月4日下午4:58:54
 *
 */
public class LogReaderUtils {

	// 上次文件大小
	private static long lastTimeFileSize = 0;

	// 先进先出的 阻塞队列
	@SuppressWarnings("rawtypes")
	private static BlockingQueue query = new LinkedBlockingQueue<>(10000);

	@SuppressWarnings("unchecked")
	public static void run(String logUrl) {

		File logFile = new File(logUrl);
		try {
			long len = logFile.length();
			if (len < lastTimeFileSize) {
				lastTimeFileSize = len;
			} else if (len > lastTimeFileSize) {
				RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
				randomFile.seek(lastTimeFileSize);
				String tmp = null;

				while ((tmp = randomFile.readLine()) != null) {
					query.add(new String(tmp.getBytes("ISO-8859-1"), "UTF-8"));

				}
				lastTimeFileSize = randomFile.length();
				randomFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String poll(String logUrl) {
		try {
			run(logUrl);
			String msg = (String) query.take();
			return msg;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
}
