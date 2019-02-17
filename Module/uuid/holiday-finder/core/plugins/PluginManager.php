<?php

class PluginManager
{
    private $plugins;

    function __construct($container, $configuration)
    {
        // Create the plugin array
        $this->plugins = array();

        // Get all PHP files
        $directories = new RecursiveDirectoryIterator($configuration->getApplicationDirectory());
        $iterator = new RecursiveIteratorIterator($directories);
        $regex = new RegexIterator($iterator, "%\.php$%i");

        // Include all files
        foreach ($regex as $file) {
            include_once($file->getPathname());
        }

        // Check all classes
        foreach (get_declared_classes() as $classname) {
            // Add all plugins
            if (is_subclass_of($classname, "Plugin")) {
                $this->plugins[] = new $classname($container);
            }
        }
    }

    public function getPlugins()
    {
        return $this->plugins;
    }
}

?>
