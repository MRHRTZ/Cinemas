<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$order_id = $_GET['order_id'];

$sql = "DELETE FROM cinemas_orders WHERE order_id = '$order_id'";
$resultJSON = new stdClass();

if (mysqli_query($conn, $sql)) {
	$resultJSON->status = true;  
  	$resultJSON->message = "1 Record deleted successfully";
	echo json_encode($resultJSON);
} else {
    $resultJSON->status = false;  
  	$resultJSON->message = "Error: " . mysqli_error($conn);
	echo json_encode($resultJSON);
}
$conn->close();
?>