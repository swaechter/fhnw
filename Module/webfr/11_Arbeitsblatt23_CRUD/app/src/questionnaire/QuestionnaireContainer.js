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
            .then(questionnaires => {
                this.setState({
                    questionnaires: questionnaires
                });
            })
            .catch(error => {
                alert('Unable to load the questionnaires: ' + error);
            });
    }

    create(questionnaire) {
        let questionnaires = this.state.questionnaires;
        let request = new Request(this.props.serverUrl, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/json'
            }), body: JSON.stringify(questionnaire)
        });
        fetch(request)
            .then(response => response.json())
            .then(newquestionnaire => {
                questionnaires.push(newquestionnaire);
                this.setState({
                    questionnaires: questionnaires
                })
            })
            .catch(error => {
                alert('Unable to create a new questionnaire: ' + error);
            });
    }

    update(questionnaire) {
        let questionnaires = this.state.questionnaires;
        let request = new Request(this.props.serverUrl + '/' + questionnaire.id, {
            method: 'PUT',
            headers: new Headers({
                'Content-Type': 'application/json'
            }), body: JSON.stringify(questionnaire)
        });
        fetch(request)
            .then(response => response.json())
            .then(updatedquestionnaire => {
                let tmpquestionnaire = questionnaires.find(item => item.id === updatedquestionnaire.id);
                tmpquestionnaire.title = questionnaire.title;
                tmpquestionnaire.description = questionnaire.description;
                this.setState({
                    questionnaires: questionnaires
                })
            })
            .catch(error => {
                alert('Unable to update the questionnaire: ' + error);
            });
    }

    delete(id) {
        let questionnaires = this.state.questionnaires;
        let request = new Request(this.props.serverUrl + '/' + id, {
            method: 'DELETE'
        });
        fetch(request)
            .then(response => {
                if (response.ok) {
                    questionnaires = questionnaires.filter(questionnaire => questionnaire.id !== id);
                    this.setState({
                        questionnaires: questionnaires
                    })
                } else {
                    alert('Unable to delete the questionnaire: ' + id);
                }
            })
            .catch(error => {
                alert('Unable to delete the questionnaire: ' + error);
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
