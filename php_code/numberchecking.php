<?php

$num = $_POST["mobile_number"];

require "database_connection.php";

$query = "select * from volunteer where mobile_number = '".$num."';";
$result  = mysqli_query($con,$query);
$query1 = "select * from organization where landline_number = '".$num."';";
$result1  = mysqli_query($con,$query1);
$query2 = "select * from donor where mobile_number = '".$num."';";
$result2  = mysqli_query($con,$query2);

if(mysqli_num_rows($result)>0 || mysqli_num_rows($result1)>0 || mysqli_num_rows($result2)>0){
	$response = array();
	$code = "true";
	$message = "Mobile Number Already Exist....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	//echo "User Already Exist....";
	
}
else{
	$response = array();
	$code = "false";
	$message = "New Mobile Number ....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	
	}


?>