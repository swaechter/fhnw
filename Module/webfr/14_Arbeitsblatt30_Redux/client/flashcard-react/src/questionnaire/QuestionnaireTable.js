import React from 'react';
import { Table } from 'reactstrap';
import QuestionnaireTableElement from './QuestionnaireTableElement';
import Message from '../misc/Message';

export default class QuestionnaireTable extends React.Component {
  render() {
    return <div>
      {this.props.questionnaires.length>0 ? (
        <Table hover className="mt-4">
          <tbody>
            {this.props.questionnaires.map(questionnaire =>
              <QuestionnaireTableElement
                  // IMPORTANT: use the 'key' property to be able to update the list dynamically
                  key={questionnaire.id}
                  questionnaire={questionnaire}
                  deleteHandler={this.props.deleteHandler}
                  updateHandler={this.props.updateHandler}
                />
              )
            }
          </tbody>
        </Table>
      ) : (
        <Message message="No Questionnaires found"/>
      )}
      </div>
  }
}
