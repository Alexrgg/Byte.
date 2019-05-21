package jeditortexto.ui;

import jeditortexto.ui.JPanelOptions;
import jeditortexto.ui.JWritingArea;
import jeditortexto.ui.JCompilacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.net.URL;


/**
  * Programa con métodos auxiliares para salto de linea
  *  y linea continua
  * @author Alejandro Rodríguez García
  * @author alex.rgg10@gmail.com
  * @version 1.0
*/

/**
  * JEditorTexto es la clase principal que hereda de  JFrame  y engloba a todos
  * elementos del editor de texto. Esta compuesto por 3 bloques prncipales:  JPanelOptions,
  * JWritingArea, y JCompilacion.
*/

public class JEditorTexto extends JFrame{
    /**
      *Establece el color de la aplicación.
    */
    public final static Color colorFondo = new Color(219, 232, 247);
    protected JPanelOptions panelOptions;
    protected JWritingArea panelWritingArea;
    protected JCompilacion panelCompilacion;
    protected JLabel lblNumeroLineas;
    protected JLabel lblNumeroPalabras;
    /**
         * Constructor del JEditorTexto.
         * @param panelOptions Contiene las funciones del editor.
         * @param panelWritingArea Contiene los archivos que se estan editando.
         * @param panelCompilacion Contiene el terminal.
    */
    public JEditorTexto(){
      super("Byte.");
      // Cambiar a la apariencia de las ventanas de Windows:
      // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
      try {
        //El programa usa el L&F del sistema
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e) {
        System.out.println("Error cargar el L&F del sistema.");
      }
      this.setComponents();
      this.setFrame();
    }

    /**
      *Crea todos los componentes del JEditorTexto.
    */
    private void setComponents(){
      //Definir estructura
      this.setLayout(new BorderLayout());

      panelWritingArea = new JWritingArea(this);
      this.add(panelWritingArea.getWritingArea(),BorderLayout.CENTER);
      panelCompilacion=new JCompilacion(this);
      this.add(panelCompilacion.getPanelCompilacion(),BorderLayout.EAST);
      panelOptions=new JPanelOptions(this);
      this.add(panelOptions.getPanelOptions(),BorderLayout.NORTH);
      this.add(this.setInfo(),BorderLayout.SOUTH);

      // panelWritingArea = new JWritingArea(this);
      // this.add(panelWritingArea,BorderLayout.CENTER);
      // panelOptions=new JPanelOptions(this);
      // this.add(panelOptions.getPanelOptions(),BorderLayout.NORTH);
      // this.add(this.setInfo(),BorderLayout.SOUTH);


    }

    /**
      *Establece los parametros gráficos del Frame.
    */
    private void setFrame(){
      this.setVisible(false);

      this.setIconImage((new ImageIcon(this.getClass().getResource("/icons/app.png"))).getImage());
      //this.setSize(800,700);

      //this.setPreferredSize(new Dimension( 400, 20));
      this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      this.setMinimumSize(new Dimension( 851, 525));

      this.getContentPane().setBackground(JEditorTexto.colorFondo);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //Lo hago visible.
      this.setVisible(true);
    }

    public JPanel setInfo(){
  		 JPanel panelInfo = new JPanel(new GridLayout(0,3));
       //Fijo color del fondo
       panelInfo.setBackground(JEditorTexto.colorFondo);

       lblNumeroLineas = new JLabel("Number of lines: 0");
       panelInfo.add(lblNumeroLineas);
       lblNumeroPalabras = new JLabel("Number of words: 0");
       panelInfo.add(lblNumeroPalabras);
       panelInfo.add(new JLabel("By Alejandro Rodriguez    "));

  		 return panelInfo;
  	}

    public void setLblNumeroLineas(int num){
      lblNumeroLineas.setText("Number of lines: " + num);
    }
    public void setLblNumeroPalabras(int num){
      lblNumeroPalabras.setText("Number of words: " + num);
    }

    public JTextArea getActualTextArea(){
      return this.panelWritingArea.getActualTextArea();
    }

    public JWritingArea getWritingArea(){
      return this.panelWritingArea;
    }

    public JCompilacion getCompilacion(){
      return this.panelCompilacion;
    }
//////////////////////////////////////////////////////////////MAIN/////////////////////////////////////////////////////////////////////////////////////
    public static void main (String []args){
      new JEditorTexto();
    }

   }
