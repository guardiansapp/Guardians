import React, { useEffect, useState } from 'react';
import { View, Text, TouchableWithoutFeedback, TouchableOpacity, FlatList, Alert } from 'react-native';
import { Switch } from 'react-native-paper';
import Calls from '../../apis/guardians/Calls';
import Menu from '../../components/menu/Menu';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import Dialog from "react-native-dialog";

import styles from './style';


interface Grupo {
    "id": number,
    "nome": string,
    "usuarios": [
        {
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
        },
    ]
}

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

export default function GroupInfo({ navigation, route }: any) {

    React.useLayoutEffect(() => {
        navigation.setOptions({
            title: groupName,
        });
    }, [navigation]);

    const groupId = route.params.id;
    const groupName = route.params.name;

    const [sendToThisGroup, setSendToThisGroup] = useState(false);
    const toggleSendToThisGroup = () => setSendToThisGroup(!sendToThisGroup);
    const [usuario, setUsuario] = useState<Usuario>();

    const [altOpt, setAltOpt] = useState(false);
    const [altGua, setAltGua] = useState(false);
    const [guaId, setGuaId] = useState<number>();

    const [group, setGroup] = useState<Grupo>();


    useEffect(() => {

        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("grupos/" + groupId, {}, "get", token)
                .then((response) => {
                    setGroup(response.data);
                });

            calls.privateCall("usuarios", {}, "get", token)
                .then((response) => {
                    setUsuario(response.data);
                });

        })

    }, []);

    useEffect(() => {

        let x = usuario?.configuracao.gruposParaEnviarIds.indexOf(groupId)! > -1;
        setSendToThisGroup(x);

    }, [usuario]);


    const UserProfile = (item: any) => {
        const obj = item.item;

        return (
            <View style={[styles.profileContainer]}>
                <View style={styles.row}>
                    <Text style={[styles.strong, styles.profileText]}>ID: </Text>
                    <Text style={styles.profileText}>#{obj.id}</Text>
                </View>

                <View style={styles.row}>
                    <Text style={[styles.strong, styles.profileText]}>Nome: </Text>
                    <Text style={styles.profileText}>{obj.nome + ' ' + obj.sobrenome} </Text>
                </View>

                <View style={styles.row}>
                    <Text style={[styles.strong, styles.profileText]}>E-mail: </Text>
                    <Text style={styles.profileText}>{obj.email}</Text>
                </View>

                {
                    (obj.configuracao.idadeVisivel)
                        ?
                        <View style={styles.row}>
                            <Text style={[styles.strong, styles.profileText]}>Idade: </Text>
                            <Text style={styles.profileText}>{new Date().getFullYear() - obj.idade}</Text>
                        </View>
                        :
                        <></>
                }
                {

                    (obj.configuracao.enderecoVisivel)
                        ?
                        <View style={styles.row}>
                            <Text style={[styles.strong, styles.profileText]}>Endereço: </Text>
                            <Text style={styles.profileText}>{obj.endereco.logradouro + ', ' + obj.endereco.bairro + ", " + obj.endereco.cidade + " - " + obj.endereco.estado + " - CEP " + obj.endereco.cep}</Text>
                        </View>
                        :
                        <></>
                }
                {
                    (obj.telefones && obj.configuracao.telefoneVisivel)
                        ?
                        <View style={styles.row}>
                            <Text style={[styles.strong, styles.profileText]}>Telefone: </Text>
                            <Text style={styles.profileText}>{obj.telefones[0]}</Text>
                        </View>
                        :
                        <></>
                }
                <TouchableOpacity
                    style={styles.linkTouch}
                    onPress={() => {
                        setAltGua(true);
                        setGuaId(obj.id);
                    }}    
                >
                    <Text style={styles.link}>Definir como guardião</Text>
                </TouchableOpacity>

            </View>
        );
    }

    return (
        <View
            style={styles.container}
        >

            <Dialog.Container visible={altOpt}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Alterar preferência do grupo</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Você tem certeza que deseja altrar essa configuração?
                </Dialog.Description>
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setAltOpt(false);
                    toggleSendToThisGroup();

                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Alterar" onPress={() => {


                    const storageUtils = new AsyncStorageUtils();

                    if (sendToThisGroup) {

                        storageUtils.get("token").then(token => {
                            const calls = new Calls();
                            calls.privateCall("configuracoes/grupospermitidos?grupo=" + groupId, {}, "post", token)
                                .then((response) => {
                                    setAltOpt(false);
                                    Alert.alert("Habilitado!", "Suas configurações foram alteradas")
                                });
                        });

                    } else {
                        storageUtils.get("token").then(token => {
                            const calls = new Calls();
                            calls.privateCall("configuracoes/grupospermitidos?grupo=" + groupId, {}, "delete", token)
                                .then((response) => {
                                    setAltOpt(false);
                                    Alert.alert("Desabilitado!", "Suas configurações foram alteradas")
                                });
                        });
                    }


                }} />
            </Dialog.Container>




            <Dialog.Container visible={altGua}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Alterar guardião</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Você tem certeza que deseja alterar seu guardião por esse usuário?
                </Dialog.Description>
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setAltGua(false);
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Alterar" onPress={() => {



                    const storageUtils = new AsyncStorageUtils();

                        storageUtils.get("token").then(token => {
                            const calls = new Calls();

                            calls.privateCall("usuarios",
                                {
                                    'guardiaoId': guaId
                                }
                                , "put", token)
                                .then(response => {
                                    if(usuario!.id != guaId){
                                        Alert.alert("Guardião definido", "Seu guardião foi definido.");
                                    }else{
                                        Alert.alert("Erro", "Você não pode se definir como seu próprio guardião.");
                                    }
                                    setAltGua(false);
                                })
                                .catch(err => {
                                    Alert.alert("Erro", "Não foi possível alterar seu guardião, tente novamente.");
                                    setAltGua(false);
                                }
                                );
                        })


                    
                }} />
            </Dialog.Container>


            <View style={styles.rowView}>
                <TouchableWithoutFeedback
                    onPress={() => toggleSendToThisGroup()}
                >
                    <Text style={styles.switchText}>Enviar notificações para esse grupo?</Text>
                </TouchableWithoutFeedback>
                <Switch
                    style={styles.defaultSwitch}
                    value={sendToThisGroup}
                    onValueChange={() => {
                        toggleSendToThisGroup();
                        setAltOpt(true);
                    }}
                    color={"#264e34"}
                />

            </View>

            <Text style={styles.title}>Integrantes do grupo</Text>


            <FlatList
                style={styles.flatList}
                data={group?.usuarios}
                keyExtractor={(item, index) => index.toString()}
                renderItem={(obj: any) => {
                    return (
                        <UserProfile item={obj.item} />
                    );
                }}
            />


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
    )
}