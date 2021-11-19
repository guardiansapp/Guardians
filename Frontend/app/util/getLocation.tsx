import * as Location from 'expo-location';

export default class LocalizationUtil {

    getLocation = () => {
        Location.installWebGeolocationPolyfill();
        navigator.geolocation.getCurrentPosition(
            pos => {
                let lat = pos.coords.latitude;
                let lon = pos.coords.longitude;
                return {
                    'lat' : lat,
                    'log' : lon
                }  
            }
        );
    }
}