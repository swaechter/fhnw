import React from 'react';
import {Button} from 'reactstrap';

export default class Counter extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            counter: 0
        }
        this.increment = this.increment.bind(this);
        this.reset = this.reset.bind(this);
    }

    increment() {
        this.setState({counter: this.state.counter + 1});
    }

    reset() {
        this.setState({counter: 0});
    }

    render() {
        const nrStyle = {
            textAlign: 'center',
            fontSize: '320px',
            margin: '10px'
        };
        return (
            <div>
                <p style={nrStyle}>{this.state.counter}</p>
                <Button onClick={this.increment}>
                    INCR
                </Button>
                &nbsp;
                <Button color="danger" onClick={this.reset}>
                    REFRESH
                </Button>
                &nbsp;
                {this.props.message}
            </div>
        )
    }
}