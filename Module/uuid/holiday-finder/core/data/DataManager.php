<?php

class DataManager
{
    public function __construct()
    {
        Utils::createSession();
    }

    public function isUserLoggedIn()
    {
        return Utils::getSession("UserStatus");
    }

    public function setLogin($email)
    {
        Utils::setSession("UserStatus", true);
        Utils::setSession("UserEmail", $email);
    }

    public function setLogout()
    {
        $_SESSION = array();
        session_unset();
    }

    public function getEmail()
    {
        return Utils::getSession("UserEmail");
    }

    public function getData()
    {
        $data = new Data();
        foreach ($_SESSION as $key => $value) {
            $data->addEntry($key, $value);
        }
        return $data;
    }
}

?>
