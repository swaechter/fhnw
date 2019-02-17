<?php

/**
 * @Entity
 * @Table
 */
class Booking
{
    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue
     */
    protected $id;

    /**
     * @Column(type="string");
     */
    protected $startdate;

    /**
     * @Column(type="string");
     */
    protected $enddate;

    /**
     * @Column(type="string");
     */
    protected $person;

    /**
     * @Column(type="string");
     */
    protected $price;

    /**
     * @Column(type="string");
     */
    protected $number;

    /**
     * @Column(type="boolean");
     */
    protected $surprise;

    /**
     * @ManyToOne(targetEntity="Holiday")
     * @JoinColumn(name="holiday_id", referencedColumnName="id")
     */
    protected $holiday;

    /**
     * @ManyToOne(targetEntity="Rating")
     * @JoinColumn(name="rating_id", referencedColumnName="id")
     */
    protected $rating;

    /**
     * @ManyToOne(targetEntity="User")
     * @JoinColumn(name="user_id", referencedColumnName="id")
     */
    protected $user;

    public function getId()
    {
        return $this->id;
    }

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

    public function getPerson()
    {
        return $this->person;
    }

    public function setPerson($person)
    {
        $this->person = $person;
    }

    public function getPrice()
    {
        return $this->price;
    }

    public function setPrice($price)
    {
        $this->price = $price;
    }

    public function getNumber()
    {
        return $this->number;
    }

    public function setNumber($number)
    {
        $this->number = $number;
    }

    public function getSurprise()
    {
        return $this->surprise;
    }

    public function setSurprise($surprise)
    {
        $this->surprise = $surprise;
    }

    public function getHoliday()
    {
        return $this->holiday;
    }

    public function setHoliday($holiday)
    {
        $this->holiday = $holiday;
    }

    public function getRating()
    {
        return $this->rating;
    }

    public function setRating($rating)
    {
        $this->rating = $rating;
    }

    public function getUser()
    {
        return $this->user;
    }

    public function setUser($user)
    {
        $this->user = $user;
    }
}

?>
