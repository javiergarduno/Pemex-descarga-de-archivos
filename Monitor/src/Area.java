import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** @see http://stackoverflow.com/a/19215436/230513 */
public class Area extends JFrame {
	private JTextArea logArea;
	private JTextArea userArea;

    public Area() {
        super("Javier Garduño - Descarga de balance de gas");        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        logArea = new JTextArea(5, 5);
        logArea.append("Este programa descarga el archivo de balance de gas y el Resumen Operativo Crudo \nademas de guardarlos con el nombre en el formato ya existente. \nLa descarga solo se realiza cuando ésta ventana se mantiene abierta, ");
        logArea.setEditable(true);
        
        userArea = new JTextArea(5, 5);
        userArea.append("Eventos:");
        userArea.setEditable(false);
        
        JScrollPane jp = new JScrollPane(logArea);        
        add(jp, BorderLayout.NORTH);
        
        JScrollPane jp2 = new JScrollPane(userArea);        
        add(jp2, BorderLayout.CENTER);
                
        pack();
        // arbitrary size to make vertical scrollbar appear
        setSize(700, 500);
        setLocationByPlatform(true);
        setVisible(true);
    }
    
    public void userText(String text){
    	userArea.append( "\n" + new Date().toString() +"  "+ text );

    }
    
    public void logText(String text){
    	logArea.append(text);
    	
    }
   

}