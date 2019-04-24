<?php
$user_id = $_POST["user_id"];
$postcata = $_POST["post_category"];
$postloc = $_POST["post_location"];
$postdata = $_POST["post_data"];

require "database_connection.php";

$query = "insert into post(user_id,post_location,post_data,post_category) 
	values('".$user_id."','".$postloc."','".$postdata."','".$postcata."');";
$result  = mysqli_query($con,$query);

if(!$result){
		
	$response = array();
	$code = "false";
	$message = "Some Server Error Occured   Try Again .....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	else{
		
	$response = array();
	$code = "true";
	$message = "Successful Posted.....!";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	mysqli_close($con);
?>