package domain.organizacion;

import domain.huellaDeCarbono.espacio.EspacioDeTrabajo;
import domain.huellaDeCarbono.trayecto.ManejadorTrayectos;
import domain.huellaDeCarbono.trayecto.Tramo;
import domain.huellaDeCarbono.trayecto.Trayecto;
import domain.miembro.AgenteSectorial;
import domain.miembro.Miembro;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Area {
  private String nombre;
  private Collection<Miembro> miembros;
  private EspacioDeTrabajo espacioDeTrabajo;
  private Collection<Trayecto> trayectosRegistados;
  private Collection<Trayecto> trayectosPendientes;

  public Area(String nombre, Collection<Miembro> miembros, EspacioDeTrabajo espacioDeTrabajo) {
    this.nombre = nombre;
    this.miembros = miembros;
    this.espacioDeTrabajo = espacioDeTrabajo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Collection<Miembro> getMiembros() {
    return miembros;
  }

  public void setMiembros(Collection<Miembro> miembros) {
    this.miembros = miembros;
}

  public EspacioDeTrabajo getEspacioDeTrabajo() {
    return espacioDeTrabajo;
  }

  public void setEspacioDeTrabajo(EspacioDeTrabajo espacioDeTrabajo) {
    this.espacioDeTrabajo = espacioDeTrabajo;
  }

  public void registrarMiembro(Miembro miembro){
    miembros.add(miembro);
  }

  public Collection<Trayecto> getTrayectosRegistados() {
    return trayectosRegistados;
  }

  public void setTrayectosRegistados(Collection<Trayecto> trayectos) {
    this.trayectosRegistados = trayectos;
  }

  public Collection<Trayecto> getTrayectosPendientes() {
    return trayectosPendientes;
  }

  public void setTrayectosPendientes(Collection<Trayecto> trayectosPendientes) {
    this.trayectosPendientes = trayectosPendientes;
  }

  public void agregarVinculacion(Trayecto trayecto) {
    trayectosPendientes.add(trayecto);
  }

  public void aceptarVinculacion(Trayecto trayecto) {
    trayectosPendientes.remove(trayecto);
    trayectosRegistados.add(trayecto);
  }

  public Double calcularHuellaCarbonoTotalAreaAnual(int anio){
    Double hcAnual = ManejadorTrayectos.getInstance().calcularHCAnual(trayectosRegistados, anio);
    return hcAnual;
  }

  public Double calcularHuellaCarbonoTotalAreaMensual(int anio, int mes) {
    Double hcMensual = ManejadorTrayectos.getInstance().calcularHCMensual(trayectosRegistados, anio, mes);

    return hcMensual;
  }

  //TODO: preguntar si el promedio de HC por miembro es anual o mensual
  public Double calcularHuellaCarbonoPromedioMiembroMensual(int anio, int mes){
    return this.calcularHuellaCarbonoTotalAreaMensual(anio, mes) / miembros.size();
  }

  public Double calcularHuellaCarbonoPromedioMiembroAnual(int anio){
    return this.calcularHuellaCarbonoTotalAreaAnual(anio) / miembros.size();
  }

  public Collection<Trayecto> getTrayectosDelMiembro(Miembro miembro) {
    Collection<Trayecto> trayectosMiembro = trayectosRegistados.stream().filter(trayecto -> trayecto.perteneceMiembro(miembro)).collect(Collectors.toList());
    return trayectosMiembro;
  }

  public boolean perteneceAArea(AgenteSectorial agenteSectorial) {
    return espacioDeTrabajo.getProvincia().equals(agenteSectorial.getSectorTerritorial())
        || espacioDeTrabajo.getMunicipio().equals(agenteSectorial.getSectorTerritorial());
  }
}
