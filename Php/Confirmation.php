<?php
$bdd = new PDO('mysql:host=127.0.0.1;dbname=sreed', 'root', '');

if(isset($_GET['Email'], $_GET['Cle']) AND !empty($_GET['Email']) AND !empty($_GET['Cle'])) {
   $Email = htmlspecialchars($_GET['Email']);
   $Cle = htmlspecialchars($_GET['Cle']);
   $requser = $bdd->prepare("SELECT * FROM users WHERE Email = ? AND CleConfirmation = ?");
   $requser->execute(array($Email, $Cle));
   $userexist = $requser->rowCount();
   if($userexist == 1) {
      $user = $requser->fetch();
      if($user['Valide'] == 0) {
         $updateuser = $bdd->prepare("UPDATE users SET Valide = 1 WHERE Email = ? AND CleConfirmation = ?");
         $updateuser->execute(array($Email,$Cle));
         echo "Votre compte a bien été confirmé !";
      } else {
         echo "Votre compte a déjà été confirmé !";
      }
   } else {
      echo "L'utilisateur n'existe pas !";
   }
}
?>