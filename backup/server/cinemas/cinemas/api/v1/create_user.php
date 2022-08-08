<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$email = $_POST['email'];
$password = $_POST['password'];

$sql = "INSERT INTO cinemas_users(email, password) VALUES('$email', MD5('$password'))";
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