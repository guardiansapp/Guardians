import { StyleSheet } from 'react-native'

const styles = StyleSheet.create({

    littleIcon: {
        width: 30,
        height: 30,
        resizeMode: 'stretch'
    },
    bigIcon: {
        width: 25,
        height: 25,
        resizeMode: 'stretch',
    },
    menuItem : {
        backgroundColor: "#264e34",
        width: 54,
        height: 54,
        borderWidth: 3,
        borderColor: "#efeee9",
        borderRadius: 27,
        alignItems: "center",
        justifyContent: "center"
    },
    tool: {
        color: "#264e34",
        fontSize:17,
        backgroundColor: "#efeee9",
        borderRadius: 10,
        width: 250,
        textAlign: 'right'
    }

})

export default styles