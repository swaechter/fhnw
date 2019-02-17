<?php

class HolidayRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function getHolidays()
    {
        return $this->getEntityManager()->getRepository('Holiday')->findAll();
    }

    public function getHolidayById($id)
    {
        $holidays = $this->getHolidays();
        foreach ($holidays as $holiday) {
            if (strcmp($holiday->getId(), $id) == 0) {
                return $holiday;
            }
        }
        return null;
    }

    public function getSuitableHolidays($search)
    {
        $suitableholidays = array();
        $holidays = $this->getHolidays();
        foreach ($holidays as $holiday) {
            if (!empty($search->getContinent()) && $holiday->getContinent() != $search->getContinent()) {
                continue;
            }

            if (!empty($search->getActivity()) && $holiday->getActivity() != $search->getActivity()) {
                continue;
            }

            $suitableholidays[] = $holiday;
        }
        return $suitableholidays;
    }

    public function addHoliday($image, $name, $description, $continent, $activity, $price)
    {
        $holiday = new Holiday();
        $holiday->setImage($image);
        $holiday->setName($name);
        $holiday->setDescription($description);
        $holiday->setContinent($continent);
        $holiday->setActivity($activity);
        $holiday->setPrice($price);
        $holiday->setStars("3");

        $this->getEntityManager()->persist($holiday);
        $this->getEntityManager()->flush();

        return $holiday;
    }
}

?>
