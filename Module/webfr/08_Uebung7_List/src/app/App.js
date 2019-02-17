import React, {Component} from 'react';
import {Container} from 'reactstrap';
import Header from "./Header";
import Footer from "./Footer";
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
                />
                <Footer
                    copyright="© 2018 FHNW Team"
                />
            </Container>
        );
    }
}

export default App;
