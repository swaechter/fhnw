<?php

class HttpView extends View
{
    private $code;

    public function __construct($code)
    {
        $this->code = $code;
    }

    public function executeView($container)
    {
        header('HTTP', true, $this->code);
    }
}

?>
