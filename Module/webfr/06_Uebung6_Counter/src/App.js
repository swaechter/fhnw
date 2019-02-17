import React, {Component} from 'react';
import Counter from './Counter';

class App extends Component {
    render() {
        return (
            <div className="container">
                <Counter message="Press these buttons to increment and to refresh"/>
            </div>
        );
    }
}

export default App;
