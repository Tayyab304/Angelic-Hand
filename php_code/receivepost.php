<?php

$show = $_POST["check"];
$cata = $_POST["catagory"];

require "database_connection.php";
if($show=="show"){
	if($cata=="all"){
		$query = "select * from post;";
	}
	else{
		$query = "select * from post where 	post_category ='".$cata."';";
	}
	

$result  = mysqli_query($con,$query);

if(mysqli_num_rows($result)>0){
	
	$response = array();
	$code = "true";
	$message = "Posting...";

	
	while($rows = mysqli_fetch_array($result)){
	$post_id=$rows[0];
	$user_id = $rows[1];
	$postloc = $rows[2];
	$postdata = $rows[3];
	$assigned=$rows[4];
	$postcata = $rows[5];
	$posttime = $rows[6];
	array_push($response,array("code"=>$code,"message"=>$message,"post_id"=>$post_id,"user_id"=>$user_id,"post_location"=>$postloc,
	"post_data"=>$postdata,"assigned_to"=>$assigned,
	"post_cata"=>$postcata,"post_time"=>$posttime));
	}
	
	echo json_encode(array("server_response"=>$response));
	
	}
	
	else{
	$response = array();
	$code = "false";
	$message = "There is no Post...";
	
	array_push($response,array("code"=>$code,"message"=>$message));
	echo json_encode(array("server_response"=>$response));
	
}

}
mysqli_close($con);
?>