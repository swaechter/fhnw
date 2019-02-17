import React from 'react';
import {StyleSheet, View} from 'react-native';
import {Led, LedPlaceHolder} from './Led';
import StatusBar from '../../StatusBar';

class ListScreen extends React.Component {
    render() {
        return (
            <View style={styles.container}>
                <StatusBar message={this.props.screenProps.message}/>
                <View style={styles.leds}>
                    <Led led={this.props.screenProps.leds[0]} handleLed={this.props.screenProps.handleLed} text='Zimmer I'/>
                    <Led led={this.props.screenProps.leds[1]} handleLed={this.props.screenProps.handleLed} text='Wohnen'/>
                    <Led led={this.props.screenProps.leds[2]} handleLed={this.props.screenProps.handleLed} text='Zimmer II'/>
                    <Led led={this.props.screenProps.leds[3]} handleLed={this.props.screenProps.handleLed} text='Zimmer III'/>
                    <Led led={this.props.screenProps.leds[4]} handleLed={this.props.screenProps.handleLed} text='Diele'/>
                    <LedPlaceHolder/>
                </View>
            </View>
        )
    }
}

export default ListScreen;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column'
    },
    leds: {
        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        backgroundColor: 'lightgrey'
    }
});
