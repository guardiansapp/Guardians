import axios from 'axios';

export default class TokenHandler {
    
    private BASE_URL = "https://guardiansapp.herokuapp.com/";

    public getToken(user:String, password:String) {

        const url = this.BASE_URL + "login";

        return axios.post(
            url,
            {
                "username" : user,
                "password" : password
            },
            {
                headers: {
                    'Content-Type': 'application/json',
                }
            }
        );

    }

}
