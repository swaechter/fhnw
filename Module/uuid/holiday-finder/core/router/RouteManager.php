<?php

class RouteManager
{
    private $configuration;

    function __construct($configuration)
    {
        $this->configuration = $configuration;
    }

    public function getRoute($uri)
    {
        // Remove the leading slash
        if (substr($uri, 0, strlen("/")) == "/") {
            $uri = substr($uri, strlen("/"));
        }

        // Trim the URI
        $uri = ltrim($uri, "/");

        // If the URI is empty, use the default URI
        if (empty($uri)) {
            $uri = $this->configuration->getDefaultUri();
        }

        // Remove the leading slash
        if (substr($uri, 0, strlen("/")) == "/") {
            $uri = substr($uri, strlen("/"));
        }

        // Check if the controller name and action name exist
        if (count(array_filter(explode("/", $uri))) < 2) {
            $uri = $this->configuration->getDefaultUri();
        }

        // Explode the URI and get the controller name and action name
        $params = array_filter(explode("/", $uri));
        $controllername = $params[0];
        $actionname = $params[1];

        // Set all data elements
        for ($i = 2; $i < count($params); $i++) {
            Utils::setGet($i - 2, $params[$i]);
        }

        // Return the route
        return new Route($controllername, $actionname);
    }

    public function executeRoute($route, $plugins)
    {
        foreach ($plugins as $plugin) {
            foreach ($plugin->getControllers() as $controller) {
                $controllerclassname = $route->getControllerName() . "Controller";
                if (class_exists($controllerclassname) && strcasecmp(get_class($controller),
                        $controllerclassname) == 0
                ) {
                    $actionname = $route->getActionName();
                    if (method_exists($controllerclassname, $actionname)) {
                        return $controller->$actionname();
                    }
                }
            }
        }
        return null;
    }
}

?>
