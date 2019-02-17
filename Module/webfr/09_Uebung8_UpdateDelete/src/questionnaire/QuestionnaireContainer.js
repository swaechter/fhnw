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
        this.update = this.update.bind(this);
        this.delete = this.delete.bind(this);
    }

    create(questionnaire) {
        let questionnaires = this.state.questionnaires;
        questionnaire.id = questionnaires.length + 1;
        questionnaires.push(questionnaire);
        this.setState({
            questionnaires: questionnaires
        });
    }

    update(questionnaire) {
        let questionnaires = this.state.questionnaires;
        let tmpquestionnaire = questionnaires.find(item => item.id === questionnaire.id);
        tmpquestionnaire.title = questionnaire.title;
        tmpquestionnaire.description = questionnaire.description;
        this.setState({
            questionnaires: questionnaires
        });
    }

    delete(id) {
        let questionnaires = this.state.questionnaires.filter(item => item.id !== id);
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
                        <QuestionnaireCreateDialog
                            create={this.create}
                        />
                    </Col>
                </Row>
                <Row>
                    <QuestionnaireTable
                        questionnaires={this.state.questionnaires}
                        update={this.update}
                        delete={this.delete}
                    />
                </Row>
                <QuestionnaireFooter
                    leftMessage="2018 FHNW Team"
                    rightMessage={numberOfItems}
                />
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
