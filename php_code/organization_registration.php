<?php


$user_id = $_POST["user_id"];
$org_name = $_POST["organization_name"];
$org_add = $_POST["organization_address"];
$org_phone = $_POST["organization_phone"];
$org_pass = $_POST["organization_password"];
$org_link = $_POST["organization_link"];
$org_no= $_POST["organization_no"];



require "database_connection.php";

$query = "select * from organization where user_id = '".$user_id."';";
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

	
	$query = "insert into organization(user_id,password,name,address,landline_number,website_link,organization_number) 
	values('".$user_id."','".$org_pass."','".$org_name."','".$org_add."','".$org_phone."','".$org_link."','".$org_no."');";
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