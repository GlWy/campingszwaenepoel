package be.camping.campingzwaenepoel.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;

public class ZipUtils {

	/**
	 * @param oldDirName
	 *            : name of the directory to be renamed (i.e. config_files)
	 * @param newDirName
	 *            : new name for the directory
	 * @param folderPath
	 *            : path to the folder to be renamed
	 */
	public static void renameFolder(String oldDirName, String newDirName,
			String folderPath) {
		oldDirName = oldDirName.replace("/", "");
		oldDirName = oldDirName.replace("\\", "");
		File file = new File(folderPath);
		String name = file.getAbsolutePath();
		name = name.replace(oldDirName, newDirName);
		File newFile = new File(name);
		file.renameTo(newFile);
	}

	/**
	 * @param directory
	 *            : directory where the zipfile is located
	 * @param zipFileAbsolutePath
	 *            : the path to the zipfile
	 * @throws IOException
	 */
	public static String uncompress(final String directory,
			String zipFileAbsolutePath) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;
		List<String> dirNames = new ArrayList<String>();

		try {
			ZipFile zipFile = new ZipFile(zipFileAbsolutePath);

			Enumeration<?> enu = zipFile.entries();

			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) enu.nextElement();

				String name = zipEntry.getName();
				long size = zipEntry.getSize();
				long compressedSize = zipEntry.getCompressedSize();
				System.out.printf(
						"name: %-20s | size: %6d | compressed size: %6d\n",
						name, size, compressedSize);

				File file = new File(directory + File.separator + name);
				if (zipEntry.isDirectory()) {
					file.mkdirs();
					dirNames.add(file.getAbsolutePath());
					continue;
				}

				File parent = file.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}

				is = zipFile.getInputStream(zipEntry);
				fos = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				is.close();
				fos.close();
			}
			zipFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}

		return directory;
	}

	static public void zipFolder(String srcFolder, String destZipFile)
			throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);

		try {
			zip = new ZipOutputStream(fileWriter);

			addFolderToZip("", srcFolder, zip);
		} finally {
			zip.flush();
			zip.close();
			fileWriter.flush();
			fileWriter.close();
		}
	}

	static private void addFileToZip(String path, String srcFile,
			ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			try {
				zip.putNextEntry(new ZipEntry(path + File.separator
						+ folder.getName()));
				while ((len = in.read(buf)) > 0) {
					zip.write(buf, 0, len);
				}
			} finally {
				in.close();
			}
		}
	}

	static private void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + File.separator
						+ fileName, zip);
			} else {
				addFileToZip(path + File.separator + folder.getName(),
						srcFolder + File.separator + fileName, zip);
			}
		}
	}

	/**
	 * Remove a directory and all of its contents.
	 * 
	 * The results of executing File.delete() on a File object that represents a
	 * directory seems to be platform dependent. This method removes the
	 * directory and all of its contents.
	 * 
	 * @return true if the complete directory was removed, false if it could not
	 *         be. If false is returned then some of the files in the directory
	 *         may have been removed.
	 */
	public static boolean removeDirectory(File directory) {

		// System.out.println("removeDirectory " + directory);

		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();

		// Some JVMs return null for File.list() when the
		// directory is empty.
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);

				// System.out.println("\tremoving entry " + entry);

				if (entry.isDirectory()) {
					if (!removeDirectory(entry))
						return false;
				} else {
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}

	/**
	 * @param directory
	 *            : directory where the zipfile is located
	 * @param artifactId
	 *            : the artifactId of the project launching this plugin
	 * @param zipFile
	 *            : name of the input zipfile
	 * @param file
	 *            : master-config file to be added to the zip file
	 * @throws Exception
	 */
	public static void addRemoveFilesExistingZip(final String directory,
			String targetDirectory, final String artifactId,
			final File zipFile, File[] filesToAdd, File[] filesToRemove)
			throws Exception {
		if (StringUtils.isEmpty(targetDirectory))
			targetDirectory = directory;

		ZipUtils.uncompress(directory, zipFile.getAbsolutePath());
		// remove zip
		if (!zipFile.delete()) {
			throw new IOException("ZipUtils.addFileToExistingZip: File "
					+ zipFile.getAbsolutePath() + " could not be deleted");
		}

		if (null != filesToAdd) {
			for (final File file : filesToAdd) {
				File newFile = new File(directory + File.separator + artifactId
						+ File.separator + file.getName());
				file.renameTo(newFile);
			}
		}

		if (null != filesToRemove) {
			for (final File file : filesToRemove) {
				// File textFile = new File(directory + File.separator +
				// artifactId + "_config" + File.separator + artifactId +
				// File.separator + NO_CONFIG + ".txt");
				if (file.exists()) {
					file.delete();
				}
			}
		}

		ZipUtils.zipFolder(directory + File.separator + artifactId,
				targetDirectory + File.separator + "config.zip");
	}

	// here is the code for the method
	public static void zipDir(String dir2zip, ZipOutputStream zos) {
		try {
			// create a new File object based on the directory we have to zip
			// File
			File zipDir = new File(dir2zip);
			// get a listing of the directory content
			String[] dirList = zipDir.list();
			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;
			// loop through dirList, and zip the files
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory()) {
					// if the File object is a directory, call this
					// function again to add its content recursively
					String filePath = f.getPath();
					zipDir(filePath, zos);
					// loop again
					continue;
				}
				// if we reached here, the File object f was not a directory
				// create a FileInputStream on top of f
				FileInputStream fis = new FileInputStream(f);
				// create a new zip entry
				ZipEntry anEntry = new ZipEntry(f.getPath());
				// place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);
				// now write the content of the file to the ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
		} catch (Exception e) {
			// handle exception
		}
	}

}
