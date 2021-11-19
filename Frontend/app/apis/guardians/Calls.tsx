import axios from 'axios';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';

export default class Calls {
    
    private BASE_URL = "https://guardiansapp.herokuapp.com/";
    private storageUtils = new AsyncStorageUtils();

    public async privateCall(url :string, data : object, method : string, token : string | null) {

        url = this.BASE_URL + url;
       
        if(method == "get" || method == "GET"){
            return axios.get(
                url,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization' : token
                    }
                }
            );
        }else if(method == 'post' || method == "POST"){
            return axios.post(
                url,
                data,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization' : token
                    }
                },
            );
        }
        else if(method == 'put' || method == 'PUT'){
            return axios.put(
                url,
                data,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization' : token
                    }
                },
            );
        }
        else if(method == 'delete' || method == 'DELETE'){
            return axios.delete(
                url,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'authorization' : token
                    }
                },
            );
        }
        return axios.get('');
      
    } 

    public async handle403Error() {
        await this.storageUtils.remove('token');
    }
}
