package com.jerry.framework.util;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.*;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码解决方案
 * 使用http://code.google.com/p/patchca/
 * Created with IntelliJ IDEA.
 * Class: CaptchaUtil
 * User: likang
 * Date: 15-5-11
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class CaptchaUtil {

    public static final String CAPTCHATOKEN = "CAPTCHATOKEN";

    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();

    private static Random random = new Random();

    static {
//        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setColorFactory(new ColorFactory() {
            @Override
            public Color getColor(int x) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        cs.setWordFactory(wf);
    }

    public static void createCaptcha(HttpSession session, HttpServletResponse response) throws IOException {
        switch (random.nextInt(5)) {
            case 0:
                cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
                break;
            case 1:
                cs.setFilterFactory(new MarbleRippleFilterFactory());
                break;
            case 2:
                cs.setFilterFactory(new DoubleRippleFilterFactory());
                break;
            case 3:
                cs.setFilterFactory(new WobbleRippleFilterFactory());
                break;
            case 4:
                cs.setFilterFactory(new DiffuseRippleFilterFactory());
                break;
        }
        setResponseHeaders(response);
        String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
        session.setAttribute(CAPTCHATOKEN, token);
        System.out.println("***************sessionid-captcha:"+ session.getId());
    }

    private static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }

    /**
     * 校验验证码
     * @param request
     * @param captcha
     * @return
     */
    public static boolean checkCaptcha(HttpServletRequest request,String captcha) {
        HttpSession session = request.getSession();
        if (StringUtils.isNotBlank(captcha) && session != null && session.getAttribute(CAPTCHATOKEN) != null
                && captcha.equals(((String)session.getAttribute(CAPTCHATOKEN)).toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
