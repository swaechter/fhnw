import React from 'react';
import loader from './loader.gif';

export default class Loader extends React.Component {
  render() {
    const centerStyle = {
      textAlign: 'center'
    };

    return (
      <div style={centerStyle}><img src={loader} alt="Loading..."/></div>
    )
  }
}
