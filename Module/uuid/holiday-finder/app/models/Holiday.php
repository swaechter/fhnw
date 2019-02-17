<?php

use Doctrine\Common\Collections\ArrayCollection;

/**
 * @Entity
 * @Table
 */
class Holiday
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
    protected $image;

    /**
     * @Column(type="string");
     */
    protected $name;

    /**
     * @Column(type="string");
     */
    protected $description;

    /**
     * @Column(type="string");
     */
    protected $continent;

    /**
     * @Column(type="string");
     */
    protected $activity;

    /**
     * @Column(type="string");
     */
    protected $price;

    /**
     * @Column(type="string");
     */
    protected $stars;

    /**
     * @OneToMany(targetEntity = "Rating", mappedBy = "holiday")
     */
    protected $ratings;

    public function __construct()
    {
        $this->ratings = new \Doctrine\Common\Collections\ArrayCollection();
    }

    public function getId()
    {
        return $this->id;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image)
    {
        $this->image = $image;
    }

    public function getName()
    {
        return $this->name;
    }

    public function setName($name)
    {
        $this->name = $name;
    }

    public function getDescription()
    {
        return $this->description;
    }

    public function setDescription($description)
    {
        $this->description = $description;
    }

    public function getContinent()
    {
        return $this->continent;
    }

    public function setContinent($continent)
    {
        $this->continent = $continent;
    }

    public function getActivity()
    {
        return $this->activity;
    }

    public function setActivity($activity)
    {
        $this->activity = $activity;
    }

    public function getPrice()
    {
        return $this->price;
    }

    public function setPrice($price)
    {
        $this->price = $price;
    }

    public function getStars()
    {
        return $this->stars;
    }

    public function setStars($stars)
    {
        $this->stars = $stars;
    }

    public function getRatings()
    {
        return $this->ratings;
    }

    public function addRating($rating)
    {
        $this->ratings[] = $rating;
    }
}

?>
