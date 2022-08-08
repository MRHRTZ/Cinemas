<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$user_id = $_POST['user_id'];
$city_id = $_POST['city_id']; 
$email = $_POST['email'];
$password = $_POST['password'];
$image = $_POST['image'];
$post_type = $_POST['post_type'];

$sql = "";
if ($post_type == 'all') {
	$sql = "UPDATE cinemas_users SET email='$email', city_id='$city_id', password=MD5('$password'), image='$image' WHERE user_id='$user_id'";
} else if ($post_type == 'no_pass') {
	$sql = "UPDATE cinemas_users SET email='$email', city_id='$city_id', image='$image' WHERE user_id='$user_id'";
} else if ($post_type == 'image') {
	$sql = "UPDATE cinemas_users SET image='$image' WHERE user_id='$user_id'";
}

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