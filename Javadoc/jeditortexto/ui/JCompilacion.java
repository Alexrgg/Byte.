package jeditortexto.ui;

import jeditortexto.ui.JEditorTexto;
import jeditortexto.logica.FuncionesProgramador;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCompilacion{
    private int numCmd; //gurada el numero de lineas que se han ejecutado en el terminal del editor
    public JLabel lblTitulo;
    public JEditorTexto jEditorTexto;
    public JPanel panelCompilacion;
    public JTextArea textArea;
    public JScrollPane scrollPane;
    public JButton btnOcultar;

    public JCompilacion(JEditorTexto jEditorTexto){
      this.jEditorTexto=jEditorTexto;
      panelCompilacion = new JPanel(new BorderLayout());

      panelCompilacion.setBackground(JEditorTexto.colorFondo);
      panelCompilacion.setOpaque(true);

      //Label titulo
      lblTitulo = new JLabel(" ");
      // lblTitulo.setVisible(false);
      lblTitulo.setFont(new Font("Calibri", Font.PLAIN, 17));
      lblTitulo.setBackground(JEditorTexto.colorFondo);
      lblTitulo.setOpaque(true);

      panelCompilacion.add(lblTitulo,BorderLayout.NORTH);
      //Text area donde se mostrara los errores y la ejecución
      textArea = new JTextArea(FuncionesProgramador.getCabecera(this));
      //textArea = new JTextArea("\n["+JCompilacion.this.getNumCmd()+"]>>");
      scrollPane= new JScrollPane(textArea);

      textArea.setVisible(false);
      textArea.addKeyListener(new KeyAdapter(){
          public void keyPressed(KeyEvent e){
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                  String terminal[] = JCompilacion.this.textArea.getText().split(">>");//Extraigo las distintas acciones que se han realizado en el Terminal
                  String comando =  terminal[terminal.length - 1];
                  if(comando.startsWith("compile"))
                    FuncionesProgramador.compilarArchivo(JCompilacion.this.jEditorTexto);
                  else if(comando.startsWith("run"))
                    FuncionesProgramador.ejecutarArchivo(JCompilacion.this.jEditorTexto);
                  else{
                      //Los comandos en los que es necesario imprimir por terminal [0]>>. Son aquellos que solo se pueden ejecutar desde el terminal.
                      if(comando.startsWith("close"))
                         FuncionesProgramador.cambiarVisibilidadTerminal(JCompilacion.this);
                      else if(comando.startsWith("save"))
                         FuncionesProgramador.guardarTerminal(JCompilacion.this);
                      else if(comando.startsWith("help"))
                          FuncionesProgramador.mostrarComandos(JCompilacion.this);
                      else if(comando.startsWith("clear")){
                           JCompilacion.this.textArea.setText("");
                           JCompilacion.this.setNumCmd(-1);
                      }else
                          JCompilacion.this.textArea.append("\n>Comand not found");

                      JCompilacion.this.addNumCmd();
                      JCompilacion.this.textArea.append(FuncionesProgramador.getCabecera(JCompilacion.this));
                  }

                  // String strTextArea = JCompilacion.this.textArea.getText();
                  // JCompilacion.this.textArea.setText(strTextArea.substring(0, strTextArea.length()-1));
                }
          }
      });

      scrollPane.setVisible(false);
      scrollPane.setPreferredSize(new Dimension( 400, 20));
      // textArea.setMaximumSize(new Dimension(  200, 20));
      // textArea.setMinimumSize(new Dimension(  600, 20));


      panelCompilacion.add(scrollPane,BorderLayout.CENTER);


      //Boton que muestra o oculta la ventana
      btnOcultar=new JButton();
      //btnOcultar.setPreferredSize(new Dimension( 40, 20));

      // ImageIcon img = new ImageIcon("jeditortexto/icons/play_button.png");
      // img.getImage().getScaledInstance(5,5,Image.SCALE_REPLICATE);
      // btnOcultar.setIcon(img);

      //"\u25c0"-> flecha apertura
      //"\u25ba"-> flecha cierre
      btnOcultar.setText("\u25c0");
      btnOcultar.setBackground(JEditorTexto.colorFondo);
      btnOcultar.setOpaque(true);
      // Font fontActual = btnOcultar.getFont();
      // btnOcultar.setFont(new Font(fontActual.getFontName(),Font.PLAIN, 20));
      btnOcultar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesProgramador.cambiarVisibilidadTerminal(JCompilacion.this);
              }
          });

      panelCompilacion.add(btnOcultar,BorderLayout.WEST);

    }

    public void escribirTerminal(String comando){
      this.textArea.append(comando);
    }

    public JPanel getPanelCompilacion(){
      return this.panelCompilacion;
    }

    public JTextArea getTextArea(){
      return this.textArea;
    }

    public int getNumCmd(){
      return this.numCmd;
    }
    public void addNumCmd(){
      this.numCmd++;
    }
    public void setNumCmd(int n){
      this.numCmd=n;
    }
}
