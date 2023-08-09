// const jobList = document.getElementById("jobList");

// function findJobs(query) {

//   fetch("/job/buscar?q=" + query)
//   .then((response) => response.text())
//   .then((data) => {
//     document.getElementById("resultadosBusqueda").innerHTML = data;
//   });
// }

// if (query != '') {
//   jobList.style.display = "block";
// } else {
//   jobList.style.display = "none";
// }

function findJobs(query) {
  // Obtener referencia al elemento jobList
  const jobList = document.getElementById("jobList");

  // Obtener referencia al elemento de búsqueda
  const searchInput = document.getElementById("searchInput");

  // Ocultar el jobList cuando se hace clic en el input de búsqueda
  // searchInput.addEventListener("click", () => {
  //   jobList.style.display = "none";
  // });

  // Mostrar el jobList cuando el input está vacío
  searchInput.addEventListener("input", () => {
    if (searchInput.value === "") {
      jobList.style.display = "block";
    }
  });

  // Realizar la búsqueda utilizando fetch
  fetch("/job/buscar?q=" + query)
    .then((response) => response.text())
    .then((data) => {
      document.getElementById("resultadosBusqueda").innerHTML = data;
    });
}
