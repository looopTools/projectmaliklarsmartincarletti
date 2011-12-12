package service;
import java.io.File;


public class Service {
	
	private static File $PATH;
	
	public Service(){
		
	}
	
	public static void setPath(File path){
		Service.$PATH = path; 
	}
	
	public static File returnPath(){
		return Service.$PATH;
	}

}
