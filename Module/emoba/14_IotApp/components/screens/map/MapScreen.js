import React from 'react';
import {View, StyleSheet, Image, Dimensions} from 'react-native';
import Light from './Light';
import Images from '@assets/images';
import StatusBar from '../../StatusBar';

class MapScreen extends React.Component {
    render() {
        const {height, width} = Dimensions.get('window');
        const cHeight = height / 2;
        const cWidth = width / 2;
        return (
            <View style={styles.container}>
                <StatusBar message={this.props.screenProps.message}/>
                <Image
                    style={{height: height, width: width}}
                    resizeMode='contain'
                    source={Images.Floor}
                />
                <Light led={this.props.screenProps.leds[0]} fromTop={cHeight - 150} fromLeft={cWidth - 100} handleLed={this.props.screenProps.handleLed}/>
                <Light led={this.props.screenProps.leds[1]} fromTop={cHeight - 50} fromLeft={cWidth - 110} handleLed={this.props.screenProps.handleLed}/>
                <Light led={this.props.screenProps.leds[2]} fromTop={cHeight + 70} fromLeft={cWidth - 130} handleLed={this.props.screenProps.handleLed}/>
                <Light led={this.props.screenProps.leds[3]} fromTop={cHeight + 40} fromLeft={cWidth + 90} handleLed={this.props.screenProps.handleLed}/>
                <Light led={this.props.screenProps.leds[4]} fromTop={cHeight - 55} fromLeft={cWidth + 50} handleLed={this.props.screenProps.handleLed}/>
            </View>
        )
    }
}

export default MapScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'lightgrey'
    }
});
