<?php

class HelpController
{
    public function __construct($container)
    {
    }

    public function help()
    {
        return new ModelView("help-help");
    }

    public function impressum()
    {
        return new ModelView("help-impressum");
    }

    public function contact()
    {
        return new ModelView("help-contact");
    }
}

?>
