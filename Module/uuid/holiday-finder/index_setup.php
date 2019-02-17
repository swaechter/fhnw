<?php

// Inlude the required files
require_once('vendor/autoload.php');
require_once('core/Application.php');

// Delete the old database
unlink("data/Database.sqlite");

// Get the placeholder text
$placeholdertext = file_get_contents("setup/Placeholdertext.txt");

// Create all manager and the container
$datamanager = new DataManager();
$databasemanager = new DatabaseManager();
$container = new Container($datamanager, $databasemanager);

// Create all repositories
$userrepository = new UserRepository($container);
$holidayrepository = new HolidayRepository($container);

// Setup the user data
$users = array(
    array("waechter.simon@gmail.com", "123", "Simon Wächter", "Strasse 42", "4242", "Niergendwohausen")
);

// Create the users
foreach ($users as $user) {
    if (!$userrepository->addUser($user[0], $user[0], $user[1], $user[1], $user[2], $user[3], $user[4], $user[5])) {
        die("Unable to create user.");
    }
}

// Setup the holiday data
$holidays = array(
    array("image.png", "Badeurlaub Barcelona", $placeholdertext, "4", "2", "299.00"),
    array("image.png", "Badeurlaub Bern", $placeholdertext, "4", "2", "350.00"),
    array("image.png", "Skiurlaub Davos", $placeholdertext, "4", "1", "240.00"),
    array("image.png", "Skiurlaub Zermatt", $placeholdertext, "4", "1", "400.00"),
    array("image.png", "Mallorca Partyurlaub", $placeholdertext, "4", "3", "365.00"),
    array("image.png", "Bildungsurlaub New York", $placeholdertext, "6", "4", "365.00")
);

// Create the holidays
foreach ($holidays as $holiday) {
    if (!$holidayrepository->addHoliday($holiday[0], $holiday[1], $holiday[2], $holiday[3], $holiday[4], $holiday[5])) {
        die("Unable to create holiday.");
    }
}

?>
