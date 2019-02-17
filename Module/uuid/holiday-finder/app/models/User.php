<?php

use Doctrine\Common\Collections\ArrayCollection;

/**
 * @Entity
 * @Table
 */
class User
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
    protected $email;

    /**
     * @Column(type="string");
     */
    protected $password;

    /**
     * @Column(type="string");
     */
    protected $name;

    /**
     * @Column(type="string");
     */
    protected $address;

    /**
     * @Column(type="string");
     */
    protected $zip;

    /**
     * @Column(type="string");
     */
    protected $town;

    /**
     * @OneToMany(targetEntity = "Booking", mappedBy = "user")
     */
    protected $bookings;

    public function __construct()
    {
        $this->bookings = new \Doctrine\Common\Collections\ArrayCollection();
    }

    public function getId()
    {
        return $this->id;
    }

    public function getEmail()
    {
        return $this->email;
    }

    public function setEmail($email)
    {
        $this->email = $email;
    }

    public function getPassword()
    {
        return $this->password;
    }

    public function setPassword($password)
    {
        $this->password = $password;
    }

    public function getName()
    {
        return $this->name;
    }

    public function setName($name)
    {
        $this->name = $name;
    }

    public function getAddress()
    {
        return $this->address;
    }

    public function setAddress($address)
    {
        $this->address = $address;
    }

    public function getZip()
    {
        return $this->zip;
    }

    public function setZip($zip)
    {
        $this->zip = $zip;
    }

    public function getTown()
    {
        return $this->town;
    }

    public function setTown($town)
    {
        $this->town = $town;
    }

    public function getBookings()
    {
        return $this->bookings;
    }

    public function addBooking($booking)
    {
        $this->bookings[] = $booking;
    }
}
