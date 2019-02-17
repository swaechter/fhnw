import React from "react";
import {Button, Col, Form, FormGroup, Input, Label, Modal, ModalBody, ModalHeader} from 'reactstrap';

class QuestionnaireShowDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
    }

    open() {
        this.setState({
            showModal: true
        })
    }

    close() {
        this.setState({
            showModal: false
        })
    }

    render() {
        return (
            <div>
                <Button color="secondary" onClick={this.open}>Show</Button>
                <Modal isOpen={this.state.showModal} toggle={this.close} size="lg"
                       autoFocus={false}>
                    <ModalHeader toggle={this.close}>
                        Show Questionnaire
                    </ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup row>
                                <Label md={2} for="formTitle">Title</Label>
                                <Col md={10}>
                                    <Input type="text" id="formTitle" plaintext>{this.props.questionnaire.title}</Input>
                                </Col>
                            </FormGroup>
                            <FormGroup row>
                                <Label md={2} for="formDescription"> Description </Label>
                                <Col md={10}>
                                    <Input type="text" id="formDescription"
                                           plaintext>{this.props.questionnaire.description} </Input>
                                </Col>
                            </FormGroup>
                            <FormGroup>
                                <Col className="clearfix" style={{padding: '.2rem'}}>
                                    <Button className="float-right" color="secondary"
                                            onClick={this.close}>Close</Button>
                                </Col>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default QuestionnaireShowDialog;
