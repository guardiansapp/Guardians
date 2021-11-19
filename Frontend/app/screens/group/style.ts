import { StyleSheet} from "react-native";

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
    pinIcon: {
        resizeMode: "stretch",
        width: 30,
        height: 30
    },
    notification : {
        margin: 15,
        minWidth:'90%',
        justifyContent: 'center',
        alignSelf: 'center',
        borderColor: '#022217',
        borderRadius: 25,
        borderWidth: 1,
        borderStyle: 'solid',
        backgroundColor: 'white',
        elevation: 10
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
        color: '#264e34',
        fontSize: 13
    },
    flatlist: {
        maxHeight: '75%',
        width: '100%',
        marginTop: 15
    },
    timestamp : {
        fontSize: 16,
        color: "#264e34"
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
        display: 'flex',
        marginTop: 20,
        marginHorizontal: 10,
        width: '100%'
    },
    users : {
        color:'#022217',
        marginBottom: 10,
        textDecorationLine:"underline",
        fontStyle: "italic"
    },
    notificationText: {
        fontWeight: 'bold',
        fontSize: 18,
        textAlign: 'center',
        color: "#022217"
    },
    headerFields: {
        justifyContent: 'center',
        alignItems: 'center',
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
    leaveButton: {
        position: 'absolute',
        bottom: 10,
        left: 10,
    },
    addButton :{
        position: 'absolute',
        bottom: 10,
    },
    new: {
        backgroundColor: "#c7d381"
    },
    leaveIcon: {
        resizeMode: "stretch",
        width: 35, 
        height:35,
    }
});


export default styles;