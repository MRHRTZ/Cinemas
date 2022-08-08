<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$sql = "SELECT * FROM cinemas_users";
$result = $conn->query($sql);
$arr = array();

if ($result->num_rows > 0) {
  // output data of each row
    while($row =mysqli_fetch_assoc($result))
    {
        $arr[] = $row;
    }
  echo json_encode($arr);
} else {
  echo json_encode($arr);;
}
$conn->close();
?>