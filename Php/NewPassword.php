<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";  

    if (isset($_POST))
    {
    
        $Email = $_POST['Email'];
        $Mdp = $_POST['Mdp'];
        $Cmdp = $_POST['Cmdp'];

        if (!empty($Mdp) && !empty($Cmdp))
        {
            if ($Mdp === $Cmdp)
            {
                $req = $bdd -> prepare("SELECT * FROM users WHERE Email = :Email");
                $req -> execute(array('Email' => $Email));
                $row = $req -> fetch();
                if($row)
                {
                    $results["error"] = false;
                    $Mdp = password_hash($Mdp, PASSWORD_BCRYPT);
                    $req = $bdd -> prepare('UPDATE users SET Mdp = :Mdp WHERE Email = :Email');
                    $req -> execute(array('Mdp' => $Mdp, 'Email' => $Email));
                }
                else
                {
                    $results["error"] = true;
                    $results["message"] = "Une Erreur s'est produite";
                }
            }
            else
            {
                $results["error"] = true;
                    $results["message"] = "les mdp ne se correspondent pas";
            }
                
        }
        else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tt les champs";
        }        
    }

    else
    {
        $results["error"] = true;
        $results["message"] = "Veuillez remplir tt les champs";
    }

        echo json_encode($results);

    

?>