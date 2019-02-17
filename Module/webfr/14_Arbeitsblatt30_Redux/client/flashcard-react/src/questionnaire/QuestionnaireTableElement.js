import React from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import QuestionnaireShowDialog from './QuestionnaireShowDialog';
import QuestionnaireUpdateDialog from './QuestionnaireUpdateDialog';

const textStyle = {
  verticalAlign: 'middle'
};

export default class QuestionnaireTableElement extends React.Component {
  render() {
    return <tr>
      <td colSpan="1" style={textStyle}>
        {this.props.questionnaire.id}
      </td>
      <td colSpan="3" style={textStyle}>
        {this.props.questionnaire.title}
      </td>
      <td colSpan="10" style={textStyle}>
        {this.props.questionnaire.description}
      </td>
      <td>
        <ButtonGroup className="float-right">
            <QuestionnaireShowDialog
              questionnaire={this.props.questionnaire}/>
            <QuestionnaireUpdateDialog
              questionnaire={this.props.questionnaire}
              updateHandler={this.props.updateHandler}/>
            <Button color="danger" onClick={() => this.props.deleteHandler(this.props.questionnaire.id)}>
              Delete
            </Button>
          </ButtonGroup>
      </td>
    </tr>
  }
}
