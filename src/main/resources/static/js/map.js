
const InitMap = (mapId, buttonsId, inputNameId, inputLatitudeId, inputLongitudeId, inputAddressId) => {
    const buttons = document.getElementsByClassName(buttonsId);
    const nameInputs = document.getElementsByClassName(inputNameId);
    const latitudeInputs = document.getElementsByClassName(inputLatitudeId);
    const longitudeInputs = document.getElementsByClassName(inputLongitudeId);
    const addressInputs = document.getElementsByClassName(inputAddressId);
    
    // https://leafletjs.com/reference.html#latlng
    const zoom = 13;
    var map = L.map(mapId);
    map.setView({ lng: longitudeInputs[0].value, lat: latitudeInputs[0].value }, zoom);

    for (let i = 0; i < nameInputs.length; i++) {
        const name = nameInputs[i].value;
        const latitude = latitudeInputs[i].value;
        const longitude = longitudeInputs[i].value;
        const address = addressInputs[i].value;

        L.marker(L.latLng(latitude, longitude)).addTo(map)
            .bindPopup(`${name}<br>${address}`)
            .openPopup();

        buttons[i].onclick = () => {
            map.setView({ lng: longitude, lat: latitude }, zoom);
        };
    }

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);
}