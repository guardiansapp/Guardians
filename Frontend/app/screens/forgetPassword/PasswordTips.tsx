
import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from "react-native";
import TokenHandler from '../../apis/guardians/TokenHandler';
import AsyncStorageUtils from '../../util/AsyncStorageUtils';

import styles from './style'

export default function PasswordTips({ navigation, route }: any) {

    const [dica1, setDica1] = useState(route.params.dica1);
    const [dica2, setDica2] = useState(route.params.dica2);
    const [nomeUsuario, setNomeUsuario] = useState(route.params.nomeUsuario);

    const [password, setPassword] = useState('');

    return (
        <View style={styles.container}>

            <Text style={styles.textTitle}>Dicas inseridas no cadastro:</Text>

            <Text style={styles.tip}>{dica1}</Text>
            <Text style={styles.tip}>{dica2}</Text>

            <Text style={styles.textTitle}>Lembrou da senha?</Text>

            <TextInput
                style={styles.input}
                secureTextEntry={true}
                placeholder={"SENHA"}
                value={password}
                onChangeText={(text) => setPassword(text)}
            />

            <TouchableOpacity
                style={styles.button}
                onPress={() => {

                    const handler = new TokenHandler();
                    handler.getToken(nomeUsuario, password).then(response => {
                        const utils = new AsyncStorageUtils();
                        utils.set("token", response.data.Authorization);
                        
                        navigation.reset({
                            index: 0,
                            routes: [{name: 'home'}],
                        });

                    }).catch(err => {
                        Alert.alert("Usuário inválido", "Senha incorreta, tente novamente.");
                    });

                }}
            >
                <Text style={styles.buttonText}>ENTRAR</Text>
            </TouchableOpacity>

        </View>
    );

}