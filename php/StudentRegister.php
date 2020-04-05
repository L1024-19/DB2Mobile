<?php
    // sql connection
    $myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
    or die ('Could not connect: ' . mysqli_error($myconnection));

    // input variables
    $email = $_POST['email'];
    // $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
    $password = $_POST['password'];
    $name = $_POST['studentname'];
    $phone = $_POST['phone'];
    $parentemail = $_POST['parentemail'];
    $grade = $_POST['grade'];
    $role = $_POST['role'];

    // get all rows with parent email
    $emailselect = "SELECT * FROM users WHERE email = '$parentemail'";
    $emailresult = mysqli_query($myconnection, $emailselect)
    or die ('Query failed: ' . mysqli_error($myconnection));

    $row = mysqli_fetch_array($emailresult, MYSQLI_ASSOC);

    // insert into users
    if (!empty($_POST['parentemail']) && $row != NULL && $row["email"] == $parentemail) {

        // set up for seeing if email is a parent
        $parentidselect = "SELECT * FROM parents WHERE parent_id = {$row['id']}";
        $parentidresult = mysqli_query($myconnection, $parentidselect)
        or die ('Query failed: ' . mysqli_error($myconnection));

        $parentid = mysqli_fetch_array($parentidresult, MYSQLI_ASSOC);
        if ($parentid == NULL) {
            echo("Parent's email is not a valid parent email.<br>");
        }
        else if ($row["id"] == $parentid["parent_id"]) {
            // check if email is taken
            $emailcheckselect = "SELECT email FROM users where email = '$email'";
            $emailcheckresult = mysqli_query($myconnection, $emailcheckselect)
            or die ('Query failed: ' . mysqli_error($myconnection));
            $emailcheck = mysqli_fetch_array($emailcheckresult, MYSQLI_ASSOC);
            if (isset($emailcheck['email']) && $email == $emailcheck['email']) {
                echo("Email is currently taken. Use another.");
            }
            else {
                // insert into users
                $insert = "INSERT INTO users (email, password, name, phone) VALUES (?, ?, ?, ?)";

                $insertstmt = $myconnection->prepare($insert);
                $insertstmt->bind_param("ssss", $email, $password, $name, $phone);

                if (empty($email) || empty($password) || empty($name)) {
                    echo 'Email, Password, and Student Name cannot be empty.<br>';
                }
                else if ($insertstmt->execute()) {
                    echo 'Inserted<br>';
                }
                else {
                    echo 'Not inserted<br>';
                }

                // insert into students
                $parentidselect = "SELECT id FROM users WHERE email = '$parentemail'";
                $parentidresult = mysqli_query($myconnection, $parentidselect)
                or die ('Query failed: ' . mysqli_error($myconnection));
                $parentidarray = mysqli_fetch_array ($parentidresult, MYSQLI_ASSOC);
                $parentid = $parentidarray['id'];

                $studentidselect = "SELECT id FROM users WHERE email = '$email'";
                $studentidresult = mysqli_query($myconnection, $studentidselect)
                or die ('Query failed: ' . mysqli_error($myconnection));
                $studentidarray = mysqli_fetch_array ($studentidresult, MYSQLI_ASSOC);
                $studentid = $studentidarray['id'];

                $insertintostudents = "INSERT INTO students (student_id, grade, parent_id) 
                VALUES ($studentid, $grade, $parentid)";

                if (mysqli_query($myconnection, $insertintostudents)) {
                    echo("New record created successfully.<br>");
                }
                else {
                    // echo("Error description: " . mysqli_error($myconnection));
                    echo("Record is already in the database.<br>");
                }

                // insert into mentees/mentors
                $insertintomentees = "INSERT INTO mentees (mentee_id) VALUES ($studentid)";
                $insertintomentors = "INSERT INTO mentors (mentor_id) VALUES ($studentid)";
                if ($role == "Both") {
                    mysqli_query($myconnection, $insertintomentors);
                    mysqli_query($myconnection, $insertintomentees);
                    echo("Insert both<br>");
                }
                else if ($role == "Mentor") {
                    mysqli_query($myconnection, $insertintomentors);
                    echo("Insert mentor<br>");
                }
                else if ($role == "Mentee") {
                    mysqli_query($myconnection, $insertintomentees);
                    echo("Insert mentee<br>");
                }
                else {
                    echo("Did not insert role<br>");
                }
                if ($parentidresult != NULL) {
                    mysqli_free_result($parentidresult);
                }
                if ($studentidresult != NULL) {
                    mysqli_free_result($studentidresult);
                }
            }
        }
    }
    else if (empty($_POST['parentemail'])) {
        echo("Parent Email cannot be empty.<br>");
    }
    else if ($row == NULL || $row["email"] != $parentemail) {
        echo("Parent email is not in the database.<br>");
    }

    // free the object
    if ($emailresult != NULL) {
        mysqli_free_result($emailresult);
    }

    // close connection
    mysqli_close($myconnection);
?>
