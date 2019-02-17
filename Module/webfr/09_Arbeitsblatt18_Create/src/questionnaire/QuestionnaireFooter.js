import React from 'react';
import {Col, Row} from 'reactstrap';

export default class QuestionnaireFooter extends React.Component {
    render() {
        return <Row>
            <Col xs={6}>{this.props.leftMessage}</Col>
            <Col xs={6} className="text-right">{this.props.rightMessage}</Col>
        </Row>
    }
}
