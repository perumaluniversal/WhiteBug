package common;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.*;

public class FileOperation {

	
	public static String ReadFile(String Filename) throws Exception
	{
		return ReadFile(Filename,"UTF-8");
	}
	
	
	public static boolean MoveFile(String Sourcefile,String Destinationfile)
	{
		 File afile=null;
 	     File bfile=null;
 	     
 	    InputStream inStream = null;
 		OutputStream outStream = null;
		try
		{
			afile =new File(Sourcefile);
			bfile =new File(Destinationfile);
			
			 inStream = new FileInputStream(afile);
		     outStream = new FileOutputStream(bfile);

    	     byte[] buffer = new byte[1024];

    	    int length;
    	    //copy the file content in bytes
    	    while ((length = inStream.read(buffer)) > 0)
    	    	outStream.write(buffer, 0, length);

    	    inStream.close();
    	    outStream.close();

    	    //delete the original file
    	    afile.delete();
    	    
    	    return true;
    	    
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			
			return false;
		}
		finally
		{
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
			inStream=null;
			
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
			outStream=null;
			
			afile = null;
			bfile = null;
		}
	}
	
	
	public static String ReadFile(String Filename,String encoding) throws Exception {
		String everything = new String();
		 FileInputStream fis = null;
         BufferedReader br = null;
		try {
			
			fis = new FileInputStream(SystemInfo.TransformFilepath(Filename));
			br = new BufferedReader(new InputStreamReader(fis,encoding));
			
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			everything = sb.toString();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally {
			
			if(br!=null)
			br.close();
			br = null;
			
			if(fis!=null)
				fis.close();
			
		}
		return everything;
	}
	
	public static Boolean CopyFile(String sourcefilepath,String destinationfilepath)
	{
		try
		{
			Files.copy(Paths.get(sourcefilepath), Paths.get(destinationfilepath),new CopyOption[]{
			      StandardCopyOption.REPLACE_EXISTING,
			      StandardCopyOption.COPY_ATTRIBUTES
			     });
			return true;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return false;
	}
	
	public static Boolean DeleteFile(String filepath)
	{
		try
		{
			if(new File(filepath).exists())
			Files.delete(Paths.get(filepath));
			return true;
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		return false;
	}
	
	
	public static void FileWriteLine(String filename, String data,String encoding)
			throws Exception {
		BufferedWriter br = null;
		try {
			 Writer writer = new OutputStreamWriter(
                     new FileOutputStream(SystemInfo.TransformFilepath(filename),true), encoding);
			br = new BufferedWriter(writer);
			br.write(data + "\r\n");
		} catch (Exception exp) {
			throw exp;
			//log.error(exp);
		} finally {
			br.close();
			br = null;
		}
	}
	
	public static void FileWriteLine(String filename, String data) throws Exception
	{
		FileWriteLine(filename,data,"UTF-8");
	}
	
	public static void ByteFileWriteLine(String filename,String data) throws Exception
	{
		FileOutputStream writer = null;
		try
		{
			writer = new FileOutputStream(SystemInfo.TransformFilepath(filename),true);
			writer.write(data.getBytes("Unicode"));
		}
		catch(Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(writer!=null)
			{
				writer.close();
				writer = null;
			}
		}
	}
	
	public static void ByteFileWrite(String filename,byte[] data) throws Exception
	{
		FileOutputStream writer = null;
		try
		{
			writer = new FileOutputStream(SystemInfo.TransformFilepath(filename),true);
			writer.write(data);
		}
		catch(Exception exp)
		{
			throw exp;
		}
		finally
		{
			if(writer!=null)
			{
				writer.close();
				writer = null;
			}
		}
	}
	
	
	public static void FileWrite(String filename, String data,boolean append)
			throws Exception {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(
					new File(SystemInfo.TransformFilepath(filename)), append));
			
			br.write(data);
		} catch (Exception exp) {
			throw exp;
			//log.error(exp);
		} finally {
			br.close();
			br = null;
		}
	}
	
	public static void FileWrite(String filename, String data)
			throws Exception {
		BufferedWriter br = null;
		try {
			br = new BufferedWriter(new FileWriter(
					new File(SystemInfo.TransformFilepath(filename)), true));
			
			br.write(data);
		} catch (Exception exp) {
			throw exp;
			//log.error(exp);
		} finally {
			br.close();
			br = null;
		}
	}
	
	
	
	public static void touchfile(String filename) throws Exception
	{
		try
		{
			if(!new File(filename).createNewFile())
				throw new Exception("unable to create empty folder");
		}
		catch(Exception exp)
		{
			throw exp;
		}
	}
	
	

	public static void CreateFileDirectory() {
		boolean success = (new File("AnalysedFiles")).mkdirs();
		if (!success) {
			// Directory creation failed
		}
	}
	
	public static void CreateFileDirectory(String Directory) {
		boolean success = (new File(Directory)).mkdirs();
		if (!success) {
			// Directory creation failed
		}
	}
	
	public static String getXMLAsStringfromZip(File unzipfile) throws Exception{
		try{
	        FileInputStream file = new FileInputStream (unzipfile );
	        DataInputStream in = new DataInputStream (file );
	        byte[] b = new byte[in.available ()];
	        in.readFully(b);
	        in.close ();
	        String result = new String (b, 0, b.length, "UTF-8");
	        //System.out.println(result);
	        return result;			
		}
		catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}

	public static String[] GetDirectoryList(String filepath) {
		try {
			File folder = new File(filepath);
			return folder.list();
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		}
	}

	public static File[] GetFileList(String filepath) {
		try {
			File folder = new File(filepath);

			return folder.listFiles();
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		}
	}

	public static String[] GetFileName(String filepath) {
		try {
			//System.out.println(filepath);
			File folder = new File(filepath);
			return folder.list();
		} catch (Exception exp) {
			exp.printStackTrace();
			throw exp;
		}
	}
	
	public static String gunzip(String gzipFile,String outputFolder) throws Exception{
		 
	     byte[] buffer = new byte[1024];
	     String outfile = "";
	     FileOutputStream out=null;
	     try{
	    	 
	    	// create output directory is not exists
				File folder = new File(outputFolder);
				if (!folder.exists()) {
					folder.mkdir();
				}
	 
	    	 GZIPInputStream gzis = 
	    		new GZIPInputStream(new FileInputStream(gzipFile));
	    	 
	    	 gzipFile = gzipFile.substring(gzipFile.lastIndexOf("\\")+1);
	    	 
	    	 outfile = outputFolder + "\\" + gzipFile.replace(".gz","");
	    	 
	    	  out = new FileOutputStream(outfile);
	 
	        int len;
	        while ((len = gzis.read(buffer)) > 0) {
	        	out.write(buffer, 0, len);
	        }
	 
	        gzis.close();
	    	out.close();
	 
	    	System.out.println("Done");
	 
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	     finally
	     {
	    	 if(out!=null)
	    	 {
	    		 out.close();
	    		 out=null;
	    	 }
	     }
	     return outfile;
	     
	   } 
	

	public static String unZipIt(String zipFile, String outputFolder) throws IOException {

		byte[] buffer = new byte[1024];
		String unzipfilepath=outputFolder;
		try {

			// create output directory is not exists
			File folder = new File(outputFolder);
			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);
				unzipfilepath = newFile.getAbsolutePath();
				System.out.println("file unzip : " + unzipfilepath);

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int i = zis.read(buffer);
				while (i != -1) {
					fos.write(buffer, 0, i);
					i = zis.read(buffer);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
			
			System.out.println("Done");
			return unzipfilepath;
			
		} catch (IOException ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			ex.printStackTrace();
			throw ex;
		}
	}

	public static void ExtractZipFile(String zipFile, String outputFolder) throws IOException {
		byte[] buffer = new byte[1024];
		try {
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				
				int i = zis.read(buffer);
				while (i != -1) {
					i = zis.read(buffer);
				}
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");
		} catch (IOException ex) {
			System.out.print(ex.toString());
			ex.printStackTrace();
			throw ex;
		}
	}

	
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.equals("");
	}

	public static boolean checkfileexist(String dir, String filename, boolean iscreatefile) {
		try {
			File downloadDirfile = new File(dir + "\\" + filename);
			if (downloadDirfile.exists()) {
				Console.println("Directory and file exist. Exit from download!");
				return false;
			} else if (iscreatefile) {
				return createFileFolder(dir, filename);
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean createFileFolder(String dir, String filename) {
		try {
			File fileDir = new File(dir);
			if (!fileDir.exists()) {
				Console.println("Directory not exist. Creating directory!");
				fileDir.mkdirs(); // create directory
			}
			File createfile = new File(dir + "\\" + filename);
			if (!createfile.exists()) {
				Console.println("File doesn't exist. Creating file!");
				createfile.createNewFile(); // create file
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean searchfile(String filepath, String searchfilename) {
		try {
			int count = 0, tot = 0;
			String result = null;
			try {
				File file = new File(filepath);
				@SuppressWarnings("resource")
				FileInputStream fis = new FileInputStream(file);
				FileChannel fc = fis.getChannel();
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(fc);
				while (scan.hasNext()) {
					scan.next();
					result = scan.findWithinHorizon(searchfilename, 0);
					if (result != null) {
						return true;
					}
					// tot++;
					// count++;
				}
				scan.close();
				fc.close();
				fis.close();

				System.out.println("Results found: " + tot + " in " + count
						+ " words ");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static ArrayList<String> searchfile(String filepath,
			ArrayList<String> ftpfilelst) {
		try {
			int count = 0, tot = 0;
			boolean result = false;
			ArrayList<String> resultfilelst = new ArrayList<String>();
			try {
				File file = new File(filepath);
				String searchfilename = "";

				for (int i = 0; i < ftpfilelst.size(); i++) {
					searchfilename = ftpfilelst.get(i);
					result = false;
					Scanner in = null;
					try {
						in = new Scanner(new FileReader(file));
						while (in.hasNextLine() && !result) {
							result = in.nextLine().indexOf(searchfilename) >= 0;
							if (result)
								break;
						}
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					} finally {
						try {
							in.close();
						} catch (Exception e) { /* ignore */
							e.printStackTrace();
						}
					}
					if (!result) {
						resultfilelst.add(searchfilename);
					}
				}
				System.out.println("Results found: " + tot + " in " + count
						+ " words ");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
			return resultfilelst;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean addFileLst(String filepath, ArrayList<String> downloadfilelst) {
		try {
			BufferedWriter br = null;
			try {
				br = new BufferedWriter(new FileWriter(new File(SystemInfo.TransformFilepath(filepath)), true));
				for (int i = 0; i < downloadfilelst.size(); i++) {
					br.append(downloadfilelst.get(i) + "\r\n");
				}
				return true;
			} catch (Exception exp) {
				
				exp.printStackTrace();
				
				return false;
			} finally {
				br.close();
				br = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public static boolean zipit(String filepath,String inputfile) throws IOException
	{
		byte[] buffer = new byte[1024];
		ZipOutputStream zos=null;
		try
		{
			FileOutputStream fos = new FileOutputStream(filepath);
    		zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(inputfile.substring(inputfile.lastIndexOf("\\")+1));
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(inputfile);
 
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}
 
    		in.close();
    		zos.closeEntry();
 
    		//remember close it
    		zos.close();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally
		{
			if(zos!=null)
			{
				zos.close();
				zos = null;
			}
		}
		return false;
	}
	
	
	public static boolean appendImportResult(String filepath,  String data) throws Exception {
		boolean resultflag = false;
		BufferedWriter br = null;
		try {
			//filename = filename.replace(".xml", ".txt");
			//if(createFileFolder(filepath, filename)){
				File folder = new File(SystemInfo.TransformFilepath(filepath));
				br = new BufferedWriter(new FileWriter(folder, true));			
				br.write(data + "\r\n");
				resultflag = true;
			//}
		} catch (Exception exp) {
			exp.printStackTrace();
			resultflag = false;
		} finally {
			br.close();
			br = null;			
		}
		return resultflag;
	}	
	
	public static class Lock
	{
		public static Boolean Create(String path)
		{
			try
			{
				FileOperation.FileWriteLine(SystemInfo.TransformFilepath(path + "lock"), "");
				//FileOperation.touchfile(path);
				return true;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return false;
		}
		
		public static Boolean Delete(String path)
		{
			try
			{
				FileOperation.DeleteFile(SystemInfo.TransformFilepath(path + "lock"));
				return true;
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return false;
		}
		
		public static Boolean HasLock(String path)
		{
			try
			{
				for(String slg : FileOperation.GetFileName(path.equals("") ? new java.io.File("").getAbsolutePath() : path))
				{
					if(slg.equals("lock"))
						return true;
				}
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			return false;
		}
	}
}