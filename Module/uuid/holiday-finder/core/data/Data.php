<?php

class Data
{
    private $entries;

    public function __construct()
    {
        $this->entries = array();
    }

    public function addEntry($key, $value)
    {
        $this->entries[$key] = $value;
    }

    public function getEntries()
    {
        return $this->entries;
    }
}

?>
