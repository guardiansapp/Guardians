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
    invisible: {
        opacity: 0
    },
    timestamp : {
        color: '#022217',
    },
    pinIcon: {
        resizeMode: "stretch",
        width: 30,
        height: 30
    },
    notification : {
        marginTop: 30,
        minWidth:'85%',
        justifyContent: 'center',
        alignSelf: 'center',
        borderColor: '#022217',
        borderRadius: 25,
        borderWidth: 1,
        borderStyle: 'solid',
        backgroundColor: 'white'
    },
    new: {
        backgroundColor: "#c7d381"
    },
    name : {
        fontWeight: 'bold',
        fontSize: 18,
        marginLeft: 15,
        color: '#022217',
    },
    message: {
        fontSize: 16,
        marginVertical: 10,
        marginHorizontal: 30,
        color: '#022217',
    },
    subContainer: {
        flexDirection: 'row',
        justifyContent: "space-between",
        marginHorizontal: 15,
        marginBottom: 5
    },
    location: {
        color: '#022217',
        fontSize: 13
    },
    flatlist: {
        maxHeight: '80%',
        width: '100%',
        marginTop: 15,
    },
  
    pinPosition : {      
        width: 30,
        height: 30,
        position: 'absolute',
        top: 0,
        right: 0,
        zIndex: 2
    },
    header : {
        fontWeight: 'bold',
        fontSize: 20,
        marginVertical: 10,
        marginTop: 19,
        textAlign: 'center',
        color: '#022217',
    }

  
 
});

export default styles;