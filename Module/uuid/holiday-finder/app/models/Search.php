<?php

class Search
{
    private $startdate;

    private $enddate;

    private $person;

    private $continent;

    private $activities;

    private $accommodation;

    public function getStartDate()
    {
        return $this->startdate;
    }

    public function setStartDate($startdate)
    {
        $this->startdate = $startdate;
    }

    public function getEndDate()
    {
        return $this->enddate;
    }

    public function setEndDate($enddate)
    {
        $this->enddate = $enddate;
    }

    public function getPersons()
    {
        return array(
            "1" => "1",
            "2" => "2",
            "3" => "3",
            "4" => "4",
            "5" => "5",
            "6" => "6",
            "7" => "7",
            "8" => "8",
            "9" => "9",
            "10" => "10"
        );
    }

    public function getPerson()
    {
        return $this->person;
    }

    public function setPerson($person)
    {
        $this->person = $person;
    }

    public function getContinents()
    {
        return array(
            "1" => "Antarktis",
            "2" => "Australien",
            "3" => "Afrika",
            "4" => "Europa",
            "5" => "Asien",
            "6" => "Nordamerika",
            "7" => "Südamerika"
        );
    }

    public function getContinent()
    {
        return $this->continent;
    }

    public function setContinent($continent)
    {
        $this->continent = $continent;
    }

    public function getActivities()
    {
        return array(
            "1" => "Ski",
            "2" => "Baden",
            "3" => "Party",
            "4" => "Bildung"
        );
    }

    public function getActivity()
    {
        return $this->activities;
    }

    public function setActivity($activities)
    {
        $this->activities = $activities;
    }

    public function getAccommodations()
    {
        return array(
            "1" => "1 Stern",
            "2" => "2 Sterne",
            "3" => "3 Sterne",
            "4" => "4 Sterne",
            "5" => "5 Sterne"
        );
    }

    public function getAccommodation()
    {
        return $this->accommodation;
    }

    public function setAccommodation($accommodation)
    {
        $this->accommodation = $accommodation;
    }
}

?>
