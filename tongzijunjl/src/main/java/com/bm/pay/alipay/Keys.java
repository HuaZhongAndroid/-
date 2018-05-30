/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.bm.pay.alipay;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	//合作身份者id，以2088开头的16位纯数字   2088911456538585
	public static final String DEFAULT_PARTNER = "2088911456538585";

	//收款支付宝账号
	public static final String DEFAULT_SELLER = "huafatech@qq.com";  

	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMizuC0C9QFDkzbWTwmrVTyxcohgxPal3//H5XErdtxkvvmN3EHljr1KrHkZ4LBTqlPFT7VVegYlnwB8t53dpzhzVhxpV58mVAzxPXvvBwF5edhmKHQPEEeek5a9/7S0tVXZR2IUEup0Tu9NIEPIMI37xwhBE/rru7k5eDt0nCUXAgMBAAECgYAPHPFmek2GvdbtDzA+cydk/Zu5R0gtpon6kcvmPLajm/aBhjwqviWEfYYFTHyN6o7t5fK9neUlZ1nUyI9vFMzNohttN2/1VqiS180dH00rKDDhrYSYJYGD89vN2rMEXQhbZTYxYzDoBkbyN/Qq1kmGKKqqqUidUBZwT60qpLWr0QJBAPpTd27BUWEyi9zPD8J78UgSoF2aImU7Hu6UPKjwpd6BBynwgLwTn9SIld8pQjUe+XIoZGuYHijUcmK8r/lPqikCQQDNQE5oWB44m5coqiJIilAv8t8Zm8WY8GvLI/AuOXzpXEfBkZIp3Z0lj3DHOHuB7nAp66gJz45/lVitd7p8eb0/AkAM1XqqEv85HH5PM857Ch+0sj/GjMB2sDFFyhPSFYRrg/H6FHqFKfxnyRjl+BeM4amoUKnVWlcxgLLcnQ/omE0BAkBgNPdLo5ou0Idu8Sh2thZWE1KNz100jeUI6ASJtjqdq7qaueA4LX+efnjPeOMziQ8cJzCxwI87sEZLsSZBeFLDAkEA+E/9isu83AMc2s2A/90V/SgX5eISMBjeVhP4OIcE3kJEG6CwkaIdRgAGd/dfdmLkA8fcIQRFPjawHw9O5a8rBg==";

	//公钥
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaTDV+KqrIfLXv2n/74vO59BLChLOrFKALFkEGKi677wUDMmgLz8RizNrqUKAyceO9/gS37ojik1q6/+XieQ5YnBftXbLz2C58L+d1unIaxqIph8ttYYZ6Q0trYUbG8jbwqJuznNCrvuBR8svTxmDoV7vyMZXJYXFK/Z5qoeVF1QIDAQAB";

}
