<?php

class Container
{
    private $datamanager;

    private $databasemanager;

    public function __construct($datamanager, $databasemanager)
    {
        $this->datamanager = $datamanager;
        $this->databasemanager = $databasemanager;
    }

    public function getDataManager()
    {
        return $this->datamanager;
    }

    public function getDatabaseManager()
    {
        return $this->databasemanager;
    }
}

?>
