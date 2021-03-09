<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    /* $_POST['Pseudo'] = "test";
    $_POST['Mdp'] = ""; */

   if ($_POST)
   {

        if(!empty($_POST['Pseudo']))
        {
            
            $Pseudo = $_POST['Pseudo'];
            $Mdp = $_POST['Mdp'];
            $sql = $bdd -> prepare("SELECT * FROM users WHERE NP = :Pseudo");
            $sql -> execute(array("Pseudo" => $Pseudo));
            $row = $sql -> fetch(PDO::FETCH_OBJ);
            
            if($row)
            {
                if(!empty($_POST['Mdp']))
                {
                    if(password_verify($Mdp, $row->MdpP))
                    {
                        $results["error"] = false;
                    }
                    else
                    {
                        $results["error"] = true;
                        $results["message"] = "Pseudo ou mot de passe incorrecte";
                    }
                }
                else
                {
                    if ($row->MdpP === Null)
                    {
                        $results["error"] = false;
                    }
                    else
                    {
                        $results["error"] = true;
                        $results["message"] = "Pseudo ou mot de passe incorrecte";
                    }
                }
                
                
            }
            else
            {
                $results["error"] = true;
                $results["message"] = "Pseudo ou mot de passe incorrecte";
            }
        }
        else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tous les champs";
        }
    }
    else
    {
        $results["error"] = true;
        $results["message"] = "Veuillez remplir tous les champs";
    }

    echo json_encode($results);

?>