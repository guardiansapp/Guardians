import AsyncStorage from '@react-native-async-storage/async-storage';

export default class AsyncStorageUtils {

    set = (key: string, value: any) => {
        AsyncStorage.setItem(key,value);
    };

    get = async (key: string) => {
        const value = await AsyncStorage.getItem(key); 
        return value;      
    };

    clear = async () => {
        AsyncStorage.clear();
    };

    remove = async (key: string) => {
        AsyncStorage.removeItem(key);
    }

}