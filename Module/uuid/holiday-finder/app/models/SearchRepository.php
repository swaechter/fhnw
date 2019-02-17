<?php

class SearchRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function getSearch()
    {
        $search = new Search();
        $search->setStartDate(Utils::getCookie("StartDate"));
        $search->setEndDate(Utils::getCookie("EndDate"));
        $search->setPerson(Utils::getCookie("Person"));
        $search->setContinent(Utils::getCookie("Continent"));
        $search->setActivity(Utils::getCookie("Activity"));
        $search->setAccommodation(Utils::getCookie("Accommodation"));

        return $search;
    }

    public function setSearch()
    {
        Utils::setCookie("StartDate", Utils::getPost("startdate"));
        Utils::setCookie("EndDate", Utils::getPost("enddate"));
        Utils::setCookie("Person", Utils::getPost("person"));
        Utils::setCookie("Continent", Utils::getPost("continent"));
        Utils::setCookie("Activity", Utils::getPost("activity"));
        Utils::setCookie("Accommodation", Utils::getPost("accommodation"));
    }
}

?>
