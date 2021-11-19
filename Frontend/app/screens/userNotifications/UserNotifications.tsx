import React, { useEffect, useState } from "react";
import { FlatList, Linking, TouchableOpacity, View, Image, Text, ImageBackground, Alert } from "react-native";
import Calls from "../../apis/guardians/Calls";
import Menu from "../../components/menu/Menu";
import AsyncStorageUtils from "../../util/AsyncStorageUtils";

import styles from './style';

interface Notificacao{
    "latitude": number,
    "longitude": number,
    "mensagem": string,
    "notificacao_id": number,
    "recebida": boolean,
    "timestamp": string,
    "usuario_nome": string,
}

export default function UserNotifications({ navigation }: any) {


    const [data, setData] = useState<Notificacao[]>();
    const storageUtils = new AsyncStorageUtils();

    useEffect(() => {

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("notificacoes/all/guardioes", {}, "get", token).then(response => {
                setData(response.data);
            })
        })

    }, []);

    useEffect(() => {
        const calls = new Calls();
        storageUtils.get("token").then(token => {
            try{
                for(let i = 0; i < data!.length; i ++){
                    if(data![i].recebida == false){
                        calls.privateCall("notificacoes/confirma/guardioes?notificacaoId=" + data![i].notificacao_id, {}, "post", token).catch((e) => {
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
                style={[styles.notification, item.recebida == false ? styles.new : {}]}
                onPress={() => {
                    if (item.latitude != null && item.longitude != null){
                        let url = 'https://www.google.com.br/maps/place/' + item.latitude + ',' + item.longitude;
                        Linking.openURL(url);
                    }else{
                        console.log("Localização não compartilhada nessa mensagem.");
                    }
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

            <Text style={styles.header}>Notificações recebidas como guardião</Text>

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

