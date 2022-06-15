package EjemploDeUso;

import apiDistancia.*;

import java.io.IOException;
import java.util.List;

public class EjemploDeUso {


  public static void main(String[] args) throws IOException {
    ServicioApiDistancia servicioApiDistancia = ServicioApiDistancia.getInstancia();
   // System.out.println("Seleccione una de las siguientes provincias, ingresando su id:");
  String idPais = new String();
  String idProvincia = new String();
  String idMunicipio = new String();
  List<Pais> listadoPaises = servicioApiDistancia.listadoDePais(1);


    if(listadoPaises.size() == 0) {
      System.out.print("Estoy retornando algo nulo");
    }else {
      System.out.print("Pais: " + listadoPaises.get(0).getNombre() + "\n");
      idPais = listadoPaises.get(0).getId();
      System.out.print("ID Pais: " + listadoPaises.get(0).getId() + "\n");
    }

    List<Provincia> listadoProvincias = servicioApiDistancia.listadoDeProvincias(1, idPais);

    if(listadoProvincias.size() == 0) {
      System.out.print("Estoy retornando algo nulo");
    }else {
      System.out.print("Provincia: " + listadoProvincias.get(0).getNombre() + "\n");
      idProvincia = listadoProvincias.get(0).getId();
      System.out.print("ID Provincia: " + idProvincia + "\n");
    }

    List<Municipio> listadoMunicipios = servicioApiDistancia.listadoMunicipios(1, idProvincia);

    if(listadoMunicipios.size() == 0) {
      System.out.print("Estoy retornando algo nulo");
    }else {
      System.out.print("Municipio: " + listadoMunicipios.get(1).getNombre() + "\n");
      idMunicipio = listadoMunicipios.get(1).getId();
      System.out.print("ID Municipio: " + idMunicipio + "\n");
    }


    List<Localidad> listadoLocalidades = servicioApiDistancia.listadoLocalidades(1, idMunicipio);

    if(listadoLocalidades.size() == 0) {
      System.out.print("Estoy retornando algo nulo");
    }else {
      System.out.print("Localidad: " + listadoLocalidades.get(1).getNombre() + "\n");
      String idLocalidad = listadoLocalidades.get(1).getId();
      System.out.print("ID Localidad: " + idLocalidad + "\n");
    }
  }
}