package domain.huellaDeCarbono.espacio;

public abstract class Espacio {
  protected Double latitud;
  protected Double longitud;
  protected String provincia;
  protected String municipio;
  protected String localidad;
  protected String direccion;
  protected String numero;
  protected float codigoPostal;
  protected String barrio;


  public Double getLatitud() {
    return latitud;
  }

  public void setLatitud(Double latitud) {
    this.latitud = latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public void setLongitud(Double longitud) {
    this.longitud = longitud;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {  this.numero = numero; }

  public float getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(float codigoPostal) {

    this.codigoPostal = codigoPostal;
  }

  public String getBarrio() {
    return barrio;
  }

  public void setBarrio(String barrio) {
    this.barrio = barrio;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getMunicipio() {
    return municipio;
  }

  public void setMunicipio(String municipio) {
    this.municipio = municipio;
  }

  public String getLocalidad() {
    return localidad;
  }

  public void setLocalidad(String localidad) {
    this.localidad = localidad;
  }

}
