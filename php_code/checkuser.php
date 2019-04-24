<?php
$user_type = $_POST["usertype"];
$user_id = $_POST["userid"];

require "database_connection.php";

if($user_type=="volunteer"){
	
	$query = "select * from volunteer where user_id = '".$user_id."';";
	$result  = mysqli_query($con,$query);
	$query1 = "select * from organization where user_id = '".$user_id."';";
	$result1  = mysqli_query($con,$query1);
	$query2 = "select * from donor where user_id = '".$user_id."';";
	$result2  = mysqli_query($con,$query2);
	
	if(mysqli_num_rows($result)>0 || mysqli_num_rows($result1)>0  || mysqli_num_rows($result2)>0){
	$response = array();
	$code = "true";
	$message = "User ID Already Exist....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	//echo "User Already Exist....";
	
}

else{
	$response = array();
	$code = "false";
	$message = "New User Id....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
}

	
}

if($user_type=="organization"){
	
	$query = "select * from volunteer where user_id = '".$user_id."';";
	$result  = mysqli_query($con,$query);
	$query1 = "select * from organization where user_id = '".$user_id."';";
	$result1  = mysqli_query($con,$query1);
	$query2 = "select * from donor where user_id = '".$user_id."';";
	$result2  = mysqli_query($con,$query2);
	
	
	if(mysqli_num_rows($result)>0 || mysqli_num_rows($result1)>0 || mysqli_num_rows($result2)>0){
	$response = array();
	$code = "true";
	$message = "User ID Already Exist....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	//echo "User Already Exist....";
	
}

else{
	$response = array();
	$code = "false";
	$message = "New User Id....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
}	
}

if($user_type=="donor"){
	
	$query = "select * from volunteer where user_id = '".$user_id."';";
	$result  = mysqli_query($con,$query);
	$query1 = "select * from organization where user_id = '".$user_id."';";
	$result1  = mysqli_query($con,$query1);
	$query2 = "select * from donor where user_id = '".$user_id."';";
	$result2  = mysqli_query($con,$query2);
	
	if(mysqli_num_rows($result)>0 || mysqli_num_rows($result1)>0 || mysqli_num_rows($result2)>0){
	$response = array();
	$code = "true";
	$message = "User ID Already Exist....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	//echo "User Already Exist....";
	
}

else{
	$response = array();
	$code = "false";
	$message = "New User Id....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
}

	
	
}


?>