<?php

class Utils
{
    public static function hasGet($key)
    {
        return isset($_GET[$key]) && strlen(trim($_GET[$key])) > 0 ? true : false;
    }

    public static function getGet($key)
    {
        return isset($_GET[$key]) ? $_GET[$key] : null;
    }

    public static function setGet($key, $value)
    {
        $_GET[$key] = $value;
    }

    public static function hasPost($key)
    {
        return isset($_POST[$key]) && strlen(trim($_POST[$key])) > 0 ? true : false;
    }

    public static function getPost($key)
    {
        return isset($_POST[$key]) ? $_POST[$key] : null;
    }

    public static function setPost($key, $value)
    {
        $_POST[$key] = $value;
    }

    public static function hasCookie($key)
    {
        return isset($_COOKIE[$key]) && strlen(trim($_COOKIE[$key])) > 0 ? true : false;
    }

    public static function getCookie($key)
    {
        return isset($_COOKIE[$key]) ? $_COOKIE[$key] : null;
    }

    public static function setCookie($key, $value)
    {
        setcookie($key, $value, time() + 60 * 60 * 10, "/", "", 0);
    }

    public static function hasSession($key)
    {
        return isset($_SESSION[$key]) && strlen(trim($_SESSION[$key])) > 0 ? true : false;
    }

    public static function getSession($key)
    {
        return isset($_SESSION[$key]) ? $_SESSION[$key] : null;
    }

    public static function setSession($key, $value)
    {
        $_SESSION[$key] = $value;
    }

    public static function createSession()
    {
        session_start();
    }

    public static function destroySession()
    {
        $_SESSION = array();
        session_unset();
    }

    public static function getServer($key)
    {
        return isset($_SERVER[$key]) ? $_SERVER[$key] : null;
    }
}

?>
