import React from 'react';
import Header from './Header';
import Footer from './Footer';
import { Container } from 'reactstrap';
//import QuestionnaireContainer from '../questionnaire/QuestionnaireContainer';
import { QuestionnaireList } from '../questionnaire/QuestionnaireContainer';
import Loader from '../misc/Loader';
import Message from '../misc/Message';
import QuestionnairesRestService from '../integration/QuestionnairesRestService';

export default class App extends React.Component {
  constructor() {
    super();
    // 'loading' is used locally only to this component to signal loading of config file
    this.state = {
      loading: true,
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
          loading: false,
          subTitle: file.subtitle
        }
      )
    } catch (ex) {
      console.log(ex);
      this.setState(
        {
          loading: false,
          error: 'Error while trying to load config file'
        }
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
          title="RIA-Client with React-Redux"
          subtitle={this.state.subTitle} />


        {(! this.state.loading) ? (
          <QuestionnaireList />
        ) : (
          comp
        )}
        
        <Footer message="The FHNW Team"/>
      </Container>
    )
  }
}
