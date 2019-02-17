import React from 'react';
import QuestionnaireCreateDialog from './QuestionnaireCreateDialog';
import QuestionnaireTable from './QuestionnaireTable';
import Loader from '../misc/Loader';
import QuestionnairesRestService from '../integration/QuestionnairesRestService';

export default class QuestionnaireContainer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      questionnaires: [],
      isLoading: true
    }
  }

  componentDidMount() {
    // Load questionnaires from backend to initialize this component
    QuestionnairesRestService.getAll().then(qs =>
      this.setState(
        {
          questionnaires: qs,
          isLoading: false
        }
      )
    )
  }

  remove = (id) => {
    var qs = this.state.questionnaires;
    this.setState(
      { isLoading: true }
    );
    QuestionnairesRestService.delete(id).then(ok => {
      if (ok) {
        qs = qs.filter(q => q.id !== id);
        this.setState(
          {
            questionnaires: qs,
            isLoading: false
          }
        );
        console.log("QuestionnaireContainer: Delete questionnaire with id=" + id);
      } else {
        console.log("QuestionnaireContainer: Could not delete questionnaire with id=" + id);
      }
    });
  }

  create = (questionnaire) => {
    this.setState(
      { isLoading: true }
    );
    var qs = this.state.questionnaires;
    QuestionnairesRestService.save(questionnaire).then(q => {
        qs.push(q);
        this.setState(
          {
            questionnaires: qs,
            isLoading: false
          }
        );
        console.log("QuestionnaireContainer: Create questionnaire with id=" + q.id);
      });
  }

  update = (questionnaire) => {
    this.setState(
      { isLoading: true }
    );
    var qs = this.state.questionnaires;
    QuestionnairesRestService.update(questionnaire).then(ok => {
      if (ok) {
        var tmp = qs.find(function (q) {
          return q.id === questionnaire.id;
        });
        tmp.title = questionnaire.title;
        tmp.description = questionnaire.description;
        this.setState(
          {
            questionnaires: qs,
            isLoading: false
          }
        );
        console.log("QuestionnaireContainer: Update questionnaire with id=" + questionnaire.id);
      } else {
        console.log("QuestionnaireContainer: Could not update questionnaire with id=" + questionnaire.id);
      }
    });
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <Loader />
        ) : (
            <div>
              <QuestionnaireCreateDialog className="float-right"
                createHandler={this.create}
              />
              <h3>Questionnaires</h3>
              <QuestionnaireTable
                questionnaires={this.state.questionnaires}
                deleteHandler={this.remove}
                updateHandler={this.update}
              />
            </div>
          )}
      </div>
    )
  }
}
