import java.awt.EventQueue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

import org.apache.commons.io.FileUtils;



public class monitor {
	PrintWriter writer;

	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	// Area area = new Area();
                // area.setText();
            }
        });
		
		LogWriter("log.txt", "Inicio de Ejecución");
		Area area = new Area();
        
        URL url = new URL("http://intranet.pemex.com/os/pep/sdc/go/Reportes%20Diarios%20Histricos/Balance_de_Gas.pdf");
		File newFile = new File("Balance_de_Gas.pdf");    
		File oldFile = new  File ("Balance_de_Gas_anterior.pdf");		
	
		try {
	        while (true) {
	           
//	            Thread.sleep(5 * 1000);
	            
	            System.out.println(new Date());       
	            
	            Download(url, newFile);
		        if (FileUtils.contentEquals(newFile,oldFile)){
		            System.out.println("Los archivos " +  newFile + " " + oldFile + " son iguales se elimina: " + newFile );
		            LogWriter("log.txt","Los archivos" +  newFile + " " + oldFile + " son iguales se elimina: " + newFile);
		        	FileUtils.forceDelete(newFile);		      
		        	}
		        else{
		        	System.out.println("Los archivos son diferentes");
		        	LogWriter("log.txt","Los archivos " +  newFile + " " + oldFile + " son diferentes");
		        	
		        	Calendar calendar = Calendar.getInstance();
		    		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); //cal.add(Calendar.DATE, -1);
		    		int month = calendar.get(Calendar.MONTH);
		    		String monthName = MonthName(month);
		    		int year = calendar.get(Calendar.YEAR);
		    		//test
		    		/*
		    		calendar.add(Calendar.DATE, -30);
		    		int tmp = calendar.get(Calendar.DAY_OF_MONTH);
		    		*/
		    		
		    		
		    		String FileName =  "Balance_de_Gas " + (dayOfMonth-1) + monthName + (year-2000) + ".pdf";    	
		    	    File newFile2 = new  File(FileName);
		    	    
		    	    //Hace una copia del archivo nuevo con la fecha en el nombre
		        	FileUtils.copyFile(newFile,newFile2 );
		        	
		        	System.out.println("El archivo \"" + newFile2 + "\" se ha guardado correctamente");
		        	area.userText("El archivo \"" + newFile2 + "\" se ha guardado correctamente");
		        	LogWriter("log.txt"," El archivo \"" + newFile2 + "\" se ha guardado correctamente");
		        	
		        	//test
		        	/*
		        	File directory = new File("D:\\Datos_Perfil\\241418\\Documents\\ADD");
		        	FileUtils.copyFileToDirectory(newFile2, directory);
		        	System.out.println("El archivo \"" + newFile2 + "\" se ha guardado correctamente en " + directory );
		        	*/
		        	
		        	//Gurada el archivo nuevo como viejo
		        	FileUtils.copyFile(newFile, oldFile);
		        	//Elimina el archivo nuevo
		        	FileUtils.forceDelete(newFile); 
		        	
		        	
		        }
		        //Thread.sleep(5 * 1000);
		        Thread.sleep(60 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		
	}
	
	static void Download(URL source,File fileToSave){
		try {
            FileUtils.copyURLToFile(source, fileToSave);            
            System.out.println("El archivo \"" + fileToSave + "\" se ha descargado");
            LogWriter("log.txt","El archivo \"" + fileToSave + "\" se ha descargado");
            
        } 
        catch(IOException e) {
        	e.printStackTrace();       
        }		
	}
		
	static String MonthName(int month){
		switch (month) {
		   case 0:
	          return "Ene";
	       case 1:
	          return "Feb";	          
	       case 2:
	    	   return "Mar";	    	   
	       case 3:
	    	   return "Abr";
	       case 4:
	    	   return "May";	      
		}
		return "";
	}
	
	static void LogWriter(String logFile, String text) throws IOException{		
		/*
		PrintWriter writer = new PrintWriter(new FileWriter(logFile,true));
		writer.println(new Date().toString() + "   " + text);
		writer.close();*/	
	}

}
