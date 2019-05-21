# Byte editor

##### Contenido
* [Estructura](#estructura)
* [Objetos](#objetos)
* [Ventanas](#ventanas)
  * [JEditorTexto](#JEditorTexto)
  * [JPanelOptions](#JPanelOptions)
  * [JWritingArea](#JWritingArea)
  * [JCompilacion](#JCompilacion)
* [Funciones](#funciones)
  * [Funciones del Editor de Texto](#editor)
  * [Funciones de Programación](#programacion)
    * [Compilar](#compilar)
    * [Ejecutar](#ejecutar)  
* [Ejemplos de Ejecución](#ejemplo-ejecucion)
* [Futuras realizaciones](#futuras-realizaciones)


## Estructura
El programa sigue una estructura de paquetes, estando dividido en tres bloques: **dominio**, **logica** y **ui**. Estos tres bloques se encuentra detro del paquete **jeditortexto**.

El paquete **dominio** contiene los objetos de la aplicación, en este caso esta solo compuesta por un único objeto **Documentos**.  El paquete **logica** contiene los métodos estáticos que se encargan de realizar funciones generales del editor. El paquete **ui** contiene todas las clases que interaccionan con la interfaz de usuario. 

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/estructuraPaquetes.jpg)

## Objetos
Contenidos en el paquete [dominio](#estructura).

##### Documentos
Es el objeto asociado a cada archivo. Contiene:

* Nombre-> nombre del archivo con su extension.
* Dominio-> PATH del archivo.
* Guardado-> boolean que tiene valor true si ha sido guardado por primera vez.
* Contenido-> guada el contenido de la ultima versión del archivo de texto.

```java
private String nombre;
private String dominio;
private boolean guardado;
private String contenido;
```
El atributo **guardado** se usa para, a la hora de guardar un archivo, comprobar si ha sido guardado previamente. Esto se realiza en la función **isActualizado()** localizada en [JWritingArea](#JWritingArea).

A la función se la llama de la sigente forma:
```java
jWritingArea.isActualizado()
```
El atributo **contenido** se usa para comprobar si un archivo ha sido editado después de la última vez que fue guardado. Esto se realiza en la función **isSaved()** localizada en [JWritingArea](#JWritingArea). Dicha función usa el método **equals()**.

A la función se la llama de la sigente forma:
```java
jWritingArea.isSaved()
```
Además de los getters y setters se ha implementado el método **equals**:
```java
@Override
public boolean equals(Object obj){
  if(obj instanceof Documento){
    Documento documento = (Documento) obj;
    if(this.getNombre().equals(documento.getNombre()) && this.getDominio().equals(documento.getDominio()))
      return true;
    return false;
  }else{
    return false;
  }
}
```
## Ventanas
Contenidas en el paquete [ui](#estructura).

### JEditorTexto
Es la clase principal y hereda de **JFrame**.

Lo primero que establezco es la misma apariencia que tienen las ventanas de windows ( https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html)
```java
try {
  //El programa usa el L&F del sistema
  UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
}
catch (Exception e) {
  System.out.println("Error cargar el L&F del sistema.");
}
```

A continuación establezco los componetes del JFrame con **setComponents()**.
El JFrame esta compuesto por un **BorderLayout()** formado por:
* Un JPanel en el "norte" que contiene los menus y los botones con las herramientas del editor([JPanelOptions](#JPanelOptions)).
* Un JPanel en el "centro" que contiene los JTextArea donde se editan los archivos([JWritingArea](#JWritingArea)).
* Un JPanel en el "norte" que contiene el número de lineas y el número de palabras. Este se crea llamando al metodo **setInfo()**.

```java
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
```

* Un JPanel en el "este" que contiene el **Terminal** ([JCompilacion](#JCompilacion)).


A continuación establezco las cualidades del JFrame con **setFrame()**.
Se fija el icono de la aplicación, el color de fondo, la opción por defecto de cerrado y las dimensiones del JFrame (ocupa toda la pantalla por defecto).

```java
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
```

### JPanelOptions
Esta compuesto por los siguentes componentes:
* Un JMenuBar:

```java
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
```
* Un JToolBar:

```java
//TOOL BAR
JToolBar toolBar;
  JButton btnGuardar;
  JButton btnNuevo;
  JButton btnAbrir;
  JButton btnCerrar;
  JButton btnCortar;
  JButton btnPegar;
  JButton btnCopiar;
  JComboBox boxFuente;
  JComboBox tamanoFuente;
  JButton btnZoomIn;   
  JButton btnZoomOut;  
  JButton btnEjecutar;
  JButton btnCompilar;    
```

La implementación de las acciones de estos componetes se han realizado mediante el uso de **ActionListener**. Toda la lógica del editor se realiza mediante métodos estáticos localizados en las clases  [FuncionesProgramador](#funciones-programacion) y [FuncionesEditor](#funciones-editor) localizados en el paquete [logica](#estructura).

##### Ejemplo:
```java
menuItemGuardarComo.addActionListener(new ActionListener(){
      @Override
      public void actionPerformed(ActionEvent e)
      {
        FuncionesEditor.guardarArchivoComo(JPanelOptions.this.jEditorTexto);
      }
});
```

### JWritingArea

Esta clase es la gestora de los documentos, asi como de sus ventanas. Además crea el JTabbedPane que permite trabajar convarios documentos a la vez.

El método **crearVentana** es el encargado de añadir un nuevo documento (un JTextArea) al JTabbedPane. El JTabbedPane tiene un **ChangeListener** que detecta cuando se cambia de pestaña para actualizar el numero de palabras y de lineas.

Además, en este metodo es donde se añade un **KeyListener** a cada JTextArea. Este **KeyListener** tiene la finalidad de actualizar el número de palabras y de lineas de cada documento, asi como definir todos los Shortcuts. La lista de Shortcuts se especifica en este [documento](https://github.com/Alexrgg/Programacio_Objetos/blob/master/Editor_de_texto/Info/Shortcuts.md).

```java
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
```

Función cerrarVentana:

```java
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
```
##### Función isActualizado()
Devuelve true si se a modificado el archivo después de la última vez que ha sido guardado.

```java
public boolean isActualizado(){
  Documento documento = (Documento) this.getActualTextArea();
  String contenidoDocumento=documento.getContenido();//contenido antes de guardarlo
  String contenidoActual = documento.getText();
  if (contenidoActual.equals(contenidoDocumento))
    return true;
  else
    return false;
}
```
##### Función isSaved()
Devuelve true si el archivo ha sido guardado alguna vez.

```java
public boolean isSaved(){
  int ventanaActual = tabbedPane.getSelectedIndex();
  return ((Documento) documentos.get(ventanaActual)).getGuardado();

}
```
### JCompilacion

Contiene los siguenes componentes:
```java
private int numCmd; //gurada el numero de lineas que se han ejecutado en el terminal del editor
public JLabel lblTitulo;
public JEditorTexto jEditorTexto;
public JPanel panelCompilacion;
public JTextArea textArea;
public JScrollPane scrollPane;
public JButton btnOcultar;
```
Esta clase se encarga de gestionar la comunicación con el usuario y las funciones de programador. Esto se ha implementado mediante el uso de un **KeyListener**. Puede consultar los comandos en el siguente [archivo](https://github.com/Alexrgg/Programacio_Objetos/blob/master/Editor_de_texto/Info/Commands.md).

```java
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
```


## Funciones
Contenidas en el paquete [logica](#estructura).

### Funciones del Editor de Texto

```java
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
```

### Funciones de Programación

#### Compilar

###### Comando:
```
[0]>>copile + ENTER
```
La compilación se realiza desde la carpeta donde se encuentra el archivo que se desea compilar. Esto permite compilar proyectos compuestos por varias clases. El programa no soporta la compilazión de proyectos con estructura de paquetes.

Para extraer los resultados de la compilazación uso un archivo llamado "compilation.txt" que se crea en el HOMEPATH del usuario. Una vez leido el texto del archivo este se elimina.

```java
public static String  HOMEPATH = System.getProperty("user.home");
```
Antes de compilar el programa guarda el archivo, esto lo hace tanto si el programa ha sido guardado previamente como si no.

###### Interrupción del proceso:
Si el proceso de compilacion se interrumpe por alguna razón se muestra en el terminal:
```
>cancelled
```
El proceso de compilacion se interrumpe si se cancela el guardado de un archivo que no ha sido guardado o si se produce un error en el tratamiento de archivos.En este último caso se imprime la "StackTrace".


###### Codigo función:
```java
public static void compilarArchivo(JEditorTexto jEditorTexto){
  FuncionesEditor.guardarArchivo(jEditorTexto);
  JWritingArea jWritingArea = jEditorTexto.getWritingArea();
  if(jWritingArea.isSaved()){
      try{
        String dominio = jWritingArea.getDominio();
        String disco = dominio.substring(0,2); //Extraigo el disco en el que se encuentr y me muevo a el.

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
        System.out.println ("Error eliminar archivo de compilation: " + ioe);
        //Error eliminar archivo de compilation
        ioe.printStackTrace();
      }
  }else{
      jEditorTexto.getCompilacion().getTextArea().append("\n>cancelled");
      jEditorTexto.getCompilacion().addNumCmd();
      jEditorTexto.getCompilacion().getTextArea().append(FuncionesProgramador.getCabecera(jEditorTexto.getCompilacion()));
  }
}
```

#### Ejecutar

###### Comando:
```
[0]>>run + ENTER
```
Se ha tenido encuenta que si al ejecutar el programa este no se ha compilado con anterioridad no se ejecute.En su lugar se muestra un mensaje de error en el que se indica que debe compilarse antes de ejecutarlo.

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/errorNoCompilado.jpg)


###### Codigo función:
```java
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
```

## Ejemplos de Ejecución

Ventana principal:

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/ventana.jpg)

Cambiar el formato y el tamaño de la fuente:

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/cambiar_formato.jpg)

Compilación y ejecución de un programa con varias cases (en este caso un programa gráfico):

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/ejecucion_finalizada.jpg)

> En el terminal en la linea "[0]>>"  no aparece el comando **compile** porque se compiló desde la opción del JMenu (Programer->Compile)

Detección de error al compilar:

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/error_compilacion.jpg)

Guardar la ejecucion:

![alt text](https://github.com/Alexrgg/Byte./blob/master/Proyecto/img/save_ejecucion.jpg)


## Futuras realizaciones
* Trabajar con estructura de paquetes
* Autocompletado
* Deshacer cambios
