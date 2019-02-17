import React from 'react';
import loader from './Loader.gif';

class Loader extends React.Component {
    render() {
        return (
            <div style={centerStyle}><img src={loader} alt="Loading..."/></div>
        )
    }
}

const centerStyle = {
    textAlign: 'center'
};

export default Loader;
