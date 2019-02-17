"use strict";

const log4js = require('log4js');
const dotenv = require('dotenv-extended');
const express = require('express');
const mongoose = require('mongoose');
const Questionnaire = require('./domain/questionnaire');


// Read the properties from file '.env' and '.env.defaults'.
// silent: true - no log message is shown when missing the .env or .env.defaults files.
dotenv.load({silent: true});
const PORT = process.env.PORT || 9090;

// Create and configure the logger
const logger = log4js.getLogger('server');
log4js.configure('log4js.json');

// Establish the database connection
const url = 'mongodb://' + process.env.MONGO_HOST + '/' + process.env.MONGO_DATABASE;
mongoose.connect(url, {
    useNewUrlParser: true
});

// Create the app
const app = express();

// Define a welcome router
app.get('/', (request, response) => {
    Questionnaire.find((err, questionnaires) => {
        if (err) {
            return response.status(400).send('database error');
        } else {
            response.status(200).json(questionnaires);
            logger.debug(`Found ${questionnaires.length} questionnaires`);
        }
    });
});

// Start the application
app.listen(PORT);

// Put a friendly message on the terminal
logger.info(`Server running on ${PORT}`);
