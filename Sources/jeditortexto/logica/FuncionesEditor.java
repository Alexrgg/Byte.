package jeditortexto.logica;

import jeditortexto.ui.JEditorTexto;
import jeditortexto.ui.JWritingArea;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextArea;

import java.util.StringTokenizer;

public class FuncionesEditor{

      public static void nuevoArchivo(JEditorTexto jEditorTexto){
         jEditorTexto.getWritingArea().crearVentana(jEditorTexto);
      }

      public static void guardarArchivo(JEditorTexto jEditorTexto){
        //Si el archivo que quiere guardar el usuario existe lo hace automático y si no llama a guardar archivo como
        JWritingArea jWritingArea = jEditorTexto.getWritingArea();
        if(jWritingArea.isSaved()){
            FileWriter fichero = null;
            PrintWriter pw = null;
            try
            {
                fichero = new FileWriter(jWritingArea.getDominio() + "\\" + jWritingArea.getNombre());
                pw = new PrintWriter(fichero);
                String texto = jEditorTexto.getWritingArea().getActualTextArea().getText();
                pw.write(texto);
                jWritingArea.setContenido(texto);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                  fichero.close();
                } catch (Exception e2) {
                  e2.printStackTrace();
                }
            }
        }else{
          FuncionesEditor.guardarArchivoComo(jEditorTexto);
        }
      }

      public static void guardarArchivoComo(JEditorTexto jEditorTexto){
        JWritingArea jWritingArea = jEditorTexto.getWritingArea();
        String texto = jWritingArea.getActualTextArea().getText(); //texto que se va a guardar.Extraigo el de la ventana que está seleccionada.

        //Guardar archivo:
        //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
        JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //Solo permite guardar archivos
        int input= fileChooser.showSaveDialog(null);//Pops up a "Save File" file chooser dialog.
        if (input== JFileChooser.CANCEL_OPTION) return;
    			File file= fileChooser.getSelectedFile(); //Obtengo la direccion del fichero que quiero guardar
          //Actualizo los valores del documento
          //https://docs.oracle.com/javase/7/docs/api/java/io/File.html

          try {
            PrintWriter output= new PrintWriter(new FileWriter(file));
            if(jWritingArea.fileOpen(file.getName(), file.getParent())){
              JOptionPane.showMessageDialog(null,"The file is already open.","Information ", JOptionPane.INFORMATION_MESSAGE);
              output.close();
              FuncionesEditor.guardarArchivoComo(jEditorTexto);
            }else{
              output.write(texto);
              output.close();

              //Actualizo los atributos del documento
              jWritingArea.setNombre(file.getName());
              jWritingArea.setDominio(file.getParent());
              jWritingArea.setGuardado(true);
              jWritingArea.setContenido(texto);
            }
    			}
    			catch (IOException ioException) {
    				JOptionPane.showMessageDialog(null,"Error while open the file","Error",JOptionPane.ERROR_MESSAGE);
    			}
      }

      public static  void abrirArchivo(JEditorTexto jEditorTexto){
      		JFileChooser fileChooser = new JFileChooser();
      		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      		int input= fileChooser.showOpenDialog(null);
      		if (input== JFileChooser.CANCEL_OPTION) return;
      		File file= fileChooser.getSelectedFile();
          JWritingArea jWritingArea = jEditorTexto.getWritingArea();
          if(! jWritingArea.fileOpen(file.getName(), file.getParent())){
        		if(file.exists()) {
        			if (file.isFile()) {
        				try {

                  //Creo un nuevo tabbe
                  jWritingArea.crearVentana(jEditorTexto);
                  //Localizo el foco en el nuevo archivo
                  jWritingArea.setFocoUltimo();

                  //Actualizo los atributos del documento
                  jWritingArea.setNombre(file.getName());
                  jWritingArea.setDominio(file.getParent());
                  jWritingArea.setGuardado(true);
        					BufferedReader textInput= new BufferedReader(new FileReader (file));
        					StringBuffer buffer= new StringBuffer();
        					String texto;
                  jWritingArea = jEditorTexto.getWritingArea();
                  JTextArea textArea=jWritingArea.getActualTextArea();
                  textArea.setText("");
        					while ((texto = textInput.readLine()) !=null)
        						    buffer.append(texto + "\n");
        					textArea.append(buffer.toString());
                  // jWritingArea.setContenido(buffer.toString());
        				}
        				catch (IOException ioException) {
                  JOptionPane.showMessageDialog(null,"Error while open the file.","Error",JOptionPane.ERROR_MESSAGE);
          			}
        			}else {
                JOptionPane.showMessageDialog(null,"You must select one file.","Information ", JOptionPane.INFORMATION_MESSAGE);
              }
        		}else {
              JOptionPane.showMessageDialog(null,"File not found.","Information ", JOptionPane.INFORMATION_MESSAGE);
            }
          }else{
            JOptionPane.showMessageDialog(null,"You can not open twice the same file.","Information ", JOptionPane.INFORMATION_MESSAGE);
          }
          //Actualizar numero de lineas de la ventana abiarta
          //Actualizar numero de lineas
          int numLineas = FuncionesEditor.setNumeroLineas(jWritingArea.getActualTextArea());
          jEditorTexto.setLblNumeroLineas(numLineas);
          //Actualizar numero de palabras
          int numPalabras =FuncionesEditor.setNumeroPalabras(jWritingArea.getActualTextArea());
          jEditorTexto.setLblNumeroPalabras(numPalabras);

      }
      public static  void cerrarArchivo(JEditorTexto jEditorTexto){
          if(jEditorTexto.getWritingArea().isActualizado()==false){ //Me aseguro que este guardada la ventana
            try{
              int opcion = JOptionPane.showConfirmDialog(null,"File not save.\n\tDo you want to save it?");
              if(opcion==JOptionPane.OK_CANCEL_OPTION){
              }else if(opcion==JOptionPane.YES_NO_CANCEL_OPTION){
                  jEditorTexto.getWritingArea().cerrarVentana(jEditorTexto);
              }else if(opcion==JOptionPane.YES_NO_OPTION){
                  FuncionesEditor.guardarArchivo(jEditorTexto);
                  jEditorTexto.getWritingArea().cerrarVentana(jEditorTexto);
              }
            }catch(HeadlessException he){
              //Error en la confirmacion de clausura
              he.printStackTrace();
            }
          }else{
            jEditorTexto.getWritingArea().cerrarVentana(jEditorTexto);
          }
      }

      public static void cortarTexto(JTextArea textArea){
        textArea.cut();
      }
      public static void copiarTexto(JTextArea textArea){
        textArea.copy();
      }
      public static void pegarTexto(JTextArea textArea){
        textArea.paste();
      }
      public static void selectAll(JTextArea textArea){
        textArea.selectAll();
      }

      public static void fuenteTexto(JTextArea textArea, String fuente){
        textArea.setFont(new Font(fuente, Font.PLAIN, textArea.getFont().getSize()));
      }

      public static void tamanoTexto(JTextArea textArea, int tamano){
        textArea.setFont(new Font(textArea.getFont().getFontName(), Font.PLAIN, tamano));
      }

      public static int setNumeroLineas(JTextArea textArea){
        return textArea.getLineCount();
      }
      public static int setNumeroPalabras(JTextArea textArea){
        StringTokenizer stringTokenizer = new StringTokenizer(textArea.getText());
        return stringTokenizer.countTokens();
      }

      public static void aumentarTamanoLetra(JEditorTexto jEditorTexto){
        JTextArea textArea = jEditorTexto.getWritingArea().getActualTextArea();
        FuncionesEditor.tamanoTexto(textArea, textArea.getFont().getSize() + 2);
      }
      public static void reducirTamanoLetra(JEditorTexto jEditorTexto){
        JTextArea textArea = jEditorTexto.getWritingArea().getActualTextArea();
        FuncionesEditor.tamanoTexto(textArea, textArea.getFont().getSize() - 2);
      }
}
