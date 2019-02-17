<?php

class UserController
{
    private $adminrepository;

    private $userrepository;

    public function __construct($container)
    {
        $this->adminrepository = new AdminRepository($container);
        $this->userrepository = new UserRepository($container);
    }

    public function login()
    {
        if (Utils::hasPost("email") && Utils::hasPost("password")) {
            if (!$this->adminrepository->isUserLoggedIn()) {
                if ($this->userrepository->hasUserByCredentials(Utils::getPost("email"), Utils::getPost("password"))) {
                    $this->adminrepository->doLogin(Utils::getPost("email"));
                    return new HttpView(200);
                } else {
                    return new HttpView(500);
                }
            } else {
                return new HttpView(200);
            }
        } else {
            return new HttpView(500);
        }
    }

    public function register()
    {
        if (Utils::hasPost("emaila") && Utils::hasPost("emailb") && Utils::hasPost("passworda") && Utils::hasPost("passwordb") && Utils::hasPost("name") && Utils::hasPost("address") && Utils::hasPost("zip") && Utils::hasPost("town")) {
            if (!$this->adminrepository->isUserLoggedIn()) {
                if ($this->userrepository->addUser(Utils::getPost("emaila"), Utils::getPost("emailb"),
                    Utils::getPost("passworda"), Utils::getPost("passwordb"),
                    Utils::getPost("name"), Utils::getPost("address"),
                    Utils::getPost("zip"), Utils::getPost("town"))
                ) {
                    $this->adminrepository->doLogin(Utils::getPost("emaila"));
                    return new HttpView(200);
                } else {
                    return new HttpView(500);
                }
            } else {
                return new HttpView(200);
            }
        } else {
            return new HttpView(500);
        }
    }

    public function logout()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            $this->adminrepository->doLogout();
            return new HttpView(200);
        } else {
            return new HttpView(500);
        }
    }

    public function isloggedin()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            return new HttpView(200);
        } else {
            return new HttpView(500);
        }
    }
}

?>
