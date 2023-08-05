document.getElementById('fileForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Evitar el comportamiento predeterminado del formulario

    const fileInput = document.getElementById('name');
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const fileContent = e.target.result; // Aquí tienes el contenido del archivo en base64 o texto

            // Mostrar la imagen en la etiqueta 'img'
            document.getElementById('previewImage').setAttribute('src', fileContent);
            document.getElementById('previewImage').style.display = 'block';

            // Realizar una solicitud al backend (Spring) para procesar el archivo
            enviarArchivoAlBackend(fileContent);
        };

        // Leer el archivo como URL de datos (para imágenes)
        reader.readAsDataURL(file);
    } else {
        // Manejar caso cuando no se selecciona ningún archivo
    }
});

function enviarArchivoAlBackend(fileContent) {
    // Código para enviar el contenido del archivo al backend (igual que en el ejemplo anterior)
    // ...
}



