package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunPython {
	public void runPython(String file) throws IOException 
	{ //need to call myscript.py and also pass arg1 as its arguments.
	  //and also myscript.py path is in C:\Demo\myscript.py

//	    String[] cmd = {
//	      "python",
//	      "C:\\Users\\KinectProcessing\\eclipse\\RADCscrapper\\resources\\PythonEmailer.py",
//	      	file
//	    };
//	    Runtime.getRuntime().exec(cmd);
	    
	    System.out.println("I will run a Python script!");
        Runtime r = Runtime.getRuntime();
        String pyScript = "resources/PythonEmailer.py";

        File f = new File(pyScript);
        if (f.exists() && !f.isDirectory()) {
            try {
                Process p = r.exec("python " + pyScript + " " + file);
                
//                BufferedReader in = new BufferedReader(
//                    new InputStreamReader(p.getInputStream()));
//                String line = null;
//                while ((line = in .readLine()) != null) {
//                    System.out.println(line);
//                }
                System.out.println("Python script ran!!");
                System.out.println("cmd=python " + pyScript + " " + file);
            } catch (Exception ex) {
                System.out.println("Something bad happened!!");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Unexistent file!" + pyScript);
        }
	}
}

