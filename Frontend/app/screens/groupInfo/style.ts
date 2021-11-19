import { StyleSheet} from "react-native";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        display: 'flex',
        backgroundColor: '#efeee9',
        alignItems: 'center',
        width: "100%",
        flexDirection: 'column',
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
    row: {
        display: 'flex',
        flexDirection: 'row',
        width: '100%',
        flexWrap: "wrap",
    },
    switchText: {
        alignSelf: 'flex-start',
        color: "#022217"
    },
    defaultSwitch: {
        alignSelf: 'flex-end'
    },
    title: {
        fontWeight: 'bold',
        fontSize: 23,
        color: "#022217"
    },
    strong : {
        fontWeight: 'bold',
        color: "#022217"
    },
    profileText: {
        fontSize: 19,
        color: "#022217"
    },
    flatList: {
        width: '100%',
        display: 'flex',
        
    },
    profileContainer: {
        borderStyle: 'solid',
        borderRadius: 5,
        borderWidth: 1,
        borderColor: "#022217",
        width: '90%',
        margin: 10,
        alignSelf: 'center',
        padding: 5,
        backgroundColor: 'white'
    },
    link: {
        color: "#022217",
        textDecorationLine: 'underline',
        fontSize: 15
    },
    linkTouch: {
        position: 'absolute',
        top: 7,
        right: 10
    }
});

export default styles;