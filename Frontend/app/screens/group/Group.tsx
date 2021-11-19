import { StackActions } from '@react-navigation/routers';
import React, { useEffect, useState } from 'react';
import { View, Text, Image, FlatList, Linking, TouchableOpacity, TouchableWithoutFeedback, Alert } from 'react-native';
import Calls from '../../apis/guardians/Calls';
import Menu from '../../components/menu/Menu';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import Dialog from "react-native-dialog";

import styles from './style';

interface Data {
    "notificacao_id": number,
    "timestamp": string,
    "mensagem": string,
    "latitude": number,
    "longitude": number,
    "usuario_nome": string,
    "grupos_id": string,
    "grupo_nome": string,
    "recebida": boolean
};

export default function Group({ navigation, route }: any) {

    const groupId = route.params.id;
    const groupName = route.params.name;

    const [exitDialog, setExitDialog] = useState(false);

    const [addUserDialog, setAddUserDialog] = useState(false);
    const [userId, setUserId] = useState('');

    const [data, setData] = useState<Data[]>();

    React.useLayoutEffect(() => {
        navigation.setOptions({
            title: groupName,
        });
    }, [navigation]);

    const storageUtils = new AsyncStorageUtils();

    useEffect(() => {

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("notificacoes/all/grupos", {}, "get", token)
                .then((response) => {
                    const temp: Data[] = [];
                    response.data.forEach((element: Data) => {
                        if (element.grupos_id == groupId)
                            temp.push(element);
                    });
                    setData(temp);
                });
        });

    }, []);

    useEffect(() => {
        const calls = new Calls();
        storageUtils.get("token").then(token => {
            try{
                for(let i = 0; i < data!.length; i ++){
                    if(data![i].recebida == false){
                        calls.privateCall("notificacoes/confirma/grupos?notificacaoId=" + data![i].notificacao_id + "&grupoId=" + groupId, {}, "post", token).catch((e) => {
                            console.log(e.response.data);
                        });
                    }
                }
            }catch(e){}
        })        
    }, [data]);

    const timeStampToString = (timestamp: string) => {

        let dts = timestamp.split('T');
        let dt = dts[0].split('-');
        let hr = dts[1].split(':');

        return dt[2] + "/" + dt[1] + "/" + dt[0] + " " + hr[0] + ":" + hr[1];
    }

    const Notificacao = (item: any) => {
        item = item.item;

        return (

            <TouchableOpacity
                style={[styles.notification, item.recebida != true ? styles.new : {}]}
                onPress={() => {
                    let url = 'https://www.google.com.br/maps/place/' + item.latitude + ',' + item.longitude;

                    if (item.latitude != null && item.longitude != null)
                        Linking.openURL(url);
                }}
            >
                <View
                    style={styles.pinPosition}
                >
                    <Image
                        source={require('../../../assets/pinIcon.png')}
                        style={[styles.pinIcon, (item.latitude == null && item.longitude == null) ? styles.invisible : {}]}
                    ></Image>
                </View>

                <Text style={styles.name}>{item.usuario_nome}</Text>
                <Text style={styles.message}>{item.mensagem}</Text>
                <View style={styles.subContainer}>
                    <Text style={styles.location}>{(item.latitude == null && item.longitude == null) ? '' : '(' + item.latitude + ', ' + item.longitude + ')'}</Text>
                    <Text style={styles.timestamp}>{timeStampToString(item.timestamp)}</Text>
                </View>
            </TouchableOpacity>
        );
    }

    return (



        <View style={styles.container}>

            <Dialog.Container visible={exitDialog}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Sair do grupo</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Você tem certeza que deseja sair do grupo?
                </Dialog.Description>
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setExitDialog(false);
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Sair" onPress={() => {
                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();
                        calls.privateCall("grupos/sair/" + groupId, {}, "delete", token).then(() => {
                            navigation.dispatch(
                                StackActions.popToTop()
                            );
                        });
                    });
                }} />
            </Dialog.Container>

            <Dialog.Container visible={addUserDialog}>
                <Dialog.Title style={{ fontSize: 22, color: "#022217" }}>Novo membro.</Dialog.Title>
                <Dialog.Description style={{ fontSize: 16 }}>
                    Entre com o Id do usuário que você deseja adicionar
                </Dialog.Description>
                
                <Dialog.Input placeholder={"Id do usuário"} keyboardType={"numeric"} style={{ fontSize: 17 }} value={userId} onChangeText={(text) => setUserId(text)} />

                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setAddUserDialog(false);
                    setUserId('');

                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Adicionar" onPress={() => {
                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();
                        calls.privateCall("grupos/entrar/" + groupId + "?userId=" + userId, {}, "post", token)
                        .then(() => {
                            Alert.alert("Usuário adicionado", "O usuário foi adicionado no grupo.");
                            setAddUserDialog(false);
                            setUserId('');
                        }).catch(() => {
                            Alert.alert("Erro", "Usuário já é um membro do grupo ou o Id está inválido.");
                            setAddUserDialog(false);
                            setUserId('');
                        });
                    });
                }} />
            </Dialog.Container>

            <View
                style={styles.header}
            >
                <TouchableWithoutFeedback
                    onPress={() => {
                        navigation.navigate('groupInfo', {
                            'id': groupId,
                            'name': groupName
                        });
                    }}
                >
                    <View
                        style={styles.headerFields}
                    >
                        <Text style={styles.users}>
                            Informações e permissões do grupo
                        </Text>
                    </View>
                </TouchableWithoutFeedback>

                <Text style={styles.notificationText}>
                    Histórico de notificações do grupo
                </Text>

            </View>

            <FlatList
                style={styles.flatlist}
                data={data}
                keyExtractor={(item, index) => index.toString()}
                renderItem={(obj: any) => {
                    return (
                        <Notificacao item={obj.item} />
                    );
                }}
            />

            <TouchableOpacity
                style={[styles.button, styles.leaveButton]}
                onPress={() => {
                    setExitDialog(true);
                }}
            >
                <Image source={require('../../../assets/leaveIcon.png')} style={styles.leaveIcon}></Image>
            </TouchableOpacity>

            <TouchableOpacity
                style={[styles.button, styles.addButton]}
                onPress={() => {
                    setAddUserDialog(true);
                }}
            >
                <Image source={require('../../../assets/addUserGroup.png')} style={styles.leaveIcon}></Image>
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