<?php

class RatingRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function addRating($holiday)
    {
        $rating = new Rating();
        $rating->setActive(false);
        $rating->setComment("Der Kommentar wurde noch nicht freigeschaltet");
        $rating->setStars(0);
        $rating->setHoliday($holiday);

        $this->getEntityManager()->persist($rating);
        $this->getEntityManager()->flush();

        return $rating;
    }

    public function updateRating($rating, $comment, $stars)
    {
        $rating->setActive(true);
        $rating->setComment($comment);
        $rating->setStars($stars);

        $this->getEntityManager()->flush();

        return true;
    }
}

?>
