<?php

class HolidayController
{
    private $adminrepository;

    private $userrepository;

    private $holidayrepository;

    private $ratingrepository;

    private $bookingrepository;

    private $searchrepository;

    public function __construct($container)
    {
        $this->adminrepository = new AdminRepository($container);
        $this->userrepository = new UserRepository($container);
        $this->holidayrepository = new HolidayRepository($container);
        $this->ratingrepository = new RatingRepository($container);
        $this->bookingrepository = new BookingRepository($container);
        $this->searchrepository = new SearchRepository($container);
    }

    public function home()
    {
        $search = $this->searchrepository->getSearch();
        return new ModelView("holiday-home", array("search" => $search));
    }

    public function submitsearch()
    {
        $this->searchrepository->setSearch();
        return new HttpView(200);
    }

    public function normalsearch()
    {
        $search = $this->searchrepository->getSearch();
        $holidays = $this->holidayrepository->getSuitableHolidays($search);
        return new ModelView("holiday-normalsearch", array("search" => $search, "holidays" => $holidays));
    }

    public function surprisesearch()
    {
        $search = $this->searchrepository->getSearch();
        $holidays = $this->holidayrepository->getSuitableHolidays($search);
        return new ModelView("holiday-surprisesearch", array("search" => $search, "holidays" => $holidays));
    }

    public function normalholiday()
    {
        if (Utils::hasGet("0")) {
            $search = $this->searchrepository->getSearch();
            $holiday = $this->holidayrepository->getHolidayById(Utils::getGet("0"));
            if ($holiday) {
                return new ModelView("holiday-normalholiday", array("search" => $search, "holiday" => $holiday));
            } else {
                return new RedirectView("/holiday/home");
            }
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function normalbook()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            if (Utils::hasGet("0")) {
                $search = $this->searchrepository->getSearch();
                $holiday = $this->holidayrepository->getHolidayById(Utils::getGet("0"));
                if ($holiday) {
                    return new ModelView("holiday-normalbook", array("search" => $search, "holiday" => $holiday));
                } else {
                    return new RedirectView("/holiday/home");
                }
            } else {
                return new RedirectView("/holiday/home");
            }
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function surprisebook()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            $search = $this->searchrepository->getSearch();
            return new ModelView("holiday-surprisebook", array("search" => $search));
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function submitnormalbook()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            if (Utils::hasGet("0") && Utils::hasPost("startdate") && Utils::hasPost("enddate") && Utils::hasPost("person") && Utils::hasPost("vendor") && Utils::hasPost("expiringdate") && Utils::hasPost("number") && Utils::hasPost("cvc")) {
                $email = $this->adminrepository->getUserEmail();
                if ($email) {
                    $holiday = $this->holidayrepository->getHolidayById(Utils::getGet("0"));
                    $user = $this->userrepository->getUserByEmail($email);
                    if ($holiday && $user) {
                        $rating = $this->ratingrepository->addRating($holiday);
                        if ($rating) {
                            if ($this->bookingrepository->addBooking(Utils::getPost("startdate"),
                                Utils::getPost("enddate"), Utils::getPost("person"), Utils::getPost("number"), false,
                                $holiday, $rating, $user)
                            ) {
                                return new ModelView("holiday-submitnormalbook", array("holiday" => $holiday));
                            }
                        } else {
                            return new RedirectView("/holiday/home");
                        }
                    } else {
                        return new RedirectView("/holiday/home");
                    }
                } else {
                    return new RedirectView("/holiday/home");
                }
            } else {
                return new RedirectView("/holiday/home");
            }
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function submitsurprisebook()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            if (Utils::hasPost("startdate") && Utils::hasPost("enddate") && Utils::hasPost("person") && Utils::hasPost("vendor") && Utils::hasPost("expiringdate") && Utils::hasPost("number") && Utils::hasPost("cvc")) {
                $email = $this->adminrepository->getUserEmail();
                if ($email) {
                    $holiday = $this->holidayrepository->getHolidayById(1);
                    $user = $this->userrepository->getUserByEmail($email);
                    if ($holiday && $user) {
                        $rating = $this->ratingrepository->addRating($holiday);
                        if ($rating) {
                            if ($this->bookingrepository->addBooking(Utils::getPost("startdate"),
                                Utils::getPost("enddate"), Utils::getPost("person"), Utils::getPost("number"), true,
                                $holiday, $rating, $user)
                            ) {
                                return new ModelView("holiday-submitsurprisebook", array("holiday" => $holiday));
                            }
                        } else {
                            return new RedirectView("/holiday/home");
                        }
                    } else {
                        return new RedirectView("/holiday/home");
                    }
                } else {
                    return new RedirectView("/holiday/home");
                }
            } else {
                return new RedirectView("/holiday/home");
            }
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function holidays()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            $email = $this->adminrepository->getUserEmail();
            if ($email) {
                $user = $this->userrepository->getUserByEmail($email);
                if ($user) {
                    $bookings = $this->bookingrepository->getBookingsByUser($user);
                    return new ModelView("holiday-holidays", array("bookings" => $bookings));
                } else {
                    return new RedirectView("/holiday/home");
                }
            } else {
                return new RedirectView("/holiday/home");
            }
        } else {
            return new RedirectView("/holiday/home");
        }
    }

    public function submitholidays()
    {
        if ($this->adminrepository->isUserLoggedIn()) {
            if (Utils::hasGet("0") && Utils::hasPost("stars") && Utils::hasPost("comment")) {
                $email = $this->adminrepository->getUserEmail();
                if ($email) {
                    $booking = $this->bookingrepository->getBookingById(Utils::getGet("0"));
                    if ($booking) {
                        $rating = $booking->getRating();
                        if ($rating) {
                            if ($this->ratingrepository->updateRating($rating, Utils::getPost("comment"),
                                Utils::getPost("stars"))
                            ) {
                                return new HttpView(200);
                            }
                        } else {
                            return new HttpView(500);
                        }
                    } else {
                        return new HttpView(500);
                    }
                } else {
                    return new HttpView(500);
                }
            } else {
                return new HttpView(500);
            }
        } else {
            return new HttpView(500);
        }
    }
}

?>
