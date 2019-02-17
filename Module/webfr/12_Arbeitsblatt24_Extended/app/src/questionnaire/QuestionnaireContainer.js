import React from 'react';
import {Col, Row} from 'reactstrap';
import QuestionnaireTable from "./QuestionnaireTable";
import QuestionnaireFooter from "./QuestionnaireFooter";
import QuestionnaireCreateDialog from "./QuestionnaireCreateDialog";
import Message from "./utils/Message";

import QuestionnairesRestService from "./services/QuestionnairesRestService";

class QuestionnaireContainer extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            questionnaires: [],
            error: ''
        };
        this.create = this.create.bind(this);
        this.update = this.update.bind(this);
        this.delete = this.delete.bind(this);
    }

    async componentDidMount() {
        try {
            QuestionnairesRestService.serverUrl = this.props.serverUrl;
            let questionnaires = await QuestionnairesRestService.getAll();
            this.setState({
                questionnaires: questionnaires
            })
        } catch (exception) {
            this.setState({
                error: 'Unable to load the questionnaires: ' + exception.message
            })
        }
    }

    async create(questionnaire) {
        try {
            let questionnaires = this.state.questionnaires;
            let newquestionnaire = await QuestionnairesRestService.save(questionnaire);
            questionnaires.push(newquestionnaire);
            this.setState({
                questionnaires: questionnaires
            })

        } catch (exception) {
            this.setState({
                error: 'Unable to create the questionnaire: ' + exception.message
            })
        }
    }

    async update(questionnaire) {
        try {
            let questionnaires = this.state.questionnaires;
            let tmpquestionnaire = questionnaires.find(item => item.id === questionnaire.id);
            tmpquestionnaire.title = questionnaire.title;
            tmpquestionnaire.description = questionnaire.description;
            await QuestionnairesRestService.update(tmpquestionnaire);
            this.setState({
                questionnaires: questionnaires
            })
        } catch (exception) {
            this.setState({
                error: 'Unable to update the questionnaire: ' + exception.message
            })
        }
    }

    async delete(id) {
        try {
            let questionnaires = this.state.questionnaires;
            await QuestionnairesRestService.delete(id);
            questionnaires = questionnaires.filter(questionnaire => questionnaire.id !== id);
            this.setState({
                questionnaires: questionnaires
            })
        } catch (exception) {
            this.setState({
                error: 'Unable to delete the questionnaire: ' + exception.message
            })
        }
    }

    render() {
        let numberOfItems = this.state.questionnaires.length + ' Questionnaires found';
        return (
            <div>
                {this.state.error !== '' && <Message message={this.state.error}/>}
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
