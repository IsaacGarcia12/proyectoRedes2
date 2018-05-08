$target_path = "subidas/";
$target_path = $target_path . basename( $_FILES['archivo']['name']);
if(move_uploaded_file($_FILES['archivo']['tmp_name'], $target_path)){
    echo "El archivo ". basename( $_FILES['archivo']['name'])." ha sido subido exitosamente!";
}
else{
    echo "Hubo un error al subir tu archivo! Por favor intenta de nuevo.";
}