import React from 'react';

export default class Footer extends React.Component {
  render() {
    return <div>&copy; {this.props.message}</div>
  }
}
