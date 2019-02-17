<?php

require_once("Globals.php");
require_once("plugins/PluginManager.php");
require_once("plugins/Plugin.php");
require_once("router/RouteManager.php");
require_once("router/Route.php");
require_once("database/DatabaseManager.php");
require_once("data/DataManager.php");
require_once("data/Data.php");
require_once("components/container/Container.php");
require_once("components/model/Repository.php");
require_once("components/model/Entity.php");
require_once("components/view/View.php");
require_once("components/view/ModelView.php");
require_once("components/view/RedirectView.php");
require_once("components/view/HttpView.php");
require_once("components/controller/Controller.php");
require_once("configuration/Configuration.php");
require_once("utils/Utils.php");

class Application
{
    private $pluginmanager;

    private $routemanager;

    private $container;

    public function __construct($configuration)
    {
        $datamanager = new DataManager();
        $databasemanager = new DatabaseManager();
        $this->container = new Container($datamanager, $databasemanager);
        $this->pluginmanager = new PluginManager($this->container, $configuration);
        $this->routemanager = new RouteManager($configuration);
    }

    public function handleRequest()
    {
        $route = $this->routemanager->getRoute(Utils::getServer("REQUEST_URI"));
        $view = $this->routemanager->executeRoute($route, $this->pluginmanager->getPlugins());
        if ($view && $view instanceof View) {
            $view->executeView($this->container);
        }
    }
}

?>
