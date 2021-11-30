import React, { useEffect, useRef, useState } from 'react';
import { View, Animated, TouchableWithoutFeedback, Image, Alert, Text } from 'react-native';
import Dialog from "react-native-dialog";
import Calls from '../../apis/guardians/Calls';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';

import styles from './style';


interface props {
    goConfigs: Function,
    goGroups: Function,
    goNotifications: Function,
    goUserInfo: Function
}

export default function Menu(props: props) {

    const [user, setUser] = useState({
        "id" : 0
    });

    const [visible, setVisible] = useState(false);
    const [guardianId, setGuardianId] = useState('');

    const [isOpen, setIsOpen] = useState(false)
    const toggleAnimation = useRef(new Animated.Value(0)).current


    const [toolNotificacoes, setToolNotificacoes] = useState(false);




    const startAnimation = () => {
        const toValue = isOpen ? 0 : 1;
        Animated.timing(toggleAnimation, {
            toValue: toValue,
            duration: 300,
            useNativeDriver: false
        }).start();
        setIsOpen(!isOpen)
    }

    useEffect(() => {

        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("usuarios", {} ,"get", token)
                .then(response => {
                    setUser(response.data);
                })
            }
        );

    }, []);

    return (
        <View
            style={{
                position: "absolute",
                alignItems: "center",
                justifyContent: "center",
                bottom: 10,
                right: 10,
            }}
        >


            <Dialog.Container visible={visible}>
                <Dialog.Title style={{ fontSize: 23, color: "#022217" }}>Adicionar guardião</Dialog.Title>
                <Dialog.Description style={{ fontSize: 17 }}>
                    Para definir um guardião entre com o id dele e confirme. Se você já tiver um guardião ele será substituido.
                </Dialog.Description>
                <Dialog.Input placeholder={"Id do guardião"} keyboardType={"numeric"} style={{ fontSize: 17 }} value={guardianId} onChangeText={(text) => setGuardianId(text)} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Cancelar" onPress={() => {
                    setVisible(false);
                    setGuardianId('');
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Remover" onPress={() => {
                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();
                        calls.privateCall("usuarios",
                            {
                                'guardiaoId': null
                            }
                            , "put", token)
                            .then(response => {
                                setGuardianId('');
                                setVisible(false);
                                Alert.alert("Guardião removido", "Seu guardião foi removido.");
                            })
                            .catch(err => {
                                setGuardianId('');
                                setVisible(false);
                                Alert.alert("Erro", "Não foi possível remover seu guardião, tente novamente.");
                            }
                        );

                    })
                }} />
                <Dialog.Button style={{ fontSize: 17, color: "#264e34" }} label="Adicionar" onPress={() => {

                    const storageUtils = new AsyncStorageUtils();

                    storageUtils.get("token").then(token => {
                        const calls = new Calls();

                        if(guardianId == ''){
                            return;
                        }

                        calls.privateCall("usuarios",
                            {
                                'guardiaoId': guardianId
                            }
                            , "put", token)
                            .then(response => {
                                if(user.id.toString() != guardianId){
                                    Alert.alert("Guardião definido", "Seu guardião foi definido.");
                                    setGuardianId('');
                                    setVisible(false);
                                }else{
                                    Alert.alert("Erro", "Você não pode se definir como seu próprio guardião.");
                                }
                            })
                            .catch(err => {
                                setGuardianId('');
                                setVisible(false);
                                Alert.alert("Erro", "Não foi possível alterar seu guardião, tente novamente.");
                            }
                            );

                    })
                }} />
            </Dialog.Container>

            <TouchableWithoutFeedback onPress={() => {
                props.goUserInfo();
                startAnimation();
            }}>
                <Animated.View
                    style={[
                        {
                            transform: [
                                {
                                    translateY: toggleAnimation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [275, -50]
                                    })
                                }
                            ],
                        },
                        styles.menuItem
                    ]}
                >
                    <Image
                        source={require('../../../assets/userProfile.png')}
                        style={styles.littleIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>

            <TouchableWithoutFeedback onPress={() => {
                setVisible(true);
            }}>
                <Animated.View
                    style={[
                        {
                            transform: [
                                {
                                    translateY: toggleAnimation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [215, -40]
                                    })
                                }
                            ],
                        },
                        styles.menuItem
                    ]}
                >
                    <Image
                        source={require('../../../assets/addGuardianIcon.png')}
                        style={styles.littleIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>

            <TouchableWithoutFeedback onPress={() => {
                props.goConfigs();
                startAnimation();
            }}>
                <Animated.View
                    style={[
                        {
                            transform: [
                                {
                                    translateY: toggleAnimation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [165, -30]
                                    })
                                }
                            ],
                        },
                        styles.menuItem
                    ]}
                >
                    <Image
                        source={require('../../../assets/configWhite.png')}
                        style={styles.littleIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>

            <TouchableWithoutFeedback onPress={() => {
                props.goGroups();
                startAnimation();
            }}>
                <Animated.View
                    style={[
                        {
                            transform: [
                                {
                                    translateY: toggleAnimation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [115, -20]
                                    })
                                }
                            ],
                        },
                        styles.menuItem
                    ]}
                >
                    <Image
                        source={require('../../../assets/groupIcon.png')}
                        style={styles.littleIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>

            <TouchableWithoutFeedback onPress={() => {
                props.goNotifications();
                startAnimation();
            }}
            onLongPress={() => {
                setToolNotificacoes(true);
                setTimeout(() => {
                    setToolNotificacoes(false);
                }, 1500);
            }}>
                <Animated.View
                    style={[
                        {
                            transform: [
                                {
                                    translateY: toggleAnimation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [55, -10]
                                    })
                                }
                            ]
                        },
                        styles.menuItem
                    ]}
                >
                    <Image
                        source={require('../../../assets/bellIcon.png')}
                        style={styles.littleIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>



            <TouchableWithoutFeedback 
            onPress={() => {
                startAnimation();
            }}
            >
                <Animated.View style={{
                    transform: [
                        {
                            rotate: toggleAnimation.interpolate({
                                inputRange: [0, 1],
                                outputRange: ["0deg", "45deg"]
                            })
                        }
                    ],
                    backgroundColor: "#264e34",
                    width: 60,
                    height: 60,
                    borderRadius: 30,
                    justifyContent: 'center',
                    alignItems: 'center'
                }}>
                    <Image
                        source={require('../../../assets/plus.png')}
                        style={styles.bigIcon}
                    ></Image>
                </Animated.View>
            </TouchableWithoutFeedback>
{/* 
            <Text style={
                [ 
                    styles.tool, 
                    {
                        display: 'none'
                    },
                    {
                        position: 'absolute', 
                        bottom:85,
                        right: 70
                    },
                ]
            }>Notificações de guardião</Text> */}


        </View>
    )
}

