<?php

class UserRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function getUsers()
    {
        return $this->getEntityManager()->getRepository('User')->findAll();
    }

    public function getUserByEmail($email)
    {
        $users = $this->getUsers();
        foreach ($users as $user) {
            if (strcmp($user->getEmail(), $email) == 0) {
                return $user;
            }
        }
        return null;
    }

    public function hasUserByCredentials($email, $password)
    {
        $users = $this->getUsers();
        foreach ($users as $user) {
            if (strcmp($user->getEmail(), $email) == 0 && strcmp($user->getPassword(), sha1($password)) == 0) {
                return true;
            }
        }
        return false;
    }

    public function hasUserByEmail($email)
    {
        $users = $this->getUsers();
        foreach ($users as $user) {
            if (strcmp($user->getEmail(), $email) == 0) {
                return true;
            }
        }
        return false;
    }

    public function addUser($emaila, $emailb, $passworda, $passwordb, $name, $address, $zip, $town)
    {
        if (strcmp($emaila, $emailb) != 0) {
            return null;
        }

        if (strcmp($passworda, $passwordb) != 0) {
            return null;
        }

        if ($this->hasUserByEmail($emaila)) {
            return null;
        }

        $user = new User();
        $user->setEmail($emaila);
        $user->setPassword(sha1($passworda));
        $user->setName($name);
        $user->setAddress($address);
        $user->setZip($zip);
        $user->setTown($town);

        $this->getEntityManager()->persist($user);
        $this->getEntityManager()->flush();

        return $user;
    }
}

?>
