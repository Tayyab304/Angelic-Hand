<?php
header('Content-type : bitmap; charset=utf-8');
require "database_connection.php";

if(isset($_POST["encoded_string"])){
	$user_type=$_POST["type"];
	$encoded_string=$_POST["encoded_string"];
	$image_name = $_POST["image_name"];
	$user_id = $_POST["user_id"];
	
	$decoded_string=base64_decode($encoded_string);
	
	$path='images/'.$image_name;
	$file=fopen($path,'wb');
	$is_written = fwrite($file,$decoded_string);
	fclose($file);
	
	if($is_written > 0){
		
		$query="update ".$user_type." set image_path ='".$image_name."' where user_id='".$user_id."';";
		$result  = mysqli_query($con,$query);
		
		if($result){
			
			
			$response = array();
			$code = "true";
			$pic_code=$image_name;
			$message = "Picture Uploaded Successfully .....";
			array_push($response,array("code"=>$code,"message"=>$message,"piccode"=>$pic_code));
			echo json_encode(array("server_response"=>$response));
		}
		else{
			$response = array();
			$code = "false";
			$message = "Picture Uploaded Failed .....";
			array_push($response,array("code"=>$code,"message"=>$message));
			echo json_encode(array("server_response"=>$response));
		}
		mysqli_close($con);
	}
	
	
	}


?>