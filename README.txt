WeNote

Folder content: [webpage]:webpage part, [WeNote]:Chrome extension, [Restful API]:sever backend.


The main package for installation is the Chrome plugin package file
To install it, click the side button in Chrome browser: Tools->Extensions->

The main functionality is implemented in two forms: a webpage, and a Chrome plugin. 

In the webpage, we implement the following functionality:
1.Register new users.
2.User login-in. 
3.List all the notes I write in different webpages.
4.Keyword search
5.List all the friends.
6.Add friends.

In the plugin, we implement the following functionality:
1.User login-in.
2.Link to the webpage.
3.Add notes on the current webpage.
4.List the notes from yourself and your friends
5.User Log-out.

We use an Amazon S3 bucket to save the static webpages, and deploy the server program on an Amazon EC2 instance. 
We save the user data and the note data on the Amazon DynamoDB. 




Web Part:

The web page used HTML5, css and javascript, with JQuery Mobile tools. 