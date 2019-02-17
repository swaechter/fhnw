<?php

require_once('vendor/autoload.php');
require_once('core/Application.php');

$configuration = new Configuration("app/", "/holiday/home");
$application = new Application($configuration);
$application->handleRequest();

?>
