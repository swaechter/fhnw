import React from 'react';
import {AppState, Platform, StatusBar} from 'react-native';
import ListScreen from './components/screens/list/ListScreen';
import MapScreen from './components/screens/map/MapScreen';
import {createBottomTabNavigator, createMaterialTopTabNavigator} from 'react-navigation';
import {MaterialIcons} from '@expo/vector-icons';
import {AppLoading} from 'expo';

if (Platform.OS === 'ios') {
    MainNavigation = createBottomTabNavigator(
        {
            List: {
                screen: ListScreen,
                navigationOptions: {
                    name: 'List',
                    tabBarIcon: ({focused}) => (
                        <MaterialIcons
                            name={'view-list'}
                            size={26}
                            style={{color: focused ? 'white' : '#949494'}}
                        />
                    ),
                }
            },
            Map: {
                screen: MapScreen,
                navigationOptions: {
                    name: 'Map',
                    tabBarIcon: ({focused}) => (
                        <MaterialIcons
                            name={'map'}
                            size={26}
                            style={{color: focused ? 'white' : '#949494'}}
                        />
                    ),
                },
            },
        },
        {
            tabBarOptions: {
                activeTintColor: 'white',
                inactiveTintColor: 'darkgrey',
                activeBackgroundColor: '#666666',
                labelStyle: {
                    fontSize: 14,
                    fontWeight: 'bold'
                },
                style: {
                    backgroundColor: 'grey'
                },
            },
            lazy: false
        }
    );
} else {
    MainNavigation = createMaterialTopTabNavigator(
        {
            List: {
                screen: ListScreen,
                navigationOptions: {
                    name: 'List'
                }
            },
            Map: {
                screen: MapScreen,
                navigationOptions: {
                    name: 'Map'
                },
            },
        },
        {
            tabBarOptions: {
                activeTintColor: 'white',
                inactiveTintColor: 'darkgrey',
                activeBackgroundColor: '#666666',
                labelStyle: {
                    fontSize: 14,
                    fontWeight: 'bold'
                },
                style: {
                    backgroundColor: 'grey',
                    paddingTop: StatusBar.currentHeight
                },
            },
            lazy: false
        }
    );
}

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            appState: AppState.currentState,
            leds: props.leds,
            isLoadingFromServer: false,
            message: ' '
        }
    }

    componentDidMount() {
        console.log('in componentDidMount: ' + this.state.appState);
        if (this.state.appState === 'active') {
            this.handleAppStateChange(this.state.appState);
        }
        AppState.addEventListener('change', this.handleAppStateChange);
    }

    componentWillUnMount() {
        if (this.websocket) {
            this.websocket.close();
        }
    }

    /*
     * Handles state changes, e.g. close websocket if app goes into background.
     */
    handleAppStateChange = (nextAppState) => {
        console.log('State from ' + this.state.appState + " to " + nextAppState);
        if (nextAppState === 'active') {
            this.connectOverWebSocket();
            this.loadFromServer();
        } else {
            this.websocket.close();
        }
        this.setState({
            appState: nextAppState
        })
    }

    render() {
        // var comp = <MapScreen leds={this.state.leds} handleLed={this.changeLedState}/>
        //var comp = <ListScreen leds={this.state.leds} handleLed={this.changeLedState}/>
        var comp;
        if (this.state.isLoadingFromServer) {
            comp = <AppLoading/>
        } else {
            comp = <MainNavigation screenProps={{
                leds: this.state.leds,
                handleLed: this.changeLedState,
                message: this.state.message
            }}/>
        }
        return (comp)
    }

    changeLedState = (led) => {
        this.setState({
            message: 'Updating...'
        })
        let url = webserviceUrl + led.id;
        fetch(url, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                status: led.status,
            })
        }).then(() => {
            this.setState({
                message: ' '
            })
        })
    }

    connectOverWebSocket() {
        console.log('trying to open websocket ' + websocketUrl);
        this.websocket = new WebSocket(websocketUrl);

        this.websocket.onopen = () => {
            console.log('websocket connected');
        };

        this.websocket.onmessage = (e) => {
            console.log("WebSocket Message received: " + e.data);
            // handle websocket message
            var request = e.data.split(":");
            var id = parseInt(request[0]);
            var status = (request[1] === '1') ? true : false;

            let tmpLeds = this.state.leds.slice();
            var tmpLed = tmpLeds.find(l =>
                l.id === id
            );
            tmpLed.status = status;

            this.setState({
                leds: tmpLeds,
                message: ' '
            })
        };

        this.websocket.onerror = (e) => {
            console.log('onerror: ' + e.message);
        };

        this.websocket.onclose = (e) => {
            console.log('websocket closed', e.reason);
        };
    }

    loadFromServer = () => {
        console.log('Loading from server...');
        this.setState({
            isLoadingFromServer: true,
        })
        fetch(webserviceUrl).then(response => {
            if (!response.ok) {
                throw Error('network unreachable')
            }
            return response.json()
        }).then(res => {
            this.setState({
                leds: res,
                isLoadingFromServer: false
            })
            console.log('Received #LEDs from server: ' + this.state.leds.length);
        }).catch(err =>
            this.setState({
                error: true,
                isLoadingFromServer: false
            })
        )
    }

}

const host = '86.119.32.225:8000/ledregistry';
const webserviceUrl = 'http://' + host + '/leds/';
const websocketUrl = 'ws://' + host + '/websocket';

App.defaultProps = {
    leds: [
        {"id": 0, "status": false},
        {"id": 1, "status": false},
        {"id": 2, "status": false},
        {"id": 3, "status": false},
        {"id": 4, "status": false}
    ]
}
