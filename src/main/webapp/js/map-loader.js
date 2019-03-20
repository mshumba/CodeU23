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
    fetch('/resource-data').then(function(response) {
      return response.json();
    }).then(function(resources){
      map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 40.7295,lng: -73.9965},
      zoom: 14
      });
      resources.forEach(function(resource){
        if(resource.lat != -200){;
          new google.maps.Marker({
            map:map,
            position: {lat: resource.lat, lng: resource.lng}
          });
        };
      });
    });
  }
