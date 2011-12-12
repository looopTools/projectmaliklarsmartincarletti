package maliklund87.javasourcemerger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Goes through every subfolder in the current directory,
 * finds all the .java files and merges them into a single
 * file.
 * @author Malik Lund
 *
 */
public class SourceMerger {
	private static FileWriter writer;
	
	public static void merge(String path) throws IOException{
		File output = new File("mergedFile.java");
		writer = new FileWriter(output);
		handleDirectory(new File(path));
		writer.close();
	}
	
	private static void handleDirectory(File file) throws IOException{
		if (file.isDirectory()){
			File[] fileList = file.listFiles();
			for (File f: fileList){
				handleDirectory(f);
			}
		} else {
			if (file.getAbsolutePath().endsWith(".java")){
				handleFile(file);
			}
		}
	}
	
	private static void handleFile(File file) throws IOException{
		Scanner scanner = new Scanner(file);
		writer.write("\n---- " + file.getAbsolutePath() + "\n\n");
		while (scanner.hasNext()){
			writer.write(scanner.nextLine() + "\n");
		}
		scanner.close();
	}
	
	public static void main(String[] args){
		try {
			SourceMerger.merge(".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
