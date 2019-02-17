import React from 'react';
import QuestionnaireTableElement from "./QuestionnaireTableElement";

const QuestionnaireTable = ({questionnaires}) => (
    <table className="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        {questionnaires.map(row => {
                return <QuestionnaireTableElement
                    key={row.id}
                    questionnaire={row}
                />
            }
        )}
        </tbody>
    </table>
);

export default QuestionnaireTable;
