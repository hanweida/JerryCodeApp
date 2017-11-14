package com.jerry.util;

import com.easou.ecom.wl.Wuliao;
import com.easou.mis.pojo.AdExtends;
import com.easou.mis.pojo.Advertisment;
import com.easou.mis.pojo.CpdAd;
import com.easou.mis.pojo.CpdAdExt;
import com.easou.mis.pojo.LockNews;
import com.easou.mis.util.AspBiz.AppAdv;
import com.easou.mis.util.AspBiz.AppShowAdv;
import com.easou.mis.util.AspBiz.BookAdv;
import com.easou.mis.util.AspBiz.BrandAdv;
import com.easou.mis.util.AspBiz.BrandAdv.Builder;
import com.easou.mis.util.AspBiz.ImageAdv;
import com.easou.mis.util.AspBiz.MusicAdv;
import com.easou.mis.util.AspBiz.ShopAdv;
import com.easou.mis.util.AspBiz.ShowTextAdv;
import com.easou.mis.util.AspBiz.TextAdv;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 * 按相关格式(ASP需要的)生成二进制数据
 * @Class Name GenRedisData
 * @Author wushuo
 * @Create In May 7, 2012
 */
public class GenRedisData {
	
/*	*//** 默认以<code>#:#</code> 分割**//*
	private static final String PARTITION = "#:#";*/

	/**
	 * 保存文本广告信息到redis
	 * @param id
	 * @param title 
	 * @param imagePath
	 * @param url
	 * @param showUrl
	 * @param remark
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveTextAdByte(String title, String desc, String url, String showUrl, String phone, String desc1) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(desc, "描述不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		//AssertUtils.notNull(title, "显示URL不能为空");
		//AssertUtils.notNull(title, "电话不能为空");
		TextAdv adMessage = TextAdv.getDefaultInstance();
		com.easou.mis.util.AspBiz.TextAdv.Builder builder = adMessage.newBuilder();
		if (null == phone || "".equals(phone)) {
			builder.setTitle(title).setDes(desc)
						.setUrl(url);
		} else if (null == url || "".equals(url)){
			builder.setTitle(title).setDes(desc)
						.setPhone(phone);
		} else {
			builder.setTitle(title).setDes(desc)
						.setPhone(phone).setUrl(url);
		}
		
		if (null != showUrl && !"".equals(showUrl)) {
			builder.setShowurl(showUrl);
		}
		if (StringUtils.isNotBlank(desc1)) {
			builder.setDes2(desc1);
		}
		adMessage = builder.build();
		return adMessage.toByteArray();
	}
	
	/**
	 * 保存图片广告信息到redis
	 * @param id
	 * @param title 
	 * @param imagePath
	 * @param url
	 * @param showUrl
	 * @param remark
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveImageAdByte(String title, String imageUpload, String url, String showUrl, String phone, String remark, String des) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(imageUpload, "上传图片不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		AssertUtils.notNull(showUrl, "显示URL不能为空");
//		AssertUtils.notNull(phone, "电话不能为空");
		AssertUtils.notNull(remark, "备注不能为空");
		ImageAdv adMessage = ImageAdv.getDefaultInstance();
		adMessage = adMessage.newBuilder().setTitle(title).setImagepath(imageUpload).setUrl(url)
					.setShowurl(showUrl).setPhone(phone).setRemark(remark).setDes(des).build();
		return adMessage.toByteArray();
	}
	
	
	/**
	 * 保存图片广告信息到redis
	 * @param id
	 * @param title 
	 * @param imagePath
	 * @param url
	 * @param showUrl
	 * @param remark
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveShopAdByte(String title, String imageUpload, String url, String showUrl, String phone, String remark, String des, String domain) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(imageUpload, "上传图片不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		AssertUtils.notNull(showUrl, "显示URL不能为空");
//		AssertUtils.notNull(phone, "电话不能为空");
		AssertUtils.notNull(remark, "备注不能为空");
		ShopAdv adMessage = ShopAdv.getDefaultInstance();
		String[] items = remark.split("\n");
		String price = "0";
		String attr = "";
		if(items.length>=4){
			if(!"".equals(items[0])){
				String pricestr = items[0];
				if(pricestr.matches("[0-9]*")){
					price = String.valueOf(Integer.parseInt(pricestr)/100);
					String end = String.valueOf(Integer.parseInt(pricestr)%100);
					if(!"0".equals(end)) price = price + "." + end;
				}else{
					price = pricestr;
				}
			}
			attr += "<span>"+items[1]+"</span><br/>";
			attr += "<span>"+items[2]+"</span><br/>";
			attr += "<span class=ad_gw_showUrl>"+items[3]+"</span><br/>";
		}
		if (null == phone || "".equals(phone)) {
			adMessage = adMessage.newBuilder().setTitle(title).setDes(des).setUrl(url).setShowurl(showUrl)
			.setImagepath(domain +"/"+imageUpload).setAttr(attr).setPrice(price).build();
		}else{
			adMessage = adMessage.newBuilder().setTitle(title).setDes(des).setUrl(url).setShowurl(showUrl)
			.setPhone(phone).setImagepath(domain +"/"+imageUpload).setAttr(attr).setPrice(price).build();
		}
		return adMessage.toByteArray();
	}
	
	/**
	 * 保存图片广告信息到redis
	 * @param id
	 * @param title 
	 * @param imagePath
	 * @param url
	 * @param showUrl
	 * @param des
	 * @param phone
	 */
	public static byte[] saveMusicAdByte(String title, String imagePath, String url, String showUrl, String phone, String des, String des2) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		AssertUtils.notNull(des, "描述不能为空");
		AssertUtils.notNull(showUrl, "显示URL不能为空");
		
		MusicAdv.Builder msg = MusicAdv.newBuilder();
		msg.setTitle(title).setUrl(url).setDes(des).setShowurl(showUrl);
		
		if(!StringUtils.isEmpty(imagePath)) {
			msg.setImagepath(imagePath);
		}
		
		if(!StringUtils.isEmpty(phone)) {
			msg.setPhone(phone);
		}
		
		if (StringUtils.isNotBlank(des2)) {
			msg.setDes2(des2);
		}
		
		return msg.build().toByteArray();
	}
	
	/**
	 * 保存小说广告信息到redis
	 * @param id
	 * @param title 
	 * @param imagePath
	 * @param url
	 * @param showUrl
	 * @param remark
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveStoryAdByte(String title, String url, String remark) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(url, "上传图片不能为空");
		AssertUtils.notNull(remark, "备注不能为空");
		StoryInfo story = GenRedisData.getStoryInfo(remark);
		//add by roger 20120719 start for 判断小说中属性不能为空
		AssertUtils.notNull(story.getCategory(), "类别不能为空");
		AssertUtils.notNull(story.getLatestChapterName(), "章节名不能为空");
		AssertUtils.notNull(story.getLatestChapterURL(), "章节url不能为空");
		AssertUtils.notNull(story.getSrcWebsite(), "章节站点不能为空");
		//add by roger 20120719 end for 判断小说中属性不能为空
		BookAdv adMessage = BookAdv.getDefaultInstance();
		adMessage = adMessage.newBuilder().setTitle(title).setUrl(url)
			.setCategory(story.getCategory()).setChapter(story.getLatestChapterName())
			.setChapterUrl(story.getLatestChapterURL()).setWebsite(story.getSrcWebsite()).build();
		return adMessage.toByteArray();
	}
	
	
	/**
	 * 保存下载广告信息到redis中
	 * @param title
	 * @param desc
	 * @param appPath
	 * @param appUrl
	 * @param remark
	 * @return byte[]
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveAppAdByte(String title, String desc, String appPath, String appUrl, String remark) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(desc, "描述不能为空");
		AssertUtils.notNull(remark, "备注不能为空");
		AppAdv adMessage = AppAdv.getDefaultInstance();
		adMessage = adMessage.newBuilder().setTitle(title).setDes(desc)
				.setApppath(appPath).setAppurl(appUrl).setRemark(remark).build();
		return adMessage.toByteArray();
	}
	
	public static StoryInfo getStoryInfo(String remark) {
		return new GenRedisData.StoryInfo(remark);
	}
	
	public static class StoryInfo {
		private String category;
		
		private String latestChapterName;
		
		private String latestChapterURL;
		
		private String srcWebsite;
		
		public StoryInfo(String remark) {
			String[] parts = remark.split("<br>");
			this.category = parts[0];
			this.latestChapterName = this.getLatestChapterName(parts[1]);
			this.latestChapterURL = this.getLatestChapterURL(parts[1]);
			this.srcWebsite = this.getSrcWebsite(parts[2]);
		}

		public String getCategory() {
			return category;
		}

		public String getLatestChapterName() {
			return latestChapterName;
		}

		public String getLatestChapterURL() {
			return latestChapterURL;
		}

		public String getSrcWebsite() {
			return srcWebsite;
		}
		
		private String getLatestChapterName(String part) {
			Document doc = Jsoup.parse(part);
			Element dodyEle = doc.body();
			return dodyEle.getElementsByTag("a").text();
		}
		
		private String getLatestChapterURL(String part) {
			Document doc = Jsoup.parse(part);
			Element dodyEle = doc.body();
			return dodyEle.getElementsByTag("a").attr("href");
		}
		
		private String getSrcWebsite(String part) {
			return getLatestChapterName(part);
		}
		
	}
	
	
	/**
	 * 
	 * @Methods Name saveBrandAdByte
	 * @Create In Aug 13, 2012 By zhenyu
	 * @param title
	 * @param imageUpload
	 * @param url
	 * @param showUrl
	 * @param phone
	 * @param des
	 * @param linkText1
	 * @param linkHref1
	 * @param linkText2
	 * @param linkHref2
	 * @param linkText3
	 * @param linkHref3
	 * @param linkText4
	 * @param linkHref4
	 * @return byte[]
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveBrandAdByte(String title, String imageUpload, String url, String showUrl, String phone, String des
			, String linkText1, String linkHref1 , String linkText2, String linkHref2 , String linkText3, String linkHref3 , String linkText4, String linkHref4 ) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(imageUpload, "上传图片不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		AssertUtils.notNull(showUrl, "显示URL不能为空");
		BrandAdv adMessage = BrandAdv.getDefaultInstance();
		Builder b = adMessage.newBuilder();
		b.setTitle(title).setImagepath(imageUpload).setUrl(url).setShowurl(showUrl).setDes(des);
		if(!"".equals(phone) && phone!=null){
			b.setPhone(phone);
		}
		if(!"".equals(linkText1) && linkText1!=null){
			b.setText1(linkText1).setHref1(linkHref1);
		}
		if(!"".equals(linkText2) && linkText2!=null){
			b.setText2(linkText2).setHref2(linkHref2);
		}
		if(!"".equals(linkText3) && linkText3!=null){
			b.setText3(linkText3).setHref3(linkHref3);
		}
		if(!"".equals(linkText4) && linkText4!=null){
			b.setText4(linkText4).setHref4(linkHref4);
		}
		adMessage =	b.build();
		return adMessage.toByteArray();
	}
	
	
	
	
	
	/**
	 * 生成展示推送广告字节数组
	 * @Methods Name saveShowAdByte
	 * @Create In 2012-12-24 By likang
	 * @param ad
	 * @param adExt
	 * @return byte[]
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveShowPushAdByte(Advertisment ad, AdExtends adExt) {
		//注意title存入desc中，广告名称存入title中
		AssertUtils.notNull(ad, "ad不能为空");
		AssertUtils.notNull(adExt, "adExt不能为空");
		AssertUtils.notNull(ad.getTitle(), "通知标题不能为空");
		AssertUtils.notNull(ad.getDesc(), "通知内容不能为空");
        AspBiz.PushAdv adMessage = AspBiz.PushAdv.getDefaultInstance();
		com.easou.mis.util.AspBiz.PushAdv.Builder builder = adMessage.newBuilder();

		//通知标题
		if (StringUtils.isNotEmpty(ad.getTitle())) {
			builder.setTitle(ad.getTitle());
		}
        //通知内容
        if (StringUtils.isNotEmpty(ad.getDesc())) {
            builder.setMessage(ad.getDesc());
        }
        //推送图标
        if (StringUtils.isNotEmpty(adExt.getLinkHref1())) {
            builder.setLogoUrl(adExt.getLinkHref1());
        }
        //下载URL,点击URL
        if(StringUtils.isNotEmpty(ad.getUrl())){
            builder.setStreamUrl(ad.getUrl());
        }

		adMessage =	builder.build();
		return adMessage.toByteArray();
	}

    /**
	 * 生成展示推广广告字节数组
	 * @Methods Name saveShowAdByte
	 * @Create In 2012-12-24 By likang
	 * @param ad
	 * @param adExt
	 * @return byte[]
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveShowAdByte(Advertisment ad, AdExtends adExt) {
		/**
		 * 　　	title		    //广告标题
			　　#show_type    //展现形式 1：横幅广告2：全屏广告3：文字广告 去掉
			　　click_type		//点击类型 1：打开网页2：下载程序3：拨打电话
			　　url 				//打开网页或下载程序url
			　　phone			//拨打电话号码
			　　app_show	    //展示类型广告 7
				imagepath1	//上传图片url1（有才set）
			　　imagepath2   //上传图片url2（有才set）
			　　imagepath3   //上传图片url3 （有才set）
			　　remark   //下载广告显示下载按钮 （0 默认：不显示 1：显示）
		 *
		 *
		 *
		 */
		//注意title存入desc中，广告名称存入title中
		AssertUtils.notNull(ad, "ad不能为空");
		AssertUtils.notNull(adExt, "adExt不能为空");
		AssertUtils.notNull(ad.getShowType(), "展现形式不能为空");
		AssertUtils.notNull(ad.getClickType(), "点击类型不能为空");
		AppShowAdv adMessage = AppShowAdv.getDefaultInstance();
		com.easou.mis.util.AspBiz.AppShowAdv.Builder builder = adMessage.newBuilder();
		//广告标题
		if (StringUtils.isNotEmpty(ad.getDesc())) {
			builder.setTitle(ad.getDesc());
		}
		//点击类型 1：打开网页2：下载程序3：拨打电话
		builder.setClickType(ad.getClickType());
		//打开网页或下载程序url
		if (StringUtils.isNotEmpty(ad.getUrl())) {
			builder.setUrl(ad.getUrl());
		}
		//拨打电话号码
		if (StringUtils.isNotEmpty(ad.getPhone())) {
			builder.setPhone(ad.getPhone());
		}
		//上传图片url1
		if (StringUtils.isNotEmpty(adExt.getLinkHref1())) {
			builder.setImagepath1(adExt.getLinkHref1());
		}
		//上传图片url2
		if (StringUtils.isNotEmpty(adExt.getLinkHref2())) {
			builder.setImagepath2(adExt.getLinkHref2());
		}
		//上传图片url3
		if (StringUtils.isNotEmpty(adExt.getLinkHref3())) {
			builder.setImagepath3(adExt.getLinkHref3());
		}
        //下载广告下载按钮
        if(StringUtils.isNotEmpty(ad.getRemark())){
            builder.setShowDownloadBtn(ad.getRemark());
        } else {
            builder.setShowDownloadBtn("0");
        }
        adMessage =	builder.build();
        return adMessage.toByteArray();
    }


    /**
     * 生成展示插屏广告字节数组
     * @Methods Name saveShowAdByte
     * @Create In 2012-12-24 By likang
     * @param ad
     * @param adExt
     * @return byte[]
     */
    @SuppressWarnings("static-access")
    public static byte[] saveShowPlaqueAdByte(Advertisment ad, AdExtends adExt) {
        //注意title存入desc中，广告名称存入title中
        AssertUtils.notNull(ad, "ad不能为空");
        AssertUtils.notNull(adExt, "adExt不能为空");
        AssertUtils.notNull(ad.getShowType(), "展现形式不能为空");
        AssertUtils.notNull(ad.getClickType(), "点击类型不能为空");
        AspBiz.PlaqueAdv adMessage = AspBiz.PlaqueAdv.getDefaultInstance();
        com.easou.mis.util.AspBiz.PlaqueAdv.Builder builder = adMessage.newBuilder();
        //广告标题
        if (StringUtils.isNotEmpty(ad.getTitle())) {
            builder.setTitle(ad.getTitle());
        }
        //点击类型 1：打开网页2：下载程序3：拨打电话
        builder.setClickType(ad.getClickType());
        //打开网页或下载程序url
        if (StringUtils.isNotEmpty(ad.getUrl())) {
            builder.setUrl(ad.getUrl());
        }
        //拨打电话号码
        if (StringUtils.isNotEmpty(ad.getPhone())) {
            builder.setPhone(ad.getPhone());
        }
        //上传图片url
        if (StringUtils.isNotEmpty(adExt.getLinkHref1())) {
            builder.setImagepath(adExt.getLinkHref1());
        }
        //下载广告下载按钮
        if(StringUtils.isNotEmpty(ad.getRemark())){
            builder.setShowDownloadBtn(ad.getRemark());
        } else {
            builder.setShowDownloadBtn("0");
        }
		adMessage =	builder.build();
		return adMessage.toByteArray();
	}
	
	
	/**
	 * 保存展示文本广告信息到redis
	 * @param title
	 * @param url
	 * @param showUrl
	 */
	@SuppressWarnings("static-access")
	public static byte[] saveShowTextAdByte(String title, String desc, String url, String showUrl, String phone, String desc1) {
		AssertUtils.notNull(title, "标题不能为空");
		AssertUtils.notNull(desc, "描述不能为空");
		AssertUtils.notNull(url, "URL不能为空");
		//AssertUtils.notNull(title, "显示URL不能为空");
		//AssertUtils.notNull(title, "电话不能为空");
		ShowTextAdv adMessage = ShowTextAdv.getDefaultInstance();
		com.easou.mis.util.AspBiz.ShowTextAdv.Builder builder = adMessage.newBuilder();
		if (StringUtils.isNotBlank(title)) {
			builder.setTitle(title);
		}
		if (StringUtils.isNotBlank(desc)) {
			builder.setDes(desc);
		}
		if (StringUtils.isNotBlank(url)) {
			builder.setUrl(url);
		}
		if (StringUtils.isNotBlank(showUrl)) {
			builder.setShowurl(showUrl);
		}
		if (StringUtils.isNotBlank(phone)) {
			builder.setPhone(phone);
		}
		if (StringUtils.isNotBlank(desc1)) {
			builder.setDes2(desc1);
		}
		adMessage = builder.build();
		return adMessage.toByteArray();
	}
	
	/**
	 * 保存展示积分墙广告到redis中
	 * @Methods Name saveShowWallAdByte
	 * @Create In 2014-3-24 By likang
	 * @param ad
	 * @param adExt
	 * @return byte[]
	 */
	public static byte[] saveShowWallAdByte(Advertisment ad, AdExtends adExt) {
        //注意包大小存入Remark中，包名存入adext LinkText1中
        AssertUtils.notNull(ad, "ad不能为空");
        AssertUtils.notNull(adExt, "adExt不能为空");
        AspBiz.PointsWallAdv adMessage = AspBiz.PointsWallAdv.getDefaultInstance();
        com.easou.mis.util.AspBiz.PointsWallAdv.Builder builder = adMessage.newBuilder();
        // new java.lang.String[] { "Downurl", "Des", "Des2", "Logo", "Image1", "Image2", "Image3", "Appname", "Pkname", "Pksize", },
        //应用名称
        if (StringUtils.isNotBlank(ad.getTitle())) {
        	builder.setAppname(ad.getTitle());
        }
        //包名
        if (StringUtils.isNotBlank(adExt.getLinkText1())) {
        	builder.setPkname(adExt.getLinkText1());
        }
        //包大小
        if (StringUtils.isNotBlank(ad.getRemark())) {
        	builder.setPksize(Integer.parseInt(ad.getRemark()));
        }
        //下载url
        if (StringUtils.isNotBlank(ad.getUrl())) {
        	builder.setDownurl(ad.getUrl());
        }
        //应用简介
        if (StringUtils.isNotBlank(ad.getDesc())) {
        	builder.setDes(ad.getDesc());
        }
        //应用介绍
        if (StringUtils.isNotBlank(ad.getDesc1())) {
        	builder.setDes2(ad.getDesc1());
        }
        //小图标
        if (StringUtils.isNotBlank(adExt.getLinkHref1())) {
        	builder.setLogo(adExt.getLinkHref1());
        }
        //截图1
        if (StringUtils.isNotBlank(adExt.getLinkHref2())) {
        	builder.setImage1(adExt.getLinkHref2());
        }
        //截图2
        if (StringUtils.isNotBlank(adExt.getLinkHref3())) {
        	builder.setImage2(adExt.getLinkHref3());
        }
        //截图3
        if (StringUtils.isNotBlank(adExt.getLinkHref4())) {
        	builder.setImage3(adExt.getLinkHref4());
        }
        //激活验证接口地址
        if (StringUtils.isNotBlank(adExt.getLinkText2())) {
        	builder.setActiveurl(adExt.getLinkText2());
        }
        //设备码
        if (StringUtils.isNotBlank(adExt.getLinkText3())) {
        	builder.setDevicetype(adExt.getLinkText3());
        }
		//appid
        if (StringUtils.isNotBlank(adExt.getRemark1())) {
        	builder.setStoreappid(adExt.getRemark1());
        }
		//进程名称
		if (StringUtils.isNotBlank(adExt.getLinkText4())) {
	      	builder.setAppprocname(adExt.getLinkText4());
	    }
		//app类型
		if (StringUtils.isNotBlank(ad.getCategoryAdStrs())) {
			builder.setAppType(ad.getCategoryAdStrs());
			builder.setAppTypeName(ad.getCategoryStrName().replace("，", " "));
		}
		//备用字段2 积分墙广告存储 获取积分说明
		if (StringUtils.isNotBlank(adExt.getRemark2())) {
        	builder.setStepInfo(StringEscapeUtils.unescapeHtml4(adExt.getRemark2()));
        }
		//备用字段3 积分墙广告按钮文字
		if (StringUtils.isNotBlank(adExt.getRemark3())) {
        	builder.setBtnName(adExt.getRemark3());
        }
		//备用字段4 积分墙广告公司名称
		if (StringUtils.isNotBlank(adExt.getRemark4())) {
        	builder.setCompany(adExt.getRemark4());
        }
        //备用字段6 IOS积分墙广告 scheme
        if(StringUtils.isNotBlank(adExt.getRemark6())){
            builder.setScheme(adExt.getRemark6());
        }
		adMessage =	builder.build();
		return adMessage.toByteArray();
	}

    /**
     * 保存锁屏广告到Redis中
     * @author asilin
     * @param ad
     * @param adExt
     * @return
     */
    public static byte[] saveLockAdByte(Advertisment ad, AdExtends adExt) {
        //同积分墙广告，包大小存入Remark中，包名存入adext LinkText1中
        AssertUtils.notNull(ad, "ad不能为空");
        AssertUtils.notNull(adExt, "adExt不能为空");
        AssertUtils.notNull(ad.getClickType(), "点击类型不能为空");
        AssertUtils.notNull(adExt.getRemark5(),"计费形式不能为空");
        AspBiz.LockScreenAdv adMessage = AspBiz.LockScreenAdv.getDefaultInstance();
        com.easou.mis.util.AspBiz.LockScreenAdv.Builder builder = adMessage.newBuilder();
        //设置点击类型
        builder.setClickType(ad.getClickType());
        //设置计费形式
        builder.setLockScreenChargeType(Integer.parseInt(adExt.getRemark5()));
        //广告名称
        if (StringUtils.isNotBlank(ad.getTitle())) {
            builder.setTitle(ad.getTitle());
        }
        //应用名称
        if (StringUtils.isNotBlank(ad.getTitle())) {
            builder.setAppname(ad.getTitle());
        }
        //简介
        if (StringUtils.isNotBlank(ad.getDesc())) {
            builder.setDes(ad.getDesc());
        }
        //介绍
        if (StringUtils.isNotBlank(ad.getDesc1())) {
            builder.setDes2(ad.getDesc1());
        }
        //宣传图片
        if (StringUtils.isNotBlank(adExt.getLinkHref1())) {
            builder.setImagepath(adExt.getLinkHref1());
        }
        //锁屏广告时 左滑下载程序时 中间页应用小图标
        if(StringUtils.isNotBlank(adExt.getLinkHref2())){
            builder.setLogo(adExt.getLinkHref2());
        }
        //锁屏广告时 左滑下载程序时 中间页应用截图
        if(StringUtils.isNotBlank(adExt.getLinkHref3())){
            builder.setImage1(adExt.getLinkHref3());
        }
        //锁屏广告 左滑下载程序时 中间页应用截图
        if(StringUtils.isNotBlank(adExt.getLinkHref4())){
            builder.setImage2(adExt.getLinkHref4());
        }
        //app类型
        if (StringUtils.isNotBlank(ad.getCategory())) {
            //锁屏广告应用类型是单选，故不必替换字符
            builder.setAppType(ad.getCategory());
            builder.setAppTypeName(ad.getCategoryStrName().replace("，", " "));
        }
        //包大小
        if (StringUtils.isNotBlank(ad.getRemark())) {
            builder.setPksize(Integer.parseInt(ad.getRemark()));
        }
        //包名
        if (StringUtils.isNotBlank(adExt.getLinkText1())) {
            builder.setPkname(adExt.getLinkText1());
        }
        //备用字段2 锁屏广告存储 获取积分说明
        if (StringUtils.isNotBlank(adExt.getRemark2())) {
            builder.setStepInfo(adExt.getRemark2());
        }
        //按钮名称
        if (StringUtils.isNotBlank(adExt.getLinkText2())){
            builder.setBtnName(adExt.getLinkText2());
        }
        //公司名称
        if (StringUtils.isNotBlank(adExt.getLinkText3())){
            builder.setCompany(adExt.getLinkText3());
        }
        adMessage =	builder.build();
        return adMessage.toByteArray();
    }

    /**
     * 保存锁屏新闻广告到redis中
     * @Methods Name saveShowLockNewsByte
     * @Create In 2014-6-14 By weida
     * @param lockNews
     * @return byte[]
     */
    public static byte[] saveShowLockNewsByte(LockNews lockNews) {
        AssertUtils.notNull(lockNews, "lockNews不能为空");
        AspBiz.NewsMsg newsMsg = AspBiz.NewsMsg.getDefaultInstance();
        AspBiz.NewsMsg.Builder builder = newsMsg.newBuilder();
        //新闻图片地址进行判空
        if (StringUtils.isNotBlank(lockNews.getImgurl())) {
            builder.setImagepath(lockNews.getImgurl());
        }
        if (StringUtils.isNotBlank(lockNews.getName())) {
            builder.setTitle(lockNews.getName());
        }
        newsMsg = builder.build();
        return newsMsg.toByteArray();
    }

    /**
     * 生成展示图文广告字节数组
     * @Methods Name saveShowAdByte
     * @Create In 2015-03-20 By weida
     * @param ad
     * @param adExt
     * @return byte[]
     */
    @SuppressWarnings("static-access")
    public static byte[] saveShowImageTextAdByte(Advertisment ad, AdExtends adExt) {
        //注意title存入desc中，广告名称存入title中
        AssertUtils.notNull(ad, "ad不能为空");
        AssertUtils.notNull(adExt, "adExt不能为空");
        AssertUtils.notNull(ad.getShowType(), "展现形式不能为空");
        AssertUtils.notNull(ad.getClickType(), "点击类型不能为空");
        AspBiz.TuWenAdv adMessage = AspBiz.TuWenAdv.getDefaultInstance();
        com.easou.mis.util.AspBiz.TuWenAdv.Builder builder = adMessage.newBuilder();
        //广告标题
        if (StringUtils.isNotEmpty(ad.getTitle())) {
            builder.setTitle(ad.getTitle());
        }
        //广告描述
        if (StringUtils.isNotEmpty(ad.getDesc())) {
            builder.setDes(ad.getDesc());
        }
        //点击类型 1：打开网页2：下载程序3：拨打电话
        builder.setClickType(ad.getClickType());
        //打开网页或下载程序url
        if (StringUtils.isNotEmpty(ad.getUrl())) {
            builder.setUrl(ad.getUrl());
        }
        //拨打电话号码
        if (StringUtils.isNotEmpty(ad.getPhone())) {
            builder.setPhone(ad.getPhone());
        }
        //上传图片url1
        if (StringUtils.isNotEmpty(adExt.getLinkHref1())) {
            builder.setImagepath1(adExt.getLinkHref1());
        }
        //上传图片url2
        if (StringUtils.isNotEmpty(adExt.getLinkHref2())) {
            builder.setImagepath2(adExt.getLinkHref2());
        }
        //上传图片url3
        if (StringUtils.isNotEmpty(adExt.getLinkHref3())) {
            builder.setImagepath3(adExt.getLinkHref3());
        }
        //下载广告下载按钮
        if(StringUtils.isNotEmpty(ad.getRemark())){
            builder.setShowDownloadBtn(ad.getRemark());
        } else {
            builder.setShowDownloadBtn("0");
        }
        adMessage =	builder.build();
        return adMessage.toByteArray();
    }

    /**
     * 生成展示图文广告字节数组
     * @Methods Name saveCpdAdByte
     * @Create In 2015-03-20 By weida
     * @param ad
     * @param adExt
     * @return byte[]
     */
    @SuppressWarnings("static-access")
    public static byte[] saveCpdAdByte(CpdAd ad, CpdAdExt adExt) {
        //注意title存入desc中，广告名称存入title中
        AssertUtils.notNull(ad, "ad不能为空");
        AssertUtils.notNull(adExt, "adExt不能为空");

        Wuliao.ICON_WL adMessage = Wuliao.ICON_WL.getDefaultInstance();
        Wuliao.ICON_WL.Builder builder = adMessage.newBuilder();
        //计划标题
        if (StringUtils.isNotEmpty(ad.getPlanname())) {
            builder.setPlanName(ad.getPlanname());
        }
        //广告标题
        if (StringUtils.isNotEmpty(ad.getTitle())) {
            builder.setTitle(ad.getTitle());
        }
        //广告描述
        if (StringUtils.isNotEmpty(ad.getDes())) {
            builder.setDesc(ad.getDes());
        }
        //广告介绍
        if (StringUtils.isNotEmpty(ad.getDes1())) {
            builder.setIntroduce(ad.getDes1());
        }
        //应用icon url地址
        if (StringUtils.isNotEmpty(ad.getIcon())) {
            builder.setIcon(ad.getIcon());
        }

        //应用截图
        if (StringUtils.isNotEmpty(adExt.getUrl1())) {
            builder.addPicUrls(adExt.getUrl1());
        }
        if (StringUtils.isNotEmpty(adExt.getUrl2())) {
            builder.addPicUrls(adExt.getUrl2());
        }
        if (StringUtils.isNotEmpty(adExt.getUrl3())) {
            builder.addPicUrls(adExt.getUrl3());
        }
        if (StringUtils.isNotEmpty(adExt.getUrl4())) {
            builder.addPicUrls(adExt.getUrl4());
        }
        if (StringUtils.isNotEmpty(adExt.getUrl5())) {
            builder.addPicUrls(adExt.getUrl5());
        }

        //应用下载地址
        if (StringUtils.isNotEmpty(ad.getPkurl())) {
            builder.setDlUrl(ad.getPkurl());
        }
        //包名
        if (StringUtils.isNotEmpty(ad.getPkname())) {
            builder.setPkgName(ad.getPkname());
        }
        //包大小
        builder.setPkgSize(ad.getPksize());
        //应用版本号
        if (StringUtils.isNotEmpty(ad.getVersion())) {
            builder.setPkgVersion(ad.getVersion());
        }
        // 应用要求的最低手机系统版本
        if (StringUtils.isNotEmpty(ad.getSupport())) {
            builder.setOsVersion(ad.getSupport());
        }
        //catalog_level
        if (StringUtils.isNotEmpty(ad.getApp_level())) {
            builder.setCatalogLevel(Integer.parseInt(ad.getApp_level()));
        }
        //catalog_level_1
        if (StringUtils.isNotEmpty(ad.getApp_level_1())) {
            builder.setCatalogLevel1(Integer.parseInt(ad.getApp_level_1()));
        }
        //catalog_level_11
        if (StringUtils.isNotEmpty(ad.getApp_level_11())) {
            builder.setCatalogLevel11(Integer.parseInt(ad.getApp_level_11()));
        }
        //catalog_level_2
        if (StringUtils.isNotEmpty(ad.getApp_level_2())) {
            builder.setCatalogLevel2(Integer.parseInt(ad.getApp_level_2()));
        }
        //catalog_level_21
        if (StringUtils.isNotEmpty(ad.getApp_level_21())) {
            builder.setCatalogLevel21(Integer.parseInt(ad.getApp_level_21()));
        }
        // 物料更新时间
        builder.setUpdateTime(DateTool.getMillisOfDate(ad.getLasttime()));
        //评分 [default = 8]
        builder.setStar(8);
        //运营类别 default = 7
        builder.setOperaTag(7);
        //cnsite = 20 [default = "安卓助手"];
        builder.setCnsite("安卓助手");
        //int32 downloadCount = 21 [default = 0];
        builder.setDownloadCount(0);

        adMessage =	builder.build();
        return adMessage.toByteArray();
    }
}
