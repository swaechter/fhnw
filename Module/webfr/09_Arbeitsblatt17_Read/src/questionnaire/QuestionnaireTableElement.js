import React from 'react';
import QuestionnaireShowDialog from "./QuestionnaireShowDialog";

const QuestionnaireTableElement = ({questionnaire}) => (
    <tr>
        <td>{questionnaire.id}</td>
        <td>{questionnaire.title}</td>
        <td>{questionnaire.description}</td>
        <td>
            <QuestionnaireShowDialog questionnaire={questionnaire}/>
        </td>
    </tr>
);

export default QuestionnaireTableElement;
