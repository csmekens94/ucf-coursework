<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="img/favicon.ico">

        <title>Group 6</title>

        <link rel='stylesheet' type='text/css' href="css/bootstrap.min.css">
        <link rel='stylesheet' type='text/css' href="css/ie10-viewport-bug-workaround.css">
        <link rel='stylesheet' type='text/css' href="css/cover.css">
        <link rel='stylesheet' type='text/css' href="css/animate.css">
        <link rel='stylesheet' type='text/css' href="css/font-awesome.min.css">
        <link rel='stylesheet' type='text/css' href='https://fonts.googleapis.com/css?family=Satisfy'>
        
        <script src="js/ie-emulation-modes-warning.js"></script>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <div class="site-wrapper">

            <div class="site-wrapper-inner">

                <div class="cover-container">

                    <div class="masthead clearfix">
                        <div class="inner">
                            <h2 class="masthead-brand fancy-font"><i class="fa fa-group"></i> Group 6 Tokenization Demo<hr/></h2>
                        </div>
                    </div>


                    <div id="work-container" class="inner cover">
              
                        <div id="getting-things-ready" class="animated">
                            <h1><i id="spinner-icon"  class="fa fa-2x fa-spinner fa-spin animated"></i></h1>
                            <h1 class="fancy-font">Getting Things Ready</h1>
                            <br/>
                            <button type="button" id="startButton" class="btn btn-info btn-lg animated" onclick="stepInformation();">
                                Start The Demo
                            </button>
                        </div>
                        
                        <div id="information" class="animated">
                            <h1 class="fancy-font">Disclaimers</h1>
                            <p>This demonstration is a simulated example of what will happen during a tokenization process.</p>
                            <br/>
                            <p><b>This demo does NOT:</b></p>
                            <p>Send any data over the internet</p>
                            <p>Display your full card data on screen</p>
                            <p>Store any data in your browser</p>
                            <p>Provide a fully functional tokenization system</p>
                            <br/>
                            <button type="button" class="btn btn-info btn-lg" onclick="stepEnterCardData();">
                                Continue
                            </button>
                        </div>
                        
                        
                        <div id="enter-card-data" class="animated">
                            <h1 class="fancy-font">Enter Your Credit Card Number</h1>
                            <br/>
                            <form onsubmit="return false;">
                                <div class="form-group">
                                    <input type="password" class="form-control" id="cardData" placeholder="Swipe your card number here...">
                                </div>
  
                                <button id="cardDataSubmit" type="submit" class="btn btn-info btn-lg" data-loading-text="Processing Data..."
                                    onclick="parseData();">
                                    Process Card Data
                                </button>
                            </form>
                        </div>
                        
                        
                        <div id="display-card-information" class="animated">
                            <h1 class="fancy-font">Displaying Card Information</h1>
                            <br/>
                            <p style="font-size: 22px;"><span id="dciCardType"></span></p>
                            <p style="font-size: 22px;"><b>Card Number:</b> <span id="dciCardNumberMasked"></span></p>
                            <p style="font-size: 22px;"><b>Card Owner Name:</b> <span id="dciCardOwnerName"></span></p>
                            <br/>
                            <button type="button" class="btn btn-info btn-lg" onclick="stepSendCardData();">
                                Continue
                            </button>
                        </div>
                        
                        
                        <div id="sending-card-data" class="animated">
                            <h1 class="fancy-font">Sending Card Data</h1>
                            <br/>
                            <div class="progress">
                                <div id="sendStatus" class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar"
                                    aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div>
                            </div>
                            <br/>
                            <p id="sendStatusMessage"></p>
                            <button id="continueSendingCardData" type="button" class="btn btn-info btn-lg animated" onclick="stepDisplayToken();">
                                Continue
                            </button>
                        </div>
                        
                        
                        <div id="display-token" class="animated">
                            <h1 class="fancy-font">Examples of Generated Tokens</h1>
                            <p>(All hash methods used SHA-512)</p>
                            
                            <table class="table table-bordered" style="text-align: center;">
                                <tr>
                                    <th></th>
                                    <th>Numeric</th>
                                    <th>Alpha Numeric</th>
                                    <th>Part of PAN in Token</th>
                                </tr>
                                <tr>
                                    <th>Original PAN</th>
                                    <td id="table-original-pan" colspan="3"></td>
                                </tr>
                                <tr>
                                    <th>Salt Used</th>
                                    <td id="table-salt-1"></td>
                                    <td id="table-salt-2"></td>
                                    <td id="table-salt-3"></td>
                                </tr>
                                <tr>
                                    <th>Token Value</th>
                                    <td id="table-token-1"></td>
                                    <td id="table-token-2"></td>
                                    <td id="table-token-3"></td>
                                </tr>
                            </table>
                            
                            <button id="continueSendingCardData" type="button" class="btn btn-warning btn-lg animated" onclick="regenerateTokens();">
                                Regenerate Tokens
                            </button>
                            <button id="continueSendingCardData" type="button" class="btn btn-info btn-lg animated" onclick="stepFinished();">
                                Finish
                            </button>
                        </div>
                        
                        
                        <div id="finished" class="animated">
                            <h1 class="fancy-font">Thank You For Your Time</h1>
                            <br/>
                            <p style="font-size: 24px;">Any Questions?</p>
                        </div>
                    </div>

                    <div class="mastfoot">
                        <div class="inner">
                            <hr/>
                            <h4 class="fancy-font">
                                Created By
                            </h4>
                            <p>
                                Robert Arango <i class="fa fa-circle" style="padding-left: 10px; padding-right: 10px;"></i> 
                                Brendan Donahue <i class="fa fa-circle" style="padding-left: 10px; padding-right: 10px;"></i> 
                                Eduardo Gonzalez <i class="fa fa-circle" style="padding-left: 10px; padding-right: 10px;"></i> 
                                Jesus Aponte
                            </p>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="js/sha512.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/workflow.js"></script>
        <script src="js/ie10-viewport-bug-workaround.js"></script>
    </body>
</html>
