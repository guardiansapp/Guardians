import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from "react-native";
import Calls from '../../apis/guardians/Calls';

import styles from './style';

export default function ForgotPassword({navigation}: any) {

    const [username, setUsername] = useState('');

    return (
        <View style={styles.container}>



            <Text style={styles.textTitle}>Precisamos que nos informe seu nome de usuário para prosseguir com a recuperação da senha.</Text>
            
            <TextInput
                style={styles.input}
                value={username}
                onChangeText={(text) => setUsername(text)}
                placeholder={"NOME DE USUÁRIO"}
            />
            
            <TouchableOpacity
                style={styles.button}
                onPress={() => {

                    const calls = new Calls();
                    calls.privateCall("acessos/nomeusuario?username=" + username, {}, "get", '')
                    .then(response => {

                        navigation.navigate('forgot2', {
                            "dica1": response.data.dica1,
                            "dica2": response.data.dica2,
                            "nomeUsuario": response.data.nomeUsuario,
                        });
                    })
                    .catch(err => {
                        Alert.alert("Erro!", "O nome de usuário não foi encontrado.");
                    });
            
                }}
            >
                <Text style={styles.buttonText}>AVANÇAR</Text>
            </TouchableOpacity>


        </View>
    );
}