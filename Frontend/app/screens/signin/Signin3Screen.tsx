import React, { useState } from "react";
import { ImageBackground, View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from "react-native";
import Calls from "../../apis/guardians/Calls";
import TokenHandler from "../../apis/guardians/TokenHandler";
import AsyncStorageUtils from "../../util/AsyncStorageUtils";

import styles from './styles';

interface Usuario {
    "id": number | null,
    "guardiaoId": number | null,
    "nome": string,
    "sobrenome": string,
    "idade": number | null,
    "email": string,
    "nome_guardiao": string | null,
    "telefones": string[],
    "acesso": {
        "nomeUsuario": string,
        "senha": string,
        "dica1": string,
        "dica2": string
    },
    "configuracao": {
        "preMensagem": string,
        "telefoneVisivel": boolean,
        "enderecoVisivel": boolean,
        "idadeVisivel": boolean,
        "enviarParaGrupos": boolean,
        "compartilharLocalizacao": boolean,
        "gruposParaEnviarIds": number[]
    },
    "endereco": {
        "estado": string,
        "cidade": string,
        "bairro": string,
        "logradouro": string,
        "cep": string,
        "numero": string,
        "complemento": string | null
    },
    "grupos_id": number[]
}

export default function Signin2Screen({ navigation, route }: any) {
    const [telefone, setTelefone] = useState(route.params.telefone);
    const [nome, setNome] = useState(route.params.nome);
    const [sobrenome, setSobrenome] = useState(route.params.sobrenome);
    const [dtNasc, setDtNasc] = useState(route.params.dt);
    const [email, setEmail] = useState(route.params.email);

    const [cep, setCep] = useState(route.params.cep);
    const [logradouro, setLogrdouro] = useState(route.params.logradouro);
    const [bairro, setBairro] = useState(route.params.bairro);
    const [cidade, setCidade] = useState(route.params.cidade);
    const [estado, setEstado] = useState(route.params.estado);
    const [numero, setNumero] = useState(route.params.numero);
    const [complemento, setComplemento] = useState(route.params.complemento);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [dica1, setDica1] = useState('');
    const [dica2, setDica2] = useState('');

    const [usuario, setUsuario] = useState<Usuario>({
        "id": null,
        "guardiaoId": null,
        "nome": "",
        "sobrenome": "",
        "idade": null,
        "email": "",
        "nome_guardiao": null,
        "telefones": [],
        "acesso": {
            "nomeUsuario": "",
            "senha": "",
            "dica1": "",
            "dica2": ""
        },
        "configuracao": {
            "preMensagem": "Preciso de ajuda.",
            "telefoneVisivel": true,
            "enderecoVisivel": true,
            "idadeVisivel": true,
            "enviarParaGrupos": false,
            "compartilharLocalizacao": false,
            "gruposParaEnviarIds": []
        },
        "endereco": {
            "estado": "",
            "cidade": "",
            "bairro": "",
            "logradouro": "",
            "cep": "",
            "numero": "",
            "complemento": null
        },
        "grupos_id": []
    });

    return (
        <View style={styles.container}>


            <Text style={styles.textTitle}>Para finalizar seu cadastro, informe suas credenciais de login.</Text>

            <TextInput
                style={styles.input}
                placeholder={"NOME DE USUÁRIO"}
                value={username}
                onChangeText={(text) => setUsername(text)}
            />
            <TextInput
                style={styles.input}
                secureTextEntry={true}
                placeholder={"SENHA"}
                value={password}
                onChangeText={(text) => setPassword(text)}
            />

            <Text style={styles.textTitle}>Caso necessite recuperar a senha, informe duas dicas para lhe ajudar a lembrá-la.</Text>

            <TextInput
                style={styles.input}
                maxLength={30}
                placeholder={"DICA DA SENHA 1"}
                value={dica1}
                onChangeText={(text) => setDica1(text)}
            />
            <TextInput
                style={styles.input}
                maxLength={30}
                placeholder={"DICA DA SENHA 2"}
                value={dica2}
                onChangeText={(text) => setDica2(text)}
            />

            <TouchableOpacity
                style={styles.button}
                onPress={() => {

                    usuario.nome = nome;
                    usuario.sobrenome = sobrenome;
                    usuario.idade = dtNasc;
                    usuario.email = email;
                    usuario.telefones = [telefone];

                    usuario.acesso.nomeUsuario = username;
                    usuario.acesso.senha = password;
                    usuario.acesso.dica1 = dica1;
                    usuario.acesso.dica2 = dica2;

                    usuario.endereco.cep = cep;
                    usuario.endereco.cidade = cidade;
                    usuario.endereco.estado = estado;
                    usuario.endereco.bairro = bairro;
                    usuario.endereco.numero = numero;
                    usuario.endereco.logradouro = logradouro;
                    usuario.endereco.complemento = complemento;


                    if (username.length > 0 && password.length > 0 && dica1.length > 0 && dica2.length > 0) {
                        const calls = new Calls();
                        calls.privateCall("usuarios", usuario, "post", "")
                            .then(response => {
                                Alert.alert("Parabéns", "Seu cadastro foi efetuado!");

                                const handler = new TokenHandler();
                                handler.getToken(username, password).then(response => {
                                    const utils = new AsyncStorageUtils();
                                    utils.set("token", response.data.Authorization);

                                    navigation.reset({
                                        index: 0,
                                        routes: [{ name: 'home' }],
                                    });

                                }).catch(err => {
                                    Alert.alert("Usuário inválido", "Senha incorreta, tente novamente.");
                                });

                            })
                            .catch(err => {
                                console.log(err.response.data);

                                Alert.alert("Quase lá!", err.response.data.message);
                            });
                    } else {
                        Alert.alert("Quase lá!", "Preencha todos os campos para concluir seu cadastro!");
                    }







                }}
            >
                <Text style={styles.buttonText}>CONCLUIR</Text>
            </TouchableOpacity>


        </View>
    );
}
