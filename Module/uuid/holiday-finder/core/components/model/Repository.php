<?php

abstract class Repository
{
    private $container;

    public function __construct($container)
    {
        $this->container = $container;
    }

    public function getDataManager()
    {
        return $this->container->getDataManager();
    }

    public function getDatabaseManager()
    {
        return $this->container->getDatabaseManager();
    }

    public function getEntityManager()
    {
        return $this->container->getDatabaseManager()->getEntityManager();
    }
}

?>
