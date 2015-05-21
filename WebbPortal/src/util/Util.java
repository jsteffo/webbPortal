package util;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Util {
	public static String restDir = "/home/stefan/.portal/restCalls/";
	  public static void copy(String from, String to){
		    Path FROM = Paths.get(restDir + from);
		    
		    Path TO = Paths.get(restDir + to);
		    //overwrite existing file, if exists
		    CopyOption[] options = new CopyOption[]{
		      StandardCopyOption.REPLACE_EXISTING,
		      StandardCopyOption.COPY_ATTRIBUTES
		    }; 
		    
		    try {
				Files.copy(FROM, TO, options);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
}
