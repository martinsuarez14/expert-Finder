const jobList = document.getElementById("jobList");

function findJobs(query) {
  if (query != "") {
    jobList.style.display = "none";
  } else {
    jobList.style.display = "block";
  }

  fetch("/job/buscar?q=" + query)
    .then((response) => response.text())
    .then((data) => {
      document.getElementById("resultadosBusqueda").innerHTML = data;
    });
}
