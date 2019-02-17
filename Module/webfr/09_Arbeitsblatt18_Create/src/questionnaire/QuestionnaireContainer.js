import React from 'react';
import {Col, Row} from 'reactstrap';
import QuestionnaireTable from "./QuestionnaireTable";
import QuestionnaireFooter from "./QuestionnaireFooter";
import QuestionnaireCreateDialog from "./QuestionnaireCreateDialog";

class QuestionnaireContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            questionnaires: this.props.qs
        };
        this.create = this.create.bind(this);
    }

    create(questionnaire) {
        let questionnaires = this.state.questionnaires;
        questionnaire.id = questionnaires.length + 1;
        questionnaires.push(questionnaire);
        this.setState({
            questionnaires: questionnaires
        });
    }

    render() {
        let numberOfItems = this.state.questionnaires.length + ' Questionnaires found';
        return (
            <div>
                <Row>
                    <Col>
                        <h2>Questionnaires</h2>
                    </Col>
                    <Col className="text-right">
                        <QuestionnaireCreateDialog create={this.create}/>
                    </Col>
                </Row>
                <Row>
                    <QuestionnaireTable questionnaires={this.state.questionnaires}/>
                    <QuestionnaireFooter
                        leftMessage="© 2018 FHNW Team"
                        rightMessage={numberOfItems}
                    />
                </Row>
            </div>
        )
    }
}

QuestionnaireContainer.defaultProps = {
    qs: [
        {'id': 1, 'title': 'Test Title 1', 'description': 'Test description 1'},
        {'id': 2, 'title': 'Test Title 2', 'description': 'Test description 2'},
        {'id': 3, 'title': 'Test Title 3', 'description': 'Test description 3'},
        {'id': 4, 'title': 'Test Title 4', 'description': 'Test description 4'},
        {'id': 5, 'title': 'Test Title 5', 'description': 'Test description 5'}
    ]
};

export default QuestionnaireContainer;
