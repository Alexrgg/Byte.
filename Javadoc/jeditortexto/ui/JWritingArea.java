package jeditortexto.ui;

import jeditortexto.logica.FuncionesEditor;
import jeditortexto.logica.FuncionesProgramador;
import jeditortexto.dominio.Documento;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.util.StringTokenizer;
import java.util.Collection;
import java.util.ArrayList;

import java.lang.IndexOutOfBoundsException;

public class JWritingArea{

   ArrayList documentos;
   ArrayList ventanas;
   JTabbedPane tabbedPane;

    public JTabbedPane getWritingArea(){
      return this.tabbedPane;
    }

    public JWritingArea(JEditorTexto jEditorTexto){
      this.documentos= new ArrayList();
      this.ventanas= new ArrayList();
      this.tabbedPane = new JTabbedPane();

      this.crearVentana(jEditorTexto);
    }

    public void crearVentana(JEditorTexto jEditorTexto){
      //Tabbed Panes
      //https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
      //Añado un nuevo documento
      this.documentos.add(new Documento());
      int numeroDocumentos=documentos.size()-1;
      //Le asocio un KeyListener
      ((JTextArea) this.documentos.get(numeroDocumentos)).addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_DELETE || e.getKeyCode()==32){ //Si se pulsa \n actualizo numero de palabras y de lineas
                    //Actualizar numero de lineas
                    int numLineas = FuncionesEditor.setNumeroLineas((JTextArea) JWritingArea.this.documentos.get(JWritingArea.this.tabbedPane.getSelectedIndex()));
                    jEditorTexto.setLblNumeroLineas(numLineas);
                    //Actualizar numero de palabras
                    int numPalabras =FuncionesEditor.setNumeroPalabras((JTextArea) JWritingArea.this.documentos.get(JWritingArea.this.tabbedPane.getSelectedIndex()));
                    jEditorTexto.setLblNumeroPalabras(numPalabras);

                  }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_PLUS){
                      FuncionesEditor.aumentarTamanoLetra(jEditorTexto);
                  }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_MINUS){
                      FuncionesEditor.reducirTamanoLetra(jEditorTexto);

                //MENU PROGRAMADOR
                }else if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_C){
                    FuncionesProgramador.compilarArchivo(jEditorTexto);

                }else if(e.isControlDown() && e.isShiftDown() && e.getKeyCode()==KeyEvent.VK_E){
                      FuncionesProgramador.ejecutarArchivo(jEditorTexto);

                //MENU ARCHIVO
                }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_S){
                    FuncionesEditor.guardarArchivo(jEditorTexto);

                }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_N){
                    FuncionesEditor.nuevoArchivo(jEditorTexto);

                }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_Q){
                    FuncionesEditor.cerrarArchivo(jEditorTexto);

                }else if(e.isControlDown() && e.getKeyCode()==KeyEvent.VK_O){
                    FuncionesEditor.abrirArchivo(jEditorTexto);
                }
            }
      });

     this.ventanas.add( new JScrollPane((JTextArea) this.documentos.get(numeroDocumentos)));
     //Añado el documento al panel
     this.tabbedPane.addTab(((Documento) documentos.get(numeroDocumentos)).getNombre(), new ImageIcon("jeditortexto/icons/new.png") ,(JScrollPane) this.ventanas.get(numeroDocumentos));
     //Action listener que detecta cuando se cambia de pestaña:
     //http://www.java2s.com/Code/JavaAPI/javax.swing/JTabbedPaneaddChangeListenerChangeListenerl.htm
      tabbedPane.addChangeListener(new ChangeListener(){
          public void stateChanged(ChangeEvent changeEvent){
            JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            //Actualizar numero de lineas
            int numLineas = FuncionesEditor.setNumeroLineas((JTextArea) JWritingArea.this.documentos.get(index));
            jEditorTexto.setLblNumeroLineas(numLineas);
            //Actualizar numero de palabras
            int numPalabras =FuncionesEditor.setNumeroPalabras((JTextArea) JWritingArea.this.documentos.get(index));
            jEditorTexto.setLblNumeroPalabras(numPalabras);
          }
      });

    }

    public void cerrarVentana(JEditorTexto jEditorTexto){
      int ventanaActual = tabbedPane.getSelectedIndex();
      try{
        tabbedPane.removeTabAt(ventanaActual);
        documentos.remove(ventanaActual);
        ventanas.remove(ventanaActual);
      }catch (IndexOutOfBoundsException indexOutOfBound){
        //Si solo hay una ventana y se cierra da error porque no mueden existir 0 ventanas.
        //Si ocurre se crea una ventana nueva.
        this.crearVentana(jEditorTexto);
        documentos.remove(ventanaActual);
        ventanas.remove(ventanaActual);
      }
    }

    public boolean fileOpen(String nombre, String dominio){
      return documentos.contains(new Documento(nombre,dominio));
    }
    //Setters
    public void setNombre(String nombre){
      int ventanaActual = tabbedPane.getSelectedIndex();
      ((Documento) documentos.get(ventanaActual)).setNombre(nombre);
      tabbedPane.setTitleAt(ventanaActual,nombre);
    }

    public void setDominio(String dominio){
      int ventanaActual = tabbedPane.getSelectedIndex();
      ((Documento) documentos.get(ventanaActual)).setDominio(dominio);
    }

    public void setGuardado(boolean guardado){
      int ventanaActual = tabbedPane.getSelectedIndex();
      ((Documento) documentos.get(ventanaActual)).setGuardado(guardado);
    }

    public void setFocoUltimo(){
      tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
    }

    public void setContenido(String contenido){
      int ventanaActual = tabbedPane.getSelectedIndex();
      ((Documento) documentos.get(ventanaActual)).setContenido(contenido);
    }

    //Getters
    public String getNombre(){
      int ventanaActual = tabbedPane.getSelectedIndex();
      return ((Documento) documentos.get(ventanaActual)).getNombre();
    }

    public String getDominio(){
      int ventanaActual = tabbedPane.getSelectedIndex();
      return ((Documento) documentos.get(ventanaActual)).getDominio();
    }

    public JTextArea getActualTextArea(){
        int num = tabbedPane.getSelectedIndex();
        return (JTextArea) documentos.get(num);
    }

    public boolean isActualizado(){
      Documento documento = (Documento) this.getActualTextArea();
      String contenidoDocumento=documento.getContenido();//contenido antes de guardarlo
      String contenidoActual = documento.getText();
      if (contenidoActual.equals(contenidoDocumento))
        return true;
      else
        return false;
    }
    public boolean isSaved(){
      int ventanaActual = tabbedPane.getSelectedIndex();
      return ((Documento) documentos.get(ventanaActual)).getGuardado();

    }
    public String getContenido(){
      int ventanaActual = tabbedPane.getSelectedIndex();
      return ((Documento) documentos.get(ventanaActual)).getContenido();
    }

}
