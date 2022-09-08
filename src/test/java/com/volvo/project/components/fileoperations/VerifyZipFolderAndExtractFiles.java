package com.volvo.project.components.fileoperations;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class VerifyZipFolderAndExtractFiles {
	private static final Logger log = LoggerFactory.getLogger(VerifyZipFolderAndExtractFiles.class);
	public static String unZipFilePathReturn = null;

	public static String unZipFolder() throws IOException {
		String zipFilePath1 = System.getProperty("user.home");
		String zipFilePath2 = getDownloadsPath();
		String zipFilePath = pickLatestFileFromDownloads();
		System.out.println("zipFilePath: "+zipFilePath);

		String destDir = System.getProperty("user.dir") + "/target/test-output";
		//String destDir = getDownloadsPath() + "/output";
		System.out.println("destDir: "+destDir);

		File dir = new File(destDir);
		// create output directory if it doesn't exist
		if(!dir.exists()) dir.mkdirs();
		FileInputStream fis;
		File unZippingFile = null;
		//buffer for read and write data to file
		byte[] buffer = new byte[1024];
		try {
			fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze = zis.getNextEntry();
			while(ze != null){
				String fileName = ze.getName();
				File newFile = new File(destDir + File.separator + fileName);
				unZippingFile= new File(newFile.getAbsolutePath());
				System.out.println("Unzipping to : "+newFile.getAbsolutePath());
				System.out.println("Unzipping return path : "+unZippingFile);
				//create directories for sub directories in zip
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				//close this ZipEntry
				zis.closeEntry();
				ze = zis.getNextEntry();
			}
			//close last ZipEntry
			zis.closeEntry();
			zis.close();
			fis.close();
			//unZipFilePathReturn=unZippingFile.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		unZipFilePathReturn = unZippingFile.toString();
		return unZippingFile.toString();
	}
	public static String getDownloadsPath() throws IOException {

		String downloadPath = System.getProperty("user.home");
		File file = new File(downloadPath + "/Downloads/");
		System.out.println("Downloaded folder path (getAbsolutePath) : "+file.getAbsolutePath());
		System.out.println("Downloaded folder path (getCanonicalPath) : "+file.getCanonicalPath());
		System.out.println("Downloaded folder path (getName) : "+file.getName());
		return file.getAbsolutePath();
		// return file.getCanonicalPath();
		//return file.getName();
	}
	// Get the latest file from Downloads path
	public static String pickLatestFileFromDownloads() {

		String currentUsersHomeDir = System.getProperty("user.home");

		String downloadFolder = currentUsersHomeDir + File.separator + "Downloads" + File.separator;

		File dir = new File(downloadFolder);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			log.info("There is no file in the folder");
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		String k = lastModifiedFile.toString();

		System.out.println("lastModifiedFile: "+lastModifiedFile);
		System.out.println("k: "+k);
		Path p = Paths.get(k);
		String file = p.getFileName().toString();
		System.out.println("file : "+file);
		//return file;
		return k;
	}
	public static String[] getZipFile_Directories() throws IOException {

		String downloadPath = System.getProperty("user.home");
		File file = new File(downloadPath + "/Downloads/");
		java.util.zip.ZipFile zipFile = null;
		try {
			zipFile = new java.util.zip.ZipFile(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		List<String> directories = new ArrayList<>();
		java.util.Enumeration<? extends java.util.zip.ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()){
			java.util.zip.ZipEntry entry = entries.nextElement();
			if(entry.isDirectory()){
				directories.add(entry.getName());
				System.out.println("File is : "+directories.add(entry.getName()));
			}
		}
		return directories.toArray(new String[directories.size()]);
	}
	public static String getFilePath(String path) {
		try {
			return new java.io.File(".").getCanonicalPath() + path;
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean isFileDownloaded(String downloadPath, String fileName) {
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		if (dir_contents != null) {
			for (File dir_content : dir_contents) {
				if (dir_content.getName().equals(fileName))
					return true;
			}
		}
		return false;
	}
}
