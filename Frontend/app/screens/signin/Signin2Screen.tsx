import React, { useEffect, useState } from "react";
import { ImageBackground, View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from "react-native";
import { TextInputMask } from 'react-native-masked-text';
import ViaCepService from "../../apis/viacep/VIACEP";

import styles from './styles';

export default function Signin2Screen({ navigation, route }: any) {
    route.params.dica1

    const [telefone, setTelefone] = useState(route.params.telefone);

    const [nome, setNome] = useState(route.params.nome);
    const [sobrenome, setSobrenome] = useState(route.params.sobrenome);
    const [dtNasc, setDtNasc] = useState(route.params.dt);

    const [email, setEmail] = useState(route.params.email);
    

    const [cep, setCep] = useState('');
    const [logradouro, setLogrdouro] = useState('');
    const [bairro, setBairro] = useState('');
    const [cidade, setCidade] = useState('');
    const [estado, setEstado] = useState('');
    const [numero, setNumero] = useState('');
    const [complemento, setComplemento] = useState('');

    useEffect(() => {

        if(cep.length == 9){
            let service = new ViaCepService();

            let data = {
                'logradouro' : '',
                'bairro' : '',
                'cidade' : '',
                'estado' : ''
            };

            service.getAddressByZipCode(cep)
            .then(function (response) {
                const respObj = response.data;
                
                data.logradouro = respObj.logradouro;
                data.bairro = respObj.bairro;
                data.cidade = respObj.localidade;
                data.estado = respObj.uf;

                setLogrdouro(data.logradouro);
                setBairro(data.bairro);
                setCidade(data.cidade);
                setEstado(data.estado);

                console.log(data);
            })
            .catch(function (error) {
                console.log(error);
            });

            

        }else{
            setLogrdouro('');
            setBairro('');
            setCidade('');
            setEstado('');
        }
    }, [cep]);
    return (       

        <View style={styles.container}>

            {/* <ImageBackground source={require("../../../assets/Back3.png")} style={styles.background}>
            </ImageBackground> */}

            <Text style={styles.textTitle}>Estamos quase lá, agora informe seu endereço.</Text>

            <TextInputMask
                style={styles.input}
                type={'zip-code'}
                options={{
                    maskType:'BRL',
                }}
                value={cep}
                onChangeText={text => setCep(text)}
                placeholder={"CEP"}
                keyboardType={"numeric"}
            />

            <TextInput
                style={[ styles.input, styles.disabledColor ]}
                placeholder={"LOGRADOURO"}
                value={logradouro}
                editable={false}
            />
            <TextInput
                style={styles.input}
                placeholder={"NÚMERO"}
                onChangeText={(text)=> setNumero(text)}
            />
            <TextInput
                style={styles.input}
                placeholder={"COMPLEMENTO"}
                onChangeText={(text)=> setComplemento(text)}
            />
            <TextInput
                style={[ styles.input, styles.disabledColor ]}
                placeholder={"BAIRRO"}
                value={bairro}
                editable={false}
            />
            <TextInput
                style={[ styles.input, styles.disabledColor ]}
                placeholder={"CIDADE"}
                value={cidade}
                editable={false}
            />
            <TextInput
                style={[ styles.input, styles.disabledColor ]}
                placeholder={"ESTADO"}
                value={estado}
                editable={false}
            />

            <TouchableOpacity
                style={styles.button}
                onPress={() => {

                    if(cep.length > 0 && estado.length > 0 && cidade.length > 0 && bairro.length > 0 && logradouro.length > 0 && numero.length > 0) {
                        const cepTemp = cep.replace('-', '');

                        navigation.navigate('signin3', {
                            "nome" : nome,
                            "sobrenome" : sobrenome,
                            "email" : email,
                            "telefone" : telefone,
                            "dt" : dtNasc, 
                            "cep" : cepTemp,
                            "estado" : estado,
                            "cidade" : cidade,
                            "bairro" : bairro,
                            "logradouro" : logradouro,
                            "numero" : numero,
                            "complemento": complemento == '' ? null : complemento
                        });
                    }else{
                        Alert.alert("Erro!", "Único campo não obrigatório é o complemento, para prosseguir preencha os demais campos (verifique se o seu CEP está correto).");
                    }

                }}
            >
                <Text style={styles.buttonText}>AVANÇAR</Text>
            </TouchableOpacity>


        </View>
    );
}