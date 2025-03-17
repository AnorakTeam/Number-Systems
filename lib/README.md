Since i'm on Linux, i did not upload the javafx jar's (also, github is complaining for large 
size files), because there are different jars for every OS. So, in order to run the GUI 
go to https://gluonhq.com/products/javafx/, download the sdk for the OS, extract the 
compressed file and then paste the content of the folder "lib" in this directory.

If you are running this in vscode, make sure to have the following extension up to date:

Extension Pack for Java (from Microsoft)

and then everything under this folder that ends with a .jar will work.

Also, although i haven't uploaded the launch.json settings, it's nice to keep in mind this for 
troubleshooting.

inside the launch.json file, it has to exist a config similar to:

{
    "type": "java",
    "name": "Launch App",
    "request": "launch",
    "mainClass": "view.Main",
    "vmArgs": "--module-path (route_to)/lib/ --add-modules javafx.controls,javafx.fxml"
}

Usually the extension itself does all the work of the config in order to launch, but if for
whatever reason it doesn't work, just add that block to the configs and it should work ok.