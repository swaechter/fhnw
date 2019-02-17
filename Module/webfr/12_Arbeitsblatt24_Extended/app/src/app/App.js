import React, {Component} from 'react';
import {Container} from 'reactstrap';
import Header from "./Header";
import QuestionnaireContainer from "../questionnaire/QuestionnaireContainer";
import Loader from "../questionnaire/utils/Loader";
import Message from "../questionnaire/utils/Message";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            url: '',
            subtitle: '',
            error: ''
        }
    }

    componentDidMount() {
        let app = this;
        fetch('application.json').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.statusText);
            }
        }).then(config => {
            app.setState({
                url: config.url,
                subtitle: config.subtitle
            })
        }).catch(exception => {
            app.setState({
                error: 'Unable to load and parse the application configuration: ' + exception.getError()
            })
        })
    }

    render() {
        let component = (this.state.error === '') ? <Loader/> : <Message message={this.state.error}/>;
        return (
            <Container fluid>
                <Header
                    title="Flashcard Client with React"
                    subtitle={this.state.subtitle}
                />
                {(this.state.url !== '') ? (<QuestionnaireContainer serverUrl={this.state.url}/>) : (component)}
            </Container>
        );
    }
}

export default App;
