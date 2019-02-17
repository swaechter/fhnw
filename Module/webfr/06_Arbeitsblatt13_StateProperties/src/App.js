import React, {Component} from 'react';

class App extends Component {
    constructor(props) {
        super(props);
        this.names = [
            'Balin', 'Dwalin', 'Fili', 'Kili', 'Dori', 'Nori', 'Ori', 'Oin', 'Gloin',
            'Bifur', 'Bofur', 'Bombur'
        ];
        this.state = {
            index: 0
        };
        this.tick = this.tick.bind(this);
    }

    componentDidMount() {
        this.timer = setInterval(this.tick, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.timer);
    }

    tick() {
        // Wird this.setState auskommentiert, wird der State eines Components nicht mehr aktualisiert
        this.setState({index: (this.state.index + 1) % this.names.length});
    }

    render() {
        return (<h1>{this.props.message} {this.names[this.state.index]}</h1>)
    }
}

export default App;
