import React, {Component} from 'react';
import {Container} from 'reactstrap';
import Header from "./Header";
import QuestionnaireContainer from "../questionnaire/QuestionnaireContainer";

class App extends Component {
    render() {
        return (
            <Container fluid>
                <Header
                    title="Flashcard Client with React"
                    subtitle="Version 1"
                />
                <QuestionnaireContainer
                    serverUrl="http://localhost:8080/flashcard-mvc/questionnaires"
                />
            </Container>
        );
    }
}

export default App;
