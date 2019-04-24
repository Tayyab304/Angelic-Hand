<?php
$show = $_POST["check"];
$check_user = $_POST["tt"];
$req = $_POST["request"];

require "database_connection.php";

if($show=="show"){
	if($check_user=="all"){
		
		$query = "select * from volunteer;";
		
		}
		else if($req=="req"){
		$query = "select * from volunteer where organization_name ='".$check_user."'  AND 	status IS NULL;";
	     }
	     
	     else{
		 	$query = "select * from volunteer where organization_name ='".$check_user."';";
		 }
	
		$result  = mysqli_query($con,$query);
		
		if(mysqli_num_rows($result)>0){
	
	$response = array();
	$code = "true";
	$message = "show volunteers...";

	
	while($rows = mysqli_fetch_array($result)){
		    $vol_id=$rows[0];
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
			$user_status=$rows[12];
			
			if($user_status==1){
				$user_status=true;
			}
			else{
				$user_status=false;
			}
			array_push($response,array("code"=>$code,"message"=>$message,"volunteer_id"=>$vol_id,"user_id"=>$user_id,"first_name"=>$fname,
		"last_name"=>$lname,"mobile_number"=>$num,"proviance"=>$proviance,"city"=>$city,"address"=>$add,"organization"=>$org,
		"volunteer_number"=>$vol_no,"user_picture"=>$user_pic,"user_status"=>$user_status));
	
	}
	
	echo json_encode(array("server_response"=>$response));
	
	}
	
	
		
		
		
	
	
	else
	{
	$response = array();
		$code = "false";
		$message = "There is no Volunteer ...";
	
		array_push($response,array("code"=>$code,"message"=>$message));
		echo json_encode(array("server_response"=>$response));
	}
	
	
	
	
}




	

mysqli_close($con);

?>