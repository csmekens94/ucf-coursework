<?php
/**
 * Name:       Robert Arango
 * Course:     CNT 4714 – Fall 2015
 * Assignment: A Three-Tier Distributed Web-Based Application Using PHP and Apache
 * Date:       November 29, 2015
 */


$url   = $_SERVER['REQUEST_URI'];
$parts = explode('/',$url);
$site  = $_SERVER['SERVER_NAME'];

for ( $i = 0; $i < count( $parts ) - 1; $i++ )
    $site .= $parts[$i] . "/";
    
$location = 'Location: http://'.$site.'index.php?';

$route;
$error_type;
$header;
$content;
$javascript;

$mysqli;
$mysqli_db_url = "127.0.0.1";
$mysqli_port   = 3306;
$mysqli_db     = "project5";

if ( isset( $_GET['r'] ) ) { $route = $_GET['r']; }
if ( isset( $_GET['t'] ) ) { $error_type = $_GET['t']; }

session_start();


if ( isset( $_GET['r'] ) )
{
    if ( strcmp( $route, "verify" ) == 0 )
    {
        if ( isset( $_POST['username'] ) && isset( $_POST['password'] ) )
        {
            $mysqli = new mysqli( $mysqli_db_url, $_POST['username'], $_POST['password'], $mysqli_db, $mysqli_port );
    
            if ( $mysqli->connect_errno )
            {                    
                header( $location.'r=error&t=connect' );
            }
            
            else
            {
                $_SESSION['username'] = $_POST["username"];
                $_SESSION['password'] = $_POST["password"];
                $_SESSION['login']    = 1;
                
                header( $location.'r=sql' );
            }
            
            $mysqli->close();
        }
        
        else
        {
            header( $location.'r=error&t=login' );
        }
    }
    
    elseif ( strcmp( $route, "login" ) == 0 )
    {
        $_SESSION['username'] = "";
        $_SESSION['password'] = "";
        $_SESSION['login']    = 0;
        
        $header = '<p class="navbar-text" style="color: #FFF;">Welcome Guest, Please Login Below</p>';
        
        $content = '<div class="col-md-5">
            <div class="well">
                <form id="login" method="post" action="index.php?r=verify">
                    <h2>Login</h2>
                    <hr/>
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="password">
                    </div>
                    <button id="login_button" type="submit" class="btn btn-success">Submit</button>
                </form>
            </div>
        </div>
        
        <div class="col-md-7">
            <h3>Welcome to the Database Client</h3>
            <p>This will allow you to run SQL queries and update statements on the Project 5 Database.</p>
            
            <h3>Database</h3>
            <p>Connecting to MySQL Database</p>
            
            <h3>Features</h3>
            <ul>
                <li>User Authentication</li>
                <li>Select Query</li>
                <li>Update Query</li>
                <li>Business Logic Implementation</li>
                <li>Results Page</li>
                <li>Error Checking</li>
            </ul>
            
            <h3>User Login</h3>
            <p>Use the following to login on the left</p>
            <ul>
                <li><b>Username:</b> Admin</li>
                <li><b>Password:</b> Pass</li>
            </ul>
        </div>';
    }
    
    elseif ( strcmp( $route, "sql" ) == 0 )
    {
        $header = '<li><p class="navbar-text" style="color: #FFF;">Welcome Back '.$_SESSION['username'].'</p></li>
        <li><p class="navbar-btn"><a href="index.php?r=logout" class="btn btn-danger">Logout</a></p></li>';
        
        $content = '<div class="col-md-2"></div>
                    
        <div class="col-md-8">
            <div class="well">
                <form id="sqlCommandForm" method="post">
                    <h3>Enter Query</h3>
                    
                    <p>Please enter a valid SQL query or update statement. You may also just press "Submit Query" to run a default query against the database.</p>
                    
                    <div class="form-group">
                        <textarea class="form-control" id="sqlCommand" name="sqlCommand"
                            placeholder="Enter your SQL update or query here..." rows="10"></textarea>
                    </div>
                    
                    <button id="runQuery" type="submit" class="btn btn-info" formaction="index.php?r=query">
                        Submit Query
                    </button>
                    
                    <button id="runUpdate" type="submit" class="btn btn-success" formaction="index.php?r=update">
                        Submit Update
                    </button>
                    
                    <button id="resetWindow" type="reset" class="btn btn-warning">
                        Reset Window
                    </button>
                </form>
            </div>
        </div>
        
        <div class="col-md-2"></div>';
    }
    
    elseif ( strcmp( $route, "query" ) == 0 )
    {
        $resultTable;
        $mysqli = new mysqli( $mysqli_db_url, $_SESSION['username'], $_SESSION['password'], $mysqli_db, $mysqli_port );
        $query  = $_POST['sqlCommand'];
        
        if ( strcmp( $query, "" ) == 0 )
            $query = "select * from suppliers";
        
        $resultTable .= '<h3>Query Results</h3><br/>';
        
        if ( $result = mysqli_query( $mysqli, $query ) )
        {
            $metadata    = mysqli_fetch_fields( $result );
            $num_columns = mysqli_num_fields( $result );
            
            $resultTable .= '<table class="table table-bordered table-striped table-condensed"><tr>';
               
            for ( $i = 0; $i < $num_columns; $i++ )
                $resultTable .= '<th style="color:white; background: #2c3e50;">' . $metadata[$i]->name . "</th>";

            $resultTable .= "</tr>";

            while ( $row = mysqli_fetch_row( $result ) )
            {
                $resultTable .= "<tr>";
                
                for ($i = 0; $i < $num_columns; $i++)
                    $resultTable .= "<td>" . $row[$i] . "</td>";
                
                $resultTable .= "</tr>";
            }
            
            $resultTable .= "</table>";
        }
        
        else
        {
            $_SESSION['sql_error'] = $mysqli->error;
            header( $location.'r=error&t=sql' );
        }
        
        $mysqli->close();
        
        $content = '<div class="col-md-2"></div>
                    
        <div class="col-md-8">
            '.$resultTable.'
            <br/>
            <a href="index.php" class="btn btn-primary">Return to Main Page</a>
        </div>
        
        <div class="col-md-2"></div>';
    }
    
    elseif ( strcmp( $route, "update" ) == 0 )
    {
        $resultTable;
        $mysqli = new mysqli( $mysqli_db_url, $_SESSION['username'], $_SESSION['password'], $mysqli_db, $mysqli_port );
        $query  = $_POST['sqlCommand'];
        
        $resultTable .= '<h3>Query Results</h3><br/>';
        
        if ( mysqli_query( $mysqli, $query ) )
        {
            $javascript = '<script type="text/javascript"> window.alert("ALERT: SUPPLIER STATUS HAS CHANGED DUE TO BUSINESS LOGIC. DISPLAYING UPDATED SUPPLIER TABLE!"); </script>';
            
            $business_suppliers = mysqli_query( $mysqli, "select distinct snum from shipments where quantity >= 100" );

            while ( $business_rows = mysqli_fetch_row( $business_suppliers ) )
            {
                mysqli_query( $mysqli, "update suppliers set status = status + 5 where snum = '" . $business_rows[$i] . "'" );
            }
            
            $query = "select * from suppliers";
            
            $result = mysqli_query( $mysqli, $query );
            
            $metadata    = mysqli_fetch_fields( $result );
            $num_columns = mysqli_num_fields( $result );
            
            $resultTable .= '<table class="table table-bordered table-striped table-condensed"><tr>';
               
            for ( $i = 0; $i < $num_columns; $i++ )
                $resultTable .= '<th style="color:white; background: #2c3e50;">' . $metadata[$i]->name . "</th>";

            $resultTable .= "</tr>";

            while ( $row = mysqli_fetch_row( $result ) )
            {
                $resultTable .= "<tr>";
                
                for ($i = 0; $i < $num_columns; $i++)
                    $resultTable .= "<td>" . $row[$i] . "</td>";
                
                $resultTable .= "</tr>";
            }
            
            $resultTable .= "</table>";
        }
        
        else
        {
            $_SESSION['sql_error'] = $mysqli->error;
            header( $location.'r=error&t=sql' );
        }
        
        $mysqli->close();
        
        $content = '<div class="col-md-2"></div>
                    
        <div class="col-md-8">
            '.$resultTable.'
            <br/>
            <a href="index.php" class="btn btn-primary">Return to Main Page</a>
        </div>
        
        <div class="col-md-2"></div>';
    }
    
    elseif ( strcmp( $route, "error" ) == 0 )
    {
        if ( strcmp( $error_type, "connect" ) == 0 )
        {
            $mysqli = new mysqli( $mysql_db_url, $_POST['username'], $_POST['password'], $mysql_db, $mysqli_port );
            
            $content = '<div class="col-md-2"></div>
                    
            <div class="col-md-8">
                <div class="alert alert-danger" role="alert" style="text-align:center;">
                    <h2 style="text-align:center;">Connect Error</h2>
                    
                    <p>Could not connect to the database for the following reason:</p>
                    <p>'.$mysqli->connect_error.'</p>
                </div>
                
                <a href="index.php" class="btn btn-primary">Return to Main Page</a>
            </div>
            
            <div class="col-md-2"></div>';
        }
        
        elseif ( strcmp( $error_type, "login" ) == 0 )
        {
            $content = '<div class="col-md-2"></div>
                    
            <div class="col-md-8">
                <div class="alert alert-danger" role="alert" style="text-align:center;">
                    <h2 style="text-align:center;">Login Error</h2>
                    
                    <p>The password or username was incorrect, please try again.</p>
                </div>
                
                <a href="index.php" class="btn btn-primary">Return to Main Page</a>
            </div>
            
            <div class="col-md-2"></div>';
        }
        
        elseif ( strcmp( $error_type, "sql" ) == 0 )
        {
            $content = '<div class="col-md-2"></div>
                        
            <div class="col-md-8">
                <div class="alert alert-danger" role="alert" style="text-align:center;">
                    <h2 style="text-align:center;">Major Error</h2>
                    
                    <p>An SQL Exception occurred while interacting with the Project 5 database.</p>
                    
                    <br/>
                    
                    <p>The error message was:</p>
                    <p>'.$_SESSION['sql_error'].'</p>
                    
                    <br/>
                    
                    <p>Please try again later</p>
                </div>
                
                <a href="index.php" class="btn btn-primary">Return to Main Page</a>
            </div>
            
            <div class="col-md-2"></div>';
        }
    }
    
    elseif ( strcmp( $route, "logout" ) == 0 )
    {
        session_destroy();
        
        $_SESSION['username'] = "";
        $_SESSION['password'] = "";
        $_SESSION['login']    = 0;
        
        header( $location.'r=login' );
    }
}

else
{
    if ( isset( $_SESSION['login'] ) && $_SESSION['login'] == 1 )
    {
        header( $location.'r=sql' );
    }
    
    else
    {
        header( $location.'r=login' );
    }
}
?>

<!DOCTYPE html>
<html lang="en">
	
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		
		<title>CNT 4714 - Project 5</title>
		
		<meta name="generator" content="Bootply" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<!--[if lt IE 9]>
			<script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<link href="css/styles.css" rel="stylesheet">
		
		<?php echo $javascript; ?>
	</head>
	
	<body>
        <div id="wrap">
            <div class="navbar navbar-default navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">Project 5</a>
                    </div>
                    
                    <div class="collapse navbar-collapse">
                        <ul class="nav navbar-nav navbar-right">
                            <?php echo $header; ?>
                        </ul>
                    </div>
                </div>
            </div>
          
            <div class="jumbotron"><div class="container"><h1>CNT 4714 - Project Five Database Client</h1></div></div>
            <div class="container"><div class="row"><?php echo $content; ?></div></div>
            
            <br/><br/>
        </div>
        
        <div id="footer">
            <div class="container">
                <p class="text-muted credit">&copy; RMA CNT 4714 PHP-based Database Client</p>
            </div>
        </div>

		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
