<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$email = $_POST['email'];
$description = $_POST['description'];

$sql = "INSERT INTO cinemas_bug_report(email, description) VALUES('$email', '$description')";
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