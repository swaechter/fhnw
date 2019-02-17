"use strict";

let Questionnaire = require('../domain/questionnaire');

let express = require('express');
let questionnaireController = require('../web/questionnaire-controller');
let expect = require('chai').expect;
let sinon = require('sinon');
let request = require('supertest');
let log4js = require('log4js');
let bodyParser = require('body-parser');
let mongoose = require('mongoose');

describe('test find*', () => {
    let app;
    let findById;
    let findAll;

    before((done) => {
        // configure logging
        log4js.configure('./test/log4js-test.json');
        // init app with the controller under test
        app = express().use('/', questionnaireController);
        // setup mocks
        findById = sinon.stub(Questionnaire, 'findById');
        findAll = sinon.stub(Questionnaire, 'find');
        done();
    });

    afterEach(() => {
        // reset all mocks created in before()
        findById.reset();
        findAll.reset();
    });

    it('should return all questionnaires', (done) => {
        var q1 = {
            title: 'Title 1',
            description: 'Description 1'
        };
        var q2 = {
            title: 'Title 2',
            description: 'Description 2'
        };
        var expectedQuestionnaires = [q1, q2];
        var err = false;
        findAll.yields(err, expectedQuestionnaires);

        request(app)
            .get('/questionnaires')
            .end((err, res) => {
                expect(res.statusCode).to.equal(200);
                expect(res.body.length).to.equal(2);
                expect(res.body[0].title).to.equal('Title 1');
                expect(res.body[0].description).to.equal('Description 1');
                expect(res.body[1].title).to.equal('Title 2');
                expect(res.body[1].description).to.equal('Description 2');

                sinon.assert.calledOnce(findAll);

                done();
            });
    });

    it('should return one questionnaire', (done) => {
        var q1 = {
            id: 1,
            title: 'Title 1',
            description: 'Description 1'
        };
        var err = false;
        findById.yields(err, q1);

        request(app)
            .get('/questionnaires/1')
            .end((err, res) => {
                expect(res.statusCode).to.equal(200);
                expect(res.body.title).to.equal('Title 1');
                expect(res.body.description).to.equal('Description 1');

                sinon.assert.calledOnce(findById);

                done();
            });
    });

    it('should return NOT_FOUND if questionnaire does not exist', (done) => {
        var err = true;
        findById.yields(err, null);

        request(app)
            .get('/questionnaires/1000')
            .end((err, res) => {
                expect(res.statusCode).to.equal(400);

                sinon.assert.calledOnce(findById);

                done();
            });
    });
});

describe('test create*', () => {
    let app;
    let save;

    before((done) => {
        // configure logging
        log4js.configure('./test/log4js-test.json');
        mongoose.Promise = global.Promise;
        // init app with the controller under test
        app = express()
            .use(bodyParser.json())
            .use('/', questionnaireController);

        // setup mocks
        save = sinon.stub(Questionnaire.prototype, 'save');
        done();
    });

    afterEach(() => {
        // reset all mocks created in before()
        save.reset();
    });

    it('should return new questionnaire', (done) => {
        var q1 = {
            id: 5,
            title: 'Title 1',
            description: 'Description 1'
        };
        var err = false;
        save.yields(err, q1);

        request(app)
            .post('/questionnaires/')
            .type('json')
            .send({title:q1.title, description:q1.description})
            .end((err, res) => {
                expect(res.statusCode).to.equal(200);
                expect(res.body.id).to.equal(5);
                expect(res.body.title).to.equal('Title 1');
                expect(res.body.description).to.equal('Description 1');

                sinon.assert.calledOnce(save);

                done();
            });
    });
});

describe('test delete*', () => {
    let app;
    let remove;

    before((done) => {
        // configure logging
        log4js.configure('./test/log4js-test.json');
        mongoose.Promise = global.Promise;
        // init app with the controller under test
        app = express()
            .use(bodyParser.json())
            .use('/', questionnaireController);
        remove = sinon.stub(Questionnaire, 'findByIdAndRemove');
        done();
    });

    afterEach(() => {
        // reset all mocks created in before()
        remove.reset();
    });

    it('should delete a questionnaire', (done) => {
        var err = false;
        remove.yields(err);

        request(app)
            .delete('/questionnaires/5')
            .end((err, res) => {
                expect(res.statusCode).to.equal(200);
                sinon.assert.calledOnce(remove);
                done();
            });
    });
});

describe('test put*', () => {
    let app;
    let findByIdAndUpdate;

    before((done) => {
        // configure logging
        log4js.configure('./test/log4js-test.json');
        mongoose.Promise = global.Promise;
        // init app with the controller under test
        app = express()
            .use(bodyParser.json())
            .use('/', questionnaireController);

        // setup mocks
        findByIdAndUpdate = sinon.stub(Questionnaire, 'findByIdAndUpdate');
        done();
    });

    afterEach(() => {
        // reset all mocks created in before()
        findByIdAndUpdate.reset();
    });

    it('should return updated questionnaire', (done) => {
        var q1 = {
            id: 5,
            title: 'Title 1',
            description: 'Description 1'
        };
        var err = false;
        findByIdAndUpdate.yields(err, q1);

        request(app)
            .put('/questionnaires/5')
            .type('json')
            .send({title:q1.title, description:q1.description})
            .end((err, res) => {
                expect(res.statusCode).to.equal(200);

                sinon.assert.calledOnce(findByIdAndUpdate);

                done();
            });
    });
});
