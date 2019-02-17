"use strict";

const log4js = require('log4js');
const dotenv = require('dotenv-extended');
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const mongoose = require('mongoose');
const dispatcher = require('./web/questionnaire-controller');

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

// Install the middleware
app.use(bodyParser.json());
app.use(cors());

// Install the dispatcher
app.use('/flashcard-express', dispatcher);

// Start the application
app.listen(PORT);

// Put a friendly message on the terminal
logger.info(`Server running on ${PORT}`);

module.exports = app;
