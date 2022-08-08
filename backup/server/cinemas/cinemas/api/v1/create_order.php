<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$order_id = $_POST['order_id'];
$studio_id = $_POST['studio_id'];
$user_id = $_POST['user_id'];
$total_ticket = $_POST['total_ticket'];
$seat = $_POST['seat'];
$tax = $_POST['tax'];
$total = $_POST['total'];
$show_time = $_POST['show_time'];
$movie_id = $_POST['movie_id'];

$sql = "INSERT INTO cinemas_orders(order_id, studio_id, user_id, regular_ticket, chair, tax, total, order_status, movie_status, show_time, movie_id) VALUES('$order_id', '$studio_id', $user_id, $total_ticket, '$seat', $tax, $total, 'success', 'active', $show_time, '$movie_id')";
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