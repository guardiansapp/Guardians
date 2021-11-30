import React, { useEffect, useRef, useState } from 'react';
import { View, StyleSheet, Text, Image, TouchableOpacity, Alert } from "react-native";
import Menu from '../../components/menu/Menu'
import Calls from '../../apis/guardians/Calls';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import { StackActions } from '@react-navigation/native';

import * as Location from 'expo-location';

import Constants from "expo-constants";
import * as Notifications from "expo-notifications";
import { Platform } from "react-native";
import { Subscription } from '@unimodules/react-native-adapter';

export default function Home({ navigation }: { navigation: any }) {

    const [nome, setNome] = useState('');
    const [id, setId] = useState<number>();

    const [expoPushToken, setExpoPushToken] = useState<string>();

    const responseListener = useRef<Subscription>();


    useEffect(() => {

        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("usuarios", {}, "get", token)
                .then(response => {
                    setNome(response.data.nome);
                    setId(response.data.id);
                })
                .catch(err => {
                    calls.handle403Error().then(() => {
                        navigation.dispatch(
                            StackActions.replace('login')
                        );
                    })
                });
        }
        );

        registerForPushNotificationsAsync().then(
            (token) => {
                setExpoPushToken(token)
            }
        );

        responseListener.current = Notifications.addNotificationResponseReceivedListener(response => {
            console.log("9999 gold");
        });

    }, []);

    useEffect(() => {


        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("usuarios/notificationtoken", 
                {
                    "token" : expoPushToken
                }, "post", token)
                .catch(err => {
                    calls.handle403Error().then(() => {
                        navigation.dispatch(
                            StackActions.replace('login')
                        );
                    });
                });
        }
        );

    }, [expoPushToken]);

    return (
        <View style={styles.container}>

            {/* <ImageBackground source={require("../../../assets/backAbstract.jpg")} style={styles.background}>
            </ImageBackground> */}

            <Text
                style={styles.welcomeText}
            >Olá {nome}, {'\n'}Você é o usuário {id}</Text>
            <TouchableOpacity
                onPress={() => {


                    const storageUtils = new AsyncStorageUtils();
                    storageUtils.get("token").then(token => {
                        const calls = new Calls();

                        Location.installWebGeolocationPolyfill();
                        navigator.geolocation.getCurrentPosition(
                            pos => {
                                let lat = pos.coords.latitude;
                                let lon = pos.coords.longitude;

                                calls.privateCall("notificacoes",
                                    {
                                        "latitude": lat,
                                        "longitude": lon
                                    }
                                    , "post"
                                    , token)
                                    .then(response => {
                                        Alert.alert("Mensagem enviada!", "Todos os grupos com permissão de recebimento de mensagens e seu guardião receberam sua mensagem.");
                                    })
                                    .catch(err => {
                                        alert(err);
                                    });
                            }
                        );

                    });

                }
                }
                style={styles.buttonRound}
            >
                <Image source={require('../../../assets/airplane.png')} style={styles.buttonImg}></Image>
                <Text style={styles.textInsideButton}>CLIQUE AQUI PARA ENVIAR</Text>
                <Text style={styles.textInsideButton}>A NOTIFICAÇÃO</Text>
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


async function sendPushNotification(expoPushToken: any) {

    const message = {
        to: expoPushToken,
        sound: "default",
        title: "Remoto via fetch",
        body: "And here is the body!",
        data: { data: "goes here" },
    };

    await fetch("https://exp.host/--/api/v2/push/send", {
        method: "POST",
        headers: {
            Accept: "application/json",
            "Accept-encoding": "gzip, deflate",
            "Content-Type": "application/json",
        },
        body: JSON.stringify(message),
    });
}

Notifications.setNotificationHandler({
    handleNotification: async () => ({
        shouldShowAlert: true,
        shouldPlaySound: true,
        shouldSetBadge: false,
    }),
});


async function registerForPushNotificationsAsync() {
    let token;
    if (Constants.isDevice) {
        const { status: existingStatus } = await Notifications.getPermissionsAsync();
        let finalStatus = existingStatus;
        if (existingStatus !== 'granted') {
            const { status } = await Notifications.requestPermissionsAsync();
            finalStatus = status;
        }
        if (finalStatus !== 'granted') {
            alert('Failed to get push token for push notification!');
            return;
        }
        token = (await Notifications.getExpoPushTokenAsync()).data;
        console.log(token);
    } else {
        alert('Must use physical device for Push Notifications');
    }

    if (Platform.OS === 'android') {
        Notifications.setNotificationChannelAsync('default', {
            name: 'default',
            importance: Notifications.AndroidImportance.MAX,
            vibrationPattern: [0, 250, 250, 250],
            lightColor: '#FF231F7C',
        });
    }

    return token;
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#efeee9',
        alignItems: 'center',
        justifyContent: 'center',
    },
    background: {
        position: "absolute",
        resizeMode: 'stretch',
        width: "100%",
        height: "100%",
        zIndex: -2
    },
    img: {
        width: 45,
        height: 45,
        resizeMode: 'stretch'
    },
    configButton: {
        position: 'absolute',
        top: 45,
        right: 25
    },
    welcomeText: {
        marginBottom: 20,
        fontSize: 32,
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#022217'
    },
    button: {
        marginTop: 15,
        backgroundColor: "white",
        borderWidth: 1,
        borderColor: "#022217",
        borderRadius: 5,
        width: '90%',
        height: 100,
        elevation: 10,
        zIndex: -1

    },
    buttonText: {
        color: "#022217",
        marginLeft: 15,
        marginRight: 15,
        fontSize: 24,
        textAlign: 'center',
        marginTop: 32
    },
    buttonRound: {
        marginTop: 15,
        backgroundColor: "#022217",
        borderWidth: 1,
        borderColor: "#022217",
        borderRadius: 200,
        width: 300,
        height: 300,
        zIndex: -1,
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center'
    },
    buttonImg: {
        width: 100,
        height: 100,
        resizeMode: 'stretch'
    },
    textInsideButton: {
        color:'white',
        fontSize: 17,
        fontWeight: 'bold',
        position: 'relative',
        top: 20
    }

});