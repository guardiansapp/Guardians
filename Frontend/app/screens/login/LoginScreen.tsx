import React, { useEffect } from "react";
import { View, Image, Text, TextInput, TouchableOpacity, Linking, Alert } from "react-native";
import { useState } from 'react';
import styles from './style';
import TokenHandler from "../../apis/guardians/TokenHandler";
import AsyncStorageUtils from "../../util/AsyncStorageUtils";
import { StackActions } from "@react-navigation/native";
import Calls from "../../apis/guardians/Calls";

export default function LoginScreen({ navigation }: { navigation: any }) {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const [loading, setLoading] = useState(false);

  const refreshToken = () => {
    const storageUtils = new AsyncStorageUtils();
    storageUtils.get("token").then(token => {
      const calls = new Calls();
      calls.privateCall("login/refresh", {}, "post", token)
        .then(response => {
          storageUtils.set("token", response.data.Authorization);
          console.log(response.data.Authorization);
        })
        .catch(err => {
          calls.handle403Error().then(() => {
            navigation.dispatch(
              StackActions.replace('login')
            );
          }
          )
        }
        );
    }
    )
  }

  useEffect(() => {
    const utils = new AsyncStorageUtils();
    utils.get('token').then(token => {
      if (token != null) {
        refreshToken();
        navigation.dispatch(
          StackActions.replace('home', {
            user: 'login',
          })
        );
      }
    })

  }, []);

  return (
    <View style={styles.container}>

      <Image
        style={styles.img}
        source={require('../../../assets/logo.png')}
      />

      <TextInput
        style={styles.input}
        placeholder={"LOGIN"}
        value={username}
        onChangeText={(text) => setUsername(text)}
      />
      <TextInput
        style={styles.input}
        secureTextEntry={true}
        placeholder={"SENHA"}
        value={password}
        onChangeText={(text) => setPassword(text)}
      />

      <View style={styles.buttonContainer}>
        <TouchableOpacity
          style={styles.button}
          onPress={() => {
            setLoading(true);
            const handler = new TokenHandler();
            handler.getToken(username, password).then(response => {
              const utils = new AsyncStorageUtils();
              utils.set("token", response.data.Authorization);

              navigation.dispatch(
                StackActions.replace('home', {
                  user: 'login',
                })
              );

            }).catch(err => {
              Alert.alert("Usuário inválido", "Tente novamente, caso tenha esquecido a senha considere utilizar a opção 'esqueci minha senha'");
              setLoading(false);
            });

          }
          }
        >
          <Text
            style={styles.loginText}
          >ENTRAR</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate('signin1')}
        >
          <Text
            style={styles.noAccessText}
          >CADASTRAR</Text>
        </TouchableOpacity>
      </View>

      <TouchableOpacity
        style={styles.imgButton}
        onPress={() => Linking.openURL('https://leonardopinheirolacerda.github.io/Guardians-site/')}>
        <Image
          source={require('../../../assets/LinkWeb.png')}
          style={styles.webLink}
        />
      </TouchableOpacity>

      <View>
        <TouchableOpacity
          style={styles.forgotPassword}
          onPress={() => navigation.navigate('forgot1')}
        >
          <Text
            style={styles.forgotPasswordText}
          >ESQUECI MINHA SENHA</Text>
        </TouchableOpacity>
      </View>

      <Image
        style={[!loading ? { opacity: 0 } : {}, styles.loadingGif]}
        source={require('../../../assets/loading.gif')}
      />

    </View>

  );
}
