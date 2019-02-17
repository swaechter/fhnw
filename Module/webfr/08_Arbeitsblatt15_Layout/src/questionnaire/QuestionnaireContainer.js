import React from 'react';

class QuestionnaireContainer extends React.Component {
    render() {
        let responses = this.props.qs.length + ' Questionnaires found';
        return (
            <h3>{responses}</h3>
        )
    }
}

QuestionnaireContainer.defaultProps = {
    qs: [
        {'id': 1, 'title': 'Test Title 1', 'description': 'Test description 1'},
        {'id': 1, 'title': 'Test Title 2', 'description': 'Test description 2'},
        {'id': 1, 'title': 'Test Title 3', 'description': 'Test description 3'},
        {'id': 1, 'title': 'Test Title 4', 'description': 'Test description 4'},
        {'id': 1, 'title': 'Test Title 5', 'description': 'Test description 5'}
    ]
}
export default QuestionnaireContainer;
