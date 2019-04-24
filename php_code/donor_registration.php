<?php

$user_id = $_POST["user_id"];
$fname = $_POST["first_name"];
$lname = $_POST["last_name"];
$num = $_POST["mobile_number"];
$proviance = $_POST["proviance"];
$city = $_POST["city"];
$add = $_POST["address"];
$pass = $_POST["password"];

require "database_connection.php";


$query = "select * from donor where user_id = '".$user_id."';";
$result  = mysqli_query($con,$query);

if(mysqli_num_rows($result)>0){
	$response = array();
	$code = "already";
	$message = "User ID Already Exist....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	//echo "User Already Exist....";
	
}


else{

	
	$query = "insert into donor(user_id,first_name,last_name,mobile_number,password,proviance,city,address) 
	values('".$user_id."','".$fname."','".$lname."','".$num."','".$pass."','".$proviance."','".$city."','".$add."');";
	$result  = mysqli_query($con,$query);
	
	if(!$result){
		
	$response = array();
	$code = "false";
	$message = "Some Server Error Occured   Try phr .....";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
	else{
		
	$response = array();
	$code = "true";
	$message = "Successful Register.. Thank You...!";
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	}
	
}

mysqli_close($con);

?>