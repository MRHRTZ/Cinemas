<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$studio_id = $_POST['studio_id'];
$studio_seat = $_POST['studio_seat'];

$sql = "UPDATE cinemas_studio SET studio_seat='$studio_seat' WHERE studio_id='$studio_id'";

$resultJSON = new stdClass();

if (mysqli_query($conn, $sql)) {
	$resultJSON->status = true;  
  	$resultJSON->message = "1 Record updated successfully";
	echo json_encode($resultJSON);
} else {
    $resultJSON->status = false;  
  	$resultJSON->message = "Error: " . mysqli_error($conn);
	echo json_encode($resultJSON);
}
$conn->close();
?>