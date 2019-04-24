<?php
$user_id = $_POST["user_id"];
$pass = $_POST["password"];
require "database_connection.php";

$query = "select * from volunteer where user_id = '".$user_id."' AND password = '".$pass."';";
$result  = mysqli_query($con,$query);

		$query1 = "select * from organization where user_id = '".$user_id."' AND password = '".$pass."';";
		$result1  = mysqli_query($con,$query1);
		
		$query2 = "select * from donor where user_id = '".$user_id."' AND password = '".$pass."';";
		$result2  = mysqli_query($con,$query2);

if(mysqli_num_rows($result)>0 || mysqli_num_rows($result1)>0 || mysqli_num_rows($result2)>0){
	
	
	
	
		$response = array();
		$code = "login_true";
		
		if(mysqli_num_rows($result)>0){
			$type="volunteer";
			$rows = mysqli_fetch_array($result);
			$user_id = $rows[1];
			$fname = $rows[2];
			$lname =$rows[3];
			$num=$rows[4];
			//$pass=$row[5];
			$proviance=$rows[6];
			$city=$rows[7];
			$add=$rows[8];
			$org=$rows[9];
			$vol_no=$rows[10];
			$user_pic=$rows[11];
		
		$message = "Login Success Welcome....";
		array_push($response,array("code"=>$code,"message"=>$message,"type"=>$type,"user_id"=>$user_id,"first_name"=>$fname,
		"last_name"=>$lname,"mobile_number"=>$num,"proviance"=>$proviance,"city"=>$city,"address"=>$add,"organization"=>$org,
		"volunteer_number"=>$vol_no,"user_picture"=>$user_pic));
		echo json_encode(array("server_response"=>$response));
		}
		
		elseif(mysqli_num_rows($result1)>0){
			
				$type="organization";
				$rows = mysqli_fetch_array($result1);
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
			elseif(mysqli_num_rows($result2)>0){
			
				$type="donor";
				$rows = mysqli_fetch_array($result2);
				
			
			$user_id = $rows[1];
			$fname = $rows[2];
			$lname =$rows[3];
			$num=$rows[4];
			//$pass=$row[5];
			$proviance=$rows[6];
			$city=$rows[7];
			$add=$rows[8];
			$user_pic=$rows[9];
		
		$message = "Login Success Welcome....";
		array_push($response,array("code"=>$code,"message"=>$message,"type"=>$type,"user_id"=>$user_id,"first_name"=>$fname,
		"last_name"=>$lname,"mobile_number"=>$num,"proviance"=>$proviance,"city"=>$city,"address"=>$add
		,"user_picture"=>$user_pic));
		echo json_encode(array("server_response"=>$response));
			}
		
	
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