"use strict";

const log4js = require('log4js');
const express = require('express');
const Questionnaire = require('./../domain/questionnaire');

// Get the dispatcher
const dispatcher = express.Router();

// Get the logger
const logger = log4js.getLogger('controller');

// Show all questionnaires
dispatcher.route('/questionnaires').get((request, response) => {
    Questionnaire.find((error, questionnaires) => {
        if (error) {
            return response.status(400).send('Database error');
        } else {
            response.status(200).json(questionnaires);
            logger.debug(`Found ${questionnaires.length} questionnaires`);
        }
    });
});

// Show a single questionnaire
dispatcher.route('/questionnaires/:id').get((request, response) => {
    Questionnaire.findById(request.params.id, (error, questionnaire) => {
        if (error) {
            return response.status(400).send('Database error');
        } else {
            response.status(200).json(questionnaire);
            logger.debug(`Found a questionnaire with ID ${questionnaire.id}`);
        }
    });
});

// Create a new questionnaire
dispatcher.route('/questionnaires').post((request, response) => {
    const questionnaire = new Questionnaire();
    questionnaire.title = request.body.title;
    questionnaire.description = request.body.description;
    questionnaire.save((error, fullquestionnaire) => {
        if (error) {
            return response.status(400).send('Database error');
        } else {
            response.status(200).json(fullquestionnaire);
            logger.debug(`Created a questionnaire with ID ${fullquestionnaire.id}`);
        }
    });
});

// Update a questionnaire
dispatcher.route('/questionnaires/:id').put((request, response) => {
    Questionnaire.findById(request.params.id, (error, questionnaire) => {
        if (error) {
            return response.status(400).send('Database error');
        } else {
            questionnaire.title = request.body.title;
            questionnaire.description = request.body.description;
            questionnaire.save((error, fullquestionnaire) => {
                if (error) {
                    return response.status(400).send('Database error');
                } else {
                    response.status(200).json(fullquestionnaire);
                    logger.debug(`Updated a questionnaire with ID ${fullquestionnaire.id}`);
                }
            });
        }
    });
});

// Delete a questionnaire
dispatcher.route('/questionnaires/:id').delete((request, response) => {
    Questionnaire.deleteOne({_id: request.params.id}, (error) => {
        if (error) {
            return response.status(400).send('Database error');
        } else {
            response.send();
            logger.debug(`Removed a questionnaire with ID ${request.params.id}`);
        }
    });
});

// export dispatcher to be able to use it outside of this module
module.exports = dispatcher;
