import React from 'react';
import {Platform, StyleSheet, Text} from 'react-native';

const StatusBar = ({message}) => {
    return (
        <Text style={styles.message}>{message}</Text>
    );
}

const styles = StyleSheet.create({
    message: {
        marginTop: Platform.OS === 'ios' ? 20 : 0,
        paddingTop: 10,
        paddingLeft: 10,
        fontSize: 14,
        color: 'white',
        backgroundColor: '#666666',
        height: 45
    }
});

export default StatusBar;
