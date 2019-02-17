<?php

class Configuration
{
    private $applicationdirectory;

    private $defaulturi;

    public function __construct($applicationdirectory, $defaulturi)
    {
        $this->applicationdirectory = $applicationdirectory;
        $this->defaulturi = $defaulturi;
    }

    public function getApplicationDirectory()
    {
        return $this->applicationdirectory;
    }

    public function getDefaultUri()
    {
        return $this->defaulturi;
    }
}

?>
