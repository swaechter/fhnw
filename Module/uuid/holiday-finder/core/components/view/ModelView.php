<?php

class ModelView extends View
{
    private $name;

    private $data;

    public function __construct($name, $data = array())
    {
        $this->name = $name;
        $this->data = $data;
    }

    public function executeView($container)
    {
        $loader = new Twig_Loader_Filesystem(Globals::$APPLICATION_DIRECTORY . Globals::$DIRECTORY_SEPARATOR . Globals::$VIEW_DIRECTORY);
        $environment = new Twig_Environment($loader, array("debug" => true));
        $environment->addExtension(new Twig_Extension_Debug());

        $template = $environment->loadTemplate($this->name . Globals::$TEMPLATE_EXTENSION);
        $renderdata = array_merge($container->getDataManager()->getData()->getEntries(), $this->data);
        echo($template->render($renderdata));
    }
}

?>
