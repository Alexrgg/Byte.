package jeditortexto.ui;

import jeditortexto.logica.FuncionesEditor;
import jeditortexto.logica.FuncionesProgramador;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


import java.net.URL;

public class JPanelOptions{

  JEditorTexto jEditorTexto;
  JPanel panelPrincipal;
    //MENU BAR
    JMenuBar menuBar;
      //->menu archivo
      JMenu menuArchivo;
        JMenuItem menuItemNuevo;
        JMenuItem menuItemGuardar;
        JMenuItem menuItemGuardarComo;
        JMenuItem menuItemAbrir;
        JMenuItem menuItemCerrar;
      //->menu edeitar
      JMenu menuEditar;
        JMenuItem menuItemCopiar;
        JMenuItem menuItemCortar;
        JMenuItem menuItemPegar;
        JMenuItem menuItemSelectAll;

        //->menu Programador
        JMenu menuProgramador;
          JMenuItem menuItemCompilar;
          JMenuItem menuItemEjecutar;
          JMenuItem menuItemTerminal;

    //TOOL BAR
    JToolBar toolBar;
      JButton btnGuardar;
      JButton btnNuevo;
      JButton btnAbrir;
      JButton btnCerrar; //
      JButton btnCortar;
      JButton btnPegar;
      JButton btnCopiar;
      // JTextArea txtTamano;
      // JTextArea txtFuente;
      // JLabel lblTamano;
      // JLabel lblFuente;
      JComboBox boxFuente;
      JComboBox tamanoFuente;
      JButton btnZoomIn;        //
      JButton btnZoomOut;       //
      JButton btnEjecutar;      //
      JButton btnCompilar;      //


  private static final int tamanos[] = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};

  public JPanel getPanelOptions(){ //Esta bien palnteado???
      return this.panelPrincipal;
  }

  public JPanelOptions(JEditorTexto jEditorTexto){
    //Menu con desplegables: Archivo, Editor, Info.
    //https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
    this.jEditorTexto = jEditorTexto;

////////////////////MENU ARCHIVO///////////////////////////////////
    menuItemNuevo = new JMenuItem("New");
    menuItemGuardar = new JMenuItem("Save");
    menuItemGuardarComo = new JMenuItem("Save As");
    menuItemAbrir = new JMenuItem("Open");
    menuItemCerrar = new JMenuItem("Close");

    menuItemNuevo.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
              FuncionesEditor.nuevoArchivo(JPanelOptions.this.jEditorTexto);
          }
    });

    menuItemGuardar.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.guardarArchivo(JPanelOptions.this.jEditorTexto);
          }
    });

    menuItemGuardarComo.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.guardarArchivoComo(JPanelOptions.this.jEditorTexto);
          }
    });

    menuItemAbrir.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.abrirArchivo(JPanelOptions.this.jEditorTexto);
          }
    });

    menuItemCerrar.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.cerrarArchivo(JPanelOptions.this.jEditorTexto);
          }
    });

    menuArchivo = new JMenu("File");
    menuArchivo.add(menuItemNuevo);
    menuArchivo.add(menuItemGuardar);
    menuArchivo.add(menuItemGuardarComo);
    menuArchivo.add(menuItemAbrir);
    menuArchivo.add(menuItemCerrar);


////////////////////MENU EDITAR///////////////////////////////////
    // Crear items:
    menuItemCortar = new JMenuItem("Cut");
    menuItemCopiar = new JMenuItem("Copy");
    menuItemPegar = new JMenuItem("Paste");
    menuItemSelectAll = new JMenuItem("Select All");

    //Asignar action un ActionListener
    menuItemCortar.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.cortarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
          }
    });

    menuItemCopiar.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.copiarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
          }
    });
    menuItemPegar.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.pegarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
          }
    });
    menuItemSelectAll.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e)
          {
            FuncionesEditor.selectAll(JPanelOptions.this.jEditorTexto.getActualTextArea());
          }
    });





    //Crear menu:
    menuEditar =new JMenu("Edit");
    menuEditar.add(menuItemCopiar);
    menuEditar.add(menuItemCortar);
    menuEditar.add(menuItemPegar);
    menuEditar.add(menuItemSelectAll);

////////////////////MENU PROGRAMADOR///////////////////////////////////

      // Crear items:
      menuItemCompilar = new JMenuItem("Compile");
      menuItemEjecutar = new JMenuItem("Run");
      menuItemTerminal = new JMenuItem("Terminal");

      //Asignar action un ActionListener
      menuItemCompilar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
              JPanelOptions.this.jEditorTexto.panelCompilacion.escribirTerminal("compile");
              FuncionesProgramador.compilarArchivo(JPanelOptions.this.jEditorTexto);
            }
      });

      menuItemEjecutar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
              JPanelOptions.this.jEditorTexto.panelCompilacion.escribirTerminal("run");
              FuncionesProgramador.ejecutarArchivo(JPanelOptions.this.jEditorTexto);
            }
      });

      menuItemTerminal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
              FuncionesProgramador.cambiarVisibilidadTerminal(JPanelOptions.this.jEditorTexto.panelCompilacion);
            }
      });

      //Crear menu:
      menuProgramador = new JMenu("Programer");
      menuProgramador.add(menuItemCompilar);
      menuProgramador.add(menuItemEjecutar);
      menuProgramador.add(menuItemTerminal);


///////////////////   MENU BAR  ////////////////////////////////////
       menuBar =new JMenuBar();

       menuBar.setBackground(JEditorTexto.colorFondo);

       menuBar.add(menuArchivo);
       menuBar.add(menuEditar);
       menuBar.add(menuProgramador);

// _________________________________________________________________________________
    //Barra en la que aparecen los símbolos de: GuardarComo, nuevoArchivo, abrir, cortar, peger, copiar. Ademas un TextBox para: tamaño, color, fuente.
    //https://docs.oracle.com/javase/tutorial/uiswing/components/toolbar.html

      // btnGuardarComo = new JButton("GuardarComo");
      // btnNuevo = new JButton("Nuevo");
      // btnAbrir = new JButton("Abrir");
      // btnCortar = new JButton("Cortar");
      // btnPegar = new JButton("Pegar");

      btnAbrir = new JButton();
      btnCerrar = new JButton();
      btnGuardar = new JButton();
      btnNuevo = new JButton();
      btnCortar = new JButton();
      btnPegar = new JButton();
      btnCopiar = new JButton();
      btnZoomIn = new JButton();
      btnZoomOut = new JButton();
      btnEjecutar = new JButton();
      btnCompilar = new JButton();

      //Establecer iconos para los botones
      btnNuevo.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/new.png")));
      btnGuardar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/save.png")));
      btnAbrir.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/open.png")));
      btnCortar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/cut.png")));
      btnPegar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/paste.png")));
      btnCopiar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/copy.png")));
      btnCerrar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/error.png")));
      btnZoomIn.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/zoom_in.png")));
      btnZoomOut.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/zoom_out.png")));
      btnEjecutar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/ejecutar.png")));
      btnCompilar.setIcon(new ImageIcon(jEditorTexto.getClass().getResource("/icons/java.png")));

      //Establecer color del fondo
      btnGuardar.setBackground(JEditorTexto.colorFondo);
      btnNuevo.setBackground(JEditorTexto.colorFondo);
      btnAbrir.setBackground(JEditorTexto.colorFondo);
      btnCortar.setBackground(JEditorTexto.colorFondo);
      btnPegar.setBackground(JEditorTexto.colorFondo);
      btnCopiar.setBackground(JEditorTexto.colorFondo);
      btnCerrar.setBackground(JEditorTexto.colorFondo);
      btnZoomIn.setBackground(JEditorTexto.colorFondo);
      btnZoomOut.setBackground(JEditorTexto.colorFondo);
      btnEjecutar.setBackground(JEditorTexto.colorFondo);
      btnCompilar.setBackground(JEditorTexto.colorFondo);

      // //Establecer bordes para los textArea
      // txtTamano.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
      // txtFuente.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
      //
      // //Dimensiones textArea
      // txtTamano.setPreferredSize(new Dimension( 40, 20));
      // txtTamano.setMaximumSize(new Dimension( 40, 20));
      // txtTamano.setMinimumSize(new Dimension( 20, 20));
      //
      // txtFuente.setPreferredSize(new Dimension( 80, 20));
      // txtFuente.setMaximumSize(new Dimension( 80, 20));
      // txtFuente.setMinimumSize(new Dimension( 20, 20));

      btnGuardar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.guardarArchivo(JPanelOptions.this.jEditorTexto);
              }
          });

      btnNuevo.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.nuevoArchivo(JPanelOptions.this.jEditorTexto);
              }
          });

      btnAbrir.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.abrirArchivo(JPanelOptions.this.jEditorTexto);
              }
          });

      btnCortar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.cortarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
              }
          });

      btnPegar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.pegarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
              }
          });

      btnCopiar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.copiarTexto(JPanelOptions.this.jEditorTexto.getActualTextArea());
              }
          });

      btnCerrar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.cerrarArchivo(JPanelOptions.this.jEditorTexto);
              }
          });
      btnZoomIn.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.aumentarTamanoLetra(JPanelOptions.this.jEditorTexto);
              }
          });
      btnZoomOut.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesEditor.reducirTamanoLetra(JPanelOptions.this.jEditorTexto);
              }
          });
      btnCompilar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesProgramador.compilarArchivo(JPanelOptions.this.jEditorTexto);
              }
          });

      btnEjecutar.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                FuncionesProgramador.ejecutarArchivo(JPanelOptions.this.jEditorTexto);
              }
          });

      //SELECTOR TIPO FUENTE
      //Obtengo los tipo de fuentes de los que dispone el sisitema:
      GraphicsEnvironment graphicsEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
      String fontNames[]= graphicsEnv.getAvailableFontFamilyNames();
      boxFuente = new JComboBox(fontNames);
      boxFuente.setPreferredSize(new Dimension( 150, 20));
      boxFuente.setMaximumSize(new Dimension( 150, 20));
      boxFuente.setMinimumSize(new Dimension( 100, 20));
      boxFuente.setBackground(JEditorTexto.colorFondo);

      boxFuente.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                  FuncionesEditor.fuenteTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(), 	JPanelOptions.this.boxFuente.getSelectedItem().toString());

              }
          });


      // lblTamano = new JLabel("Tamano: ");
      // lblFuente = new JLabel("Fuente: ");
      // txtTamano = new JTextArea();
      // txtFuente = new JTextArea();

      //SELECTOR TAMAÑO FUENTE
      tamanoFuente= new JComboBox();
  		//tamanoFuente.addItem("tamano Fuente");
      for(int i : tamanos){
        tamanoFuente.addItem(Integer.toString(i));
      }

  		//tamanoFuente.setToolTipText("tamano de fuente");
      tamanoFuente.setPreferredSize(new Dimension( 50, 20));
      tamanoFuente.setMaximumSize(new Dimension( 50, 20));
      tamanoFuente.setMinimumSize(new Dimension( 30, 20));
      tamanoFuente.setBackground(JEditorTexto.colorFondo);


  		tamanoFuente.addItemListener(
  			new ItemListener () {
  				public void itemStateChanged(ItemEvent e) {

  					int elegido;
  					elegido=tamanoFuente.getSelectedIndex();

  					switch (elegido) {
              case 0:
  							FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[0]);
  							break;
  						case 1:
  							FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[1]);
  							break;

    					case 2:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[2]);
                break;

    					case 3:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[3]);
                break;

    					case 4:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[4]);
                break;

    					case 5:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[5]);
                break;
              case 6:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[6]);
                break;

              case 7:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[7]);
                break;

              case 8:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[8]);
                break;

              case 9:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[9]);
                break;

              case 10:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[10]);
                break;
              case 11:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[11]);
                break;

              case 12:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[12]);
                break;

              case 13:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[13]);
                break;

              case 14:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[14]);
                break;

              case 15:
                FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),tamanos[15]);
                break;
  					}
  				}
      });

      // txtTamano.addKeyListener(new KeyAdapter()
      //     {
      //         @Override
      //         public void keyPressed(KeyEvent e)
      //         {
      //           if(e.getKeyCode()==KeyEvent.VK_ENTER)
      //               FuncionesEditor.tamanoTexto(JPanelOptions.this.jEditorTexto.getActualTextArea(),Integer.parseInt(JPanelOptions.this.txtTamano.getText()));
      //         }
      //     });
      //

      toolBar = new JToolBar();

      toolBar.setBackground(JEditorTexto.colorFondo);

      toolBar.add(btnGuardar);
      toolBar.add(btnNuevo);
      toolBar.add(btnAbrir);
      toolBar.add(btnCerrar);
      toolBar.add(btnCortar);
      toolBar.add(btnPegar);
      toolBar.add(btnCopiar);
      toolBar.add(boxFuente);
      toolBar.add(tamanoFuente);
      toolBar.add(btnZoomIn);
      toolBar.add(btnZoomOut);
      toolBar.add(btnEjecutar);
      toolBar.add(btnCompilar);
      // toolBar.add(lblTamano);
      // toolBar.add(txtTamano);
      // toolBar.add(lblFuente);
      // toolBar.add(txtFuente);

// _________________________________________________________________________________
      panelPrincipal = new JPanel(new BorderLayout());
      panelPrincipal.add(menuBar,BorderLayout.NORTH);
      panelPrincipal.add(toolBar,BorderLayout.SOUTH);
  }
}
