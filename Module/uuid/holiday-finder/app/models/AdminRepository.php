<?php

class AdminRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function isUserLoggedIn()
    {
        return $this->getDataManager()->isUserLoggedIn();
    }

    public function doLogin($email)
    {
        $this->getDataManager()->setLogin($email);
    }

    public function doLogout()
    {
        $this->getDataManager()->setLogout();
    }

    public function getUserEmail()
    {
        return $this->getDataManager()->getEmail();
    }
}

?>
