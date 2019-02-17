import React from 'react';
import QuestionnaireCreateDialog from './QuestionnaireCreateDialog';
import QuestionnaireTable from './QuestionnaireTable';
import Loader from '../misc/Loader';
import { createQuestionnaireOnServer, updateQuestionnaireOnServer, deleteQuestionnaireOnServer, fetchQuestionnairesFromServer } from './Actions';
import { connect } from 'react-redux';

class QuestionnaireContainer extends React.Component {
  componentDidMount() {
    // Load questionnaires from backend to initialize this component
    this.props.fetchData();
  }

  render() {
    return (
      <div>
        {this.props.loading ? (
          <Loader />
        ) : (
            <div>
              <QuestionnaireCreateDialog className="float-right"
                createHandler={this.props.createHandler}
              />
              <h3>Questionnaires</h3>
              <QuestionnaireTable
                questionnaires={this.props.questionnaires}
                deleteHandler={this.props.deleteHandler}
                updateHandler={this.props.updateHandler}
              />
            </div>
          )}
      </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    questionnaires: state.questionnaires,
    isLoading: state.isLoading
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    createHandler: (questionnaire) => dispatch(createQuestionnaireOnServer(questionnaire)),
    updateHandler: (questionnaire) => dispatch(updateQuestionnaireOnServer(questionnaire)),
    deleteHandler: (id) => dispatch(deleteQuestionnaireOnServer(id)),
    fetchData: () => dispatch(fetchQuestionnairesFromServer())
  }
}

export const QuestionnaireList = connect(
  mapStateToProps,
  mapDispatchToProps
)(QuestionnaireContainer)
