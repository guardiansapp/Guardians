import React, { useState } from "react";
import { ImageBackground, View, Image, Text, TextInput, TouchableOpacity, Linking, StyleSheet, Alert } from "react-native";
import { ScrollView } from "react-native-gesture-handler";
import { TextInputMask } from "react-native-masked-text";

import styles from './styles';

export default function Signin1Screen({ navigation }: { navigation: any }) {

    const [tel, setTel] = useState('');

    const [nome, setNome] = useState('');
    const [sobrenome, setSobrenome] = useState('');
    const [dtNasc, setDtNasc] = useState('');

    const [email, setEmail] = useState('');

    return (
        <View style={styles.container}>

            <ScrollView>
                <Text style={styles.textTitle}>Olá, precisamos das informações abaixo para prosseguirmos com o seu cadastro.</Text>

                <Text style={styles.imputText}>Nome</Text>
                <TextInput
                    style={styles.input}
                    value={nome}
                    onChangeText={(text) => setNome(text)}
                />
                <Text style={styles.imputText}>Sobrenome</Text>
                <TextInput
                    style={styles.input}
                    value={sobrenome}
                    onChangeText={(text) => setSobrenome(text)}
                />
                <Text style={styles.imputText}>Data de nascimento</Text>
                <TextInput
                    style={styles.input}
                    value={dtNasc}
                    placeholder={"AAAA"}
                    onChangeText={(text) => setDtNasc(text)}
                />

                <Text style={styles.imputText}>Número de celular</Text>
                <TextInputMask
                    style={styles.input}
                    type={'cel-phone'}
                    options={{
                        maskType: 'BRL'
                    }}
                    value={tel}
                    onChangeText={text => setTel(text)}
                    placeholder={"(DDD) 00000-0000"}
                    keyboardType={"numeric"}
                />

                <Text style={styles.imputText}>E-mail</Text>
                <TextInput
                    style={styles.input}
                    placeholder={"usuario@email.com"}
                    value={email}
                    onChangeText={(text) => setEmail(text)}
                />


                <TouchableOpacity
                    style={styles.button}
                    onPress={() => {

                        if (nome.length > 0 && sobrenome.length > 0 && email.length > 0 && tel.length > 0 && dtNasc.length > 0) {
                            let tempTel = tel.replace("(", "");
                            tempTel = tempTel.replace(") ", "");
                            tempTel = tempTel.replace("-", "");

                            let tempNome = nome[0].toUpperCase() + nome.substring(1, nome.length);
                            let tempSobre = sobrenome[0].toUpperCase() + sobrenome.substring(1, sobrenome.length);

                            navigation.navigate('signin2', {
                                "nome": tempNome,
                                "sobrenome": tempSobre,
                                "email": email,
                                "telefone": tempTel,
                                "dt": dtNasc
                            });
                        } else {
                            Alert.alert("Erro", "Preencha todos os campos antes de continuar com o cadastro");
                        }

                    }}
                >
                    <Text style={styles.buttonText}>AVANÇAR</Text>
                </TouchableOpacity>
            </ScrollView>

        </View>
    );
}