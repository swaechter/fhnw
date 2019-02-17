import React from "react";
import {Button, Col, Form, FormGroup, Input, Label, Modal, ModalBody, ModalHeader} from 'reactstrap';

class QuestionnaireCreateDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            title: '',
            description: ''
        };
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.handle = this.handle.bind(this);
        this.create = this.create.bind(this);
    }

    open() {
        this.setState({
            showModal: true
        })
    }

    close() {
        this.setState({
            title: '',
            description: '',
            showModal: false
        })
    }

    handle(event) {
        let value = event.target.value;
        if (event.target.id === 'formTitle') {
            this.setState({title: value});
        } else if (event.target.id === 'formDescription') {
            this.setState({description: value});
        }
    }

    create() {
        this.props.create({title: this.state.title, description: this.state.description});
        this.close();
    }

    render() {
        return (
            <div>
                <Button color="success" onClick={this.open}>Add Questionnaire</Button>
                <Modal isOpen={this.state.showModal} toggle={this.close} size="lg"
                       autoFocus={false}>
                    <ModalHeader toggle={this.close}>
                        Create Questionnaire
                    </ModalHeader>
                    <ModalBody>
                        <Form>
                            <FormGroup row>
                                <Label md={2} for="formTitle">Title</Label>
                                <Col md={10}>
                                    <Input type="text" id="formTitle" value={this.state.title} onChange={this.handle}/>
                                </Col>
                            </FormGroup>
                            <FormGroup row>
                                <Label md={2} for="formDescription"> Description </Label>
                                <Col md={10}>
                                    <Input type="text" id="formDescription" value={this.state.description}
                                           onChange={this.handle}/>
                                </Col>
                            </FormGroup>
                            <FormGroup>
                                <Col className="clearfix" style={{padding: '.2rem'}}>
                                    <Button className="float-right" color="secondary"
                                            onClick={this.create}>Save</Button>
                                </Col>
                            </FormGroup>
                        </Form>
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}

export default QuestionnaireCreateDialog;
