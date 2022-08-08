<?php
require('koneksi.php');
header('Content-Type: application/json; charset=utf-8');

$uid = $_GET['uid'];

$sql = "SELECT * FROM cinemas_orders INNER JOIN cinemas_studio ON cinemas_studio.studio_id = cinemas_orders.studio_id WHERE user_id = $uid ORDER BY cinemas_orders.movie_status, cinemas_orders.show_time";
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