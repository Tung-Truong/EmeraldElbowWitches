# EmeraldElbowWitches

This is an implementation of a pathfinding kiosk for Brigham and Women's Hospital in Boston, MA. It has been created for [Prof. Wong's](https://www.wpi.edu/people/faculty/wwong2) CS 3733 class at [Worcester Polytechnic Institute](wpi.edu), B term 2017.

This project currently features the ability to view a map of the hospital, receive drawn paths from two points within certain regions of the hospital, to add and remove pathfinding nodes and edges, and to send emailed maintenance requests to an email account.

Additionally, at the launch of the program, it displays the floor number and a list of services located near the kiosk.

## To run this project on Windows

The team encountered issues with differences in Windows / Mac file paths. On a Windows machine, you need to open

`EmeraldElbowWitches/src/view/ui/UI_v1.fxml`

in Scene Builder, and change the source of the files

`EmeraldElbowWitches/src/view/media/HomeScreen.png`

and 

`EmeraldElbowWitches/src/view/media/legend.png`

to be direct file paths.

## Service Request Notes

Currently, all created service requests are sent to one of the team member's personal email accounts.

If you would like to test the service request feature, you must modify the `email.addRecipient` line in `EmeraldElbowWitches/src/model/JanitorService.java` to change the currently listed email to your own.

