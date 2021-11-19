import React, { useEffect, useState } from 'react';
import { View, Text, TouchableOpacity, TextInput, Alert } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import { TextInputMask } from 'react-native-masked-text';
import Calls from '../../apis/guardians/Calls';
import Dialog from "react-native-dialog";

import Menu from '../../components/menu/Menu';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import styles from './style';
import ViaCepService from '../../apis/viacep/VIACEP';

interface Usuario {
    "id": number,
    "guardiaoId": number,
    "nome": string,
    "sobrenome": string,
    "idade": number,
    "email": string,
    "nome_guardiao": string,
    "telefones": string[],
    "acesso": {
        "nomeUsuario": string
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
        "complemento": string
    },
    "grupos_id": number[]
}

export default function UsuarioInfo({ navigation }: any) {

    const [usuario, setUsuario] = useState<Usuario>({
        "id": 0,
        "guardiaoId": 0,
        "nome": "",
        "sobrenome": "",
        "idade": 0,
        "email": "",
        "nome_guardiao": "",
        "telefones": [
            ""
        ],
        "acesso": {
            "nomeUsuario": ""
        },
        "configuracao": {
            "preMensagem": "",
            "telefoneVisivel": true,
            "enderecoVisivel": true,
            "idadeVisivel": true,
            "enviarParaGrupos": true,
            "compartilharLocalizacao": true,
            "gruposParaEnviarIds": [
                0
            ]
        },
        "endereco": {
            "estado": "",
            "cidade": "",
            "bairro": "",
            "logradouro": "",
            "cep": "",
            "numero": "",
            "complemento": ""
        },
        "grupos_id": [
            0
        ]
    });

    const [popup, setPopup] = useState(false);


    const [id, setId] = useState('');
    const [nome, setNome] = useState('');
    const [sobrenome, setSobrenome] = useState('');
    const [dt, setDt] = useState('');

    const [email, setEmail] = useState('');
    const [telefone, setTelefone] = useState('');
    const [oldTelefone, setOldTelefone] = useState('');

    const [guaId, setGuaId] = useState('');
    const [guaNome, setGuaNome] = useState('');

    const [cep, setCep] = useState('');
    const [estado, setEstado] = useState('');
    const [cidade, setCidade] = useState('');
    const [bairro, setBairro] = useState('');
    const [logradouro, setLogradouro] = useState('');
    const [numero, setNumero] = useState('');
    const [complemento, setComplemento] = useState('');


    useEffect(() => {
        const storageUtils = new AsyncStorageUtils();
        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("usuarios", {}, "get", token)
                .then((response) => {
                    setUsuario(response.data);
                });
        })
    }, []);

    useEffect(() => {

        if(cep.length == 9 || cep.length == 8 && cep.indexOf('-') == -1){
            let cepTemp = '';
            
            if(cep.length == 8){
                cepTemp = cep.substring(0,5) + '-' + cep.substring(5,8);
            }else{
                cepTemp = cep;
            }
            let service = new ViaCepService();
            

            let data = {
                'logradouro' : '',
                'bairro' : '',
                'cidade' : '',
                'estado' : ''
            };

            service.getAddressByZipCode(cepTemp)
            .then(function (response) {
                const respObj = response.data;
                
                data.logradouro = respObj.logradouro;
                data.bairro = respObj.bairro;
                data.cidade = respObj.localidade;
                data.estado = respObj.uf;

                setLogradouro(data.logradouro);
                setBairro(data.bairro);
                setCidade(data.cidade);
                setEstado(data.estado);

                console.log(data);
            })
            .catch(function (error) {
                console.log(error);
            });

            

        }else{
            setLogradouro('');
            setBairro('');
            setCidade('');
            setEstado('');
        }
    }, [cep]);

    useEffect(() => {

        setId('' + usuario.id);
        setNome(usuario.nome);
        setSobrenome(usuario.sobrenome);
        setDt('' + usuario.idade);

        setEmail(usuario.email);
        setOldTelefone('' + usuario.telefones[0]);
        setTelefone('' + usuario.telefones[0]);
        

        setGuaId('' + usuario.guardiaoId);
        setGuaNome(usuario.nome_guardiao);

        setCep(usuario.endereco.cep);
        setEstado(usuario.endereco.estado);
        setCidade(usuario.endereco.cidade);
        setBairro(usuario.endereco.bairro);
        setLogradouro(usuario.endereco.logradouro);
        setNumero(usuario.endereco.numero);
        setComplemento(usuario.endereco.complemento);

    }, [usuario]);

    return (
        <View
            style={styles.container}
        >

            <Dialog.Container visible={popup}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Salvar Alterações</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Você tem certeza que deseja alterar suas informações?
                </Dialog.Description>
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setPopup(false);
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Confirmar" onPress={() => {

                    let tempCep = cep.replace('-', '');

                    let tempTel = telefone.replace('(', '');
                    tempTel = tempTel.replace(' ', '');
                    tempTel = tempTel.replace('-', '');
                    tempTel = tempTel.replace(')', '');

                    let tempOldTel = oldTelefone.replace('(', '');
                    tempOldTel = tempOldTel.replace(' ', '');
                    tempOldTel = tempOldTel.replace('-', '');
                    tempOldTel = tempOldTel.replace(')', '');

                    usuario.nome = nome;
                    usuario.sobrenome = sobrenome;
                    usuario.idade = + dt;

                    usuario.email = email;
                    usuario.telefones = [tempTel];
                    setOldTelefone(tempOldTel);

                    usuario.endereco.cep = tempCep;
                    usuario.endereco.estado = estado;
                    usuario.endereco.cidade = cidade;
                    usuario.endereco.bairro = bairro;
                    usuario.endereco.logradouro = logradouro;
                    usuario.endereco.numero = numero;
                    usuario.endereco.complemento = complemento;

                    console.log(usuario);

                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();
                        calls.privateCall("usuarios", usuario, "put", token)
                            .then(response => {
                                calls.privateCall("enderecos", usuario.endereco, "put", token)
                                .then(response => {
                                    calls.privateCall("usuarios/telefones?telefone=" + oldTelefone, usuario.endereco, "delete", token)
                                    .then(response => {
                                        calls.privateCall("usuarios/telefones?telefone=" + usuario.telefones[0], usuario.endereco, "post", token)
                                        .then(response => {
                                            Alert.alert("Dados alterados!", "Seus dados foram altarados com sucesso!");
                                            setPopup(false);
                                        });
                                    });
                                });
                            })
                            .catch(err => {
                                Alert.alert("Erro", "Algo deu errado! Tente novamente mais tarde.");
                                setPopup(false);
                            });
                        }
                    )




                   
                }} />
            </Dialog.Container>

            <ScrollView
                style={{ width: "100%" }}
            >


                <Text style={styles.header}>Dados Pessoais</Text>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>ID</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"ID"}
                        value={id}
                        onChangeText={(text) => setId(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Nome</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"NOME"}
                        value={nome}
                        onChangeText={(text) => setNome(text)}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Sobrenome</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"SOBRENOME"}
                        value={sobrenome}
                        onChangeText={(text) => setSobrenome(text)}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Ano de nascimento</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"ANO DE NASCIMENTO"}
                        value={dt}
                        onChangeText={(text) => setDt(text)}
                    />
                </View>

                <Text style={styles.header}>Contatos</Text>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>E-mail</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"E-MAIL"}
                        value={email}
                        onChangeText={(text) => setEmail(text)}
                    />
                </View>


                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Telefone</Text>
                    <TextInputMask
                        style={styles.input}
                        type={'cel-phone'}
                        options={{
                            maskType: 'BRL'
                        }}
                        placeholder={"TELEFONE"}
                        keyboardType={"numeric"}
                        value={telefone}
                        onChangeText={(text) => setTelefone(text)}
                    />
                </View>

                <Text style={styles.header}>Guardião</Text>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>ID do guardião</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"ID DO GUARDIÃO"}
                        value={guaId}
                        onChangeText={(text) => setGuaId(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Nome do guardião</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"NOME DO GUARDIÃO"}
                        value={guaNome}
                        onChangeText={(text) => setGuaNome(text)}
                        editable={false}
                    />
                </View>


                <Text style={styles.header}>Endereço</Text>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>CEP</Text>
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
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Estado</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"ESTADO"}
                        value={estado}
                        onChangeText={(text) => setEstado(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Cidade</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"CIDADE"}
                        value={cidade}
                        onChangeText={(text) => setCidade(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Bairro</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"BAIRRO"}
                        value={bairro}
                        onChangeText={(text) => setBairro(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Logradouro</Text>
                    <TextInput
                        style={[styles.input, styles.disabledColor]}
                        placeholder={"LOGRADOURO"}
                        value={logradouro}
                        onChangeText={(text) => setLogradouro(text)}
                        editable={false}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Número</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"NÚMERO"}
                        value={numero}
                        onChangeText={(text) => setNumero(text)}
                    />
                </View>

                <View
                    style={styles.row}
                >
                    <Text style={styles.bold}>Complemento</Text>
                    <TextInput
                        style={styles.input}
                        placeholder={"COMPLEMENTO"}
                        value={complemento}
                        onChangeText={(text) => setComplemento(text)}
                    />
                </View>

                <View
                    style={styles.buttonView}
                >
                    <TouchableOpacity
                        style={styles.mainButton}
                        onPress={() => {
                            setPopup(true);
                        }}
                    >
                        <Text
                            style={styles.textButton}
                        >Salvar</Text>
                    </TouchableOpacity>

                </View>




            </ScrollView>
            <Menu
                goConfigs={() => {
                    navigation.navigate('config');
                }}
                goGroups={() => {
                    navigation.navigate('groups');
                }}
                goNotifications={() => {
                    navigation.navigate('notification');
                }}
                goUserInfo={() => {
                    navigation.navigate('userInfo');
                }}
            ></Menu>
        </View>
    );
}