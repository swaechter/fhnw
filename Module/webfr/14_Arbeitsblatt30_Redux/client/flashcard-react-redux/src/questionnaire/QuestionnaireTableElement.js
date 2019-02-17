import React from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import QuestionnaireShowDialog from './QuestionnaireShowDialog';
import QuestionnaireUpdateDialog from './QuestionnaireUpdateDialog';

const textStyle = {
  verticalAlign: 'middle'
};

const QuestionnaireTableElement = ({ questionnaire, updateHandler, deleteHandler }) => (
  <tr>
    <td colSpan="1" style={textStyle}>
      {questionnaire.id}
    </td>
    <td colSpan="3" style={textStyle}>
      {questionnaire.title}
    </td>
    <td colSpan="10" style={textStyle}>
      {questionnaire.description}
    </td>
    <td>
      <ButtonGroup className="float-right">
        <QuestionnaireShowDialog
          questionnaire={questionnaire} />
        <QuestionnaireUpdateDialog
          questionnaire={questionnaire}
          updateHandler={updateHandler} />
        <Button color="danger" onClick={() => deleteHandler(questionnaire.id)}>
          Delete
        </Button>
      </ButtonGroup>
    </td>
  </tr>
)
export default QuestionnaireTableElement;

