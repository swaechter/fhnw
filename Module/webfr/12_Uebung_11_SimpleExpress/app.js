require('dotenv-extended').load();

const log4js = require('log4js');
const logger = log4js.getLogger();
logger.level = 'debug';

const port = process.env.SERVER_PORT;

const express = require('express');
const app = express();

app.get('/', function (request, response) {
    logger.debug("Request received");
    response.send("Hello world!");
});

app.listen(port);
