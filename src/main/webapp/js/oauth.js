function onSignIn(googleUser) {
  // The ID token you need to pass to your backend:
  var id_token = googleUser.getAuthResponse().id_token;
  var profile = googleUser.getBasicProfile();
  console.log("ID Token: " + id_token);
  fetch(`https://oauth2.googleapis.com/tokeninfo?id_token=${id_token}`)
    .then((response)=>response.json())
    .then((response)=>{
      console.log(response);
      fetch(`/login?token=${response.sub}&email=${profile.getEmail()}`,{
        method:'POST'
      }).then((response)=>{window.location.href=response.url});
    });
}
function signOut() {
    gapi.load('auth2', function(){
      gapi.auth2.init().then(() => {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(()=>{
          window.location.href='/index.html';
        });
      });
    });

  }
function init(){
  gapi.load('auth2', function(){});
}
