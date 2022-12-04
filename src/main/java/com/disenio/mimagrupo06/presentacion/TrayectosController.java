package com.disenio.mimagrupo06.presentacion;

import com.disenio.mimagrupo06.domain.huellaDeCarbono.espacio.*;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.medioDeTransporte.*;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.trayecto.Tramo;
import com.disenio.mimagrupo06.domain.huellaDeCarbono.trayecto.Trayecto;
import com.disenio.mimagrupo06.domain.miembro.Miembro;
import com.disenio.mimagrupo06.domain.miembro.Persona;
import com.disenio.mimagrupo06.presentacion.dto.TramoDTO;
import com.disenio.mimagrupo06.presentacion.dto.TrayectoNuevoDTO;
import com.disenio.mimagrupo06.presentacion.dto.TrayectoExistenteDTO;
import com.disenio.mimagrupo06.repositorios.*;
import com.disenio.mimagrupo06.seguridad.roles.Usuario;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class TrayectosController {
    @Autowired
    RepoArea repoArea;
    @Autowired
    RepoPersona repoPersona;
    @Autowired
    RepoTrayecto repoTrayecto;
    @Autowired
    RepoTramos repoTramos;
    @Autowired
    RepoEspacio repoEspacio;
    @Autowired
    RepoMiembro repoMiembro;
    @Autowired
    RepoMedioTransporte repoMedioTransporte;

    private final Handlebars handlebars = new Handlebars();
    @GetMapping("/trayectos")
    public ResponseEntity obtenerTrayectos(@RequestHeader("Authorization") String idSesion){
        Iterable<Trayecto> listaTrayectos = repoTrayecto.findAll();
        return ResponseEntity.status(201).body(listaTrayectos);
    }

    @GetMapping("/registrarTrayecto")
    public ResponseEntity<String> registrarTrayecto() throws IOException {
        //validar accion en capa modelo según roles o usuario asociados al idSesion
        Template template = handlebars.compile("/Template/eleccionCreacionTrayecto");

        Map<String, Object> model = new HashMap<>();
        //model.put("listamascotas", mascotas);

        String html = template.apply(model);

        return ResponseEntity.status(200).body(html);
    }

    @GetMapping("/registrarTrayectoNuevo")
    public ResponseEntity<String> registrarTrayectoNuevo() throws IOException {
        //validar accion en capa modelo según roles o usuario asociados al idSesion
        Template template = handlebars.compile("/Template/registrarTrayectoNuevo");

        Map<String, Object> model = new HashMap<>();
        //model.put("listamascotas", mascotas);

        String html = template.apply(model);

        return ResponseEntity.status(200).body(html);
    }

    @GetMapping("/registrarTrayectoExistente")
    public ResponseEntity<String> registrarTrayectoExistente() throws IOException {
        //validar accion en capa modelo según roles o usuario asociados al idSesion
        Template template = handlebars.compile("/Template/registrarTrayectoExistente");

        Map<String, Object> model = new HashMap<>();
        //model.put("listamascotas", mascotas);

        String html = template.apply(model);

        return ResponseEntity.status(200).body(html);
    }
    //recibo idsesion y area y devuelvo trayectos pertenecientes a esta area (tmbn devolvemos area o se lo puede guardar en local storage?)
    /*@GetMapping("/registrarTrayectoExistente")
    public ResponseEntity<List<Trayecto>> registrarTrayectoExistente() throws IOException {
        //validar accion en capa modelo según roles o usuario asociados al idSesion
        Template template = handlebars.compile("/Template/registrarTrayectoExistente");

        Map<String, Object> model = new HashMap<>();
        //model.put("listamascotas", mascotas);

        String html = template.apply(model);

        return ResponseEntity.status(200).body(html);
    }*/

    @PostMapping("/registrarTrayectoExistente")
    public void recibirTrayectoExistente(@RequestBody TrayectoExistenteDTO trayectoExistenteDTO) throws IOException {
        Miembro miembroSesion = this.encontrarMiembro(trayectoExistenteDTO.getIdSesion(), trayectoExistenteDTO.getArea());
        System.out.println("Estoy recibiendo un trayecto existente");
        Trayecto trayecto = repoTrayecto.findById(trayectoExistenteDTO.getIdTrayecto()).get();
        trayecto.getTramos().forEach(tramo -> tramo.agregarMiembro(miembroSesion));
        repoTrayecto.save(trayecto);
    }

    @PostMapping("/registrarTrayectoNuevo")
    public void recibirTrayectoNuevo(@RequestBody TrayectoNuevoDTO trayectoNuevoDTO) throws IOException {

        Miembro miembroSesion = this.encontrarMiembro(trayectoNuevoDTO.getIdSesion(), trayectoNuevoDTO.getNombreArea());
        /*if (miembroSesion == null) {
            return ResponseEntity.status(404).build();
        }*/

        //creamos referencias para datos de trayectoNuevo
        Espacio espacioPartida = null;
        Espacio espacioLlegada = null;
        List<Tramo> listaTramosTrayectoNuevo = new ArrayList<Tramo>();

        if(trayectoNuevoDTO.getEspacioLlegada().getId() == null){
            if(trayectoNuevoDTO.getEspacioLlegada().getClase().equals("hogar")){
                espacioLlegada = new Hogar(trayectoNuevoDTO.getEspacioLlegada().getLatitud(), trayectoNuevoDTO.getEspacioLlegada().getLongitud(), trayectoNuevoDTO.getEspacioLlegada().getProvincia(), trayectoNuevoDTO.getEspacioLlegada().getMunicipio(), trayectoNuevoDTO.getEspacioLlegada().getLocalidad(),
                    trayectoNuevoDTO.getEspacioLlegada().getDireccion(), trayectoNuevoDTO.getEspacioLlegada().getNumero(), trayectoNuevoDTO.getEspacioLlegada().getCodigoPostal(), trayectoNuevoDTO.getEspacioLlegada().getPisoDepartamento(), trayectoNuevoDTO.getEspacioLlegada().getDepartamento(),
                    trayectoNuevoDTO.getEspacioLlegada().getTipoDeHogar());
                repoEspacio.save(espacioLlegada);
            }else{
                if(trayectoNuevoDTO.getEspacioLlegada().getClase().equals("trabajo")){
                    espacioLlegada = new EspacioDeTrabajo(trayectoNuevoDTO.getEspacioLlegada().getLatitud(), trayectoNuevoDTO.getEspacioLlegada().getLongitud(), trayectoNuevoDTO.getEspacioLlegada().getProvincia(), trayectoNuevoDTO.getEspacioLlegada().getMunicipio(), trayectoNuevoDTO.getEspacioLlegada().getLocalidad(),
                        trayectoNuevoDTO.getEspacioLlegada().getDireccion(), trayectoNuevoDTO.getEspacioLlegada().getNumero(), trayectoNuevoDTO.getEspacioLlegada().getCodigoPostal(), trayectoNuevoDTO.getEspacioLlegada().getPisoDepartamento(), trayectoNuevoDTO.getEspacioLlegada().getUnidad());
                    repoEspacio.save(espacioLlegada);
                }else{
                    if(trayectoNuevoDTO.getEspacioLlegada().getClase().equals("parada")){
                        espacioLlegada = new Parada(trayectoNuevoDTO.getEspacioLlegada().getLatitud(), trayectoNuevoDTO.getEspacioLlegada().getLongitud(), trayectoNuevoDTO.getEspacioLlegada().getProvincia(), trayectoNuevoDTO.getEspacioLlegada().getMunicipio(), trayectoNuevoDTO.getEspacioLlegada().getLocalidad(),
                            trayectoNuevoDTO.getEspacioLlegada().getDireccion(), trayectoNuevoDTO.getEspacioLlegada().getNumero(), trayectoNuevoDTO.getEspacioLlegada().getCodigoPostal());
                        repoEspacio.save(espacioLlegada);
                    }
                }
            }
        }else{
            espacioLlegada = repoEspacio.findById(trayectoNuevoDTO.getEspacioLlegada().getId()).get();
        }

        if(trayectoNuevoDTO.getEspacioPartida().getId() == null){
            if(trayectoNuevoDTO.getEspacioPartida().getClase().equals("hogar")){
                espacioPartida = new Hogar(trayectoNuevoDTO.getEspacioPartida().getLatitud(), trayectoNuevoDTO.getEspacioPartida().getLongitud(), trayectoNuevoDTO.getEspacioPartida().getProvincia(), trayectoNuevoDTO.getEspacioPartida().getMunicipio(), trayectoNuevoDTO.getEspacioPartida().getLocalidad(),
                    trayectoNuevoDTO.getEspacioPartida().getDireccion(), trayectoNuevoDTO.getEspacioPartida().getNumero(), trayectoNuevoDTO.getEspacioPartida().getCodigoPostal(), trayectoNuevoDTO.getEspacioPartida().getPisoDepartamento(), trayectoNuevoDTO.getEspacioPartida().getDepartamento(),
                    trayectoNuevoDTO.getEspacioPartida().getTipoDeHogar());
                repoEspacio.save(espacioPartida);
            }else{
                if(trayectoNuevoDTO.getEspacioPartida().getClase().equals("trabajo")){
                    espacioPartida = new EspacioDeTrabajo(trayectoNuevoDTO.getEspacioPartida().getLatitud(), trayectoNuevoDTO.getEspacioPartida().getLongitud(), trayectoNuevoDTO.getEspacioPartida().getProvincia(), trayectoNuevoDTO.getEspacioPartida().getMunicipio(), trayectoNuevoDTO.getEspacioPartida().getLocalidad(),
                        trayectoNuevoDTO.getEspacioPartida().getDireccion(), trayectoNuevoDTO.getEspacioPartida().getNumero(), trayectoNuevoDTO.getEspacioPartida().getCodigoPostal(), trayectoNuevoDTO.getEspacioPartida().getPisoDepartamento(), trayectoNuevoDTO.getEspacioPartida().getUnidad());
                    repoEspacio.save(espacioPartida);
                }else{
                    if(trayectoNuevoDTO.getEspacioPartida().getClase().equals("parada")){
                        espacioPartida = new Parada(trayectoNuevoDTO.getEspacioPartida().getLatitud(), trayectoNuevoDTO.getEspacioPartida().getLongitud(), trayectoNuevoDTO.getEspacioPartida().getProvincia(), trayectoNuevoDTO.getEspacioPartida().getMunicipio(), trayectoNuevoDTO.getEspacioPartida().getLocalidad(),
                            trayectoNuevoDTO.getEspacioPartida().getDireccion(), trayectoNuevoDTO.getEspacioPartida().getNumero(), trayectoNuevoDTO.getEspacioPartida().getCodigoPostal());
                        repoEspacio.save(espacioPartida);
                    }
                }
            }
        }else{
            espacioPartida = repoEspacio.findById(trayectoNuevoDTO.getEspacioPartida().getId()).get();
        }


        trayectoNuevoDTO.getTramos().stream().forEach(tramoDTO -> {
            try {
                this.agregarTramos(tramoDTO, listaTramosTrayectoNuevo, miembroSesion);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //iniciamos datos base trayecto nuevo
        Trayecto trayectoNuevo = new Trayecto(espacioPartida, espacioLlegada, listaTramosTrayectoNuevo, trayectoNuevoDTO.getFechaInicio(), trayectoNuevoDTO.getDiasUtilizados());
        repoTrayecto.save(trayectoNuevo);

        miembroSesion.getArea().agregarVinculacion(trayectoNuevo);
        //repoMiembro.save(miembroSesion);
        repoArea.save(miembroSesion.getArea());

        //return ResponseEntity.status(201).body(trayectoNuevo);

    }

    private void agregarTramos(TramoDTO tramoDTO, List<Tramo> listaTramos, Miembro miembroSesion) throws IOException {
        if(tramoDTO.getId() == null) {/*
            Tramo tramo = new Tramo();
            if(tramoDTO.getLlegada().getClase().equals("hogar")){
                Hogar hogar = new Hogar(tramoDTO.getLlegada().getLatitud(),tramoDTO.getLlegada().getLongitud(),tramoDTO.getLlegada().getProvincia(),tramoDTO.getLlegada().getMunicipio(),tramoDTO.getLlegada().getLocalidad(),
                    tramoDTO.getLlegada().getDireccion(),tramoDTO.getLlegada().getNumero(),tramoDTO.getLlegada().getCodigoPostal(),tramoDTO.getLlegada().getPisoDepartamento(),tramoDTO.getLlegada().getDepartamento(),
                    tramoDTO.getLlegada().getTipoDeHogar());
                repoHogar.save(hogar);
                tramo.setLlegada(hogar);
            }else{
                if(tramoDTO.getLlegada().getClase().equals("trabajo")){
                    EspacioDeTrabajo espacioDeTrabajo = new EspacioDeTrabajo(tramoDTO.getLlegada().getLatitud(),tramoDTO.getLlegada().getLongitud(),tramoDTO.getLlegada().getProvincia(),tramoDTO.getLlegada().getMunicipio(),tramoDTO.getLlegada().getLocalidad(),
                        tramoDTO.getLlegada().getDireccion(),tramoDTO.getLlegada().getNumero(),tramoDTO.getLlegada().getCodigoPostal(),tramoDTO.getLlegada().getPisoDepartamento(),tramoDTO.getLlegada().getUnidad());
                    repoEspacioTrabajo.save(espacioDeTrabajo);
                    tramo.setLlegada(espacioDeTrabajo);
                }else{
                    if(tramoDTO.getLlegada().getClase().equals("parada")){
                        Parada parada = new Parada(tramoDTO.getLlegada().getLatitud(),tramoDTO.getLlegada().getLongitud(),tramoDTO.getLlegada().getProvincia(),tramoDTO.getLlegada().getMunicipio(),tramoDTO.getLlegada().getLocalidad(),
                            tramoDTO.getLlegada().getDireccion(),tramoDTO.getLlegada().getNumero(),tramoDTO.getLlegada().getCodigoPostal());
                        repoParada.save(parada);
                        tramo.setLlegada(parada);
                    }
                }
            }

            if(tramoDTO.getPartida().getClase().equals("hogar")){
                Hogar hogar = new Hogar(tramoDTO.getPartida().getLatitud(),tramoDTO.getPartida().getLongitud(),tramoDTO.getPartida().getProvincia(),tramoDTO.getPartida().getMunicipio(),tramoDTO.getPartida().getLocalidad(),
                    tramoDTO.getPartida().getDireccion(),tramoDTO.getPartida().getNumero(),tramoDTO.getPartida().getCodigoPostal(),tramoDTO.getPartida().getPisoDepartamento(),tramoDTO.getPartida().getDepartamento(),
                    tramoDTO.getPartida().getTipoDeHogar());
                repoHogar.save(hogar);
                tramo.setPartida(hogar);
            }else{
                if(tramoDTO.getPartida().getClase().equals("trabajo")){
                    EspacioDeTrabajo espacioDeTrabajo = new EspacioDeTrabajo(tramoDTO.getPartida().getLatitud(),tramoDTO.getPartida().getLongitud(),tramoDTO.getPartida().getProvincia(),tramoDTO.getPartida().getMunicipio(),tramoDTO.getPartida().getLocalidad(),
                        tramoDTO.getPartida().getDireccion(),tramoDTO.getPartida().getNumero(),tramoDTO.getPartida().getCodigoPostal(),tramoDTO.getPartida().getPisoDepartamento(),tramoDTO.getPartida().getUnidad());
                    repoEspacioTrabajo.save(espacioDeTrabajo);
                    tramo.setPartida(espacioDeTrabajo);
                }else{
                    if(tramoDTO.getPartida().getClase().equals("parada")){
                        Parada parada = new Parada(tramoDTO.getPartida().getLatitud(),tramoDTO.getPartida().getLongitud(),tramoDTO.getPartida().getProvincia(),tramoDTO.getPartida().getMunicipio(),tramoDTO.getPartida().getLocalidad(),
                            tramoDTO.getPartida().getDireccion(),tramoDTO.getPartida().getNumero(),tramoDTO.getPartida().getCodigoPostal());
                        repoParada.save(parada);
                        tramo.setPartida(parada);
                    }
                }
            }

            this.settearMedioDeTransporte(tramoDTO,tramo);
            List<Miembro> miembros =new ArrayList<Miembro>();
            miembros.add(miembroSesion);
            tramo.setMiembros(miembros);
            repoTramos.save(tramo);
            trayecto.setTramos(Arrays.asList(tramo));
            //trayecto.agregarTramo(tramo);
            repoMiembro.save(miembroSesion);
            repoTrayecto.save(trayecto);*/
        }else{
            Tramo tramo =repoTramos.findById(tramoDTO.getId()).get();
            listaTramos.add(tramo);
        }
    }

    private void settearMedioDeTransporte(TramoDTO tramoDTO, Tramo tramo) throws IOException {
        if(tramoDTO.getTransporte().getId() == null){
            if(tramoDTO.getTransporte().getClaseAInicializar().equals("servicioContratado")){
                ServicioContratado servicioContratado = new ServicioContratado(tramoDTO.getTransporte().getTipoServicioContratado());
                repoMedioTransporte.save(servicioContratado);
                tramo.setTransporte(servicioContratado);
            }else{
                if(tramoDTO.getTransporte().getClaseAInicializar().equals("transporteNoMotorizado")){
                    TransporteNoMotorizado transporteNoMotorizado = new TransporteNoMotorizado(tramoDTO.getTransporte().getTipoNoMotorizado());
                    repoMedioTransporte.save(transporteNoMotorizado);
                    tramo.setTransporte(transporteNoMotorizado);
                }else{
                    if(tramoDTO.getTransporte().getClaseAInicializar().equals("transportePublico")){
                        TransportePublico transportePublico = new TransportePublico(tramoDTO.getTransporte().getTipoTransporte(),tramoDTO.getTransporte().getNombre());
                        repoMedioTransporte.save(transportePublico);
                        tramo.setTransporte(transportePublico);
                    }else{
                        if(tramoDTO.getTransporte().getClaseAInicializar().equals("vehiculoParticular")){
                            VehiculoParticular vehiculoParticular = new VehiculoParticular(tramoDTO.getTransporte().getTipoVehiculoParticular(),tramoDTO.getTransporte().getTipoCombustible());
                            repoMedioTransporte.save(vehiculoParticular);
                            tramo.setTransporte(vehiculoParticular);
                        }
                    }
                }
            }
        }else {
            MedioDeTransporte medio = repoMedioTransporte.findById(tramoDTO.getTransporte().getId()).get();
            tramo.setTransporte(medio);
        }
    }

    public Miembro encontrarMiembro(String idSesion, String nombreArea) {

        Map<String, Object> atributosSesion = SesionManager.get().obtenerAtributos(idSesion);
        Usuario usuarioSesion = (Usuario) atributosSesion.get("usuario");
        Persona personaSesion = repoPersona.findByUsuario(usuarioSesion);
        List<Miembro> miembrosDeSesion = repoMiembro.findAllByPersona(personaSesion);

        List<Miembro> miembrosDeSesionConArea = miembrosDeSesion.stream()
            .filter(miembro -> mismaArea(miembro,nombreArea))
            .collect(Collectors.toList());

        return  miembrosDeSesionConArea.get(0);
    }

    private boolean mismaArea(Miembro miembro, String nombreArea){
        return miembro.getArea().getNombre().equals(nombreArea);
    }
}
