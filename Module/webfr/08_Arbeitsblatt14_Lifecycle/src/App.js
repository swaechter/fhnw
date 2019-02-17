import React, {Component} from 'react';
import Counter from './Counter';

class App extends Component {
    render() {
        return (
            <div className="container">
                <Counter message="Wait to see the counter increase and refresh manually!"/>
            </div>
        );
    }
}

export default App;
