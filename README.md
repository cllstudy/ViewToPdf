# ViewToPdf
itextpdf尝试,android系统生成pdf,截长图
#### 通过系统PdfDocument将view生成pdf

 ![](/view.jpg)

该方式生成pdf太耗时,测试在30s内.
上传项目后在测试,程序会无响应,应该是线程的问题,可以把生成pdf这块放在子线程.

#### 截取RecycleView

 ![](/recy.jpg)

可以先点击截图然后再点击生成pdf,速度快,但缺点是RecycleView数据过多时pdf显示出问题,无法分页.

#### 通过itextG生成pdf

 ![](/itextpdf.jpg)

速度快,自动分页,使用该方式需要注意:

gradle导包需要导入itextG包,而不是itext,itextG是android适用的包,itext包含java.awt,java.awt是java环境的，不能在android上用的。

>  compile 'com.itextpdf:itextg:5.5.10'

itextG不支持中文,如果不做任何处理,中文会显示空白,网上的解决方案是加入itext-asian.jar 包,
然后如下设置:

	public static Font getPdfChineseFont() throws Exception {
    BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
            BaseFont.NOT_EMBEDDED);
    Font fontChinese = new Font(bfChinese, 12, Font.NORMAL);
    return fontChinese;
	}

其实这么做没有任何用处,处理方式如下:

	baseFont = BaseFont.createFont("assets/simsun.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

只需要修改此处就可以了,引入本地字体库.具体见代码
