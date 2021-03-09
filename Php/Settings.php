<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    // $_POST['pseudo'] = "test";
    // $_POST['email'] = "test@test.fr";
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";

    if (isset($_POST))
    {
        if (!empty($_POST['NP']))
        {
            $Email = $_POST['Email'];
            if ($_POST['Check'] == "true")
            {
                $sql = $bdd -> prepare("UPDATE users SET NP = :NP, MdpP = :Mdp  WHERE Email = :Email"); 
                $sql -> execute (array('NP' => $_POST['NP'], 'Mdp' => null, 'Email' => $Email));
                if(!$sql)
                {
                    $results["error"] = true;
                    $results["message"] = "Erreur lors de l'inscription";
                }
            }
            else
            {
                if($_POST['Mdp'])
                {
                    $Mdp = $_POST['Mdp'];
                    $Mdp = password_hash($Mdp, PASSWORD_BCRYPT);
                    $sql = $bdd -> prepare("UPDATE users SET NP = :NP, MdpP = :Mdp  WHERE Email = :Email"); 
                    $sql -> execute (array('NP' => $_POST['NP'], 'Mdp' => $Mdp, 'Email' => $Email));
                    if(!$sql)
                    {
                        $results["error"] = true;
                        $results["message"] = "Erreur lors de l'inscripttion";
                    }
                }
                else
                {
                    $results["error"] = true;
                    $results["message"] = "Veuillez mettre un mdp";
                }
            }

        }
    
        else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tt les champs";
        }
    }
    echo json_encode($results);

?>