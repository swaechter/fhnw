import React from 'react';

export default class ErrorMsg extends React.Component {
  render() {
    const centerStyle = {
      textAlign: 'center'
    };

    return (
      <div style={centerStyle}><h2>{this.props.message}</h2></div>
    )
  }
}
