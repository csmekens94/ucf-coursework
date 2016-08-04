<?php
  // A PHP array that holds the alphabet, so I can iterate through 
  // indexes 0 - 25 and print the corresponding letters.
  $letters = [
    'A','B','C','D','E','F','G','H','I','J','K','L','M',
    'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
  ];
?>

<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="assets/css/style.css"></script>
        <link rel="stylesheet" href="assets/css/font-awesome.min.css">
        <script src="assets/js/jquery-2.1.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/scripts/assignment1.js"></script>
    </head>
    
    <body onload='$("#hide-extras").hide();'>
      
      <nav class="navbar navbar-default navbar-fixed-top">
      
          <div class="container">
      
              <div class="navbar-header">
      
                  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
      
                      <span class="sr-only">Toggle navigation</span>
      
                      <span class="icon-bar"></span>
      
                      <span class="icon-bar"></span>
      
                      <span class="icon-bar"></span>
      
                  </button>
      
                  <a class="navbar-brand" href="index.php">CIS 3362</a>
      
              </div>
      
              <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      
                  <ul class="nav navbar-nav navbar-right">
      
                      <li><a href="#">Robert Arango</a></li>
      
                  </ul>
      
              </div>
      
          </div>
      
      </nav>
      
      <div class="container">
        
        <div class="well">
          
          <div class="row">
            
            <div class="col-md-12">
              
              <h3 style="text-align: center;">Substitution Cipher Helper</h3>
              
              <p>This is a tool that was created for the coding-lite portion of the class that will hopefully allow you to crack the substitution cipher in a few minutes instead of months.</p>
              <br/>
              
              <h3>Encrypted Message</h3>
              
              <p>The encrypted message can either be typed or just copy and pasted. The letter occurrences and common occurrences of groups of letters will auto update and display as the information is entered or updated.</p>
              
              <p><b>Suggestions</b></p>
              
              <ul>
                
                <li><p>Don't use symbols or other non-letter characters. (They can cause serious issues)</p></li>
                
                <li><p>Spaces and newlines are fine, they won't cause any issues.</p></li>
                
                <li><p>Updating this field will not affect your choices in the decrypt message section, it will just update the message with the new letters and substitute any choices that you have already populated.</p></li>
              
              </ul>
              
              <textarea id="encryptedMessage" class="form-control" rows="4" placeholder="Enter the encrypted text here..." oninput="runProg();"></textarea>
              
            </div>
          
          </div>
        
        </div>
      
      </div>
      
      <div class="container">
        
        <div class="well" id="hide-extras">    
          
          <h4>Letter Frequency</h4>
          
          <p>This section shows how many times each letter has come up in the encrypted message and the progress bars give a graphical representation of the most frequently used characters.</p>
          
          <?php
            
            for ($i = 0; $i < count($letters); $i++) {
            
              echo '<div class="row">';
            
              for ($j = $i; $j < $i + 6; $j++) {
            
                if ($j <= 25) {
            
                  echo '<div class="col-md-2">';
            
                    echo '<p class="progress-label"><b>'.$letters[$j].'</b></p>';       
            
                    echo '<div class="progress">';
            
                      echo '<div id="letFreq'.$letters[$j].'" class="progress-bar" role="progressbar" style="width: 0%;"></div>';
                    
                    echo '</div>';
                  
                  echo '</div>';
                
                }
                
                else { echo '<div class="col-md-2"></div>'; }
              
              }
              
              $i = $i + 5;
              
              echo '</div>';
            }
          ?>
          
          <br/>
          
          <h4>Common Occurrences</h4>
          
          <p>This section will find any common occurrences of letters and how many times those letters occur, as long as it's more than 2 times. It will only check for commonalities between 2 and 5 letter sequences. The number of times the sequence occurs will be shown in red next to the characters.</p>
          
          <div class="row">
            
            <div class="col-md-4"><div id="twoLetters"></div></div>
            
            <div class="col-md-4"><div id="threeLetters"></div></div>
            
            <div class="col-md-2"><div id="fourLetters"></div></div>
            
            <div class="col-md-2"><div id="fiveLetters"></div></div>
          
          </div>
          
          <br/>
          
          <h4>Decrypt Message</h4>
          
          <p>The decryption process is pretty straight forward, you use your head and the information from the previous 2 sections to try and guess the message.</p>
          
          <ul>
            
            <li><p>The letters that are available in the encrypted message will show up red on the left side.</p></li>
            
            <li><p>Once you fill the text field with a character it will turn green to let you know that a value has been supplied to replace that letter. The <b>gray box letters</b> are not used in the encrypted message which means that you do not need to use these and updating them will not actually do anything to the right message.</p></li>
            
            <li><p>As you input letters into the left text fields, they will update and change red in the right hand side in order for you to see your changes as you go.</p></li>
          
          </ul>
          
          <div class="row">
            
            <div class="col-md-6">
            
              <?php
                for ($i = 0; $i < count($letters); $i++) {
                  echo '<div class="row">';
                  for ($j = $i; $j < $i + 4; $j++) {
                    if ($j <= 25) {
                      echo '<div class="col-md-3">';
                        echo '<div class="input-group" id="decryptInput'.$letters[$j].'">';
                          echo '<div class="input-group-addon"><b>'.$letters[$j].'</b></div>';
                          echo '<input type="text" class="form-control" id="decrypt'.$letters[$j].
                            '" placeholder="'.$letters[$j].'" maxlength="1" oninput="decryptMessage();">';
                        echo '</div>';
                      echo '</div>';
                    }
                    else { echo '<div class="col-md-3"></div>'; }
                  }
                  $i = $i + 3;
                  echo '<div class="col-md-3"></div></div>';
                }
              ?>
            
            </div>
            
            <div class="col-md-6">
            
              <div id="decryptedMessage"></div>
            
            </div>
          
          </div>
        
        </div>
      
      </div>     
    
    </body>
</html>