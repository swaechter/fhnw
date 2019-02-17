import React from 'react';
import {Alert} from 'reactstrap';

class Message extends React.Component {
    render() {
        return (
            <Alert color="danger">{this.props.message}</Alert>
        )
    }
}

export default Message;
