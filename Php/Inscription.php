<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    // $_POST['pseudo'] = "test";
    // $_POST['email'] = "test@test.fr";
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";  
    
    /* $_POST['Email'] = "testt@test.fr";
    $_POST['Mdp'] = "test";
    $_POST['Cmdp'] = "test"; */

    if (isset($_POST))
    {
        if (!empty($_POST['Email']) && !empty($_POST['Mdp']) && !empty($_POST['Cmdp']))
        {
            $Email = $_POST['Email'];
            $Mdp = $_POST['Mdp'];
            $Cmdp = $_POST['Cmdp'];


            if(!filter_var($Email, FILTER_VALIDATE_EMAIL))
            {
                
                $results['error'] = true;
                $results["message"]['Email'] = "Email invalide";
            }
            else
            {
                $req = $bdd -> prepare("SELECT * FROM users WHERE Email = :Email");
                $req -> execute(array('Email' => $Email));
                $row = $req -> fetch();
                if($row)
                {
                    $results["error"] = true;
                    $results["message"]['Email'] = "Email existe deja";
                }
            }
           

            if($Mdp !== $Cmdp)
            {
                $results["error"] = true;
                $results["message"]['Mdp'] = "les mdp ne se correspondent pas";
            }

            if($results["error"] === false)
            {
                $Mdp = password_hash($Mdp, PASSWORD_BCRYPT);
                $longueurKey = 15;
                $Cle = "";
                for($i=1; $i<$longueurKey; $i++) 
                {
                    $Cle .= mt_rand(0,9);
                }
                
                $sql = $bdd -> exec("INSERT INTO users(Email, Mdp, CleConfirmation) VALUES ('$Email', '$Mdp', '$Cle')"); 

                if(!$sql)
                {
                    $results["error"] = true;
                    $results["message"] = "Erreur lors de l'inscripttion";
                }
                else
                {
                    //header("Location:Email.php?Email=" .$Email. "&Cle=" .$Cle);
                    
                    $results["error"] = false;
                    $results["message"] = "inscription reussie";
                    //mail($Email, "Récupération de mot de passe - APC.com", "azerty", "");


                  /*   //require("\PHPMailer\PHPMailer.php");
                    // require("/PHPMailer/SMTP.php");
                    // require("/PHPMailer/Exception.php");
                    require 'PHPMailer/PHPMailer.php';

                    $mail = PHPMailer();
                    $mail->IsSMTP();

                    $mail->SMTPDebug = 1; // debugging: 1 = errors and messages, 2 = messages only
                    $mail->SMTPAuth = true; // authentication enabled
                    $mail->SMTPSecure = 'ssl'; // secure transfer enabled REQUIRED for Gmail
                    $mail->Host = "smtp.gmail.com";
                    $mail->Port = 465; // or 587
                    $mail->IsHTML(true);
                    $mail->Username = "apc.l3ummto@gmail.com";
                    $mail->Password = "apconline";
                    $mail->SetFrom("apc.l3ummto@gmail.com");
                    $mail->AddAddress($Email);
                    $mail->Subject = "Test";
                    $mail->Body = "
                        check this link
                    "; */


    
                    
                }
            }
        }

        else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tt les champs";
        }

        echo json_encode($results);

    }

?>