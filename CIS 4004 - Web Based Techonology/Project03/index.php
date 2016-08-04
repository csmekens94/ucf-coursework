<!DOCTYPE html>

<head>
    <meta charset="utf-8">
    <title>CIS 4004</title>
    
    <style TYPE="text/css">
        body {
            background: #333;
            color: #FFF;
        }    
        
        a:link {
            color: #FFF;
        }
        
        a:visited {
            color: #FFF;
        }
        
        a:hover {
            color: #FFF;
        }
        
        a:active {
            color: #FFF;
        }
    </style>
</head>


<body>

<h1>CIS 4004 - Web Projects</h1>

<ul>
    <?php
        $sites = scandir("/var/www/Development/cis4004.dev/Project03");
        
        foreach($sites as $site)
        {
            if ($site != "." && $site != ".." && $site != "index.php")
            {
                $title = $site;
                echo '<li><h2><a href="http://cis4004.dev/Project03/' . $site . '/">' . $title . '/</a></h2></li>';
            }
        }
    ?>

</body>
</html>