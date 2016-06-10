
#####Store Contributor Names#####
- names are often shared among adjacent issues, and spelling mistakes
 can be an unfortunately common occurrance.
- keep an unseen list of used names and raise a passive flag if it
 appears there is a spelling mistake or misprint.

#####Error and Warning Logging/Flagging#####
- WARNING: flag files that look like they may have a possible issue. 
  simply print their file name as a suggested revision
- ERROR: hold files suspected to have more meaningful errors, or which 
  cannot be effectively parsed. should prevent these files from being 
  added to the export, print their filenames, perhaps along with error reason. 
  (should be able to force export with additional flag and control printouts)

#####Make into a Webservice#####
- set it up so that you can send an HTTP GET request
 with the contents of one of the text files in the body
 and have it return the json object
- write script to automate sending each individual one so that
 no one has to touch the code at all

#####Convert to package for command line installation#####
- convert to an installable package that you could install with
 'sudo apt-get install __' and point it at a repository
 containing all the .txt files, and the text config file locations
- move some of the config properties into bash flags and/or commands
