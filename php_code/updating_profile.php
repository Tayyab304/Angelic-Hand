<?php

$user_id = $_POST["user_id"];
$fname = $_POST["first_name"];
$lname = $_POST["last_name"];
$add = $_POST["address"];
$vol_no = $_POST["volunteer_number"];
$org = $_POST["organization"];
require "database_connection.php";

		$query="update volunteer set first_name ='".$fname."',last_name ='".$lname."',
					address ='".$add."',org_volunteer_id ='".$vol_no."',organization_name ='".$org."' where user_id='".$user_id."';";
		$result  = mysqli_query($con,$query);
		
		if(!$result){
		
	$response = array();
	$code = "false";
	$message = "Profile Updated Failure.....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	else{
		
	$response = array();
	$code = "true";
	$message = "Successful Updated Profile....!";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
		
?>