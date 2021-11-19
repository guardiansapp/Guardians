import { BackHandler, StyleSheet } from "react-native";
import { white } from "react-native-paper/lib/typescript/styles/colors";

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
        zIndex: -2
    },
    rowView :{
        display: 'flex',
        flexDirection: 'row',
        width: '100%',
        justifyContent: 'space-between',
        paddingHorizontal: 30,
        marginVertical: 20
    },
    switchText: {
        alignSelf: 'flex-start',
        color: "#264e34"
    },
    defaultSwitch: {
        alignSelf: 'flex-end'
    },
    sectionTitle : {
        marginVertical: 20,
        color: '#022217',
        fontWeight: 'bold',
        fontSize: 21,
        textAlign: 'center'
    },
    buttonView: {
        display: 'flex',
        flexDirection: 'row',
        width: '100%',
        justifyContent: 'center',
        marginBottom: 13
    },
    mainButton: {
        margin: 10,
        backgroundColor: '#264e34',
        borderRadius: 5,
        borderColor: '#264e34',
        borderWidth: 2
    },
    secondaryButton : {
        margin: 10,
        backgroundColor: '#efeee9',
        borderRadius: 5,
        borderColor: '#264e34',
        borderWidth: 2
    },
    textButton : {
        paddingHorizontal: 20,
        paddingVertical: 10,
        color: "#264e34"
    },
    textButtonMain : {
        paddingHorizontal: 20,
        paddingVertical: 10,
        color: 'white'
    },
    input: {
        fontSize: 15,
        padding: 7,
        width: '90%',
        backgroundColor: 'white',
        borderRadius: 5,
        borderStyle: 'solid',
        borderWidth: 1,
        borderColor: '#022217',
        color: '#264e34',
        marginBottom: 14,
        alignSelf: 'center'
    }
});

export default styles;