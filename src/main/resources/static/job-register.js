const defaultFile = 'https://objetivoligar.com/wp-content/uploads/2017/03/blank-profile-picture-973460_1280-580x580.jpg';

const file = document.getElementById('archivo');
const img = document.getElementById ('img');
file.addEventListener( 'change', e => {
    if( e.target.files[0] ) {
        const reader = new FileReader();
        reader.onload = function(e) {
            img.src = e.target.result;
        };
        reader.readAsDataURL(e.target.files[0]);
    } else {
        img.src = defaultFile;
    }
});