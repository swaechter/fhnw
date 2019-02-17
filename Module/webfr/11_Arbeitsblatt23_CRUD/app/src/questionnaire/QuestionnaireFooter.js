import React from 'react';
import {Col, Row} from 'reactstrap';

class QuestionnaireFooter extends React.Component {
    render() {
        return <Row>
            <Col>{this.props.leftMessage}</Col>
            <Col className="text-right">{this.props.rightMessage}</Col>
        </Row>
    }
}

export default QuestionnaireFooter;
