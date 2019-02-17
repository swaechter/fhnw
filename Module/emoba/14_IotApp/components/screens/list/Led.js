import React, {Component} from 'react';
import {Image, StyleSheet, Text, TouchableWithoutFeedback, View} from 'react-native';
import Images from '@assets/images';

export class LedPlaceHolder extends Component {
    render() {
        return (
            <View style={[styles.led, styles.placeholder]}>
            </View>
        );
    }
}

export class Led extends Component {
    render() {
        let imgSource = Images.LedOff;
        if (this.props.led.status) {
            imgSource = Images.LedOn
        }
        return (
            <View style={styles.led}>
                <TouchableWithoutFeedback onPress={this.onPressButton} underlayColor='grey'>
                    <Image
                        style={styles.image}
                        source={imgSource}
                    />
                </TouchableWithoutFeedback>
                <Text style={{color: 'white', fontWeight: 'bold', paddingBottom: 10}}>{this.props.text}</Text>
            </View>
        );
    }

    onPressButton = () => {
        let newState = !this.props.led.status;
        this.props.handleLed({id: this.props.led.id, status: newState})
    }
}

const styles = StyleSheet.create({
    led: {
        width: '48%',
        alignItems: 'center',
        justifyContent: 'center',
        borderColor: 'lightgrey',
        borderWidth: 1,
        margin: 3,
        backgroundColor: 'grey'
    },
    placeholder: {
        borderWidth: 0,
        backgroundColor: 'lightgrey'
    },
    image: {
        margin: 10,
        width: 80,
        height: 80,
    }
});
