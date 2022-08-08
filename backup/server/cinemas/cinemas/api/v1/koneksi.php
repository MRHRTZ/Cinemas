<?php
$servername = "localhost";
$username = "mrhrtzco_gararetech";
$password = "g4r4r3t3ch#123";
$database = "mrhrtzco_gararetech";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $database);

// Check connection
if (!$conn) {
  die("Connection failed: " . mysqli_connect_error());
}
 //echo "Connected successfully";
?>