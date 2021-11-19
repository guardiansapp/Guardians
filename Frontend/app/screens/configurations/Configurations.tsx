import React, { useEffect, useState } from 'react';
import { View, Text, TouchableWithoutFeedback, TextInput, ScrollView, Alert } from 'react-native';
import Menu from '../../components/menu/Menu';
import { Switch } from 'react-native-paper';

import styles from './style';
import { TouchableOpacity } from 'react-native-gesture-handler';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';
import { StackActions } from '@react-navigation/native';
import Calls from '../../apis/guardians/Calls';

export default function Configurations({ navigation }: { navigation: any }) {
    const [isTelOn, SetIsTelOn] = useState(false);
    const [isAgeOn, SetIsAgeOn] = useState(false);
    const [isAdrOn, SetIsAdrOn] = useState(false);

    const [isPermitedSendToGroupsOn, setIsPermitedSendToGroupsOn] = useState(false);
    const [isPermitedToShareLocationOn, setIsPermitedToShareLocationOn] = useState(false);

    const [menssage, setMenssage] = useState('');

    const toggleTelSwitch = () => SetIsTelOn(!isTelOn);
    const toggleAgeSwitch = () => SetIsAgeOn(!isAgeOn);
    const toggleAdrSwitch = () => SetIsAdrOn(!isAdrOn);

    const togglePSG = () => setIsPermitedSendToGroupsOn(!isPermitedSendToGroupsOn);
    const togglePSL = () => setIsPermitedToShareLocationOn(!isPermitedToShareLocationOn);

    useEffect(() => {

        const storageUtils = new AsyncStorageUtils();

        storageUtils.get("token").then(token => {
            const calls = new Calls();
            calls.privateCall("usuarios", {}, "get", token)
            .then(response => {
               
               const conf = response.data.configuracao;

               SetIsTelOn(conf.telefoneVisivel);
               SetIsAgeOn(conf.idadeVisivel);
               SetIsAdrOn(conf.enderecoVisivel);
               setIsPermitedSendToGroupsOn(conf.enviarParaGrupos);
               setIsPermitedToShareLocationOn(conf.compartilharLocalizacao);
               setMenssage(conf.preMensagem);

            })
            .catch(err => {
                calls.handle403Error().then(() => {
                    alert("VOLTOU SEM TOKEN");
                    navigation.dispatch(
                        StackActions.replace('login')
                    );
                })
            });

        })

    }, []);

    return (

        <View style={styles.container}>
            <ScrollView style={{width: "100%"}}>

                <Text style={styles.sectionTitle}>
                    Visibilidade
                </Text>

                <View style={styles.rowView}>
                    <TouchableWithoutFeedback
                        onPress={() => toggleTelSwitch()}
                    >
                        <Text style={styles.switchText}>Telefone visíveis para outros usuários</Text>
                    </TouchableWithoutFeedback>
                    <Switch
                        style={styles.defaultSwitch}
                        value={isTelOn}
                        onValueChange={toggleTelSwitch}
                        color={'#264e34'}
                    />
                </View>

                <View style={styles.rowView}>
                    <TouchableWithoutFeedback
                        onPress={() => toggleAgeSwitch()}
                    >
                        <Text style={styles.switchText}>Idade visível para outros usuários</Text>
                    </TouchableWithoutFeedback>
                    <Switch
                        style={styles.defaultSwitch}
                        value={isAgeOn}
                        onValueChange={toggleAgeSwitch}
                        color={'#264e34'}
                    />
                </View>

                <View style={styles.rowView}>
                    <TouchableWithoutFeedback
                        onPress={() => toggleAdrSwitch()}
                    >
                        <Text style={styles.switchText}>Endereço visível para outros usuários</Text>
                    </TouchableWithoutFeedback>
                    <Switch
                        style={styles.defaultSwitch}
                        value={isAdrOn}
                        onValueChange={toggleAdrSwitch}
                        color={'#264e34'}
                    />
                </View>

                <Text style={styles.sectionTitle}>
                    Permissões
                </Text>

                <View style={styles.rowView}>
                    <TouchableWithoutFeedback
                        onPress={() => togglePSG()}
                    >
                        <Text style={styles.switchText}>Habilitar envio de mensagens para grupos</Text>
                    </TouchableWithoutFeedback>
                    <Switch
                        style={styles.defaultSwitch}
                        value={isPermitedSendToGroupsOn}
                        onValueChange={togglePSG}
                        color={'#264e34'}
                    />
                </View>

                <View style={styles.rowView}>
                    <TouchableWithoutFeedback
                        onPress={() => togglePSL()}
                    >
                        <Text style={styles.switchText}>Compartilhar localização nas notificações</Text>
                    </TouchableWithoutFeedback>
                    <Switch
                        style={styles.defaultSwitch}
                        value={isPermitedToShareLocationOn}
                        onValueChange={togglePSL}
                        color={'#264e34'}
                    />
                </View>

                <Text style={styles.sectionTitle}>
                    Mensagem pré-definida
                </Text>

                <TextInput
                    style={styles.input}
                    value={menssage}
                    onChangeText={(text => setMenssage(text))}
                    placeholder={"MENSAGEM PRÉ-DEFINIDA"}
                />

                <View style={styles.buttonView}>
                    <TouchableOpacity style={styles.secondaryButton}
                        onPress={() => {
                            const utils = new AsyncStorageUtils();

                            utils.get("token").then(token => {
                                const calls = new Calls();
                                calls.privateCall("usuarios/notificationtoken", {}, "delete", token);
                            }
                            );

                            utils.remove('token');

                            navigation.reset({
                                index: 0,
                                routes: [{name: 'login'}],
                            });
                        }}
                    >
                        <Text style={styles.textButton}>Deslogar</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.mainButton}
                        onPress={() => {
                            const call = new Calls();


                            const storageUtils = new AsyncStorageUtils();

                            storageUtils.get("token").then(token => {
                                const calls = new Calls();
                                calls.privateCall("configuracoes", 
                                {
                                    "preMensagem": menssage,
                                    "telefoneVisivel": isTelOn,
                                    "enderecoVisivel": isAdrOn,
                                    "idadeVisivel": isAgeOn,
                                    "enviarParaGrupos": isPermitedSendToGroupsOn,
                                    "compartilharLocalizacao": isPermitedToShareLocationOn
                                }, 
                                "put", token)
                                .then(() => {
                                    Alert.alert("Salvo!","Suas configurações foram atualizadas.");
                                })
                                .catch(err => {
                                    calls.handle403Error().then(() => {
                                        navigation.dispatch(
                                            StackActions.replace('login')
                                        );
                                    })
                                });
                    
                            })

                        }}
                    >
                        <Text style={[styles.textButtonMain]}>Salvar</Text>
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