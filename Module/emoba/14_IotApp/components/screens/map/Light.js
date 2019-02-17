import React, {Component} from 'react';
import {Image, TouchableWithoutFeedback, View} from 'react-native';
import Images from '@assets/images';

export default class Light extends Component {
    render() {
        let imgSource = Images.LightOff;
        if (this.props.led.status) {
            imgSource = Images.LightOn
        }
        const top = Number(this.props.fromTop);
        const left = Number(this.props.fromLeft);
        return (
            <View style={{position: 'absolute', top: top, left: left}}>
                <TouchableWithoutFeedback onPress={this.onPressButton} underlayColor='grey'>
                    <Image
                        source={imgSource}
                    />
                </TouchableWithoutFeedback>
            </View>
        );
    }

    onPressButton = () => {
        let newState = !this.props.led.status;
        this.props.handleLed({id: this.props.led.id, status: newState})
    }
}
