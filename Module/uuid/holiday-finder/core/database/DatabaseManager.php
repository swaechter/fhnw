<?php

use Doctrine\ORM\Mapping;
use Doctrine\ORM\Tools\Setup;
use Doctrine\ORM\Tools\SchemaTool;
use Doctrine\ORM\EntityManager;

class DatabaseManager
{
    private $entitymanager;

    public function __construct()
    {
        $config = Setup::createAnnotationMetadataConfiguration(array("app"));
        $connection = array('driver' => 'pdo_sqlite', 'path' => Globals::$DATABASE_NAME);
        $this->entitymanager = \Doctrine\ORM\EntityManager::create($connection, $config);

        $classes = $this->entitymanager->getMetadataFactory()->getAllMetadata();
        $schematool = new SchemaTool($this->entitymanager);
        $schematool->updateSchema($classes);
    }

    public function getEntityManager()
    {
        return $this->entitymanager;
    }
}

?>
