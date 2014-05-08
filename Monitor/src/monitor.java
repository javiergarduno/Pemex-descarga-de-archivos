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
	static Area area;

	public static void main(String[] args) throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	// Area area = new Area();
                // area.setText();
            }
        });
		
		LogWriter("log.txt", "Inicio de Ejecución");
		area = new Area();
        
        URL url = new URL("http://intranet.pemex.com/os/pep/sdc/go/Reportes%20Diarios%20Histricos/Balance_de_Gas.pdf");
		File newFile = new File("Balance_de_Gas.pdf");    
		File oldFile = new  File ("Balance_de_Gas_anterior.pdf");	
		
		URL urlOperativoCrudo = new URL("http://intranet.pemex.com/os/pep/sdc/go/Reportes%20Diarios%20Histricos/Resumen_Operativo_Crudo.xlsx");
		File newOperativoCrudo = new File("Resumen_Operativo_Crudo.xlsx");
		File oldOperativoCrudo = new File("Resumen_Operativo_Crudo_anterior.xlsx");
	
		try {
	        while (true) {
	        	
	            System.out.println(new Date());       
	            
	            if(Download(url, newFile)){
	            	SaveFile(newFile,oldFile,BlanceGasFormtat());
	            }
	            
	            
	            if(Download(urlOperativoCrudo, newOperativoCrudo)){	            
	            	SaveFile(newOperativoCrudo, oldOperativoCrudo, OperativoCrudoFormtat());
	            }
	            
	            
		        
		        Thread.sleep(5 * 1000);
		        //Thread.sleep(60 * 1000);
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	        System.out.println("Error en la descarga");
	    }
		
	}
	
	static boolean Download(URL source,File fileToSave){
		try {
            FileUtils.copyURLToFile(source, fileToSave);            
            LogWriter("log.txt","El archivo \"" + fileToSave + "\" se ha descargado");
            System.out.println("El archivo " + fileToSave + " Se ha descargado");
            return true;
            
        } 
        catch(IOException e) {
        	e.printStackTrace();
        	System.out.println("El archivo " + fileToSave + " No se ha descargado");
        	return false;
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
	       case 5:
	    	   return "Jun";
	       case 6:
	    	   return "Jul";
	       case 7:
	    	   return "Ago";
	       case 8:
	    	   return "Sep";
	       case 9:
	    	   return "Oct";
	       case 10:
	    	   return "Nov";
	       case 11:
	    	   return "Dic";	       
	    	   
		}
		return "";
	}
	
	static void LogWriter(String logFile, String text) throws IOException{		
		/*
		PrintWriter writer = new PrintWriter(new FileWriter(logFile,true));
		writer.println(new Date().toString() + "   " + text);
		writer.close();*/	
	}
	
	static String BlanceGasFormtat(){
		
		Calendar calendar = Calendar.getInstance();
		//int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
		int month = calendar.get(Calendar.MONTH);
		String monthName = MonthName(month);
		int year = calendar.get(Calendar.YEAR);
		
		calendar.add(Calendar.DATE, -1);
		int dayOfMonth_1 = calendar.get(Calendar.DAY_OF_MONTH);
		
		return "Balance_de_Gas " + dayOfMonth_1 + monthName + (year-2000) + ".pdf";
	}
	
	static String OperativoCrudoFormtat(){
		
		Calendar calendar = Calendar.getInstance();
		//int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); 
		int month = calendar.get(Calendar.MONTH);
		String monthName = MonthName(month);
		int year = calendar.get(Calendar.YEAR);
		
		//calendar.add(Calendar.DATE, -1);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		return "Resumen Operativo Crudo " + dayOfMonth + monthName.toLowerCase() + (year-2000) + ".xlsx";
	}

	static void SaveFile(File newFile, File oldFile, String fileName) throws IOException, InterruptedException{
		
		  if (FileUtils.contentEquals(newFile,oldFile)){
	            System.out.println("Los archivos " +  newFile + " " + oldFile + " son iguales se elimina: " + newFile );
	            LogWriter("log.txt","Los archivos" +  newFile + " " + oldFile + " son iguales se elimina: " + newFile);
	        	FileUtils.forceDelete(newFile);		      
	        	}
	        else{
	        	System.out.println("Los archivos son diferentes");
	        	LogWriter("log.txt","Los archivos " +  newFile + " " + oldFile + " son diferentes");		        	
	        	
	    		   	
	    	    File newFile2 = new  File(fileName);
	    	    
	    	    //Hace una copia del archivo nuevo con la fecha en el nombre
	        	FileUtils.copyFile(newFile,newFile2 );
	        	
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
	       
	}
}
