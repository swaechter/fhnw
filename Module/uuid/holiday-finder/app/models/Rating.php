<?php

/**
 * @Entity
 * @Table
 */
class Rating
{
    /**
     * @Id
     * @Column(type="integer")
     * @GeneratedValue
     */
    protected $id;

    /**
     * @Column(type="boolean");
     */
    protected $active;

    /**
     * @Column(type="string");
     */
    protected $comment;

    /**
     * @Column(type="string");
     */
    protected $stars;

    /**
     * @ManyToOne(targetEntity="Holiday", inversedBy="ratings")
     * @JoinColumn(name="holiday_id", referencedColumnName="id")
     */
    protected $holiday;

    public function getId()
    {
        return $this->id;
    }

    public function getActive()
    {
        return $this->active;
    }

    public function setActive($active)
    {
        $this->active = $active;
    }

    public function getComment()
    {
        return $this->comment;
    }

    public function setComment($comment)
    {
        $this->comment = $comment;
    }

    public function getStars()
    {
        return $this->stars;
    }

    public function setStars($stars)
    {
        $this->stars = $stars;
    }

    public function getHoliday()
    {
        return $this->holiday;
    }

    public function setHoliday($holiday)
    {
        $this->holiday = $holiday;
    }
}

?>
