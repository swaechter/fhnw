<?php

class BookingRepository extends Repository
{
    public function __construct($container)
    {
        parent::__construct($container);
    }

    public function getBookings()
    {
        return $this->getEntityManager()->getRepository('Booking')->findAll();
    }

    public function getBookingById($id)
    {
        $bookings = $this->getBookings();
        foreach ($bookings as $booking) {
            if ($booking->getId() == $id) {
                return $booking;
            }
        }
        return null;
    }

    public function getBookingsByUser($user)
    {
        $userbookings = array();
        $bookings = $this->getBookings();
        foreach ($bookings as $booking) {
            if ($booking->getUser()->getId() == $user->getId()) {
                $userbookings[] = $booking;
            }
        }
        return $userbookings;
    }

    public function addBooking($startdate, $enddate, $person, $number, $surprise, $holiday, $rating, $user)
    {
        $booking = new Booking();
        $booking->setStartDate($startdate);
        $booking->setEndDate($enddate);
        $booking->setPerson($person);
        $booking->setPrice($holiday->getPrice());
        $booking->setNumber($number);
        $booking->setSurprise($surprise);
        $booking->setHoliday($holiday);
        $booking->setRating($rating);
        $booking->setUser($user);

        $this->getEntityManager()->persist($booking);
        $this->getEntityManager()->flush();

        return $booking;
    }
}

?>
