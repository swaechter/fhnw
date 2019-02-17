const initialState = {
    questionnaires: [
        {id: '0', title: 'initial title 1', description: 'initial description 1'},
        {id: '1', title: 'initial title 2', description: 'initial description 2'},
        {id: '2', title: 'initial title 3', description: 'initial description 3'}
    ]
}

export function isLoading(state = false, action) {
    switch (action.type) {
        case 'IS_LOADING':
            return action.isLoading;
        default:
            return state;
    }
}

export function questionnaires(state=initialState.questionnaires, action) {
    switch (action.type) {
        case 'CREATE_QUESTIONNAIRE':
            let questionnaire = action.questionnaire;
            //questionnaire.id = generateId(state).toString();
            // add new questionnaire at the tail of old array
            return [
                ...state,
                questionnaire
            ];
        case 'UPDATE_QUESTIONNAIRE': {
            let tmp = state.find(q => q.id === action.questionnaire.id);
            // update questionnaire with new values; tmp is a reference into the array element
            tmp.title = action.questionnaire.title;
            tmp.description = action.questionnaire.description;
            return state;
        }
        case 'DELETE_QUESTIONNAIRE': {
            let qs = state.slice();
            qs = qs.filter(q => q.id !== action.id);
            return qs;
            /*
            return [
                ...state.slice(0, action.id),
                ...state.slice(action.id + 1)
            ]
            */
        }
        case 'QUESTIONNAIRES_FETCH_SUCCESS': {
            return action.questionnaires;
        }
        default:
            return state;
    }
}

/*
const generateId = questionnaires => {
    if (questionnaires.length > 0) {
        const nr = parseInt(questionnaires[questionnaires.length - 1].id, 10);
        return nr + 1;
    }
    return 0;
}
*/