import React from 'react';
import QuestionnaireTableElement from "./QuestionnaireTableElement";

class QuestionnaireTable extends React.Component {
    render() {
        return <table className="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {this.props.questionnaires.map(row => {
                    return <QuestionnaireTableElement
                        key={row.id}
                        questionnaire={row}
                        update={this.props.update}
                        delete={this.props.delete}
                    />
                }
            )}
            </tbody>
        </table>
    }
}

export default QuestionnaireTable;
