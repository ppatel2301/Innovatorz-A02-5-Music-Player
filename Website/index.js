function showSection (sectionId, linkId) {
  collapseNavbar();
  var sections = document.getElementsByTagName ('section');
  for (var i = 0; i < sections.length; i++) {
    if (sections[i].id === sectionId) {
      sections[i].classList.remove ('hidden');
    } else {
      sections[i].classList.add ('hidden');
    }
  }  
}

function showFeatures(){
  fetch("./assets/videos.json").then(response => response.json())
  .then(data =>{
    for (var i = 0; i < data.length; i++) {
      var container = document.getElementById('video-card-container');
      if (container.innerHTML != null)
        container.innerHTML += videoCard (i, data[i]);
    }
  })
  .catch(error =>{
    console.error("Fetch error:", error)
  })
}

function videoCard(index, thing){
  url = thing.videoURL
  if (thing.videoURL == ""){
    thing.videoURL = `
    <svg
  class="bd-placeholder-img card-img-top"
  width="100%"
  height="180"
  xmlns="http://www.w3.org/2000/svg"
  role="img"
  aria-label="Placeholder: Image cap"
  preserveAspectRatio="xMidYMid slice"
  focusable="false"
>
  <title>Placeholder</title>
  <rect width="100%" height="100%" fill="#868e96" />
  <text x="50%" y="50%" fill="#dee2e6" dy=".3em">Placeholder</text>
</svg>
`
  }
  return `
  <div class="col">
    <div class="card-custom h-100">
      <div class="card-body">
      <div class="embed-responsive embed-responsive-16by9 ">
        ${thing.videoURL}
      </div>
        <h5 class="card-title">${thing.feature}</h5>
        <p class="card-text">${thing.featureDescription}</p>
      </div>
    </div>
  </div>
  `
}

function makeActive(element){
  cleanActive();
  element.classList.add("active-custom")
}

function cleanActive(){
  actives = document.getElementsByClassName("active-custom");
  for (var i = 0; i < actives.length; i++) {
    actives[i].classList.remove("active-custom");
  }
}

function collapseNavbar () {
  $ ('#navbarSupportedContent').collapse ('hide');
}
