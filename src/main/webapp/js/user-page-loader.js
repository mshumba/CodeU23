/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Get ?user=XYZ parameter value
const urlParams = new URLSearchParams(window.location.search);
const parameterUsername = urlParams.get('user');

// URL must include ?user=XYZ parameter. If not, redirect to homepage.
if (!parameterUsername) {
  window.location.replace('/');
}

/** Sets the page title based on the URL parameter username. */
function setPageTitle() {
  document.getElementById('page-title').innerText = parameterUsername;
  document.title = parameterUsername + ' - User Page';
}

/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn &&
            loginStatus.username == parameterUsername) {
          const messageForm = document.getElementById('message-form');
          messageForm.classList.remove('hidden');
        }
      });
}

/** Fetches messages and add them to the page. */
function fetchMessages() {
  const url = '/messages?user=' + parameterUsername;
  fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((messages) => {
        const messagesContainer = document.getElementById('message-container');
        if (messages.length == 0) {
          messagesContainer.innerHTML = '<p>This user has no posts yet.</p>';
        } else {
          messagesContainer.innerHTML = '';
        }
        messages.forEach((message) => {
          const messageDiv = buildMessageDiv(message);
          messagesContainer.appendChild(messageDiv);

        });
      });
}


function buildResponseDiv(){
/*
<label>Reply:</label>
<input type="text" ng-model="yourMessage" placeholder="Reply to the above message" id="replyBox">
<br>
Add an image to your response:
<input type="file" name="image">
<br>
<input type="submit" value="Reply" onclick="msg()">
*/
}
/**
 * Builds an element that displays the message.
 * @param {Message} message
 * @return {Element}
 */
function buildMessageDiv(message) {
  const headerDiv = document.createElement('div');
  headerDiv.classList.add('message-header');
  headerDiv.appendChild(document.createTextNode(
      message.user + ' - ' + new Date(message.timestamp)));

  const bodyDiv = document.createElement('div');
  bodyDiv.classList.add('message-body');
  bodyDiv.innerHTML = message.text;

  const messageDiv = document.createElement('div');
  messageDiv.classList.add('message-div');
  messageDiv.appendChild(headerDiv);
  messageDiv.appendChild(bodyDiv);
if(message.imageUrl){
  bodyDiv.innerHTML += '<br/>';
  bodyDiv.innerHTML += '<img src="' + message.imageUrl + '" />';
}
if(message.imageLabels){
  bodyDiv.innerHTML += '<br/>';
  bodyDiv.innerHTML += message.imageLabels;
}
    const responseDiv = document.createElement('div');
    responseDiv.classList.add('response-div');
    responseDiv.innerHTML+= '<div ng-app="">';
        responseDiv.innerHTML+='<hr>'
        responseDiv.innerHTML+='<b>New Message</b><br/>';
        responseDiv.innerHTML+='<input ng-model="Comment" Style="width:40%;display:inline-block;" class="form-control" placeholder="Write Your Name.. " />';
        responseDiv.innerHTML+='<button class="btn btn-danger">Add Comment</button>';
      messageDiv.appendChild(responseDiv);
  return messageDiv;
}


/** Fetches data and populates the UI of the page. */
function buildUI() {
  setPageTitle();
  showMessageFormIfViewingSelf();
  fetchMessages();
}
/**
 * Shows the message form if the user is logged in and viewing their own page.
 */
function showMessageFormIfViewingSelf() {
  fetch('/login-status')
      .then((response) => {
        return response.json();
      })
      .then((loginStatus) => {
        if (loginStatus.isLoggedIn &&
            loginStatus.username == parameterUsername) {
          fetchImageUploadUrlAndShowForm();
        }
      });
      reply_message();
}

function reply_message(id, sender){
    console.log('Message id: '+id);
    console.log('Reply to: '+sender);
}

function fetchImageUploadUrlAndShowForm() {
  fetch('/image-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('message-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
}
function makeReplyForm(){

}
