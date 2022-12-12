
document.getElementById("navRegistrarTrayecto").addEventListener('click', e=> {
    e.preventDefault()
    //pedirAlBack().then()
    cambiarUrlConIdSesion("registrarTrayecto")

})

document.getElementById("navHCTotal").addEventListener('click', e=> {
    e.preventDefault()
    //pedirAlBack().then()
    cambiarUrlSinIdSesion("generar_reporte_hc_total")

})

document.getElementById("navEvolucionHC").addEventListener('click', e=> {
    e.preventDefault()
    //pedirAlBack().then()
    cambiarUrlSinIdSesion("generar_reporte_evolucion")

})

document.getElementById("navComposicionHC").addEventListener('click', e=> {
    e.preventDefault()
    //pedirAlBack().then()
    cambiarUrlSinIdSesion("generar_reporte_composicion")

})

function mandarAlBack() {
    fetch('https://dds-2022-mi-ma-grupo-06.herokuapp.com/registrarTrayecto', {
        body: {
            idSesion: obtenerSesion()
        },
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(this.body)
    }).then()
}

document.getElementById("cerrarSesion").addEventListener('click', e=> {
    e.preventDefault()
    cerrarSesion()
})

document.getElementById("navHome").addEventListener('click', e=> {
    e.preventDefault()
    //pedirAlBack().then()
    cambiarUrlConIdSesion("home")
})
