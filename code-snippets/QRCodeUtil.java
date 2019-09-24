package com.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

/**
 * 生成二维码工具类
 */
public class QRCodeUtil {
    /**
     * 推荐二维码颜色 0xFF 后的六位为16进制的颜色值#000000
     */
    public static final int QRCODE_COLOR = 0xFF000000;
    /**
     * 推荐二维码背景颜色
     */
    public static final int QRCODE_BACKGROUND_COLOR = 0xFFFFFFFF;
    /**
     * 推荐二维码的白边设置(0-4) 0为无白边
     */
    public static final int QRCODE_MARGIN = 0;
    /**
     * 生成二维码
     * @param url
     * @return
     */
    public static String generateQRCode(String url) {
        return generateCode(url, 400, 400, "",BarcodeFormat.QR_CODE);
    }


    /**
     * generateCode   通过barcodeFormat枚举  生成二维码/条形码
     * @param url 要生成的内容
     * @param width
     * @param height
     * @param logoPath logo所在的网络地址
     * @param barcodeFormat 返回的base64格式的图片字符串(png格式)
     * @return
     */
    public static String generateCode(String url,int width, int height, String logoPath,BarcodeFormat barcodeFormat) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, QRCODE_MARGIN);  //设置白边
        String binary = null;
        try {
            //生成二维码矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, barcodeFormat, width, height, hints);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //二维码中画入logo
            BufferedImage image = writeLogoToQrcode(bitMatrix,logoPath);
            //文件转换为字节数组
            ImageIO.write(image, "png", out);
            byte[] bytes = out.toByteArray();

            //进行base64编码
            binary = Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回png格式的base64编码数据
        return "data:image/png;base64,"+ binary;
    }



    /**
     *
     * @Title: toBufferedImage
     * @Description: 二维码矩阵转换为BufferedImage
     * @param matrix
     * @return BufferedImage 返回类型
     * @throws
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //ConstantQRCode.QRCODE_COLOR和ConstantQRCode.QRCODE_BACKGROUND_COLOR为二维码颜色和背景颜色
                image.setRGB(x, y, matrix.get(x, y) ? QRCODE_COLOR : QRCODE_BACKGROUND_COLOR);
            }
        }
        return image;
    }
    /**
     *
     * @param matrix 二维码矩阵相关
     * @param logoUrl logo路径
     * @throws IOException
     */
    public static BufferedImage writeLogoToQrcode(BitMatrix matrix,String logoUrl) throws IOException {
        //二维码矩阵转换为BufferedImage
        BufferedImage image = toBufferedImage(matrix);
        //是否传入了logo地址
        if(StringUtils.isNotBlank(logoUrl)){
            URL url = new URL(logoUrl);
            //取得二维码图片的画笔
            Graphics2D gs = image.createGraphics();

            int ratioWidth = image.getWidth()*2/10;
            int ratioHeight = image.getHeight()*2/10;
            //读取logo地址
            Image img = ImageIO.read(url);
            int logoWidth = img.getWidth(null)>ratioWidth?ratioWidth:img.getWidth(null);
            int logoHeight = img.getHeight(null)>ratioHeight?ratioHeight:img.getHeight(null);
            //设置logo图片的位置
            int x = (image.getWidth() - logoWidth) / 2;
            int y = (image.getHeight() - logoHeight) / 2;
            //开画
            //gs.drawImage(Image logo, int logo横坐标, int logo纵坐标, int logo宽, int logo高, null);
            gs.drawImage(img, (int)(x), (int)(y), logoWidth, logoHeight, null);
            gs.dispose();
            img.flush();
        }
        return image;
    }
}
