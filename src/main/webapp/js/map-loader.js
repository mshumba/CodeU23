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
/*
 * Create a new map object to be displayed by the map page
 */
  let map;
  function createMap(){
    map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 40.7295,lng: -73.9965},
      zoom: 14
    });
    //marker and event for comedy center
    const comedyCenterMarker = new google.maps.Marker({
      position: {lat:40.7302,lng: -74.0006},
      map: map,
      title: "ComedyCenter"
    });
    var comedyCenterInfoWindow = new google.maps.InfoWindow({
      content:'Famous comedy club where you can curse your life with joy.'
    });
    comedyCenterMarker.addListener('click', function() {
      comedyCenterInfoWindow.open(map, comedyCenterMarker);
    });

    //marker and event for WAshington Square Park
    const wspMarker = new google.maps.Marker({
      position:{lat:40.7308, lng:-73.9973},
      map:map,
      title:"Washington Square Park"
    });

    var wspInfoWindow = new google.maps.InfoWindow({
      content:"No matter you want to hug a stranger or cry your guts out in the public, this is the best place."
    });
    wspMarker.addListener('click',function(){
      wspInfoWindow.open(map,wspMarker);
    })

    
}