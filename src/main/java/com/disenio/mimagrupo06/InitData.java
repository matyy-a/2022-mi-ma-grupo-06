package com.disenio.mimagrupo06;

import com.disenio.mimagrupo06.apiDistancia.DatosApi;
import com.disenio.mimagrupo06.apiDistancia.Pais;
import com.disenio.mimagrupo06.apiDistancia.ServicioApiDistancia;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.CalculadoraHC.CalculadoraHCActividad;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.CalculadoraHC.TipoActividad;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.espacio.*;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.medioDeTransporte.*;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.trayecto.Tramo;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.trayecto.Trayecto;
import com.disenio.mimagrupo06.domain.miembro.Miembro;
import com.disenio.mimagrupo06.domain.miembro.Persona;
import com.disenio.mimagrupo06.domain.miembro.TipoDocumento;
import com.disenio.mimagrupo06.domain.organizacion.Area;
import com.disenio.mimagrupo06.domain.organizacion.Clasificacion;
import com.disenio.mimagrupo06.domain.organizacion.Organizacion;
import com.disenio.mimagrupo06.domain.organizacion.TipoDeOrganizacion;
import com.disenio.mimagrupo06.domain.sector.PaisSector;
import com.disenio.mimagrupo06.domain.sector.ProvinciaSector;
import com.disenio.mimagrupo06.domain.sector.Sector;
import com.disenio.mimagrupo06.repositorios.*;
import com.disenio.mimagrupo06.seguridad.roles.AgenteSectorial;
import com.disenio.mimagrupo06.seguridad.roles.UsuarioComun;
import com.disenio.mimagrupo06.seguridad.roles.UsuarioOrganizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Component
public class InitData implements CommandLineRunner {

    @Autowired
    private RepoTA ta;

    @Autowired
    private DatosApi da;

    @Autowired
    private RepoUsuario ru;

    @Autowired
    private RepoPersona rp;

    @Autowired
    private RepoMiembro rm;

    @Autowired
    private RepoEspacio re;

    @Autowired
    private RepoArea ra;

    @Autowired
    private RepoTrayecto rt;
    
    @Autowired
    private RepoOrganizacion ro;

    @Autowired
    private RepoMedioTransporte rmt;

    @Autowired
    private RepoTramos rtr;

    @Autowired
    private RepoProvinciaSector rprs;

    @Autowired
    private RepoPaisSector rps;

    @Override
    public void run(String... args) throws Exception {

        if(ta.count() == 0) {

           // da.cargarDatos();

            PaisSector paisSector = new PaisSector(9L,"ARGENTINA");
            rps.save(paisSector);
            ProvinciaSector provinciaSector = new ProvinciaSector(168L,"BUENOS AIRES", paisSector);
            rprs.save(provinciaSector);

            AgenteSectorial agenteSectorial = new AgenteSectorial("Guido2002@gmail.com","contraCOntraKCRF1234", provinciaSector);
            ru.save(agenteSectorial);

            Hogar hogarGuido = new Hogar(1.0, 1.0, "BUENOS AIRES", "ADOLFO ALSINA", "CARHUE", "unaCalle", "Alturacalle", 1992, 3, "Hola", TipoDeHogar.CASA);
            UsuarioComun usuarioGuido = new UsuarioComun("Guido2000", "contraCOntraKCRF123");
            UsuarioOrganizacion usuarioGuido2 = new UsuarioOrganizacion("Guido2001@gmail.com", "contraCOntraKCRF123");
            
            Persona personaGuido = new Persona("Guido", "Serco", TipoDocumento.DNI, "4256565656", hogarGuido, usuarioGuido);
            Miembro miembroGuido = new Miembro(personaGuido);
            Miembro miembroGuido2 = new Miembro(personaGuido);
            ru.save(usuarioGuido);
            ru.save(usuarioGuido2);
            re.save(hogarGuido);
            rp.save(personaGuido);
            rm.save(miembroGuido2);
            rm.save(miembroGuido);

            Hogar hogarTaylor = new Hogar(1.0, 1.0, "BUENOS AIRES", "ADOLFO ALSINA", "CARHUE", "unaCalle", "Alturacalle", 1992, 3, "Hola", TipoDeHogar.CASA);
            re.save(hogarTaylor);
            UsuarioComun usuarioTaylor = new UsuarioComun("Taylor1234", "djf8ree245");
            Persona personaTaylor = new Persona("Taylor", "Swift", TipoDocumento.DNI, "367789999", hogarTaylor, usuarioTaylor);
            Miembro miembroTaylor = new Miembro(personaTaylor);
            ru.save(usuarioTaylor);
            rp.save(personaTaylor);
            rm.save(miembroTaylor);

            UsuarioComun usuarioJake = new UsuarioComun("Jake_The_Taylor_Hater", "Taylor_hater_4_life");
            Persona personaJake = new Persona("Jake", "Gyllenhaal", TipoDocumento.DNI, "4256565656", hogarGuido, usuarioJake);
            Miembro miembroJake = new Miembro(personaJake);
            ru.save(usuarioJake);
            rp.save(personaJake);
            rm.save(miembroJake);

            

            Parada espacioOrigen = new Parada(1.0, 1.0, "BUENOS AIRES", "ADOLFO ALSINA", "CARHUE", "maipu", "100", 1992);
            EspacioDeTrabajo espacioTrabajoArea = new EspacioDeTrabajo(1.0, 1.0, "BUENOS AIRES", "ADOLFO ALSINA", "CARHUE", "O'Higgins", "200", 1992,2, "A");
            re.save(espacioOrigen);
            re.save(espacioTrabajoArea);

            MedioDeTransporte medioDeTransporte1 = new ServicioContratado(TipoServicioContratado.TAXI);
            MedioDeTransporte medioDeTransporte2 = new VehiculoParticular(TipoVehiculo.AUTO,TipoCombustible.NAFTA);
            rmt.save(medioDeTransporte1);
            rmt.save(medioDeTransporte2);

            Tramo tramo1 = new Tramo(espacioOrigen, espacioTrabajoArea, medioDeTransporte1, Arrays.asList(miembroGuido));
            Tramo tramo2 = new Tramo(espacioOrigen, espacioTrabajoArea, medioDeTransporte2, Arrays.asList(miembroTaylor,miembroJake));
            //Tramo tramo3 = new Tramo(espacioOrigen, espacioTrabajoArea, medioDeTransporte2, Arrays.asList(miembroTaylor));
            rtr.save(tramo1);
            rtr.save(tramo2);
            //rtr.save(tramo3);

            Trayecto trayecto1 = new Trayecto(espacioOrigen,espacioTrabajoArea,Arrays.asList(tramo1), LocalDate.of(2021, 1, 1),5);
            Trayecto trayecto2 = new Trayecto(espacioOrigen,espacioTrabajoArea,Arrays.asList(tramo2), LocalDate.of(2021, 1, 1),3);
            //Trayecto trayecto3 = new Trayecto(espacioOrigen,espacioTrabajoArea,Arrays.asList(tramo3), LocalDate.of(2021, 1, 1),2);
            rt.save(trayecto1);
            rt.save(trayecto2);
            //rt.save(trayecto3);

            Area area = new Area("Area1", Arrays.asList(miembroGuido), espacioTrabajoArea);
            area.solicitudMiembro(miembroTaylor);
            Area area2 = new Area("Area2", Arrays.asList(miembroGuido2), espacioTrabajoArea);
            area.agregarVinculacion(trayecto1);
            ra.save(area);
            ra.save(area2);

            miembroTaylor.setAreaPendiente(area);
            rm.save(miembroTaylor);

            Area area4Ever21 = new Area("Area4Ever21", Arrays.asList(miembroJake), espacioTrabajoArea);
            area4Ever21.agregarVinculacion(trayecto2);
            //area4Ever21.agregarVinculacion(trayecto3);
            area4Ever21.aceptarVinculacion(trayecto2);
            //area4Ever21.aceptarVinculacion(trayecto3);
            ra.save(area4Ever21);

            miembroJake.setArea(area4Ever21);
            rm.save(miembroJake);

            Organizacion organizacion = new Organizacion("Nueva Seguro", TipoDeOrganizacion.EMPRESA, Arrays.asList(area4Ever21),
            Clasificacion.MINISTERIO);
            organizacion.setUsuarioOrganizacion(usuarioGuido2);
            organizacion.setSectores(Arrays.asList(area,area4Ever21));
            ro.save(organizacion);


            TipoActividad gasNatural = new TipoActividad("Gas Natural", "m3",10);
            TipoActividad dieselGasoil = new TipoActividad("DieselGasoil", "lt",7);
            TipoActividad kerosene = new TipoActividad("Kerosene", "lt",5);
            TipoActividad fuelOil= new TipoActividad("Fuel Oil", "lt",8);
            TipoActividad nafta = new TipoActividad("Nafta", "lt",9);
            TipoActividad carbon = new TipoActividad("Carbon", "kg",10);
            TipoActividad carbonDeLenia = new TipoActividad("Carbon de Lenia", "kg",5);
            TipoActividad lenia = new TipoActividad("Lenia", "kg",8);
            TipoActividad combustibleGasoil = new TipoActividad("Combustible Gasoil", "lt",7);
            TipoActividad combustibleGNC = new TipoActividad("Combustible GNC", "lt",8);
            TipoActividad combustibleNafta = new TipoActividad("Combustible Nafta", "lt",7);
            TipoActividad electricidad = new TipoActividad("Electricidad", "Kwh",10);
            TipoActividad distMediaRecorrida = new TipoActividad("Distancia Media Recorrida", "km",6);
            TipoActividad pesoTotalTransportado = new TipoActividad("Peso Total Transportado", "kg",4);
            TipoActividad materiaPrima = new TipoActividad("Materia Prima", " ",1);
            TipoActividad camionUtilitario = new TipoActividad("Medio de Transporte: Camion utilitario", " ",1);
            TipoActividad camionCarga = new TipoActividad("Medio de Transporte: Camion de carga", " ",1);


            System.out.println("No está nula, va a arrancar a guardar");
            List<TipoActividad> tiposActividad = new ArrayList<>();
            tiposActividad.addAll(Arrays.asList(gasNatural, dieselGasoil, kerosene, fuelOil, nafta, carbon, carbonDeLenia, lenia, combustibleGasoil, combustibleGNC, combustibleNafta,
                    electricidad, distMediaRecorrida, pesoTotalTransportado, materiaPrima, camionUtilitario, camionCarga));

            tiposActividad.forEach(da->ta.save(da));
        }

    }
}
