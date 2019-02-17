"use strict";

const log4js = require('log4js');
const dotenv = require('dotenv-extended');
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const dispatcher = require('./web/questionnaire-controller');


// Read the properties from file '.env' and '.env.defaults'
dotenv.load({silent: true});

// Configure log4js based on the definitions in file 'log4js.json'
log4js.configure('log4js.json');
const logger = log4js.getLogger('app');

// Use native promises, needed with Mongoose since v4.1.0
mongoose.Promise = global.Promise;

// Connect to the database using the connection parameters found in the property-files
const url = 'mongodb://' + process.env.MONGO_HOST + '/' + process.env.MONGO_DATABASE;
logger.debug(`Database URL used "${url}"`);
mongoose.connect(url, { useNewUrlParser: true });

// Create the Express Server App
const app = express();

// Configure body-parser. The parser handles the JSON payload.
app.use(bodyParser.json());

// Enable CORS (for all requests)
app.use(cors());

// Configure the dispatcher with all its routes
app.use('/flashcard-express', dispatcher);


// Read PORT from the configuration, default to 9090
const PORT = process.env.PORT || 9090;

// Start the App as HTTP server
app.listen(PORT);

// Use backquotes for the es6 feature
logger.info(`Server started on port ${PORT}`);

module.exports = app;
