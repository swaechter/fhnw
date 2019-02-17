<?php

class HolidayFinderPlugin extends Plugin
{
    private $controllers = array();

    public function __construct($container)
    {
        $this->controllers[] = new HolidayController($container);
        $this->controllers[] = new UserController($container);
        $this->controllers[] = new HelpController($container);
    }

    public function getControllers()
    {
        return $this->controllers;
    }
}

?>
