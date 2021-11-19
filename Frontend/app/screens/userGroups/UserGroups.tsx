import React, { useEffect, useState } from 'react';
import { Image, View, Text, TouchableOpacity, FlatList, Alert } from "react-native";
import Calls from '../../apis/guardians/Calls';
import Menu from '../../components/menu/Menu'
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import Dialog from "react-native-dialog";

import styles from './style';

interface Usuario {
    "id": number,
    "guardiaoId": number,
    "nome": string,
    "sobrenome": string,
    "idade": number,
    "email": string,
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

export default function UserGroups({ navigation }: { navigation: any }) {

    const [popup, setPopup] = useState(false);
    const [newGroup, setNewGroup] = useState('');
    const [usuario, setUsuario] = useState<Usuario>();

    const [groups, setGroups] = useState([
        {
            "id": 0,
            "nome": "",
            "totalNotificacoes": 2,
            "notificacoesNaoLidas": 2
        }
    ]);

    useEffect(() => {
        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("grupos/usuarios", {}, "get", token).then(response => {
                setGroups(response.data);
                const calls = new Calls();
                calls.privateCall("usuarios", {}, "get", token).then(response => {
                    setUsuario(response.data);
                });
            });
        })
    }, []);

    const Group = ({ item }: any) => {
        return (

            (item.id != 0)

                ?

                <TouchableOpacity
                    style={styles.group}
                    onPress={() => {
                        navigation.navigate('group', {
                            'id': item.id,
                            'name': item.nome
                        });
                    }}
                >
                    <View style={[styles.littleBall, (item.notificacoesNaoLidas == '0') ? { opacity: 0 } : {}]}>
                        <Text style={styles.littleBallsText}>{item.notificacoesNaoLidas}</Text>
                    </View>
                    <Text style={styles.groupName}>{item.nome}</Text>
                    <Text style={styles.qtdNotifications}>
                        Total de notificações (
                        <Text style={styles.qtd}>
                            {item.totalNotificacoes}
                        </Text>
                        )
                    </Text>
                </TouchableOpacity>

                :

                <></>
        );
    }



    return (
        <View style={styles.container}>

            <Dialog.Container visible={popup}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Criação de grupo</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Entre com o nome do grupo que deseja criar.
                </Dialog.Description>

                <Dialog.Input placeholder={"Nome do grupo"} keyboardType={"numeric"} style={{ fontSize: 17 }} value={newGroup} onChangeText={(text) => setNewGroup(text)} />

                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setPopup(false);
                    setNewGroup('');
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Criar" onPress={() => {

                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();
                        calls.privateCall("grupos", 
                        {
                            "nome" : newGroup
                        }, 
                        "post", token).then(response => {

                            const id = (response.data.id);

                            storageUtils.get("token").then(token => {
                                const calls = new Calls();
                                calls.privateCall("grupos/entrar/" + id + "?userId=" + usuario?.id, {}, "post", token).then(response => {
                                    const calls = new Calls();
                                    calls.privateCall("grupos/usuarios", {}, "get", token).then(response => {
                                        setGroups(response.data);

                                        setPopup(false);
                                        setNewGroup('');
                                        Alert.alert("Concluído", "Seu grupo foi criado com sucesso, adicione outros usuários ao seu grupo.");
                                    });
                                });
                    
                            });
                        });

                    });

                }} />
            </Dialog.Container>


            <FlatList
                style={styles.flat}
                data={groups}
                keyExtractor={item => {
                    return item.id.toString()
                }}
                renderItem={({ item }) => {
                    return (
                        <Group item={item} />
                    )
                }}
            />

            <TouchableOpacity
                style={[styles.button, styles.addButton]}
                onPress={() => {
                    setPopup(true);
                }}
            >
                <Image source={require('../../../assets/addGroup.png')} style={styles.addIcon}></Image>
            </TouchableOpacity>

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
