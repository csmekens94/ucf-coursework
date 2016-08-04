var gettingThingsReady     = $('#getting-things-ready');
var information            = $('#information');
var enterCardData          = $('#enter-card-data');
var displayCardInformation = $('#display-card-information');
var sendingCardData        = $('#sending-card-data');
var displayToken           = $('#display-token');
var finished               = $('#finished');

var cardData, cardNumber, cardNumberMasked, cardName, cardType;
var salt1,  salt2,  salt3;
var token1, token2, token3;


//gettingThingsReady.hide();
information.hide();
enterCardData.hide();
displayCardInformation.hide();
sendingCardData.hide();
displayToken.hide();
finished.hide();


$('#startButton').css("opacity", "0");
$('#continueSendingCardData').css("opacity", "0");


var Convert = {
    chars: " !\"#$%&amp;'()*+'-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~",
    hex: '0123456789ABCDEF', bin: ['0000', '0001', '0010', '0011', '0100', '0101', '0110', '0111', '1000', '1001', '1010', '1011', '1100', '1101', '1110', '1111'],

    decToHex: function(d){
        return (this.hex.charAt((d - d % 16)/16) + this.hex.charAt(d % 16));
    },
    toBin: function(ch){
        var d = this.toDec(ch);
        var l = this.hex.charAt(d % 16);
        var h = this.hex.charAt((d - d % 16)/16);

        var hhex = "ABCDEF";
        var lown = l < 10 ? l : (10 + hhex.indexOf(l));
        var highn = h < 10 ? h : (10 + hhex.indexOf(h));
        return this.bin[highn] + ' ' + this.bin[lown];
    },
    toHex: function(ch){
        return this.decToHex(this.toDec(ch));
    },
    toDec: function(ch){
        var p = this.chars.indexOf(ch);
        return (p <= -1) ? 0 : (p + 32);
    }
};


function toTitleCase(str)
{
    return str.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
}


function hidePanel( elem )
{
    setTimeout( function() {
        elem.show().addClass('slideOutLeft');
    }, 500);
    
    setTimeout( function() {
        elem.show().hide();
    }, 1000);
}


function showPanel( elem )
{
    setTimeout( function() {
        elem.show().addClass('slideInRight');
    }, 1000);
}


function updateSendStatus( width, time, message )
{
    setTimeout( function() {
        $( '#sendStatus' ).css( 'width', width ).html( width + ' Complete' );
        $( '#sendStatusMessage' ).html( message );    
    }, time);
}


function getRandomSalt()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 16; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}


function getFirstSixteen( txt )
{
    var tempSalt = "";
    
    for ( i = 0; i < 16; i++ )
        tempSalt += txt.charAt(i);
        
    return tempSalt;
}


function getSplitValues( txt )
{
    var tempSplit = "";
    
    for ( i = 0; i < 16; i++ )
    {
        if ( i % 4 == 0 && i != 0 )
            tempSplit += ' ';
        
        tempSplit += txt.charAt(i);
    }
        
    return tempSplit;
}


function createString( num )
{
    var tempString = '';
    
    for ( i = 0; i < num.length; i++ )
        tempString += Convert.toDec( num.charAt(i) );
    
    return tempString;
}


function insertPan( hash )
{
    var tempString = '';
    
    for ( i = 0; i < 12; i++ )
        tempString += hash.charAt(i);
    
    return tempString + cardNumber.charAt(12) + cardNumber.charAt(13) + cardNumber.charAt(14) + cardNumber.charAt(15);
}


function getTokenNumeric( cardNum )
{
    var tempSaltObj = new jsSHA( "SHA-512", "TEXT" );
    var tempHashObj = new jsSHA( "SHA-512", "TEXT" );
    var tempToken   = '';
    
    salt1 = getFirstSixteen( getRandomSalt() );
    
    tempSaltObj.update( salt1 );
    tempHashObj.update( tempSaltObj.getHash('HEX') + cardNum );
    
    tempToken = createString( tempHashObj.getHash( "HEX" ) );
    token1    = getFirstSixteen( tempToken );
    
    $('#table-salt-1').html( getSplitValues( salt1 ) );
    $('#table-token-1').html( getSplitValues( token1 ) );
}


function getTokenAlphaNumeric( cardNum )
{
    var tempSaltObj = new jsSHA( "SHA-512", "TEXT" );
    var tempHashObj = new jsSHA( "SHA-512", "TEXT" );
    var tempToken   = '';
    
    salt2 = getFirstSixteen( getRandomSalt() );
    
    tempSaltObj.update( salt2 );
    tempHashObj.update( tempSaltObj.getHash('HEX') + cardNum );
    
    tempToken = tempHashObj.getHash( "HEX" );
    token2    = getFirstSixteen( tempToken );
    
    $('#table-salt-2').html( getSplitValues( salt2 ) );
    $('#table-token-2').html( getSplitValues( token2 ) );
}


function getTokenSplit( cardNum )
{
    var tempSaltObj = new jsSHA( "SHA-512", "TEXT" );
    var tempHashObj = new jsSHA( "SHA-512", "TEXT" );
    var tempToken   = '';
    
    salt3 = getFirstSixteen( getRandomSalt() );
    
    tempSaltObj.update( salt3 );
    tempHashObj.update( tempSaltObj.getHash('HEX') + cardNum );
    
    tempToken = createString( tempHashObj.getHash( "HEX" ) );
    token3    = getFirstSixteen( tempToken );
    token3    = insertPan( token3 );
    
    $('#table-salt-3').html( getSplitValues( salt3 ) );
    $('#table-token-3').html( getSplitValues( token3 ) );
}


function regenerateTokens()
{
    $('#table-original-pan').html( cardNumberMasked );
    getTokenNumeric( cardNumber );
    getTokenAlphaNumeric( cardNumber );
    getTokenSplit( cardNumber );
}


function parseData()
{
	$('#cardDataSubmit').button('loading');
	
	cardData = $('#cardData').val();
			
	var swipeWithMiddle = /\%\w\d{14,16}\W\b\w{2,30}\/(\s|)\w{2,30}\s\w/;
	var swipeNoMiddle   = /\%\w\d{14,16}\W\b\w{2,30}\/(\s|)\w{2,30}/;
	var swipeError      = /\;E\?/;
	var type            = /\b\d{16}\b/;
	
	if ( cardData.match( swipeWithMiddle ) != null )
	{
		var cardNumberArray = cardData.match(/\d{14,16}/);
		
		cardNumber = cardNumberArray[0];
		NameArray  = cardData.match(/[A-Z]{1,30}/g);
		FirstName  = toTitleCase(NameArray[2]);
		MiddleName = toTitleCase(NameArray[3]);
		LastName   = toTitleCase(NameArray[1]);
		
		cardName         = FirstName + ' ' + MiddleName + ' ' + LastName;
		cardType         = setCardType( cardNumber );
		cardNumberMasked = maskCardNumber( cardNumber );
		
		stepDisplayCardInformation();
	}
	
	else if ( cardData.match( swipeNoMiddle ) != null )
	{
		var cardNumberArray = cardData.match(/\d{14,16}/);
		
		cardNumber = cardNumberArray[0];
		NameArray  = cardData.match(/[A-Z]{1,30}/g);
		FirstName  = toTitleCase(NameArray[2]);
		LastName   = toTitleCase(NameArray[1]);
		
		cardName         = FirstName + ' ' + LastName;
		cardType         = setCardType( cardNumber );
		cardNumberMasked = maskCardNumber( cardNumber );
		
		stepDisplayCardInformation();
	}
	
	else if ( cardData.match( type ) != null )
	{
		var cardNumberArray = cardArray.match(/\d{14,16}/);
		
		cardNumber       = cardNumberArray[0];
		cardType         = setCardType( cardNumber );
		cardNumberMasked = maskCardNumber( cardNumber );
		
		stepDisplayCardInformation();
	}
	
	else if ( cardData.match( swipeError ) != null ) { alert( 'Swipe Error' ); }
	else { alert( 'Something Went Wrong' ); }
}


function setCardType( cardNum )
{
    type = '';
    
    if ( cardNum.charAt(0) == '4' ) { type = 'visa'; }
    else if ( cardNum.charAt(0) == '5' ) { type = 'mastercard'; }
    else if ( cardNum.charAt(0) == '3' ) { type = 'amex'; }
    else if ( cardNum.charAt(0) == '6' ) { type = 'discover'; }
        
    return '<i class="fa fa-5x fa-cc-' + type + '"></i>';
}


function maskCardNumber( cardNum )
{
    var maskedNum = '';
    
    if ( cardNum.length == 15 )
    {        
        for ( i=0; i<cardNum.length; i++ )
        {
            if ( i % 4 == 0 && i != 0 ) { maskedNum = maskedNum + ' '; }
            if ( i > 3 && i < 12 ) { maskedNum = maskedNum + '*'; }
            else { maskedNum = maskedNum + cardNum.charAt(i); }
        }
    }
    
    else if ( cardNum.length == 16 )
    {
        for ( i=0; i<cardNum.length; i++ )
        {
            if ( i % 4 == 0 && i != 0 ) { maskedNum = maskedNum + ' '; }
            if ( i > 3 && i < 12 ) { maskedNum = maskedNum + '*'; }
            else { maskedNum = maskedNum + cardNum.charAt(i); }
        }
    }
    
    else { maskedNum = "**** **** **** ****" }
    
    return maskedNum;
}


function stepInformation()
{
    hidePanel( gettingThingsReady );
    showPanel( information );
}


function stepEnterCardData()
{
    hidePanel( information );
    showPanel( enterCardData );
    
    setTimeout( function() {
        $('#cardData').focus();
    }, 2000);
}


function stepDisplayCardInformation()
{
    $('#dciCardOwnerName').html( cardName );
    $('#dciCardNumberMasked').html( cardNumberMasked );
    $('#dciCardType').html( cardType );
    
    hidePanel( enterCardData );
    showPanel( displayCardInformation );
}


function stepSendCardData()
{    
    hidePanel( displayCardInformation );
    showPanel( sendingCardData );
    
    updateSendStatus( '10%', 2000, "Verifying PAN information for transmission" );
    updateSendStatus( '15%', 5000, "Establishing secure connection through SSL" );
    updateSendStatus( '30%', 9000, "Sending PAN through secured transmission" );
    updateSendStatus( '40%', 15000, "\"Vault\" is verifying authenticity of request" );
    updateSendStatus( '55%', 19000, "Request verified, generating token" );
    updateSendStatus( '80%', 30000, "Token Generated" );
    updateSendStatus( '90%', 34000, "Receiving Token over SSL" );
    updateSendStatus( '95%', 40000, "Storing token on merchants POS" );
    updateSendStatus( '100%', 50000, "Process Complete" );
    
    setTimeout( function() {
        $( '#continueSendingCardData' ).addClass( 'fadeIn' );  
    }, 53000);
}


function stepDisplayToken()
{
    hidePanel( sendingCardData );
    showPanel( displayToken );
    
    regenerateTokens();
}


function stepFinished()
{
    hidePanel( displayToken );
    showPanel( finished );
}


function startDemo()
{
    setTimeout( function() {
        $('#spinner-icon').removeClass('fa-spin').addClass('fadeOut');
    }, 3750);
    
    setTimeout( function() {
        $('#spinner-icon').addClass('fa-thumbs-up').removeClass('fa-spinner');
    }, 4750);
    
    setTimeout( function() {
        $('#spinner-icon').addClass('fadeIn').removeClass('fadeOut');
        $('#startButton').addClass('fadeIn');
    }, 5250);
}

startDemo();