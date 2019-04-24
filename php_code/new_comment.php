<?php
$user_id = $_POST["userid"];
$post_id = $_POST["postid"];
$comm_data = $_POST["data"];


require "database_connection.php";

$query = "insert into comments(user_id,post_id,comment_data) 
	values('".$user_id."','".$post_id."','".$comm_data."');";
$result  = mysqli_query($con,$query);

if(!$result){
		
	$response = array();
	$code = "false";
	$message = "Some Server Error Occured   Comment Failed .....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	else{
		
	$response = array();
	$code = "true";
	$message = "Successful Posted Comment.....!";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);

?>