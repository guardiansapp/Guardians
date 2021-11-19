import { StyleSheet} from "react-native";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#efeee9',
        width: "100%",
    },
    header : {
        //borderBottomWidth: 1,
        //borderBottomColor: "#022217",
        //paddingBottom: 7,
        color: "#022217",
        fontSize: 20,
        textAlign: "center",
        width: '100%',
        marginTop: 20,
        marginBottom: 20,
        
        fontWeight: 'bold',
    },
    row : {
        marginTop: 10,
        display: 'flex',
        flexDirection:'row',
        alignItems: 'center',
        justifyContent: 'space-between'
    },
    bold:{
        
        color: "#022217",
        fontSize: 17,
        marginLeft: 15
    },
    input: {
        height: 43,
        width: 200,
        margin: 3,
        borderRadius: 5,
        borderWidth: 1,
        padding: 10,
        backgroundColor: "white",
        borderColor: "#264e34",
        marginRight:15
    },

    mainButton: {
        margin: 10,
        backgroundColor: '#efeee9',
        borderRadius: 5,
        borderColor: '#264e34',
        borderWidth: 2,
        width: 120,
        marginVertical: 40
    },

    textButton : {
        paddingHorizontal: 20,
        paddingVertical: 10,
        color: '#264e34',
        textAlign: 'center'
    },
    buttonView: {
        display: 'flex',
        alignItems: 'center'
    },
    disabledColor: {
        color: "#666666"
    },

});


export default styles;