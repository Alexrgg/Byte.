package jeditortexto.dominio;

import javax.swing.JTextArea;

public class Documento extends JTextArea{
  private String nombre;
  private String dominio;
  private boolean guardado; //false-> si todabía no se ha guardado. Nombre:Doc* Dominio:C:\Users\alexr\Documents\
  private String contenido; //Contiene el ultimo texto guardado en el documento.

  public Documento(){
    this.setGuardado(false);
    this.setNombre("Doc*");
    this.setDominio("C:\\Users\\alexr\\Documents\\");
    this.setContenido("");
  }

  public Documento(String nombre, String dominio){
    this.setGuardado(false);
    this.setNombre(nombre);
    this.setDominio(dominio);
    this.setContenido("");
  }
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

  //Getters
  public String getNombre(){
    return this.nombre;
  }

  public String getDominio(){
    return this.dominio;
  }

  public boolean getGuardado(){
    return this.guardado;
  }

  public String getContenido(){
    return this.contenido;
  }

  //Setters
  public void setNombre(String nombre){
    this.nombre=nombre;
  }

  public void setDominio(String dominio){
    this.dominio=dominio;
  }

  public void setGuardado(boolean guardado){
    this.guardado=guardado;
  }
  public void setContenido(String contenido){
    this.contenido=contenido;
  }
}
