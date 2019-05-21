package jeditortexto.logica;

import jeditortexto.ui.JEditorTexto;
import jeditortexto.ui.JWritingArea;
import jeditortexto.ui.JCompilacion;
import jeditortexto.logica.FuncionesEditor;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.lang.Process;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.StringBuffer;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;


public class FuncionesProgramador{

      public static String  HOMEPATH = System.getProperty("user.home");

      public static void compilarArchivo(JEditorTexto jEditorTexto){
        FuncionesEditor.guardarArchivo(jEditorTexto);
        JWritingArea jWritingArea = jEditorTexto.getWritingArea();
        if(jWritingArea.isSaved()){
            try{
              String dominio = jWritingArea.getDominio();
              String disco = dominio.substring(0,2); //Extraigo el disco en el que se encuentr y me muevo a el.
              // Runtime.getRuntime().exec("cmd.exe " + disco);
              // Runtime.getRuntime().exec("cd \"" + dominio + "\"");  // "\"" -> añado " a amabos lados para que en caso de que halla un espacio en el dominio funcione
              //
              // Process process = Runtime.getRuntime().exec("javac " + jWritingArea.getNombre() + " 2> " + HOMEPATH + " \\compilacion.txt");
              // process.waitFor();

              StringBuffer comando=new StringBuffer();
              comando.append("cmd.exe /c ")
                     .append(disco)
                     .append(" & cd \"" )
                     .append(dominio)
                     .append("\" & javac ")
                     .append(jWritingArea.getNombre())
                     .append(" 2> ")
                     .append(HOMEPATH)
                     .append("\\compilation.txt");

              Process process = Runtime.getRuntime().exec( comando.toString());
              // Runtime.getRuntime().exec("exit");
              process.waitFor();

            }catch (IOException ioe) {
              ioe.printStackTrace();
            }catch (InterruptedException ie){
              //Error en el waitFor
              ie.printStackTrace();
            }
            FuncionesProgramador.mostrarTerminal(jEditorTexto, HOMEPATH + "\\compilation.txt");
            try{
                StringBuffer eliminar=new StringBuffer();
                eliminar.append("cmd.exe /c del ")
                        .append(HOMEPATH)
                        .append("\\compilation.txt");

                Process process = Runtime.getRuntime().exec( eliminar.toString());
            }catch (IOException ioe) {
              //Error eliminar archivo de compilation
              ioe.printStackTrace();
            }
        }else{
            jEditorTexto.getCompilacion().getTextArea().append("\n>cancelled");
            jEditorTexto.getCompilacion().addNumCmd();
            jEditorTexto.getCompilacion().getTextArea().append(FuncionesProgramador.getCabecera(jEditorTexto.getCompilacion()));
        }
      }

      public static void ejecutarArchivo(JEditorTexto jEditorTexto){
        JWritingArea jWritingArea = jEditorTexto.getWritingArea();
        if(jWritingArea.isSaved()){
            try{
              String dominio = jWritingArea.getDominio();
              String disco = dominio.substring(0,2); //Extraigo el disco en el que se encuentr y me muevo a el.
              // Runtime.getRuntime().exec("cmd.exe /c "+disco);
              // Runtime.getRuntime().exec("cmd.exe /c cd \"" + dominio + "\"");  // "\"" -> a�ado " a amabos lados para que en caso de que halla un espacio en el dominio funcione

              String nombre = jWritingArea.getNombre();
              nombre = nombre.substring(0,nombre.indexOf(".")); //Eliminar la extension del nombre.
              // Process process = Runtime.getRuntime().exec("cmd.exe /c java " + nombre + " > " + HOMEPATH + " \\run.txt");
              StringBuffer comando=new StringBuffer();
              comando.append("cmd.exe /c ")
                     .append(disco)
                     .append(" & cd \"" )
                     .append(dominio)
                     .append("\" & java ")
                     .append(nombre)
                     .append(" > ")
                     .append(HOMEPATH)
                     .append("\\run.txt");
              Process process = Runtime.getRuntime().exec( comando.toString());
              process.waitFor();

            }catch (IOException ioe) {
              ioe.printStackTrace();
            }catch (InterruptedException ie){
              //Error en el waitFor
              ie.printStackTrace();
            }
            FuncionesProgramador.mostrarTerminal(jEditorTexto,HOMEPATH + "\\run.txt");
            try{
                StringBuffer eliminar=new StringBuffer();
                eliminar.append("cmd.exe /c del ")
                        .append(HOMEPATH)
                        .append("\\run.txt");

                Process process = Runtime.getRuntime().exec( eliminar.toString());
            }catch (IOException ioe) {
              //Error eliminar archivo de run
              ioe.printStackTrace();
            }
        }else{
            jEditorTexto.getCompilacion().getTextArea().append("\n>cancelled");
            jEditorTexto.getCompilacion().addNumCmd();
            jEditorTexto.getCompilacion().getTextArea().append(FuncionesProgramador.getCabecera(jEditorTexto.getCompilacion()));
            JOptionPane.showMessageDialog(null,"It is necessary to compile before running the program..","Error",JOptionPane.ERROR_MESSAGE);
        }
      }

      public static void mostrarTerminal(JEditorTexto jEditorTexto,String archivoCompilacion){
          JCompilacion jCompilacion = jEditorTexto.getCompilacion();
          JTextArea textArea=jCompilacion.getTextArea();
          boolean compilando = true;
          jCompilacion.addNumCmd();
          if(archivoCompilacion.contains("compilation"))
                textArea.append("\n>Compilation:\n");
          else{
              compilando=false;
              textArea.append("\n>Run:\n");
          }
          try{
              FileReader fr = new FileReader(archivoCompilacion);
              BufferedReader br = new BufferedReader(fr);
              StringBuffer sbr= new StringBuffer();
              String s = br.readLine();

              while(s!=null){
                sbr.append(s + "\n");
                //jWritingArea.setContenido(br.toString());
                s = br.readLine();
              }

              if(!sbr.toString().contains(" ")){
                if(compilando)
                      textArea.append("Generated run file ");
                else
                      textArea.append("Run is completed successfully  ");
              }else{
                textArea.append(sbr.toString());
              }
              br.close();
              fr.close();
              textArea.append(FuncionesProgramador.getCabecera(jCompilacion));
          }
          catch(IOException ioe)
          {
              //ioe.printStackTrace();
              javax.swing.JOptionPane.showMessageDialog(null, "File not found", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
          }
          //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos/personas.obj"));
      }
      public static void cambiarVisibilidadTerminal(JCompilacion jCompilacion){
          if(jCompilacion.textArea.isVisible()){
            jCompilacion.textArea.setVisible(false);
            jCompilacion.scrollPane.setVisible(false);
            // lblTitulo.setVisible(false);
            jCompilacion.lblTitulo.setText(" ");
            jCompilacion.btnOcultar.setText("\u25c0");
          }else{
            jCompilacion.textArea.setVisible(true);
            jCompilacion.scrollPane.setVisible(true);
            // lblTitulo.setVisible(true);
            jCompilacion.lblTitulo.setText("Terminal");
            jCompilacion.btnOcultar.setText("\u25ba");
          }
          jCompilacion.jEditorTexto.revalidate();
      }

      public static void guardarTerminal(JCompilacion jCompilacion){
        String texto = jCompilacion.textArea.getText(); //texto que se va a guardar.Extraigo el de la ventana que está seleccionada.
        //Guardar archivo:
        //https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //Solo permite guardar archivos
        int input= fileChooser.showSaveDialog(null);//Pops up a "Save File" file chooser dialog.
        if (input== JFileChooser.CANCEL_OPTION){
          jCompilacion.textArea.append("\n>cancelled");
          FuncionesProgramador.getCabecera(jCompilacion);
          return;
        }

        File file= fileChooser.getSelectedFile(); //Obtengo la direccion del fichero que quiero guardar
        //Actualizo los valores del documento
        //https://docs.oracle.com/javase/7/docs/api/java/io/File.html

        try {
          PrintWriter output= new PrintWriter(new FileWriter(file));
          output.write(texto);
          output.close();
        }
        catch (IOException ioException) {
          JOptionPane.showMessageDialog(null,"Error while saving terminal content","Error",JOptionPane.ERROR_MESSAGE);
          jCompilacion.textArea.append("\n>cancelled\n");
          FuncionesProgramador.getCabecera(jCompilacion);
        }
      }

      public static void mostrarComandos(JCompilacion jCompilacion){
        jCompilacion.textArea.append("\n>Commands:\n\tcompile->compile current file.\n\trun->run current file.\n\tclose->close terminal\n\tsave->save terminal.\n\tclear->clear terminal.\n\thelp->show all commands.");
        FuncionesProgramador.getCabecera(jCompilacion);
      }

      public static String getCabecera(JCompilacion jCompilacion){
        StringBuffer cabecera=new StringBuffer();
        cabecera.append("\n[")
                    .append(jCompilacion.getNumCmd())
                    .append("]>>");
        return cabecera.toString();
      }

}
