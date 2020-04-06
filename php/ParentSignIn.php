<?php
if (!empty($_POST)) {
    $json_user = array(); 
    if (empty($_POST['parentemail']) || empty($_POST['parentpassword'])) {
        echo "Fields cannot be left blank.<br>";
    }
    else if (isset($_POST['parentemail']) && isset($_POST['parentpassword'])) {
        // Getting submitted user data from database
        $myconnection = new mysqli('localhost', 'root', '', 'db2');
        $stmt = $myconnection->prepare("SELECT id, email, password FROM parents, users WHERE email = ? ".
        "AND parents.parent_id = users.id");
        $stmt->bind_param('s', $_POST['parentemail']);
        $stmt->execute();
        $result = $stmt->get_result();
    	$user = $result->fetch_object();

    	// Verify user password and set $_SESSION
    	if ($user != NULL && $_POST['parentpassword'] == $user->password) {
            $userinfo = [
                'user_id' => $user->id
            ];
            array_push($json_user, $userinfo);
            echo json_encode($json_user);
        }
        else {
            echo("Either Email or Password is incorrect.");
        }
    }
}
?>