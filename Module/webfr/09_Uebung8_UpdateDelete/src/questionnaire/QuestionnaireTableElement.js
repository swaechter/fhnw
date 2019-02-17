import React from 'react';
import {Button, Row} from 'reactstrap';

import QuestionnaireShowDialog from "./QuestionnaireShowDialog";
import QuestionnaireUpdateDialog from "./QuestionnaireUpdateDialog";

class QuestionnaireTableElement extends React.Component {
    constructor(props) {
        super(props)
        this.delete = this.delete.bind(this);
    }

    delete() {
        this.props.delete(this.props.questionnaire.id);
    }

    render() {
        return <tr>
            <td>{this.props.questionnaire.id}</td>
            <td>{this.props.questionnaire.title}</td>
            <td>{this.props.questionnaire.description}</td>
            <td>
                <Row>
                    <QuestionnaireShowDialog
                        questionnaire={this.props.questionnaire}
                    />
                    <QuestionnaireUpdateDialog
                        questionnaire={this.props.questionnaire}
                        update={this.props.update}
                    />
                    <Button color="danger" onClick={this.delete}>Delete</Button>
                </Row>
            </td>
        </tr>
    }
}

export default QuestionnaireTableElement;
