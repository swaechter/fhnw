<?php

class Route
{
    private $controllername;

    private $actionname;

    public function __construct($controllername, $actionname)
    {
        $this->controllername = $controllername;
        $this->actionname = $actionname;
    }

    public function getControllerName()
    {
        return $this->controllername;
    }

    public function getActionName()
    {
        return $this->actionname;
    }
}

?>
