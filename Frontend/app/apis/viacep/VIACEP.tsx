import axios from 'axios';

export default class ViaCepService {
    
    private BASE_URL = "https://viacep.com.br/ws/";
    private RETURN_FORMAT = "/json/"

    public getAddressByZipCode(cep:String) {
        cep = cep.replace('-', '');
        const url = this.BASE_URL + cep + this.RETURN_FORMAT;
        return axios.get(url);
    }

}
