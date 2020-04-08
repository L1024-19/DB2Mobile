<?php
if (!empty($_POST)) {
    $json_user = array(); 
    if (empty($_POST['studentemail']) || empty($_POST['studentpassword'])) {
        echo "Fields cannot be left blank.<br>";
    }
    else if (isset($_POST['studentemail']) && isset($_POST['studentpassword'])) {
        // Getting submitted user data from database
        $myconnection = new mysqli('localhost', 'root', '', 'db2');
        $stmt = $myconnection->prepare("SELECT id, email, password FROM students, users WHERE email = ? ".
        "AND students.student_id = users.id");
        $stmt->bind_param('s', $_POST['studentemail']);
        $stmt->execute();
        $result = $stmt->get_result();
    	$user = $result->fetch_object();

        // Verify user password and set $_SESSION
    	if ($user != NULL && $_POST['studentpassword'] == $user->password) {
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
