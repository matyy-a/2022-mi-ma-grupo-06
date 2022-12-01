//Funciones relacionadas con el idSesion

const ID_SESION = "idSesion"
const guardarSesion = (idSesion) => localStorage.setItem(ID_SESION, idSesion)
const obtenerSesion = () => localStorage.getItem(ID_SESION)
const borrarSesion = () => localStorage.removeItem(ID_SESION)



//Funciones de redireccion entre páginas

const loginFunction = async () => {
    // Obtenemos los parametros del formulario
    const form = document.getElementById("formLogin");
    const username = form.username.value;
    const password = form.password.value;
    // Mandamos las credenciales al backend
    const backendResponse = await loginAlBack(username, password)
    // Obtenemos el idSesion (Podriamos mejorar el codigo y validar errores o que exista)
    const idSesion = backendResponse.idSesion


    //const tipoUsuario = backendResponse.tipoUsuario


    // Guardamos en localStorage el idSesion del usuario
    guardarSesion(idSesion)


    cambiarUrlConIdSesion("home")
}
const loginAlBack = async (username, password) => {
    // Se arma el body en formato JSON (igual que en postman)
    const body = {
        username,
        password
    }
    // Se ejecuta la request
    const backResponse = await fetch('http://localhost:8080/login', {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(body)
    })
    // Retornamos la respuesta del backend
    return await backResponse.json()
}

const cambiarUrlSinIdSesion = (url = "/") => {

    const idSesion = obtenerSesion()
    if (!idSesion) {
        window.location = "/login"
    } else {
        window.location = `/${url}`
    }
}

const cambiarUrlConIdSesion = (url = "/") => {

    const idSesion = obtenerSesion()
    if (!idSesion) {
        window.location = "/login"
    } else {
        window.location = `/${url}?sesion=${idSesion}`
    }
}