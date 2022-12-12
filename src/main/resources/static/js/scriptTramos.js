/*
* Acá esta toda la logica referida a modificar la pantalla de tramos
* */

const opcionesTramo = document.getElementById("tipoTramo")

const partidaTramo = document.getElementById("partidaTramo")
const llegadaTramo = document.getElementById("llegadaTramo")
const tramoNuevos = document.getElementById("tramoNuevo")
const tramoExistente = document.getElementById("tramoExistente")

const opcionesTramoPartida = document.getElementById("tipoPartidaTramo")
const opcionesTramoLlegada = document.getElementById("tipoLlegadaTramo")

const espacioPartidaTramoNuevo = document.getElementById("espacioNuevoPartidaTramo")
const espacioPartidaTramoExistentes = document.getElementById("espacioExistentesPartidaTramo")

const espacioLlegadaTramoNuevo = document.getElementById("espacioNuevoLlegadaTramo")
const espacioLlegadaTramoExistentes = document.getElementById("espacioExistentesLlegadaTramo")

const tramosCargados = document.getElementById("tramosCargados");

const botonFinalizarCarga = document.getElementById("tramosCompletos")
const botonAgregarNuevoTramo = document.getElementById("agregarMasTramos");

const espacioSeleccionadoPartidaTramo = document.getElementById("tipoEspacioPartidaTramo")
const espacioSeleccionadoLlegadaTramo = document.getElementById("tipoEspacioLlegadaTramo")

// medios de transporte

const medioDeTransporte = document.getElementById("medioTransporte");

const servicioContratado = document.getElementById("divServicioContratado")
const noMotorizado = document.getElementById("divNoMotorizado")
const transportePublico = document.getElementById("divTransportePublico")
const vehiculoParticular = document.getElementById("divVehiculoParticular")

let bodyTramos = []
let bodyLlegada = "llegada"
let bodyPartida = "partida"
let bodyTransporte = "transporte"


opcionesTramo.addEventListener('change', e => {
    e.preventDefault();
    mostrarSegunRespuesta(opcionesTramo.value);
})

medioDeTransporte.addEventListener('change', e => {
    e.preventDefault();
    mostrarMedioTransporteSegunRespuesta(medioDeTransporte.value)
})

opcionesTramoPartida.addEventListener('change', e => {
    e.preventDefault();
    mostrarSegunRespuestaEspacioPartidaTramo(opcionesTramoPartida.value);
})

opcionesTramoLlegada.addEventListener('change', e => {
    e.preventDefault()
    mostrarSegunRespuestaEspacioLlegadaTramo(opcionesTramoLlegada.value)
})

botonAgregarNuevoTramo.addEventListener('click', e => {
    e.preventDefault();
    console.log("aprete el boton para agregar un tramo nuevo")
    cargarNuevoTramo();
})

botonFinalizarCarga.addEventListener('click', e => {
    e.preventDefault();
    console.log("aprete el boton para finalizar la carga de tramos")
    const callback = () => cambiarUrlConIdSesion("home");
    mandarDatosAlBack(callback);

})


espacioSeleccionadoPartidaTramo.addEventListener('change', e => {
    e.preventDefault();

    console.log("espacio seleccionado partida tramo")
    mostrarOpcionesSegunClaseTramoPartida(espacioSeleccionadoPartidaTramo.value);
})

espacioSeleccionadoLlegadaTramo.addEventListener('change', e => {
    e.preventDefault();
    console.log("espacio seleccionado llegada tramo")
    mostrarOpcionesSegunClaseTramoLlegada(espacioSeleccionadoLlegadaTramo.value);
})


function mostrarMedioTransporteSegunRespuesta(medioTransporteSeleccionado) {
    console.log(medioTransporteSeleccionado)

    if (medioTransporteSeleccionado === "servicioContratado") {
        servicioContratado.classList.remove("hidden");
        noMotorizado.classList.add("hidden");
        transportePublico.classList.add("hidden");
        vehiculoParticular.classList.add("hidden");
    } else if (medioTransporteSeleccionado === "transporteNoMotorizado") {
        noMotorizado.classList.remove("hidden")
        transportePublico.classList.add("hidden");
        vehiculoParticular.classList.add("hidden");
        servicioContratado.classList.add("hidden")

    } else if (medioTransporteSeleccionado === "transportePublico") {
        transportePublico.classList.remove("hidden")
        vehiculoParticular.classList.add("hidden");
        noMotorizado.classList.add("hidden");
        servicioContratado.classList.add("hidden")
    } else {
        //vechiculo particular
        vehiculoParticular.classList.remove("hidden")
        noMotorizado.classList.add("hidden");
        servicioContratado.classList.add("hidden")
        transportePublico.classList.add("hidden");
    }

}

function mostrarSegunRespuesta(opcion) {

    console.log(opcion)
    if (opcion === "nuevo") {
        partidaTramo.classList.remove("hidden")
        llegadaTramo.classList.remove("hidden")
        tramoNuevos.classList.remove("hidden");
        tramoExistente.classList.add("hidden");
        console.log("nuevito")
    } else {
        console.log("existente")
        tramoExistente.classList.remove("hidden");
        tramoNuevos.classList.add("hidden");

    }
}

function mostrarSegunRespuestaEspacioPartidaTramo(valor) {

    console.log("estoy en mostrar segun rta espacio llegada:" + valor)
    if (valor === "nuevo") {

        espacioPartidaTramoNuevo.classList.remove("hidden");
        espacioPartidaTramoExistentes.classList.add("hidden");
        //oculto el existente y muestro el nuevo


    } else {
        espacioPartidaTramoNuevo.classList.add("hidden");
        espacioPartidaTramoExistentes.classList.remove("hidden");
        //oculto el nuevo y muestro el existete
    }
}


function mostrarSegunRespuestaEspacioLlegadaTramo(valor) {

    console.log("estoy en mostrar segun rta espacio llegada:" + valor)
    if (valor === "nuevo") {

        espacioLlegadaTramoNuevo.classList.remove("hidden");
        espacioLlegadaTramoExistentes.classList.add("hidden");

        //oculto el existente y muestro el nuevo


    } else {
        espacioLlegadaTramoNuevo.classList.add("hidden");
        espacioLlegadaTramoExistentes.classList.remove("hidden");
        //oculto el nuevo y muestro el existete
    }
}

function cargarNuevoTramo() {
    //Esta funcion se va a encargar de:
    //armar los bodies
    //mostar los trayectos ya cargados

    bodyPartida = definirBodyPartidaTramo(espacioPartidaTramoNuevo.value)
    bodyLlegada = definirBodyLlegadaTramo(espacioLlegadaTramoNuevo.value)
    bodyTransporte = definirBodyTransporte(medioDeTransporte.value)


    if(document.getElementById("tipoTramo").value === "nuevo"){
        bodyParcial = {

                id: null,
                partida: bodyPartida,
                llegada: bodyLlegada,
                transporte: bodyTransporte

    }
    }
    else {
    bodyParcial = {
            id: parseInt(document.getElementById("tramosExistentes").value),
            partida: null,
            llegada: null,
            transporte: null
        }
    }

    console.log(bodyParcial)

    bodyTramos.push(bodyParcial)
    console.log(bodyTramos)

    volverAEstadoDefault()

}

function actualizarListaTramos() {

    let ultimoTramoAgregado = bodyTramos.at(-1)
    console.log(ultimoTramoAgregado)
    let espacioPartida = ultimoTramoAgregado.partida
    let espacioLlegada = ultimoTramoAgregado.llegada
    let medioTransporte = ultimoTramoAgregado.transporte

    let htmlNuevo = " <p>" + "Vas a ir desde" + espacioPartida.direccion + " " + espacioPartida.numero +
        " hasta "  +  espacioLlegada.direccion
        + espacioLlegada.numero + "en" + medioTransporte.tipoTransporte + "</p> <br>" ;
    tramosCargados.innerHTML = tramosCargados.innerHTML + htmlNuevo;

    console.log("--------------")
    console.log(ultimoTramoAgregado)
    console.log(espacioPartida.direccion)
    console.log(espacioLlegada.direccion)
    console.log(medioTransporte.tipoTransporte)
    console.log("--------------")
}

function volverAEstadoDefault() {

    //1. agregar el divisor para que se vea un tramo agregado

    //actualizarListaTramos()
    //2.  resetar los valores en las variables guardadas
    document.getElementById("tramos")
    opcionesTramo.classList.remove("hidden");
    ocultarTodo();

}

function ocultarTodo() {

    partidaTramo.classList.add("hidden")
    llegadaTramo.classList.add("hidden")


}

function definirBodyTransporte(medioTransporteSeleccionado) {

    let claseAInicializar = "";

    if (medioTransporteSeleccionado === "servicioContratado") {
        claseAInicializar = "servicioContratado"
        const valorServicioContratado = document.getElementById("servicioContratado").value
        bodyTransporte = {
            claseAInicializar: claseAInicializar,
            tipoServicioContratado: valorServicioContratado
        }

    } else if (medioTransporteSeleccionado === "transporteNoMotorizado") {
        claseAInicializar = "transporteNoMotorizado"
        const valorNoMotorizado = document.getElementById("noMotorizado").value
        bodyTransporte = {
            claseAInicializar: claseAInicializar,
            tipoNoMotorizado: valorNoMotorizado
        }

    } else if (medioTransporteSeleccionado === "transportePublico") {
        claseAInicializar = "transportePublico"
        const tipoTransportePublico = document.getElementById("transportePublico").value
        const valorLinea = document.getElementById("nombreTransporte").value
        bodyTransporte = {
            claseAInicializar: claseAInicializar,
            tipoTransporte: tipoTransportePublico,
            nombre: valorLinea
        }
    } else {
        claseAInicializar = "vehiculoParticular"
        const valorVehiculoParticular = document.getElementById("vehiculoParticular").value
        const valortipoCombustible = document.getElementById("tipoCombustible").value
        bodyTransporte = {
            claseAInicializar: claseAInicializar,
            tipoVehiculoParticular: valorVehiculoParticular,
            tipoCombustible: valortipoCombustible
        }

    }
    return bodyTransporte
}

const espacioTrabajoTramoPartida = document.getElementById("claseEspacioTrabajoPartidaTramo")
const espacioHogarTramoPartida = document.getElementById("claseHogarPartidaTramo")


function mostrarOpcionesSegunClaseTramoPartida(valor) {
    if (valor === "trabajo") {
        console.log("trabajooooooooo")
        espacioTrabajoTramoPartida.classList.remove("hidden");
        espacioHogarTramoPartida.classList.add("hidden");
    } else {
        if (valor === "hogar") {
            console.log("hogaaaaaaaaaaaaaaaaaar")
            espacioTrabajoTramoPartida.classList.add("hidden");
            espacioHogarTramoPartida.classList.remove("hidden");
        } else {
            if (valor === "parada") {
                espacioTrabajoTramoPartida.classList.add("hidden");
                espacioHogarTramoPartida.classList.add("hidden");
            }
        }
    }
}

const espacioTrabajoTramoLlegada = document.getElementById("claseEspacioTrabajoLlegadaTramo")
const espacioHogarTramoLlegada = document.getElementById("claseHogarLlegadaTramo")

function mostrarOpcionesSegunClaseTramoLlegada(valor) {
    if (valor === "trabajo") {
        console.log("trabajooooooooo")
        espacioTrabajoTramoLlegada.classList.remove("hidden");
        espacioHogarTramoLlegada.classList.add("hidden");
    } else {
        if (valor === "hogar") {
            console.log("hogaaaaaaaaaaaaaaaaaar")
            espacioTrabajoTramoLlegada.classList.add("hidden");
            espacioHogarTramoLlegada.classList.remove("hidden");
        } else {
            if (valor === "parada") {
                espacioTrabajoTramoLlegada.classList.add("hidden");
                espacioHogarTramoLlegada.classList.add("hidden");
            }
        }
    }
}


function definirBodyPartidaTramo(espacioSeleccionado) {
    let claseAInicializar;

    const latitudTramoPartida = document.getElementById("inputLatitudTramoPartida").value
    const longitudTramoPartida = document.getElementById("inputLongitudTramoPartida").value
    const provinciaTramoPartida = document.getElementById("inputProvinciaTramoPartida").value
    const localidadTramoPartida = document.getElementById("inputMunicipioTramoPartida").value
    const direccionTramoPartida = document.getElementById("inputDireccionTramoPartida").value
    const numeroPartida = document.getElementById("inputNumeroTramoPartida").value
    const codigoPostalPartida = document.getElementById("inputcodigoPostalTramoPartida").value
    let bodyEspacioPartida

    if (espacioSeleccionado === "hogar") {
        claseAInicializar = "hogar"
        const valorHogar = document.getElementById("tipoEspacioPartidaTramo").value
        const pisoHogar = document.getElementById("pisoHogarPartidaTramo").value
        const departamentoHogar = document.getElementById("departamentoPartidaTramo").value
        const valorTipoHogar = document.getElementById("inputHogarPartidaTramo").value
          bodyEspacioPartida = {
            //id: idEspacioPartidTramo,
            clase: claseAInicializar,
            tipoEspacioLlegada: valorHogar,
            piso: pisoHogar,
            departamento: departamentoHogar,
            pisoTrabajo: null,
            unidad: null,
            tipoHogar: valorTipoHogar,
            latitud: latitudTramoPartida,
            longitud: longitudTramoPartida,
            localidad: localidadTramoPartida,
            provincia: provinciaTramoPartida,
            direccion: direccionTramoPartida,
            numero: numeroPartida,
            codigoPostal: codigoPostalPartida

        }
    } else if (espacioSeleccionado === "trabajo") {
        claseAInicializar = "trabajo"

        const valorTrabajo = document.getElementById("tipoEspacioPartidaTramo").value
        const pisoTrabajo = document.getElementById("pisoTrabajoPartidaTramo").value
        const unidadTrabajo = document.getElementById("unidadPartidaTramo").value
        bodyEspacioPartida = {
            //id: idEspacioPartidaTramo,
            clase: claseAInicializar,
            tipoEspacioLlegada: valorTrabajo,
            pisoDepartamento: null,
            departamento: null,
            piso: pisoTrabajo,
            unidad: unidadTrabajo,
            latitud: latitudTramoPartida,
            longitud: longitudTramoPartida,
            localidad: localidadTramoPartida,
            provincia: provinciaTramoPartida,
            direccion: direccionTramoPartida,
            numero: numeroPartida,
            codigoPostal: codigoPostalPartida

        }
    } else {
        claseAInicializar = "parada"
        const valorParada = document.getElementById("tipoEspacioPartidaTramo").value

        bodyEspacioPartida = {
           // id: idEspacioPartidaTramo,
            clase: claseAInicializar,
            bodyEspacioPartida: valorParada,
            pisoDepartamento: null,
            departamento: null,
            pisoTrabajo: null,
            unidad: null,
            latitud: latitudTramoPartida,
            longitud: longitudTramoPartida,
            localidad: localidadTramoPartida,
            provincia: provinciaTramoPartida,
            direccion: direccionTramoPartida,
            numero: numeroPartida,
            codigoPostal: codigoPostalPartida
        }
    }

    return bodyEspacioPartida

}

function definirBodyLlegadaTramo(espacioSeleccionado) {
    let claseAInicializar;

    const latitudTramoLlegada = document.getElementById("inputLatitudLlegadaTramo").value
    const longitudTramoLlegada = document.getElementById("inputLongitudLlegadaTramo").value
    const provinciaTramoLlegada = document.getElementById("inputProvinciaLlegadaTramo").value
    const localidadTramoLlegada = document.getElementById("inputMunicipioLlegadaTramo").value
    const direccionTramoLlegada = document.getElementById("inputDireccionLlegadaTramo").value
    const numeroLlegada = document.getElementById("inputNumeroLlegadaTramo").value
    const codigoPostalLlegada = document.getElementById("inputCodigoPostalLlegadaTramo").value

    let bodyEspacioLlegada;
    if (espacioSeleccionado === "hogar") {
        if(opcionLlegada.value === "existente") {
            idEspacioLlegada = document.getElementById("puntoLlegadaExistente").value
        }
        claseAInicializar = "hogar"
        const valorHogar = document.getElementById("tipoEspacioLlegadaTramo").value
        const pisoHogar = document.getElementById("pisoHogarLlegadaTramo").value
        const departamentoHogar = document.getElementById("departamentoLlegadaTramo").value
        const valorTipoHogar = document.getElementById("inputHogarLlegadaTramo").value
        bodyEspacioLlegada = {
            clase: claseAInicializar,
            tipoEspacioLlegada: valorHogar,
            pisoDepartamento: pisoHogar,
            departamento: departamentoHogar,
            pisoTrabajo: null,
            unidad: null,
            tipoHogar: valorTipoHogar,
            latitud: latitudTramoLlegada,
            longitud: longitudTramoLlegada,
            localidad: localidadTramoLlegada,
            provincia: provinciaTramoLlegada,
            direccion: direccionTramoLlegada,
            numero: numeroLlegada,
            codigoPostal: codigoPostalLlegada
        }
    } else if (espacioSeleccionado === "trabajo") {
        claseAInicializar = "trabajo"
        const valorTrabajo = document.getElementById("tipoEspacioLlegadaTramo").value
        const pisoTrabajo = document.getElementById("pisoTrabajoLlegadaTramo").value
        const unidadTrabajo = document.getElementById("unidadLlegadaTramo").value
        bodyEspacioLlegada = {
            clase: claseAInicializar,
            tipoEspacioLlegada: valorTrabajo,
            pisoDepartamento: null,
            departamento: null,
            pisoTrabajo: pisoTrabajo,
            unidad: unidadTrabajo,
            latitud: latitudTramoLlegada,
            longitud: longitudTramoLlegada,
            localidad: localidadTramoLlegada,
            provincia: provinciaTramoLlegada,
            direccion: direccionTramoLlegada,
            numero: numeroLlegada,
            codigoPostal: codigoPostalLlegada
        }
    } else {
        claseAInicializar = "parada"
        const valorParada = document.getElementById("tipoEspacioLlegadaTramo").value
        bodyEspacioLlegada = {
            clase: claseAInicializar,
            tipoEspacioLlegada: valorParada,
            pisoDepartamento: null,
            departamento: null,
            pisoTrabajo: null,
            unidad: null,
            latitud: latitudTramoLlegada,
            longitud: longitudTramoLlegada,
            localidad: localidadTramoLlegada,
            provincia: provinciaTramoLlegada,
            direccion: direccionTramoLlegada,
            numero: numeroLlegada,
            codigoPostal: codigoPostalLlegada
        }
    }

    return bodyEspacioLlegada
}

function mandarDatosAlBack(callbackAEjecutarThen) {
    //esta funcion va a generar el body y mandarlo al back

    const bodyTrayectos = generarBodyTrayectos()
    //fetch al back

        // Se ejecuta la request
    fetch('https://dds-2022-mi-ma-grupo-06.herokuapp.com/registrarTrayectoNuevo', {
        headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("idSesion")
        },
            method: "POST",
            body: JSON.stringify(bodyTrayectos)
        }).then(callbackAEjecutarThen)

}

function generarBodyTrayectos() {

    let bodyLlegada = definirBodyLlegadaTrayecto(espacioSeleccionadoLlegada.value)
    console.log(bodyLlegada)
    let bodyPartida = definirBodyPartidaTrayecto(espacioSeleccionadoPartida.value)
    console.log(bodyPartida)
    let dias = document.getElementById("inputdiasUtilizados").value
    let fechaInicio = document.getElementById("startInicio").value
    let fechaFin = document.getElementById("startFin").value

    const bodyARetornar = {
        nombreArea: obtenerArea(),
        espacioLlegada: bodyLlegada,
        espacioPartida: bodyPartida,
        tramos: bodyTramos,
        diasUtilizados: dias,
        fechaInicio: fechaInicio,
        fechaFin: fechaFin
    }

    console.log(bodyARetornar)
    return bodyARetornar

}

function definirBodyLlegadaTrayecto(espacioSeleccionado) {

    let claseAInicializar = ""
    let bodyInicializado = {}
    const latitudTrayectoLlegada = document.getElementById("inputLatitud2").value
    const longitudTrayectoLlegada = document.getElementById("inputLongitud2").value
    const municipioTrayectoLlegada = document.getElementById("inputMunicipio2").value
    const provinciaTrayectoLlegada = document.getElementById("inputProvincia2").value
    const localidadTrayectoLlegada = document.getElementById("inputLocalidad2").value
    const direccionTrayectoLlegada = document.getElementById("inputDireccion2").value
    const numeroLlegadaTrayecto = document.getElementById("inputNumero2").value
    const codigoPostalLlegadaTrayecto = document.getElementById("inputcodigoPostal2").value


    if (espacioSeleccionado === "hogar") {
        if(opcionLlegada.value === "existente") {
            idEspacioLlegada = document.getElementById("puntoLlegadaExistente").value
        }
        claseAInicializar = "hogar"
        const pisoHogar = document.getElementById("pisoHogarLlegada").value
        const departamentoHogar = document.getElementById("departamentoLlegada").value
        const valorTipoHogar = document.getElementById("inputHogarLlegada").value
        bodyInicializado = {
            id: idEspacioLlegada,
            clase: claseAInicializar,
            pisoDepartamento: pisoHogar,
            departamento: departamentoHogar,
            pisoTrabajo: null,
            unidad: null,
            tipoHogar: valorTipoHogar,
            latitud: latitudTrayectoLlegada,
            longitud: longitudTrayectoLlegada,
            municipio: municipioTrayectoLlegada,
            localidad: localidadTrayectoLlegada,
            provincia: provinciaTrayectoLlegada,
            direccion: direccionTrayectoLlegada,
            numero: numeroLlegadaTrayecto,
            codigoPostal: codigoPostalLlegadaTrayecto

        }
    } else if (espacioSeleccionado === "trabajo") {
        if(opcionLlegada.value === "existente") {
            idEspacioLlegada = document.getElementById("puntoLlegadaExistente").value
        }
        claseAInicializar = "trabajo"
        const pisoTrabajo = document.getElementById("pisoTrabajoLlegada").value
        const unidadTrabajo = document.getElementById("unidadLlegada").value
        bodyInicializado = {
            id: idEspacioLlegada,
            clase: claseAInicializar,
            pisoDepartamento: null,
            departamento: null,
            piso: pisoTrabajo,
            unidad: unidadTrabajo,
            latitud: latitudTrayectoLlegada,
            longitud: longitudTrayectoLlegada,
            municipio: municipioTrayectoLlegada,
            localidad: localidadTrayectoLlegada,
            provincia: provinciaTrayectoLlegada,
            direccion: direccionTrayectoLlegada,
            numero: numeroLlegadaTrayecto,
            codigoPostal: codigoPostalLlegadaTrayecto

        }

    } else {
        if(opcionLlegada.value === "existente") {
            idEspacioLlegada = document.getElementById("puntoLlegadaExistente").value
        }
        claseAInicializar = "parada"
        bodyInicializado = {
            id: idEspacioLlegada,
            clase: claseAInicializar,
            pisoDepartamento: null,
            departamento: null,
            pisoTrabajo: null,
            unidad: null,
            latitud: latitudTrayectoLlegada,
            longitud: longitudTrayectoLlegada,
            localidad: localidadTrayectoLlegada,
            provincia: provinciaTrayectoLlegada,
            direccion: direccionTrayectoLlegada,
            numero: numeroLlegadaTrayecto,
            codigoPostal: codigoPostalLlegadaTrayecto
        }
    }
    return bodyInicializado

}

function definirBodyPartidaTrayecto(espacioSeleccionado) {

    let claseAInicializar
    let bodyInicializado

    const latitudTrayectoPartida = document.getElementById("inputLatitud1").value
    const longitudTrayectoPartida = document.getElementById("inputLongitud1").value
    const provinciaTrayectoPartida = document.getElementById("inputProvincia1").value
    const municipioTrayectoPartida = document.getElementById("inputMunicipio1").value
    const localidadTrayectoPartida = document.getElementById("inputLocalidad1").value
    const direccionTrayectoPartida = document.getElementById("inputDireccion1").value
    const numeroPartida = document.getElementById("inputNumero1").value
    const codigoPostalPartida = document.getElementById("inputcodigoPostal1").value

    if (espacioSeleccionado === "hogar") {

        if(opcionPartida.value === "existente") {
            idEspacioPartida = document.getElementById("puntoPartidaExistente").value
        }
        claseAInicializar = "hogar"
        const pisoHogar = document.getElementById("pisoHogarPartida").value
        const departamentoHogar = document.getElementById("departamentoPartida").value
        const valorTipoHogar = document.getElementById("inputHogarPartida").value
        bodyInicializado = {
            id: idEspacioPartida,
            clase: claseAInicializar,
            pisoTrabajo: null,
            unidad: null,
            piso: pisoHogar,
            departamento: departamentoHogar,
            tipoHogar: valorTipoHogar,
            latitud: latitudTrayectoPartida,
            longitud: longitudTrayectoPartida,
            localidad: localidadTrayectoPartida,
            provincia: provinciaTrayectoPartida,
            municipio: municipioTrayectoPartida,
            direccion: direccionTrayectoPartida,
            numero: numeroPartida,
            codigoPostal: codigoPostalPartida

        }
    } else if (espacioSeleccionado === "trabajo") {
        if(opcionPartida.value === "existente") {
            idEspacioPartida = document.getElementById("puntoPartidaExistente").value
        }

        claseAInicializar = "trabajo"
        const pisoTrabajo = document.getElementById("pisoTrabajoPartida").value
        const unidadTrabajo = document.getElementById("unidadPartida").value
        bodyInicializado = {
            id: idEspacioPartida,
            clase: claseAInicializar,
            pisoDepartamento: null,
            departamento: null,
            piso: pisoTrabajo,
            unidad: unidadTrabajo,
            latitud: latitudTrayectoPartida,
            longitud: longitudTrayectoPartida,
            localidad: localidadTrayectoPartida,
            provincia: provinciaTrayectoPartida,
            direccion: direccionTrayectoPartida,
            numero: numeroPartida,
            codigoPostal: numeroPartida
        }
    } else {
        if(opcionPartida.value === "existente") {
            idEspacioPartida = document.getElementById("puntoPartidaExistente").value
        }
        claseAInicializar = "parada"
        bodyInicializado = {
            id: idEspacioPartida,
            clase: claseAInicializar,
            pisoDepartamento: null,
            departamento: null,
            pisoTrabajo: null,
            unidad: null,
            latitud: latitudTrayectoPartida,
            longitud: longitudTrayectoPartida,
            localidad: localidadTrayectoPartida,
            provincia: provinciaTrayectoPartida,
            direccion: direccionTrayectoPartida,
            numero: numeroPartida,
            codigoPostal: codigoPostalPartida
        }
    }

    return bodyInicializado

}