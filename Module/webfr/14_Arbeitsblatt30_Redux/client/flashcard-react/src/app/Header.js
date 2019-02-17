import React from 'react';
import { Jumbotron } from 'reactstrap';

export default class Header extends React.Component {
  render() {
    return <Jumbotron>
        <h1>{this.props.title}</h1>
        <h3>{this.props.subtitle}</h3>
      </Jumbotron>
  }
}
