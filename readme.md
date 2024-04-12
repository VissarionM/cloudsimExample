# Password protection for static pages
This simple HTML document helps you protecting static pages or whole websites with no server configuration required: you can now use Dropbox, Amazon S3 or any generic hosting service to host a private, password protected site.

This small project is a byproduct of my Tumbless blogging platform project.

# Setup
Upload the index.html document and the background image to your static hosting service.
Load it up in your browser, enter the password of your choice
It will show "wrong password", never mind. Copy the section of the URL after the # sign.
Create a folder with that name next to the index.html file
Upload the content that you want to protect inside the folder
The final structure will be:

- index.html
- background.jpg
- this-is-a-hash      <-- the SHA1 hash of your password               
  \ - index.html      <-- your original index document
# Is this secure?
Pretty much secure, please consider that:

If your hosting service offers directory listing, a visitor can bypass the protection.
there's no protection against brute force attack. Pick a very long and hard to guess password.
The password's hash is part of the URI. Enforce HTTPS to avoid man in the middle attacks.
# Troubleshooting
Test the demo page in your browser with password 'secret'
Deploy the whole repo on your hosting, and test again.
This file has the style, modified a bit.

# Vanilla JavaScript App
[Azure Static Web Apps](https://docs.microsoft.com/azure/static-web-apps/overview) allows you to easily build JavaScript apps in minutes. Use this repo with the [quickstart](https://docs.microsoft.com/azure/static-web-apps/getting-started?tabs=vanilla-javascript) to build and customize a new static site.

This repo is used as a starter for a _very basic_ HTML web application using no front-end frameworks.

This repo has a dev container. This means if you open it inside a [GitHub Codespace](https://github.com/features/codespaces), or using [VS Code with the remote containers extension](https://code.visualstudio.com/docs/remote/containers), it will be opened inside a container with all the dependencies already installed.
