require('dotenv-extended').load();

const http = require('http');
const log4js = require('log4js');

const hostname = process.env.SERVER_HOSTNAME;
const port = process.env.SERVER_PORT;
const logger = log4js.getLogger();

logger.level = 'debug';

const server = http.createServer((request, response) => {
    logger.debug("Received request");
    response.statusCode = 200;
    response.setHeader('Content-Type', 'text/plain');
    response.end('Hello World\n');
});

server.listen(port, hostname, () => {
    logger.debug(`Server running at http://${hostname}:${port}/`);
});
