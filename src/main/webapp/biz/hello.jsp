<!DOCTYPE html>
<html>
<head>
    <title>CCTV5 酷玩直播</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="description" content="酷玩直播免费观看足球、NBA、中超、英超、西甲、意甲、德甲、法甲和世界杯直播!">
    <meta name="keywords" content="酷玩直播">

    <meta charset="utf-8" />
    <meta name="applicable-device" content="pc">
    <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <meta HTTP-EQUIV="Expires" CONTENT="0">
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.8.3/jquery.min.js" charset="utf-8"></script>
    <!--<script src="/static/pc/js/fbi.js" charset="utf-8"></script>-->
</head>
<body>
<div class="container">
    <div class="nav-side">
    </div>  <div class="main">
    <div class="wrap">
        <div class="game-play">
            <div class="game-header">
                <div class="game-status">
                    <p class="start-time">CCTV5</p>
                </div>
            </div>
            <div id="showplayer">
                <script type="text/javascript">nostart = false;player('m3u8','http://rtmp-play.3mtw.com/37ting/555373_1/playlist.m3u8');</script>
            </div>
        </div>
        <div class="signal">
            <p onclick="player('cctv5','MkUuBw0adngzO0O0OhO0O0Oi')" class="active">dd</p>

            <p onclick="player('cctv5_bak','MkUuBw0adngzO0O0OhO0O0Oi')" >ss</p>
        </div>
        <div class="clear"></div>
    </div>

</div>
</div>
</div>

</body>
<script type="text/javascript">
    $(function () {
        player('m3u8','http://rtmp-play.3mtw.com/37ting/555373_1/playlist.m3u8');
    });
</script>

<script type="text/javascript">
    var ua = navigator.userAgent,
            iframe_height;
    var ipad = ua.match(/(iPad).*OS\s([\d_]+)/),
            isIphone = !ipad && ua.match(/(iPhone\sOS)\s([\d_]+)/),
            isAndroid = ua.match(/(Android)\s+([\d.]+)/),
            isMobile = isIphone || isAndroid;
    if (isMobile) {
        iframe_height = "200px"
    } else {
        iframe_height = "450px"
    }
    function player(player, vid) {
        if (nostart == true) {
            var ifrmae_url = $("#share_link").val();
            document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='" + ifrmae_url + "' allowfullscreen></iframe>";
            return false;
        }
        switch (player) {
            case 'qie':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='https://1.kuwantiyu.com?s=qie&id=" + vid + "' allowfullscreen></iframe>";
                break
            case 'zhangyu':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=zhangyu&id=" + vid + "' allowfullscreen></iframe>";
                break
            case 'tv':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=tv&id=" + vid + "' allowfullscreen></iframe>";
                break
            case "le":
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=le&id=" + vid + "' allowfullscreen></iframe>";
                break;
            case 'pptv':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=pptv&id=" + vid + "' allowfullscreen></iframe>";
                break
            case 'cctv5':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=cctv5&id=" + vid + "' allowfullscreen></iframe>";
                break;
            case 'longzhu':
                var l_url = isMobile ? "https://1.kuwantiyu.com": 'http://api.kuwanzhibo.com';
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='" + l_url + "?s=longzhu&id=" + vid + "' allowfullscreen></iframe>";
                break
            case 'url':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=url&id=" + vid + "' allowfullscreen></iframe>";
                break
            case 'https':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='https://1.kuwantiyu.com?s=url&id=" + vid + "' allowfullscreen></iframe>";
                break;
            case 'no':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=no&id=" + vid + "' allowfullscreen></iframe>";
                break;
            case 'm3u8':
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' height='200px' frameborder='0' src='http://api.fultz.cn:88/zhibo/m3u8.php?id=" + vid + "' allowfullscreen></iframe>";
                break
            default:
                document.getElementById('showplayer').innerHTML = "<iframe id='video' width='100%' scrolling='no' height='" + iframe_height + "' frameborder='0' src='http://api.kuwanzhibo.com?s=" + player + "&id=" + vid + "' allowfullscreen></iframe>";
                break;
        }
        $(function() {
            var cotrs = $(".signal p");
            cotrs.click(function() {
                $(this).addClass("active").siblings().removeClass("active");
            });
        });
    }
    function init_copy(guid, copy_id) {
        if (guid == 'undefind') {
            return false;
        }
        var clip = null;
        clip = new ZeroClipboard.Client();
        clip.setHandCursor(true);
        clip.addEventListener('mouseOver',
                function(client) {
                    clip.setText($('#' + guid + '').val());
                });
        clip.addEventListener('complete',
                function(client, text) {
                    alert("复制成功，您可以使用该接口地址了^_^");
                });
        clip.glue(copy_id);
    }
</script>
</html>