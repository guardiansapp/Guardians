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
        zIndex: -2
    },
    groupName: {
        fontWeight: 'bold',
        fontSize: 16,
        color: '#022217'
    },
    group : {
        borderColor: '#022217',
        borderStyle: "solid",
        borderWidth: 1,
        borderRadius: 5,
        width: '80%',
        marginTop: 20,
        padding: 10,
        backgroundColor: '#FFF',
        elevation: 10,
        justifyContent: 'center',
        alignSelf: 'center'
    },
    qtdNotifications:{
        fontSize: 14,
        textAlign: 'center',
        color: '#264e34'
    },
    qtd: {
        fontWeight: 'bold',
    },
    littleBall : {
        width: 32,
        height: 32,
        borderRadius: 16,
        backgroundColor: '#264e34',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'absolute',
        top: -16,
        right: -16
    },
    littleBallsText : {
        color: 'white'
    }, 
    flat: {
        width: '100%',
        maxHeight: '90%',
    },
    button: {
        width: 60,
        height: 60,
        borderRadius: 30,
        borderWidth: 0,
        color: 'white',
        backgroundColor: '#264e34', 
        justifyContent: 'center',
        alignItems: 'center'    
    },
    addButton :{
        position: 'absolute',
        bottom: 10,
        left: 10
    },
    addIcon: {
        resizeMode: "stretch",
        width: 35, 
        height:35,
    }
});

export default styles;