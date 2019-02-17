import React from 'react';
import {Col, Row} from 'reactstrap';
import QuestionnaireTable from "./QuestionnaireTable";
import QuestionnaireFooter from "./QuestionnaireFooter";
import QuestionnaireCreateDialog from "./QuestionnaireCreateDialog";

class QuestionnaireContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            questionnaires: []
        };
        this.create = this.create.bind(this);
        this.update = this.update.bind(this);
        this.delete = this.delete.bind(this);
    }

    componentDidMount() {
        fetch(this.props.serverUrl)
            .then(response => response.json())
            .then(json => {
                this.setState({
                    questionnaires: json
                });
            })
            .catch(error => {
                alert('Error while loading the questionnaires: ' + error);
            });
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

export default QuestionnaireContainer;
