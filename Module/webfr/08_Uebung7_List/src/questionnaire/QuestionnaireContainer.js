import React from 'react';
import QuestionnaireTable from "./QuestionnaireTable";

class QuestionnaireContainer extends React.Component {
    render() {
        return (
            <QuestionnaireTable questionnaires={this.props.qs}/>
        )
    }
}

QuestionnaireContainer.defaultProps = {
    qs: [
        {'id': 1, 'title': 'Test Title 1', 'description': 'Test description 1'},
        {'id': 2, 'title': 'Test Title 2', 'description': 'Test description 2'},
        {'id': 3, 'title': 'Test Title 3', 'description': 'Test description 3'},
        {'id': 4, 'title': 'Test Title 4', 'description': 'Test description 4'},
        {'id': 5, 'title': 'Test Title 5', 'description': 'Test description 5'}
    ]
}
export default QuestionnaireContainer;
