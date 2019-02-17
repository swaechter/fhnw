"use strict";

const log4js = require('log4js');
const express = require('express');
const dispatcher = express.Router();
const Questionnaire = require('../domain/questionnaire');

// Create a logger
const logger = log4js.getLogger('controller');

/*
 * Just for testing
 * HTTP-GET to '/questionnaires?name=????'
 */
dispatcher.route('/questionnaires')
	.get((req, res) => {
		if (req.query.name) {
			logger.debug(`called with request param 'name=': ` + req.query.name);
			res.status(200).json("Hello to " + req.query.name);
		} else {
			req.next();
		}
});

/*
 * Returns all questionnaires
 * HTTP-GET to '/questionnaires'
 */
dispatcher.route('/questionnaires')
	.get((req, res) => {
	    Questionnaire.find((err, questionnaires) => {
	      if (err) {
	        return res.status(400).send('database error');
	      }
	      logger.debug(`Found ${questionnaires.length} questionnaires`);
	      res.status(200).json(questionnaires);
	    });
	});


/*
 * Returns a given questionnaire
 * HTTP-GET to '/questionnaires/{id}'
 */
dispatcher.route('/questionnaires/:id')
	.get((req, res) => {
		Questionnaire.findById(req.params.id, (err, questionnaire) => {
			if (err) {
				res.status(400).send('database error');
			}
			logger.debug(`Found questionnaire with id "${questionnaire.id}"`);
			res.status(200).json(questionnaire);
		});
	});

/*
 * Creates a new questionnaire
 * HTTP-POST to '/questionnaires'
 */
dispatcher.route('/questionnaires')
	.post((req, res) => {
		// Create a new instance of the Questionnaire model
		let questionnaire = new Questionnaire();
		questionnaire.title = req.body.title;
		questionnaire.description = req.body.description;

		// Save the questionnaire and check for errors
		questionnaire.save((err, questionnaireCreated) => {
			if (err) {
				logger.error(`Could not create new questionnaire`);
				res.status(412).send('database error');
			} else {
				logger.debug(`Successfully created questionnaire with id "${questionnaire.id}"`);
				res.status(201).json(questionnaireCreated);
			}
		});
	});

/*
 * Updates a given questionnaire
 * HTTP-PUT to to '/questionnaires/{id}'
 */
dispatcher.route('/questionnaires/:id')
	.put((req, res) => {
		Questionnaire.findById(req.params.id, (err, questionnaire) => {
			if (err) {
				logger.error(`Could not update questionnaire with id "${req.params.id}"`);
				res.status(404).send('database error');
			}
			questionnaire.title = req.body.title;
			questionnaire.description = req.body.description;

			// Update the questionnaire and check for errors
			questionnaire.save(err => {
				if (err) {
					res.status(404).send('database error');
				}
				logger.debug(`Successfully updated questionnaire with id "${questionnaire.id}"`);
				res.status(200).json(questionnaire);
			});
		});
	});

/*
 * Deletes a given questionnaire
 * HTTP-DELETE to '/questionnaires/{id}'
 */
dispatcher.route('/questionnaires/:id')
	.delete((req, res) => {
		Questionnaire.remove({_id: req.params.id}, (err, questionnaire) => {
			if (err) {
				logger.error(`Could not delete questionnaire with id "${req.params.id}"`);
				res.status(404).send('database error');
			}
			logger.debug(`Successfully deleted questionnaire with id "${req.params.id}"`);
			res.status(200).send();
		});
	});

module.exports = dispatcher;
