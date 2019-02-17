import React from 'react';
import ReactDOM from 'react-dom';
import App from './app/App';
import registerServiceWorker from './registerServiceWorker';
import { createStore, combineReducers, applyMiddleware, compose } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { questionnaires, isLoading } from './questionnaire/Reducer';
import 'bootstrap/dist/css/bootstrap.min.css';

const initialState = {}

const flashcardApp = combineReducers({
    questionnaires,
    isLoading
})

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(flashcardApp, initialState, composeEnhancers(
    applyMiddleware(thunk)
));

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>, 
    document.getElementById('root'));
registerServiceWorker();
