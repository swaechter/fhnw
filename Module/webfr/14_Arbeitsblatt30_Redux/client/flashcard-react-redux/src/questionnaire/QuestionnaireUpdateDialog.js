import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, Form, FormGroup, Col, Label, Input } from 'reactstrap';

export default class QuestionnaireUpdateDialog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showModal: false,
      title: props.questionnaire.title,
      description: props.questionnaire.description
    }
  }

  render() {
    return (
      <div>
        <Button color="primary" onClick={this.open}>Edit</Button>
        <Modal isOpen={this.state.showModal} toggle={this.close} size="lg" autoFocus={false}>
          <ModalHeader toggle={this.close} >
            Update Questionnaire
          </ModalHeader>
          <ModalBody>
            <Form>
              <FormGroup row>
                <Label md={2} for="formTitle">
                  Title
                 </Label>
                <Col md={10}>
                  <Input type="text" id="formTitle" placeholder="Title" autoFocus={true}
                    value={this.state.title} onChange={this.handleChange} />
                </Col>
              </FormGroup>

              <FormGroup row>
                <Label md={2} for="formDescription">
                  Description
                 </Label>
                <Col md={10}>
                  <Input type="text" id="formDescription" placeholder="Description"
                    value={this.state.description} onChange={this.handleChange} />
                </Col>
              </FormGroup>

              <FormGroup>
                <Col className="clearfix" style={{ padding: '.2rem' }}>
                  <Button className="float-right" color="primary" onClick={this.submit}>Submit</Button>
                </Col>
              </FormGroup>
            </Form>
          </ModalBody>
        </Modal>
      </div>
    )
  }

  handleChange = (e) => {
    if (e.target.id === 'formTitle') {
      this.setState({ title: e.target.value });
    } else if (e.target.id === 'formDescription') {
      this.setState({ description: e.target.value });
    }
  }

  close = () => {
    this.setState({ showModal: false });
  }

  submit = () => {
    this.props.updateHandler({
      id: this.props.questionnaire.id,
      title: this.state.title, description: this.state.description
    });
    this.setState({ showModal: false });
  }

  open = () => {
    this.setState({ showModal: true });
  }
}
