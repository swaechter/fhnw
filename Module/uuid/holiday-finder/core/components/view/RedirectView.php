<?php

class RedirectView extends View
{
    private $url;

    public function __construct($url)
    {
        $this->url = $url;
    }

    public function executeView($container)
    {
        header("Location: " . $this->url);
    }
}

?>
