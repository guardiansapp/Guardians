import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#efeee9',
        alignItems: 'center',
        width: "100%"
    },
    background: {
        position: "absolute",
        resizeMode: 'stretch',
        width: "100%",
        height: "100%",
        zIndex: -1
    },
    input: {
        height: 40,
        width: 240,
        margin: 3,
        borderRadius: 5,
        borderWidth: 1,
        padding: 10,
        backgroundColor: "white",
        borderColor: "#264e34",
    },
    disabledColor: {
        color: "#666666"
    },
    button: {
        marginTop: 15,
        backgroundColor: "#264e34",
        borderRadius: 5,
        margin: 1,
    },
    textTitle :{
        color: "#022217",
        fontSize: 24,
        fontWeight : "bold",
        margin: 20,
        marginTop: 30,
        marginBottom:20,
        textAlign: "center"
    },
    text: {
        color: "#022217",
        fontSize: 18,
        marginTop: 15,
        fontWeight : "bold",
    },
    buttonText : {
        margin:10,
        marginLeft: 30,
        marginRight: 30,
        color: "white",
        fontSize: 18
    }

});

export default styles;