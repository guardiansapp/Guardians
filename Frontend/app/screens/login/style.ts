import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
    container:{
      flex:1,
      backgroundColor: '#efeee9',
      alignItems: 'center',
      width: "100%"
    },
    buttonContainer : {
      display : 'flex',
      flexWrap: 'wrap', 
      alignItems: 'flex-start',
      flexDirection:'row',
    },
    loginText: {
      color: '#efeee9',
      marginLeft:29,
      marginRight:29,
      marginTop:5,
      marginBottom: 5
    },
    noAccessText: {
      color: '#efeee9',
      marginLeft:9,
      marginRight:9,
      marginTop:5,
      marginBottom: 5
    },
    button :{
      marginTop: 3,
      backgroundColor: "#264e34",
      borderRadius: 5,
      borderWidth: 1,
      borderColor: "white",
      margin: 1
    },
    titulo:{
      fontSize: 28,
      color: '#264e34',
      fontWeight: 'bold',
    },
    img: {
      marginTop:120,
      marginBottom: 10,
      width: 195,
      height: 125,
      resizeMode: 'stretch'
    },
    webLink: {
      width: 50,
      height: 50,
      resizeMode: 'stretch',
    },
    imgButton : {
      position: "absolute",
      bottom: 20,
      right: 20
    },
    input: {
      height: 40,
      width: 240,
      margin: 3,
      borderRadius: 5,
      borderWidth: 1,
      padding: 10,
      backgroundColor: "white",
      borderColor: "#022217",
      color: "#022217"
    },
    background : {
      position: "absolute",
      resizeMode: 'stretch',
      width: "100%",
      height: "100%",
      zIndex: -1
    },
    forgotPassword : {
      marginTop: 3,
      backgroundColor: "white",
      borderWidth: 1,
      borderColor: "#264e34",
      borderRadius: 5
    },
    forgotPasswordText : {
      color: "#264e34",
      marginLeft:15,
      marginRight:15,
      marginTop:5,
      marginBottom: 5
    },
    loadingGif : {
      resizeMode: 'stretch', 
      width: 60, 
      height: 60,
      marginTop: 10
    }
});

export default styles;