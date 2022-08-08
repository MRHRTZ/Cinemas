<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$studio_id = $_POST['studio_id'];
$studio_hall = $_POST['studio_hall'];
$theater_name = $_POST['theater_name'];
$studio_seat = $_POST['studio_seat'];


$sql = "INSERT INTO cinemas_studio(studio_id, studio_hall, theater_name, studio_seat) VALUES('$studio_id', '$studio_hall', '$theater_name', '$studio_seat')";
$resultJSON = new stdClass();

if (mysqli_query($conn, $sql)) {
	$resultJSON->status = true;  
  	$resultJSON->message = "New record created successfully";
	echo json_encode($resultJSON);
} else {
    $resultJSON->status = false;  
  	$resultJSON->message = "Error: " . $sql . "\n" . mysqli_error($conn);
	echo json_encode($resultJSON);
}
$conn->close();
?>