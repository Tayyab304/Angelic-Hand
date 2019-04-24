<?php

$user_id = $_POST["user_id"];
$pass = $_POST["password"];
require "database_connection.php";

$query = "select * from organization where user_id = '".$user_id."' AND password = '".$pass."';";
$result  = mysqli_query($con,$query);

if(mysqli_num_rows($result)>0){
			
			
			$response = array();
			$code = "login_true";
			$type="organization";
			$rows = mysqli_fetch_array($result);
			$user_id = $rows[1];
			//$pass = $rows[2];
			$org_name =$rows[3];
			$org_no=$rows[4];
			$org_add=$rows[5];
			//$status=$row[6];
			$org_num=$rows[7];
			$org_link=$rows[8];
			$org_pic=$rows[9];
			
			$message = "Login Success Welcome....";
			array_push($response,array("code"=>$code,"message"=>$message,"type"=>$type,"user_id"=>$user_id,"org_name"=>$org_name,
			"org_no"=>$org_no,"org_add"=>$org_add,"org_num"=>$org_num,"org_link"=>$org_link,"org_picture"=>$org_pic));
			echo json_encode(array("server_response"=>$response));	
			
			}
			
			
			else{
				$response = array();
				$code = "login_false";
				$message = "InCorrect User Name OR Password...";
				array_push($response,array("code"=>$code,"message"=>$message));
				echo json_encode(array("server_response"=>$response));
			}
		
	
	


mysqli_close($con);

?>