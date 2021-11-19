import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import React, { SetStateAction, useEffect, useRef, useState } from 'react';
import Configurations from './app/screens/configurations/Configurations';
import ForgotPassword from './app/screens/forgetPassword/ForgotPassword';
import PasswordTips from './app/screens/forgetPassword/PasswordTips';
import Group from './app/screens/group/Group';
import GroupInfo from './app/screens/groupInfo/GroupInfo';
import Home from './app/screens/home/Home';
import LoginScreen from './app/screens/login/LoginScreen';
import Signin1Screen from './app/screens/signin/Signin1Screen';
import Signin2Screen from './app/screens/signin/Signin2Screen';
import Signin3Screen from './app/screens/signin/Signin3Screen';
import UserGroups from './app/screens/userGroups/UserGroups';
import UserNotifications from './app/screens/userNotifications/UserNotifications';
import UsuarioInfo from './app/screens/usuarioInfo/UsuarioInfo';




const Stack = createStackNavigator();

export default function App() {

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName={'login'}>
        <Stack.Screen name="login" component={LoginScreen} options={{
          title: "",
          headerTransparent: true,
          animationEnabled: true
        }} />
        <Stack.Screen name="signin1" component={Signin1Screen} options={{
          title: "DADOS PESSOAIS E CONTATO",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />
        <Stack.Screen name="signin2" component={Signin2Screen} options={{
          title: "INFORMAÇÕES ADICIONAIS",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />
        <Stack.Screen name="signin3" component={Signin3Screen} options={{
          title: "CONCLUIR CADASTRO",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="forgot1" component={ForgotPassword} options={{
          title: "ESQUECI MINHA SENHA",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />
        <Stack.Screen name="forgot2" component={PasswordTips} options={{
          title: "ESQUECI MINHA SENHA",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="home" component={Home} options={{
          title: "",
          headerTransparent: true
        }} />

        <Stack.Screen name="groups" component={UserGroups} options={{
          title: "GRUPOS",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="group" component={Group} options={{
          title: "GRUPO",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="config" component={Configurations} options={{
          title: "CONFIGURAÇÕES",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="groupInfo" component={GroupInfo} options={{
          title: "GRUPO",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="notification" component={UserNotifications} options={{
          title: "NOTIFICAÇÕES",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

        <Stack.Screen name="userInfo" component={UsuarioInfo} options={{
          title: "INFORMAÇÕES",
          headerStyle: {
            backgroundColor: '#264e34',
          },
          headerTintColor: 'white',
          animationEnabled: true
        }} />

      </Stack.Navigator>
    </NavigationContainer>
  );
}

