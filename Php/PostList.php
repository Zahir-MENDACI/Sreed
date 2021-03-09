<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    if (isset($_POST))
    {
        if(isset($_POST['Email']))
        {
            $Email = $_POST['Email'];
             
                $sql = $bdd -> query("SELECT * FROM posts WHERE Email = '$Email'"); 
                // while($data=$sql->fetch())
                //   {
                //     extract($data);
                //     $result["Num"] = $Num;
                //     $result["Email"] = $Email;
                //     $result["Contenu"] = $Contenu;
                //     $json = json_encode($result) . ",";
                //   }
                //$rows = array();
                while ($row = $sql->fetch(PDO::FETCH_ASSOC)) {
                    $rows[] = $row;
                }
                echo json_encode(array('PostList' => $rows));


                if(!$sql)
                {
                    $results["error"] = true;
                    $results["message"] = "Erreur lors de l'inscripttion";
                }
            }
            else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tt les champs";
        }
            
    }

        

        



?>