document.getElementById("archivo").addEventListener("change", function () {
  const imagenPreview = document.getElementById("img");
  const file = this.files[0]; // Obtiene el archivo seleccionado

  if (file) {
    // Crea un objeto FileReader para leer el archivo
    const reader = new FileReader();

    // Cuando el archivo esté completamente cargado, establece la imagen como fuente de la vista previa
    reader.onload = function (e) {
      imagenPreview.src = e.target.result;
      imagenPreview.style.display = "inline"; // Muestra la vista previa
    };

    // Lee el archivo como una URL de datos (data URL)
    reader.readAsDataURL(file);
  } else {
    imagenPreview.style.display = "none"; // Oculta la vista previa si no hay imagen seleccionada
  }
});
