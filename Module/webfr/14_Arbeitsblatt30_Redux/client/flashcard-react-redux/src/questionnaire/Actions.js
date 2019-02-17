import QuestionnairesRestService from '../integration/QuestionnairesRestService';

export function createQuestionnaire(questionnaire) {
  return {
    type: 'CREATE_QUESTIONNAIRE',
    questionnaire: questionnaire
  };
}

export function updateQuestionnaire(questionnaire) {
  return {
    type: 'UPDATE_QUESTIONNAIRE',
    questionnaire: questionnaire
  };
}

export function deleteQuestionnaire(id) {
  return {
    type: 'DELETE_QUESTIONNAIRE',
    id: id
  };
}

export function isLoading(bool) {
  return {
    type: 'IS_LOADING',
    isLoading: bool
  };
}

export function questionnairesFetchSuccess(questionnaires) {
  return {
    type: 'QUESTIONNAIRES_FETCH_SUCCESS',
    questionnaires
  };
}

export function fetchQuestionnairesFromServer() {
  return (dispatch) => {
    // Signal start loading
    dispatch(isLoading(true));
    QuestionnairesRestService.getAll().then((questionnaires) => {
      // Update redux store with loaded questionnaires
      dispatch(questionnairesFetchSuccess(questionnaires))
    })
    // Signal loading finished
    dispatch(isLoading(false));
  }
}

export function createQuestionnaireOnServer(questionnaire) {
  return (dispatch) => {
    // Signal start loading
    dispatch(isLoading(true));

    QuestionnairesRestService.save(questionnaire).then(q => {
      // Update redux store with loaded questionnaires
      console.log(q);
      dispatch(createQuestionnaire(q))
      console.log("Successfully created questionnaire with id=" + q.id);

      // Signal loading finished
      dispatch(isLoading(false));
    })
  }
}

export function deleteQuestionnaireOnServer(id) {
  return (dispatch) => {
    // Signal start loading
    dispatch(isLoading(true));

    QuestionnairesRestService.delete(id).then(ok => {
      if (ok) {
        // Update redux store
        dispatch(deleteQuestionnaire(id))
        console.log("Successfully deleted questionnaire with id=" + id);
      } else {
        console.log("Could not delete questionnaire with id=" + id);
      }
      // Signal loading finished
      dispatch(isLoading(false));
    })
  }
}

export function updateQuestionnaireOnServer(questionnaire) {
  return (dispatch) => {
    // Signal start loading
    dispatch(isLoading(true));

    QuestionnairesRestService.update(questionnaire).then(ok => {
      if (ok) {
        // Signal loading finished
        dispatch(updateQuestionnaire(questionnaire))
        console.log("Successfully updated questionnaire with id=" + questionnaire.id);
      } else {
        console.log("Could not update questionnaire with id=" + questionnaire.id);
      }
      // Signal loading finished
      dispatch(isLoading(false));
    })
  }
}