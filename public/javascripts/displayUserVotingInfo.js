
  (function() {
    $(function() {
      var path = "/whoamiString" ;
      return $.get(path, function(data) {
          var username = data[0];
          return $('#count'+index).text(count);
      });
    });
  }).call(this);

  (function voteDisplay() {
          if(currentUser === "guest") {
              //nothing
          } else if(voted){
              document.getElementById("Unvote@index").hidden = false;
          }
          else if(!voted){
              document.getElementById("Vote@index").hidden = false;
          }
      }).call(this);