<?php

$postid = $_POST["post_id"];

require "database_connection.php";

$query = "select * from comments where 	post_id ='".$postid."';";
$result  = mysqli_query($con,$query);

if(mysqli_num_rows($result)>0){
	$response = array();
	$code = "true";
	$message = "Uploading Comments...";

	
	while($rows = mysqli_fetch_array($result)){
	//$commentid=$rows[0];
	$user_id = $rows[1];
	//$post_id = $rows[2];
	$comment_time = $rows[3];
	$data = $rows[4];
	
	array_push($response,array("code"=>$code,"message"=>$message,"user_id"=>$user_id,"comment_time"=>$comment_time,"comment_data"=>$data));
	
	}
	
	echo json_encode(array("server_response"=>$response));
	
	}
	
	else{
	$response = array();
	$code = "false";
	$message = "There is no Comment...";
	
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	
}

?>