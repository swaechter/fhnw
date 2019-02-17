import React from 'react';
import Header from './Header';
import Footer from './Footer';
import { Container } from 'reactstrap';
import QuestionnaireContainer from '../questionnaire/QuestionnaireContainer';
import Loader from '../misc/Loader';
import Message from '../misc/Message';
import QuestionnairesRestService from '../integration/QuestionnairesRestService';

//This configuration option does not work after 'npm run build'
//because webpack processes the file and its content and puts them
//into the build package, although the file is copy as normal text file
//as well.
//Import Configuration from '../../public/application.json';

export default class App extends React.Component {
  constructor() {
    super();
    //console.log("URL is: " + Configuration.url);
    this.state = {
      isLoading: true,
      error: ''
    }
  }
  
  checkUrl(url) {
    if (url.slice(-1) === '/') {
      url = url.slice(0, -1);
    }
    return url;
  }

  async componentDidMount() {
    try {
      const response = await fetch('application.json');
      if (!response.ok) {
        throw Error(response.statusText);
      }
      const file = await response.json();
      console.log(file.url);
      const url = this.checkUrl(file.url);
      QuestionnairesRestService.serverUrl = url + "/questionnaires";
      this.setState(
        {
          isLoading: false,
          subTitle: file.subtitle
        }
      )
    } catch (ex) {
      console.log(ex);
      this.setState(
        { error: 'Error while trying to load config file' }
      )
    }
  }

  render() {
    var comp;
    if (this.state.error === '') {
      comp = <Loader/>;
    } else {
      comp = <Message message={this.state.error}/>;
    }
    return (
      <Container fluid>
        <Header
          title="Flashcard Client with React"
          subtitle={this.state.subTitle}/>

        {(! this.state.isLoading) ? (
          <QuestionnaireContainer />
        ) : (
          comp
        )}
        
        <Footer message="The FHNW Team"/>
      </Container>
    )
  }
}
